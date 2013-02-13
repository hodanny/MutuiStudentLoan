package com.corgipark.mutui.car;
import java.text.NumberFormat;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.corgipark.mutui.car.R;

public class LoanAdapter extends ArrayAdapter<Loan>{

    Context context; 
    int layoutResourceId;    
    ArrayList<Loan> data = null;
    
    public LoanAdapter(Context context, int layoutResourceId, ArrayList<Loan> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        LoanHolder holder = null;
        
        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            
            holder = new LoanHolder();
            holder.imgIcon = (ImageView)row.findViewById(R.id.imgIcon);
            holder.paymentTitle = (TextView)row.findViewById(R.id.paymentTitle);
            holder.txtTitle = (TextView)row.findViewById(R.id.txtTitle);
            
            row.setTag(holder);
        }
        else
        {
            holder = (LoanHolder)row.getTag();
        }
        
        Loan loan = data.get(position);
        holder.txtTitle.setText(loan.getDescription());
        
        double mPayment = LoanUtils.calculateMonthlyPayment(loan);
        
        holder.paymentTitle.setText(NumberFormat.getCurrencyInstance().format(LoanUtils.calculateMonthlyPayment(loan)));
        if(Global.affordability > mPayment)
		{
            holder.paymentTitle.setTextColor(Color.GREEN);            
		}
        else { holder.paymentTitle.setTextColor(Color.RED); }
        holder.imgIcon.setImageResource(loan.icon);
        return row;
    }
    
    static class LoanHolder
    {
        TextView txtTitle;
        TextView paymentTitle;
        ImageView imgIcon;
    }
}