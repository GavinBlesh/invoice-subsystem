package com.vgb;

import java.util.UUID;

/*
 * Gavin Blesh
 * 
 * 2025-05-02
 * 
 * Abstract class to represent Item, which is extended in the classes Contract, Material and Equipment
 */

public abstract class Item {

	private UUID code;
	private String name;

	/*
	 * Constructor method for the Item class
	 */
	public Item(UUID code, String name) {
		this.code = code;
		this.name = name;

	}

	/*
	 * Getter method for the UUID
	 */
	public UUID getCode() {
		return code;
	}

	/*
	 * Getter method for the name
	 */
	public String getName() {
		return name;
	}

	/*
	 * Getter method for Invoices
	 */
	public String getInvoices() {
		return "Invoices[]";
	}

	/*
	 * Abstract function that calls upon one of the subclasses to calculate the
	 * total Depends on what the item is
	 */
	public abstract double calculateTotal();

	/*
	 * Abstract function that calls upon one of the subclasses to calculate the tax
	 * Depends on what the item is.
	 */
	public abstract double calculateTax();

	/*
	 * Abstract function that calls upon one of the subclasses to calculate the
	 * grand total
	 */
	public abstract double calculateGrandTotal();

}
