package com.vgb;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.UUID;

/**
 * Gavin Blesh
 * 
 * 2025-05-09
 * 
 * JUnit test suite for VGB invoice system.
 */
public class EntityTests {

	public static final double TOLERANCE = 0.001;

	/**
	 * Creates an instance of a piece of equipment and tests if its cost and tax
	 * calculations are correct.
	 * 
	 */
	@Test
	public void testEquipment() {

		UUID uuid = UUID.randomUUID();
		String name = "Backhoe 3000";
		String model = "BH30X2";
		double cost = 95125.00;

		Equipment equipment = new Equipment(uuid, name, model, cost);

		double expectedCost = 95125.00;
		double expectedTax = 4994.06;

		double actualCost = MathRounding.round((equipment.calculateTotal()));
		double actualTax = MathRounding.round((equipment.calculateTax()));

		assertEquals(expectedCost, actualCost, TOLERANCE);
		assertEquals(expectedTax, actualTax, TOLERANCE);

		assertTrue(equipment.toString().contains("Backhoe 3000"));
		assertTrue(equipment.toString().contains("BH30X2"));
		assertTrue(equipment.toString().contains("95125.00"));
	}

	/*
	 * Creates an instance of a lease and tests if its cost and tax calculations are
	 * correct.
	 */
	@Test
	public void testLease() {

		UUID uuid = UUID.randomUUID();
		String name = "Backhoe 3000";
		String model = "BH30X2";
		double cost = 95125.0;
		LocalDate startDate = LocalDate.parse("2024-01-01");
		LocalDate endDate = LocalDate.parse("2026-06-01");

		Lease lease = new Lease(uuid, name, model, cost, startDate, endDate);

		double expectedTax = 1500.00;
		double expectedCost = 69037.29;

		double actualCost = lease.calculateTotal();
		double actualTax = lease.calculateTax();

		assertEquals(expectedCost, actualCost, TOLERANCE);
		assertEquals(expectedTax, actualTax, TOLERANCE);

		assertTrue(lease.toString().contains("Backhoe 3000"));
		assertTrue(lease.toString().contains("BH30X2"));
		assertTrue(lease.toString().contains("69037.29"));
	}

	/*
	 * Creates an instance of a rental agreement and tests if its cost and tax
	 * calculations are correct.
	 */
	@Test
	public void testRental() {

		UUID uuid = UUID.randomUUID();
		String name = "Backhoe 3000";
		String model = "BH30X2";
		double cost = 95125.00;
		int hoursRented = 25;

		Rental rental = new Rental(uuid, name, model, cost, hoursRented);

		double expectedCost = 2378.13;
		double expectedTax = 104.16;

		double actualCost = rental.calculateTotal();
		double actualTax = rental.calculateTax();

		assertEquals(expectedCost, actualCost, TOLERANCE);
		assertEquals(expectedTax, actualTax, TOLERANCE);

		assertTrue(rental.toString().contains("Backhoe 3000"));
		assertTrue(rental.toString().contains("BH30X2"));
		assertTrue(rental.toString().contains("2378.13"));
	}

	/*
	 * Creates an instance of a piece of material and tests if its cost and tax
	 * calculations are correct.
	 */
	@Test
	public void testMaterial() {

		UUID uuid = UUID.randomUUID();
		String name = "Nails";
		String unit = "Box";
		double pricePerUnit = 9.99;
		int quantity = 31;

		Material material = new Material(uuid, name, unit, pricePerUnit, quantity);

		double expectedCost = 309.69;
		double expectedTax = 22.14;

		double actualCost = material.calculateTotal();
		double actualTax = material.calculateTax();

		assertEquals(expectedCost, actualCost, TOLERANCE);
		assertEquals(expectedTax, actualTax, TOLERANCE);

		assertTrue(material.toString().contains("Nails"));
		assertTrue(material.toString().contains("Box"));
		assertTrue(material.toString().contains("9.99"));
		assertTrue(material.toString().contains("31"));

	}

	/*
	 * Creates an instance of a contract and tests if its cost and tax calculations
	 * are correct.
	 */
	@Test
	public void testContract() {

		UUID uuid = UUID.randomUUID();
		String name = "Foundation Pour";
		Person contact = new Person(uuid, "Play", "Doe", "402-531-0000");
		Address address = new Address("343 Brown St", "Lincoln", "NE", "68508");
		Company servicer = new Company(UUID.randomUUID(), "Mitchell LLC", contact, address);
		double amount = 10500.00;

		Contract contract = new Contract(uuid, name, servicer, amount);

		double expectedCost = 10500.00;

		double actualCost = contract.calculateTotal();

		assertEquals(expectedCost, actualCost, TOLERANCE);

		assertTrue(contract.toString().contains("Foundation Pour"));
		assertTrue(contract.toString().contains("Mitchell LLC"));

	}

}