package com.example.surakchya;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MessageUpdate extends AppCompatActivity {
    EditText editText;
    Button update_button;
    String message;
    String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_update);

        editText = (EditText) findViewById(R.id.update_message_text);
        update_button = (Button) findViewById(R.id.update_button);

        manageIntentData();
        update_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MessageDatabaseHelper messageDatabaseHelper = new MessageDatabaseHelper(MessageUpdate.this);
                message = editText.getText().toString().trim();
                messageDatabaseHelper.updateMessage(id,message);
                Toast.makeText(MessageUpdate.this, "Update button pressed!", Toast.LENGTH_LONG).show();
            }
        });
        //editText.setText();



    }
    void manageIntentData(){
        if(getIntent().hasExtra("message_id") && getIntent().hasExtra("message_content")){
            id = getIntent().getStringExtra("message_id");
            message = getIntent().getStringExtra("message_content");
        }
        editText.setText(message);
    }
}