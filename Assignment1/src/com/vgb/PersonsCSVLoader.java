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
 * Reads the data from Person.csv and loads it into the appropriate variables
 */
public class PersonsCSVLoader {

	/*
	 * Used in DataConverter to read in the contents of the Person file and creates
	 * the Person object
	 */
	public static List<Person> parsePerson(String fileName) {
		List<Person> persons = new ArrayList<>();
		File f = new File(fileName);

		try {

			Scanner s = new Scanner(f);
			s.nextLine();

			while (s.hasNextLine()) {
				String line = s.nextLine();
				/*
				 * Replaces all the blank space at the end with nothing so it is not accidently
				 * read as input
				 */
				line = line.replace(" ", "");

				if (!line.isEmpty()) {
					String tokens[] = line.split(",");
					UUID uuid = UUID.fromString(tokens[0]);
					String firstName = tokens[1];
					String lastName = tokens[2];
					String phoneNumber = tokens[3];
					Person person = new Person(uuid, firstName, lastName, phoneNumber);

					/*
					 * Adds any optional emails to the Person object
					 */
					for (int i = 4; i < tokens.length; i++) {
						person.addEmail(tokens[i]);
					}

					persons.add(person);

				}

			}
			s.close();

		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);

		}

		return persons;
	}
}
