package com.adgvit.papervit2.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.adgvit.papervit2.Adapter.DevelopersRecyclerViewAdapter;
import com.adgvit.papervit2.R;

public class AboutUs extends AppCompatActivity {

    RecyclerView recyclerView;
    ImageView backImageView;

    String[] names = {"Utkarsh Dixit", "Siddy", "Siddharth Singh", "Utkarsh Dixit", "Siddy", "Siddharth Singh"};
    String[] techStack = {"iOS Developer, UI/UX Designer", "Android Developer", "iOS Developer, UI/UX Designer", "iOS Developer, UI/UX Designer", "Android Developer", "iOS Developer, UI/UX Designer"};
    int[] images = {R.drawable.utkarsh, R.drawable.utkarsh, R.drawable.utkarsh, R.drawable.utkarsh, R.drawable.utkarsh, R.drawable.utkarsh};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.activity_about_us);

        backImageView = findViewById(R.id.backImageView);
        backImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        recyclerView = findViewById(R.id.developers);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new DevelopersRecyclerViewAdapter(names,techStack, images));

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}