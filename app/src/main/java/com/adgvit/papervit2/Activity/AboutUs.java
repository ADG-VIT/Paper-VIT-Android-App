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

    String[] names = {"Utkarsh Dixit", "Riddhi Gupta", "Henit Chobisa", "Samridh Agarwal", "Vaibhav Shah", "Divesh Arora", "Panshul Jindal", "Siddharth Singh", "Aaron Mathew", "Ansh Goel", "Abhinav Gorantla", "Ishaan Ohri", "Akshit Sadana"};
    String[] techStack = {"iOS Developer, UI/UX Designer", "iOS Developer", "iOS Developer", "iOS Developer", "iOS Developer", "Backend Developer", "Android Developer", "Android Developer", "Android Developer", "Android Developer", "Backend Developer", "Backend Developer", "Backend Developer"};
    String[] images = {"https://res.cloudinary.com/fakeyudi/image/upload/v1634721032/PaperVITDevs/utkarsh_ybhllc.png", "https://res.cloudinary.com/fakeyudi/image/upload/v1634721026/PaperVITDevs/riddhi_fkns0l.png", "https://res.cloudinary.com/fakeyudi/image/upload/v1634720186/PaperVITDevs/Henit_uyednj.png", "https://res.cloudinary.com/fakeyudi/image/upload/v1634720188/PaperVITDevs/Samridh_ecucbe.png", "https://res.cloudinary.com/fakeyudi/image/upload/v1634720187/PaperVITDevs/Vaibhav_ajk1q0.png", "https://res.cloudinary.com/fakeyudi/image/upload/v1634752211/PaperVITDevs/Divesh_mekk38.png", "https://res.cloudinary.com/fakeyudi/image/upload/v1634752289/PaperVITDevs/Panshul_avthzc.png", "https://res.cloudinary.com/fakeyudi/image/upload/v1634760712/PaperVITDevs/Siddharth_cm55hu.png", "https://res.cloudinary.com/fakeyudi/image/upload/v1634760706/PaperVITDevs/Aaron_yujutr.png", "https://res.cloudinary.com/fakeyudi/image/upload/v1634760711/PaperVITDevs/Ansh_vyhcw9.png", "https://res.cloudinary.com/fakeyudi/image/upload/v1634760712/PaperVITDevs/Abhinav_dkyvqo.png", "https://res.cloudinary.com/fakeyudi/image/upload/v1634760713/PaperVITDevs/Ishaan_moo5pe.png", "https://res.cloudinary.com/fakeyudi/image/upload/v1634760822/PaperVITDevs/Akshit_awmcap.jpg"};


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
        recyclerView.setAdapter(new DevelopersRecyclerViewAdapter(AboutUs.this,names,techStack, images));

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}