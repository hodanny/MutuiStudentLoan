package com.coppio.tuition;

import java.math.BigDecimal;


public class LoanUtils {
	
	public static BigDecimal calculateMonthlyPayment(BigDecimal principal, BigDecimal term, BigDecimal interest)
	{
		return new BigDecimal("0");
	}
	
	public static BigDecimal calculateNet(BigDecimal principal, BigDecimal term, BigDecimal interest)
	{
		
		return new BigDecimal("0");
	}
	public static BigDecimal calculateTotalInterest(BigDecimal principal, BigDecimal term, BigDecimal interest)
	{
		
		return new BigDecimal("0");
	}
	
	/*
	 * Determines monthly payment amount
	 */
	public static double calculateMonthlyPayment(double principal, int term, double interest, boolean rounded)
	{

		double mI, denominator = 0;

		if(term < 1) 
		{
			term = 1;
		}
		
		if(interest == 0)
		{
			return LoanUtils.round(principal/12,2);
		}
		else
		{
			mI = interest/(12*100);
			denominator = (1 - Math.pow((1 + mI),-(term)));
			if(denominator != 0)
			{
				double unrounded = principal * ( mI / denominator);
				if(rounded)
				{
					return LoanUtils.round(unrounded,2);
				}
				else
				{
					return unrounded;
				}
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
	public static double calculateTotalInterest(double principal, int term, double interest)
	{
		double interestPaid = LoanUtils.calculateTotalPaid(principal, term, interest) - principal;
		if(interestPaid < 0) 
			return 0;
		else
			return LoanUtils.calculateTotalPaid(principal, term, interest) - principal;
	}
	
	/*
	 * Determines net amount paid with interest
	 */
	public static double calculateTotalPaid(double principal, int term, double interest)
	{
		double monthlyAmount = LoanUtils.calculateMonthlyPayment(principal, term, interest, false);
		return LoanUtils.round(term*monthlyAmount, 2);
	}
	
	public static double round(double number, double decimal_places)
	{
		double mask = Math.pow(10, decimal_places);
		number *= mask;
		number = Math.round(number);
		return number/mask;
	}
	
	
}
