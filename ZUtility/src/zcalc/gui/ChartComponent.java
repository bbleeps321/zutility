package zcalc.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Rectangle2D;
import java.text.NumberFormat;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartMouseEvent;
import org.jfree.chart.ChartMouseListener;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.axis.TickUnit;
import org.jfree.chart.axis.TickUnitSource;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.Marker;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.ValueMarker;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.RectangleAnchor;
import org.jfree.ui.RectangleEdge;

import zcalc.math.Constant;
import zcalc.math.ExprString;
import zcalc.math.Expression;
import zcalc.math.Function;
import zcalc.math.Variable;

/**
 * Controls the chart and graphing of functions.
 */
public class ChartComponent extends OutputComponent implements KeyListener
{
	private static final int ENABLED = 0;
	private static final int NAME = 1;
	private static final int FUNCTION = 2;
	
	public static String VARIABLE = "X";
	
	private JTable functionTable;
	private ChartPanel chart;
	private XYSeriesCollection functionData;	// row index in table (index in functions list) is key.
	private ZoomDialog zoomDialog;	// dialog for handling window range
	
	// the following 2 lists should be synchronized in size and values
	private ArrayList<Function> functions;
	private ArrayList<Boolean> enabled;
	
	// Last marker to be added to the domain/range axes.
	private Marker oldDomainMarker, oldRangeMarker;
	
	public ChartComponent()
	{
		super(new BorderLayout());
		
		functions = new ArrayList<Function>();
		enabled = new ArrayList<Boolean>();

		// set up table
		JPanel tablePane = createTableComponent();
		
		functionData = new XYSeriesCollection();	// empty
		
		// set up chart
		chart = createChartPanel();
		updateAxesScales();
		
		// set up split		
		JSplitPane split = new JSplitPane(JSplitPane.VERTICAL_SPLIT, tablePane, chart);
		
		// add components to this component
		add(split, BorderLayout.CENTER);
	}
	
	/**
	 * Handles when the any key is pressed.
	 */
	public void keyPressed(KeyEvent e)
	{
		// enter key
		if (!functionTable.isEditing())
			return;
		Component comp = functionTable.getEditorComponent();
		if (!(comp instanceof ExprLineEdit))
			return;
		ExprLineEdit editor = (ExprLineEdit)comp;
		
		if (e.getKeyCode() == KeyEvent.VK_ENTER)
			functionTable.editingStopped(null);
		else
			editor.keyPressed(e);
	}
	
	/**
	 * Adds the specified function to the component and plots it.
	 */
	public void addFunction(Function f)
	{
		// update table
		functions.add(f);
		enabled.add(true);	// new functions enabled by default
		functionTable.updateUI();
		
		// update chart ONLY if f is a valid function
		if (f.isValid())
		{
			XYSeries data = getData(f);
			if (data == null)
				return;
			functionData.addSeries(data);
		}
		rescaleChart();	// maintain window range
	}
	
	/**
	 * Sets the function at the specified index and updates the plot.
	 */
	public void setFunction(int index, Function f)
	{
		functions.set(index, f);
		// update chart ONLY if f is a valid function
		if (f.isValid())
		{
			XYSeries series = getData(f); 
			if (series == null)
				return;
			// series currently plotted
			if (isPlotted(index))
				functionData.removeSeries(functionData.indexOf(index));
			functionData.addSeries(series);
		}
		
		// table
		functions.set(index, f);
		enabled.set(index, true);	// new functions enabled by default
		functionTable.updateUI();
		
		rescaleChart();	// maintain window range
	}
	
	/**
	 * Gets XY data as a double[][] for the specified function
	 * over the domain of the current window of the chart.
	 */
	private XYSeries getData(Function f)
	{
		// test function with dummy value
		if (!f.isValid())
		{
			System.err.println("Invalid function!");
			return null;
		}
		Constant oldVal = Variable.getVar(VARIABLE).evaluate();	// old variable value
		double xMin = Config.WINDOW_RANGE.xMin();
		double xMax = Config.WINDOW_RANGE.xMax();
		double xScl = Config.WINDOW_RANGE.xScl();
		int TOTAL_POINTS = (int)((xMax-xMin)/xScl)*Config.SCALE_OFFSET;
		XYSeries data = new XYSeries(functions.indexOf(f), true, false);
		System.out.println(f+".key="+data.getKey());
		for (int i = 0; i < TOTAL_POINTS; i++)
		{
			double xVal = xMin + xScl/Config.SCALE_OFFSET*i;
			double yVal = f.evaluate(VARIABLE, xVal).doubleValue();
			data.add(xVal, yVal);
		}
		Variable.getVar(VARIABLE).setValue(oldVal);	// reset to old val
		
		return data;
	}
	
