
package com.corgipark.activity;

import android.app.Activity;
import android.os.Bundle;

import com.corgipark.mutui.car.R;

public class BaseActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_base);
	}



}
