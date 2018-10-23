package zcalc.math;

import java.util.ArrayList;
import java.util.Stack;

import utilities.Symbol;
import utilities.ZMath;

/**
 * ArrayList of strings, each representing a number, operator,
 * variable, etc.
 */
public class ExprString extends ArrayList<String>
{
	private int tokenIndex;		// index of currently preceded token in expression.
								// ranges from 0 - size()
	
	public ExprString()
	{
		super();
		tokenIndex = 0;
	}
	
	/**
	 * Converts the stored string into an expression.
	 */
	public Expression toExpr()
	{
		return null;
	}
	
	/**
	 * Returns a copy of the string.
	 */
	public ExprString copy()
	{
		ExprString e = new ExprString();
		for (String s : this)
			e.add(s);
		e.setTokenIndex(tokenIndex);
		
		return e;
	}

	/**
	 * Adds the specified String to the list.
	 */
	public boolean add(String s)
	{
		s = s.trim();

		tokenIndex++;
//		System.out.println("adding: " + s);
		return super.add(s);
	}
	
	/**
	 * Adds the specified String to the list at the target index
	 */
	public void add(int index, String s)
	{
		s = s.trim();

		if (index >= tokenIndex)
			tokenIndex++;
//		System.out.println("adding: " + s);
		super.add(index, s);
	}
	
	public String remove(int index)
	{
//		System.out.println("removing: " + get(index) + " from " + index);
		if (index < tokenIndex)
			shiftTokenIndexLeft(1);
		return super.remove(index);
	}
	
	public void add(ArrayList<String> list)
	{
		for (String s : list)
			add(s);
	}
	
	/**
	 * Prepares an infix expression for conversion to postfix.
	 * eg. scientific notation, constants, etc.
	 */
	public ExprString prepare()
	{
		ExprString expr = copy();
		
		for (int i = 0; i < expr.size(); i++) // for each token
		{
			String token = expr.get(i);
			
			// all variables: insert implied "*"
			if (Variable.isVariable(token))
			{
				// preceded by number
				if (i > 0 && ZMath.isNumber(expr.get(i-1)))
				{
					expr.add(i, Operator.MULTIPLY.toString());
					i++;
				}

				// followed by number
				if (i + 1 < expr.size()  && ZMath.isNumber(expr.get(i+1)))
				{
					expr.add(i+1, Operator.MULTIPLY.toString());
				}
			}
			
			// special symbols
			else if (Symbol.isSymbol(token))
			{
				Symbol s = Symbol.getSymbol(token);
				String replacement = s.replacement();
				ArrayList<String> insert = new ArrayList<String>();
				insert.add(replacement);

				// preceded by number
				if (i > 0 && ZMath.isNumber(expr.get(i-1)))
					insert.add(0, Operator.MULTIPLY.toString());
				// followed by number or variable
				if (i + 1 < expr.size() && ZMath.isNumber(expr.get(i+1)))
					insert.add(Operator.MULTIPLY.toString());

				expr.remove(i);
				for (int j = 0; j < insert.size(); j++)
					expr.add(i+j, insert.get(j));
			}
			
			// "-" --> "+-" ONLY when it is as an operator
			else if (token.equals("-"))
			{
				ArrayList<String> insert = new ArrayList<String>();
				insert.add("-1");
				
				// not followed by operator:
				if (i+1 < expr.size() && !Operator.isOperator(expr.get(i+1)))
					insert.add(Operator.MULTIPLY.toString());
				// preceded by number
				if (i > 0 && (ZMath.isNumber(expr.get(i-1)) || Variable.isVariable(expr.get(i-1))))
					insert.add(0, Operator.ADD.toString());
				
				expr.remove(i);
				for (int j = 0; j < insert.size(); j++)
					expr.add(i+j, insert.get(j));
			}
			
			// "(" --> "*(" ONLY when preceded by number
			else if (token.equals("("))
			{
				// previous was number or closing parenthesis : add *
				if (i == 0)
					continue;
				String previous = expr.get(i-1);
				if (ZMath.isNumber(previous) || previous.equals(")"))
				{
					expr.add(i, Operator.MULTIPLY.toString());
					i++; // offset
				}
			}
			
			// unary operators whose input follows: insert implied "*"
			else if (Operator.isOperator(token) &&
					Operator.getOperator(token).type() == Operator.Type.UNARY_POST)
			{
				// preceded by number
				if (i > 0 && ZMath.isNumber(expr.get(i-1)))
				{
					expr.add(i, Operator.MULTIPLY.toString());
					i++;
				}
			}
		}

//		System.out.println("afterprepare="+expr.toStringDebug());
		
		return expr;
	}
	
	/**
	 * Returns a copy of the specified infix string 
	 * converted to postfix form.
	 */
	public ExprString toPostfix()
	{
		// prepare infix by replacing certain symbols
		ExprString infix = copy().prepare();
		
		Stack<String> stack = new Stack<String>();
		ExprString postfix = new ExprString();
		for (int i = 0; i < infix.size(); i++)
		{
			String token = infix.get(i);
			
			// do nothing for whitespace
			if (token.isEmpty())
				continue;
			
			// operator
			if (Operator.isOperator(token))
			{
				while (!stack.empty() && Operator.priority(stack.peek()) >= Operator.priority(token))
					postfix.add(stack.pop());
				stack.push(token);
				
				// special case if operator also acts as parenthesis
				if (Operator.getOperator(token).isParenthesis())
					stack.push("(");
			}
			
			// parentheses
			else if (token.equals("("))
				stack.push(token);
			else if (token.equals(")"))
			{
				while (!stack.peek().equals("(")) // find last opening parenthesis
					postfix.add(stack.pop());
				stack.pop();
			}
			
			// part of number or variable
			else if (ZMath.isNumber(token) || token.equals(".") 
					|| token.equals("-") || Variable.isVariable(token))
			{
				// if following characters are digits, add to postfix together
				if (i+1 < infix.size())
				{
					String next = infix.get(i+1);
					while (i+1 < infix.size() && (ZMath.isNumber(next) || next.equals(".") || token.equals("-")))
					{
						token += next;
						i++;
						if (i+1 < infix.size())
							next = infix.get(i+1);
						else
							break;
					}
				}
				postfix.add(token);
			}
		}
		
		// add remaining operators to string
		while (!stack.empty())
		{
			if (!stack.peek().equals("("))
				postfix.add(stack.pop());
			else
				stack.pop();
		}
		
//		System.out.println("postfix="+postfix.toStringDebug());
		return postfix;	// remove trailing white space
	}
	
	public int getTokenIndex()
	{
		return tokenIndex;
	}
	
	public void setTokenIndex(int index)
	{
		tokenIndex = index;
	}

	public void shiftTokenIndexLeft(int shift)
	{
		tokenIndex -= shift;
		if (tokenIndex < 0)
			tokenIndex = 0;
//		System.out.println("tokenindex = " + tokenIndex);
	}
	
	public void shiftTokenIndexRight(int shift)
	{
		tokenIndex += shift;
		if (tokenIndex > size())
			tokenIndex = size();
//		System.out.println("tokenindex = " + tokenIndex);
	}
	
	public String toString()
	{
		String str = "";
		for (String s : this)
			str += s;
		return str;
	}
	
	public String toStringDebug()
	{
		String str = "";
		for (String s : this)
			str += s + ",";
		return str;
	}
	
	public String toString(int tokenMax)
	{
		String str = "";
		for (int i = 0; i < tokenMax && i < size(); i++)
			str += get(i);
//		System.out.println("short=" + str);
		return str;
	}
}