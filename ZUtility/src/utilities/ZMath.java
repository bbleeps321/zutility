package utilities;

public class ZMath
{
	/**
	 * Returns whether or not the specified String can be
	 * converted into a number.
	 */
	public static boolean isNumber(String s)
	{
		try
		{
			Double.parseDouble(s);
			
			// successful parsing, return true.
			return true;
		}
		
		// unsuccessful parsing, return false.
		catch (NumberFormatException e)
		{
			return false;
		}
	}
	
	/**
	 * Returns the factorial of the input value.
	 */
	public static int factorial(int val)
	{
//		int sign = (val < 0) ? -1 : 1;
		if (val == 0)
			return 1;
		else
			return val*factorial(Math.abs(val) - 1);
	}
	
	/**
	 * Returns a raised to the power b. Usually the same 
	 * as Math.pow.
	 * 
	 * Exception include but are not limited to:
	 * raising negative numbers to non-integer powers
	 */
	public static double pow(double a, double b)
	{
		if (a < 0 && (int)b != b)
			return -1*Math.pow(-a,b);

		return Math.pow(a,b);	// most cases
	}
}
