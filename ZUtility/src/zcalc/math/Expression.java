package zcalc.math;

import java.util.EmptyStackException;
import java.util.Iterator;
import java.util.Stack;

import utilities.ZMath;

/**
 * Encapsulates data of an expression of two numbers and 
 * a single function. Can store either simpler numbers or 
 * other expressions. 
 */
public abstract class Expression extends Number
{
	private ExprString original;	// string expression was read from
									// must be explicitly set.
	
	/**
	 * Parses an expression from the specified String.
	 * The String must be in infix form, with spaces between tokens.
	 * Returns null if error occurs (incorrect format).
	 */
	public static Expression read(ExprString orig) throws MathException
	{
		if (orig.isEmpty())
			return null;
		
		Stack<Expression> stack = new Stack<Expression>();
		
		Expression expr = null;
		ExprString postfix = orig.toPostfix();	// convert to postfix (copy)
		// remove all parenthesis
		for (int i = 0; i < postfix.size(); i++)
		{
			if (postfix.get(i).equals("(") || postfix.get(i).equals(")"))
			{
				postfix.remove(i);
				i--;
			}
		}
		
		try
		{
			Iterator<String> iter = postfix.iterator();
			while (iter.hasNext())
			{
				String token = iter.next();
				
				// number -> constant
				if (ZMath.isNumber(token))
					stack.push(new Constant(token));
				// letter -> variable
				else if (Variable.isVariable(token))
					stack.push(Variable.getVar(token));
				// operator symbol -> expression
				else
				{
					Operator oper = Operator.getOperator(token);
					if (oper.type() == Operator.Type.BINARY)
					{
						Expression right = stack.pop();
						Expression left = stack.pop();
						stack.push(new BinaryExpression(left, oper, right));
					}
					else if (oper.type() == Operator.Type.UNARY_PRE || oper.type() == Operator.Type.UNARY_POST)
					{
						Expression input = stack.pop();
						stack.push(new UnaryExpression(oper, input));
					}
				}
			}
		}
		catch (EmptyStackException e)
		{
			throw new MathException("Invalid expression!");
		}		
		
		// top level in stack is top level expression
		Expression e = stack.peek();
		e.setOriginal(orig);
		return e;
	}
	
	/**
	 * Sets the stored original string this expression
	 * was based off of.
	 */
	public void setOriginal(ExprString s)
	{
		original = s;
	}
	
	/**
	 * Returns the original string this expression was
	 * based off of.
	 */
	public ExprString getOriginal()
	{
		return original;
	}
	
	/**
	 * Returns the value of this expression as a 
	 * double.
	 */
	public double doubleValue()
	{
		return evaluate().doubleValue();
	}

	/**
	 * Returns the value of this expression casted 
	 * as a float.
	 */
	public float floatValue()
	{
		return evaluate().floatValue();
	}

	/**
	 * Returns the value of this expression casted 
	 * as an int.
	 */
	public int intValue()
	{
		return evaluate().intValue();
	}

	/**
	 * Returns the value of this expression casted 
	 * as a long.
	 */
	public long longValue()
	{
		return evaluate().longValue();
	}
	
	/**
	 * Returns the value of this expression. Different depending on the operation type.
	 */
	public abstract Constant evaluate();
}
