package com.vgb;

/*
 * Gavin Blesh
 * 
 * 2025-05-09
 *
 * Class to represent and model an Address to be used alongside the Person class
 */
public class Address {

	private String street;
	private String city;
	private String state;
	private String zipCode;

	/*
	 * Constructor method for the class
	 */
	public Address(String street, String city, String state, String zipCode) {
		this.street = street;
		this.city = city;
		this.state = state;
		this.zipCode = zipCode;
	}

	/*
	 * Getter method for the street part of Address
	 */
	public String getStreet() {
		return street;
	}

	/*
	 * Getter method for the city part of Address
	 */
	public String getCity() {
		return city;
	}

	/*
	 * Getter method for the State part of Address
	 */
	public String getState() {
		return state;
	}

	/*
	 * Getter method for the Zipcode part of Address
	 */
	public String getZipCode() {
		return zipCode;
	}

	/*
	 * toString function to properly format the data
	 */
	@Override
	public String toString() {
		return String.format("%s, %s, %s, %s", street, city, state, zipCode);
	}

}
