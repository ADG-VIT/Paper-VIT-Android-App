package com.adgvit.papervit2.Activity;


import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.adgvit.papervit2.Adapter.RecyclerAdapterExamMain;
import com.adgvit.papervit2.Object.HomeData;
import com.adgvit.papervit2.Object.HomeObject;
import com.adgvit.papervit2.R;
import com.adgvit.papervit2.Services.API;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private CardView cat1CardView, cat2CardView, fatCardView;
    Button uploadButton;
    private ImageView settings;
    private int READ_STORAGE = 100;
    private int WRITE_STORAGE = 101;

    RecyclerView examsRecyclerView;

    private static Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl("https://adg-papervit.herokuapp.com/")
            .addConverterFactory(GsonConverterFactory.create());

    private static Retrofit retrofit = builder.build();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        examsRecyclerView = findViewById(R.id.recyclerview_Exams);

        settings = (ImageView) findViewById(R.id.uploadButton1);
        uploadButton = findViewById(R.id.aboutUs);
        RecyclerAdapterExamMain.showShimmer=true;
        API api = retrofit.create(API.class);
        Call<HomeData> call = api.getHome();
        call.enqueue(new Callback<HomeData>() {
            @Override
            public void onResponse(Call<HomeData> call, Response<HomeData> response) {
                HomeData data = response.body();
                List<HomeObject> list = new ArrayList<>();
                list = data.getData().getExamTypes();
                RecyclerAdapterExamMain.showShimmer=false;
                LinearLayoutManager manager=new LinearLayoutManager(MainActivity.this);
                examsRecyclerView.setLayoutManager(manager);
                RecyclerAdapterExamMain adapter = new RecyclerAdapterExamMain(MainActivity.this, list);
                examsRecyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<HomeData> call, Throwable t) {

            }
        });

        if ((checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED))
        {
            requestPermissions(new String[] {Manifest.permission.READ_EXTERNAL_STORAGE},READ_STORAGE);
        }
        else if ((checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED))
        {
            requestPermissions(new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE},WRITE_STORAGE);
        }else {

            settings.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, Settings.class);
                    startActivity(intent);
                }
            });

            uploadButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this,UploadPaper.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            });

            /*cat1CardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Exam.examType = "CAT 1";
                    Intent intent = new Intent(MainActivity.this,Exam.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);

                }
            });

            cat2CardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Exam.examType = "CAT 2";
                    Intent intent = new Intent(MainActivity.this,Exam.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);

                }
            });

            fatCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Exam.examType = "FAT";
                    Intent intent = new Intent(MainActivity.this,Exam.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);

                }
            });*/
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && requestCode == READ_STORAGE)
        {

            if(checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
            {
                requestPermissions(new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE},WRITE_STORAGE);
            }
            else {
                settings.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MainActivity.this, Settings.class);
                        startActivity(intent);
                    }
                });

                uploadButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        Intent intent = new Intent(MainActivity.this,Upload.class);
//                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                        startActivity(intent);
                    }
                });

                /*cat1CardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Exam.examType = "CAT 1";
                        Intent intent = new Intent(MainActivity.this,Exam.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);

                    }
                });

                cat2CardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Exam.examType = "CAT 2";
                        Intent intent = new Intent(MainActivity.this,Exam.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);

                    }
                });

                fatCardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Exam.examType = "FAT";
                        Intent intent = new Intent(MainActivity.this,Exam.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);

                    }
                });*/

            }
        }
        else if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && requestCode == WRITE_STORAGE)
        {

            if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
            {
                requestPermissions(new String[] {Manifest.permission.READ_EXTERNAL_STORAGE},WRITE_STORAGE);
            }
            else {
                settings.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MainActivity.this, Settings.class);
                        startActivity(intent);
                    }
                });

                uploadButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        Intent intent = new Intent(MainActivity.this,Upload.class);
//                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                        startActivity(intent);
                    }
                });

                /*cat1CardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Exam.examType = "CAT 1";
                        Intent intent = new Intent(MainActivity.this,Exam.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);

                    }
                });

                cat2CardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Exam.examType = "CAT 2";
                        Intent intent = new Intent(MainActivity.this,Exam.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);

                    }
                });

                fatCardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Exam.examType = "FAT";
                        Intent intent = new Intent(MainActivity.this,Exam.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);

                    }
                });*/
            }

        }
    }

    boolean doubleBackToExitPressedOnce = false;
    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            //super.onBackPressed();
            super.onBackPressed();
            moveTaskToBack(true);
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(0);
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }

}

