package com.vgb;

import java.util.Comparator;

/*
 * Gavin Blesh
 * 
 * 2025-05-09
 *  
 * Compares two Invoices based on the Invoice Total using a comparator
 */
public class InvoiceTotalComparator implements Comparator<Invoice> {

	/*
	 * Compares two invoices using the grand total of both Uses the invoices' UUID
	 * to break any tie breakers
	 */
	public int compare(Invoice a, Invoice b) {

		if (a.calculateGrandTotal() < b.calculateGrandTotal()) {
			return 1;
		} else if (a.calculateGrandTotal() > b.calculateGrandTotal()) {
			return -1;
		} else {
			return a.getInvoiceUUID().compareTo(b.getInvoiceUUID());
		}
	}

}
