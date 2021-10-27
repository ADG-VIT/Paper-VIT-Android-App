package com.adgvit.papervit2.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.adgvit.papervit2.Activity.Exam;
import com.adgvit.papervit2.Object.HomeObject;
import com.adgvit.papervit2.R;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.List;

public class RecyclerAdapterExamMain extends RecyclerView.Adapter<RecyclerAdapterExamMain.ExamViewHolder> {

    Context context;
    List<HomeObject> list;

    public static Boolean showShimmer = false;
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


        if (showShimmer){
            holder.shimmerFrameLayout.startShimmer();
        }else {
            //holder.examSubNameTV.setText(subName);
            holder.shimmerFrameLayout.stopShimmer();
            holder.shimmerFrameLayout.setShimmer(null);
            holder.examNameTV.setBackground(null);
            holder.examNameTV.setText(list.get(position).getExamType());
            holder.examSubNameTV.setBackground(null);
            holder.examSubNameTV.setText(list.get(position).getExamName());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {                                //For CAT1, NOW testing
                    Intent intent = new Intent(context, Exam.class);
                    intent.putExtra("type", String.valueOf(list.get(position).get_id()));
                    intent.putExtra("name", list.get(position).getExamType());
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

    }

    @Override
    public int getItemCount() {
        return showShimmer? 4 : list.size();
    }

    public class ExamViewHolder extends RecyclerView.ViewHolder{

        TextView examNameTV;
        TextView examSubNameTV;
        ShimmerFrameLayout shimmerFrameLayout;
        public ExamViewHolder(@NonNull View itemView) {
            super(itemView);

            examNameTV = itemView.findViewById(R.id.examNameTextView);
            examSubNameTV = itemView.findViewById(R.id.examSubNameTextView);
            shimmerFrameLayout = itemView.findViewById(R.id.homeShimmer);
            context = itemView.getContext();

        }
    }

}
