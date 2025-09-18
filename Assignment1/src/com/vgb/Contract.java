package com.vgb;

import java.util.UUID;

/*
 * Gavin Blesh
 * 
 * 2025-05-09
 * 
 * Class to represent a contract/subcontract, which is an extension of the item class
 */

public class Contract extends Item {

	private Company servicer;
	private double amount;

	/*
	 * Constructor method for the contract class
	 */
	public Contract(UUID code, String name, Company servicer, double amount) {
		super(code, name);
		this.servicer = servicer;
		this.amount = amount;
	}

	/*
	 * Copy Constructor method for the contract class
	 */
	public Contract(Contract contract, double amount) {
		super(contract.getCode(), contract.getName());
		this.servicer = contract.getServicer();
		this.amount = amount;
	}

	/*
	 * Getter method for the servicer
	 */
	public Company getServicer() {
		return servicer;
	}

	/*
	 * Getter method for the amount
	 */
	public double getAmount() {
		return amount;
	}

	/*
	 * calculates the total $ amount before tax
	 */
	public double calculateTotal() {
		return MathRounding.round(amount);

	}

	/*
	 * Calculates the tax in $ (returns 0.00, contracts are tax-exempt
	 */
	public double calculateTax() {
		return 0.00;
	}

	/*
	 * Calculates the Grand Total
	 */
	public double calculateGrandTotal() {
		return calculateTotal() + calculateTax();
	}

	/*
	 * toString function to format the output
	 */
	@Override
	public String toString() {
		return String.format("%s (Contract) %s \n %s: %s\n %-30s $%-20.2f $%-20.2f $%-20.2f", getCode(), getName(),
				"Servicer", servicer.getName(), "", calculateTax(), calculateTotal(), calculateGrandTotal());

	}

}
