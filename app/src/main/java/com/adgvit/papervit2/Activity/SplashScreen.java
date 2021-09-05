package com.adgvit.papervit2.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.adgvit.papervit2.R;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        SharedPreferences sharedPreferences = getSharedPreferences("com.adgvit.papervit.theme",MODE_PRIVATE);

        if(sharedPreferences != null)
        {
            if(sharedPreferences.getString("theme","").equals("sys_def"))
            {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
            }
            else if(sharedPreferences.getString("theme","").equals("light"))
            {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }
            else if(sharedPreferences.getString("theme","").equals("dark"))
            {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            }
        }

        SharedPreferences sharedPreferences1 = getSharedPreferences("com.adgvit.papervit.userlog",MODE_PRIVATE);
        SharedPreferences.Editor myEdit1 = sharedPreferences.edit();

        new CountDownTimer(2000,1000)
        {

            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {
//                Intent intent = new Intent(SplashScreen.this, MainActivity.class);
//                //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(intent);
                if(sharedPreferences1 == null || sharedPreferences.getString("user","").equals("")){
                    myEdit1.putString("user", "1");
                    myEdit1.apply();
                    Intent intent = new Intent(getApplicationContext(), OnBoardScreen.class);
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        }.start();
    }
}
