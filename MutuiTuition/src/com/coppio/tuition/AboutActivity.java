package com.coppio.tuition;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.ads.AdRequest;
import com.google.ads.AdSize;
import com.google.ads.AdView;

public class AboutActivity extends Activity {
	private AdView ad;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_about);
		LinearLayout layout = (LinearLayout) findViewById(R.id.about_toplevel_layout);
		ad = new AdView(this, AdSize.BANNER, "a151209edabfa3b");
	    layout.addView(ad);
//	    AdRequest adRequest = new AdRequest();
//	    adRequest.addTestDevice("4FEE23C761CF84D39A3AB3D1CADD481F");
	    // Initiate a generic request to load it with an ad
	    ad.loadAd(new AdRequest());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.about, menu);
		return true;
	}
	
	@Override
	public void onDestroy() {
	  if (ad != null) {
	    ad.destroy();
	  }
	  super.onDestroy();
	}
	
	private boolean MyStartActivity(Intent aIntent) {
	    try
	    {
	        startActivity(aIntent);
	        return true;
	    }
	    catch (ActivityNotFoundException e)
	    {
	        return false;
	    }
	}
	
	//On click event for rate this app button
	public void onClick_rate(View v) {
	    Intent intent = new Intent(Intent.ACTION_VIEW);
	    //Try Google play
	    intent.setData(Uri.parse("market://details?id=" + getApplicationContext().getPackageName()));
	    if (MyStartActivity(intent) == false) {
	        //Market (Google play) app seems not installed, let's try to open a webbrowser
	        intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=" + getApplicationContext().getPackageName()));
	        if (MyStartActivity(intent) == false) {
	            //Well if this also fails, we have run out of options, inform the user.
	            Toast.makeText(this, "Could not open Android market, please install the market app.", Toast.LENGTH_SHORT).show();
	        }
	    }
	}
	
	public void onClick_website(View v)
	{
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setData(Uri.parse("http://www.coppio.com"));
		startActivity(intent);
	}
	
	
	//On click event for rate this app button
	public void onClick_mutuiCars(View v) {
	    Intent intent = new Intent(Intent.ACTION_VIEW);
	    //Try Google play
	    intent.setData(Uri.parse("market://details?id=com.corgipark.mutui.car"));
	    if (MyStartActivity(intent) == false) {
	        //Market (Google play) app seems not installed, let's try to open a webbrowser
	        intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.corgipark.mutui.car"));
	        if (MyStartActivity(intent) == false) {
	            //Well if this also fails, we have run out of options, inform the user.
	            Toast.makeText(this, "Could not open Android market, please install the market app.", Toast.LENGTH_SHORT).show();
	        }
	    }
	}
}
