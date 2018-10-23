package zcalc.gui;

import java.awt.Component;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.util.EventObject;

import javax.swing.AbstractCellEditor;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

import zcalc.math.ExprString;

/**
 * A cell editor version of ExprLineEdit.
 */
public class ExprCellEdit extends AbstractCellEditor implements TableCellEditor, KeyListener
{
	ExprLineEdit editor;
	
	public ExprCellEdit()
	{
		super();
		editor = new ExprLineEdit();
		editor.setFocusable(true);
		editor.addKeyListener(this);
	}
	
	public Component getTableCellEditorComponent(JTable table, Object value,
			boolean isSelected, int row, int column)
	{
		editor.setExprStr((ExprString)value);
		return editor;
	}

	public Object getCellEditorValue()
	{
		return editor.getExpression().getOriginal();
	}
	
	/**
	 * Handles when the any key is pressed.
	 */
	public void keyPressed(KeyEvent e)
	{
		// enter key --> done editing
		if (e.getKeyCode() == KeyEvent.VK_ENTER)
			fireEditingStopped();
	}
	
	/**
	 * Returns whether or not the cell should be made 
	 * editable by the specified event.
	 */
	public boolean isCellEditable(EventObject e)
	{
		if (e instanceof MouseEvent)
			return (((MouseEvent)e).getClickCount() > 1);
		else
			return true;
	}
	
	/**Required by KeyListener.**/
	public void keyTyped(KeyEvent e) {}
	public void keyReleased(KeyEvent e) {}	
}
