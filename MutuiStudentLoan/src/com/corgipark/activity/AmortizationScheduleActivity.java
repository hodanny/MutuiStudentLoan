package com.corgipark.activity;

import java.math.BigDecimal;
import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ListView;

import com.corgipark.mutui.car.AmortizationAdapter;
import com.corgipark.mutui.car.Loan;
import com.corgipark.mutui.car.LoanUtils;
import com.corgipark.mutui.car.R;

public class AmortizationScheduleActivity extends Activity {
	  private ListView listView_aSchedule;
	  private AmortizationAdapter adapter;
	  ArrayList<LoanUtils.Tuple<BigDecimal, BigDecimal, BigDecimal>> list;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_amortization_schedule);
	    listView_aSchedule = (ListView) findViewById(R.id.listView_aSchedule1);
	    Loan loan = (Loan) getIntent().getSerializableExtra("LoanObject");
		list = LoanUtils.calculateAmortizationSchedule(loan);
	    adapter = new AmortizationAdapter(this, R.layout.listview_amortization_row, list);
	    listView_aSchedule.setAdapter(adapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_amortization_schedule, menu);
		return true;
	}

}
