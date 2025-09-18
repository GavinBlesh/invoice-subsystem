package com.vgb;

import java.util.Comparator;

/*
 * Gavin Blesh
 * 
 * 2025-05-09
 * 
 *  Comparator that compares the customer names on an Invoice
 */
public class InvoiceCustomerNameComparator implements Comparator<Invoice> {

	/*
	 * Compares a customer name on an invoice to another Uses the customer's UUID to
	 * break any tie breakers
	 */
	public int compare(Invoice a, Invoice b) {
		int cmp = a.getCustomer().getName().compareTo(b.getCustomer().getName());

		if (cmp != 0) {
			return cmp;
		} else {
			return a.getInvoiceUUID().compareTo(b.getInvoiceUUID());
		}
	}

}
