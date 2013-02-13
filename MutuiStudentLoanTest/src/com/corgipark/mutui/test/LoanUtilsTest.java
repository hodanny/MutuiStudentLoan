package com.corgipark.mutui.test;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.corgipark.mutui.car.Loan;
import com.corgipark.mutui.car.LoanUtils;

public class LoanUtilsTest {

	/*
	 *  Monthly Payment Calculator Tests
	 */
	@Test
	public void testMonthlyPayment()
	{
		assertEquals(221.34, LoanUtils.calculateMonthlyPayment(new Loan(10000,48,3)), 0.001); //ints
		assertEquals(103.72, LoanUtils.calculateMonthlyPayment(new Loan(1234.56,12,1.5)), 0.001); //doubles
		assertEquals(1666.67, LoanUtils.calculateMonthlyPayment(new Loan(20000,12,0)), 0.001); //0% interest
		assertEquals(298.23, LoanUtils.calculateMonthlyPayment(new Loan(20000,100,10.25)), 0.001); //double interest
		assertEquals(2.24, LoanUtils.calculateMonthlyPayment(new Loan(100.99,48,3)), 0.001); //double price
		assertEquals(0, LoanUtils.calculateMonthlyPayment(new Loan(0,48,3)), 0.001); //double price
		assertEquals(1000,LoanUtils.calculateMonthlyPayment(new Loan(1000,1,0)), 0.001); //term=1
		assertEquals(1000,LoanUtils.calculateMonthlyPayment(new Loan(1000,0,0)), 0.001); //term=0
	}
	
	/*
	 * Test Total Payments required
	 */
	
	@Test
	public void testGetTotalPayments()
	{
		assertEquals("Results", 10, LoanUtils.getTotalPayments(10000,1000));
		assertEquals("Results", 0, LoanUtils.getTotalPayments(0,0));
		assertEquals("Results", 0, LoanUtils.getTotalPayments(0,10));
		assertEquals("Results", 11, LoanUtils.getTotalPayments(10001,1000));
		assertEquals("Results", 1, LoanUtils.getTotalPayments(0.01,1));
		assertEquals("Results", 1, LoanUtils.getTotalPayments(2,2));
	}
	
	@Test
	public void testGetPriceWithTax()
	{
		assertEquals(12, LoanUtils.calculatePriceWithTax(10, 20), 0.01);
		assertEquals(10, LoanUtils.calculatePriceWithTax(10, 0), 0.01);
		assertEquals(0, LoanUtils.calculatePriceWithTax(0, 10), 0.01);
		assertEquals(1925.91, LoanUtils.calculatePriceWithTax(1234.56, 56), 0.01);
		assertEquals(1200, LoanUtils.calculatePriceWithTax(1000, 20), 0.01);
	}
	
	@Test
	public void testCalculateGasCosts()
	{
		assertEquals(1785, LoanUtils.calculateGasCosts(15000,10,1.19), 0.01);
	}
	
	@Test
	public void testRound()
	{
		assertEquals(10.41, LoanUtils.round(10.4111, 2), 0);
	}
	
	@Test public void testLoan()
	{
		Loan loan = new Loan();
		loan.setDescription("Hello");
		loan.setDiscount(1000);
		loan.setFees(500);
		loan.setInterest(1.9);
		loan.setPrice(2500.50);
		loan.setTax(10.001);
		loan.setTerm(40);
		
		assertEquals(1000, loan.getDiscount(), 0);
		assertEquals("Hello", loan.getDescription());
		assertEquals(1000, loan.getDiscount(), 0);
		assertEquals(500, loan.getFees(), 0);
		assertEquals(1.9, loan.getInterest(), 0);
		assertEquals(10.001, loan.getTax(), 0);
		assertEquals("Term", 40, loan.getTerm());
	}
	
	@Test
	public void testCalculateAffordability()
	{
		assertEquals(5935.50, LoanUtils.calculateAffordability(500, 2, 12, 0), 0.01);
		assertEquals(0, LoanUtils.calculateAffordability(0, 2, 12, 0), 0.01);
		assertEquals(0, LoanUtils.calculateAffordability(600, 2, 0, 0), 0.01);
		assertEquals(600, LoanUtils.calculateAffordability(600, 0, 1, 0), 0.01);
	}
	
	@Test
	public void testAmortization()
	{
//		ArrayList<Integer> list = new ArrayList<Integer>();
//		list.add(1);
//		list.add(2);
//		list.add(3);
//
//		ArrayList<Double> list2= LoanUtils.calculateAmortizationSchedule(new Loan(250000, 360,5));
//		
//		
//		assertEquals(list, list2);
	}
	
	@Test
	public void testTotalLoanPayments()
	{
		assertEquals(21891.36, 456.07*48, 0.01);
	}
	
	@Test
	public void testTotalInterestPaid()
	{
		assertEquals(1891.36, LoanUtils.calculateTotalInterest(new Loan(20000,48, 4.5)), 0.01);
	}
	@Before
	public void setup()
	{
		
	}
	
	@After
	public void teardown()
	{
	
	}
}
