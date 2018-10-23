package zcalc.gui;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.text.Keymap;

import zcalc.math.ExprString;
import zcalc.math.Expression;
import zcalc.math.MathException;

/**
 * Handles input and editing of expressions.
 */
public class ExprLineEdit extends JTextField implements KeyListener
{
	private ExprString expr;	// current content

	
	public ExprLineEdit()
	{
		super();
		expr = new ExprString();
		
		// set up keymap
		setupKeyMap();
		
		// set up caret to always show regardless of focus
		getCaret().setBlinkRate(0);
		getCaret().setVisible(true);
		
		// add listeners
		this.addKeyListener(this);
		this.addMouseListener(new MouseListener()
		{
			public void mousePressed(MouseEvent e)
			{
				updateDisplay();
			}
			
			public void mouseClicked(MouseEvent e) {}
			public void mouseReleased(MouseEvent e) {}
			public void mouseExited(MouseEvent e) {}
			public void mouseEntered(MouseEvent e) {}
		});
	}
	
	public void keyPressed(KeyEvent e)
	{
		InputHandler.handleKeyEvent(e, this);	// updates display as needed
		e.consume();
	}
	
	public Expression getExpression()
	{
		try
		{
			return Expression.read(expr);
		}
		catch (MathException e)
		{
			System.err.println("Problem reading expression!");
			JOptionPane.showMessageDialog(this, "Can't read expression: " + getText() + 
					". Reformat!", "Bad expression", JOptionPane.ERROR_MESSAGE);
			return null;
		}
	}
	
	public void setExprStr(ExprString expr)
	{
		expr.setTokenIndex(expr.size());
		this.expr = expr;
		updateDisplay();
	}
	
	/**
	 * Clears the contents of the editor.
	 */
	public void clear()
	{
		expr = new ExprString();
		updateDisplay();
	}
	
	public ExprString getExprString()
	{
		return expr;
	}
	
	public void setText(String s)
	{
		// do nothing
	}
	
	public void updateDisplay()
	{
		super.setText(expr.toString());
		setCaretPosition(expr.toString(expr.getTokenIndex()).length());
		
//		System.out.println(expr.toStringDebug());
//		System.out.println(expr.getTokenIndex());
	}
	
	private void setupKeyMap()
	{
		Keymap map = getKeymap();
		
		// remove all lowercase letters
		for (char c = 'a'; c < 'z'; c++)
			map.removeKeyStrokeBinding(KeyStroke.getKeyStroke(c));
		
		map.setResolveParent(null);
	}
	
	public void append(String s)
	{
		expr.add(s);
		updateDisplay();
	}
	
	public void append(ExprString expStr)
	{
		for (String s : expStr)
			expr.add(s);
		updateDisplay();
	}
	
	public void keyReleased(KeyEvent e) {}
	public void keyTyped(KeyEvent e) {}
}
