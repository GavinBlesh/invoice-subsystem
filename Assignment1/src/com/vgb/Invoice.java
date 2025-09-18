package com.vgb;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/*
 * Gavin Blesh
 * 
 * 2025-05-09
 * 
 * Class to model an Invoice that the company receives.
 */
public class Invoice {

	private UUID invoiceUUID;
	private Company customer;
	private Person salesPerson;
	private LocalDate date;
	private List<Item> items;

	/*
	 * Constructor function for the invoice class
	 */
	public Invoice(UUID invoiceUUID, Company customer, Person salesPerson, LocalDate date) {
		this.invoiceUUID = invoiceUUID;
		this.customer = customer;
		this.salesPerson = salesPerson;
		this.date = date;
		this.items = new ArrayList<>();
	}

	/*
	 * Getter function for the Invoice UUID
	 */
	public UUID getInvoiceUUID() {
		return invoiceUUID;
	}

	/*
	 * Getter function for the Customer
	 */
	public Company getCustomer() {
		return customer;
	}

	/*
	 * Getter function for the Sales Person
	 */
	public Person getSalesPerson() {
		return salesPerson;
	}

	/*
	 * Getter function for the Date (Year-Month-Day Format)
	 */
	public LocalDate getDate() {
		return date;
	}

	/*
	 * Getter function for Items from the list
	 */
	public List<Item> getItems() {
		return items;
	}

	/*
	 * Function that adds items to the list
	 */
	public void addItem(Item item) {
		items.add(item);
	}

	/*
	 * Function that calculates the total across the invoice before tax.
	 */
	public double calculateTotal() {
		double subTotal = 0.0;
		for (Item item : items) {
			subTotal += item.calculateTotal();
		}
		return MathRounding.round(subTotal);
	}

	/*
	 * Function that calculates the total tax across the invoice.
	 */
	public double calculateTaxTotal() {
		double taxTotal = 0.0;
		for (Item item : items) {
			taxTotal += item.calculateTax();
		}
		return MathRounding.round(taxTotal);
	}

	/*
	 * Calculates the total cost with tax included.
	 */
	public double calculateGrandTotal() {
		return MathRounding.round(calculateTotal() + calculateTaxTotal());
	}

	/*
	 * toString to properly format the data
	 */
	@Override
	public String toString() {
		return String.format(
				"\nInvoice UUID: %s\nDate: %s\nCustomer:\n %s\n %-5s%s\n %-5s%s\n\n Sales Person:\n %s\n\n %-6s (%d) %-18s %-20s %-15s %20s",
				invoiceUUID, date, customer, "", customer.getContact(), "", customer.getAddress(), salesPerson,
				"Items: ", getItems().size(), "", "Tax Total", "SubTotal", "Grand Total");
	}
}
