package com.corgipark.mutui.car;

import com.corgipark.mutui.car.R;

import android.util.Log;

public class Loan implements java.io.Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//description
	private String description = null;
	
	//mandatory fields
	private double price    = 0;
	private double interest = 0; 
	private int    term     = 0;
	
	//optional fields
	private double discount = 0;
	private double tax      = 0;
	private double fees     = 0;
	public int icon = 0;
	
	/*
	 * Test constructor
	 */
	public Loan(String description, int icon)
	{
		this.icon = icon;
		this.description = description;
	}
	
	public Loan(double price, int term, double interest)
	{
		this.price = price;
		this.interest = interest;
		this.term = term;
	}
	
	public Loan(){
		icon = R.drawable.ic_launcher;
	}
	
	public double getPrice()
	{
		return price;
	}
	
	public double getInterest()
	{
		return interest;
	}
	
	public int getTerm()
	{
		return term;
	}
	
	public void setPrice(double price)
	{
		this.price = price;
	}
	
	public void setInterest(double interest)
	{
		this.interest = interest;
	}
	
	public void setTerm(int term)
	{
		this.term = term;
	}
	
	public double getPrincipal()
	{
		return LoanUtils.calculatePriceWithTax(price-discount+fees, tax);
		//return price - discount;
	}
	
	public String getDescription()
	{
		return description;
	}
	
	public void setDescription(String description)
	{
		this.description = description;
	}
	
	public void setTax(double tax)
	{
        this.tax = tax;
	}
	
	public double getTax()
	{
		return tax;
	}
	
	public void setFees(double fees)
	{
		this.fees = fees;
	}
	
	public double getFees()
	{
		return fees;
	}
	
	public double getDiscount()
	{
		return discount;
	}
	public void setDiscount(double discount)
	{
		this.discount = discount;
	}
	
	public void loanDetails()
	{
		Log.d("DEBUG", "--------------------------");
		Log.d("DEBUG", "Description: " + description);
		Log.d("DEBUG", "Price: " + Double.toString(price));
		Log.d("DEBUG", "Interest: " + Double.toString(interest));
		Log.d("DEBUG", "Term: " + Integer.toString(term));
		Log.d("DEBUG", "Tax: " + Double.toString(tax));
		Log.d("DEBUG", "Discount: " + Double.toString(discount));
		Log.d("DEBUG", "Fees: " + Double.toString(fees));
	}
	
}
