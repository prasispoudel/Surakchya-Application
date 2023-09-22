package com.example.surakchya;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class UserGuide extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_guide);
        Button buttonEmergency = (Button) findViewById(R.id.buttonUserEmergency);
        buttonEmergency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),UserGuideEmergency.class);
                startActivity(intent);
            }
        });
        Button buttonTwoWheeler = (Button) findViewById(R.id.buttonTwoWheeler);
        buttonTwoWheeler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent = new Intent(getApplicationContext(),UserGuideTwoWheeler.class);
               startActivity(intent);
            }
        });
        Button buttonEditMessage = (Button) findViewById(R.id.buttonUserMessage);
        buttonEditMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent = new Intent(getApplicationContext(),UserGuideMessage.class);
               startActivity(intent);
            }
        });
        Button buttonEditAudioSetting = (Button) findViewById(R.id.buttonUserRecording);
        buttonEditAudioSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent intent = new Intent(getApplicationContext(),UserGuideAudioSetting.class);
            startActivity(intent);
            }
        });
        Button buttonHelpDoc = (Button) findViewById(R.id.buttonUserHelpDoc);
        buttonHelpDoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             Intent intent = new Intent(getApplicationContext(),UserGuideHelpDoc.class);
             startActivity(intent);
            }
        });
        Button buttonFirstAid = (Button) findViewById(R.id.buttonUserFirstAid);
        buttonFirstAid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent intent = new Intent(getApplicationContext(),UserGuideFirstAid.class);
            startActivity(intent);
            }
        });
        Button buttonAddContact = (Button) findViewById(R.id.buttonUserContact);
        buttonAddContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             Intent intent = new Intent(getApplicationContext(),UserGuideAddContact.class);
             startActivity(intent);
            }
        });
    }
}