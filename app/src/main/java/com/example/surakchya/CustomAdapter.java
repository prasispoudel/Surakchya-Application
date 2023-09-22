package com.example.surakchya;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {
   private Context context;
   private ArrayList customerid,customername,customerphone;
   Activity activity;
    public CustomAdapter(Activity activity,Context context, ArrayList customerid, ArrayList customername, ArrayList customerphone) {
        this.activity = activity;
        this.context = context;
        this.customerid = customerid;
        this.customername = customername;
        this.customerphone = customerphone;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view  =layoutInflater.inflate(R.layout.data_row,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        // binding the values from the array into the holder
        holder.customerid_txt.setText(String.valueOf(customerid.get(position)));
        holder.customername_txt.setText(String.valueOf(customername.get(position)));
        holder.customerphone_txt.setText(String.valueOf(customerphone.get(position)));
        holder.mainlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,ContactUpdate.class);
                intent.putExtra("contact_id",String.valueOf(customerid.get(position)));
                intent.putExtra("contact_name", String.valueOf(customername.get(position)));
                intent.putExtra("contact_number", String.valueOf(customerphone.get(position)));
                activity.startActivityForResult(intent,2);
            }
        });
        /*
        if( holder.mainlayoutContact != null){
            holder.mainlayoutContact.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context,ContactUpdate.class);
                    intent.putExtra("contact_id",String.valueOf(customerid.get(position)));
                    intent.putExtra("contact_name", String.valueOf(customername.get(position)));
                    intent.putExtra("contact_number", String.valueOf(customerphone.get(position)));
                    activity.startActivityForResult(intent,1);
                }
            });
        }else{
            Toast.makeText(this.context,"NULL",Toast.LENGTH_SHORT);
        }
         */

    }

    @Override
    public int getItemCount() {
        return customerid.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView customerid_txt,customername_txt,customerphone_txt;
        LinearLayout mainlayout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            customerid_txt = itemView.findViewById(R.id.customer_id_txt);
            customername_txt = itemView.findViewById(R.id.customer_name_txt);
            customerphone_txt = itemView.findViewById(R.id.customer_phone_txt);
            mainlayout = itemView.findViewById(R.id.mainLayout);
        }
    }
}
