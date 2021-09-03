package com.adgvit.papervit.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.adgvit.papervit.Activity.Exam;
import com.adgvit.papervit.Object.HomeObject;
import com.adgvit.papervit.R;

import java.util.List;

public class RecyclerAdapterExamMain extends RecyclerView.Adapter<RecyclerAdapterExamMain.ExamViewHolder> {

    Context context;
    List<HomeObject> list;

    public RecyclerAdapterExamMain(Context context, List<HomeObject> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerAdapterExamMain.ExamViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.exam_layout_item,parent,false);
        return new ExamViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapterExamMain.ExamViewHolder holder, int position) {



        holder.examNameTV.setText(list.get(position).getExamType());
        //holder.examSubNameTV.setText(subName);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {                                //For CAT1, NOW testing
                Intent intent = new Intent(context, Exam.class);
                intent.putExtra("type", String.valueOf(list.get(position).get_id()));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);

                //Exam.examType = "CAT 2";
                //Intent intent = new Intent(MainActivity.this,Exam.class);
                //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                //context.startActivity(intent);

                //Exam.examType = "FAT";
                //Intent intent = new Intent(MainActivity.this,Exam.class);
                //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                //context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ExamViewHolder extends RecyclerView.ViewHolder{

        TextView examNameTV;
        TextView examSubNameTV;

        public ExamViewHolder(@NonNull View itemView) {
            super(itemView);

            examNameTV = itemView.findViewById(R.id.examNameTextView);
            examSubNameTV = itemView.findViewById(R.id.examSubNameTextView);

            context = itemView.getContext();

        }
    }

}
