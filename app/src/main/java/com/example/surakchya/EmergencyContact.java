package com.example.surakchya;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class EmergencyContact extends AppCompatActivity {
    MyDataBaseHelper myDataBaseHelper;
    RecyclerView recyclerView;
    FloatingActionButton add_button;
    private Context context;
    ArrayList<String> customerid,customername,customerphone;
    CustomAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergecy_contact);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        add_button = (FloatingActionButton) findViewById(R.id.add_button);
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddContact.class);
                startActivity(intent);
            }
        });
        myDataBaseHelper = new MyDataBaseHelper(EmergencyContact.this);
        customerid = new ArrayList<String>();
        customername = new ArrayList<String>();
        customerphone = new ArrayList<String>();
        storeData();
        adapter = new CustomAdapter(EmergencyContact.this,this,customerid,customername,customerphone);
        recyclerView.setAdapter(adapter);
        // showing inside recycler view within Emergency Contact Activity
        recyclerView.setLayoutManager(new LinearLayoutManager(EmergencyContact.this));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
         if(requestCode == 2){
             recreate();
         }
         if(resultCode == 2){
             recreate();
         }
    }

    void storeData(){
        Cursor cursor = myDataBaseHelper.getAllEmeregncyContacts();
        if(cursor.getCount() == 0){
        //     Toast.makeText(this.context,this.context.getString(R.string.no_data_found),Toast.LENGTH_SHORT).show();
        }else{
            while (cursor.moveToNext()){
                customerid.add(cursor.getString(0));
                customername.add(cursor.getString(1));
                customerphone.add(cursor.getString(2));
            }
        }
    }


    public ArrayList<String> getCustomerid() {
        return customerid;
    }

    public ArrayList<String> getCustomername() {
        return customername;
    }

    public ArrayList<String> getCustomerphone() {
        return customerphone;
    }
}