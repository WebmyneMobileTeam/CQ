package com.example.computerquiz.ui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;

import com.example.computerquiz.R;
import com.example.computerquiz.helpers.DatabaseHelper;
import com.example.computerquiz.model.Category;

import java.util.ArrayList;

public class LevelActivity extends ActionBarActivity {

    private ArrayList<Category> levels;
    private ProgressDialog pausingDialog;
    private DatabaseHelper dbHelper;
    private Toolbar toolbar;
    private ListView lvLevels;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_level);
        init();
        setupToolBar();
	}
    private void init() {
        lvLevels = (ListView) findViewById(R.id.listLevelss);
    }

    private void setupToolBar() {

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setTitle("Welcome User,");
            setSupportActionBar(toolbar);
        }
        toolbar.setNavigationIcon(R.drawable.ic_launcher);

    }

}
