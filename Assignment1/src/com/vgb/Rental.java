package com.vgb;

import java.util.UUID;

/*
 * Gavin Blesh
 * 
 * 2025-05-02
 * 
 * Class to represent a Rental which is an extension of the Equipment class
 */

public class Rental extends Equipment {

	private double hoursRented;

	/*
	 * Constructor for the Rental class
	 */
	public Rental(UUID code, String name, String modelNumber, double retailCost, double hoursRented) {
		super(code, name, modelNumber, retailCost);
		this.hoursRented = hoursRented;
	}

	/*
	 * Copy Constructor for the Rental Class
	 */
	public Rental(Equipment equipment, double hoursRented) {
		super(equipment.getCode(), equipment.getName(), equipment.getModelNumber(), equipment.calculateTotal());
		this.hoursRented = hoursRented;
	}

	/*
	 * Function to calculate the total
	 */
	public double calculateTotal() {
		double hourlyCharge = super.calculateTotal() * 0.001;
		return MathRounding.round(hourlyCharge * hoursRented);
	}

	/*
	 * Function to calculate the tax
	 */
	public double calculateTax() {
		return MathRounding.round(calculateTotal() * 0.0438);
	}

	/*
	 * Function to calculate the grand total (subtotal + tax)
	 */
	public double calculateGrandTotal() {
		return calculateTotal() + calculateTax();
	}

	/*
	 * toString function to format the data for output
	 */
	@Override
	public String toString() {
		return String.format(
				"%s (Rental) %s (%s)\n%.2f hours rented @ $%.2f per hour\n %-30s $%-20.2f $%-20.2f $%-20.2f", getCode(),
				getName(), getModelNumber(), hoursRented, (MathRounding.round(calculateTotal() / hoursRented)), "",
				calculateTax(), calculateTotal(), calculateGrandTotal());
	}

}
