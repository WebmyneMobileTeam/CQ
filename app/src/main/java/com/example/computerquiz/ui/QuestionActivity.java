package com.example.computerquiz.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.computerquiz.R;
import com.example.computerquiz.helpers.ComplexPreferences;
import com.example.computerquiz.helpers.DatabaseHelper;
import com.example.computerquiz.helpers.Prefs;
import com.example.computerquiz.model.Question;
import com.example.computerquiz.model.Test;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

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
    private HashMap<Integer,Integer> answersMap;
    public static final String DATE_FORMAT_NOW = "dd-MM-yyyy HH:mm";
    private ImageView globalImageview;


    int  totalQuestions;
    int correctQuestions;
    int inCorrectQuestions;
    float percentage;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_question);
        init();
        setupToolBar();
        globalImageview = (ImageView) findViewById(R.id.globalImageView);
        selected_category = getIntent().getIntExtra("selected_category",0);
        selected_level = getIntent().getIntExtra("selected_level",0);
        new fetchQuestions().execute();
        answersMap = new HashMap<>();

	}

    @Override
    protected void onResume() {
        super.onResume();
        updatePreferences();

    }

    private void updatePreferences() {
        String color = Prefs.with(QuestionActivity.this).getString("back", "#494949");
        globalImageview.setBackgroundColor(Color.parseColor(color));
    }

    public static String now() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
        return sdf.format(cal.getTime());
    }
    private void init() {

        txtNext = (TextView)findViewById(R.id.txtNextQuestion);
        txtPrevious = (TextView)findViewById(R.id.txtPreviousQuestion);
        txtQuestionNumber = (TextView)findViewById(R.id.txtQuestionNumber);
        txtQuestionDescription = (TextView)findViewById(R.id.txtQuestionDescription);
        radioGroup = (RadioGroup)findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener(checkedChangeListner);

        txtPrevious.setOnClickListener(previousListner);
        txtNext.setOnClickListener(nextListner);
    }

    private RadioGroup.OnCheckedChangeListener checkedChangeListner = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            answersMap.put(current_question,checkedId);
        }
    };

    private View.OnClickListener nextListner = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            if(current_question == questions.size()-1){

                processFinish();
            }else{
                current_question = current_question+1;
                setupQuestion(current_question);
            }
        }
    };


    private void processFinish() {

          totalQuestions = questions.size();
          correctQuestions = 0;
          inCorrectQuestions = 0;
          percentage = 0;

        new AsyncTask<Void, Void, Void>() {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected Void doInBackground(Void... params) {


                Set keys = answersMap.keySet();
                for (int i=0;i<questions.size();i++){

                    if(keys.contains(i)){

                        Log.e("Answer :",String.format("Question %d is answered and given answer is %d",i,answersMap.get(i)));
                        if(questions.get(i).correct_answer == answersMap.get(i)+1){
                            Log.e("Correct/Incorrect :","Correct");
                            correctQuestions = correctQuestions + 1;
                        }else{
                            Log.e("Correct/Incorrect :","InCorrect");
                            inCorrectQuestions = inCorrectQuestions + 1;
                        }

                    }else{
                        inCorrectQuestions = inCorrectQuestions + 1;
                        Log.e("Answer :",String.format("Question %d is not answered",i));
                    }
                }


                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);

                percentage = (float)((correctQuestions*100)/totalQuestions);

                boolean isPassed = false;

                if(percentage>=35.0){

                    isPassed = true;
                    Toast.makeText(QuestionActivity.this, "Pass", Toast.LENGTH_SHORT).show();
                }else{

                    isPassed = false;
                    Toast.makeText(QuestionActivity.this, "Fail", Toast.LENGTH_SHORT).show();
                }

                Test currentTest = new Test();
                currentTest.category_id = selected_category;
                currentTest.level_id = selected_level;
                currentTest.correct_questions = correctQuestions;
                currentTest.total_questions = totalQuestions;
                currentTest.isPassed = isPassed;
                currentTest.generated = now();
                currentTest.questions = questions;

                DatabaseHelper helper = new DatabaseHelper(QuestionActivity.this);
                helper.insertTest(currentTest);
                helper.close();

                ComplexPreferences complexPreferences = ComplexPreferences.getComplexPreferences(QuestionActivity.this, "mypref", MODE_PRIVATE);;
                complexPreferences.putObject("currentTest", currentTest);
                complexPreferences.commit();

                Intent i = new Intent(QuestionActivity.this,ResultActivity.class);
                i.putExtra("isPassed",isPassed);
                i.putExtra("total",totalQuestions);
                i.putExtra("correct",correctQuestions);
                startActivity(i);


            }
        }.execute();

        Log.e("Result", String.format("Correct Are %d AND Incorrect Are %d", correctQuestions, inCorrectQuestions));

    }

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

    @Override
    public void onBackPressed() {
       // super.onBackPressed();

        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Wait..");
        alert.setMessage("Current test will lost. ");
        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {


                Intent i = new Intent(QuestionActivity.this,HomeActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);

            }
        });

        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();

            }
        });

        alert.show();
    }

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
        fillOptions(selectedQuestion.getOptions(),current_question);

    }

    @Override
    protected void onStop() {
        super.onStop();
        answersMap.clear();
        questions.clear();
    }

    private void fillOptions(ArrayList<String> options,int current_question) {

        radioGroup.removeAllViews();
        RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        for (int i=0;i<options.size();i++){

            RadioButton button = new RadioButton(QuestionActivity.this);
            button.setText(options.get(i));
            button.setId(i);
            button.setTextColor(Color.WHITE);
            radioGroup.addView(button,params);
        }

        Set keys = answersMap.keySet();
        if(keys.contains(current_question)){
                radioGroup.check(questions.get(current_question).correct_answer - 1);
        }
            radioGroup.invalidate();


    }


}
