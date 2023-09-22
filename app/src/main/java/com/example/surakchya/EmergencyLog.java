package com.example.surakchya;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class EmergencyLog extends AppCompatActivity {
    EmergencyLogDatabaseHelper myDataBaseHelper;
    RecyclerView recyclerView;
    private Context context;
    ArrayList<String> emergencyid,emergencycontact,emergencycontent,emergencylattitude,emergencylongitude;
    CustomerAdapterEmergencyLog adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency_log);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewEmergency);
        myDataBaseHelper = new EmergencyLogDatabaseHelper(EmergencyLog.this);
       // myDataBaseHelper.addEmergencyLog("9860903837","Emergency Message","25.23232","-121.65131");

        emergencyid = new ArrayList<String>();
        emergencycontact = new ArrayList<String>();
        emergencycontent = new ArrayList<String>();
        emergencylattitude = new ArrayList<String>();
        emergencylongitude = new ArrayList<String>();
        loadData();
        adapter = new CustomerAdapterEmergencyLog(this,emergencyid,emergencycontact,emergencycontent,emergencylattitude,emergencylongitude,EmergencyLog.this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(EmergencyLog.this));
    }
    void loadData(){
        Cursor cursor = myDataBaseHelper.getAllEmergencyLog();
        if(cursor.getCount() == 0){
            Toast.makeText(this,getString(R.string.no_data_found),Toast.LENGTH_SHORT);
        }else{
            while (cursor.moveToNext()){
                emergencyid.add(cursor.getString(0));
                emergencycontact.add(cursor.getString(1));
                emergencycontent.add(cursor.getString(2));
                emergencylattitude.add(cursor.getString(3));
                emergencylongitude.add(cursor.getString(4));
            }
        }
    }

}