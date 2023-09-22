package com.example.surakchya;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;

public class SelfDefence extends AppCompatActivity {
    RecyclerView recyclerView;
    SelfDefenceDataBaseHelper selfDefenceDataBaseHelper;
    ArrayList<String> Self_ID, Self_Content, Self_Heading;
    CustomerAdapterSelfDefence adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_self_defence);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerSelfDefence);
        selfDefenceDataBaseHelper = new SelfDefenceDataBaseHelper(this);
        if(selfDefenceDataBaseHelper.getAllSelfDefenceGuideContent().getCount() == 0){
            selfDefenceDataBaseHelper.AddSelfDefenceGuideContent("Self Defence Heading","Self Defence Content");
        }

        Self_ID = new ArrayList<String>();
        Self_Heading = new ArrayList<String>();
        Self_Content = new ArrayList<String>();
        loadData();
        adapter = new CustomerAdapterSelfDefence(this,Self_Content,Self_Heading,Self_ID,SelfDefence.this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(SelfDefence.this));
    }

    void loadData(){
        Cursor cursor = selfDefenceDataBaseHelper.getAllSelfDefenceGuideContent();
        if(cursor.getCount() == 0){
            Toast.makeText(this,getString(R.string.no_data_found),Toast.LENGTH_SHORT);
        }else{
            while (cursor.moveToNext()){
                Self_ID.add(cursor.getString(0));
                Self_Heading.add(cursor.getString(1));
                Self_Content.add(cursor.getString(2));
            }
        }
    }
}