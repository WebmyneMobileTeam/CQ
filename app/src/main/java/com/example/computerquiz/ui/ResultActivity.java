package com.example.computerquiz.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.computerquiz.R;

public class ResultActivity extends Activity {


	private boolean isPassed;
	private int totalQuestions;
	private int correctQuestions;

	private TextView txtResultNumber;
	private ImageView imgResult;
	private TextView txtResult;

    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_result);

		Intent i=getIntent();
		isPassed = i.getBooleanExtra("isPassed",false);
		totalQuestions = i.getIntExtra("total", 0);
		correctQuestions = i.getIntExtra("correct",0);

		txtResult = (TextView)findViewById(R.id.txtResult);
		txtResultNumber = (TextView)findViewById(R.id.txtResultNumber);
		imgResult = (ImageView)findViewById(R.id.imgResult);

		setupResult();


	}

	@Override
	public void onBackPressed() {
		// super.onBackPressed();

		Intent i = new Intent(ResultActivity.this,HomeActivity.class);
		i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(i);
	}

	private void setupResult() {

		if (isPassed == true){
			imgResult.setImageResource(R.drawable.ic_keyboard_alt);
			txtResult.setText("You Passed");
		}else{
			imgResult.setImageResource(R.drawable.ic_thumb_down);
			txtResult.setText("You Failed");
		}

		txtResultNumber.setText(String.format("%d/%d",correctQuestions,totalQuestions));



	}


}
