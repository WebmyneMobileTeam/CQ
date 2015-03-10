package com.example.computerquiz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class ResultAnalysis extends Activity {

	String score="0";
	String arr[]=new String[15];
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_result_analysis);
		Intent i=getIntent();		//getting the values sent
		score=i.getStringExtra("score");
		arr=i.getStringArrayExtra("arr");
		call();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.result_analysis, menu);
		return true;
	}
	public void call()
	{
		int s=Integer.parseInt(score);		//convert the score into integer
		double sc=(s*1.0/15.0)*100.0;		//calculating the percentage 
		TextView tv1=(TextView)findViewById(R.id.textView2);		//binding the text to alter it
		TextView tv2=(TextView)findViewById(R.id.textView4);
		tv1.setText("TOTAL = "+score+"/15");		//display the score in first format
		tv2.setText("PERCENTAGE = "+sc);			//display the score in second format
	}
	public void review(View v)		//what to do when review button is clicked
	{
		Intent i=new Intent(this,Review.class);		//go to review of the page
		i.putExtra("arr", arr);
		startActivity(i);
		finish();
	}

}
