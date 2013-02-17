package com.coppio.tuition;

import java.text.NumberFormat;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.google.ads.AdRequest;
import com.google.ads.AdSize;
import com.google.ads.AdView;

public class MutuiTuition extends Activity  {
	
	static boolean animation_guard = false;
	EditText editText_interestrate;
	EditText editText_term;
	EditText editText_principal;
	
	TextView textView_monthly;
	TextView textView_net;
	TextView textView_interestpaid;
	
	static ViewSwitcher viewSwitcher;
	
	AdView ad;
	//second, we create the TextWatcher
	TextWatcher textWatcher = new TextWatcher() {
	 
	    @Override
	    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
	 
	    }
	 
	    @Override
	    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
	    }
	 
	    @Override
	    public void afterTextChanged(Editable editable) {
			calculate();
	    }
	};
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		editText_interestrate = (EditText) findViewById(R.id.main_edittext_interestrate);
		editText_term         = (EditText) findViewById(R.id.main_edittext_term);
		editText_principal    = (EditText) findViewById(R.id.main_edittext_principal);
		textView_monthly      = (TextView) findViewById(R.id.main_textview_monthly);
		textView_net          = (TextView) findViewById(R.id.main_textview_net);
		textView_interestpaid = (TextView) findViewById(R.id.main_textview_totalinterest);
		
		editText_interestrate.addTextChangedListener(textWatcher);
		editText_term.addTextChangedListener(textWatcher);
		editText_principal.addTextChangedListener(textWatcher);
		
		editText_interestrate.setFilters(new InputFilter[] {new DecimalDigitsInputFilter(4,2)});
		editText_principal.setFilters(new InputFilter[] {new DecimalDigitsInputFilter(8,2)});
		
		viewSwitcher = (ViewSwitcher) findViewById(R.id.main_viewswitcher);

		if(!animation_guard)
		{
			final Handler handler = new Handler();
			
			handler.postDelayed(new Runnable() {
				@Override
				public void run()
				{
					viewSwitcher.showNext();
				}
			}, 2000);
			animation_guard = true;
		}
		else viewSwitcher.showNext();
		
		LinearLayout layout = (LinearLayout) findViewById(R.id.container);
	    ad = new AdView(this, AdSize.BANNER, "a151209edabfa3b");
	    layout.addView(ad);
//	    AdRequest adRequest = new AdRequest();
//	    adRequest.addTestDevice("4FEE23C761CF84D39A3AB3D1CADD481F");
	    // Initiate a generic request to load it with an ad
	    ad.loadAd(new AdRequest());
	    
		calculate();
	}

	public void calculate()
	{
		
//		if(editText_interestrate.getText().toString().matches("")) editText_interestrate.setText("0");
//		if(editText_term.getText().toString().matches("")) editText_term.setText("0");
//		if(editText_principal.getText().toString().matches("")) editText_principal.setText("0");
		
		try{
		double interest = Double.parseDouble(editText_interestrate.getText().toString());
		int term            = Integer.parseInt(editText_term.getText().toString());
		term *= 12;
		if (term == 0) term = 12;
		double principal    = Double.parseDouble(editText_principal.getText().toString());
		
		textView_monthly.setText(NumberFormat.getCurrencyInstance().format(LoanUtils.calculateMonthlyPayment(principal, term, interest, true)));
		textView_net.setText(NumberFormat.getCurrencyInstance().format(LoanUtils.calculateTotalPaid(principal, term, interest)));
		textView_interestpaid.setText(NumberFormat.getCurrencyInstance().format(LoanUtils.calculateTotalInterest(principal, term, interest)));
		}
		catch(Exception e)
		{
			textView_monthly.setText("NaN");
			textView_net.setText("NaN");
			textView_interestpaid.setText("NaN");
		}
	}
}
