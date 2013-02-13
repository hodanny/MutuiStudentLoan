package com.corgipark.activity;

import java.text.NumberFormat;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import com.corgipark.mutui.car.DecimalDigitsInputFilter;
import com.corgipark.mutui.car.LoanUtils;
import com.corgipark.mutui.car.R;

public class AffordabilityActivity extends Activity {

	EditText editText_monthlyPayment;
	EditText editText_interest;
	EditText editText_term;
	EditText editText_discount;
	
	TextView textView_affordability;

    @Override
    public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_affordability);
		editText_monthlyPayment = (EditText) findViewById(R.id.edittext_affordability_payment);
		editText_interest = (EditText) findViewById(R.id.edittext_affordability_interest);
		editText_term = (EditText) findViewById(R.id.edittext_affordability_term);
		editText_discount = (EditText) findViewById(R.id.edittext_affordability_tiv);
		
		editText_monthlyPayment.setFilters(new InputFilter[] {new DecimalDigitsInputFilter(8,2)});
		editText_interest.setFilters(new InputFilter[] {new DecimalDigitsInputFilter(4,2)});
		editText_discount.setFilters(new InputFilter[] {new DecimalDigitsInputFilter(8,2)});
		
		textView_affordability = (TextView) findViewById(R.id.affordability_textview_affordability_value);
		
		editText_monthlyPayment.addTextChangedListener(textWatcher);
		editText_interest.addTextChangedListener(textWatcher);
		editText_term.addTextChangedListener(textWatcher);
		editText_discount.addTextChangedListener(textWatcher);
		
		setAffordability();
    }
    
    TextWatcher textWatcher = new TextWatcher() {
   	 
	    @Override
	    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
	 
	    }
	 
	    @Override
	    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
	    }
	 
	    @Override
	    public void afterTextChanged(Editable editable) {
	    	
	    	try{
	    		setAffordability();
	    	}
	    	catch(Exception e)
	    	{
	    		textView_affordability.setText("NaaN");
	    	}
	    }
	};
	
	/*
	 * If editText is empty, set it to 0 
	 */
	private boolean isEmpty(EditText editText) {
		
		//editText length is less than 0
	    if (editText.getText().toString().trim().length() < 0) {
	        return true;
	    }
	    else return false;
	}
	
	private void setAffordability()
	{
		textView_affordability.setText(NumberFormat.getCurrencyInstance().format(LoanUtils.calculateAffordability(
				Double.parseDouble(editText_monthlyPayment.getText().toString()), 
				Double.parseDouble(editText_interest.getText().toString()), 
				Integer.parseInt(editText_term.getText().toString()), 
				Double.parseDouble(editText_discount.getText().toString()))
				));	
	}
}