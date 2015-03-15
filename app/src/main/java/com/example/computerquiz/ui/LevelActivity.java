package com.example.computerquiz.ui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.computerquiz.R;
import com.example.computerquiz.helpers.DatabaseHelper;
import com.example.computerquiz.helpers.Prefs;
import com.example.computerquiz.model.Level;

import java.util.ArrayList;

public class LevelActivity extends ActionBarActivity {

    private ArrayList<Level> levels;
    private ProgressDialog pausingDialog;
    private DatabaseHelper dbHelper;
    private Toolbar toolbar;
    private ListView lvLevels;
    private ImageView globalImageview;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_level);
        init();
        setupToolBar();
        new fetchLevels().execute();
	}

    @Override
    protected void onResume() {
        super.onResume();
        updatePreferences();
    }

    private void init() {
        globalImageview = (ImageView) findViewById(R.id.globalImageView);
        lvLevels = (ListView) findViewById(R.id.listLevelss);
        lvLevels.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(LevelActivity.this, QuestionActivity.class);
                i.putExtra("selected_level", levels.get(position)._id);
                i.putExtra("selected_category", levels.get(position).category_id);
                startActivity(i);
                finish();
            }
        });
    }

    private void updatePreferences() {

        String color = Prefs.with(LevelActivity.this).getString("back", "#494949");
        globalImageview.setBackgroundColor(Color.parseColor(color));


    }

    private void setupToolBar() {

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setTitle("Select Level");
            setSupportActionBar(toolbar);
        }
        toolbar.setNavigationIcon(R.drawable.ic_launcher);

    }

    private class fetchLevels extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Void doInBackground(Void... params) {

            dbHelper = new DatabaseHelper(LevelActivity.this);
            levels = dbHelper.getLevels(getIntent().getIntExtra("selected_category",0));
            dbHelper.close();

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            fillLevels();
        }
    }

    private void fillLevels() {
        LevelAdapter adapter = new LevelAdapter(LevelActivity.this,levels);
        lvLevels.setAdapter(adapter);
    }

    public class LevelAdapter extends BaseAdapter {
        private final Activity context;
        private final ArrayList<Level> programs;

        class ViewHolder {
            public CardView cardView;
            public TextView txtTitleCategoryName;

        }

        public LevelAdapter(Activity context, ArrayList<Level> programs) {
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
            Level level = programs.get(position);
            holder.txtTitleCategoryName.setText(String.format("%d. %s",position+1,level.level_name));
            return rowView;
        }
    }

}
