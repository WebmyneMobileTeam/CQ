package com.example.computerquiz.ui;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.example.computerquiz.R;
import com.example.computerquiz.helpers.Prefs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SettingsActivity extends ActionBarActivity {
    private ImageView globalImageview;
    private Toolbar toolbar;
    private CardView card_view_backgroundColor;
    private ImageView selectedColorView;
    private ArrayList<String> colors;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        globalImageview = (ImageView)findViewById(R.id.globalImageView);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setTitle("Settings");
            setSupportActionBar(toolbar);
        }
        toolbar.setNavigationIcon(R.drawable.ic_launcher);

        card_view_backgroundColor = (CardView)findViewById(R.id.card_view_backgroundColor);
        card_view_backgroundColor.setOnClickListener(colorClickListner);
        selectedColorView = (ImageView)card_view_backgroundColor.findViewById(R.id.selectedColorView);
        colors = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.arrColor)));

    }

    private View.OnClickListener colorClickListner = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            displayColorDialog();
        }
    };

    private void displayColorDialog() {

        final Dialog dialog = new Dialog(SettingsActivity.this);
        dialog.setContentView(R.layout.item_color_picker);
        dialog.setTitle("Choose Color");
        dialog.show();

        GridView gridView = (GridView)dialog.findViewById(R.id.grid_color);
        ArrayList<String> cs = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.arrColor)));
        gridView.setAdapter(new ColorAdapter(cs,SettingsActivity.this));
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                dialog.dismiss();
                selectedColorView.setBackgroundColor(Color.parseColor(colors.get(position)));
                Prefs.with(SettingsActivity.this).save("back", colors.get(position));
                updatePreferences();
            }
        });

    }

    private class ColorAdapter extends BaseAdapter{

        private ArrayList<String> colors;
        private Context ctx;
        private int width;

        public ColorAdapter(ArrayList<String> colors, Context ctx) {
            this.colors = colors;
            this.ctx = ctx;
            width = ctx.getResources().getDisplayMetrics().widthPixels;
        }

        @Override
        public int getCount() {
            return colors.size();
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

            ImageView imageView;
            if (convertView == null) {  // if it's not recycled, initialize some attributes
                imageView = new ImageView(ctx);
                imageView.setLayoutParams(new GridView.LayoutParams(width/3,width/3));
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setPadding(4, 4, 4, 4);
            } else {
                imageView = (ImageView) convertView;
            }
            imageView.setBackgroundColor(Color.parseColor(colors.get(position)));
            return imageView;
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        updatePreferences();
    }

    private void updatePreferences() {

        String color = Prefs.with(SettingsActivity.this).getString("back", "#494949");
        globalImageview.setBackgroundColor(Color.parseColor(color));
        selectedColorView.setBackgroundColor(Color.parseColor(color));
    }

}
