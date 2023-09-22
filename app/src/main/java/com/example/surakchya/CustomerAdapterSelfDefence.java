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

public class CustomerAdapterSelfDefence extends RecyclerView.Adapter<CustomerAdapterSelfDefence.MyViewHolder> {
    private Context context;
    private ArrayList selfDefenceGuideContent, SelfDefenceGuideHeading, SelfDefenceID ;
    Activity activity;

    public CustomerAdapterSelfDefence(Context context, ArrayList selfDefenceGuideContent, ArrayList selfDefenceGuideHeading, ArrayList selfDefenceID, Activity activity) {
        this.context = context;
        this.selfDefenceGuideContent = selfDefenceGuideContent;
        this.SelfDefenceGuideHeading = selfDefenceGuideHeading;
        this.SelfDefenceID = selfDefenceID;
        this.activity = activity;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view  =layoutInflater.inflate(R.layout.data_row_selfdefence,parent,false);
        return new CustomerAdapterSelfDefence.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.Self_ID.setText(String.valueOf(SelfDefenceID.get(position)));
        holder.Self_heading.setText(String.valueOf(SelfDefenceGuideHeading.get(position)));
        holder.SelfDefence_Guide_txt.setText(String.valueOf(selfDefenceGuideContent.get(position)));
        holder.mainlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,SelfDefenceDetail.class);
                intent.putExtra("Header",String.valueOf(SelfDefenceGuideHeading.get(position)));
                intent.putExtra("Content",String.valueOf(selfDefenceGuideContent.get(position)));
                activity.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return selfDefenceGuideContent.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView SelfDefence_Guide_txt, Self_heading,Self_ID;
        LinearLayout mainlayout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            Self_ID= itemView.findViewById(R.id.self_id_txt);
            Self_heading = itemView.findViewById(R.id.defence_header_txt);
            SelfDefence_Guide_txt = itemView.findViewById(R.id.textViewSelfDefencecontent);
            mainlayout = itemView.findViewById(R.id.mainLayoutSelfDefence);
        }
    }
}
