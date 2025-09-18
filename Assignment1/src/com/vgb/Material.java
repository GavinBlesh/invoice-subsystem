package com.vgb;

import java.util.UUID;

/*
 * Gavin Blesh
 * 
 * 2025-05-02
 * 
 * Class to represent Material used which is an extension of the Item class
 */

public class Material extends Item {

	private String unit;
	private double pricePerUnit;
	private int quantity;

	/*
	 * Constructor method for the Material class
	 */
	public Material(UUID code, String name, String unit, double pricePerUnit, int quantity) {
		super(code, name);
		this.unit = unit;
		this.pricePerUnit = pricePerUnit;
		this.quantity = quantity;
	}

	/*
	 * Copy Constructor method for the Material Class
	 */
	public Material(Material materialCopy, int quantity) {
		super(materialCopy.getCode(), materialCopy.getName());
		this.unit = materialCopy.unit;
		this.pricePerUnit = materialCopy.pricePerUnit;
		this.quantity = quantity;
	}

	/*
	 * Getter method for the unit
	 */
	public String getUnit() {
		return unit;
	}

	/*
	 * Getter method for the Cost Per Unit
	 */
	public double getPricePerUnit() {
		return pricePerUnit;
	}

	/*
	 * function that gets the quantity of the material
	 */
	public int getQuantity() {
		return quantity;
	}

	/*
	 * Calculates the total pre-tax cost
	 */
	public double calculateTotal() {
		return MathRounding.round(pricePerUnit * quantity);
	}

	/*
	 * Calculates the tax
	 */
	public double calculateTax() {
		return MathRounding.round(calculateTotal() * 0.0715);
	}

	/*
	 * Function to calculate the Grand Total
	 */
	public double calculateGrandTotal() {
		return calculateTotal() + calculateTax();
	}

	/*
	 * toString to format the data
	 */
	@Override
	public String toString() {
		return String.format("%s (Material) %s\n%d @ $%.2f/%s\n %-30s $%-20.2f $%-20.2f $%-20.2f", getCode(), getName(),
				quantity, pricePerUnit, unit, "", calculateTax(), calculateTotal(), calculateGrandTotal());
	}
}
