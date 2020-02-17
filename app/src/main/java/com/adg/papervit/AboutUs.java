package com.adg.papervit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;

public class AboutUs extends AppCompatActivity {

    private ImageView ishaanLinkedIn, ishaanGitHub, ishaanMail, akshitLinkedIn, akshitGitHub, akshitMail;
    private String ishaanLinkedInLink = "https://www.linkedin.com/in/ishaanohri/";
    private String ishaanGitHubLink = "https://github.com/IshaanOhri";
    private String ishaanMailLink = "ishaan99ohri@gmail.com";
    private String akshitLinkedInLink = "https://www.linkedin.com/in/akshit-sadana-b051ab121/";
    private String akshitGitHubLink = "https://github.com/Akshit8";
    private String akshitMailLink = "akshitsadana@gmail.com";
    private String privacyPolicy = "https://ishaanohri.github.io/papervit-privacy-policy.github.io/";
    private CardView privacyPolicyCardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.activity_about_us);

        ishaanLinkedIn = findViewById(R.id.ishaanLinkedIn);
        ishaanGitHub = findViewById(R.id.ishaanGitHub);
        ishaanMail = findViewById(R.id.ishaanMail);
        akshitLinkedIn = findViewById(R.id.akshitLinkedIn);
        akshitGitHub = findViewById(R.id.akshitGitHub);
        akshitMail = findViewById(R.id.akshitMail);
        privacyPolicyCardView = findViewById(R.id.privacyPolicyCardView);

        privacyPolicyCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(privacyPolicy));
                startActivity(intent);
            }
        });

        ishaanLinkedIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(ishaanLinkedInLink));
                startActivity(intent);
            }
        });

        ishaanGitHub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(ishaanGitHubLink));
                startActivity(intent);
            }
        });

        ishaanMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent email = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto",ishaanMailLink,null
                ));
                email.putExtra(Intent.EXTRA_SUBJECT, "Contact regarding PaperVIT");
                startActivity(Intent.createChooser(email,"Send Email..."));
            }
        });

        akshitLinkedIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(akshitLinkedInLink));
                startActivity(intent);
            }
        });

        akshitGitHub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(akshitGitHubLink));
                startActivity(intent);
            }
        });

        akshitMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent email = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto",akshitMailLink,null
                ));
                email.putExtra(Intent.EXTRA_SUBJECT, "Contact regarding PaperVIT");
                startActivity(Intent.createChooser(email,"Send Email..."));
            }
        });

    }
}