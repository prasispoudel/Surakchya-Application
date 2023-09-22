package com.example.surakchya;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.InputStream;

public class Instruction extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instruction);

        TextView heading = (TextView) findViewById(R.id.heading_help);
        Button selfdefence = (Button) findViewById(R.id.button_selfdefence);
        Button pshycological  = (Button) findViewById(R.id.button_psycological);
        Button legal = (Button) findViewById(R.id.button_Legal);

        selfdefence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),SelfDefence.class);
                startActivity(intent);
            }
        });
        pshycological.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             Intent intent = new Intent(getApplicationContext(),psycologialGuide.class);
             startActivity(intent);
            }
        });
        legal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),legalGuide.class);
                startActivity(intent);
            }
        });
    }
}