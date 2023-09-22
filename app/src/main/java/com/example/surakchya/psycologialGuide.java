package com.example.surakchya;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;

public class psycologialGuide extends AppCompatActivity {
    RecyclerView recyclerView;
    PsychologicalDataBaseHelper psychologicalDataBaseHelper;
    ArrayList<String> psycho_ID, psycho_Content, psycho_Header;
    CustomerAdapterPsychologicalGuide customerAdapterPsychologicalGuide;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_psycologial_guide);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerphycological);
        psychologicalDataBaseHelper = new PsychologicalDataBaseHelper(this);

        if(psychologicalDataBaseHelper.getAllPsychologicalGuideContent().getCount() == 0){
            psychologicalDataBaseHelper.AddPsychologicalGuideContent("Psychological Guide Header","Psychological Guide Content");
        }

        psycho_ID = new ArrayList<String>();
        psycho_Header = new ArrayList<String>();
        psycho_Content = new ArrayList<String>();


        loadData();
        customerAdapterPsychologicalGuide = new CustomerAdapterPsychologicalGuide(this,psycho_Content,psycho_Header,psycho_ID,psycologialGuide.this);
        recyclerView.setAdapter(customerAdapterPsychologicalGuide);
        recyclerView.setLayoutManager(new LinearLayoutManager(psycologialGuide.this));
    }

    void loadData(){
        Cursor cursor = psychologicalDataBaseHelper.getAllPsychologicalGuideContent();
        if(cursor.getCount() == 0){
            Toast.makeText(this,getString(R.string.no_data_found),Toast.LENGTH_SHORT);
        }else{
            while (cursor.moveToNext()){
                psycho_ID.add(cursor.getString(0));
                psycho_Header.add(cursor.getString(1));
                psycho_Content.add(cursor.getString(2));
            }
        }
    }
}