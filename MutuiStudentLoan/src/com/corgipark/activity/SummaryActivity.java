package com.corgipark.activity;

import java.text.NumberFormat;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.corgipark.database.LoanProvider;
import com.corgipark.mutui.car.LineGraph;
import com.corgipark.mutui.car.Loan;
import com.corgipark.mutui.car.LoanUtils;
import com.corgipark.mutui.car.R;
import com.db4o.ObjectSet;

public class SummaryActivity extends BaseActivity {
	
	protected LoanProvider db;
	Loan loan;
	TextView textView_description;
	TextView textView_description_top;
	boolean editMode;
	
	protected TextView textView_term;
	protected TextView textView_interest;
	protected TextView textView_price;
	protected TextView textView_tax;
	protected TextView textView_discount;
	protected TextView textView_fees;
	protected TextView textView_payment;
	
	protected EditText editText_term;
	protected EditText editText_interest;
	protected EditText editText_price;
	protected EditText editText_tax;
	protected EditText editText_discount;
	protected EditText editText_fees;
	protected EditText editText_description;
	
	protected ViewSwitcher switcher;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_summary);
		switcher = (ViewSwitcher) findViewById(R.id.summary_viewswitcher);
		loan = (Loan) getIntent().getSerializableExtra("LoanSummary");
		editMode = false;
		//initialize activity
		textView_payment = (TextView) findViewById(R.id.summary_textview_payment);
		
		textView_description = (TextView) findViewById(R.id.summary_textview_description);
		//textView_description_top = (TextView) findViewById(R.id.summary_textview_description_top);
		textView_term = (TextView) findViewById(R.id.summary_textview_term);
		textView_price = (TextView) findViewById(R.id.summary_textview_price);
		textView_interest = (TextView) findViewById(R.id.summary_textview_interest);
		textView_tax = (TextView) findViewById(R.id.summary_textview_tax);
		textView_discount = (TextView) findViewById(R.id.summary_textview_discount);
		textView_fees = (TextView) findViewById(R.id.summary_textview_fees);
		
		
		editText_price = (EditText) findViewById(R.id.summary_edittext_price);
		editText_term = (EditText) findViewById(R.id.summary_edittext_term);
		editText_interest = (EditText) findViewById(R.id.summary_edittext_interest);
		editText_description = (EditText) findViewById(R.id.summary_edittext_description);
		editText_tax = (EditText) findViewById(R.id.summary_edittext_tax);
		editText_discount = (EditText) findViewById(R.id.summary_edittext_discount);
		editText_fees = (EditText) findViewById(R.id.summary_edittext_fees);
		
		textView_payment.setText(loan.getDescription() + " @ " + NumberFormat.getCurrencyInstance().format(LoanUtils.calculateMonthlyPayment(loan)) + " / month");
		textView_description.setText(loan.getDescription());
		//textView_description_top.setText(loan.getDescription());
		textView_price.setText(Double.toString(loan.getPrice()));
		textView_term.setText(Integer.toString(loan.getTerm()));
		textView_interest.setText(Double.toString(loan.getInterest()));
		textView_tax.setText(Double.toString(loan.getTax()));
		textView_discount.setText(Double.toString(loan.getDiscount()));
		textView_fees.setText(Double.toString(loan.getFees()));
		
		
//		
		initializeDB();
		loan.loanDetails();
		
	}



    public void onClick_delete(View view)
    {
    	view.startAnimation(AnimationUtils.loadAnimation(getBaseContext(), R.animator.image_click));
    	showDeleteAlert(SummaryActivity.this);
    }
    
	public void onClick_delete()
	{
		db.delete(loan);
		Intent intent = new Intent(this, MainActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
	}
	
	public void onClick_edit(View view)
	{
		view.startAnimation(AnimationUtils.loadAnimation(getBaseContext(), R.animator.image_click));
		if(!editMode)
		{
			switcher.showNext();
			editText_price.setText(textView_price.getText().toString());
			editText_interest.setText(textView_interest.getText().toString());
			editText_term.setText(textView_term.getText().toString());
			editText_description.setText(textView_description.getText().toString());
			editText_tax.setText(textView_tax.getText().toString());
			editText_discount.setText(textView_discount.getText().toString());
			editText_fees.setText(textView_fees.getText().toString());
			editMode = !editMode;
		}
		else
		{
			switcher.showPrevious();
			
			ObjectSet<?> results = db.db().queryByExample(loan);

			if(results.hasNext())
			{
				Loan temp = (Loan) results.next();
				textView_price.setText(editText_price.getText().toString());
				textView_interest.setText(editText_interest.getText().toString());
				textView_term.setText(editText_term.getText().toString());
				textView_description.setText(editText_description.getText().toString());
				textView_tax.setText(editText_tax.getText().toString());
				textView_discount.setText(editText_discount.getText().toString());
				textView_fees.setText(editText_fees.getText().toString());
	//			
				temp.setPrice(Double.parseDouble(textView_price.getText().toString()));
				temp.setTerm(Integer.parseInt(textView_term.getText().toString()));
				temp.setInterest(Double.parseDouble(textView_interest.getText().toString()));
				temp.setDescription(textView_description.getText().toString());
				temp.setTax(Double.parseDouble(textView_tax.getText().toString()));
				temp.setDiscount(Double.parseDouble(textView_discount.getText().toString()));
				temp.setFees(Double.parseDouble(textView_fees.getText().toString()));
				editMode = !editMode;
				db.store(temp);
				launchMain();
			}
		}
	}
	
	private void launchMain()
	{
		Intent intent = new Intent(this, LoanListActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
	}
	
	
	public void onClick_save(View view)
	{
		switcher.getNextView();
	}
	private void initializeDB()
	{
		db = new LoanProvider(this);
		db.startup();
 	}
	@Override
	protected void onResume(){
	    super.onResume();
		db.startup();
	}
	
	@Override
	protected void onPause()
	{
		Log.d("SummaryActivity", "onPause");
		db.shutdown();
		super.onPause();
	}
	
	public void onClick_amortization(View view)
	{
		view.startAnimation(AnimationUtils.loadAnimation(getBaseContext(), R.animator.image_click));
		LineGraph line = new LineGraph();
		Intent lineIntent = line.getIntent(this, loan);
		lineIntent.setClass(this, XYChartBuilder.class);
		Intent intent = new Intent(this, XYChartBuilder.class);
		intent.putExtra("LoanSummary", loan);
		//Intent intent = new Intent(this, AmortizationActivity.class);
		startActivity(intent);
	}
	
	
	private void showDeleteAlert(final Context context)
	{
		AlertDialog.Builder alert = new AlertDialog.Builder(this);

		alert.setTitle("Deleting loan: " + loan.getDescription());
		alert.setMessage("Are you sure?");

		alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
		  public void onClick(DialogInterface dialog, int whichButton) {
		  }
		});
		
		alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
		public void onClick(DialogInterface dialog, int whichButton) {
			Toast toast = Toast.makeText(context, "Loan deleted!", Toast.LENGTH_SHORT);
			toast.show();
			onClick_delete();
		  }
		});

		alert.show();
	}
}
