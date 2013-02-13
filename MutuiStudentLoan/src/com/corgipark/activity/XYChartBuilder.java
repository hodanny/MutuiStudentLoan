/**
 * Copyright (C) 2009, 2010 SC 4ViewSoft SRL
 *  
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *  
 *      http://www.apache.org/licenses/LICENSE-2.0
 *  
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.corgipark.activity;

import java.math.BigDecimal;
import java.util.ArrayList;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint.Align;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;

import com.corgipark.mutui.car.Loan;
import com.corgipark.mutui.car.LoanUtils;
import com.corgipark.mutui.car.R;

public class XYChartBuilder extends Activity {
  public static final String TYPE = "type";
  ArrayList<LoanUtils.Tuple<BigDecimal, BigDecimal, BigDecimal>> list;

  private GraphicalView mChartView;
  private GraphicalView mChartView2;
  
  Loan loan;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    
    setContentView(R.layout.xy_chart);

    loan = (Loan) getIntent().getSerializableExtra("LoanSummary");
	list = LoanUtils.calculateAmortizationSchedule(loan);
  }

  public void onClick_schedule(View view)
  {
	  Intent intent = new Intent(this, AmortizationScheduleActivity.class);
	  intent.putExtra("LoanObject", loan);
	  startActivity(intent);
  }
  @SuppressWarnings("deprecation")
@Override
  protected void onResume() {
    super.onResume();
 
	XYSeries principalSeries = new XYSeries("Principal", 0);
	XYSeries interestSeries = new XYSeries("Interest", 0);
	XYSeries balanceSeries = new XYSeries("Balance", 0);
	
	for(int i = 0; i < loan.getTerm(); i++)
	{
		principalSeries.add(i,list.get(i).x.doubleValue()); //principal payment
		interestSeries.add(i, list.get(i).y.doubleValue()); //interest payment
		balanceSeries.add(i, list.get(i).z.doubleValue());  //remaining balance
	}

	XYMultipleSeriesDataset mDataset = new XYMultipleSeriesDataset();
	XYMultipleSeriesDataset mDataset2 = new XYMultipleSeriesDataset();
	mDataset2.addSeries(principalSeries);
	mDataset2.addSeries(interestSeries);
	mDataset.addSeries(balanceSeries);
	
	XYMultipleSeriesRenderer mRenderer = new XYMultipleSeriesRenderer();
	XYMultipleSeriesRenderer mRenderer2 = new XYMultipleSeriesRenderer();
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
	mRenderer.addSeriesRenderer(renderer3);
	
	/*
	 * graph1 properties
	 */
	setGraphProperties(mRenderer2, "Period (Payment Number)", "Payment Amount", "Principal/Interest Payment Breakdown");
    
	/*
	 * graph2 properties
	 */
    //second y-axis
	
    setGraphProperties(mRenderer, "Period (Payment Number)", "Remaining Balance", "Remaining on Principal Balance");
    
    if (mChartView == null) {
      LinearLayout layout = (LinearLayout) findViewById(R.id.chart);
      mChartView = ChartFactory.getLineChartView(this, mDataset2, mRenderer2);
      layout.addView(mChartView, new LayoutParams(LayoutParams.FILL_PARENT,
          LayoutParams.FILL_PARENT));
    }
    if (mChartView2 == null) {
        LinearLayout layout = (LinearLayout) findViewById(R.id.chart2);
        mChartView2 = ChartFactory.getLineChartView(this, mDataset, mRenderer);
        layout.addView(mChartView2, new LayoutParams(LayoutParams.FILL_PARENT,
            LayoutParams.FILL_PARENT));
      }
  }
  public void setGraphProperties(XYMultipleSeriesRenderer mRenderer2, String XLabel, String YLabel, String Title)
  {
		mRenderer2.setChartTitle(Title);
	    mRenderer2.setXTitle(XLabel);
	    mRenderer2.setYTitle(YLabel);
	    mRenderer2.setApplyBackgroundColor(true);
	    mRenderer2.setBackgroundColor(Color.argb(100, 50, 50, 50));

	    mRenderer2.setAxesColor(Color.LTGRAY);
	    //mRenderer.setZoomButtonsVisible(true);
	    mRenderer2.setPointSize(5);
		
		mRenderer2.setXLabelsAlign(Align.RIGHT);
	    mRenderer2.setYLabelsAlign(Align.RIGHT);
		
	    mRenderer2.setShowGrid(true);
	    
	    mRenderer2.setMargins(new int[] { 100, 50, 0, 50 });//top, left, bottom, right
	    //text styles
		mRenderer2.setChartTitleTextSize(50);
		mRenderer2.setAxisTitleTextSize(30);
	    mRenderer2.setClickEnabled(true);
		mRenderer2.setGridColor(Color.BLUE);
		mRenderer2.setChartTitleTextSize(20);
  }

}
