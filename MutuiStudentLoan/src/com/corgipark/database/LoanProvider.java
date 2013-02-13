package com.corgipark.database;
import java.util.List;

import android.content.Context;
import android.util.Log;

import com.corgipark.mutui.car.Loan;
import com.db4o.ObjectSet;

public class LoanProvider extends Db4oHelper {
	
	public LoanProvider(Context context)
	{
		super(context);
	}
	
	public void store(Loan car)
	{
		db().store(car);
		db().commit();
	}
	
	public void delete(Loan loan)
	{
		if(loan != null)
		{
			ObjectSet<?> results = db().queryByExample(new Loan(loan.getDescription(), 0));
			if(results.hasNext())	 
			{
			     loan =(Loan) results.next(); 
			     Log.d("Database Operation", "Deleting: " + loan.getDescription());
			     db().delete(loan);
			}
			else
			{
				Log.d("Database Operation", "Could not delete object.");
			}
		}
		else Log.d("Database Operation", "Object is null");
	}
	
	public Loan get(Loan loan)
	{
		ObjectSet<?> results = db().queryByExample(new Loan(loan.getDescription(), 0));
		loan = (Loan) results.next();
		return loan;
	}
	
	public void update(Loan loan)
	{
		store(get(loan));
	}
	
	@Override
	public List<Loan> selectAll()
	{
		return (List<Loan>)db().query(Loan.class);
	}
	
}
