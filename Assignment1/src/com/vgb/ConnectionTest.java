package com.vgb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.junit.Test;
import org.junit.Test.None;

/*
 * Gavin Blesh
 * 
 * 2025-05-09
 * 
 * Class to test the SQL & JDBC Connection and make sure it properly connects. It is implemented via JUnit testing
 */
public class ConnectionTest {

	/*
	 * Test case to make sure the connection is created
	 */
	@Test(expected = None.class)
	public void databaseConnectionTest() {

		Connection conn = null;

		try {
			conn = DriverManager.getConnection(DatabaseInfo.URL, DatabaseInfo.USERNAME, DatabaseInfo.PASSWORD);
			conn.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}
}
