package zcalc.gui;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

import utilities.Symbol;
import zcalc.math.ExprString;
import zcalc.math.Operator;

/**
 * Handles input from the keyboard and 
 * appends expression tokens to ExprStrings.
 */
public class InputHandler
{
	public static void handleInput(Input input, ExprLineEdit editor)
	{
		if (input.getAction() != null)
			input.doAction();
		else if (input.getKeyEvent() != null)
			handleKeyEvent(input.getKeyEvent(), editor);
	}
	
	/**
	 * Handles a key event as input, modifying the target
	 * ExprString. Returns the token appended to the 
	 * expression or "" if nothing was appended or something 
	 * was taken away.
	 */
	public static String handleKeyEvent(KeyEvent e, ExprLineEdit editor)
	{
		String toAdd = "";
		ExprString expr = editor.getExprString();
		boolean toUpdate = true;
		boolean override = true;
		
		// handle arrows
		if (e.getKeyCode() == KeyEvent.VK_LEFT)
		{
			if (e.isShiftDown())
			{
				expr.shiftTokenIndexLeft(1); // tokenIndex--
				editor.moveCaretPosition(expr.toString(expr.getTokenIndex()).length());
				toUpdate = false;
			}
			else
			{
				expr.shiftTokenIndexLeft(1);
			}
			override = false;
		}
		else if (e.getKeyCode() == KeyEvent.VK_RIGHT)
		{
			if (e.isShiftDown())
			{
				expr.shiftTokenIndexRight(1); // tokenIndex++
				editor.moveCaretPosition(expr.toString(expr.getTokenIndex()).length());
				toUpdate = false;
			}
			else
			{
				expr.shiftTokenIndexRight(1);
			}
			override = false;
		}

		// handle backspace
		else if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE)
		{
			// remove previous element in ExprString only if no text selected
			if (editor.getSelectedText() == null && expr.getTokenIndex() > 0)
				expr.remove(expr.getTokenIndex() - 1);
		}

		// handle delete
		else if (e.getKeyCode() == KeyEvent.VK_DELETE)
		{
			// remove next element in ExprString.
			if (expr.getTokenIndex() < expr.size())
				expr.remove(expr.getTokenIndex());
		}

		// handle home
		else if (e.getKeyCode() == KeyEvent.VK_HOME)
		{
			expr.setTokenIndex(0);
		}

		// handle end
		else if (e.getKeyCode() == KeyEvent.VK_END)
		{
			expr.setTokenIndex(expr.size());
		}

		else if (e.getKeyCode() == KeyEvent.VK_TAB) // last answer
		{
			if (e.isControlDown())
				toAdd = Config.LAST_ANSWER().getSymbol();
		}
		
		// alt modifier
		else if (e.isAltDown())
		{
			if (e.getKeyCode() == KeyEvent.VK_A)	// absolute value
			{
				toAdd = Operator.ABS.symbol();
			}
			else if (e.getKeyCode() == KeyEvent.VK_C)
			{
				if (e.isShiftDown())	// arccos
					toAdd = Operator.ARCCOS.symbol();
				else	// cosine
					toAdd =  Operator.COS.symbol();
			}

			else if (e.getKeyCode() == KeyEvent.VK_E)
			{
				if (e.isShiftDown())	// scientific notation
					toAdd = Operator.SCI_NOT.symbol();
				else	// e
					toAdd = Symbol.E.symbol();
			}

			else if (e.getKeyCode() == KeyEvent.VK_I)
			{
				if (e.isShiftDown()) // infinity
					toAdd = Symbol.INFINITY.symbol();
			}

			else if (e.getKeyCode() == KeyEvent.VK_L)	// log
				toAdd = Operator.LOG.symbol();

			else if (e.getKeyCode() == KeyEvent.VK_N)	// ln
				toAdd =  Operator.LN.symbol();

			else if (e.getKeyCode() == KeyEvent.VK_P)	// pi
				toAdd = Symbol.PI.symbol();

			else if (e.getKeyCode() == KeyEvent.VK_R)
			{
				if (e.isShiftDown())	// root
					toAdd = Operator.ROOT.symbol();
				else	// square root
					toAdd =  Operator.SQRT.symbol();
			}			

			else if (e.getKeyCode() == KeyEvent.VK_S)
			{
				if (e.isShiftDown())	// arcsin
					toAdd = Operator.ARCSIN.symbol();
				else	// sine
					toAdd =  Operator.SIN.symbol();
			}

			else if (e.getKeyCode() == KeyEvent.VK_T)
			{
				if (e.isShiftDown())	// arctan
					toAdd = Operator.ARCTAN.symbol();
				else	// tangent
					toAdd =  Operator.TAN.symbol();
			}
			else
			{
				override = false;
				toUpdate = false;
			}
		}
		
		// control modifier
		else if (e.isControlDown())
		{
			if (e.getKeyCode() == KeyEvent.VK_A) // select all
			{
				editor.selectAll();
				//			editor.setCaretPosition(0);	// caret to start
				expr.shiftTokenIndexRight(expr.size()); // tokenIndex to end 
				//			editor.moveCaretPosition(expr.toString(expr.getTokenIndex()).length());	// caret to end
				toUpdate = false;
				override = false;
			}
			else
			{
				override = false;
				toUpdate = false;
			}
		}

		// most cases: append what was typed
		else
		{
			// don't add unknown key characters (ex. shift)
			if (!isValidKey(e))
				return "";
//			// no modifiers for letters
//			if (!e.isShiftDown() && !e.isControlDown() && !e.isAltDown() && Character.isLetter(e.getKeyChar()))
//				return "";

			toAdd = Character.toString(e.getKeyChar()).toUpperCase();
		}
		
		if (!toAdd.isEmpty())
			expr.add(expr.getTokenIndex(), toAdd);
		
		// handle replacing of selected text (if applicable)
		if (editor.getSelectedText() != null && override)
		{
			// replace old with new
			int start = editor.getSelectionStart();
			int end = editor.getSelectionEnd();
			ArrayList<Integer> toRemove = new ArrayList<Integer>();
			for (int i = 1; i <= expr.size(); i++)
			{
				int length = expr.toString(i).length();
				if (start < length && length <= end)
					toRemove.add(i-1);	// remove one at index before
			}

			// remove from expr variable
			// offset from newly typed character
			int offset = (toAdd.length() == 0) ? 0 : 1;
			for (int i = 0; i < toRemove.size(); i++)
			{
				expr.remove(toRemove.get(i)+offset);
				offset--;
			}
		}
		
		// update display (if applicable)
		if (toUpdate)
			editor.updateDisplay();
		
		return toAdd;
	}
	
	/**
	 * Returns whether or not the specified key event is valid as input
	 * that shows up by default.
	 */
	public static boolean isValidKey(KeyEvent e)
	{
		char c = e.getKeyChar();
		
		if (e.isAltDown())
			return false;
		if (e.isControlDown())
			return false;
		return isValidSymbol(c);
	}
	
	/**
	 * Returns whether or not the specified symbol is valid as input
	 * that shows up by default.
	 */
	public static boolean isValidSymbol(char c)
	{
		if (Operator.isOperator(Character.toString(c)))
			return true;
		if (Character.isLetterOrDigit(c))
			return true;
		if (c == '(' || c == ')')
			return true;
		if (c == '-')
			return true;
		if (c == '.')
			return true;
		return false;
	}

}
