package com.example.computerquiz.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.computerquiz.R;
import com.example.computerquiz.helpers.ComplexPreferences;
import com.example.computerquiz.helpers.Prefs;
import com.example.computerquiz.model.Test;

public class ResultActivity extends ActionBarActivity {


	private boolean isPassed;
	private int totalQuestions;
	private int correctQuestions;
	private TextView txtResultNumber;
	private ImageView imgResult;
	private TextView txtResult;
	private ImageView globalImageview;
	private Test currentTest;
	private Toolbar toolbar;
	private Button btnReviewResult;

    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_result);

		btnReviewResult = (Button)findViewById(R.id.btnReviewResult);
		btnReviewResult.setOnClickListener(reviewClickListner);

			toolbar = (Toolbar) findViewById(R.id.toolbar);
			if (toolbar != null) {
				toolbar.setTitle("Test Result");
				setSupportActionBar(toolbar);
			}
			toolbar.setNavigationIcon(R.drawable.ic_launcher);

		ComplexPreferences complexPreferences = ComplexPreferences.getComplexPreferences(ResultActivity.this, "mypref", MODE_PRIVATE);;
		currentTest = complexPreferences.getObject("currentTest",Test.class);
		complexPreferences.commit();

		Toast.makeText(ResultActivity.this,""+currentTest.questions.size(),Toast.LENGTH_SHORT).show();
		Intent i=getIntent();

		isPassed = i.getBooleanExtra("isPassed",false);
		totalQuestions = i.getIntExtra("total", 0);
		correctQuestions = i.getIntExtra("correct",0);

		globalImageview = (ImageView) findViewById(R.id.globalImageView);
		txtResult = (TextView)findViewById(R.id.txtResult);
		txtResultNumber = (TextView)findViewById(R.id.txtResultNumber);
		imgResult = (ImageView)findViewById(R.id.imgResult);

		setupResult();
	}

	@Override
	protected void onResume() {
		super.onResume();
		updatePreferences();
	}

	private void updatePreferences() {

		String color = Prefs.with(ResultActivity.this).getString("back", "#494949");
		globalImageview.setBackgroundColor(Color.parseColor(color));


	}

	private View.OnClickListener reviewClickListner = new View.OnClickListener() {
		@Override
		public void onClick(View v) {

		}
	};

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

		txtResultNumber.setText(String.format("Correct Questions : %d\nTotal Questions : %d",currentTest.correct_questions,currentTest.total_questions));



	}


}
