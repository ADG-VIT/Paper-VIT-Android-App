package com.adgvit.papervit2.Activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.OpenableColumns;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.adgvit.papervit2.R;
import com.mikhaellopez.circularprogressbar.CircularProgressBar;

import org.jetbrains.annotations.NotNull;

import java.io.File;

public class UploadPaper extends AppCompatActivity {

    Button backPressed;
    TextView uploadTextView;
    ImageView uploadImageView;
    TextView infoTextView;
    TextView statusTextView;

    Button uploadPDFButton;
    String displayName = "Your File";

    CircularProgressBar circularProgressBar;

    private  String encodedPDF;

    private final int REQ_PDF = 21;
    private int READ_STORAGE = 100;
    private int WRITE_STORAGE = 101;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_paper1);

        backPressed = findViewById(R.id.backPressed);
        uploadImageView = findViewById(R.id.uploadImageView);
        uploadTextView = findViewById(R.id.uploadTextView);
        infoTextView = findViewById(R.id.infoTextView);
        statusTextView = findViewById(R.id.statusTextView);

        uploadPDFButton = findViewById(R.id.uploadbtn);

        circularProgressBar = findViewById(R.id.circularProgressBar);

        uploadImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED))
                {
                    requestPermissions(new String[] {Manifest.permission.READ_EXTERNAL_STORAGE},READ_STORAGE);
                }
                else if ((checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED))
                {
                    requestPermissions(new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE},WRITE_STORAGE);
                }
                else {
                    selectPDF();
                }
            }
        });

        uploadTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED))
                {
                    requestPermissions(new String[] {Manifest.permission.READ_EXTERNAL_STORAGE},READ_STORAGE);
                }
                else if ((checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED))
                {
                    requestPermissions(new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE},WRITE_STORAGE);
                }
                else {
                    selectPDF();
                }
            }
        });

        uploadPDFButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadTextView.setOnClickListener(null);
                uploadPDF();
            }
        });

        backPressed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    public void selectPDF()
    {
        Intent chooseFile = new Intent(Intent.ACTION_GET_CONTENT);
        chooseFile.setType("application/pdf");
        chooseFile = Intent.createChooser(chooseFile, "Choose a file");
        startActivityForResult(chooseFile, REQ_PDF);
        new CountDownTimer(2000,1000)
        {
            @Override
            public void onTick(long l) {
            }

            @Override
            public void onFinish() {
                uploadPDFButton.setVisibility(View.VISIBLE);
            }
        }.start();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull @NotNull String[] permissions, @NonNull @NotNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && requestCode == READ_STORAGE)
        {

            if(checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
            {
                requestPermissions(new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE},WRITE_STORAGE);
            }
            else {
                Intent chooseFile = new Intent(Intent.ACTION_GET_CONTENT);
                chooseFile.setType("application/pdf");
                chooseFile = Intent.createChooser(chooseFile, "Choose a file");
                startActivityForResult(chooseFile, REQ_PDF);
            }
        }
        else if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && requestCode == WRITE_STORAGE)
        {

            if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
            {
                requestPermissions(new String[] {Manifest.permission.READ_EXTERNAL_STORAGE},WRITE_STORAGE);
            }
            else {
                Intent chooseFile = new Intent(Intent.ACTION_GET_CONTENT);
                chooseFile.setType("application/pdf");
                chooseFile = Intent.createChooser(chooseFile, "Choose a file");
                startActivityForResult(chooseFile, REQ_PDF);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,  Intent data) {

        if (requestCode==REQ_PDF){
            if(resultCode==RESULT_OK && data!=null){
                Uri uri = data.getData();
                String uriString = uri.toString();
                File myFile = new File(uriString);
                String path = myFile.getAbsolutePath();

                if (uriString.startsWith("content://")) {
                    Cursor cursor = null;
                    try {
                        cursor = this.getContentResolver().query(uri, null, null, null, null);
                        if (cursor != null && cursor.moveToFirst()) {
                            displayName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                        }
                    } finally {
                        cursor.close();
                    }
                } else if (uriString.startsWith("file://")) {
                    displayName = myFile.getName();
                }
            }
        }
        uploadTextView.setText(displayName);
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void uploadPDF(){
        uploadTextView.setText(displayName);
        statusTextView.setText("Uploading...");
        infoTextView.setText("Please Wait a Moment till the Paper is Uploaded on the Server.");
        uploadPDFButton.setText("Uploading File");

        circularProgressBar.setProgressWithAnimation(100f, 3000L);

        new CountDownTimer(3000,5000)
        {

            @Override
            public void onTick(long l) {
            }

            @Override
            public void onFinish() {
                Toast.makeText(getApplicationContext(),"Your file has successfully uploaded!",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }
        }.start();

    }

}
