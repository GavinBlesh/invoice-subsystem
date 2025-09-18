package com.vgb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;

/*
 * Gavin Blesh
 * 
 * 2025-05-02
 * 
 * Uses the JDBC connection to the SQL database to load in the InvoiceItems table into Java Objects
 */
public class InvoiceItemsDatabaseLoader {

	/*
	 * Uses loadInvoiceItem to load in all InvoiceItems from the SQL Database
	 */
	public static List<Item> loadInvoiceItems() {
		List<Item> invoiceItems = new ArrayList<>();
		Item item = null;
		Connection conn = null;

		try {
			conn = DriverManager.getConnection(DatabaseInfo.URL, DatabaseInfo.USERNAME, DatabaseInfo.PASSWORD);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		PreparedStatement ps = null;
		ResultSet rs = null;

		String invoiceItemsQuery = "select invoiceItemId, invoiceId, itemId, purchaseType, quantity, amount, hoursRented, startDate, endDate from InvoiceItem";

		try {
			ps = conn.prepareStatement(invoiceItemsQuery);
			rs = ps.executeQuery();

			while (rs.next()) {
				int invoiceItemId = rs.getInt("invoiceItemId");
				item = loadInvoiceItem(invoiceItemId);
				invoiceItems.add(item);
			}

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		try {
			DatabaseConnection.closeConnection(rs, ps, conn);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return invoiceItems;
	}

	/*
	 * Loads in a singular InvoiceItem from the database to update all values for an
	 * Item
	 */
	public static Item loadInvoiceItem(int invoiceItemId) {
		Item item = null;
		Connection conn = null;

		try {
			conn = DriverManager.getConnection(DatabaseInfo.URL, DatabaseInfo.USERNAME, DatabaseInfo.PASSWORD);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		PreparedStatement ps = null;
		ResultSet rs = null;

		String invoiceItemQuery = "select invoiceItemId, invoiceId, itemId, purchaseType, quantity, amount, hoursRented, startDate, endDate from InvoiceItem where invoiceItemId = ?";

		try {
			ps = conn.prepareStatement(invoiceItemQuery);
			ps.setInt(1, invoiceItemId);
			rs = ps.executeQuery();

			while (rs.next()) {
				int itemId = rs.getInt("itemId");
				String purchaseType = rs.getString("purchaseType");

				Item originalItem = ItemsDatabaseLoader.loadItem(itemId);

				if (originalItem instanceof Equipment) {
					Equipment equipment = (Equipment) originalItem;

					if (purchaseType.equals("R")) {
						double hoursRented = rs.getDouble("hoursRented");
						item = new Rental(equipment, hoursRented);
					} else if (purchaseType.equals("L")) {
						LocalDate startDate = LocalDate.parse(rs.getString("startDate"));
						LocalDate endDate = LocalDate.parse(rs.getString("endDate"));
						item = new Lease(equipment, startDate, endDate);
					} else {
						item = equipment;
					}
				} else if (originalItem instanceof Material) {
					int quantity = rs.getInt("quantity");
					Material material = (Material) originalItem;
					item = new Material(material, quantity);
				} else if (originalItem instanceof Contract) {
					double amount = rs.getDouble("amount");
					Contract contract = (Contract) originalItem;
					item = new Contract(contract, amount);
				}

			}
			try {
				DatabaseConnection.closeConnection(rs, ps, conn);
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}

			return item;

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}
