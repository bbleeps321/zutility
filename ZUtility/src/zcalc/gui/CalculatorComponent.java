package zcalc.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.table.AbstractTableModel;

import zcalc.math.Constant;
import zcalc.math.ExprString;
import zcalc.math.Expression;

/**
 * Controls user input boxes and record of expressions boxes.
 */
public class CalculatorComponent extends OutputComponent implements KeyListener
{
	private static final int EXPRESSION = 0;
	private static final int VALUE = 1;
	
	private JTable recordTable;
	private ExprLineEdit inputEditor;
	
	// the following 2 lists should be synchronized in size and values
	// (exprHistory[0].evaluate() = valHistory[0])
	private ArrayList<ExprString> exprHistory;
	private ArrayList<Constant> valHistory;
	private int exprIndex;
	
	public CalculatorComponent()
	{
		super(new BorderLayout());
		
		exprHistory = new ArrayList<ExprString>();
		valHistory = new ArrayList<Constant>();
		exprIndex = 0;
		
		// set up record component
		recordTable = createTable();

		JScrollPane recordPane = new JScrollPane(recordTable);
		
		// set up panel with record and buttons
		JPanel top = new JPanel(new BorderLayout());
		top.add(recordPane, BorderLayout.CENTER);
		top.add(createButtonBar(), BorderLayout.SOUTH);
		
		// set up input component
		inputEditor = new ExprLineEdit();
		inputEditor.addKeyListener(this);
		
		// place components
		add(top, BorderLayout.CENTER);
		add(inputEditor, BorderLayout.SOUTH);
		
		setMinimumSize(new Dimension(300,250));
	}
	
	/**
	 * Evaluates the current expression in the input component.
	 * If the expression is valid, evaluates it, copies it and
	 * the result to the record box, and clears the input component.
	 * If not valid, displays an error message to the user.
	 */
	public void evaluateInput()
	{
		// get expression (if possible) from input
		Expression expr = inputEditor.getExpression();	// creates message if bad expression
		if (expr == null)
			return;
		
		Constant val = expr.evaluate();
		Config.LAST_ANSWER().setValue(val);
		// append expression to record and display result there
		exprHistory.add(expr.getOriginal());
		valHistory.add(expr.evaluate());
		exprIndex = exprHistory.size();
		recordTable.updateUI();
		
		// clear input
		inputEditor.clear();
	}
	
	/**
	 * Handles when the any key is pressed.
	 */
	public void keyPressed(KeyEvent e)
	{
		// enter key
		if (e.getKeyCode() == KeyEvent.VK_ENTER)
			evaluateInput();
		// up key
		else if (e.getKeyCode() == KeyEvent.VK_UP)
			loadPreviousEntry();
		// down key
		else if (e.getKeyCode() == KeyEvent.VK_DOWN)
			loadNextEntry();
		// handle other keys if not already handled by inputEditor
		else if (e.getSource() != inputEditor)
			inputEditor.keyPressed(e);
	}
	
