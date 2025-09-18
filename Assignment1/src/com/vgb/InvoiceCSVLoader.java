package com.vgb;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

/*
 * Gavin Blesh
 * 
 * 2025-05-09
 * 
 * Loads in the Invoice.csv file
 */
public class InvoiceCSVLoader {

	/*
	 * Parses the data from Invoice.csv and puts it into the correct variables
	 */
	public static List<Invoice> parseInvoices(String fileName, List<Company> companies, List<Person> persons) {

		List<Invoice> invoices = new ArrayList<>();
		File f = new File(fileName);

		try {

			Scanner scanner = new Scanner(f);
			scanner.nextLine();
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();

				if (!line.isEmpty()) {

					String tokens[] = line.split(",");
					UUID invoiceUUID = UUID.fromString(tokens[0]);
					UUID customerUUID = UUID.fromString(tokens[1]);
					UUID salesPersonUUID = UUID.fromString(tokens[2]);
					LocalDate date = LocalDate.parse(tokens[3]);

					Company customer = null;
					Person salesPerson = null;

					for (Company company : companies) {
						if (company.getCompanyUUID().equals(customerUUID)) {
							customer = company;
						}
					}

					for (Person person : persons) {
						if (person.getPersonUUID().equals(salesPersonUUID)) {
							salesPerson = person;
						}
					}

					if (customer != null && salesPerson != null) {
						Invoice invoice = new Invoice(invoiceUUID, customer, salesPerson, date);
						invoices.add(invoice);
					}
				}
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}

		return invoices;
	}
}
