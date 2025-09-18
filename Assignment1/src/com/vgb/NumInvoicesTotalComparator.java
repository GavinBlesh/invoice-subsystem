package com.vgb;

import java.util.Comparator;

/*
 * Gavin Blesh
 * 
 * 2025-05-09
 * 
 * Comparator that compares the total of all invoices a company has
 */
public class NumInvoicesTotalComparator implements Comparator<Company> {

	/*
	 * Compares the invoices of company a to the invoices of company b Uses the
	 * companyUUID as a tie breaker for matching totals
	 */
	public int compare(Company a, Company b) {
		double totalA = 0.00;
		double totalB = 0.00;

		for (Invoice invoice : a.getInvoices()) {
			totalA += invoice.calculateGrandTotal();
		}

		for (Invoice invoice : b.getInvoices()) {
			totalB += invoice.calculateGrandTotal();
		}

		if (totalA > totalB) {
			return 1;
		} else if (totalA < totalB) {
			return -1;
		} else {
			return a.getCompanyUUID().compareTo(b.getCompanyUUID());
		}
	}

}
