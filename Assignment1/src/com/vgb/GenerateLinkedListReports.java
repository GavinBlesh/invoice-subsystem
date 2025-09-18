package com.vgb;

import java.util.Comparator;
import java.util.List;

/*
 * Gavin Blesh
 * 
 * 2025-05-09
 * 
 * Generates the 3 reports (Invoices by Total, Customer Name, Total of all Invoices) using the linked list data structure. 
 * It is called in InvoiceReports.
 */
public class GenerateLinkedListReports {

	/*
	 * Generates the reports that sort the Invoice by total
	 */
	public static void generateInvoicesByTotal(List<Invoice> invoices) {
		System.out.println(
				"----------------------------------------------------------------------------------------------------");
		System.out.println(" Invoices By Total ");
		System.out.println(
				"----------------------------------------------------------------------------------------------------");
		System.out.printf("\n%-38s %-40s %-15s\n", "Invoice", "Customer", "Total");

		Comparator<Invoice> cmp = new InvoiceTotalComparator();
		LinkedList<Invoice> sortedLinkedList = new LinkedList<>(cmp);

		for (Invoice invoice : invoices) {
			sortedLinkedList.insert(invoice);
		}

		for (int j = 0; j < sortedLinkedList.size(); j++) {
			Invoice invoice = sortedLinkedList.get(j);
			System.out.printf("%-38s %-40s $%-15.2f\n", invoice.getInvoiceUUID(), invoice.getCustomer().getName(),
					invoice.calculateGrandTotal());
		}
		System.out
				.println("-------------------------------------------------------------------------------------------");
	}

	/*
	 * Generates the reports that sort invoices by customer name
	 */
	public static void generateInvoicesByCustomer(List<Invoice> invoices) {
		System.out.println(
				"----------------------------------------------------------------------------------------------------");
		System.out.println(" Invoices By Customer Name ");
		System.out.println(
				"----------------------------------------------------------------------------------------------------");
		System.out.printf("\n%-38s %-40s %-15s\n", "Invoice", "Customer", "Total");

		Comparator<Invoice> cmp = new InvoiceCustomerNameComparator();
		LinkedList<Invoice> sortedLinkedList = new LinkedList<>(cmp);

		for (Invoice invoice : invoices) {
			sortedLinkedList.insert(invoice);
		}

		for (int j = 0; j < sortedLinkedList.size(); j++) {
			Invoice invoice = sortedLinkedList.get(j);
			System.out.printf("%-38s %-40s $%-15.2f\n", invoice.getInvoiceUUID(), invoice.getCustomer().getName(),
					invoice.calculateGrandTotal());
		}
		System.out
				.println("-------------------------------------------------------------------------------------------");
	}

	/*
	 * Generates the reports that sort by the total of all invoices a customer has
	 */
	public static void generateNumCustomerInvoiceTotals(List<Invoice> invoices, List<Company> allCompanies) {
		System.out.println(
				"----------------------------------------------------------------------------------------------------");
		System.out.println(" Customer Invoice Totals ");
		System.out.println(
				"----------------------------------------------------------------------------------------------------");
		System.out.printf("\n%-38s %-40s %-15s\n", "Company", "Count", "Total");

		/*
		 * Iterates through all the companies and invoices and matches them together by
		 * companyUUID
		 */
		for (int i = 0; i < invoices.size(); i++) {
			Invoice invoice = invoices.get(i);
			Company invoiceCompany = invoice.getCustomer();

			for (int j = 0; j < allCompanies.size(); j++) {
				Company company = allCompanies.get(j);
				if (company.getCompanyUUID().equals(invoiceCompany.getCompanyUUID())) {
					company.addInvoice(invoice);
					break;
				}
			}
		}

		Comparator<Company> cmp = new NumInvoicesTotalComparator();
		LinkedList<Company> sortedLinkedList = new LinkedList<>(cmp);

		for (int i = 0; i < allCompanies.size(); i++) {
			sortedLinkedList.insert(allCompanies.get(i));
		}

		for (int i = 0; i < sortedLinkedList.size(); i++) {
			Company company = sortedLinkedList.get(i);
			double total = 0.00;
			int count = 0;

			for (int j = 0; j < company.getInvoices().size(); j++) {
				Invoice invoice = company.getInvoices().get(j);
				total += invoice.calculateGrandTotal();
				count++;
			}
			System.out.printf("%-38s %-40s $%-15.2f\n", company.getName(), count, total);
		}

		System.out.println(
				"----------------------------------------------------------------------------------------------------");
	}
}
