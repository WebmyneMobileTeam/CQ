package com.example.computerquiz.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import com.example.computerquiz.R;

public class ResultActivity extends Activity {

	String score="";
	String arr[]=new String[15];
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_result);
		Intent i=getIntent();
		score=i.getStringExtra("score");
		System.out.println(score);
		arr=i.getStringArrayExtra("arr");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.result, menu);
		return true;
	}
	public void call(View v)		//what to do when button is clicked
	{
		Intent i=new Intent(this,ResultAnalysisActivity.class);		//move to the Result analysis
		i.putExtra("score", score);
		i.putExtra("arr", arr);
        startActivity(i);
        finish();
	}

}
