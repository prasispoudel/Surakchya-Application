package com.example.surakchya;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MessageEditor extends AppCompatActivity {
    MessageDatabaseHelper myDataBaseHelper;
    RecyclerView recyclerViewMessage;
    private Context context;
    ArrayList<String> messageid,messagecontent;
    CustomAdapterMessage adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_editor);
        recyclerViewMessage = (RecyclerView) findViewById(R.id.recyclerViewMessage);
        myDataBaseHelper = new MessageDatabaseHelper(MessageEditor.this);
        // inserting defualt message in the database
        if(myDataBaseHelper.getEmergencyMessage().getCount() == 0){
            //myDataBaseHelper.addEmergencymessage("Help me! I am in danger");
            String defualt_message = (String) getString(R.string.emergency_click);
            myDataBaseHelper.addEmergencymessage(defualt_message);
            myDataBaseHelper.addEmergencymessage(getString(R.string.accident_happen));
        }
       // myDataBaseHelper.truncateMessage(String.valueOf(1));
        messageid = new ArrayList<String>();
        messagecontent = new ArrayList<String>();
       storeMessage();
        adapter = new CustomAdapterMessage(MessageEditor.this,this,messageid,messagecontent);
        recyclerViewMessage.setAdapter(adapter);
        // showing inside recycler view within Emergency Contact Activity
        recyclerViewMessage.setLayoutManager(new LinearLayoutManager(MessageEditor.this));
    }


    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            recreate();
        }
    }

    void storeMessage(){
        Cursor cursor = myDataBaseHelper.getEmergencyMessage();
        if(cursor.getCount() == 0){
//            Toast.makeText(this.context,"No data found",Toast.LENGTH_SHORT).show();
        }else{
            while (cursor.moveToNext()){
                messageid.add(cursor.getString(0));
                messagecontent.add(cursor.getString(1));
            }
        }
    }

    public ArrayList<String> getMessagecontent() {
        return messagecontent;
    }
}