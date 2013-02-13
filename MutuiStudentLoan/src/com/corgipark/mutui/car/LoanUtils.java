package com.corgipark.mutui.car;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;

import android.util.Log;

public class LoanUtils {

	public static class Tuple<X,Y,Z> { 
		  public final X x; 
		  public final Y y; 
		  public final Z z;
		  public Tuple(X x, Y y, Z z) { 
		    this.x = x; 
		    this.y = y;
		    this.z = z;
		  } 
	} 
	
	/*
	 * Determines amortization schedule
	 */
	public static ArrayList<Tuple<BigDecimal, BigDecimal, BigDecimal>> calculateAmortizationSchedule(Loan loan)
	{
		ArrayList<Tuple<BigDecimal, BigDecimal, BigDecimal>> schedule = new ArrayList<Tuple<BigDecimal, BigDecimal, BigDecimal>>();
				
		BigDecimal initialMonthlyPayment = new BigDecimal(LoanUtils.calculateMonthlyPayment(loan));
		BigDecimal remainingBalance = new BigDecimal(loan.getPrice());
		BigDecimal monthlyInterestRate = new BigDecimal(loan.getInterest()/12);
		int i = 1;
		Log.d("T", "T: ");
		Log.d("AmortizationSchedule", "Initiate");
		while(remainingBalance.compareTo(new BigDecimal("0")) > 0 || i < loan.getTerm())
		{
		
			BigDecimal interestPaid = remainingBalance.multiply(monthlyInterestRate).divide(new BigDecimal(100));
			interestPaid = interestPaid.setScale(2, BigDecimal.ROUND_HALF_UP);
			BigDecimal principalPaid = initialMonthlyPayment.subtract(interestPaid);
			principalPaid = principalPaid.setScale(2, BigDecimal.ROUND_HALF_UP);
			remainingBalance = remainingBalance.subtract(principalPaid);
			remainingBalance =remainingBalance.setScale(2, BigDecimal.ROUND_HALF_UP);
			if(remainingBalance.compareTo(new BigDecimal("0")) < 0) remainingBalance = new BigDecimal("0");
			
			Tuple<BigDecimal, BigDecimal, BigDecimal> tuple = new Tuple<BigDecimal, BigDecimal, BigDecimal>(principalPaid, interestPaid, remainingBalance);
			
			schedule.add(tuple);
			Log.d("AmortizationSchedule", i + " B: " + remainingBalance.toString() + " I: " 
			+ interestPaid.toString() + " P: " + principalPaid.toString() + "Total: " + interestPaid.add(principalPaid).toString());
			 i++;
		}
 		return schedule;
	}
	
	/*
	 * Determines number of payments required to pay off loan
	 */
	public static int getTotalPayments(double principal, double monthlyPayment)
	{
		return (int)Math.ceil(principal/monthlyPayment);
	}
	
	/*
	 * Determines monthly payment amount
	 */
	public static double calculateMonthlyPayment(Loan loan)
	{
		//http://www.hughchou.org/calc/formula.html
		// M = total monthly payment
		// P = principal amount
		// J = monthly interest
		// N = length of term in months
		//
		//                            J
		//       M  =  P  x ------------------------
		//                  1  - ( 1 + J ) ^ -N
		//final double DECIMAL_PLACES = 100; //2 decimal places
		double mI, denominator = 0;
		
		int term = loan.getTerm();
		if(term < 1) term = 1;
		
		if(loan.getInterest() == 0)
		{
			return LoanUtils.round(loan.getPrincipal()/term, 2);
		}
		else
		{
			mI = loan.getInterest()/(12*100);
			denominator = (1 - Math.pow((1 + mI),-(term)));
			if(denominator != 0)
			{
				double unrounded = loan.getPrincipal() * ( mI / denominator);
				return LoanUtils.round(unrounded,2);
			}
			else
			{
				return 0;
			}
		}
	}
	
	/*
	 * Determines total interest paid
	 */
	public static double calculateTotalInterest(Loan loan)
	{
		double interestPaid = LoanUtils.calculateTotalPaid(loan) - loan.getPrice();
		if(interestPaid < 0) 
			return 0;
		else
			return LoanUtils.calculateTotalPaid(loan) - loan.getPrice();
	}
	
	/*
	 * Determines net amount paid with interest
	 */
	public static double calculateTotalPaid(Loan loan)
	{
		double monthlyAmount = LoanUtils.calculateMonthlyPayment(loan);
		int numOfPayments = loan.getTerm();
		return LoanUtils.round(numOfPayments*monthlyAmount, 2);
	}
	/*
	 * Determines interest ontop of price
	 */
	public static double calculatePriceWithTax(double price, double tax)
	{
		double i = 1 + (tax * 0.01);
		i = i*price;
		i = (Math.round(i*100));
		return i/100;
	}
	
	/*
	 * Calculate cost of gas per year (MPG)
	 */
	public static double calculateGasCosts(double milesPerYear, double milesPerGallon, double cost)
	{
		return LoanUtils.round(milesPerYear/milesPerGallon*cost, 2);
	}
	
	/*
	 * Rounds a double by decimal places
	 */
	public static double round(double number, double decimal_places)
	{
		double mask = Math.pow(10, decimal_places);
		number *= mask;
		number = Math.round(number);
		return number/mask;
	}
	
	/*
	 * Calculates the maximum amount you can finance (tax and fees included)
	 */
	// M = total monthly payment
	// P = principal amount
	// J = monthly interest
	// N = length of term in months
	//
	//       M * (1  - ( 1 + J ) ^ -N)
	// P =  --------------------------
	//                J
	public static double calculateAffordability(double monthlyPayment, double interest, int term, double discount)
	{
		double mI = interest/(12*100);
		if(interest==0) mI = 0.0000001;
		return LoanUtils.round(monthlyPayment*(1-Math.pow((1 + mI), -term))/mI, 2) + discount;
	}
	
	public static BigDecimal calculateAffordability(BigDecimal monthlyPayment, BigDecimal interest, BigDecimal term, BigDecimal discount)
	{
		BigDecimal mI = interest.divide(new BigDecimal(1200), 2, RoundingMode.HALF_UP);
		//if(interest.==0) mI = 0.0000001;
		BigDecimal pow = (new BigDecimal(1).add(mI)).pow(-1*term.intValue(), new MathContext(2, RoundingMode.HALF_UP));
		return (monthlyPayment.multiply(new BigDecimal(1).subtract(new BigDecimal(1).subtract(pow)))).divide(mI).setScale(2, BigDecimal.ROUND_HALF_UP);	
	}
}
