package com.adg.papervit;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.OpenableColumns;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLongPressListener;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class Upload extends AppCompatActivity {

    private CardView pickButton, cancelCardView, submitCardView, cardView3;
    private final int PDF_CODE = 100;
    private PDFView pdfView;
    private TextView fileNameTextView, submit;
    private Uri uri;
    private int READ_STORAGE = 100;
    private int WRITE_STORAGE = 101;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.activity_upload);

        pickButton = findViewById(R.id.addFileCardView);
        pdfView = findViewById(R.id.pdfViewer);
        cancelCardView = findViewById(R.id.cancelCardView);
        submitCardView = findViewById(R.id.sumbitCardView);
        fileNameTextView = findViewById(R.id.fileNameTextView);
        submit = findViewById(R.id.submit);
        cardView3 = findViewById(R.id.cardView3);

        fileNameTextView.setText(R.string.add);
        submitCardView.setEnabled(false);
        submitCardView.setCardBackgroundColor(getResources().getColor(R.color.disableColor));
        submit.setAlpha(0.4f);

        submitCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMail();
            }
        });

        cancelCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Upload.super.onBackPressed();
            }
        });

        pickButton.setOnClickListener(new View.OnClickListener() {
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
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    intent.setType("application/pdf");
                    startActivityForResult(intent,PDF_CODE);
                }
            }
        });
    }

    private void sendMail() {
        //CONVERTING FILE TO BYTE ARRAY
        InputStream is = null;
        byte[] bytesArray = null;
        try {
            is = getApplication().getContentResolver().openInputStream(uri);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            bytesArray = new byte[is.available()];
            is.read(bytesArray);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //SAVING FILE
        final File dwldsPath = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM) + "/" + "PaperUpload" + ".pdf");
        FileOutputStream os = null;
        try {
            os = new FileOutputStream(dwldsPath, false);
            os.write(bytesArray);
            os.flush();
            os.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //MAILING
        JavaMailAPI javaMailAPI = new JavaMailAPI(this,"ishaan.ohri2018@vitstudent.ac.in","UPLOAD PAPER","",Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM) + "/" + "PaperUpload" + ".pdf");
        javaMailAPI.execute();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[ ] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        
        if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && requestCode == READ_STORAGE)
        {

            if(checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
            {
                requestPermissions(new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE},WRITE_STORAGE);
            }
            else {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("application/pdf");
                startActivityForResult(intent,PDF_CODE);
            }
        }
        else if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && requestCode == WRITE_STORAGE)
        {

            if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
            {
                requestPermissions(new String[] {Manifest.permission.READ_EXTERNAL_STORAGE},WRITE_STORAGE);
            }
            else {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("application/pdf");
                startActivityForResult(intent,PDF_CODE);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == PDF_CODE)
        {
            if (resultCode == RESULT_OK && intent != null)
            {
                uri = intent.getData();
                String uriString = uri.toString();
                File myFile = new File(uriString);
                String path = myFile.getPath();
                String displayName = null;

                if (uriString.startsWith("content://")) {
                    Cursor cursor = null;
                    try {
                        cursor = Upload.this.getContentResolver().query(uri, null, null, null, null);
                        if (cursor != null && cursor.moveToFirst()) {
                            displayName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                        }
                    } finally {
                        cursor.close();
                    }
                } else if (uriString.startsWith("file://")) {
                    displayName = myFile.getName();
                }

                fileNameTextView.setText(displayName);

                submitCardView.setEnabled(true);
                submitCardView.setCardBackgroundColor(getColor(R.color.cardColor));
                submit.setAlpha(1.0f);

                final String finalDisplayName = displayName;
                pdfView.fromUri(uri)
                        .onLongPress(new OnLongPressListener() {
                            @Override
                            public void onLongPress(MotionEvent e) {
                                Toast.makeText(Upload.this, finalDisplayName, Toast.LENGTH_SHORT).show();
                            }
                        })

                        .load();
            }
        }
        super.onActivityResult(requestCode, resultCode, intent);
    }
    @Override
    public void onBackPressed() {
    }
}
