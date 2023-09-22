package com.example.surakchya;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class FirstAid extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_aid);
        Button backButton = (Button) findViewById(R.id.Back_button);
        Button nextButton = (Button) findViewById(R.id.nextButton);
        ImageView imageView = (ImageView) findViewById(R.id.imageView2);
        imageView.setImageResource(R.drawable.first_index);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),FristAidPage2.class);
                startActivity(intent);
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent = new Intent(getApplicationContext(),MainActivity.class);
               startActivity(intent);
           }
        });
    }

}