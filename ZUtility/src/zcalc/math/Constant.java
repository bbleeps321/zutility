package zcalc.math;

/**
 * Expression representing a constant.
 */
public class Constant extends Expression
{
	private static final double EPSILON = 1E-6;
	private double myVal;
	
	public Constant(double val)
	{
		myVal = val;
		
		// convert infinities to NaN
//		if (Double.isInfinite(myVal))
//			myVal = Double.NaN;
	}
	
	public Constant(String s)
	{
		myVal = Double.parseDouble(s);
	}
	
	public Constant evaluate()
	{
		return this;
	}
	
	/**
	 * Returns the value of this expression as a 
	 * double.
	 */
	public double doubleValue()
	{
		return myVal;
	}

	/**
	 * Returns the value of this expression casted 
	 * as a float.
	 */
	public float floatValue()
	{
		return (float)myVal;
	}

	/**
	 * Returns the value of this expression casted 
	 * as an int.
	 */
	public int intValue()
	{
		return (int)myVal;
	}

	/**
	 * Returns the value of this expression casted 
	 * as a long.
	 */
	public long longValue()
	{
		return (long)myVal;
	}
	
	public String toString()
	{
		// return without decimal if integer value
		if ((int)myVal == myVal)
			return Integer.toString((int)myVal);
		return Double.toString(myVal);
	}
	
	public ExprString toExprString()
	{
		// return without decimal if integer value
		String s = toString();
		ExprString exprStr = new ExprString();
		for (int i = 0; i < s.length(); i++)
			exprStr.add(s.substring(i,i+1));
		return exprStr;
	}
}
