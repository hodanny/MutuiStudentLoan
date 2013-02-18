package com.coppio.tuition.test;
import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.ImageView;
import com.coppio.tuition.R;
import com.coppio.tuition.MutuiTuition;
import com.jayway.android.robotium.solo.Solo;

public class MainActivityTest extends ActivityInstrumentationTestCase2<MutuiTuition>{

	private Solo solo;
	Activity mActivity;
	ImageView about;
	
	public MainActivityTest() {
		super(MutuiTuition.class);
	}
	
	@Override
	public void setUp() throws Exception {
		//setUp() is run before a test case is started. 
		//This is where the solo object is created.
		solo = new Solo(getInstrumentation(), getActivity());
		solo.assertCurrentActivity("Expected Mainy Activity", "MutuiTuition");
		about = (ImageView) solo.getView(R.id.main_imageview_about);

	}

	@Override
	public void tearDown() throws Exception {
		//tearDown() is run after a test case has finished. 
		//finishOpenedActivities() will finish all the activities that have been opened during the test execution.
		solo.finishOpenedActivities();
	}
	
	public void testPreConditions() throws Exception {
		solo.assertCurrentActivity("Expected Main Activity", "MutuiTuition");
		assertNotNull("Expected About Image", about);
	}
	
	public void testStartAboutActivity() throws Exception
	{
		solo.clickOnView(about);
		solo.assertCurrentActivity("Expected About Activity", "AboutActivity");
	}
}