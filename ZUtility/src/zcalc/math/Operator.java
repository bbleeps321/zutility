package zcalc.math;

import utilities.Symbol;
import utilities.ZMath;
import zcalc.gui.Config;

/**
 * Handles basic zcalc.math functions and their types.
 */
public enum Operator
{
	ADD("+", 0, Type.BINARY),
//	SUBTRACT("-", 0),  // subtraction treated as + (-).
	MULTIPLY("*", 1, Type.BINARY),
	DIVIDE("/", 1, Type.BINARY),
	REMAINDER("%", 1, Type.BINARY),
	POWER("^", 2, Type.BINARY),
	INVERSE("\u207B\u00B9", 2, Type.UNARY_PRE),
	SQUARED("\u00B2", 2, Type.UNARY_PRE),
	POWER_TEN("\u2081\u2080^(", 2, Type.UNARY_POST, true),
	POWER_E(Symbol.E.symbol() + "^(", 2, Type.UNARY_POST, true),
	SQRT("\u221A(", 3, Type.UNARY_POST, true),
	ROOT("\u207F\u221A(", 3, Type.BINARY, true),
	FACTORIAL("!", 3, Type.UNARY_PRE, false),
	SCI_NOT("\u0112", 4, Type.BINARY),
	LOG("log(", 4, Type.UNARY_POST, true),
	LN("ln(", 4, Type.UNARY_POST, true),
	SIN("sin(", 4, Type.UNARY_POST, true),
	COS("cos(", 4, Type.UNARY_POST, true),
	TAN("tan(", 4, Type.UNARY_POST, true),
	ARCSIN("sin\u207B\u00B9(", 4, Type.UNARY_POST, true),
	ARCCOS("cos\u207B\u00B9(", 4, Type.UNARY_POST, true),
	ARCTAN("tan\u207B\u00B9(", 4, Type.UNARY_POST, true),
	CSC("csc(", 4, Type.UNARY_POST, true),
	SEC("sec(", 4, Type.UNARY_POST, true),
	COT("cot(", 4, Type.UNARY_POST, true),
	ARCCSC("csc\u207B\u00B9(", 4, Type.UNARY_POST, true),
	ARCSEC("sec\u207B\u00B9(", 4, Type.UNARY_POST, true),
	ARCCOT("cot\u207B\u00B9(", 4, Type.UNARY_POST, true),
	ABS("abs(", 4, Type.UNARY_POST, true);
	
	public enum Type
	{
		BINARY,
		UNARY_PRE,	// unary, input precedes (ex. !)
		UNARY_POST; // unary, input follows (ex. log)
	}
	
	private String symbol;
	private Type type;
	private int priority;	// 0 = least
	private boolean parenthesis;	// if acts as opening parentheses, too.
									// false by default
	
	private Operator(String symbol, int priority, Type t)
	{
		this.symbol = symbol;
		this.priority = priority;
		this.type = t;
		this.parenthesis = false;
	}
	
	private Operator(String symbol, int priority, Type t, boolean parentheses)
	{
		this.symbol = symbol;
		this.priority = priority;
		this.type = t;
		this.parenthesis = parentheses;
	}
	
	/**
	 * Evaluates operator with left and right objects.
	 * Intended for binary operators. If the operator is unary,
	 * only used the left object.
	 */
	public double evaluate(Number leftObj, Number rightObj)
	{
//		// if unary, only use left
//		if (type() == Type.UNARY_PRE || type() == Type.UNARY_POST)
//			return evaluate(leftObj);
		
		double left = leftObj.doubleValue();
		double right = rightObj.doubleValue();
//		System.out.println("performing: " + left + " " + toString() + " " + right);
		
		// check type and evaluate accordingly
		if (this.equals(ADD))
			return left + right;
//		else if (this.equals(SUBTRACT))
//			return left - right;
		else if (this.equals(MULTIPLY))
			return left * right;
		else if (this.equals(DIVIDE))
			return left / right;
		else if (this.equals(REMAINDER))
			return left % right;
		else if (this.equals(POWER))
			return ZMath.pow(left, right);	// ZMath handles special cases
		else if (this.equals(SCI_NOT))
			return left * Math.pow(10, right);
		else if (this.equals(ROOT))
			return Math.pow(right, 1/left);
		// unknown type
		else
		{
			System.err.println("Unknown operator type!");
			return 0;
		}
	}
	
