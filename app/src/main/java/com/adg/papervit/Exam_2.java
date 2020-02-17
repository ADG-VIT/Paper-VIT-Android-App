package com.adg.papervit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.Manifest;
import android.app.Dialog;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.github.barteksc.pdfviewer.PDFView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class Exam_2 extends AppCompatActivity {

    private ProgressDialog progressDialog;
    private PDFView pdfView;
    private String copy;
    private Button downloadPdf;
    private boolean paperDownloaded;
    private int READ_STORAGE = 100;
    private int WRITE_STORAGE = 101;
    private String filename;
    private ImageView downloadIcon;

    private static Gson gson = new GsonBuilder()
            .setLenient()
            .create();

    private static Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl("https://papervit.herokuapp.com/")
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson));


    private static Retrofit retrofit = builder.build();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.activity_exam_2);

        TextView subShort, subName, subName2, subCode, subYear, subSlot;

        Intent intent = getIntent();
        String subjectName = intent.getStringExtra("subName");
        String subjectShort = intent.getStringExtra("subShort");
        String subjectCode = intent.getStringExtra("subCode");
        String subjectYear = intent.getStringExtra("subYear");
        String subjectSlot = intent.getStringExtra("subSlot");
        String paperId = intent.getStringExtra("paperId");

        subShort = findViewById(R.id.subjectShortTextView2);
        subName = findViewById(R.id.subjectNameTextView3);
        subName2 = findViewById(R.id.subjectNameTextView4);
        subCode = findViewById(R.id.subjectCodeTextView2);
        subYear = findViewById(R.id.courseYearTextView);
        subSlot = findViewById(R.id.courseSlotTextView);
        downloadPdf = findViewById(R.id.downloadPdfButton);
        pdfView = findViewById(R.id.pdfViewer2);
        downloadIcon = findViewById(R.id.downloadIcon);

        subShort.setText(subjectShort);
        subName.setText(subjectName);
        subName2.setText(subjectName);
        subCode.setText(subjectCode);
        subYear.setText(subjectYear);
        subSlot.setText(subjectSlot);

        filename = subjectCode + "_" + subjectYear + "_" + subjectSlot + "_" + Exam.examType + ".pdf";

        final File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/" + filename);

        if(file.isFile()){

            pdfView.fromFile(file).load();
            downloadPdf.setText(getString(R.string.view));
            paperDownloaded = true;
            downloadIcon.setImageDrawable(getDrawable(R.drawable.downloaded));


        }else{
            Log.i("INFO","file not found");
            paperDownloaded = false;
            downloadIcon.setImageDrawable(getDrawable(R.drawable.not_downloaded));
        }

        String url = "https://papervit.herokuapp.com/papers/data?id=" + paperId;

        if(!paperDownloaded) {
            progressDialog = ProgressDialog.show(Exam_2.this, "Loading Paper", "Please wait...", false, false);

            API api = retrofit.create(API.class);

            Call<String> call = api.getPaper(url);

            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {

                    copy = response.body();
                    byte[] pdfAsBytes = Base64.decode(response.body(), Base64.DEFAULT);
                    pdfView.fromBytes(pdfAsBytes).load();
                    progressDialog.dismiss();


                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Log.i("INFO", t.toString());
                    progressDialog.dismiss();
                    Dialog dialog = new Dialog(Exam_2.this);
                    dialog.setCancelable(false);
                    dialog.setContentView(R.layout.alert);
                    dialog.show();

                    Button button = dialog.findViewById(R.id.home);
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(Exam_2.this, Exam_1.class);
                            startActivity(intent);
                        }
                    });
                }
            });
        }

        downloadPdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if ((checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED))
                {
                    requestPermissions(new String[] {Manifest.permission.READ_EXTERNAL_STORAGE},READ_STORAGE);
                }
                else if ((checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED))
                {
                    requestPermissions(new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE},WRITE_STORAGE);
                }
                else {

                    if(paperDownloaded){
                        Intent intent = new Intent(Exam_2.this, FullView.class);
                        intent.putExtra("filename",filename);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);

                    }else {
                        downloadPdf.setText(getString(R.string.download));
                        downloadPdf.setEnabled(false);
                        downloadPdf.setAlpha(0.7f);
                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                savePdf(copy,filename);
                            }
                        }, 1000);
                    }

                }
            }
        });

        downloadIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(paperDownloaded){

                    startActivity(new Intent(DownloadManager.ACTION_VIEW_DOWNLOADS));

                }else{
                    Toast toast = Toast.makeText(Exam_2.this, "Press Download PDF button to download the paper",Toast.LENGTH_LONG);
                    TextView textView = toast.getView().findViewById(android.R.id.message);
                    if (textView!=null)
                    {
                        textView.setGravity(Gravity.CENTER);
                        toast.show();
                    }
                }
            }
        });

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
                if(paperDownloaded){
                    Intent intent = new Intent(Exam_2.this, FullView.class);
                    intent.putExtra("filename",filename);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);

                }else {
                    downloadPdf.setText(getString(R.string.download));
                    downloadPdf.setEnabled(false);
                    downloadPdf.setAlpha(0.7f);
                    savePdf(copy,filename);
                }

            }
        }
        else if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && requestCode == WRITE_STORAGE)
        {

            if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
            {
                requestPermissions(new String[] {Manifest.permission.READ_EXTERNAL_STORAGE},WRITE_STORAGE);
            }
            else {
                if(paperDownloaded){
                    Intent intent = new Intent(Exam_2.this, FullView.class);
                    intent.putExtra("filename",filename);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);

                }else {
                    downloadPdf.setText(getString(R.string.download));
                    downloadPdf.setEnabled(false);
                    downloadPdf.setAlpha(0.7f);
                    savePdf(copy,filename);
                }

            }
        }
    }

    private void savePdf(String body, String filename){

        final File downloadPath = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/" + filename);
        byte[] pdfAsBytes = Base64.decode(body, Base64.DEFAULT);
        FileOutputStream os = null;
        try {
            os = new FileOutputStream(downloadPath, false);
            os.write(pdfAsBytes);
            os.flush();
            os.close();

            downloadPdf.setText(getString(R.string.view));
            downloadPdf.setAlpha(1f);
            downloadPdf.setEnabled(true);
            paperDownloaded = true;
            downloadIcon.setImageDrawable(getDrawable(R.drawable.downloaded));
            Toast.makeText(this, "Paper saved at " + downloadPath.getAbsolutePath(), Toast.LENGTH_LONG).show();

            Log.i("INFO","File saved successfully");

        } catch (FileNotFoundException e) {
            Log.i("INFO",e.toString());
        } catch (IOException e) {
            Log.i("INFO",e.toString());
            Log.i("INFO","IOException");
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        RecyclerViewAdapter2.showShimmer = false;
    }
}

