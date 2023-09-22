package com.example.surakchya;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class PsychologicalGuideDetail extends AppCompatActivity {
    TextView headerPsycho;
    TextView contentPsycho;
    String content;
    String header;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_psychological_guide_detail);
        headerPsycho = (TextView) findViewById(R.id.textViewPsychoHeading);
        contentPsycho = (TextView) findViewById(R.id.textViewPsychocontent);
        manageData();

    }
    void manageData(){
        if(getIntent().hasExtra("Header") && getIntent().hasExtra("Content")){
            header = getIntent().getStringExtra("Header");
            content = getIntent().getStringExtra("Content");
        }
        headerPsycho.setText(header);
        contentPsycho.setText(content);
    }
}