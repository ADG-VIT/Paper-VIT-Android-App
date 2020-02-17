package com.adg.papervit;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.gson.internal.LinkedTreeMap;

import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent;
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Exam extends AppCompatActivity {

    public static RecyclerViewAdapter favRecyclerViewAdapter, allSubjectRecyclerViewAdapter;
    public static RecyclerView favRecyclerView, allSubjectRecyclerView;

    public static ArrayList<String> favSubjectNameArrayList = new ArrayList<>();
    public static ArrayList<String> favSubjectShortArrayList = new ArrayList<>();
    public static ArrayList<String> favSubjectCodeArrayList = new ArrayList<>();
    public static ArrayList<Boolean> favCheckArrayList = new ArrayList<>();

    public static ArrayList<String> subjectNameArrayList = new ArrayList<>();
    public static ArrayList<String> subjectShortArrayList = new ArrayList<>();
    public static ArrayList<String> subjectCodeArrayList = new ArrayList<>();
    public static ArrayList<Boolean> checkArrayList = new ArrayList<>();
    public static ArrayList<String> subjectIdArrayList = new ArrayList<>();

    public static ArrayList<String> searchedSubjectNameArrayList = new ArrayList<>();
    public static ArrayList<String> searchedSubjectShortArrayList = new ArrayList<>();
    public static ArrayList<String> searchedSubjectCodeArrayList = new ArrayList<>();
    public static ArrayList<Boolean> searchedcheckArrayList = new ArrayList<>();

    public static ArrayList<ArrayList<String>> idMapping = new ArrayList<>();

    public static TextView favTextView,examTypeEditText;
    private EditText searchEditText;
    public static Context context;
    private static String cat1 = "CAT 1";
    private static String cat2 = "CAT 2";
    private static String fat = "FAT";
    private String cat1_action = "android.intent.action.CAT1";
    private String cat2_action = "android.intent.action.CAT2";
    private String fat_action = "android.intent.action.FAT";


    public static String examType = "CAT 1";
    private Database database;

    private static Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl("https://papervit.herokuapp.com/")
            .addConverterFactory(GsonConverterFactory.create());

    private static Retrofit retrofit = builder.build();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.activity_exam);

        favRecyclerView = findViewById(R.id.favRecyclerView);
        allSubjectRecyclerView = findViewById(R.id.allSubjectRecyclerView);
        favTextView = findViewById(R.id.favTextView);
        examTypeEditText = findViewById(R.id.examTypeEditText);
        searchEditText = findViewById(R.id.searchEditText);

        context = Exam.this;

        try {
            String action = getIntent().getAction();

            if(action.equals(cat2_action))
            {
                examType = cat2;
            }
            else if(action.equals(fat_action))
            {
                examType = fat;
            }
            else
            {
                examType = cat1;
            }
        }
        catch (Exception e)
        {

        }

        RecyclerViewAdapter.showShimmer = true;

        database = new Database(this);

        API api = retrofit.create(API.class);

        examTypeEditText.setText(examType);

        Call<Sub> call;

        subjectNameArrayList = new ArrayList<>();
        subjectCodeArrayList = new ArrayList<>();
        subjectShortArrayList = new ArrayList<>();
        checkArrayList = new ArrayList<>();
        subjectIdArrayList = new ArrayList<>();

        favSubjectNameArrayList = new ArrayList<>();
        favSubjectShortArrayList = new ArrayList<>();
        favSubjectCodeArrayList = new ArrayList<>();
        favCheckArrayList = new ArrayList<>();

