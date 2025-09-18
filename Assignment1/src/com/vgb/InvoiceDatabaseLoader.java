package com.vgb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/*
 * Gavin Blesh
 * 
 * 2025-05-02
 * 
 * Uses the JDBC connection to the SQL database to load in Invoice data from it into Java Objects
 */
public class InvoiceDatabaseLoader {

	/*
	 * Uses loadInvoice() to load in all Invoices from the SQL database
	 */
	public static List<Invoice> loadInvoices() {
		List<Invoice> invoices = new ArrayList<>();
		Invoice invoice = null;
		Connection conn = null;

		try {
			conn = DriverManager.getConnection(DatabaseInfo.URL, DatabaseInfo.USERNAME, DatabaseInfo.PASSWORD);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		String invoiceQuery = "select invoiceId from Invoice";
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = conn.prepareStatement(invoiceQuery);
			rs = ps.executeQuery();

			while (rs.next()) {
				int invoiceId = rs.getInt("invoiceId");
				invoice = loadInvoice(invoiceId);
				invoices.add(invoice);
			}

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		try {
			DatabaseConnection.closeConnection(rs, ps, conn);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return invoices;
	}

	/*
	 * Uses invoiceId to load in info for Invoice from the Invoice table and then
	 * uses it to load in all items from the InvoiceItems table as well.
	 */
	public static Invoice loadInvoice(int invoiceId) {
		Invoice invoice = null;
		Connection conn = null;

		try {
			conn = DriverManager.getConnection(DatabaseInfo.URL, DatabaseInfo.USERNAME, DatabaseInfo.PASSWORD);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		PreparedStatement ps = null;
		PreparedStatement psItems = null;
		ResultSet rs = null;
		ResultSet rsItems = null;

		String invoiceItemsQuery = "select invoiceItemId from InvoiceItem where invoiceId = ?";
		String invoiceQuery = "select invoiceId, invoiceUUID, date, customerId, salesPersonId from Invoice where invoiceId = ?";

		try {
			ps = conn.prepareStatement(invoiceQuery);
			ps.setInt(1, invoiceId);
			rs = ps.executeQuery();

			while (rs.next()) {
				UUID invoiceUUID = UUID.fromString(rs.getString("invoiceUUID"));
				LocalDate date = LocalDate.parse(rs.getString("date"));
				int customerId = rs.getInt("customerId");
				int salesPersonId = rs.getInt("salesPersonId");

				Company customer = CompanyDatabaseLoader.loadCompany(customerId);
				Person salesPerson = PersonsDatabaseLoader.loadPerson(salesPersonId);

				invoice = new Invoice(invoiceUUID, customer, salesPerson, date);

				/*
				 * Loads in all items associated with that invoiceId
				 */
				psItems = conn.prepareStatement(invoiceItemsQuery);
				psItems.setInt(1, invoiceId);
				rsItems = psItems.executeQuery();

				while (rsItems.next()) {
					int invoiceItemId = rsItems.getInt("invoiceItemId");
					Item item = InvoiceItemsDatabaseLoader.loadInvoiceItem(invoiceItemId);
					invoice.addItem(item);
				}

			}

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		try {
			DatabaseConnection.closeConnection(rs, ps, conn);
			if (rsItems != null && !rsItems.isClosed()) {
				rsItems.close();
			}
			if (psItems != null && !psItems.isClosed()) {
				psItems.close();
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return invoice;
	}
}
