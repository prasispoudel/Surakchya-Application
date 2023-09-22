package com.example.surakchya;


import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomerAdapterEmergencyLog extends RecyclerView.Adapter<CustomerAdapterEmergencyLog.MyViewHolder> {
    private Context context;
    private ArrayList emergencyid, emergencyContact,emergencyContent,emergencyLattitude,emergencyLongitude;
    Activity activity;

    public CustomerAdapterEmergencyLog(Context context, ArrayList emergencyid, ArrayList emergencyContact, ArrayList emergencyContent, ArrayList emergencyLattitude, ArrayList emergencyLongitude, Activity activity) {
        this.context = context;
        this.emergencyid = emergencyid;
        this.emergencyContact = emergencyContact;
        this.emergencyContent = emergencyContent;
        this.emergencyLattitude = emergencyLattitude;
        this.emergencyLongitude = emergencyLongitude;
        this.activity = activity;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view  =layoutInflater.inflate(R.layout.emergency_log,parent,false);
        return new CustomerAdapterEmergencyLog.MyViewHolder(view);
        // return null;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.emergency_id_txt.setText(String.valueOf(emergencyid.get(position)));
        holder.emergency_contact_txt.setText(String.valueOf(emergencyContact.get(position)));
        holder.emergency_content_txt.setText(String.valueOf(emergencyContent.get(position)));
        holder.emergency_lattitude_txt.setText(String.valueOf(emergencyLattitude.get(position)));
        holder.emergency_longitude_txt.setText(String.valueOf(emergencyLongitude.get(position)));
        /*
        holder.mainlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

         */
    }

    @Override
    public int getItemCount() {
        return emergencyid.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView emergency_id_txt,emergency_contact_txt,emergency_content_txt,emergency_lattitude_txt,emergency_longitude_txt;
        LinearLayout mainlayout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            emergency_id_txt = itemView.findViewById(R.id.emergency_id_txt);
            emergency_contact_txt = itemView.findViewById(R.id.emergency_contact_txt);
            emergency_content_txt = itemView.findViewById(R.id.emergency_content_txt);
            emergency_lattitude_txt = itemView.findViewById(R.id.emergency_lattitude_txt);
            emergency_longitude_txt = itemView.findViewById(R.id.emergency_longitude_txt);
            mainlayout = itemView.findViewById(R.id.mainLayout);
        }
    }
}
