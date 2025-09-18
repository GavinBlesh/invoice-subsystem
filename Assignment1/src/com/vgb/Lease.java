package com.vgb;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

/*
 * Gavin Blesh
 * 
 * 2025-05-02
 * 
 * Class to model a Lease which is an extension of the Equipment class
 */
public class Lease extends Equipment {

	private LocalDate startDate;
	private LocalDate endDate;

	/*
	 * Constructor function for the Lease class
	 */
	public Lease(UUID code, String name, String modelNumber, double retailCost, LocalDate startDate,
			LocalDate endDate) {
		super(code, name, modelNumber, retailCost);
		this.startDate = startDate;
		this.endDate = endDate;
	}

	/*
	 * Copy Constructor function for the Lease Class
	 */
	public Lease(Equipment equipment, LocalDate startDate, LocalDate endDate) {
		super(equipment.getCode(), equipment.getName(), equipment.getModelNumber(), equipment.calculateTotal());
		this.startDate = startDate;
		this.endDate = endDate;
	}

	/*
	 * Getter Function for the Start Date of the Lease
	 */
	public LocalDate getStartDate() {
		return startDate;
	}

	/*
	 * Getter function for the end date of the Lease
	 */
	public LocalDate getEndDate() {
		return endDate;
	}

	/*
	 * Calculates the days between the start and end of the lease. + 1 to include
	 * the end date.
	 */
	public double calculateLeaseDuration() {
		return (int) ChronoUnit.DAYS.between(getStartDate(), endDate) + 1;
	};

	/*
	 * Function to calculate the total cost (pre-tax) for the lease
	 */
	public double calculateTotal() {

		double totalYears = calculateLeaseDuration() / 365.0;
		double amortizedCost = (totalYears / 5.0);
		double preTaxCost = super.calculateTotal() * amortizedCost * 1.5;

		return MathRounding.round(preTaxCost);
	}

	/*
	 * Function to calculate the tax for the lease.
	 */
	public double calculateTax() {
		double tax;
		if (calculateTotal() >= 12500.0) {
			tax = 1500.00;
		} else if (calculateTotal() >= 5000.0) {
			tax = 500.00;
		} else {
			tax = 0.00;
		}
		return tax;
	}

	/*
	 * Function to calculate the Grand Total
	 */
	public double calculateGrandTotal() {
		return calculateTotal() + calculateTax();
	}

	/*
	 * toString to properly format the data.
	 */
	@Override
	public String toString() {
		return String.format("%s (Lease) %s (%s)\n %.0f days leased (%s to %s)\n %-30s $%-20.2f $%-20.2f $%-20.2f",
				getCode(), getName(), getModelNumber(), calculateLeaseDuration(), startDate, endDate, "",
				calculateTax(), calculateTotal(), calculateGrandTotal());
	}
}
