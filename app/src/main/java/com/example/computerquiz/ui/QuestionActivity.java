package com.example.computerquiz.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.computerquiz.R;
import com.example.computerquiz.helpers.DatabaseHelper;
import com.example.computerquiz.model.Question;

import java.util.ArrayList;

public class QuestionActivity extends ActionBarActivity {

    private Toolbar toolbar;
    private int current_question = 0;
    private TextView txtNext;
    private TextView txtPrevious;
    private TextView txtQuestionNumber;
    private int selected_level;
    private int selected_category;
    private ArrayList<Question> questions;
    private DatabaseHelper dbHelper;
    private TextView txtQuestionDescription;
    private RadioGroup radioGroup;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_question);
        init();
        setupToolBar();

        selected_category = getIntent().getIntExtra("selected_category",0);
        selected_level = getIntent().getIntExtra("selected_level",0);

        new fetchQuestions().execute();

	}

    @Override
    protected void onResume() {
        super.onResume();


    }

    private void init() {

        txtNext = (TextView)findViewById(R.id.txtNextQuestion);
        txtPrevious = (TextView)findViewById(R.id.txtPreviousQuestion);
        txtQuestionNumber = (TextView)findViewById(R.id.txtQuestionNumber);
        txtQuestionDescription = (TextView)findViewById(R.id.txtQuestionDescription);
        radioGroup = (RadioGroup)findViewById(R.id.radioGroup);

        txtPrevious.setOnClickListener(previousListner);
        txtNext.setOnClickListener(nextListner);
    }

    private View.OnClickListener nextListner = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            if(current_question == questions.size()-1){
                Toast.makeText(QuestionActivity.this, "Finish", Toast.LENGTH_SHORT).show();
            }else{
                current_question = current_question+1;
                setupQuestion(current_question);
            }

        }
    };

    private View.OnClickListener previousListner = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            if(current_question == 0){
                Toast.makeText(QuestionActivity.this, "First", Toast.LENGTH_SHORT).show();
            }else{
                current_question = current_question-1;
                setupQuestion(current_question);
            }

        }
    };

    private void setupToolBar() {

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setTitle("Test Page");
            setSupportActionBar(toolbar);
        }
        toolbar.setNavigationIcon(R.drawable.ic_launcher);

    }


    private class fetchQuestions extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Void doInBackground(Void... params) {

            dbHelper = new DatabaseHelper(QuestionActivity.this);
            questions = dbHelper.getQuestions(selected_category, selected_level);
            dbHelper.close();

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if(questions.isEmpty()){
                Toast.makeText(QuestionActivity.this, "No Questions", Toast.LENGTH_SHORT).show();
            }else{
                setupQuestion(current_question);
            }
        }
    }

    private void setupQuestion(int current_question) {

        if(current_question == 0){
            txtPrevious.setVisibility(View.INVISIBLE);
        }else{
            txtPrevious.setVisibility(View.VISIBLE);
        }

        if(current_question == questions.size()-1){

            txtNext.setText("Finish");
        }else{
            txtNext.setText("Next");
        }

        txtQuestionNumber.setText(String.format("Question %d / %d",current_question+1,questions.size()));

        // fill question details

        Question selectedQuestion = questions.get(current_question);
        txtQuestionDescription.setText(selectedQuestion.description);
        fillOptions(selectedQuestion.getOptions());




    }

    private void fillOptions(ArrayList<String> options) {

        radioGroup.removeAllViews();
        radioGroup.invalidate();
        RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        for (int i=0;i<options.size();i++){

            RadioButton button = new RadioButton(QuestionActivity.this);
            button.setText(options.get(i));
            radioGroup.addView(button,params);
        }


    }


}
