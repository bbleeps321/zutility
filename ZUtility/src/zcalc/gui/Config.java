package zcalc.gui;

import zcalc.math.Variable;


/**
 * Stores static configuration variables.
 */
public class Config
{
	// CONSTANTS
	public static final int SCALE_OFFSET = 20;	// offset for scale, used to multiply number of points
	public static final double X_SCL_DEFAULT = 1;
	public static final double X_MIN_DEFAULT = -10;
	public static final double X_MAX_DEFAULT = 10;
	public static final double Y_SCL_DEFAULT = 1;
	public static final double Y_MIN_DEFAULT = -10;
	public static final double Y_MAX_DEFAULT = 10;
	
	// window range
	public static WindowRange WINDOW_RANGE = WindowRange.STANDARD.modifiableCopy();
	public static WindowRange BASE_WINDOW_RANGE = WindowRange.STANDARD;
	public static boolean AUTO_FIT_RANGE = false;
	
	// plot options
	public static boolean CROSSHAIR_ON = true; // flag for whether or not to display crosshair for mouse on chart.
	
	// calculation options
	public static boolean IN_RADIANS = true;	// if in radians. false in degrees 
	public static Variable LAST_ANSWER()		// last answer stored
	{
		return Variable.getVar(" Ans");
	}
	
	
}
