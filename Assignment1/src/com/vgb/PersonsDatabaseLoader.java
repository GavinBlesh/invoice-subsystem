package com.vgb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/*
 * Gavin Blesh
 * 
 * 2025-05-02
 * 
 * Uses the JDBC Connection the SQL database to pull data from it and insert it into Java Objects.
 */
public class PersonsDatabaseLoader {

	/*
	 * Uses the loadPerson() function to load in all people from the SQL database
	 */
	public static List<Person> loadPersons() {

		List<Person> persons = new ArrayList<>();
		Person person = null;
		Connection conn = null;

		try {
			conn = DriverManager.getConnection(DatabaseInfo.URL, DatabaseInfo.USERNAME, DatabaseInfo.PASSWORD);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		String personQuery = "select personId from Person";
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = conn.prepareStatement(personQuery);
			rs = ps.executeQuery();

			while (rs.next()) {
				int personId = rs.getInt("personId");
				person = loadPerson(personId);
				persons.add(person);
			}

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		try {
			DatabaseConnection.closeConnection(rs, ps, conn);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return persons;
	}

	/*
	 * uses emailId and personId to load in a singular Person and their associated
	 * data from the SQL database
	 */
	public static Person loadPerson(int personId) {
		Person person = null;
		Connection conn = null;

		try {
			conn = DriverManager.getConnection(DatabaseInfo.URL, DatabaseInfo.USERNAME, DatabaseInfo.PASSWORD);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		PreparedStatement ps = null;
		ResultSet rs = null;
		PreparedStatement psEmail = null;
		ResultSet rsEmail = null;
		String personQuery = "select personUUID, firstName, lastName, phoneNumber from Person where personId = ?";
		String emailQuery = "Select email from Email where personId = ?";

		try {
			ps = conn.prepareStatement(personQuery);
			ps.setInt(1, personId);
			rs = ps.executeQuery();

			while (rs.next()) {
				UUID personUUID = UUID.fromString(rs.getString("personUUID"));
				String firstName = rs.getString("firstName");
				String lastName = rs.getString("lastName");
				String phoneNumber = rs.getString("phoneNumber");

				person = new Person(personUUID, lastName, firstName, phoneNumber);

				psEmail = conn.prepareStatement(emailQuery);
				psEmail.setInt(1, personId);
				rsEmail = psEmail.executeQuery();

				while (rsEmail.next()) {
					String email = rsEmail.getString("email");
					person.addEmail(email);
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		try {
			DatabaseConnection.closeConnection(rs, ps, conn);
			if (rsEmail != null && !rsEmail.isClosed()) {
				rsEmail.close();
			}
			if (psEmail != null && !psEmail.isClosed()) {
				psEmail.close();
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return person;
	}
}
