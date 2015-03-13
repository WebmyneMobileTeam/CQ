package com.example.computerquiz.ui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.computerquiz.R;
import com.example.computerquiz.helpers.DatabaseHelper;
import com.example.computerquiz.model.Level;
import com.example.computerquiz.model.Test;

import java.util.ArrayList;

public class TestListActivity extends ActionBarActivity {

    private ArrayList<Test> tests;
    private ProgressDialog pausingDialog;
    private DatabaseHelper dbHelper;
    private Toolbar toolbar;
    private ListView lvTests;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_list);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setTitle("Select Level");
            setSupportActionBar(toolbar);
        }
        toolbar.setNavigationIcon(R.drawable.ic_launcher);
        lvTests = (ListView)findViewById(R.id.listTests);
        new fetchLevels().execute();


    }

    private class fetchLevels extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Void doInBackground(Void... params) {

            dbHelper = new DatabaseHelper(TestListActivity.this);
            tests = dbHelper.getAllTests();
            dbHelper.close();

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

           fillTests();
        }
    }

    private void fillTests() {


        TestAdapter adapter = new TestAdapter(TestListActivity.this,tests);
        lvTests.setAdapter(adapter);

    }

    public class TestAdapter extends BaseAdapter {
        private final Activity context;
        private final ArrayList<Test> programs;

        class ViewHolder {

            public TextView title;
            public TextView subTitle;
            public TextView date;

        }

        public TestAdapter(Activity context, ArrayList<Test> programs) {
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
                rowView = inflater.inflate(R.layout.item_test, null);
                // configure view holder
                ViewHolder viewHolder = new ViewHolder();
                viewHolder.title = (TextView) rowView.findViewById(R.id.txtTestItemTitle);
                viewHolder.subTitle = (TextView) rowView.findViewById(R.id.txtTestItemSubTitle);
                viewHolder.date = (TextView) rowView.findViewById(R.id.txtTestItemDate);
                rowView.setTag(viewHolder);

            }

            ViewHolder holder = (ViewHolder) rowView.getTag();
            Test test = programs.get(position);

            if(test.isPassed == true){
                holder.title.setText("Passed");
            }else{
                holder.title.setText("Failed");
            }

            holder.date.setText(test.generated);
            holder.subTitle.setText(String.format("Result : %d / %d",test.correct_questions,test.total_questions));


            return rowView;
        }
    }


}
