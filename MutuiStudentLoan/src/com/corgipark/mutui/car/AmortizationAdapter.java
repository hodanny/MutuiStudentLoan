package com.corgipark.mutui.car;
import java.math.BigDecimal;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.corgipark.mutui.car.R;

public class AmortizationAdapter extends ArrayAdapter<LoanUtils.Tuple<BigDecimal, BigDecimal, BigDecimal>> {

    Context context; 
    int layoutResourceId;    
    ArrayList<LoanUtils.Tuple<BigDecimal, BigDecimal, BigDecimal>>  data = null;
    
    public AmortizationAdapter(Context context, int layoutResourceId, ArrayList<LoanUtils.Tuple<BigDecimal, BigDecimal, BigDecimal>> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        AmortizationHolder holder = null;
        
        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            
            holder = new AmortizationHolder();
            holder.paymentNum = (TextView)row.findViewById(R.id.aSchedule_lv_payment_number);
            holder.principal = (TextView)row.findViewById(R.id.aSchedule_lv_principal);
            holder.interest = (TextView)row.findViewById(R.id.aSchedule_lv_interest);
            holder.balance = (TextView)row.findViewById(R.id.aSchedule_lv_balance);
            row.setTag(holder);
        }
        else
        {
            holder = (AmortizationHolder)row.getTag();
        }
        
        LoanUtils.Tuple<BigDecimal, BigDecimal, BigDecimal> tuple = data.get(position);
        
        holder.paymentNum.setText(Integer.toString(position + 1));
        holder.principal.setText(tuple.x.toString());
        holder.interest.setText(tuple.y.toString());
        holder.balance.setText(tuple.z.toString());
        
        return row;
    }
    
    static class AmortizationHolder
    {
    	TextView paymentNum;
        TextView principal;
        TextView interest;
        TextView balance;
    }
}