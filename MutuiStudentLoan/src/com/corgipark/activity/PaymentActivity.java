package com.corgipark.activity;

import java.text.NumberFormat;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.corgipark.database.LoanProvider;
import com.corgipark.mutui.car.DecimalDigitsInputFilter;
import com.corgipark.mutui.car.Loan;
import com.corgipark.mutui.car.LoanUtils;
import com.corgipark.mutui.car.R;


public class PaymentActivity extends BaseActivity{

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
	
	protected LoanProvider db;
	protected Loan currentCar = new Loan();
	//mandatory fields
	protected EditText editText_stickerPrice;
	protected EditText editText_interestRate;
	protected EditText editText_term;
	
	/*
	 * checked   = month
	 * unchecked = Year
	 */ 
	protected ToggleButton toggleButton_yearMonth;
	
	//optional fields
	protected EditText editText_discount;
	protected EditText editText_tax;
	protected EditText editText_fees;
	
	/*
	 * Calculated fields
	 */
	protected TextView  textView_monthly;
	protected TextView  textView_interestpaid;
	protected TextView  textView_net_total;
	
	protected ImageView imageView_paymentSave;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_payment);
		editText_stickerPrice = (EditText)findViewById(R.id.editText_price);
		editText_stickerPrice.setFilters(new InputFilter[] {new DecimalDigitsInputFilter(8,2)});
		editText_interestRate = (EditText)findViewById(R.id.editText_interest);
		editText_interestRate.setFilters(new InputFilter[] {new DecimalDigitsInputFilter(4,2)});
		editText_term = (EditText)findViewById(R.id.editText_term);
		//editText_stickerPrice.setFilters(new InputFilter[] {new DecimalDigitsInputFilter(2,0)});
		editText_discount = (EditText)findViewById(R.id.editText_discount);
		editText_discount.setFilters(new InputFilter[] {new DecimalDigitsInputFilter(8,2)});
		editText_tax = (EditText)findViewById(R.id.editText_salesTax);
		editText_tax.setFilters(new InputFilter[] {new DecimalDigitsInputFilter(4,2)});
		editText_fees = (EditText)findViewById(R.id.editText_fees);
		editText_fees.setFilters(new InputFilter[] {new DecimalDigitsInputFilter(8,2)});
		InputFilter[] FilterArray = new InputFilter[1];
		FilterArray[0] = new InputFilter.LengthFilter(3);
		editText_term.setFilters(FilterArray);
		initializeDB();
		
		imageView_paymentSave = (ImageView) findViewById(R.id.imageView_paymentSave);
		toggleButton_yearMonth = (ToggleButton) findViewById(R.id.payment_toggle_yearmonth);
		CharSequence month = "Month";
		CharSequence year = "Year";
		toggleButton_yearMonth.setTextOn(month);
		toggleButton_yearMonth.setTextOff(year);
		toggleButton_yearMonth.setChecked(true);
	    
		editText_stickerPrice.addTextChangedListener(textWatcher);
		editText_interestRate.addTextChangedListener(textWatcher);
		editText_term.addTextChangedListener(textWatcher);
		editText_discount.addTextChangedListener(textWatcher);
		editText_tax.addTextChangedListener(textWatcher);
		editText_fees.addTextChangedListener(textWatcher);
		editText_stickerPrice.addTextChangedListener(textWatcher);
		
		textView_monthly = (TextView) findViewById(R.id.payment_textview_monthly);
		textView_interestpaid = (TextView) findViewById(R.id.payment_textview_interestpaid);
		textView_net_total = (TextView) findViewById(R.id.payment_textview_net);
		calculate();
	}
	
	

	public void calculate()
	{
		currentCar.setPrice(Double.parseDouble(editTextValidator(editText_stickerPrice)));
		currentCar.setInterest(Double.parseDouble(editTextValidator(editText_interestRate)));
		
		/*
		 * checked   = month
		 * unchecked = year
		 */ 
		if(!toggleButton_yearMonth.isChecked())
		{
			currentCar.setTerm(Integer.parseInt(editTextValidator(editText_term))*12);
		}
		else
		{
			currentCar.setTerm(Integer.parseInt(editTextValidator(editText_term)));
		}
		
		//optionals
		currentCar.setTax(Double.parseDouble(editTextValidator(editText_tax)));
		currentCar.setDiscount(Double.parseDouble(editTextValidator(editText_discount)));
		currentCar.setFees(Double.parseDouble(editTextValidator(editText_fees)));		
//		
		textView_monthly.setText(NumberFormat.getCurrencyInstance().format(LoanUtils.calculateMonthlyPayment(currentCar)));
		textView_interestpaid.setText(NumberFormat.getCurrencyInstance().format(LoanUtils.calculateTotalInterest(currentCar)));
		textView_net_total.setText(NumberFormat.getCurrencyInstance().format(LoanUtils.calculateTotalPaid(currentCar)));
	}
	
	public void onClick_Calculate(View view)
	{
		LoanUtils.calculateAmortizationSchedule(currentCar);
	}
	public void onClick_Save(View view)
	{
		showSaveDialog(this);
	}
	
	private void initializeDB()
	{
		db = new LoanProvider(this);
		db.startup();
 	}
	
	//replace AlertDialog with AddLoanWindow
	private void showSaveDialog(final Context context)
	{
		AlertDialog.Builder alert = new AlertDialog.Builder(this, R.style.mutui_blue);

		alert.setTitle("Adding New Car");
		alert.setMessage("Description");

		// Set an EditText view to get user input 
		final EditText input = new EditText(this);
		alert.setView(input);

		alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
		  public void onClick(DialogInterface dialog, int whichButton) {
		  }
		});
		
		alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
		public void onClick(DialogInterface dialog, int whichButton) {
			Toast toast = Toast.makeText(context, "Car saved!", Toast.LENGTH_SHORT);
			toast.show();
			
			currentCar.setDescription(input.getText().toString());

			db.store(currentCar);
			currentCar = new Loan();
			launchMain();

		  }
		});

		alert.show();
	}
	
	private void launchMain()
	{
		Intent intent = new Intent(this, MainActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
	}
	
	@Override
	protected void onResume(){
	    super.onResume();
	    db.startup();
	}
	
	@Override
	protected void onPause()
	{
		db.shutdown();
		super.onPause();
	}
	
	protected String editTextValidator(EditText editText)
	{
		if(editText.getText().toString().equals("") || editText.getText().toString() == null )
		{
			return "0";
		}
		else
		{
			return editText.getText().toString();
			
		}
	}
	
	public void onClick_toggle(View view)
	{
		calculate();
	}
}
