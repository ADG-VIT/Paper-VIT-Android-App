package com.adgvit.papervit2.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.adgvit.papervit2.R;

import static java.lang.String.format;

public class Settings extends AppCompatActivity {
    private ConstraintLayout aboutUs,feedback,refer,privacy,linkedin,facebook,instagram,rate,terms, settings;
    private final String appURL = "https://play.google.com/store/apps/details?id=com.adgvit.papervit";

    private static final String PRIVACY_POLICY = "https://fakeyudi.notion.site/Privacy-Policy-e74a1361a6774ebfa2d443f411c0191f";

    ImageView backImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        backImageView = findViewById(R.id.backImageView);
        backImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        settings = findViewById(R.id.appearanceSettings);
        aboutUs = findViewById(R.id.aboutUsSettings);
        feedback = findViewById(R.id.feedbackSettings);
        refer = findViewById(R.id.referSettings);
        privacy = findViewById(R.id.privacySettings);
        linkedin = findViewById(R.id.linkedinSettings);
        facebook = findViewById(R.id.facebookreferSettings);
        instagram = findViewById(R.id.instagramSettings);
        rate = findViewById(R.id.rateSettings);
        //terms = findViewById(R.id.termsSettings);

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),AppearenceSettings.class));
            }
        });

        aboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Settings.this,AboutUs.class));
            }
        });

        feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_EMAIL,new String[] {"appledevelopersgroup@gmail.com"});
                intent.putExtra(Intent.EXTRA_SUBJECT,"");
                intent.putExtra(Intent.EXTRA_TEXT,"");
                intent.setType("message/rfc822");
                startActivity(intent);
            }
        });

        refer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent shareIntent =   new Intent(android.content.Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT,"");
                String text = "With PaperVIT by your side, ab " +"*back*"+" nhi lagegi!!\n"+
                        "Discover frequently asked questions, question paper pattern and model questions through old question papers available on PaperVIT.\n" +
                        "Jab "+"*6 hazar*"+" ki ho baat, to yeh bhi try karlo yaar.\n"+"App Link: "+ appURL;
                shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, text);
                v.getContext().startActivity(Intent.createChooser(shareIntent,"Share via"));
            }
        });

        rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse(appURL);
                Intent intent = new Intent(Intent.ACTION_VIEW,uri);
                v.getContext().startActivity(intent);
            }
        });

        /*terms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://tnc.adgvit.com/papervit");
                Intent intent = new Intent(Intent.ACTION_VIEW,uri);
                v.getContext().startActivity(intent);
            }
        });*/
        privacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Uri uri = Uri.parse(PRIVACY_POLICY);
                Intent intent = new Intent(Intent.ACTION_VIEW,uri);
                v.getContext().startActivity(intent);
            }
        });

        linkedin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://www.linkedin.com/company/adgvit/");
                Intent intent = new Intent(Intent.ACTION_VIEW,uri);
                v.getContext().startActivity(intent);
            }
        });

        facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://www.facebook.com/vitios/");
                Intent intent = new Intent(Intent.ACTION_VIEW,uri);
                v.getContext().startActivity(intent);
            }
        });

        instagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://www.instagram.com/adgvit/");
                Intent intent = new Intent(Intent.ACTION_VIEW,uri);
                v.getContext().startActivity(intent);
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home)
        {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}