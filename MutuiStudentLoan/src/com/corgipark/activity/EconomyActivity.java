package com.corgipark.activity;

import java.text.NumberFormat;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import com.corgipark.mutui.car.LoanUtils;
import com.corgipark.mutui.car.R;

public class EconomyActivity extends BaseActivity {

	EditText editText_mpy; //miles per year
	EditText editText_mpg; //miles per gallon
	EditText editText_ppg; //price per gallon

	SeekBar seekBar_mpy;
	SeekBar seekBar_mpg;
	SeekBar seekBar_ppg;
	
	TextView textView_mpy_value;
	TextView textView_mpg_value;
	TextView textView_ppg_value;
	TextView textView_total;
	TextView textView_total_monthly;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_economy);
		editText_mpy = (EditText) findViewById(R.id.edittext_economy_mpy);
		editText_mpg = (EditText) findViewById(R.id.edittext_economy_mpg);
		editText_ppg = (EditText) findViewById(R.id.edittext_economy_ppg);
		
		seekBar_mpy = (SeekBar) findViewById(R.id.economy_seekbar_mpy);
		seekBar_mpg = (SeekBar) findViewById(R.id.economy_seekbar_mpg);
		seekBar_ppg = (SeekBar) findViewById(R.id.economy_seekbar_ppg);
		
		textView_mpy_value = (TextView) findViewById(R.id.economy_textview_mpy_value);
		textView_mpg_value = (TextView) findViewById(R.id.economy_textview_mpg_value);
		textView_ppg_value = (TextView) findViewById(R.id.economy_textview_ppg_value);
		textView_total     = (TextView) findViewById(R.id.economy_textview_total);
		textView_total_monthly     = (TextView) findViewById(R.id.economy_textview_total_monthly);
		
		textView_mpy_value.setText(Integer.toString(seekBar_mpy.getProgress()*500));
		textView_mpg_value.setText(Integer.toString(seekBar_mpg.getProgress()));
		textView_ppg_value.setText(Double.toString(LoanUtils.round((double)seekBar_ppg.getProgress()/100,2)));
		setGasCosts();
		seekBar_mpy.setOnSeekBarChangeListener( new OnSeekBarChangeListener()
		{
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
			{
				textView_mpy_value.setText(Integer.toString(progress*500));
				setGasCosts();
		    }
		
		    public void onStartTrackingTouch(SeekBar seekBar)
		    {
		
		    }
		
		    public void onStopTrackingTouch(SeekBar seekBar)
		    {
		
		    }
		});
		seekBar_mpg.setOnSeekBarChangeListener( new OnSeekBarChangeListener()
		{
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
			{
				textView_mpg_value.setText(Integer.toString(progress));
				textView_total.setText(Double.toString(LoanUtils.calculateGasCosts(
						seekBar_mpy.getProgress()*500, seekBar_mpg.getProgress(), seekBar_ppg.getProgress())));
				setGasCosts();
		    }
		
		    public void onStartTrackingTouch(SeekBar seekBar)
		    {
		    	
		    }
		
		    public void onStopTrackingTouch(SeekBar seekBar)
		    {
		    	
		    }
		});
		seekBar_ppg.setOnSeekBarChangeListener( new OnSeekBarChangeListener()
		{
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
			{
				textView_ppg_value.setText(Double.toString(LoanUtils.round((double)progress/100,2)));
				setGasCosts();
		    }
		
		    public void onStartTrackingTouch(SeekBar seekBar)
		    {
		
		    }
		
		    public void onStopTrackingTouch(SeekBar seekBar)
		    {
		
		    }
		});
				
    }
    
	public void setGasCosts()
	{
		double yearlyGasCost = LoanUtils.calculateGasCosts(
				Double.parseDouble(textView_mpy_value.getText().toString()), 
				Double.parseDouble(textView_mpg_value.getText().toString()),
				Double.parseDouble(textView_ppg_value.getText().toString())
				);
		textView_total.setText(NumberFormat.getCurrencyInstance().format(yearlyGasCost) + "/year");
		textView_total_monthly.setText(NumberFormat.getCurrencyInstance().format(LoanUtils.round(yearlyGasCost/12,2)) + "/month");
	}
	
}
