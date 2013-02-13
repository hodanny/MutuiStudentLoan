package com.corgipark.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;

import com.corgipark.mutui.car.R;

public class MainActivity extends BaseActivity  {
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	//listener to go to PaymentActivity
	public void onClick_addCar()
	{
		Intent intent = new Intent(this, PaymentActivity.class);
		startActivity(intent);
	}
	

	public void onClick_calculator(View view)
	{
		view.startAnimation(AnimationUtils.loadAnimation(getBaseContext(), R.animator.image_click));
		onClick_addCar();
	}
	
	public void onClick_cars(View view)
	{
		view.startAnimation(AnimationUtils.loadAnimation(getBaseContext(), R.animator.image_click));
		Intent intent = new Intent(this, LoanListActivity.class);
		startActivity(intent);
	}
	
	public void onClick_economy(View view)
	{
		view.startAnimation(AnimationUtils.loadAnimation(getBaseContext(), R.animator.image_click));
		Intent intent = new Intent(this, EconomyActivity.class);
		startActivity(intent);
	}
	
	public void onClick_settings(View view)
	{
		view.startAnimation(AnimationUtils.loadAnimation(getBaseContext(), R.animator.image_click));
		Intent intent = new Intent(this, AffordabilityActivity.class);
		startActivity(intent);
	}

}