	/**
	 * Returns a button bar with remove, clear buttons.
	 */
	private JPanel createButtonBar()
	{
		JPanel buttonBar = new JPanel(new GridLayout(2,3,2,2));
		JButton button = null;
		
		// "clear all" button
		button = new JButton("Clear All");
		button.addKeyListener(this);
		button.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				// remove all entries from history.
				exprHistory.clear();
				valHistory.clear();
				recordTable.updateUI();
				
				// remove input text
				inputEditor.clear();
			}
		});
		buttonBar.add(button);
		
		// "remove" button
		button = new JButton("Remove Row");
		button.addKeyListener(this);
		button.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				// remove selected entries from history.
				int[] selected = recordTable.getSelectedRows();
				int removedCount = 0;
				for (int row : selected)
				{
					exprHistory.remove(row - removedCount);
					valHistory.remove(row - removedCount);
					removedCount++;
				}
				recordTable.clearSelection();
				recordTable.updateUI();
			}
		});
		buttonBar.add(button);
		
		// "previous entry" button
		button = new JButton("Previous");
		button.addKeyListener(this);
		button.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				loadPreviousEntry();
			}
		});
		buttonBar.add(button);
		
		// "clear input" button
		button = new JButton("Clear");
		button.addKeyListener(this);
		button.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				// remove input text
				inputEditor.clear();
			}
		});
		buttonBar.add(button);		
		
		// "last" button
		button = new JButton("Last Answer");
		button.addKeyListener(this);
		button.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				// append symbol for last answer to input
				inputEditor.append(Config.LAST_ANSWER().evaluate().toString());
			}
		});
		buttonBar.add(button);
		
		// "previous entry" button
		button = new JButton("Next");
		button.addKeyListener(this);
		button.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				loadNextEntry();
			}
		});
		buttonBar.add(button);
		
		return buttonBar;
	}
	
	private JTable createTable()
	{
		JTable table = new JTable(new AbstractTableModel()
		{
			public String getColumnName(int column)
			{
				if (column == EXPRESSION)
					return "Expression";
				else if (column == VALUE)
					return "Value";
				return null;					
			}

			public Object getValueAt(int rowIndex, int columnIndex)
			{
				if (columnIndex == EXPRESSION)
					return exprHistory.get(rowIndex);
				else if (columnIndex == VALUE)
					return valHistory.get(rowIndex);
				return null;
			}
			
			public boolean isCellEditable(int row, int col)
			{
				return false;
			}
			
			public void setValueAt(Object obj, int row, int col)
			{
				// nothing editable
			}
			
			public int getColumnCount()
			{
				return 2;
			}

			public int getRowCount()
			{
				return exprHistory.size();
			}
		});
//		table.addKeyListener(this);
//		table.getInputMap().remove(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0));
		table.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), new AbstractAction()
		{
			public void actionPerformed(ActionEvent e)
			{
				System.out.println("Action performed!");
				int row = recordTable.getSelectedRow();
				int col = recordTable.getSelectedColumn();
				
				// find contents of selected cell
				ExprString selectedItem = null;
				if (col == EXPRESSION)
					selectedItem = exprHistory.get(row);
				else if (col == VALUE)
					selectedItem = valHistory.get(row).toExprString();
				else
					return;
				
				// append to line edit
				inputEditor.append(selectedItem);
			}
		});	
		
		table.getColumnModel().getColumn(EXPRESSION).setResizable(false);
		table.getColumnModel().getColumn(VALUE).setResizable(false);
		table.getTableHeader().setReorderingAllowed(false);
		table.putClientProperty("terminateEditOnFocusLost", true);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setRowSelectionAllowed(true);
		table.setColumnSelectionAllowed(true);
		table.addMouseListener(new MouseAdapter()
		{
			public void mouseClicked(MouseEvent e)
			{
				// do nothing if not a double click
				if (e.getClickCount() != 2)
					return;
				
				// find row/column at location
				int row = recordTable.rowAtPoint(e.getPoint());
				int col = recordTable.columnAtPoint(e.getPoint());
				
				// find contents of selected cell
				ExprString selectedItem = null;
				if (col == EXPRESSION)
					selectedItem = exprHistory.get(row);
				else if (col == VALUE)
					selectedItem = valHistory.get(row).toExprString();
				else
					return;
				
				// append to line edit
				inputEditor.append(selectedItem);
			}
		});

		
		return table;
	}

	public void loadPreviousEntry()
	{
		if (exprIndex - 1 < 0)
			return;
		inputEditor.setExprStr(exprHistory.get(exprIndex - 1).copy());
		exprIndex--;
	}
	
	public void loadNextEntry()
	{
		if (exprIndex + 1 > exprHistory.size())
			return;
		
		if (exprIndex + 1 == exprHistory.size())
			inputEditor.setExprStr(new ExprString());	// clear
		else
			inputEditor.setExprStr(exprHistory.get(exprIndex + 1).copy());
		
		exprIndex++;
	}
	
	public void doAction()
	{
		evaluateInput();
	}
	
	public void doUp()
	{
		// move cursor to start
		inputEditor.getExprString().setTokenIndex(0);
		inputEditor.updateDisplay();
	}
	
	public void doDown()
	{
		// move cursor to end
		int endIndex = inputEditor.getExprString().size();
		inputEditor.getExprString().setTokenIndex(endIndex);
		inputEditor.updateDisplay();
	}
	
	public void doLeft()
	{
		// move token left one
		inputEditor.getExprString().shiftTokenIndexLeft(1);
		inputEditor.updateDisplay();
	}
	
	public void doRight()
	{
		// move token left one
		inputEditor.getExprString().shiftTokenIndexRight(1);
		inputEditor.updateDisplay();
	}
	
	public ExprLineEdit getEditor()
	{
		return inputEditor;
	}
	
	public String getTitle()
	{
		return "Home";
	}
	
	/**Required by KeyListener.**/
	public void keyTyped(KeyEvent e) {}
	public void keyReleased(KeyEvent e) {}
}
