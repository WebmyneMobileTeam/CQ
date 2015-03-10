package com.example.computerquiz.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import com.example.computerquiz.R;

public class SubCategoryActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sub_category);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.sub_category, menu);
		return true;
	}
	public void call(View v)		//what to do when the button is clicked for now all 5 sub categories are linked to same function 
	{
		int a[]=new int[15];
		Intent i=new Intent(this,QuestionActivity.class);		//going to questions
		i.putExtra("q", "1");
		i.putExtra("choice",a );
        startActivity(i);
	}

}
