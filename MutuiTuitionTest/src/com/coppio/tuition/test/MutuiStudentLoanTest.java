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
	
	/*
	 * Monthly 1
	 * Total 3
	 * Interest 5
	 * Principal 0
	 * Term 1
	 * Interest Rate 2
	 * 
	 */
	public void testCase1()
	{
		solo.enterText(0, "50000");
		solo.enterText(1, "10");
		solo.enterText(2, "10");
		
		//660.75 79290.44 29290.44
		assertEquals("TestCase 1", "$660.75", solo.getText(1).toString());
		assertEquals("TestCase 1", "$79290.44", solo.getText(3).toString());
		assertEquals("TestCase 1", "$29290.44", solo.getText(5).toString());
	}
}