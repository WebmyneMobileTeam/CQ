package com.example.computerquiz.ui;

import android.app.Activity;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.computerquiz.R;
import com.example.computerquiz.helpers.ComplexPreferences;
import com.example.computerquiz.helpers.Prefs;
import com.example.computerquiz.model.Category;
import com.example.computerquiz.model.Question;
import com.example.computerquiz.model.Test;

import java.util.ArrayList;
import java.util.Set;

public class ReviewActivity extends ActionBarActivity {

    private Toolbar toolbar;
    private Test currentTest;
    private ListView lvReview;
    private ImageView globalImageview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setTitle("Test Review");
            setSupportActionBar(toolbar);
        }
        toolbar.setNavigationIcon(R.drawable.ic_launcher);
        globalImageview = (ImageView) findViewById(R.id.globalImageView);
        ComplexPreferences complexPreferences = ComplexPreferences.getComplexPreferences(ReviewActivity.this, "mypref", MODE_PRIVATE);
        ;
        currentTest = complexPreferences.getObject("currentTest", Test.class);
        complexPreferences.commit();
        lvReview = (ListView) findViewById(R.id.lvReview);
    }

    @Override
    protected void onResume() {
        super.onResume();
        updatePreferences();
        fillQuestions();
    }

    private void fillQuestions() {

        ReviewQuestionsAdapter adapter = new ReviewQuestionsAdapter(ReviewActivity.this, currentTest.questions);
        lvReview.setAdapter(adapter);

    }

    private void updatePreferences() {
        String color = Prefs.with(ReviewActivity.this).getString("back", "#494949");
        globalImageview.setBackgroundColor(Color.parseColor(color));
    }

    public class ReviewQuestionsAdapter extends BaseAdapter {
        private final Activity context;
        private final ArrayList<Question> programs;

        class ViewHolder {
            public TextView txtTitleQuestionName;
            public LinearLayout linearInnerQuestions;

        }

        public ReviewQuestionsAdapter(Activity context, ArrayList<Question> programs) {
            this.context = context;
            this.programs = programs;

        }

        @Override
        public int getCount() {
            return programs.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View rowView = convertView;
            // reuse views
            if (rowView == null) {
                LayoutInflater inflater = context.getLayoutInflater();
                rowView = inflater.inflate(R.layout.item_review, null);
                // configure view holder
                ViewHolder viewHolder = new ViewHolder();
                viewHolder.txtTitleQuestionName = (TextView) rowView.findViewById(R.id.txtTitleQuestionName);
                viewHolder.linearInnerQuestions = (LinearLayout) rowView.findViewById(R.id.linearInnerQuestions);
                rowView.setTag(viewHolder);

            }
            ViewHolder holder = (ViewHolder) rowView.getTag();
            Question question = programs.get(position);

            holder.txtTitleQuestionName.setText(String.format("Question : %s", question.description));

            ArrayList<String> options = programs.get(position).getOptions();
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

            Set keys = currentTest.answersMap.keySet();

            holder.linearInnerQuestions.removeAllViews();
            for (int i = 0; i < options.size(); i++) {
                TextView tv = new TextView(context);
                tv.setText(String.format("%d. %s", i + 1, options.get(i)));
                holder.linearInnerQuestions.addView(tv, params);
                if(i == programs.get(position).correct_answer-1){
                    tv.setBackgroundColor(Color.GREEN);
                }
            }




            if(keys.contains(position)){
                int answeredPosition = currentTest.answersMap.get(position);
                TextView tv = (TextView)holder.linearInnerQuestions.getChildAt(answeredPosition);

                if(answeredPosition == programs.get(position).correct_answer-1){

                }else{
                    tv.setBackgroundColor(Color.RED);
                }

            }

            return rowView;
        }
    }


}
