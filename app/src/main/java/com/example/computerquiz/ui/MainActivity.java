package com.example.computerquiz.ui;



import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.view.Menu;

import com.example.computerquiz.R;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		call();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	public void call()
	{
		Handler mHandler = new Handler();

		mHandler.post(new Runnable() {
            public void run() {
                func();
            }
        });
		
	}
	public void func()
	{
/*		final ProgressDialog pausingDialog = ProgressDialog.show(this, "", "Loading..", true);	//display the loading button
		new Thread() {
			public void run() {
				try {
					Thread.sleep(1000);			//Lets the thread sleep for 5000msec
					
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} // The length to 'pause' for				
				pausingDialog.dismiss();
			}
			
		}.start();		*/		//Starts the thread


		Intent i=new Intent(this,HomeActivity.class);	//Divert the control to Categories after 5000msec automatically
        startActivity(i);
        finish();
	}

}
