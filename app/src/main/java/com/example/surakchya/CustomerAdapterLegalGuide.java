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

import java.io.InputStream;
import java.util.ArrayList;

public class CustomerAdapterLegalGuide  extends RecyclerView.Adapter<CustomerAdapterLegalGuide.MyViewHolder> {

    private Context context;
    private ArrayList legalGuideContent, LegalGuideHeader, LegalGuideID;
    Activity activity;

    public CustomerAdapterLegalGuide(Context context, ArrayList legalGuideContent, ArrayList legalGuideHeader, ArrayList legalGuideID, Activity activity) {
        this.context = context;
        this.legalGuideContent = legalGuideContent;
        this.LegalGuideHeader = legalGuideHeader;
        this.LegalGuideID = legalGuideID;
        this.activity = activity;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view  =layoutInflater.inflate(R.layout.data_row_legal,parent,false);
        return new CustomerAdapterLegalGuide.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.Legal_id.setText(String.valueOf(LegalGuideID.get(position)));
        holder.Legal_Guide_header.setText(String.valueOf(LegalGuideHeader.get(position)));
        holder.Legal_Guide_txt.setText(String.valueOf(legalGuideContent.get(position)));
      // holder.Legal_Guide_txt.setText(String.valueOf(legalGuideContent.get(position)));
      // holder.Legal_Guide_txt.setText(String.valueOf());
       holder.mainlayout.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent = new Intent(context,LegalGuideDetial.class);
                intent.putExtra("Header",String.valueOf(LegalGuideHeader.get(position)));
                intent.putExtra("Content",String.valueOf(legalGuideContent.get(position)));
                activity.startActivityForResult(intent,1);
           }
       });
    }

    @Override
    public int getItemCount() {
        return LegalGuideHeader.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView Legal_Guide_txt, Legal_Guide_header, Legal_id;
        LinearLayout mainlayout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            Legal_id = itemView.findViewById(R.id.legal_id_txt);
            Legal_Guide_txt = itemView.findViewById(R.id.textViewlegalcontent);
            Legal_Guide_header = itemView.findViewById(R.id.legal_header_txt);
            mainlayout = itemView.findViewById(R.id.mainLayoutLegal);
        }
    }
}