	/**
	 * Scales the chart to maintain the set window range.
	 */
	private void rescaleChart()
	{
		JFreeChart c = chart.getChart();
		c.getXYPlot().getDomainAxis().setRange(Config.WINDOW_RANGE.getXRange());
		c.getXYPlot().getRangeAxis().setRange(Config.WINDOW_RANGE.getYRange());
	}
	
	private ChartPanel createChartPanel()
	{
		ChartPanel panel = new ChartPanel(createEmptyChart());
		panel.addChartMouseListener(new ChartMouseListener()
		{
			public void chartMouseMoved(ChartMouseEvent e)
			{
				updatePointDisplay(e.getTrigger());
			}
			
			public void chartMouseClicked(ChartMouseEvent e) {}
		});
		panel.addMouseListener(new MouseListener()
		{
			private Point pressPos = null;
			
			public void mouseReleased(MouseEvent e)
			{
				if (pressPos == null)
				{
					System.err.println("wtf???");
					return;
				}
				// zoom fit
				if (e.getPoint().getX() < pressPos.getX() && e.getPoint().getY() < pressPos.getY())
				{
					// reset to defaults
					chart.getChart().getXYPlot().getDomainAxis().setRange(Config.BASE_WINDOW_RANGE.getXRange());
					chart.getChart().getXYPlot().getRangeAxis().setRange(Config.BASE_WINDOW_RANGE.getYRange());
					Config.WINDOW_RANGE = Config.BASE_WINDOW_RANGE.modifiableCopy();
					updateAxesScales();
				}
				else
				{
					// update window range settings.
					Config.WINDOW_RANGE.setXMin(chart.getChart().getXYPlot().getDomainAxis().getLowerBound());
					Config.WINDOW_RANGE.setXMax(chart.getChart().getXYPlot().getDomainAxis().getUpperBound());
					Config.WINDOW_RANGE.setYMin(chart.getChart().getXYPlot().getRangeAxis().getLowerBound());
					Config.WINDOW_RANGE.setYMax(chart.getChart().getXYPlot().getRangeAxis().getUpperBound());
					updateAxesScales();
				}				
			}
			
			public void mousePressed(MouseEvent e)
			{
				pressPos = e.getPoint();	// store for later
			}
			public void mouseClicked(MouseEvent e) {}
			public void mouseEntered(MouseEvent e) {}
			public void mouseExited(MouseEvent e) {}
		});
		panel.addKeyListener(this);
//		updateAxesScales();			// set up scales
		panel.setBorder(BorderFactory.createEmptyBorder(15,15,15,15));
		panel.setMinimumSize(new Dimension(250,250));
		return panel;
	}
	
	/**
	 * Creates an empty chart with the current configuration settings applied.
	 */
	private JFreeChart createEmptyChart()
	{
		JFreeChart c = ChartFactory.createXYLineChart("", "X", "f(x)", functionData,
				PlotOrientation.VERTICAL, false, true, false);
		
		// origin visible
		c.getXYPlot().setDomainZeroBaselineVisible(true);
		c.getXYPlot().setRangeZeroBaselineVisible(true);
		
		// set window range
		c.getXYPlot().getDomainAxis().setRange(Config.WINDOW_RANGE.getXRange());
		c.getXYPlot().getRangeAxis().setRange(Config.WINDOW_RANGE.getYRange());
		c.getXYPlot().getDomainAxis().setDefaultAutoRange(Config.WINDOW_RANGE.getXRange());
		c.getXYPlot().getRangeAxis().setDefaultAutoRange(Config.WINDOW_RANGE.getYRange());
		
		// other axes settings
		c.getXYPlot().getDomainAxis().setPositiveArrowVisible(true);
//		c.getXYPlot().setDomainCrosshairVisible(true);
//		c.getXYPlot().setDomainCrosshairLockedOnData(true);
		c.getXYPlot().getDomainAxis().setNegativeArrowVisible(true);
		c.getXYPlot().getRangeAxis().setPositiveArrowVisible(true);
//		c.getXYPlot().setRangeCrosshairVisible(true);
//		c.getXYPlot().setRangeCrosshairLockedOnData(true);
		c.getXYPlot().getRangeAxis().setNegativeArrowVisible(true);
		c.getXYPlot().getRangeAxis().setAutoRange(Config.AUTO_FIT_RANGE);
		
                c.getXYPlot().getDomainAxis().setTickLabelsVisible(false);
                c.getXYPlot().getRangeAxis().setTickLabelsVisible(false);
		c.getXYPlot().getRangeAxis().setVerticalTickLabels(false);
		
		return c;
	}
	
