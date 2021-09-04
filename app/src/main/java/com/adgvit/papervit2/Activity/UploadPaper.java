package com.adgvit.papervit2.Activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.adgvit.papervit2.R;

import org.jetbrains.annotations.NotNull;

public class UploadPaper extends AppCompatActivity {

    Button backPressed;
    TextView uploadTextView;
    ImageView uploadImageView;

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

            }
        }



        super.onActivityResult(requestCode, resultCode, data);
    }
}
