package com.example.surakchya;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class SelfDefenceDetail extends AppCompatActivity {
    TextView headerSelfDefence;
    TextView contentSelfDefence;
    String content;
    String header;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_self_defence_detail);
        headerSelfDefence = (TextView) findViewById(R.id.textViewDefenceHeading);
        contentSelfDefence = (TextView) findViewById(R.id.textViewSelfcontent);
        ManageData();
    }
    void ManageData(){
        if(getIntent().hasExtra("Header") && getIntent().hasExtra("Content")){
            header = getIntent().getStringExtra("Header");
            content = getIntent().getStringExtra("Content");
        }
        headerSelfDefence.setText(header);
        contentSelfDefence.setText(content);
    }
}