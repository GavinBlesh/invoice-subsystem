package com.vgb;

import java.util.UUID;

/*
 * Gavin Blesh
 * 
 * 2025-05-09
 * 
 * Class to represent Equipment which is an extension of the Item class
 */

public class Equipment extends Item {

	private String modelNumber;
	private double retailCost;
	public String type;

	/*
	 * Constructor for the Equipment class
	 */
	public Equipment(UUID code, String name, String modelNumber, double retailCost) {
		super(code, name);
		this.modelNumber = modelNumber;
		this.retailCost = retailCost;
	}

	/*
	 * Getter method for the model number
	 */
	public String getModelNumber() {
		return modelNumber;
	}

	/*
	 * Function that calculates the total cost of the money (pre-tax)
	 */
	public double calculateTotal() {
		return MathRounding.round(retailCost);
	}

	/*
	 * Function that calculates the total cost of the tax on the Equipment.
	 */
	public double calculateTax() {
		return MathRounding.round(calculateTotal() * 0.0525);
	}

	/*
	 * Function that calculates the Grand total
	 */
	public double calculateGrandTotal() {
		return calculateTotal() + calculateTax();
	}

	/*
	 * toString to properly format the data
	 */
	@Override
	public String toString() {
		return String.format("%s (Equipment Purchase) %s (%s)\n%-30s $%-20.2f $%-20.2f $%-20.2f", getCode(), getName(),
				modelNumber, "", calculateTax(), calculateTotal(), calculateGrandTotal());
	}

}
