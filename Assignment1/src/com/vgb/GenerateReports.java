package com.vgb;

import java.util.List;
import java.util.UUID;

/*
 * Gavin Blesh
 * 
 * 2025-05-09
 * 
 * Generates the 3 Different Reports
 */
public class GenerateReports {

	/*
	 * Generates the Summary Report of Invoices
	 */
	public static void generateSummaryReport(List<Invoice> invoices) {

		System.out.println(
				"----------------------------------------------------------------------------------------------------");
		System.out.println(" Summary Report - By Total");
		System.out.println(
				"----------------------------------------------------------------------------------------------------");
		System.out.printf("\n%-40s %-20s %-6s %-12s %-12s\n", "InvoiceUUID", "Customer", "Items", "Subtotal", "Tax");

		double totalTax = 0;
		double totalTotal = 0;
		int totalItems = 0;

		for (Invoice invoice : invoices) {

			UUID UUID = invoice.getInvoiceUUID();
			String customer = invoice.getCustomer().getName();
			int numItems = invoice.getItems().size();
			double tax = invoice.calculateTaxTotal();
			double Total = invoice.calculateGrandTotal();

			System.out.printf("\n%-40s %-20s %-6d $%-12.2f $%-12.2f", UUID, customer, numItems, Total, tax);

			totalTax += tax;
			totalTotal += Total;
			totalItems += numItems;

		}
		System.out.println(
				"\n-----------------------------------------------------------------------------------------------------");
		System.out.printf("\n%-40s %-20s %-6d $%-15.2f $%-15.2f\n", "", "", totalItems, totalTotal, totalTax);

	}

	/*
	 * Generates the Company Report of Invoices
	 */
	public static void generateCustomerReport(List<Invoice> invoices, List<Company> allCompanies) {

		System.out.println("--------------------------------------------");
		System.out.println("Company Invoice Summary Report");
		System.out.println("--------------------------------------------");
		System.out.printf("\n%-10s %-15s %-15s\n", "Company", "Invoice Count", "Grand Total");

		int totalInvoices = 0;
		double totalGrandTotal = 0.0;

		/*
		 * Goes through the company list and matches customer and company off the
		 * invoice data
		 */
		for (Company company : allCompanies) {
			int invoiceCount = 0;
			double companyTotal = 0.0;

			for (Invoice aInvoice : invoices) {
				if (aInvoice.getCustomer().getCompanyUUID().equals(company.getCompanyUUID())) {
					invoiceCount++;
					companyTotal += aInvoice.calculateGrandTotal();
				}
			}

			System.out.printf("\n%-20s %-10d $%-15.2f", company.getName(), invoiceCount, companyTotal);

			totalInvoices += invoiceCount;
			totalGrandTotal += companyTotal;
		}

		System.out.println("\n------------------------------------------------------------------");

		System.out.printf("%-20s %-10d  $%-20.2f", "", totalInvoices, totalGrandTotal);

		System.out.println("\n------------------------------------------------------------------");
		System.out.println("\n");
	}

	/*
	 * Generates the Individual Invoice Report
	 */
	public static void generateIndividualReport(List<Invoice> invoices) {
		System.out.println("---------------------------------------");
		System.out.println("Individual Reports");
		System.out.println("---------------------------------------");

		for (Invoice invoice : invoices) {
			System.out.println(invoice.toString());
			System.out.println(
					"---------------------------------------------------------------------------------------------");

			/*
			 * Goes through all the items in the invoice and prints them out using toString
			 * function
			 */
			for (Item item : invoice.getItems()) {
				System.out.println("Item: \n" + item);
			}

			System.out.println(
					"------------------------------------------------------------------------------------------------");
			System.out.printf("%31s $%-20.2f $%-20.2f $%-20.2f\n", "", invoice.calculateTaxTotal(),
					invoice.calculateTotal(), invoice.calculateGrandTotal());
			System.out.println(
					"=================================================================================================");
		}

	}
}
