package com.example.computerquiz.ui;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.computerquiz.R;
import com.example.computerquiz.helpers.Prefs;
import com.example.computerquiz.ui.widget.HalfHeightLayout;

public class HomeActivity extends ActionBarActivity implements View.OnClickListener {

    private HalfHeightLayout btnGiveTest;
    private HalfHeightLayout btnSeeTests;
    private TextView tvSettings;
    private ImageView globalImageview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);




        btnGiveTest = (HalfHeightLayout) findViewById(R.id.btnGiveTest);
        btnGiveTest.setOnClickListener(newTestListner);
        btnSeeTests = (HalfHeightLayout) findViewById(R.id.btnSeeTests);
        btnSeeTests.setOnClickListener(seeTestsListner);
        tvSettings = (TextView) findViewById(R.id.tvSettings);
        tvSettings.setOnClickListener(this);
        globalImageview = (ImageView) findViewById(R.id.globalImageView);

    }

    @Override
    protected void onResume() {
        super.onResume();
        updatePreferences();
    }

    private void updatePreferences() {

        String color = Prefs.with(HomeActivity.this).getString("back", "#494949");
        globalImageview.setBackgroundColor(Color.parseColor(color));

    }

    private View.OnClickListener newTestListner = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            proceedNewTest();
        }
    };
    private View.OnClickListener seeTestsListner = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            processSeeTests();
        }
    };


    private void processSeeTests() {

        Intent iNewTest = new Intent(HomeActivity.this, TestListActivity.class);
        startActivity(iNewTest);
    }

    private void proceedNewTest() {

        Intent iNewTest = new Intent(HomeActivity.this, CategoriesActivity.class);
        startActivity(iNewTest);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.tvSettings:
                Intent iSettings = new Intent(HomeActivity.this, SettingsActivity.class);
                startActivity(iSettings);
                break;
        }

    }
}
