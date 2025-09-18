package com.vgb;

/*
 * Gavin Blesh
 * 
 * 2025-05-02
 * 
 * Class that is called in EntityTests and InvoiceTests to round the number
 */
public class MathRounding {

	/*
	 * Rounds the number to the nearest hundredth (0.00)
	 */
	public static double round(double x) {
		return Math.round(x * 100.0) / 100.0;
	}
}