	/**
	 * Evaluates operator with left and right objects.
	 * Intended for unary operators.
	 */
	public double evaluate(Number inputObj) throws MathException
	{
		double input = inputObj.doubleValue();
		
		// check type and evaluate accordingly
		if (this.equals(LOG))
			return Math.log10(input);
		else if (this.equals(LN))
			return Math.log(input);
		else if (this.equals(SQRT))
			return Math.sqrt(input);
		else if (this.equals(FACTORIAL))
		{
			int intInput = (int)input;
			if (intInput != input)
				throw new MathException("Invalid input! Must be integer.");
			return ZMath.factorial(intInput);
		}
		else if (this.equals(ABS))
			return Math.abs(input);
		else if (this.equals(INVERSE))
			return 1/input;
		else if (this.equals(SQUARED))
			return input*input;
		else if (this.equals(POWER_TEN))
			return Math.pow(10,input);
		else if (this.equals(POWER_E))
			return Math.pow(Math.E,input);
		else if (this.equals(SIN))
		{
			if (Config.IN_RADIANS)
				return Math.sin(input);
			else
				return Math.sin(Math.toRadians(input));
		}
		else if (this.equals(COS))
		{
			if (Config.IN_RADIANS)
				return Math.cos(input);
			else
				return Math.cos(Math.toRadians(input));
		}
		else if (this.equals(TAN))
		{
			if (Config.IN_RADIANS)
				return Math.tan(input);
			else
				return Math.tan(Math.toRadians(input));
		}
		else if (this.equals(ARCSIN))
		{
			if (Config.IN_RADIANS)
				return Math.asin(input);
			else
				return Math.toDegrees(Math.asin(input));
		}
		else if (this.equals(ARCCOS))
		{
			if (Config.IN_RADIANS)
				return Math.acos(input);
			else
				return Math.toDegrees(Math.acos(input));
		}
		else if (this.equals(ARCTAN))
		{
			if (Config.IN_RADIANS)
				return Math.atan(input);
			else
				return Math.toDegrees(Math.atan(input));
		}
		else if (this.equals(CSC))
		{
			if (Config.IN_RADIANS)
				return 1/Math.sin(input);
			else
				return 1/Math.sin(Math.toRadians(input));
		}
		else if (this.equals(SEC))
		{
			if (Config.IN_RADIANS)
				return 1/Math.cos(input);
			else
				return 1/Math.cos(Math.toRadians(input));
		}
		else if (this.equals(COT))
		{
			if (Config.IN_RADIANS)
				return 1/Math.tan(input);
			else
				return 1/Math.tan(Math.toRadians(input));
		}
		else if (this.equals(ARCCSC))
		{
			if (Config.IN_RADIANS)
				return Math.asin(1/input);
			else
				return Math.toDegrees(Math.asin(1/input));
		}
		else if (this.equals(ARCSEC))
		{
			if (Config.IN_RADIANS)
				return Math.acos(1/input);
			else
				return Math.toDegrees(Math.acos(1/input));
		}
		else if (this.equals(ARCCOT))
		{
			if (Config.IN_RADIANS)
				return Math.atan(1/input);
			else
				return Math.toDegrees(Math.atan(1/input));
		}
		// unknown type
		else
		{
			System.err.println("Unknown operator type!");
			return 0;
		}
	}

	public int priority()
	{
		return priority;
	}
	
	public String symbol()
	{
		return symbol;
	}
	
	public String toString()
	{
		return symbol;
	}
	
	public boolean isParenthesis()
	{
		return parenthesis;
	}
	
	public Type type()
	{
		return type;
	}
	
	public static boolean isOperator(String symbol)
	{
		for (Operator op : values())
			if (op.symbol().equals(symbol))
				return true;
		return false;
	}
	
	public static Operator getOperator(String symbol)
	{
		for (Operator op : values())
			if (op.symbol().equals(symbol))
				return op;
		System.err.println("Operator " + symbol + " not found!");
		return null;
	}
	
	public static int priority(String symbol)
	{
		Operator op = getOperator(symbol);
		if (op == null)// ERROR!
			return -1;
		return op.priority();
	}
	
}
