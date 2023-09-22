package com.example.surakchya;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class LegalGuideDetial extends AppCompatActivity {

    TextView headerLegal;
    TextView contentLegal;
    String content;
    String header;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_legal_guide_detial);
       // recyclerView = (RecyclerView) findViewById(R.id.recyclerViewContent);
        headerLegal = (TextView) findViewById(R.id.textViewLegalHeading);
        contentLegal = (TextView) findViewById(R.id.textViewcontent);
        ManageData();

    }

    void ManageData(){
        if(getIntent().hasExtra("Header") && getIntent().hasExtra("Content")){
            header = getIntent().getStringExtra("Header");
            content = getIntent().getStringExtra("Content");
        }
        headerLegal.setText(header);
        contentLegal.setText(content);
    }
}