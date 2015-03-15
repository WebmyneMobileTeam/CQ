package com.example.computerquiz.ui;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.computerquiz.R;
import com.example.computerquiz.helpers.ComplexPreferences;
import com.example.computerquiz.model.Test;

public class ReviewActivity extends ActionBarActivity {

    private Toolbar toolbar;
    private Test currentTest;

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

        ComplexPreferences complexPreferences = ComplexPreferences.getComplexPreferences(ReviewActivity.this, "mypref", MODE_PRIVATE);;
        currentTest = complexPreferences.getObject("currentTest",Test.class);
        complexPreferences.commit();

    }

}
