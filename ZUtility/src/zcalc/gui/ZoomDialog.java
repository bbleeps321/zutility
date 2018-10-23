package zcalc.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.NumberFormat;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 * Dialog for specifying the window range of the chart.
 * The dialog is modal.
 */
public class ZoomDialog extends JDialog
{
	// Constants
	private static final int ZOOM_DIALOG_W = 250;	// Width of zoom dialog.
	private static final int ZOOM_DIALOG_H = 215;	// Height of zoom dialog.
	private static final int GAP = 2;
	
//	private JFreeChart chart;	// chart being modified.
	private WindowRange windowRange;
	private WindowRange baseWindowRange;
	private JFormattedTextField xMinIn, xMaxIn, xSclIn;	// input for x axis range.
	private JFormattedTextField yMinIn, yMaxIn, ySclIn;	// input for y axis range.
	private JCheckBox fitRangeCheck;
	private JComboBox baseZoomCombo;
	
	private boolean updating;	// if currently updating
	private boolean okClosed;
	
	/**
	 * Creates a new modal dialog with the specified 
	 * chart to modify. The dialog is hidden by default.
	 */
	public ZoomDialog(Frame parent)
	{
		super(parent, "Zoom Chart", true);	// modal
		
		updating = false;
		windowRange = Config.WINDOW_RANGE.modifiableCopy();
		baseWindowRange = Config.BASE_WINDOW_RANGE;
		
		// set up content pane
		JPanel contentPane = new JPanel(new BorderLayout());
		contentPane.add(createInputPanel(), BorderLayout.CENTER);	// inputs at top
		contentPane.add(createButtonPanel(), BorderLayout.SOUTH);	// buttons at bottom
		contentPane.setBorder(BorderFactory.createEmptyBorder(15,15,15,15));
		
		// personal settings
		setContentPane(contentPane);
		setSize(ZOOM_DIALOG_W, ZOOM_DIALOG_H);
		setVisible(false);	// hidden by default;
	}
	
	/**
	 * Displays the dialog with the current chart window 
	 * range values from the specified chart.
	 */
	public void setVisible(boolean visible)
	{
		if (visible)
		{
			getWindowRange();
			updateInputText();
			updateCombo();
			setLocationRelativeTo(this.getParent());	// centered over parent component
			okClosed = false;	// reset
		}
		super.setVisible(visible);
	}
	
