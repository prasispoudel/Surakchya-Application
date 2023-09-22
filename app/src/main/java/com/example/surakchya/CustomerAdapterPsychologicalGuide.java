package com.example.surakchya;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomerAdapterPsychologicalGuide extends RecyclerView.Adapter<CustomerAdapterPsychologicalGuide.MyViewHolder> {


    private Context context;
    private ArrayList psychologicalGuideContent, psychologicalGuideHeading, psychologicalGuideID;
    Activity activity;

    public CustomerAdapterPsychologicalGuide(Context context, ArrayList psychologicalGuideContent, ArrayList psychologicalGuideHeading, ArrayList psychologicalGuideID, Activity activity) {
        this.context = context;
        this.psychologicalGuideContent = psychologicalGuideContent;
        this.psychologicalGuideHeading = psychologicalGuideHeading;
        this.psychologicalGuideID = psychologicalGuideID;
        this.activity = activity;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view  =layoutInflater.inflate(R.layout.data_row_psycological,parent,false);
        return new CustomerAdapterPsychologicalGuide.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.Psychological_Guide_ID.setText(String.valueOf(psychologicalGuideID.get(position)));
        holder.Psychological_Guide_Heading.setText(String.valueOf(psychologicalGuideHeading.get(position)));
         holder.Psychological_Guide_txt.setText(String.valueOf(psychologicalGuideContent.get(position)));
         holder.mainlayout.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent intent = new Intent(context,PsychologicalGuideDetail.class);
                 intent.putExtra("Header",String.valueOf(psychologicalGuideHeading.get(position)));
                 intent.putExtra("Content", String.valueOf(psychologicalGuideContent.get(position)));
                 activity.startActivity(intent);
             }
         });
    }

    @Override
    public int getItemCount() {
       return psychologicalGuideContent.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView Psychological_Guide_txt,Psychological_Guide_Heading,Psychological_Guide_ID;
        LinearLayout mainlayout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            Psychological_Guide_ID = itemView.findViewById(R.id.psycho_id_txt);
            Psychological_Guide_Heading = itemView.findViewById(R.id.psychological_content_txt);
            Psychological_Guide_txt = itemView.findViewById(R.id.textViewPsyhcologicalcontent);
            mainlayout = itemView.findViewById(R.id.mainLayoutPsychological);
        }
    }
}
