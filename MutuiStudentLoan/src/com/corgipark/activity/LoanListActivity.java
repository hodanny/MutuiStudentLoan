package com.corgipark.activity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.corgipark.database.LoanProvider;
import com.corgipark.mutui.car.Loan;
import com.corgipark.mutui.car.LoanAdapter;
import com.corgipark.mutui.car.R;

public class LoanListActivity extends Activity {
	
	private LoanProvider db = new LoanProvider(this);
	Button button_clear;
	ArrayList<Loan> carList = new ArrayList<Loan>();
	ListView listView_carList;
	LoanAdapter adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_loan_list);
		initializeDB();
		
		
		loadCars();
		
        listView_carList = (ListView)findViewById(R.id.listView_carList);
        adapter = new LoanAdapter(this, R.layout.listview_item_row, carList);
        listView_carList.setAdapter(adapter);
        listView_carList.setOnItemClickListener(new OnItemClickListener(){
        public void onItemClick(AdapterView<?> arg0, View v, int position, long id)
        {
        	Loan loan = (Loan)listView_carList.getItemAtPosition(position);
        	startSummary(loan);
        }
        });
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_loan_list, menu);
		return true;
	}
	
	private void loadCars()
	{
		List<Loan> list = getCars();
		Iterator<Loan> itr = list.iterator();
		carList.clear();
		while(itr.hasNext())
		{
			carList.add(itr.next());
		}
	}
	
	@Override
	protected void onPause()
	{
		db.shutdown();
		super.onPause();
	}
	
	@Override
	protected void onResume(){
	    super.onResume();
	    db.startup();
	}
	
	private void startSummary(Loan loan)
	{
    	Intent intent = new Intent(this, SummaryActivity.class);
    	intent.putExtra("LoanSummary", loan);
    	startActivity(intent);
	}
	
	
	private void initializeDB()
	{
		db = new LoanProvider(this);
		db.startup();
 	}
	
	//returns a list of Objects in the database
	// cast to CarBean to access properties
	private List<Loan> getCars()
	{
		if(db != null)
			return db.selectAll();
		else return null;
	}
	
	public void onClick_delete(View view)
	{
		showSaveDialog(this);
	}
	
	//replace AlertDialog with AddLoanWindow
	private void showSaveDialog(final Context context)
	{
		AlertDialog.Builder alert = new AlertDialog.Builder(this, R.style.mutui_blue);

		alert.setTitle("Are you sure you want to delete all loans?");

		// Set an EditText view to get user input 

		alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
		  public void onClick(DialogInterface dialog, int whichButton) {
		  }
		});
		
		alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
		public void onClick(DialogInterface dialog, int whichButton) {
			Toast toast = Toast.makeText(context, "Database cleared!", Toast.LENGTH_SHORT);
			toast.show();
			db.clear();
			loadCars();
			adapter.notifyDataSetChanged();

		  }
		});
		alert.show();
	}

}
