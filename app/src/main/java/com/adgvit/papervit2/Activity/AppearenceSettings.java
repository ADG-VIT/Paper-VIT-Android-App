package com.adgvit.papervit2.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;

import com.adgvit.papervit2.Adapter.RecyclerViewAdapter;
import com.adgvit.papervit2.R;

public class AppearenceSettings extends AppCompatActivity {

    ImageView backImageView;

    RadioButton deafult, light, dark;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appearence_settings);

//        SharedPreferences sharedPreferences1 = getSharedPreferences("com.adgvit.papervit.theme",MODE_PRIVATE);
//        if(sharedPreferences1 != null)
//        {
//            if(sharedPreferences1.getString("CurrentTheme","").equals("Def"))
//            {
//                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
//            }
//            else if(sharedPreferences1.getString("CurrentTheme","").equals("Light"))
//            {
//                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
//            }
//            else if(sharedPreferences1.getString("CurrentTheme","").equals("Dark"))
//            {
//                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
//            }
//        }

        backImageView = findViewById(R.id.backImageView);
        backImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        deafult = findViewById(R.id.deafultButton);
        light = findViewById(R.id.lightButton);
        dark = findViewById(R.id.darkButton);

        SharedPreferences sharedPreferences = getSharedPreferences("com.adgvit.papervit.theme",MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();

        if(sharedPreferences != null)
        {
            if(sharedPreferences.getString("theme","").equals("sys_def"))
            {
                deafult.setChecked(true);
            }
            else if(sharedPreferences.getString("theme","").equals("light"))
            {
                light.setChecked(true);
            }
            else if(sharedPreferences.getString("theme","").equals("dark"))
            {
                dark.setChecked(true);
            }
        }

        if (sharedPreferences.getString("theme","").equals(""))
        {
            deafult.setChecked(true);
        }

        deafult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                light.setChecked(false);
                deafult.setChecked(true);
                dark.setChecked(false);

                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
                myEdit.putString("theme", "sys_def");
                myEdit.apply();

            }
        });

        light.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                light.setChecked(true);
                deafult.setChecked(false);
                dark.setChecked(false);

                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                myEdit.putString("theme", "light");
                myEdit.apply();

            }
        });

        dark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                light.setChecked(false);
                deafult.setChecked(false);
                dark.setChecked(true);

                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                myEdit.putString("theme", "dark");
                myEdit.apply();

            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}