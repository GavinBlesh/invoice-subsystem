package com.vgb;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/*
 * Gavin Blesh
 * 
 * 2025-05-09
 * 
 * Class to represent the Company on invoices
 */
public class Company {

	private UUID companyUUID;
	private String name;
	private Person contact;
	private Address address;
	private List<Invoice> invoices;

	/*
	 * Constructor method for the company
	 */
	public Company(UUID companyUUID, String name, Person contact, Address address) {
		this.companyUUID = companyUUID;
		this.name = name;
		this.contact = contact;
		this.address = address;
		this.invoices = new ArrayList<>();
	}

	/*
	 * Getter method for the company UUID
	 */
	public UUID getCompanyUUID() {
		return companyUUID;
	}

	/*
	 * Getter method for the name
	 */
	public String getName() {
		return name;
	}

	/*
	 * Getter method for the ContactUUID
	 */
	public Person getContact() {
		return contact;
	}

	/*
	 * Getter method for the Address
	 */
	public Address getAddress() {
		return address;
	}

	/*
	 * Getter method for the invoices
	 */
	public List<Invoice> getInvoices() {
		return invoices;
	}

	/*
	 * Method that adds an invoice
	 */
	public void addInvoice(Invoice invoice) {
		this.invoices.add(invoice);
	}

	/*
	 * toString function to properly format the data
	 */
	@Override
	public String toString() {
		return String.format("%s (%s)", name, companyUUID);
	}

}
