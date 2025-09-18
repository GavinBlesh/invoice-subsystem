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
 * Uses the JDBC connection to the SQL database to load the data from the Invoice table into its respective Java Object
 */
public class ItemsDatabaseLoader {

	/*
	 * Uses loadItem() to load in all Items
	 */
	public static List<Item> loadItems() {
		List<Item> items = new ArrayList<>();
		Item item = null;
		Connection conn = null;

		try {
			conn = DriverManager.getConnection(DatabaseInfo.URL, DatabaseInfo.USERNAME, DatabaseInfo.PASSWORD);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		String itemQuery = "select itemId from Item";
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = conn.prepareStatement(itemQuery);
			rs = ps.executeQuery();

			while (rs.next()) {
				int itemId = rs.getInt("itemId");
				item = loadItem(itemId);
				items.add(item);
			}

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		try {
			DatabaseConnection.closeConnection(rs, ps, conn);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return items;
	}

	/*
	 * Uses itemId to load in a singular Item and all of its associated data
	 */
	public static Item loadItem(int itemId) {

		Item item = null;
		Connection conn = null;

		try {
			conn = DriverManager.getConnection(DatabaseInfo.URL, DatabaseInfo.USERNAME, DatabaseInfo.PASSWORD);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		PreparedStatement ps = null;
		ResultSet rs = null;

		String itemQuery = "Select itemUUID, name, itemType, servicerId, unit, pricePerUnit, modelNumber, retailCost from Item where itemId = ?";

		try {
			ps = conn.prepareStatement(itemQuery);
			ps.setInt(1, itemId);
			rs = ps.executeQuery();

			while (rs.next()) {
				UUID itemUUID = UUID.fromString(rs.getString("itemUUID"));
				String name = rs.getString("name");
				String itemType = rs.getString("itemType");

				if (itemType.equals("E")) {
					String modelNumber = rs.getString("modelNumber");
					double retailCost = rs.getDouble("retailCost");
					item = new Equipment(itemUUID, name, modelNumber, retailCost);
				} else if (itemType.equals("M")) {
					String unit = rs.getString("unit");
					double pricePerUnit = rs.getDouble("pricePerUnit");

					// 0 is the placeholder for quantity which will be updated in
					// InvoiceItemsDatabaseLoader with proper values
					item = new Material(itemUUID, name, unit, pricePerUnit, 0);

				} else if (itemType.equals("C")) {
					int servicerId = rs.getInt("servicerId");

					Company servicer = CompanyDatabaseLoader.loadCompany(servicerId);
					// 0 is the placeholder for amount, which will be updated in
					// InvoiceItemsDatabaseLoader with proper values
					item = new Contract(itemUUID, name, servicer, 0);
				}
			}

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		try {
			DatabaseConnection.closeConnection(rs, ps, conn);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return item;
	}

}
