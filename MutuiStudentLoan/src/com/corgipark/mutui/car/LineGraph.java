package com.corgipark.mutui.car;

import java.math.BigDecimal;
import java.util.ArrayList;

import org.achartengine.ChartFactory;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint.Align;

public class LineGraph {

	public Intent getIntent(Context context, Loan loan)
	{
		ArrayList<LoanUtils.Tuple<BigDecimal, BigDecimal, BigDecimal>> list = new ArrayList<LoanUtils.Tuple<BigDecimal, BigDecimal, BigDecimal>>();
		
		XYSeries principalSeries = new XYSeries("Principal", 0);
		XYSeries interestSeries = new XYSeries("Interest", 0);
		XYSeries balanceSeries = new XYSeries("Balance", 1);
		
		list = LoanUtils.calculateAmortizationSchedule(loan);
		for(int i = 0; i < loan.getTerm(); i++)
		{
			principalSeries.add(i,list.get(i).x.doubleValue()); //principal payment
			interestSeries.add(i, list.get(i).y.doubleValue()); //interest payment
			balanceSeries.add(i, list.get(i).z.doubleValue());  //remaining balance
		}

		XYMultipleSeriesDataset mDataset2 = new XYMultipleSeriesDataset();
		mDataset2.addSeries(principalSeries);
		mDataset2.addSeries(interestSeries);
		mDataset2.addSeries(balanceSeries);
		
		XYMultipleSeriesRenderer mRenderer2 = new XYMultipleSeriesRenderer(2);
		
		
		
		/*
		 * Principal series renderer
		 */
		XYSeriesRenderer renderer = new XYSeriesRenderer();
		renderer.setColor(Color.WHITE);
		renderer.setFillPoints(true);
		renderer.setPointStyle(PointStyle.POINT);
		renderer.setLineWidth(3f);
		/*
		 * Interest series renderer
		 */
		XYSeriesRenderer renderer2 = new XYSeriesRenderer();
		renderer2.setColor(Color.YELLOW);
		renderer2.setFillPoints(true);
		renderer2.setPointStyle(PointStyle.POINT);
		renderer2.setLineWidth(3f);

		/*
		 * Balance series renderer
		 */
		XYSeriesRenderer renderer3 = new XYSeriesRenderer();
		renderer3.setColor(Color.RED);
		renderer3.setFillPoints(true);
		renderer3.setPointStyle(PointStyle.POINT);
		renderer3.setLineWidth(3f);
		/*
		 * add to multirenderer
		 */
		mRenderer2.addSeriesRenderer(renderer);
		mRenderer2.addSeriesRenderer(renderer2);
		mRenderer2.addSeriesRenderer(renderer3);
		
		/*
		 * graph properties
		 */
	    mRenderer2.setApplyBackgroundColor(true);
	    mRenderer2.setBackgroundColor(Color.argb(100, 50, 50, 50));

	    mRenderer2.setAxesColor(Color.LTGRAY);
	    //mRenderer.setZoomButtonsVisible(true);
	    mRenderer2.setPointSize(5);
		
		mRenderer2.setChartTitle("Loan Amortization Schedule");
		mRenderer2.setXTitle("Period (Payment Number)");
		mRenderer2.setYTitle("Payment Amount", 0);
		mRenderer2.setXLabelsAlign(Align.RIGHT);
	    mRenderer2.setYLabelsAlign(Align.RIGHT);
		
	    mRenderer2.setShowGrid(true);
	    
	    //second y-axis
		mRenderer2.setYTitle("Remaining Balance", 1);
		mRenderer2.setGridColor(Color.BLUE);
		mRenderer2.setYAxisAlign(Align.RIGHT, 1);
	    mRenderer2.setYLabelsAlign(Align.LEFT, 1);
	    
	    mRenderer2.setMargins(new int[] { 100, 50, 0, 50 });//top, left, bottom, right
	    //text styles
		mRenderer2.setChartTitleTextSize(50);
		//mRenderer.setLabelsTextSize(30);
		mRenderer2.setAxisTitleTextSize(30);
	    
	    mRenderer2.setClickEnabled(true);
	    
		Intent intent = ChartFactory.getLineChartIntent(context,  mDataset2,  mRenderer2, "Amortization Graph");
		return intent;
	}
}
