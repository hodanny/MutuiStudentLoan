package com.coppio.tuition.test;

import android.test.ActivityInstrumentationTestCase2;

import com.coppio.tuition.MutuiTuition;
import com.jayway.android.robotium.solo.Solo;

public class MutuiStudentLoanTest extends ActivityInstrumentationTestCase2<MutuiTuition>{

	private Solo solo;
	
	public MutuiStudentLoanTest() {
		super(MutuiTuition.class);
	}
	
	@Override
	public void setUp() throws Exception {
		//setUp() is run before a test case is started. 
		//This is where the solo object is created.
		solo = new Solo(getInstrumentation(), getActivity());
		solo.clearEditText(0);
		solo.clearEditText(1);
		solo.clearEditText(2);
	}
	

	@Override
	public void tearDown() throws Exception {
		//tearDown() is run after a test case has finished. 
		//finishOpenedActivities() will finish all the activities that have been opened during the test execution.
		solo.finishOpenedActivities();
	}
	

	public void testCase1()
	{
		solo.enterText(0, "50000");
		solo.enterText(1, "10");
		solo.enterText(2, "10");

		assertEquals("Monthly Value Exception", "$660.75", solo.getText(1).getText().toString());
		assertEquals("Net Value Exception", "$79,290.44", solo.getText(3).getText().toString());
		assertEquals("Interest Value Exception", "$29,290.44", solo.getText(5).getText().toString());
	}
	
	public void testCase2()
	{
		solo.enterText(0, "25000");
		solo.enterText(1, "1");
		solo.enterText(2, "1");

		assertEquals("Monthly Value Exception", "$2,094.64", solo.getText(1).getText().toString());
		assertEquals("Net Value Exception", "$25,135.62", solo.getText(3).getText().toString());
		assertEquals("Interest Value Exception", "$135.62", solo.getText(5).getText().toString());
		
	}
	
	public void testCase3()
	{
		solo.enterText(0, "25000");
		solo.enterText(1, "0");
		solo.enterText(2, "1");

		assertEquals("Monthly Value Exception", "$2,094.64", solo.getText(1).getText().toString());
		assertEquals("Net Value Exception", "$25,135.62", solo.getText(3).getText().toString());
		assertEquals("Interest Value Exception", "$135.62", solo.getText(5).getText().toString());
	}
	
	public void testCase4()
	{
		solo.enterText(0, "12000");
		solo.enterText(1, "1");
		solo.enterText(2, "0");

		assertEquals("Monthly Value Exception", "$1,000.00", solo.getText(1).getText().toString());
		assertEquals("Net Value Exception", "$12,000.00", solo.getText(3).getText().toString());
		assertEquals("Interest Value Exception", "$0.00", solo.getText(5).getText().toString());
	}
	
	public void testCase5()
	{
		solo.enterText(0, "250.55");
		solo.enterText(1, "30");
		solo.enterText(2, "1.19");

		assertEquals("Monthly Value Exception", "$0.83", solo.getText(1).getText().toString());
		assertEquals("Net Value Exception", "$298.05", solo.getText(3).getText().toString());
		assertEquals("Interest Value Exception", "$47.50", solo.getText(5).getText().toString());
	}
	
	public void testCase6()
	{
		solo.enterText(0, "0");
		solo.enterText(1, "30");
		solo.enterText(2, "1.19");

		assertEquals("Monthly Value Exception", "$0.00", solo.getText(1).getText().toString());
		assertEquals("Net Value Exception", "$0.00", solo.getText(3).getText().toString());
		assertEquals("Interest Value Exception", "$0.00", solo.getText(5).getText().toString());
	}
}