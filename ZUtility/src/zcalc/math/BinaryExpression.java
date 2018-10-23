package zcalc.math;

/**
 * Expression representing an operation on two numbers.
 */
public class BinaryExpression extends Expression
{
	// Numbers on left and right side of expression.
	// Can be other expressions or simple numbers.
	private Expression left, right;
	
	// Operator for expression.
	private Operator oper;
	
	public BinaryExpression(Expression left, Operator oper, Expression right)
	{
		this.left = left;
		this.right = right;
		this.oper = oper;
		if (oper.type() != Operator.Type.BINARY)
			System.err.println("Operator not binary!");
	}
	
	/**
	 * Returns the evaluated value of this binary expression.
	 */
	public Constant evaluate()
	{
		return new Constant(oper.evaluate(left, right));
	}
	
	/**
	 * Returns the left-hand expression.
	 */
	public Expression getLeft()
	{
		return left;
	}
	
	/**
	 * Returns the right-hand expression.
	 */
	public Expression getRight()
	{
		return right;
	}
	
	/**
	 * Returns the operator for the expression.
	 */
	public Operator getOperator()
	{
		return oper;
	}
	
	/**
	 * Returns a String representation of the expression.
	 */
	public String toString()
	{
		return left.toString() + " " + oper.toString() + " " + right.toString();
	}
}
