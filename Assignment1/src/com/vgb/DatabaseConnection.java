package com.vgb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/*
 * Gavin Blesh
 * 
 * 2025-05-09
 * 
 * Class that manages the Database Connection
 */

public class DatabaseConnection {

	/*
	 * Closes the connection the database
	 */
	public static void closeConnection(ResultSet rs, PreparedStatement ps, Connection conn) throws SQLException {

		if (rs != null && !rs.isClosed()) {
			rs.close();
		}

		if (ps != null && !ps.isClosed()) {
			ps.close();
		}

		if (conn != null && !conn.isClosed()) {
			conn.close();
		}
	}

	/*
	 * Closes the connection and prepared statement, but not the result set
	 */
	public static void closeConnectionWithoutRS(PreparedStatement ps, Connection conn) throws SQLException {

		if (ps != null && !ps.isClosed()) {
			ps.close();
		}

		if (conn != null && !conn.isClosed()) {
			conn.close();
		}
	}

	/*
	 * Closes the rs (ResultSet) and ps (PreparedStatement)
	 */
	public static void closeRsPs(PreparedStatement ps, ResultSet rs) throws SQLException {
		rs.close();
		ps.close();
	}
}