//        Toast.makeText(context, examType, Toast.LENGTH_SHORT).show();

        if (examType.equals(cat2)) {

            call = api.getSubCat2();
        }
        else if (examType.equals(fat)) {

            call = api.getSubFat();
        }
        else
        {
            call = api.getSubCat1();
        }

        call.enqueue(new Callback<Sub>() {
            @Override
            public void onResponse(Call<Sub> call, Response<Sub> response) {

                List list = response.body().getResponse();

                for (Object item : list)
                {
                    LinkedTreeMap<Object,Object> linkedTreeMap = (LinkedTreeMap) item;
                    String code = linkedTreeMap.get("code").toString();
                    String subject = linkedTreeMap.get("subject").toString();
                    String shortform = linkedTreeMap.get("shortform").toString();
                    String id = linkedTreeMap.get("_id").toString();

                    subjectNameArrayList.add(subject);
                    subjectCodeArrayList.add(code);
                    subjectShortArrayList.add(shortform);
                    checkArrayList.add(false);
                    subjectIdArrayList.add(id);
                }

                idMapping.add(subjectCodeArrayList);
                idMapping.add(subjectIdArrayList);

                Log.i("INFO","Request Successful");

                getFav();

                RecyclerViewAdapter.showShimmer = false;
                favRecyclerViewAdapter.notifyDataSetChanged();
                allSubjectRecyclerViewAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<Sub> call, Throwable t) {
                Log.i("INFO",t.toString());
                Dialog dialog = new Dialog(Exam.this);
                dialog.setCancelable(false);
                dialog.setContentView(R.layout.alert);
                dialog.show();

                Button button = dialog.findViewById(R.id.home);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Exam.this, MainActivity.class);
                        startActivity(intent);
                    }
                });
            }
        });

        KeyboardVisibilityEvent.setEventListener(
                Exam.this,
                new KeyboardVisibilityEventListener() {
                    @Override
                    public void onVisibilityChanged(boolean isOpen) {
                        if(isOpen)
                        {
                            favTextView.setVisibility(View.GONE);
                            favRecyclerView.setVisibility(View.GONE);
                        }
                        else
                        {
                            favTextView.setVisibility(View.VISIBLE);
                            favRecyclerView.setVisibility(View.VISIBLE);
                        }
                    }
                }
        );

        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String searchText = String.valueOf(charSequence).toLowerCase();
                searchedSubjectNameArrayList = new ArrayList<>();
                searchedSubjectShortArrayList = new ArrayList<>();
                searchedSubjectCodeArrayList = new ArrayList<>();
                searchedcheckArrayList = new ArrayList<>();

                for (String name : subjectNameArrayList)
                {
                    if(isContain(name.toLowerCase(), searchText))
                    {
                        Log.i("INFO",name);
                        searchedSubjectNameArrayList.add(name);
                        searchedSubjectCodeArrayList.add(subjectCodeArrayList.get(subjectNameArrayList.indexOf(name)));
                        searchedSubjectShortArrayList.add(subjectShortArrayList.get(subjectNameArrayList.indexOf(name)));
                        searchedcheckArrayList.add(checkArrayList.get(subjectNameArrayList.indexOf(name)));

                        allSubjectRecyclerViewAdapter = new RecyclerViewAdapter(searchedSubjectNameArrayList,searchedSubjectShortArrayList,searchedSubjectCodeArrayList,context, searchedcheckArrayList);
                        allSubjectRecyclerView.setAdapter(allSubjectRecyclerViewAdapter);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        favRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        allSubjectRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        updateAllSubjectRecyclerView();

        updateFavRecyclerView();
    }

    public static void updateFavRecyclerView()
    {
        if(favSubjectNameArrayList.size() == 0)
        {
            favTextView.setVisibility(View.GONE);
            favRecyclerView.setVisibility(View.GONE);
        }
        else {
            favTextView.setVisibility(View.VISIBLE);
            favRecyclerView.setVisibility(View.VISIBLE);
        }

        favRecyclerViewAdapter = new RecyclerViewAdapter(favSubjectNameArrayList,favSubjectShortArrayList,favSubjectCodeArrayList,context, favCheckArrayList);
        favRecyclerView.setAdapter(favRecyclerViewAdapter);

    }

    public static void updateAllSubjectRecyclerView()
    {
        allSubjectRecyclerViewAdapter = new RecyclerViewAdapter(subjectNameArrayList,subjectShortArrayList,subjectCodeArrayList,context, checkArrayList);
        allSubjectRecyclerView.setAdapter(allSubjectRecyclerViewAdapter);
    }

    public void getFav()
    {
        Cursor data = database.getFav(examType);
        while (data.moveToNext())
        {
            favSubjectCodeArrayList.add(data.getString(0));
            int index = subjectCodeArrayList.indexOf(data.getString(0));
            favSubjectShortArrayList.add(subjectShortArrayList.get(index));
            favSubjectNameArrayList.add(subjectNameArrayList.get(index));
            favCheckArrayList.add(true);
            checkArrayList.set(index,true);
            updateAllSubjectRecyclerView();
            updateFavRecyclerView();
        }
    }

    private static boolean isContain(String source, String subItem){
        String pattern = "^.*" + subItem + ".*$";
        Pattern p=Pattern.compile(pattern);
        Matcher m=p.matcher(source);
        return m.find();
    }
}