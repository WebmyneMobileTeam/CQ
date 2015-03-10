package com.example.computerquiz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

public class Categories extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_categories);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.categories, menu);
		return true;
	}
	public void but1(View v)
	{
		Intent i=new Intent(this,SubCategory.class);	//Web and Inteface button on being clicked diverted to SubCategory page
        startActivity(i);
	}
	
	public void but2(View v)
	{
		Intent i=new Intent(this,SubCategory.class);	//Web and Inteface button on being clicked diverted to SubCategory page
        startActivity(i);
	}
	
	public void but3(View v)
	{
		Intent i=new Intent(this,SubCategory.class);	//Web and Inteface button on being clicked diverted to SubCategory page
        startActivity(i);
		
	}

}
