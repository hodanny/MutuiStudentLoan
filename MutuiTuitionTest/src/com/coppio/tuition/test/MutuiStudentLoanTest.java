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
	}
	
	@Override
	public void tearDown() throws Exception {
		//tearDown() is run after a test case has finished. 
		//finishOpenedActivities() will finish all the activities that have been opened during the test execution.
		solo.finishOpenedActivities();
	}
}