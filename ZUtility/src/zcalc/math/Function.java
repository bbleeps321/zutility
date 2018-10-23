package zcalc.math;

import java.util.ArrayList;


/**
 * Stores an expression and has functions 
 * for evaluating values from the expression.
 */
public class Function
{
	private Expression expression;	// expression object, with variable value form last evaluation
								    // for variables. variables 0 by default.
	private ArrayList<Constant> variables;	// list of all variables in expr
	
	/**
	 * Expr should be in infix.
	 */
	public Function(Expression expr)
	{
		expression = expr;
//		if (expression == null)
//			System.err.println("Invalid expression for function!");
	}
	
	/**
	 * Evaluates the expression for the given set of variables
	 * and values for the variables.
	 */
	public Constant evaluate(String var, double val)
	{
		// set static variable value
		Variable.getVar(var).setValue(val);
		return expression.evaluate();
	}
	
	/**
	 * Evaluates the expression for the given set of variables
	 * and values for the variables.
	 */
	public Constant evaluate(String[] var, double[] val)
	{
		if (var.length != val.length)
		{
			System.err.println("Unequal variable-value lengths");
			return null;
		}
		
		// set all static variable values.
		for (int i = 0; i < var.length; i++)
			Variable.getVar(var[i]).setValue(val[i]);
		return expression.evaluate();
	}
	
	/**
	 * Returns whether or not this function is valid.
	 * (Can be evaluated). Can still throw an exception
	 * if it is too poorly formatted.
	 */
	public boolean isValid()
	{
		try
		{
			return expression.evaluate() != null;
		}
		catch (Exception e)
		{
			return false;
		}
	}
	
	/**
	 * Returns the expression.
	 */
	public Expression getExpression()
	{
		return expression;
	}
	
	/**
	 * Returns the expression as a string. Empty if expression is null.
	 */
	public String toString()
	{
		if (expression == null)
			return "";
		return expression.getOriginal().toString();
	}
	
	/**
	 * Returns the function as an ExprString.
	 */
	public ExprString toExprString()
	{
		if (expression == null)
			return new ExprString();
		return expression.getOriginal();
	}
}