	/**
	 * Creates a empty function table component.
	 */
	private JPanel createTableComponent()
	{
		// set up table
		functionTable = new JTable(new AbstractTableModel()
		{
			public String getColumnName(int column)
			{
				if (column == ENABLED)
					return "Enabled";
				else if (column == NAME)
					return "Name";
				else if (column == FUNCTION)
					return "Function";
				return null;					
			}
			
			public Object getValueAt(int rowIndex, int columnIndex)
			{
				if (columnIndex == ENABLED)
					return enabled.get(rowIndex);		// Boolean class
				else if (columnIndex == NAME)
					return "f"+(rowIndex + 1) + "(x)";		// String class
				else if (columnIndex == FUNCTION)
					return functions.get(rowIndex).toExprString().copy();	// ExprString class
				return null;
			}
			
			public Class getColumnClass(int c)
			{
				return getValueAt(0, c).getClass();
			}
			 
			public boolean isCellEditable(int row, int col)
			{
				return col == FUNCTION || col == ENABLED;
			}
			
			public void setValueAt(Object obj, int row, int col)
			{
				// change enabled state
				if (col == ENABLED)
				{
					// update display for the target function
					Function f = functions.get(row);
					XYSeries series = getData(f);
//					int index = toDataIndex(row);
					// in set already: remove
					if (isPlotted(row))
						functionData.removeSeries(functionData.indexOf(row));
					else if (f.isValid()) // not in set: add
						functionData.addSeries(series);
					enabled.set(row, (Boolean)obj); // toggle
				}
				
				// change function expression (if possible)
				else if (col == FUNCTION)
				{
					try
					{
						Function f = new Function(Expression.read((ExprString)obj));

						// test with a dummy value of x
						if (!f.isValid())
							return;	// null OK when adding new
						
						// remove old, add this
						setFunction(row, f);
					}
					catch (Exception e)
					{
						JOptionPane.showMessageDialog(null, "Can't read function: " + obj.toString() + 
								". Reformat", "Bad expression", JOptionPane.ERROR_MESSAGE);
						e.printStackTrace();
						return;
					}
				}
			}
			
			public int getColumnCount()
			{
				return 3;
			}

			public int getRowCount()
			{
				return functions.size();
			}
		});
		functionTable.addKeyListener(this);
		functionTable.setDefaultEditor(ExprString.class, new ExprCellEdit());
//		functionTable.putClientProperty("autoStartsEdit", false);
		
		JScrollPane tablePane = new JScrollPane(functionTable);
		tablePane.setMinimumSize(new Dimension(250,100));
		
		// table column widths
		functionTable.getColumnModel().getColumn(ENABLED).setMaxWidth(65);
		functionTable.getColumnModel().getColumn(NAME).setMaxWidth(50);
		// function column takes up rest of space...
		functionTable.getColumnModel().getColumn(ENABLED).setResizable(false);
		functionTable.getColumnModel().getColumn(NAME).setResizable(false);
		functionTable.getColumnModel().getColumn(FUNCTION).setResizable(false);
		functionTable.getTableHeader().setReorderingAllowed(false);
		
		
		// full panel (with button bar)
		JPanel panel = new JPanel(new BorderLayout());
		panel.add(tablePane, BorderLayout.CENTER);
		panel.add(createButtonBar(), BorderLayout.SOUTH);
		
		return panel;
	}

