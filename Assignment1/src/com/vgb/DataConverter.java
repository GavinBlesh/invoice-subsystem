package com.vgb;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

/*
 * Gavin Blesh
 * 
 * 2025-05-09
 * 
 * Main class that calls the functions to run the program, as well as the GSON/JSON library to convert the data
 */

public class DataConverter {

	/*
	 * Adapter for JSON that allows it to take in LocalDate variables
	 */
	private static final class LocalDateAdapter extends TypeAdapter<LocalDate> {
		/*
		 * Gives JSON the ability to write for LocalDate variables
		 */
		@Override
		public void write(final JsonWriter jsonWriter, final LocalDate localDate) throws IOException {
			jsonWriter.value(localDate.toString());
		}

		/*
		 * Gives JSON the ability to read for LocalDate variables
		 */
		@Override
		public LocalDate read(final JsonReader jsonReader) throws IOException {
			return LocalDate.parse(jsonReader.nextString());
		}
	}

	/*
	 * GSONBuilder that uses the JSON LocalDate Adapter to create a gson builder
	 */
	public static final Gson GSON = new GsonBuilder()
			.registerTypeAdapter(LocalDate.class, new LocalDateAdapter().nullSafe()).setPrettyPrinting().create();

	/*
	 * Calls the functions and classes used in the program to run and convert the
	 * data
	 */
	public static void main(String[] args) {

		List<Person> persons = PersonsCSVLoader.parsePerson("./data/Persons.csv");

		List<Company> companies = CompanyCSVLoader.parseCompanies("./data/Companies.csv", persons);

		List<Item> items = ItemsCSVLoader.parseItems("./data/Items.csv", companies);

		String personsJson = "persons:" + GSON.toJson(persons);
		String companiesJson = "companies:" + GSON.toJson(companies);
		String itemsJson = "items:" + GSON.toJson(items);

		JSONFileWriter.fileWriter("./data/Persons.json", personsJson);
		JSONFileWriter.fileWriter("./data/Companies.json", companiesJson);
		JSONFileWriter.fileWriter("./data/Items.json", itemsJson);

	}
}