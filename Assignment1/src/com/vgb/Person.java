package com.vgb;

import java.util.ArrayList;
import java.util.UUID;

/*
 * Gavin Blesh
 * 
 * 2025-05-02
 * 
 * Class to represent a Person
 */
public class Person {

	private UUID personUUID;
	private String lastName;
	private String firstName;
	private String phoneNumber;
	private ArrayList<String> emails;

	/*
	 * Constructor method for the Person class
	 */
	public Person(UUID uuid, String firstName, String lastName, String phoneNumber) {
		this.personUUID = uuid;
		this.firstName = firstName;
		this.lastName = lastName;
		this.phoneNumber = phoneNumber;
		this.emails = new ArrayList<>();
	}

	/*
	 * Getter method for the UUID
	 */
	public UUID getPersonUUID() {
		return personUUID;
	}

	/*
	 * Getter method for the First Name
	 */
	public String getFirstName() {
		return firstName;
	}

	/*
	 * Getter method for the Last Name
	 */
	public String getLastName() {
		return lastName;
	}

	/*
	 * Getter method for the phone number
	 */
	public String getPhoneNumber() {
		return phoneNumber;
	}

	/*
	 * Getter method for email(s)
	 */
	public ArrayList<String> getEmails() {
		return emails;
	}

	/*
	 * Method to add emails to the ArrayList
	 */
	public void addEmail(String email) {
		this.emails.add(email);
	}

	/*
	 * toString to properly format the data
	 */
	@Override
	public String toString() {
		return String.format("%s, %s (%s) %n [%s]", lastName, firstName, personUUID, getEmails());
	}

}
