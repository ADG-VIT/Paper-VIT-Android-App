package com.adg.papervit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.gson.internal.LinkedTreeMap;

import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent;
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEventListener;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Exam_1 extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerViewAdapter2 recyclerViewAdapter;
    private ArrayList<Integer> randomImage = new ArrayList<Integer>();
    private ArrayList<String> courseSlot = new ArrayList<String>();
    private ArrayList<String> courseDesc = new ArrayList<String>();
    private TextView subjectShortTextView, subjectNameTextView, subjectNameTextView2, subjectCodeTextView;

    public static ArrayList<String> paperIdArrayList = new ArrayList<>();
    public static ArrayList<String> paperSlotArrayList = new ArrayList<>();
    public static ArrayList<String> paperYearArrayList = new ArrayList<>();
    public static ArrayList<String> paperExamArrayList = new ArrayList<>();
    public static ArrayList<String> paperFileNameArrayList = new ArrayList<>();
    public static ArrayList<String> paperUrlList = new ArrayList<>();


    private static String cat1 = "CAT 1";
    private static String cat2 = "CAT 2";
    private static String fat = "FAT";

    public static String subjectName, subjectCode, subjectShort, subjectId;

    private static Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl("https://adg-papervit.herokuapp.com/")
            .addConverterFactory(GsonConverterFactory.create());

    private static Retrofit retrofit = builder.build();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.activity_exam_1);

        Intent intent = getIntent();
        subjectId = intent.getStringExtra("subjectID");
        subjectName = intent.getStringExtra("subjectName");
        subjectCode = intent.getStringExtra("subjectCode");
        subjectShort = intent.getStringExtra("subjectShort");


        recyclerView = findViewById(R.id.recyclerView);
        subjectShortTextView = findViewById(R.id.subjectShortTextView);
        subjectNameTextView = findViewById(R.id.subjectNameTextView);
        subjectNameTextView2 = findViewById(R.id.subjectNameTextView2);
        subjectCodeTextView = findViewById(R.id.subjectCodeTextView);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(gridLayoutManager);

        RecyclerViewAdapter2.showShimmer = true;

        subjectCodeTextView.setText(subjectCode);
        subjectShortTextView.setText(subjectShort);
        subjectNameTextView.setText(subjectName);
        subjectNameTextView2.setText(subjectName);


        paperIdArrayList = new ArrayList<>();
        paperSlotArrayList = new ArrayList<>();
        paperExamArrayList = new ArrayList<>();
        paperYearArrayList = new ArrayList<>();
        paperFileNameArrayList = new ArrayList<>();


        API api = retrofit.create(API.class);

        Call<root1> call;

        if (Exam.examType.equals(cat2)) {

            call = api.getPaperCat2(subjectId);
        }
        else if (Exam.examType.equals(fat)) {

            call = api.getPaperFat(subjectId);
        }
        else
        {
            call = api.getPaperCat1(subjectId);
        }

        call.enqueue(new Callback<root1>() {
            @Override
            public void onResponse(Call<root1> call, Response<root1> response) {

                root1 model = response.body();

                for (paperObject item : model.getData().getPapers())
                {


                    paperIdArrayList.add(item.get_id());
                    paperSlotArrayList.add(item.getSlot());
                    paperUrlList.add(item.getUrl());
                    // paperExamArrayList.add(exam);
                    paperYearArrayList.add(item.getSemester());
                    paperFileNameArrayList.add(item.getFileName());

                }

                Log.i("INFO","Request Successful" + paperSlotArrayList.get(0));

                RecyclerViewAdapter2.showShimmer = false;
                recyclerViewAdapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<root1> call, Throwable t) {
                Log.i("INFO",t.toString());
                Dialog dialog = new Dialog(Exam_1.this);
                dialog.setCancelable(false);
                dialog.setContentView(R.layout.alert);
                dialog.show();

                Button button = dialog.findViewById(R.id.home);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Exam_1.this, Exam.class);
                        startActivity(intent);
                    }
                });
            }
        });

        recyclerViewAdapter = new RecyclerViewAdapter2(paperYearArrayList,paperSlotArrayList, Exam_1.this);
        recyclerView.setAdapter(recyclerViewAdapter);

    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        RecyclerViewAdapter.showShimmer = false;
        Intent intent = new Intent(this,Exam.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);

    }
}