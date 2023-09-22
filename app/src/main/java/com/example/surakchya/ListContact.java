package com.example.surakchya;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class ListContact extends AppCompatActivity {
   // permission management is left  for accessing the contacts within the android device
    ListView listContact;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_contact);
        listContact = (ListView) findViewById(R.id.list_contact);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && checkSelfPermission(Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, 2000);
        } else {
            // Start the program if the permission is granted
            this.get(listContact);
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void get(View v){
        Cursor cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,null,null);
        startManagingCursor(cursor);
        // declaring and intializing array of strings with values of contacts name ,contact number and contact id
        String [] from = {ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,ContactsContract.CommonDataKinds.Phone.NUMBER, ContactsContract.CommonDataKinds.Phone._ID};

        int [] to = {android.R.id.text1,android.R.id.text2};
        Toast.makeText(this,this.getString(R.string.loading_local_contacts),Toast.LENGTH_SHORT).show();
        // declaring and initilizing simple Cursor adapter
        SimpleCursorAdapter simpleCursorAdapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_2,cursor,from,to);
        listContact.setAdapter(simpleCursorAdapter);
        listContact.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        listContact.setItemChecked(0,true);


    }
    /*
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //  super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 2000){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                this.get(listContact);
            }else {
                finish();
            }
        }
    }
     */
}