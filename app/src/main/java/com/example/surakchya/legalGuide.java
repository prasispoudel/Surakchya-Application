package com.example.surakchya;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;

public class legalGuide extends AppCompatActivity {
    RecyclerView recyclerView;
    LegalDataBaseHelper legalDataBaseHelper;
    ArrayList<String> legalGuideID, legalGuideContent, legalGuideHeader;
    CustomerAdapterLegalGuide adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_legal_guide);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerLegal);
        legalDataBaseHelper = new LegalDataBaseHelper(legalGuide.this);

        if(legalDataBaseHelper.getLegalGuideContent().getCount() == 0){
            legalDataBaseHelper.AddLegalGuideContent(getString(R.string.legalConsultation),"This is the legal guide content");
        }


        /*
        legalDataBaseHelper.AddLegalGuideContent("The contract between the media provider and applications. Contains definitions for the supported URIs and columns.\n" +
                "\n" +
                "The media provider provides an indexed collection of common media types, such as Audio, Video, and Images, from any attached storage devices. Each collection is " +
                "organized based on the primary MIME type of the underlying content;" +
                " for example, image/* content is indexed under Images. The Files collection provides a broad view across all collections, and does not filter by MIME type.");
         */

        legalGuideID = new ArrayList<String>();
        legalGuideHeader = new ArrayList<String>();
        legalGuideContent = new ArrayList<String>();
        loadData();
        adapter = new CustomerAdapterLegalGuide(this,legalGuideContent,legalGuideHeader,legalGuideID,legalGuide.this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(legalGuide.this));

    }

    void loadData(){
        Cursor cursor = legalDataBaseHelper.getLegalGuideContent();
        if(cursor.getCount() == 0){
            Toast.makeText(this,getString(R.string.no_data_found),Toast.LENGTH_SHORT);
        }else{
            while (cursor.moveToNext()){
                legalGuideID.add(cursor.getString(0));
                legalGuideHeader.add(cursor.getString(1));
                legalGuideContent.add(cursor.getString(2));
            }
        }
    }

}