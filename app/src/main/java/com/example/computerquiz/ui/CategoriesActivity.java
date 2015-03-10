package com.example.computerquiz.ui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.computerquiz.R;
import com.example.computerquiz.helpers.DatabaseHelper;
import com.example.computerquiz.model.Category;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class CategoriesActivity extends ActionBarActivity {

    private ArrayList<Category> categories;
    private ProgressDialog pausingDialog;
    private DatabaseHelper dbHelper;
    private Toolbar toolbar;
    private ListView lvCategories;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
        init();
        setupToolBar();
        new fetchCategories().execute();
    }

    private void init() {

        lvCategories = (ListView) findViewById(R.id.listCategories);
    }

    private void setupToolBar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {

            toolbar.setTitle("Welcome User,");

            setSupportActionBar(toolbar);
        }
        toolbar.setNavigationIcon(R.drawable.ic_launcher);

    }

    private class fetchCategories extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Void doInBackground(Void... params) {

            dbHelper = new DatabaseHelper(CategoriesActivity.this);
            categories = dbHelper.getAllCategories();
            dbHelper.close();

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            fillCategories();
        }
    }

    private void fillCategories() {

        CategoryAdapter adapter = new CategoryAdapter(CategoriesActivity.this,categories);
        lvCategories.setAdapter(adapter);

    }

    public class CategoryAdapter extends BaseAdapter {
        private final Activity context;
        private final ArrayList<Category> programs;

        class ViewHolder {
            public CardView cardView;
            public TextView txtTitleCategoryName;

        }

        public CategoryAdapter(Activity context, ArrayList<Category> programs) {
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
                rowView = inflater.inflate(R.layout.item_category, null);
                // configure view holder
                ViewHolder viewHolder = new ViewHolder();
                viewHolder.cardView = (CardView) rowView.findViewById(R.id.card_view);
                viewHolder.txtTitleCategoryName = (TextView)rowView.findViewById(R.id.txtTitleCategoryName);
                rowView.setTag(viewHolder);

            }
            ViewHolder holder = (ViewHolder) rowView.getTag();
            Category eachCategory = categories.get(position);

            holder.txtTitleCategoryName.setText(String.format("%d. %s",position+1,eachCategory.category_name));

            return rowView;
        }
    }


}
