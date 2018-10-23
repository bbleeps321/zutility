package zcalc.math;

public class UnaryExpression extends Expression
{
	private Expression input;
	private Operator oper;
	
	
	public UnaryExpression(Operator oper, Expression input)
	{
		this.input = input;
		this.oper = oper;
		if (oper.type() != Operator.Type.UNARY_POST && oper.type() != Operator.Type.UNARY_PRE)
			System.err.println("Operator not unary!");
	}
	
	public Expression getInput()
	{
		return input;
	}
	
	/**
	 * Returns the operator for the expression.
	 */
	public Operator getOperator()
	{
		return oper;
	}
	
	public Constant evaluate()
	{
		try
		{
			return new Constant(oper.evaluate(input));
		}
		catch(MathException e)
		{
			System.err.println(e);
			return null;
		}
	}
	
	/**
	 * Returns a String representation of the expression.
	 */
	public String toString()
	{
		if (oper.type() == Operator.Type.UNARY_PRE)
			return input.toString() + oper.toString();
		else
			return oper.toString() + input.toString();
	} 

}
