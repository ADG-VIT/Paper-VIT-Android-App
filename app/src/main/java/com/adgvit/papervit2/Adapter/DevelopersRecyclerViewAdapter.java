package com.adgvit.papervit2.Adapter;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.adgvit.papervit2.R;

import java.util.ArrayList;

public class DevelopersRecyclerViewAdapter extends RecyclerView.Adapter<DevelopersRecyclerViewAdapter.DeveloperViewHolder> {

    private String[] name;
    private String[] techStack;
    private int[] developersImage;

    public DevelopersRecyclerViewAdapter(String[] name, String[] techStack, int[] developersImage) {
        this.name = name;
        this.techStack = techStack;
        this.developersImage = developersImage;
    }

    @NonNull
    @Override
    public DevelopersRecyclerViewAdapter.DeveloperViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.developers_item,parent,false);
        return new DeveloperViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DevelopersRecyclerViewAdapter.DeveloperViewHolder holder, int position) {
        holder.developerName.setText(name[position]);
        holder.developerTech.setText(techStack[position]);
        holder.developerImage.setImageResource(developersImage[position]);
    }

    @Override
    public int getItemCount() {
        return name.length;
    }

    public class DeveloperViewHolder extends RecyclerView.ViewHolder {

        ImageView developerImage;
        TextView developerName;
        TextView developerTech;

        public DeveloperViewHolder(@NonNull View itemView) {
            super(itemView);
            developerImage = itemView.findViewById(R.id.developer_image);
            developerName = itemView.findViewById(R.id.developer_name);
            developerTech = itemView.findViewById(R.id.developer_tech);

        }
    }

}
