package com.vgb;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

/*
 * Gavin Blesh
 * 
 * 2025-05-02
 * 
 * Class that loads the data from Items.csv and reads it into the appropriate variables to create the Items object
 */
public class ItemsCSVLoader {

	/*
	 * Loads the data from Items.csv into the proper areas and is called in
	 * DataConverter
	 */
	public static List<Item> parseItems(String fileName, List<Company> companies) {

		List<Item> items = new ArrayList<>();
		File f = new File(fileName);
		Company servicer = null;

		try {

			Scanner s = new Scanner(f);
			s.nextLine();
			while (s.hasNextLine()) {
				String line = s.nextLine();

				if (!line.isEmpty()) {

					String tokens[] = line.split(",");
					UUID code = UUID.fromString(tokens[0]);
					String type = tokens[1];
					String name = tokens[2];

					if (type.equals("E")) {

						String modelNumber = tokens[3];
						double retailCost = Double.parseDouble(tokens[4]);

						items.add(new Equipment(code, name, modelNumber, retailCost));

					} else if (type.equals("M")) {
						String unit = tokens[3];
						double pricePerUnit = Double.parseDouble(tokens[4]);

						Material material = new Material(code, name, unit, pricePerUnit, 0);
						items.add(material);

					} else if (type.equals("C")) {
						UUID servicerUUID = UUID.fromString(tokens[3]);

						/*
						 * Checks to see if their is a match between Items.csv and Companies.csv for
						 * their UUIDs
						 */
						for (Company company : companies) {
							if (company.getCompanyUUID().equals(servicerUUID)) {
								servicer = company;
							}
						}

						Contract contract = new Contract(code, name, servicer, 0);
						items.add(contract);

					}

				}
			}
			s.close();

		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}

		return items;
	}

}
