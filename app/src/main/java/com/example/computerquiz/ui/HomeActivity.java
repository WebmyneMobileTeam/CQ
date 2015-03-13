package com.example.computerquiz.ui;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.computerquiz.R;
import com.example.computerquiz.ui.widget.HalfHeightLayout;

public class HomeActivity extends ActionBarActivity {

    private HalfHeightLayout btnGiveTest;
    private HalfHeightLayout btnSeeTests;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        btnGiveTest = (HalfHeightLayout)findViewById(R.id.btnGiveTest);
        btnGiveTest.setOnClickListener(newTestListner);

        btnSeeTests = (HalfHeightLayout)findViewById(R.id.btnSeeTests);
        btnSeeTests.setOnClickListener(seeTestsListner);


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


        Intent iNewTest = new Intent(HomeActivity.this,TestListActivity.class);
        startActivity(iNewTest);

    }


    private void proceedNewTest() {

        Intent iNewTest = new Intent(HomeActivity.this,CategoriesActivity.class);
        startActivity(iNewTest);

    }

}
