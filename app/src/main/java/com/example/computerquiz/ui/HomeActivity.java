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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        btnGiveTest = (HalfHeightLayout)findViewById(R.id.btnGiveTest);
        btnGiveTest.setOnClickListener(newTestListner);


    }

    private View.OnClickListener newTestListner = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
                proceedNewTest();
        }
    };

    private void proceedNewTest() {

        Intent iNewTest = new Intent(HomeActivity.this,CategoriesActivity.class);
        startActivity(iNewTest);

    }


    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/
}
