package com.vgb;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.UUID;

import org.junit.jupiter.api.Test;

/**
 * Gavin Blesh
 * 
 * 2025-05-02
 * 
 * JUnit test suite for VGB invoice system.
 */
public class InvoiceTests {

	public static final double TOLERANCE = 0.001;

	/**
	 * Tests the subtotal, tax total and grand total values of an invoice in the VGB
	 * system using material, equipment, and contract.
	 */
	@Test
	public void testInvoice01() {

		UUID uuid = UUID.randomUUID();
		Equipment equipment = new Equipment(uuid, "Backhoe 3000", "BH30X2", 95125.00);

		UUID uuid1 = UUID.randomUUID();
		Material material = new Material(uuid1, "Nails", "Box", 9.99, 31);

		UUID uuid2 = UUID.randomUUID();
		UUID companyUUID = UUID.randomUUID();
		UUID contactUUID = UUID.randomUUID();
		Person contactPerson = new Person(contactUUID, "Brad", "Denif", "501-342-9403");
		Address address = new Address("232 Green Ville Ave", "Omaha", "NE", "68046");
		Company servicer = new Company(companyUUID, "Mitchell LLC", contactPerson, address);
		Contract contract = new Contract(uuid2, "Foundation Pour", servicer, 10500.00);

		UUID customerUUID = UUID.randomUUID();
		UUID salesPersonUUID = UUID.randomUUID();
		Company customer = new Company(customerUUID, "Mitchell LLC", contactPerson, address);
		Person salesPerson = new Person(salesPersonUUID, "Tom", "Watts", "402-403-2494");
		LocalDate invoiceDate = LocalDate.parse("2023-10-01");
		Invoice invoice = new Invoice(UUID.randomUUID(), customer, salesPerson, invoiceDate);
		invoice.addItem(equipment);
		invoice.addItem(material);
		invoice.addItem(contract);

		double expectedSubtotal = MathRounding.round(95125.00 + (9.99 * 31) + 10500.00);
		double expectedTaxTotal = 5016.20;
		double expectedGrandTotal = expectedSubtotal + expectedTaxTotal;

		double actualSubtotal = invoice.calculateTotal();
		double actualTaxTotal = invoice.calculateTaxTotal();
		double actualGrandTotal = invoice.calculateGrandTotal();

		assertEquals(expectedSubtotal, actualSubtotal, TOLERANCE);
		assertEquals(expectedTaxTotal, actualTaxTotal, TOLERANCE);
		assertEquals(expectedGrandTotal, actualGrandTotal, TOLERANCE);

		assertTrue(invoice.toString().contains("Invoice UUID"));
		assertTrue(invoice.toString().contains("Mitchell LLC"));
		assertTrue(invoice.toString().contains("Watts, Tom"));
		assertTrue(invoice.toString().contains("2023-10-01"));
		assertTrue(invoice.toString().contains("(3)"));
	}

	/**
	 * Tests the subtotal, tax total and grand total values of an invoice in the VGB
	 * system using lease and rent.
	 */
	@Test
	public void testInvoice02() {

		UUID uuid2 = UUID.randomUUID();
		LocalDate startDate = LocalDate.parse("2024-01-01");
		LocalDate endDate = LocalDate.parse("2026-06-01");
		Lease lease = new Lease(uuid2, "Backhoe 3000", "BH30X2", 95125.00, startDate, endDate);

		UUID uuid3 = UUID.randomUUID();
		Rental rental = new Rental(uuid3, "Backhoe 3000", "BH30X2", 95125.00, 25);

		UUID customerUUID = UUID.randomUUID();
		Address address = new Address("314 Nowhere St", "Twin Peaks", "WA", "80943");
		Person customerContact = new Person(UUID.randomUUID(), "Tom", "Hardy", "561-535-3553");
		Company customer = new Company(customerUUID, "Lincoln Corporation", customerContact, address);

		UUID salesPersonUUID = UUID.randomUUID();
		Person salesPerson = new Person(salesPersonUUID, "Lebron", "James", "123-456-7890");

		LocalDate invoiceDate = LocalDate.parse("2023-10-01");
		Invoice invoice = new Invoice(UUID.randomUUID(), customer, salesPerson, invoiceDate);
		invoice.addItem(lease);
		invoice.addItem(rental);

		double expectedSubtotal = MathRounding.round(2378.13 + 69037.29);
		double expectedTaxTotal = MathRounding.round(1604.16);
		double expectedGrandTotal = MathRounding.round(expectedSubtotal + expectedTaxTotal);

		double actualSubtotal = invoice.calculateTotal();
		double actualTaxTotal = invoice.calculateTaxTotal();
		double actualGrandTotal = invoice.calculateGrandTotal();

		assertEquals(expectedSubtotal, actualSubtotal, TOLERANCE);
		assertEquals(expectedTaxTotal, actualTaxTotal, TOLERANCE);
		assertEquals(expectedGrandTotal, actualGrandTotal, TOLERANCE);

		assertTrue(invoice.toString().contains("Invoice UUID"));
		assertTrue(invoice.toString().contains("Lincoln Corporation"));
		assertTrue(invoice.toString().contains("James, Lebron"));
		assertTrue(invoice.toString().contains("2023-10-01"));
		assertTrue(invoice.toString().contains("(2)"));
	}

}