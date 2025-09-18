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
 * 2025-05-09
 * 
 * Uses JDBC to connect to SQL Database and fill in data for Java objects with data inserted into the SQL Database
 */

public class CompanyDatabaseLoader {

	/*
	 * Uses the loadCompany() function to load in all companies in the SQL database
	 */
	public static List<Company> loadCompanies() {

		List<Company> companies = new ArrayList<>();
		Company company = null;
		Connection conn = null;

		try {
			conn = DriverManager.getConnection(DatabaseInfo.URL, DatabaseInfo.USERNAME, DatabaseInfo.PASSWORD);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		String companyQuery = "select companyId from Company";
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = conn.prepareStatement(companyQuery);
			rs = ps.executeQuery();

			while (rs.next()) {
				int companyId = rs.getInt("companyId");
				company = loadCompany(companyId);
				companies.add(company);
			}

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		try {
			DatabaseConnection.closeConnection(rs, ps, conn);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return companies;
	}

	/*
	 * Loads in a singular Company and its associated data via its companyId and
	 * addressId
	 */
	public static Company loadCompany(int companyId) {

		Company company = null;
		Address address = null;
		Connection conn = null;

		try {
			conn = DriverManager.getConnection(DatabaseInfo.URL, DatabaseInfo.USERNAME, DatabaseInfo.PASSWORD);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		String companyQuery = "select companyUUID, name, contactId, addressId from Company where companyId = ?";
		String addressQuery = "select street, city, state, zipCode from Address where addressId = ?";
		PreparedStatement ps = null;
		ResultSet rs = null;
		PreparedStatement psAddress = null;
		ResultSet rsAddress = null;

		try {
			ps = conn.prepareStatement(companyQuery);
			ps.setInt(1, companyId);
			rs = ps.executeQuery();

			while (rs.next()) {
				UUID companyUUID = UUID.fromString(rs.getString("companyUUID"));
				String name = rs.getString("name");
				int contactId = rs.getInt("contactId");
				int addressId = rs.getInt("addressId");

				Person contact = PersonsDatabaseLoader.loadPerson(contactId);

				psAddress = conn.prepareStatement(addressQuery);
				psAddress.setInt(1, addressId);
				rsAddress = psAddress.executeQuery();

				while (rsAddress.next()) {
					String street = rsAddress.getString("street");
					String city = rsAddress.getString("city");
					String state = rsAddress.getString("state");
					String zipCode = rsAddress.getString("zipCode");

					address = new Address(street, city, state, zipCode);
				}

				company = new Company(companyUUID, name, contact, address);
			}

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		try {
			DatabaseConnection.closeConnection(rs, ps, conn);
			if (rsAddress != null && rsAddress.isClosed()) {
				rsAddress.close();
			}
			if (psAddress != null && !psAddress.isClosed()) {
				psAddress.close();
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return company;
	}

}
