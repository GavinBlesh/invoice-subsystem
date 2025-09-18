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
 * 2025-05-09
 * 
 * Class which takes the data from Companies.csv and reads it into the appropriate variables
 */

public class CompanyCSVLoader {

	/*
	 * Reads in the Companies.csv file as well as the persons list and then parses
	 * the data from Companies.csv and stores it to create the Company object.
	 */
	public static List<Company> parseCompanies(String fileName, List<Person> persons) {

		List<Company> companies = new ArrayList<>();

		File f = new File(fileName);
		Person contactPerson = null;

		try {

			Scanner s = new Scanner(f);
			s.nextLine();
			while (s.hasNextLine()) {
				String line = s.nextLine();

				if (!line.isEmpty()) {

					String tokens[] = line.split(",");
					UUID companyUUID = UUID.fromString(tokens[0]);
					UUID contactUUID = UUID.fromString(tokens[1]);
					String name = tokens[2];
					String street = tokens[3];
					String city = tokens[4];
					String state = tokens[5];
					String zip = tokens[6];

					/*
					 * For Loop that checks the contactUUID against the PersonUUID in Person.csv for
					 * matches
					 */
					for (Person person : persons) {
						if (person.getPersonUUID().equals(contactUUID)) {
							contactPerson = person;
						}
					}

					Address address = new Address(street, city, state, zip);
					Company company = new Company(companyUUID, name, contactPerson, address);
					companies.add(company);

				}
			}
			s.close();

		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}

		return companies;
	}

}
