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

public class CustomAdapterMessage extends RecyclerView.Adapter<CustomAdapterMessage.MyViewHolderMessage> {

    private Context context;
    private ArrayList messageid,messagecontent;
    Activity activity;
    public CustomAdapterMessage(Activity activity,Context context, ArrayList messageid, ArrayList messagecontent) {
        this.activity = activity;
        this.context = context;
        this.messageid = messageid;
        this.messagecontent = messagecontent;
    }

    @NonNull
    @Override
    public CustomAdapterMessage.MyViewHolderMessage onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view  =layoutInflater.inflate(R.layout.data_row_message,parent,false);
        return new CustomAdapterMessage.MyViewHolderMessage(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomAdapterMessage.MyViewHolderMessage holder, int position) {
        holder.messageid_txt.setText(String.valueOf(messageid.get(position)));
        holder.messagecontent_txt.setText(String.valueOf(messagecontent.get(position)));
        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent intent = new Intent(context,MessageUpdate.class);
            intent.putExtra("message_id",String.valueOf(messageid.get(position)));
            intent.putExtra("message_content",String.valueOf(messagecontent.get(position)));
            activity.startActivityForResult(intent,1);
            }
        });
    }

    @Override
    public int getItemCount() {
        return messageid.size();
    }

    public class MyViewHolderMessage extends RecyclerView.ViewHolder{
        TextView messageid_txt,messagecontent_txt;
        // id in the data_row_message xml file
        LinearLayout mainLayout;
        public MyViewHolderMessage(@NonNull View itemView) {
            super(itemView);
            messageid_txt = itemView.findViewById(R.id.message_id_txt);
            messagecontent_txt = itemView.findViewById(R.id.message_content_txt);
            mainLayout = itemView.findViewById(R.id.mainLayoutMessage);

        }
    }
}