	/**
	 * Generates the panel containing the inputs and labels.
	 * Buttons are placed in a 3x3 grid.
	 */
	private JPanel createInputPanel()
	{
		JPanel inputPanel = new JPanel(new GridLayout(1,3,GAP,GAP));
		NumberFormat format = NumberFormat.getNumberInstance();
		
		JPanel labels = new JPanel(new GridLayout(3,1, GAP, GAP));
		labels.add(new JLabel(""));
		labels.add(new JLabel("X Axis"));
		labels.add(new JLabel("Y Axis"));
		
		// MIN
		JPanel minPanel = new JPanel(new GridLayout(3,1, GAP, GAP));
		minPanel.add(new JLabel("Min", SwingConstants.CENTER));
		// xMin
		xMinIn = new JFormattedTextField(format);
		xMinIn.addPropertyChangeListener(new PropertyChangeListener()
		{
			public void propertyChange(PropertyChangeEvent e)
			{
				windowRange.setXMin(((Number)((JFormattedTextField)e.getSource()).getValue()).doubleValue());
			}
		});
		minPanel.add(xMinIn);
		// yMin
		yMinIn = new JFormattedTextField(format);
		yMinIn.addPropertyChangeListener(new PropertyChangeListener()
		{
			public void propertyChange(PropertyChangeEvent e)
			{
				windowRange.setYMin(((Number)((JFormattedTextField)e.getSource()).getValue()).
						doubleValue());
			}
		});
		minPanel.add(yMinIn);
		
		// MAX
		JPanel maxPanel = new JPanel(new GridLayout(3,1, GAP, GAP));
		maxPanel.add(new JLabel("Max", SwingConstants.CENTER));
		// xMax
		xMaxIn = new JFormattedTextField(format);
		xMaxIn.addPropertyChangeListener(new PropertyChangeListener()
		{
			public void propertyChange(PropertyChangeEvent e)
			{
				windowRange.setXMax(((Number)((JFormattedTextField)e.getSource()).getValue()).doubleValue());
			}
		});
		maxPanel.add(xMaxIn);
		// yMax
		yMaxIn = new JFormattedTextField(format);
		yMaxIn.addPropertyChangeListener(new PropertyChangeListener()
		{
			public void propertyChange(PropertyChangeEvent e)
			{
				windowRange.setYMax(((Number)((JFormattedTextField)e.getSource()).getValue()).
						doubleValue());
			}
		});
		maxPanel.add(yMaxIn);
		
		// SCALE
		JPanel sclPanel = new JPanel(new GridLayout(3,1, GAP, GAP));
		sclPanel.add(new JLabel("Scale"));
		// xScl
		xSclIn = new JFormattedTextField(format);
		xSclIn.addPropertyChangeListener(new PropertyChangeListener()
		{
			public void propertyChange(PropertyChangeEvent e)
			{
				windowRange.setXScl(((Number)((JFormattedTextField)e.getSource()).getValue()).doubleValue());
			}
		});
		sclPanel.add(xSclIn);
		// yScl
		ySclIn = new JFormattedTextField(format);
		ySclIn.addPropertyChangeListener(new PropertyChangeListener()
		{
			public void propertyChange(PropertyChangeEvent e)
			{
				windowRange.setYScl(((Number)((JFormattedTextField)e.getSource()).getValue()).
						doubleValue());
			}
		});
		sclPanel.add(ySclIn);
		
		JPanel otherPanel = new JPanel(new GridLayout(2,1, GAP, GAP));
		// fit-to-range checkbox
		fitRangeCheck = new JCheckBox("Fit range to data");
		fitRangeCheck.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				boolean selected = ((JCheckBox)e.getSource()).isSelected();
				yMaxIn.setEnabled(!selected);
				yMinIn.setEnabled(!selected);
			}
		});
		otherPanel.add(fitRangeCheck);
		
		// base zoom combobox
		baseZoomCombo = new JComboBox();
		baseZoomCombo.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if (updating)
					return;
				baseWindowRange = (WindowRange)((JComboBox)e.getSource()).getSelectedItem();
				windowRange = baseWindowRange.modifiableCopy();
				updateInputText();
			}
		});
		otherPanel.add(baseZoomCombo);
		
		// add to panel
		inputPanel.add(minPanel);
		inputPanel.add(maxPanel);
		inputPanel.add(sclPanel);
		
		JPanel panel = new JPanel(new BorderLayout(GAP, GAP));
		panel.add(labels, BorderLayout.WEST);
		panel.add(inputPanel, BorderLayout.CENTER);
		panel.add(otherPanel, BorderLayout.SOUTH);
		return panel;
	}
	
	/**
	 * Generates the panel containing the cancel and ok buttons.
	 * Buttons are aligned right with a flow layout.
	 */
	private JPanel createButtonPanel()
	{
		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, GAP, GAP));
		
		// reset button
		JButton button = new JButton("Reset");
		button.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				windowRange = baseWindowRange.modifiableCopy();
				
				updateInputText();
			}
		});
		buttonPanel.add(button);
		
		// cancel button
		button = new JButton("Cancel");
		button.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				setVisible(false);
				okClosed = false;
			}
		});
		buttonPanel.add(button);
		
		// ok button
		button = new JButton("OK");
		button.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				applyChanges();
				setVisible(false);
				okClosed = true;
			}
		});
		buttonPanel.add(button);
		this.getRootPane().setDefaultButton(button);
		
		return buttonPanel;
	}
	
	private void getWindowRange()
	{
		// get current window range.
		windowRange = Config.WINDOW_RANGE.modifiableCopy();
		baseWindowRange = Config.BASE_WINDOW_RANGE;
	}
	
	/**
	 * Updates the text in the input text fields 
	 * to match the current window range values.
	 */
	private void updateInputText()
	{
		updating = true;
		
		xMinIn.setValue(windowRange.xMin());
		xMaxIn.setValue(windowRange.xMax());
		xSclIn.setValue(windowRange.xScl());
		yMinIn.setValue(windowRange.yMin());
		yMaxIn.setValue(windowRange.yMax());
		ySclIn.setValue(windowRange.yScl());
		fitRangeCheck.setSelected(Config.AUTO_FIT_RANGE);

		updating = false;
	}
	
	private void updateCombo()
	{
		updating = true;
		
		// update contents of combo based on mode
		baseZoomCombo.removeAllItems();
		baseZoomCombo.addItem(WindowRange.STANDARD);
		if (Config.IN_RADIANS)
			baseZoomCombo.addItem(WindowRange.TRIG_RAD);
		else
			baseZoomCombo.addItem(WindowRange.TRIG_DEG);
//		baseZoomCombo.addItem(WindowRange.FIT);
		baseZoomCombo.setSelectedItem(Config.BASE_WINDOW_RANGE);
		
		updating = false;
	}
	
	
	/**
	 * Applies the stored window ranges to the chart.
	 */
	private void applyChanges()
	{		
		Config.WINDOW_RANGE = windowRange;
		Config.BASE_WINDOW_RANGE = baseWindowRange;
		Config.AUTO_FIT_RANGE = fitRangeCheck.isSelected();
	}
	
	public boolean wasOKClosed()
	{
		return okClosed;
	}
}
