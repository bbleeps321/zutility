package zcalc.gui;

import java.awt.BorderLayout;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import zcalc.math.ExprString;
import zcalc.math.Expression;
import zcalc.math.Variable;

/**
 * Table for displaying and editing variable values.
 */
public class VariableEditPanel extends JPanel
{
	public static final int VARIABLE = 0;
	public static final int VALUE = 1;
	
	private JTable variableTable;
	
	public VariableEditPanel()
	{
		super(new BorderLayout());
		
		// table
		variableTable = new JTable(new AbstractTableModel()
		{
			public String getColumnName(int column)
			{
				if (column == VARIABLE)
					return "Variable";
				else if (column == VALUE)
					return "Value";
				return null;					
			}

			public Object getValueAt(int row, int col)
			{
				if (col == VARIABLE)
					return Variable.getVar(row).getSymbol();
				else if (col == VALUE)
					return Variable.getVar(row).evaluate().toExprString();	// class ExprString
				return null;
			}
			
			public Class getColumnClass(int c)
			{
				return getValueAt(0, c).getClass();
			}
			
			public boolean isCellEditable(int row, int col)
			{
				return col == VALUE;	// only value column is editable
			}
			
			public void setValueAt(Object obj, int row, int col)
			{
				// change expression and recalculate
				// get expression (if possible) from input
				Expression expr = null;
				try
				{
					expr = Expression.read((ExprString)obj);
					if (expr == null)
						return;
				}
				catch (Exception e)
				{
					JOptionPane.showMessageDialog(null, "Can't read expression: " + obj.toString() + 
							". Reformat", "Bad expression", JOptionPane.ERROR_MESSAGE);
					e.printStackTrace();
					return;
				}
				
				if (expr == null)
					System.err.println("Problem reading expression!");
				
				// set variable to value of expression
				Variable.getVar(row).setValue(expr.evaluate().doubleValue());
			}
			
			public int getColumnCount()
			{
				return 2;
			}

			public int getRowCount()
			{
				return Variable.variableCount();
			}
		});
		variableTable.putClientProperty("terminateEditOnFocusLost", true);
		variableTable.getColumnModel().getColumn(VARIABLE).setResizable(false);
		variableTable.getColumnModel().getColumn(VALUE).setResizable(false);
		variableTable.getTableHeader().setReorderingAllowed(false);
		variableTable.setDefaultEditor(ExprString.class, new ExprCellEdit());
		JScrollPane tablePane = new JScrollPane(variableTable);
		
		// add to content pane
		add(tablePane, BorderLayout.CENTER);
	}
	
	public JTable getTable()
	{
		return variableTable;
	}
}
