package com.vgb;

/*
 * Gavin Blesh
 * 
 * 2025-05-09
 * 
 * Configuration for the SQL Database configuration
 */
public class DatabaseInfo {

	/**
	 * User name used to connect to the SQL server
	 */
	public static final String USERNAME = "gblesh2";

	/**
	 * Password used to connect to the SQL server
	 */
	public static final String PASSWORD = "chi1Phielohd";

	/**
	 * Connection parameters that may be necessary for server configuration
	 * 
	 */
	public static final String PARAMETERS = "";

	/**
	 * SQL server to connect to
	 */
	public static final String SERVER = "nuros.unl.edu";

	/**
	 * Fully formatted URL for a JDBC connection
	 */
	public static final String URL = String.format("jdbc:mysql://%s/%s?%s", SERVER, USERNAME, PARAMETERS);

}
