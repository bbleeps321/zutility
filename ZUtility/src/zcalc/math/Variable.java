package zcalc.math;

import java.util.Iterator;
import java.util.TreeMap;

/**
 * Represents a variable whose value can be set.
 * Static variables for entire program, use getVar(c)
 * to access. Some symbols (ex. Ans) have a space in
 * front of key for sorting purposes.
 */
public class Variable extends Expression
{
	private String symbol;
	private double currentValue;
	
	// static map of characters to their variables.
	private static TreeMap<String, Variable> varMap;
	
	/**
	 * Returns the static instance of the variable associated 
	 * with the specified symbol.
	 * ALWAYS USE THIS WHEN ACCESSING VARIABLES!.
	 */
	public static Variable getVar(String s)
	{
		Variable var = varMap().get(s);
		if (var == null)
			var = varMap().get(" " + s);
		return var;
	}
	
	/**
	 * Returns whether or not the specified String is 
	 * a variable (1 character long; {A-Z}.
	 */
	public static boolean isVariable(String s)
	{
		return varMap().keySet().contains(s) || varMap().keySet().contains(" "+s);
	}
	
	/**
	 * Returns number of variables.
	 */
	public static int variableCount()
	{
		return varMap().size();
	}
	
	/**
	 * Returns an iterator for all the characters in map. 
	 */
	public static Iterator<Variable> iterator()
	{
		return varMap().values().iterator();
	}
	
	/**
	 * Returns the variable at the specified index.
	 */
	public static Variable getVar(int index)
	{
		Iterator<String> iter = varMap().keySet().iterator();
		for (int i = 0; iter.hasNext(); i++)
		{
			if (i == index)
				return getVar(iter.next().trim());
			iter.next();
		}
		return null;
	}
	
	/**
	 * Returns the variable map, initialized if not already.
	 * USE THIS WHEN ACCESSING MAP.
	 */
	private static TreeMap<String, Variable> varMap()
	{
		// initialize var map if first call
		if (varMap == null)
			initializeVarMap();
		return varMap;
	}
	
	/**
	 * Initializes the variable map.
	 * Keys = {A-Z}
	 */
	private static void initializeVarMap()
	{
		varMap = new TreeMap<String,Variable>();
		for (char c = 'A'; c <= 'Z'; c++)
			varMap.put(Character.toString(c), new Variable(Character.toString(c)));
		varMap.put(" Ans", new Variable("Ans"));
	}
	
	/**
	 * Creates a variable represented by the specified
	 * symbol. The default stored value is 0.
	 */
	private Variable(String symbol)
	{
		this.symbol = symbol;
		currentValue = 0;
	}
	
	/**
	 * Sets the current value stored by this variable.
	 */
	public void setValue(double val)
	{
		currentValue = val;
	}
	
	/**
	 * Sets the current value stored by this variable.
	 */
	public void setValue(Constant val)
	{
		currentValue = val.doubleValue();
	}
	
	/**
	 * Returns the symbol representing this variable.
	 */
	public String getSymbol()
	{
		return symbol;
	}
	
	/**
	 * Increases the stored value by the specified amount.
	 */
	public void increase(double change)
	{
		currentValue += change;
	}
	
	/**
	 * Decreases the stored value by the specified amount.
	 */
	public void decrease(double change)
	{
		currentValue -= change;
	}
	
	/**
	 * Returns the current value of this variable.
	 */
	public Constant evaluate()
	{
		return new Constant(currentValue);
	}
}
