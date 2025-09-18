package com.vgb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.UUID;

/**
 * Gavin Blesh
 * 
 * 2025-05-02
 * 
 * This is a collection of utility methods that define a general API for
 * interacting with the database supporting this application.
 *
 */
public class InvoiceData {

	/**
	 * Removes all records from all tables in the database.
	 */
	public static void clearDatabase() {
		Connection conn = null;
		PreparedStatement ps = null;

		String deleteInvoiceItemQuery = "delete from InvoiceItem";
		String deleteItemQuery = "delete from Item";
		String deleteInvoiceQuery = "delete from Invoice";
		String deleteEmailQuery = "delete from Email";
		String deleteCompanyQuery = "delete from Company";
		String deleteAddressQuery = "delete from Address";
		String deletePersonQuery = "delete from Person";

		try {
			conn = DriverManager.getConnection(DatabaseInfo.URL, DatabaseInfo.USERNAME, DatabaseInfo.PASSWORD);

			ps = conn.prepareStatement(deleteInvoiceItemQuery);
			ps.executeUpdate();

			ps = conn.prepareStatement(deleteItemQuery);
			ps.executeUpdate();

			ps = conn.prepareStatement(deleteInvoiceQuery);
			ps.executeUpdate();

			ps = conn.prepareStatement(deleteEmailQuery);
			ps.executeUpdate();

			ps = conn.prepareStatement(deleteCompanyQuery);
			ps.executeUpdate();

			ps = conn.prepareStatement(deleteAddressQuery);
			ps.executeUpdate();

			ps = conn.prepareStatement(deletePersonQuery);
			ps.executeUpdate();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		try {
			DatabaseConnection.closeConnectionWithoutRS(ps, conn);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Method to add a person record to the database with the provided data.
	 *
	 * @param personUuid
	 * @param firstName
	 * @param lastName
	 * @param phone
	 */
	public static void addPerson(UUID personUuid, String firstName, String lastName, String phone) {

		Person checkPerson = PersonsDatabaseLoader.loadPerson(InvoiceData.findPerson(personUuid));
		if (checkPerson != null) {
			return;
		}

		Connection conn = null;
		PreparedStatement ps = null;

		String addPersonQuery = "insert into Person (personUUID, firstName, lastName, phoneNumber) values (?,?,?,?)";

		try {
			conn = DriverManager.getConnection(DatabaseInfo.URL, DatabaseInfo.USERNAME, DatabaseInfo.PASSWORD);

			ps = conn.prepareStatement(addPersonQuery);

			ps.setString(1, personUuid.toString());
			ps.setString(2, lastName);
			ps.setString(3, firstName);
			ps.setString(4, phone);
			ps.executeUpdate();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		try {
			DatabaseConnection.closeConnectionWithoutRS(ps, conn);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Adds an email record corresponding person record corresponding to the
	 * provided <code>personUuid</code>
	 *
	 * @param personUuid
	 * @param email
	 */
	public static void addEmail(UUID personUuid, String email) {
		Connection conn = null;
		PreparedStatement ps = null;

		String addEmailQuery = "insert into Email (personId, email) values (?, ?)";

		try {
			conn = DriverManager.getConnection(DatabaseInfo.URL, DatabaseInfo.USERNAME, DatabaseInfo.PASSWORD);
			ps = conn.prepareStatement(addEmailQuery);
			ps.setInt(1, InvoiceData.findPerson(personUuid));
			ps.setString(2, email);
			ps.executeUpdate();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		try {
			DatabaseConnection.closeConnectionWithoutRS(ps, conn);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Adds a company record to the database with the primary contact person
	 * identified by the given code.
	 *
	 * @param companyUuid
	 * @param name
	 * @param contactUuid
	 * @param street
	 * @param city
	 * @param state
	 * @param zip
	 */
	public static void addCompany(UUID companyUuid, UUID contactUuid, String name, String street, String city,
			String state, String zip) {

		Company checkCompany = CompanyDatabaseLoader.loadCompany(InvoiceData.findCompany(companyUuid));
		if (checkCompany != null) {
			return;
		}

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		String addAddressQuery = "insert into Address (street, city, state, zipCode) values (?, ?, ?, ?)";
		String addCompanyQuery = "insert into Company (companyUUID, name, contactId, addressId) values (?, ?, ?, ?)";

		try {
			conn = DriverManager.getConnection(DatabaseInfo.URL, DatabaseInfo.USERNAME, DatabaseInfo.PASSWORD);

			ps = conn.prepareStatement(addAddressQuery, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, street);
			ps.setString(2, city);
			ps.setString(3, state);
			ps.setString(4, zip);
			ps.executeUpdate();
			rs = ps.getGeneratedKeys();
			int addressId = 0;

			while (rs.next()) {
				addressId = rs.getInt(1);

			}
			DatabaseConnection.closeRsPs(ps, rs);

			ps = conn.prepareStatement(addCompanyQuery);
			ps.setString(1, companyUuid.toString());
			ps.setString(2, name);
			ps.setInt(3, InvoiceData.findPerson(contactUuid));
			ps.setInt(4, addressId);
			ps.executeUpdate();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		try {
			DatabaseConnection.closeConnectionWithoutRS(ps, conn);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

	/**
	 * Adds an equipment record to the database of the given values.
	 *
	 * @param equipmentUuid
	 * @param name
	 * @param modelNumber
	 * @param retailPrice
	 */
	public static void addEquipment(UUID equipmentUuid, String name, String modelNumber, double retailPrice) {

		Item checkEquipment = ItemsDatabaseLoader.loadItem(InvoiceData.findItem(equipmentUuid));
		if (checkEquipment != null) {
			return;
		}

		Connection conn = null;
		PreparedStatement ps = null;

		String equipmentQuery = "insert into Item (itemUUID, name, itemType, modelNumber, retailCost) values (?,?,?,?,?)";

		try {
			conn = DriverManager.getConnection(DatabaseInfo.URL, DatabaseInfo.USERNAME, DatabaseInfo.PASSWORD);
			ps = conn.prepareStatement(equipmentQuery);
			ps.setString(1, equipmentUuid.toString());
			ps.setString(2, name);
			ps.setString(3, "E");
			ps.setString(4, modelNumber);
			ps.setDouble(5, retailPrice);
			ps.executeUpdate();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		try {
			DatabaseConnection.closeConnectionWithoutRS(ps, conn);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

	/**
	 * Adds an material record to the database of the given values.
	 *
	 * @param materialUuid
	 * @param name
	 * @param unit
	 * @param pricePerUnit
	 */
	public static void addMaterial(UUID materialUuid, String name, String unit, double pricePerUnit) {

		Item checkMaterial = ItemsDatabaseLoader.loadItem(InvoiceData.findItem(materialUuid));
		if (checkMaterial != null) {
			return;
		}

		Connection conn = null;
		PreparedStatement ps = null;

		String materialQuery = "insert into Item (itemUUID, name, itemType, unit, pricePerUnit) values (?,?,?,?,?)";

		try {
			conn = DriverManager.getConnection(DatabaseInfo.URL, DatabaseInfo.USERNAME, DatabaseInfo.PASSWORD);
			ps = conn.prepareStatement(materialQuery);
			ps.setString(1, materialUuid.toString());
			ps.setString(2, name);
			ps.setString(3, "M");
			ps.setString(4, unit);
			ps.setDouble(5, pricePerUnit);
			ps.executeUpdate();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		try {
			DatabaseConnection.closeConnectionWithoutRS(ps, conn);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

	/**
	 * Adds an contract record to the database of the given values.
	 *
	 * @param contractUuid
	 * @param name
	 * @param unit
	 * @param pricePerUnit
	 */
	public static void addContract(UUID contractUuid, String name, UUID servicerUuid) {

		Item checkContract = ItemsDatabaseLoader.loadItem(InvoiceData.findItem(contractUuid));
		if (checkContract != null) {
			return;
		}

		Connection conn = null;
		PreparedStatement ps = null;

		String contractQuery = "insert into Item (itemUUID, name, itemType, servicerId) values (?,?,?,?)";

		try {
			conn = DriverManager.getConnection(DatabaseInfo.URL, DatabaseInfo.USERNAME, DatabaseInfo.PASSWORD);
			ps = conn.prepareStatement(contractQuery);
			ps.setString(1, contractUuid.toString());
			ps.setString(2, name);
			ps.setString(3, "C");
			ps.setInt(4, InvoiceData.findCompany(servicerUuid));
			ps.executeUpdate();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		try {
			DatabaseConnection.closeConnectionWithoutRS(ps, conn);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

	/**
	 * Adds an Invoice record to the database with the given data.
	 *
	 * @param invoiceUuid
	 * @param customerUuid
	 * @param salesPersonUuid
	 * @param date
	 */
	public static void addInvoice(UUID invoiceUuid, UUID customerUuid, UUID salesPersonUuid, LocalDate date) {

		Invoice checkInvoice = InvoiceDatabaseLoader.loadInvoice(InvoiceData.findInvoice(invoiceUuid));
		if (checkInvoice != null) {
			return;
		}

		Connection conn = null;
		PreparedStatement ps = null;

		String invoiceQuery = "insert into Invoice (invoiceUUID, date, customerId, salesPersonId) values (?,?,?,?)";

		try {
			conn = DriverManager.getConnection(DatabaseInfo.URL, DatabaseInfo.USERNAME, DatabaseInfo.PASSWORD);
			ps = conn.prepareStatement(invoiceQuery);
			ps.setString(1, invoiceUuid.toString());
			ps.setString(2, date.toString());
			ps.setInt(3, InvoiceData.findCompany(customerUuid));
			ps.setInt(4, InvoiceData.findPerson(salesPersonUuid));
			ps.executeUpdate();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		try {
			DatabaseConnection.closeConnectionWithoutRS(ps, conn);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

	/**
	 * Adds an Equipment purchase record to the given invoice.
	 *
	 * @param invoiceUuid
	 * @param itemUuid
	 */
	public static void addEquipmentPurchaseToInvoice(UUID invoiceUuid, UUID itemUuid) {

		if ((InvoiceData.findInvoice(invoiceUuid) == 0) || (InvoiceData.findItem(itemUuid) == 0)) {
			return;
		}

		Connection conn = null;
		PreparedStatement ps = null;

		String equipmentPurchaseQuery = "insert into InvoiceItem (invoiceId, itemId, purchaseType) values (?,?,?)";

		try {
			conn = DriverManager.getConnection(DatabaseInfo.URL, DatabaseInfo.USERNAME, DatabaseInfo.PASSWORD);
			ps = conn.prepareStatement(equipmentPurchaseQuery);
			ps.setInt(1, InvoiceData.findInvoice(invoiceUuid));
			ps.setInt(2, InvoiceData.findItem(itemUuid));
			ps.setString(3, "P");
			ps.executeUpdate();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		try {
			DatabaseConnection.closeConnectionWithoutRS(ps, conn);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

	/**
	 * Adds an Equipment lease record to the given invoice.
	 *
	 * @param invoiceUuid
	 * @param itemUuid
	 * @param start
	 * @param end
	 */
	public static void addEquipmentLeaseToInvoice(UUID invoiceUuid, UUID itemUuid, LocalDate start, LocalDate end) {

		if ((InvoiceData.findInvoice(invoiceUuid) == 0) || (InvoiceData.findItem(itemUuid) == 0)) {
			return;
		}

		Connection conn = null;
		PreparedStatement ps = null;

		String equipmentLeaseQuery = "insert into InvoiceItem (invoiceId, itemId, purchaseType, startDate, endDate) values (?,?,?,?,?)";

		try {
			conn = DriverManager.getConnection(DatabaseInfo.URL, DatabaseInfo.USERNAME, DatabaseInfo.PASSWORD);
			ps = conn.prepareStatement(equipmentLeaseQuery);
			ps.setInt(1, InvoiceData.findInvoice(invoiceUuid));
			ps.setInt(2, InvoiceData.findItem(itemUuid));
			ps.setString(3, "L");
			ps.setString(4, start.toString());
			ps.setString(5, end.toString());
			ps.executeUpdate();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		try {
			DatabaseConnection.closeConnectionWithoutRS(ps, conn);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

	/**
	 * Adds an Equipment rental record to the given invoice.
	 *
	 * @param invoiceUuid
	 * @param itemUuid
	 * @param numberOfHours
	 */
	public static void addEquipmentRentalToInvoice(UUID invoiceUuid, UUID itemUuid, double numberOfHours) {

		if ((InvoiceData.findInvoice(invoiceUuid) == 0) || (InvoiceData.findItem(itemUuid) == 0)) {
			return;
		}

		Connection conn = null;
		PreparedStatement ps = null;

		String equipmentRentalQuery = "insert into InvoiceItem (invoiceId, itemId, purchaseType, hoursRented) values (?,?,?,?)";

		try {
			conn = DriverManager.getConnection(DatabaseInfo.URL, DatabaseInfo.USERNAME, DatabaseInfo.PASSWORD);
			ps = conn.prepareStatement(equipmentRentalQuery);
			ps.setInt(1, InvoiceData.findInvoice(invoiceUuid));
			ps.setInt(2, InvoiceData.findItem(itemUuid));
			ps.setString(3, "R");
			ps.setDouble(4, numberOfHours);
			ps.executeUpdate();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		try {
			DatabaseConnection.closeConnectionWithoutRS(ps, conn);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

	/**
	 * Adds a material record to the given invoice.
	 *
	 * @param invoiceUuid
	 * @param itemUuid
	 * @param numberOfUnits
	 */
	public static void addMaterialToInvoice(UUID invoiceUuid, UUID itemUuid, int numberOfUnits) {

		if ((InvoiceData.findInvoice(invoiceUuid) == 0) || (InvoiceData.findItem(itemUuid) == 0)) {
			return;
		}

		Connection conn = null;
		PreparedStatement ps = null;

		String materialInvoiceQuery = "insert into InvoiceItem (invoiceId, itemId, quantity) values (?, ?, ?)";

		try {
			conn = DriverManager.getConnection(DatabaseInfo.URL, DatabaseInfo.USERNAME, DatabaseInfo.PASSWORD);
			ps = conn.prepareStatement(materialInvoiceQuery);
			ps.setInt(1, InvoiceData.findInvoice(invoiceUuid));
			ps.setInt(2, InvoiceData.findItem(itemUuid));
			ps.setInt(3, numberOfUnits);
			ps.executeUpdate();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		try {
			DatabaseConnection.closeConnectionWithoutRS(ps, conn);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

	/**
	 * Adds a contract record to the given invoice.
	 *
	 * @param invoiceUuid
	 * @param itemUuid
	 * @param amount
	 */
	public static void addContractToInvoice(UUID invoiceUuid, UUID itemUuid, double amount) {

		if ((InvoiceData.findInvoice(invoiceUuid) == 0) || (InvoiceData.findItem(itemUuid) == 0)) {
			return;
		}

		Connection conn = null;
		PreparedStatement ps = null;

		String contractInvoiceQuery = "insert into InvoiceItem (invoiceId, itemId, amount) values (?,?,?)";

		try {
			conn = DriverManager.getConnection(DatabaseInfo.URL, DatabaseInfo.USERNAME, DatabaseInfo.PASSWORD);
			ps = conn.prepareStatement(contractInvoiceQuery);
			ps.setInt(1, InvoiceData.findInvoice(invoiceUuid));
			ps.setInt(2, InvoiceData.findItem(itemUuid));
			ps.setDouble(3, amount);
			ps.executeUpdate();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		try {
			DatabaseConnection.closeConnectionWithoutRS(ps, conn);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

	/*
	 * Finds the invoiceId of an Invoice based off the invoiceUUID
	 */
	public static int findInvoice(UUID invoiceUuid) {
		PreparedStatement ps = null;
		Connection conn = null;
		ResultSet rs = null;

		String invoiceIdQuery = "select invoiceId from Invoice where invoiceUUID = ?";

		try {
			conn = DriverManager.getConnection(DatabaseInfo.URL, DatabaseInfo.USERNAME, DatabaseInfo.PASSWORD);
			ps = conn.prepareStatement(invoiceIdQuery);
			ps.setString(1, invoiceUuid.toString());
			rs = ps.executeQuery();
			int invoiceId = 0;

			while (rs.next()) {
				invoiceId = rs.getInt("invoiceId");

			}
			DatabaseConnection.closeConnection(rs, ps, conn);
			return invoiceId;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

	/*
	 * Finds the itemId of an Item based off the itemUUID
	 */
	public static int findItem(UUID itemUuid) {
		PreparedStatement ps = null;
		Connection conn = null;
		ResultSet rs = null;

		String itemIdQuery = "select itemId from Item where itemUUID = ?";

		try {
			conn = DriverManager.getConnection(DatabaseInfo.URL, DatabaseInfo.USERNAME, DatabaseInfo.PASSWORD);
			ps = conn.prepareStatement(itemIdQuery);
			ps.setString(1, itemUuid.toString());
			rs = ps.executeQuery();
			int itemId = 0;

			while (rs.next()) {
				itemId = rs.getInt("itemId");
			}
			DatabaseConnection.closeConnection(rs, ps, conn);
			return itemId;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

	/*
	 * Finds the personId of a Person based off the personUUID
	 */
	public static int findPerson(UUID personUuid) {
		PreparedStatement ps = null;
		Connection conn = null;
		ResultSet rs = null;

		String findPersonQuery = "select personId from Person where personUUID = ?";
		try {
			conn = DriverManager.getConnection(DatabaseInfo.URL, DatabaseInfo.USERNAME, DatabaseInfo.PASSWORD);
			ps = conn.prepareStatement(findPersonQuery);
			ps.setString(1, personUuid.toString());
			rs = ps.executeQuery();
			int personId = 0;

			while (rs.next()) {
				personId = rs.getInt("personId");
			}
			DatabaseConnection.closeConnection(rs, ps, conn);
			return personId;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/*
	 * Finds the companyId based off the companyUUID
	 */
	public static int findCompany(UUID companyUuid) {
		PreparedStatement ps = null;
		Connection conn = null;
		ResultSet rs = null;

		String findCompanyQuery = "select companyId from Company where companyUUID = ?";

		try {
			conn = DriverManager.getConnection(DatabaseInfo.URL, DatabaseInfo.USERNAME, DatabaseInfo.PASSWORD);
			ps = conn.prepareStatement(findCompanyQuery);
			ps.setString(1, companyUuid.toString());
			rs = ps.executeQuery();
			int companyId = 0;

			while (rs.next()) {
				companyId = rs.getInt("companyId");
			}
			DatabaseConnection.closeConnection(rs, ps, conn);
			return companyId;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}