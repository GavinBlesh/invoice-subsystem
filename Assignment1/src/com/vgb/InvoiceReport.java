package com.vgb;

import java.util.List;

/*
 * Gavin Blesh
 * 
 * 2025-05-09
 * 
 * Class That Loads in the data and calls the generate function for the Reports.
 */

/*
 * Loads in the CSV Data, parses it, and then generates the three reports by calling upon the GenerateReports class
 */
public class InvoiceReport {

	/*
	 * Acts as the main function and calls the needed functions
	 */
	public static void main(String[] args) {

		List<Person> personsDatabase = PersonsDatabaseLoader.loadPersons();

		List<Company> companyDatabase = CompanyDatabaseLoader.loadCompanies();

		List<Item> itemDatabase = ItemsDatabaseLoader.loadItems();

		List<Invoice> invoiceDatabase = InvoiceDatabaseLoader.loadInvoices();

		List<Item> invoiceItemsDatabase = InvoiceItemsDatabaseLoader.loadInvoiceItems();

		GenerateLinkedListReports.generateInvoicesByTotal(invoiceDatabase);
		GenerateLinkedListReports.generateInvoicesByCustomer(invoiceDatabase);
		GenerateLinkedListReports.generateNumCustomerInvoiceTotals(invoiceDatabase, companyDatabase);

	}

}
