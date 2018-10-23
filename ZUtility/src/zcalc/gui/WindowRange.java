package zcalc.gui;

import org.jfree.chart.plot.XYPlot;
import org.jfree.data.Range;

/**
 * Stores data abouta window range.
 */
public class WindowRange
{
	public static final WindowRange STANDARD = new WindowRange("Zoom Standard", -10, 10, 1, -10, 10, 1);
	public static final WindowRange TRIG_RAD = new WindowRange("Zoom Trig", -2*Math.PI, 2*Math.PI, Math.PI/2, -4, 4, 1);
	public static final WindowRange TRIG_DEG = new WindowRange("Zoom Trig", -360, 360, 90, -4, 4, 1);
	public static final WindowRange FIT = new WindowRange("Zoom Fit", -10, 10, 1, -10, 10, 1);
	
	
	private String name;
	private double xMin, xMax, xScl;
	private double yMin, yMax, yScl;
	private boolean modifiable;
	private boolean autoX, autoY;	// if auto scale to fit
	
	public WindowRange(String name, double xMin, double xMax, double xScl, double yMin,
			double yMax, double yScl, boolean autoX, boolean autoY, boolean modifiable)
	{
		super();
		this.name = name;
		this.xMax = xMax;
		this.xMin = (xMin < xMax) ? xMin : xMax;
		this.xScl = xScl;
		this.yMax = yMax;
		this.yMin = yMin;
		this.yScl = yScl;
		this.autoX = autoX;
		this.autoY = autoY;
		this.modifiable = modifiable;
	}
	
	public WindowRange(String name, double xMin, double xMax, double xScl,
			double yMin, double yMax, double yScl)
	{
		super();
		this.name = name;
		this.xMax = xMax;
		this.xMin = xMin;
		this.xScl = xScl;
		this.yMax = yMax;
		this.yMin = yMin;
		this.yScl = yScl;
		modifiable = false;
	}
	
	public WindowRange(String name, double xMin, double xMax, double xScl,
			double yMin, double yMax, double yScl, boolean modifiable)
	{
		super();
		this.name = name;
		this.xMax = xMax;
		this.xMin = xMin;
		this.xScl = xScl;
		this.yMax = yMax;
		this.yMin = yMin;
		this.yScl = yScl;
		this.modifiable = modifiable;
	}
	
	public WindowRange modifiableCopy()
	{
		return new WindowRange(name, xMin, xMax, xScl, yMin, yMax, yScl, autoX, autoY, true);
	}
	
	/**
	 * Scales the plot according to the object's range settings.
	 */
	public void format(XYPlot plot)
	{
		// domain
		if (autoX)
			plot.getDomainAxis().setAutoRange(true);
		else
			plot.getDomainAxis().setRange(xMin, xMax);
		plot.getDomainAxis().setDefaultAutoRange(new Range(xMin, xMax));
		
		// range
		if (autoY)
			plot.getRangeAxis().setAutoRange(true);
		else
			plot.getRangeAxis().setRange(yMin, yMax);
		plot.getRangeAxis().setDefaultAutoRange(new Range(xMin, xMax));
	}
	
	public Range getXRange()
	{
		return new Range(xMin, xMax);
	}
	
	public Range getYRange()
	{
		return new Range(yMin, yMax);
	}

	public double xMin()
	{
		return xMin;
	}

	public double xMax()
	{
		return xMax;
	}

	public double xScl()
	{
		return xScl;
	}

	public double yMin()
	{
		return yMin;
	}

	public double yMax()
	{
		return yMax;
	}

	public double yScl()
	{
		return yScl;
	}
	
	public String name()
	{
		return name;
	}

	public void setXMin(double min)
	{
		if (modifiable)
			xMin = min;
	}

	public void setXMax(double max)
	{
		if (modifiable)
			xMax = max;
	}

	public void setXScl(double scl)
	{
		if (modifiable)
			xScl = scl;
	}

	public void setYMin(double min)
	{
		if (modifiable)
			yMin = min;
	}

	public void setYMax(double max)
	{
		if (modifiable)
			yMax = max;
	}

	public void setYScl(double scl)
	{
		if (modifiable)
			yScl = scl;
	}

	public String toString()
	{
		return name;
	}
}
