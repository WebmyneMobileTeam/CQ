package com.example.computerquiz.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.computerquiz.R;

public class QuestionActivity extends Activity {

	int n=0;			//Storing the question number
	int score=0;		//Storing the total score
	int choice[]=new int[15];		//store the choices selected by the user
	String arr[]=new String[15];	//Store the question and its correct answer
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_question);
		Intent i=getIntent();						//receives the intent sent
		n=Integer.parseInt(i.getStringExtra("q"));	//stores the question number on which the user currently is
		choice=i.getIntArrayExtra("choice");		//store the choices till now selected by the user
		for(int k=0;k<15;k++)
		{
			choice[k]=0;							//initially store all the choices as 0
		}
		call();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.question, menu);
		return true;
	}
	public void call()
	{
		String Questions[]={"Question 1","Question 2","Question 3","Question 4","Question 5","Question 6","Question 7","Question 8","Question 9","Question 10","Question 11","Question 12","Question 13","Question 14","Question 15"};		//contains all the 15 questions
		String option1[]={"Option 1","Option 1","Option 1","Option 1","Option 1","Option 1","Option 1","Option 1","Option 1","Option 1","Option 1","Option 1","Option 1","Option 1","Option 1"};											//contain all the option1 of the questions
		String option2[]={"Option 2","Option 2","Option 2","Option 2","Option 2","Option 2","Option 2","Option 2","Option 2","Option 2","Option 2","Option 2","Option 2","Option 2","Option 2"};											//contain all the option2 of the questions
		String option3[]={"Option 3","Option 3","Option 3","Option 3","Option 3","Option 3","Option 3","Option 3","Option 3","Option 3","Option 3","Option 3","Option 3","Option 3","Option 3"};											//contain all the option3 of the questions
		String option4[]={"Option 4","Option 4","Option 4","Option 4","Option 4","Option 4","Option 4","Option 4","Option 4","Option 4","Option 4","Option 4","Option 4","Option 4","Option 4"};											//contain all the option4 of the questions
		String option5[]={"Option 5","Option 5","Option 5","Option 5","Option 5","Option 5","Option 5","Option 5","Option 5","Option 5","Option 5","Option 5","Option 5","Option 5","Option 5"};											//contain all the option5 of the questions
		int answers[]={1,1,1,1,1,1,1,1,1,1,1,1,1,1,1};		//store the correct option number of the questions
		TextView tv1=(TextView)findViewById(R.id.textView1);		//bind the textview so that its text can be changed
		TextView tv2=(TextView)findViewById(R.id.textView2);
		Button b1=(Button)findViewById(R.id.button1);				//bind the button to change the text on the button with options
		Button b2=(Button)findViewById(R.id.button2);
		Button b3=(Button)findViewById(R.id.button3);
		Button b4=(Button)findViewById(R.id.button4);
		Button b5=(Button)findViewById(R.id.button5);
		Button b6=(Button)findViewById(R.id.button6);
		Button b7=(Button)findViewById(R.id.button7);
		if(n==16)		//if all questions are answered
		{
			int noAnswer=0;		//contain total no of questions left unanswered
			for(int i=0;i<15;i++)
			{
				if(answers[i]==choice[i])		//select all correct answer
				{
					score++;					//for all correct answer increase score by 1
				}
				if(choice[i]==0)				//select all unanswered questions
				{
				noAnswer++;						//for all unanswered question increase count of non answered question by 1
				}
				switch(answers[i])				//for saving all questions with its correct options in array arr for reviewing
				{
				case 1:arr[i]=Questions[i]+"\n"+option1[i];break;
				case 2:arr[i]=Questions[i]+"\n"+option2[i];break;
				case 3:arr[i]=Questions[i]+"\n"+option3[i];break;
				case 4:arr[i]=Questions[i]+"\n"+option4[i];break;
				case 5:arr[i]=Questions[i]+"\n"+option5[i];break;
				
				}
				
			}
			int wrongAnswer=((15-noAnswer)-score);		//calculating total wrong answers
			score=score-wrongAnswer;					//subtracting 1 for all wrongly guessed questions
			if(score<8)									//if student fail
			{
			Intent i=new Intent(this,Result2.class);	//go to Result2 page
			i.putExtra("score", score+"");				//send score of student there
			i.putExtra("arr", arr);						//send questions and correct answers for display at review page
	        startActivity(i);
	        finish();									//ends the current page
			}
			else		//if passes(45%+)
			{
			Intent i=new Intent(this,ResultActivity.class);		//go to Result page
			i.putExtra("score", score+"");
			i.putExtra("arr", arr);
		    startActivity(i);
		    finish();
			}
		}
		if(n>0 && n<16)		//for the questions to be answered ie user still answering
		{
		tv1.setText("Q"+n+" of 15");		//changing the text with respect to the question on same page
		tv2.setText(Questions[n-1]);		//changing the question on same page
		b1.setText(option1[n-1]);			//changing options on the button
		b2.setText(option2[n-1]);
		b3.setText(option3[n-1]);
		b4.setText(option4[n-1]);
		b5.setText(option5[n-1]);
		}
		if(n<=0)
		{
			n=1;
		}
	}
	public void next(View v)		//what to do when next button is clicked(go to next question)
	{
		Intent i=new Intent(this,QuestionActivity.class);
		i.putExtra("q", (n+1)+"");		//next question as n+1 is sent
		i.putExtra("choice", choice);
        startActivity(i);
        finish();
	}
	
	public void previous(View v)	//what to do when previous button is clicked(go to previous question)
	{
		Intent i=new Intent(this,QuestionActivity.class);
		i.putExtra("q", (n-1)+"");		//previous question as n-1 is sent
		i.putExtra("choice", choice);
        startActivity(i);
        finish();
	}
	public void test(View v)
	{
		switch(v.getId())			//checking what option is selected by the user for each question
		{
		case R.id.button1:choice[n-1]=1;break;  // storing the option selected in choice
		case R.id.button2:choice[n-1]=2;break;
		case R.id.button3:choice[n-1]=3;break;
		case R.id.button4:choice[n-1]=4;break;
		case R.id.button5:choice[n-1]=5;break;
		}
		Intent i=new Intent(this,QuestionActivity.class);
		i.putExtra("q", (n+1)+"");		//after selecting a option automatically goes to the next question
		i.putExtra("choice", choice);
        startActivity(i);
        finish();
	}

}
