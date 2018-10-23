package utilities;

public enum Symbol
{
	PI("\u03C0", Double.toString(Math.PI)),
	E("e", Double.toString(Math.E)),
	INFINITY("\u221E", Double.toString(Double.POSITIVE_INFINITY));
	
	private String symbol;
	private String replacement;
	
	private Symbol(String symbol, String replacement)
	{
		this.symbol = symbol;
		this.replacement = replacement;
	}
	
	public String toString()
	{
		return symbol;
	}
	
	public String symbol()
	{
		return symbol;
	}
	
	public String replacement()
	{
		return replacement;
	}
	
	public static Symbol getSymbol(String s)
	{
		Symbol[] symbols = values();
		for (Symbol symbol : symbols)
			if (s.equals(symbol.symbol()))
				return symbol;
		return null;
	}
	
	public static boolean isSymbol(String s)
	{
		Symbol[] symbols = values();
		for (Symbol symbol : symbols)
			if (s.equals(symbol.symbol()))
				return true;
		return false;
	}
}
