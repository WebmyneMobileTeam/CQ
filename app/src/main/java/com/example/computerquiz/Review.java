package com.example.computerquiz;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class Review extends Activity {

	String arr[]=new String[15];
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_review);
		Intent i=getIntent();
		arr=i.getStringArrayExtra("arr");		//getting the array with questions and right options
		call();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.review, menu);
		return true;
	}
	public void call()
	{
		ListView lv=(ListView)findViewById(R.id.listView1);		//using list view to display as a list
		ArrayList<String> a1=new ArrayList<String>();
		for(int i=0;i<15;i++)
		{
			a1.add(arr[i]);		//Adding all question and answers to array list
		}
		ArrayAdapter<String> ad=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,a1);		//using array list to display the list of questions and correct answers
		lv.setAdapter(ad);
	}
	public void end(View v)		//what to do on clicking the button
	{
		finish();		//ending the activity so it goes to the subcategory page again
	}

}
