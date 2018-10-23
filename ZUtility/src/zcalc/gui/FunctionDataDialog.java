package zcalc.gui;

import java.awt.Frame;

import javax.swing.JDialog;
import javax.swing.JTable;

public class FunctionDataDialog extends JDialog
{
	private JTable dataTable;
	
	public FunctionDataDialog(ChartComponent parent)
	{
		super((Frame)parent.getTopLevelAncestor(), "Function Data Table", false);	// non-modal
		
//		dataTable = new JTable(new AbstractTableModel()
//		{
//			public String getColumnName(int column)
//			{
//				if (column == ENABLED)
//					return "Enabled";
//				else if (column == NAME)
//					return "Name";
//				else if (column == FUNCTION)
//					return "Function";
//				return null;					
//			}
//			
//			public Object getValueAt(int rowIndex, int columnIndex)
//			{
//				if (columnIndex == ENABLED)
//					return enabled.get(rowIndex);		// Boolean class
//				else if (columnIndex == NAME)
//					return "f"+(rowIndex + 1) + "(x)";		// String class
//				else if (columnIndex == FUNCTION)
//					return functions.get(rowIndex).toExprString().copy();	// ExprString class
//				return null;
//			}
//			
//			public Class getColumnClass(int c)
//			{
//				return getValueAt(0, c).getClass();
//			}
//			 
//			public boolean isCellEditable(int row, int col)
//			{
//				return col == FUNCTION || col == ENABLED;
//			}
//			
//			public void setValueAt(Object obj, int row, int col)
//			{
//				// change enabled state
//				if (col == ENABLED)
//				{
//					enabled.set(row, (Boolean)obj); // toggle
//					
//					// update display for the target function
//					Function f = functions.get(row);
//					String key = getKey(row, f);
//					// in set already: remove
//					if (dataset.indexOf(key) != -1)
//						dataset.removeSeries(key);
//					// not in set: add
//					else
//					{
//						if (f.isValid())
//						{
//							double[][] data = getData(f);
//							if (data == null)
//								return;
//							dataset.addSeries(key, data);
//						}
//					}
//				}
//				
//				// change function expression (if possible)
//				else if (col == FUNCTION)
//				{
//					try
//					{
//						Function f = new Function(Expression.read((ExprString)obj));
//						
//						// test with a dummy value of x
//						if (!f.isValid())
//							return;	// null OK when adding new
//						
//						// remove old, add this
//						setFunction(row, f);
//					}
//					catch (Exception e)
//					{
//						JOptionPane.showMessageDialog(null, "Can't read function: " + obj.toString() + 
//								". Reformat", "Bad expression", JOptionPane.ERROR_MESSAGE);
//						e.printStackTrace();
//						return;
//					}
//				}
//			}
//			
//			public int getColumnCount()
//			{
//				return 3;
//			}
//
//			public int getRowCount()
//			{
//				return functions.size();
//			}
//		});
	}
}