	/**
	 * Returns a button bar with add new, remove, and plot buttons.
	 */
	private JPanel createButtonBar()
	{
		JPanel buttonBar = new JPanel(new GridLayout(1,4,5,5));
		JButton button = null;
		
		// "add new" button
		button = new JButton("New");
		button.addKeyListener(this);
		button.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				// add new empty function to list
				addFunction(new Function(null));
				
				// focus new function for editing
				functionTable.editCellAt(functions.size() - 1, 2);
				functionTable.grabFocus();
			}
		});
		buttonBar.add(button);
		
		// "remove" button
		button = new JButton("Remove");
		button.addKeyListener(this);
		button.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				// remove selected entries from history.
				int[] selected = functionTable.getSelectedRows();
				int removedCount = 0;
				for (int row : selected)
				{
					// if in dataset, remove it
					if (enabled.get(row - removedCount))
						functionData.removeSeries(getData(functions.get(row - removedCount)));
					functions.remove(row - removedCount);
					enabled.remove(row - removedCount);
					removedCount++;
				}
				functionTable.clearSelection();
				functionTable.updateUI();
			}
		});
		buttonBar.add(button);
		
		// "Zoom" combo
		button = new JButton("Zoom");
		button.addKeyListener(this);
		button.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if (zoomDialog == null)
					zoomDialog = new ZoomDialog((Frame)getTopLevelAncestor());
				zoomDialog.setVisible(true);
				// modal...after dialog closed, update chart range
				if (zoomDialog.wasOKClosed())
				{
					chart.getChart().getXYPlot().getDomainAxis().setRange(Config.WINDOW_RANGE.getXRange());
					chart.getChart().getXYPlot().getRangeAxis().setRange(Config.WINDOW_RANGE.getYRange());
					chart.getChart().getXYPlot().getRangeAxis().setAutoRange(Config.AUTO_FIT_RANGE);
					plot();	// update
					chart.getChart().getXYPlot().getDomainAxis().setDefaultAutoRange(Config.BASE_WINDOW_RANGE.getXRange());
					chart.getChart().getXYPlot().getRangeAxis().setDefaultAutoRange(Config.BASE_WINDOW_RANGE.getYRange());
					updateAxesScales();
				}
			}
		});
		buttonBar.add(button);
		
		// "plot" button
		button = new JButton("Plot");
		button.addKeyListener(this);
		button.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				plot();		
			}
		});
		buttonBar.add(button);
		
		return buttonBar;
	}
	
	private void updateAxesScales()
	{
		XYPlot plot = chart.getChart().getXYPlot();
		
		plot.getDomainAxis().setStandardTickUnits(new TickUnitSource()
		{
			public TickUnit getCeilingTickUnit(double size)
			{
				return new NumberTickUnit(Config.WINDOW_RANGE.xScl());
			}
			
			public TickUnit getCeilingTickUnit(TickUnit unit)
			{
				return new NumberTickUnit(Config.WINDOW_RANGE.xScl());
			}
			
			public TickUnit getLargerTickUnit(TickUnit unit)
			{
				return new NumberTickUnit(Config.WINDOW_RANGE.xScl());
			}
		});
		
		plot.getRangeAxis().setStandardTickUnits(new TickUnitSource()
		{
			public TickUnit getCeilingTickUnit(double size)
			{
				return new NumberTickUnit(Config.WINDOW_RANGE.yScl());
			}
			
			public TickUnit getCeilingTickUnit(TickUnit unit)
			{
				return new NumberTickUnit(Config.WINDOW_RANGE.yScl());
			}
			
			public TickUnit getLargerTickUnit(TickUnit unit)
			{
				return new NumberTickUnit(Config.WINDOW_RANGE.yScl());
			}
		});
		// clear axes markers
//		plot.clearDomainMarkers();
//		plot.clearRangeMarkers();
		
//		// domain
//		int count = (int)((Config.WINDOW_RANGE.xMax() - Config.WINDOW_RANGE.xMin())/Config.WINDOW_RANGE.xScl());
//		for (int i = 0; i < count; i++)
//		{
//			ValueMarker marker = new ValueMarker(Config.WINDOW_RANGE.xMin()+Config.WINDOW_RANGE.xScl()*i);
//			marker.setLabel(Double.toString(marker.getValue()));
//			plot.addDomainMarker(marker);
//		}
//		
//		// range
//		count = (int)((Config.WINDOW_RANGE.yMax() - Config.WINDOW_RANGE.yMin())/Config.WINDOW_RANGE.yScl());
//		for (int i = 0; i < count; i++)
//			plot.addRangeMarker(new ValueMarker(Config.WINDOW_RANGE.yMin()+Config.WINDOW_RANGE.yScl()*i));
	}
	
	/**
	 * Updates the text in the point display using the 
	 * specified mouse event's coordinates.
	 * If the draw rectangle flag is on, will 
	 * add markers at the current location.
	 */
	private void updatePointDisplay(MouseEvent e)
	{
		XYPlot plot = chart.getChart().getXYPlot();
		double x = e.getX();
		double y = e.getY();
		ValueAxis domain = plot.getDomainAxis();
		ValueAxis range = plot.getRangeAxis();
		Rectangle2D rect = chart.getScreenDataArea();
		RectangleEdge bottom = plot.getDomainAxisEdge();
		RectangleEdge left = plot.getRangeAxisEdge();
		
		NumberFormat nf = NumberFormat.getNumberInstance();
		nf.setMaximumFractionDigits(10);
		
		String xStr = nf.format(domain.java2DToValue(x, rect, bottom));
		String yStr = nf.format(range.java2DToValue(y, rect, left));
		
		// draw crosshair if flag is on.
		if (Config.CROSSHAIR_ON)
		{
//			plot.setDomainCrosshairValue(domain.java2DToValue(x, rect, bottom));
//			plot.setRangeCrosshairValue(range.java2DToValue(y, rect, left));
			// create markers
			Marker domainMarker = new ValueMarker(domain.java2DToValue(x, rect, bottom));
			Marker rangeMarker = new ValueMarker(range.java2DToValue(y, rect, left));
			
			// set labels
			domainMarker.setLabelAnchor(RectangleAnchor.BOTTOM);
			domainMarker.setLabel(xStr);
			rangeMarker.setLabelAnchor(RectangleAnchor.LEFT);
			rangeMarker.setLabel("                     " + yStr);	// add spaces so not cut off when drawing
			
			// remove old markers if existing
			if (oldDomainMarker != null)
				plot.removeDomainMarker(oldDomainMarker);
			if (oldRangeMarker != null)
				plot.removeRangeMarker(oldRangeMarker);
			
			// add markers
			plot.addDomainMarker(domainMarker);
			plot.addRangeMarker(rangeMarker);
			
			// store these markers as old ones.
			oldDomainMarker = domainMarker;
			oldRangeMarker = rangeMarker;
		}
	}
	
	private void plot()
	{
		// start from scratch
		functionData = new XYSeriesCollection();
		chart.getChart().getXYPlot().setDataset(functionData);
		
		// add only the enabled functions to the set
		for (int i = 0 ; i < functions.size(); i++)
		{
			if (enabled.get(i))
			{
				Function f = functions.get(i);
				if (f.isValid())
					functionData.addSeries(getData(f));
			}
		}
	}
	
	/**
	 * Returns whether the function at the specified 
	 * index is plotted on the graph.
	 */
	private boolean isPlotted(int index)
	{
		if (!enabled.get(index))
			return false;
		if (functionData.indexOf(index) == -1)
			return false;
		return true;
	}

	public void doAction()
	{
		if (!functionTable.isEditing())
			return;
		functionTable.editingStopped(null);
	}
	
	public void doUp()
	{
	}
	
	public void doDown()
	{
	}
	
	public void doLeft()
	{
	}
	
	public void doRight()
	{
	}
	
	public ExprLineEdit getEditor()
	{
		// if table not being edited, return null
		if (!functionTable.isEditing())
			return null;
		
		// if expression not being edited, return null
		Component editor = functionTable.getEditorComponent();
		if (!(editor instanceof ExprLineEdit))
			return null;

		return (ExprLineEdit)editor;
	}
	
	public String getTitle()
	{
		return "Graph";
	}
	
	public void keyReleased(KeyEvent e) {}
	public void keyTyped(KeyEvent e) {}
}
