package com.vgb;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

/*
 * Gavin Blesh
 * 
 * 2025-05-02
 * 
 * Class that loads in InvoiceItems.csv
 */
public class InvoiceItemsCSVLoader {

	/*
	 * Parses the data from InvoiceItems.csv and loads into the proper data values
	 */
	public static List<Item> parseInvoiceItems(String fileName, List<Invoice> invoices, List<Item> items) {

		File f = new File(fileName);

		try {

			Scanner scanner = new Scanner(f);
			scanner.nextLine();
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();

				if (!line.isEmpty()) {
					String tokens[] = line.split(",");

					UUID invoiceUUID = UUID.fromString(tokens[0]);
					UUID itemUUID = UUID.fromString(tokens[1]);

					Invoice invoice = null;

					/*
					 * Matches the UUID to another invoice UUID in the invoices list
					 */
					for (Invoice inv : invoices) {
						if (inv.getInvoiceUUID().equals(invoiceUUID)) {
							invoice = inv;

						}
					}

					/*
					 * Matches the UUID to an item UUID in the Items list
					 */
					Item item = null;
					for (Item itm : items) {
						if (itm.getCode().equals(itemUUID)) {
							item = itm;

						}
					}

					/*
					 * Checks if invoice and item has values then determines what subclass of Item
					 * it is based on InvoiceItems.csv
					 */
					if (invoice != null && item != null) {

						if (item instanceof Equipment) {
							Equipment equipment = (Equipment) item;
							String type = tokens[2];

							if (type.equals("P")) {
								invoice.addItem(equipment);

							} else if (type.equals("L")) {

								LocalDate startDate = LocalDate.parse(tokens[3]);
								LocalDate endDate = LocalDate.parse(tokens[4]);

								Lease lease = new Lease(equipment, startDate, endDate);

								invoice.addItem(lease);

							} else if (type.equals("R")) {
								double hoursRented = Double.parseDouble(tokens[3]);
								Rental rental = new Rental(equipment, hoursRented);
								invoice.addItem(rental);

							}

						} else if (item instanceof Material) {
							int quantity = Integer.parseInt(tokens[2]);
							Material material = new Material((Material) item, quantity);
							invoice.addItem(material);

						} else if (item instanceof Contract) {
							double amount = Double.parseDouble(tokens[2]);
							Contract contract = new Contract((Contract) item, amount);
							invoice.addItem(contract);

						}

					}
				}
			}
			scanner.close();

		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
		return items;

	}

}
