package com.example.surakchya;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ContactUpdate extends AppCompatActivity {
    EditText contact_name;
    EditText contact_number;
    int contact_id;
    String contact_name_txt;
    String contact_number_txt;
    String name;
    String number;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_update);
        contact_name = (EditText) findViewById(R.id.name_text);
        contact_number = (EditText) findViewById(R.id.number_text);
        Button updateButton = (Button) findViewById(R.id.update_button);
        Button deleteButton = (Button) findViewById(R.id.delete_button);
        updateButton.setText(R.string.update_emergency_message);
        manageIntent();
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyDataBaseHelper databaseHelper = new MyDataBaseHelper(ContactUpdate.this);
                name = contact_name.getText().toString().trim();
                number = contact_number.getText().toString().trim();
                databaseHelper.updateContact(String.valueOf(contact_id),name,number);
                Toast.makeText(ContactUpdate.this, "Update button pressed!", Toast.LENGTH_LONG).show();
            }
        });
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               MyDataBaseHelper myDataBaseHelper = new MyDataBaseHelper(ContactUpdate.this);
               myDataBaseHelper.removeEmergencyContact(String.valueOf(contact_id));
                Toast.makeText(ContactUpdate.this, "Delete button pressed!", Toast.LENGTH_LONG).show();
            }
        });

    }
    void manageIntent(){
        if(getIntent().hasExtra("contact_id") && getIntent().hasExtra("contact_name") && getIntent().hasExtra("contact_number")){
            contact_id = Integer.parseInt(getIntent().getStringExtra("contact_id"));
            contact_name_txt  = getIntent().getStringExtra("contact_name");
            contact_number_txt = getIntent().getStringExtra("contact_number");
        }else{
            Log.d("CUSUPDATE","empty intent");
        }
        contact_name.setText(contact_name_txt);
        contact_number.setText(contact_number_txt);
    }
}