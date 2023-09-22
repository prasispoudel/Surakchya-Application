package com.example.surakchya;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ActionBarOverlayLayout;

import android.Manifest;
import android.app.ActionBar;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class AddContact extends AppCompatActivity {
    EditText name;
    EditText number;
   // Integer PICK_CONTACT;
   private final int PICK_CONTACT = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);
        Button button = (Button) findViewById(R.id.update_button);
        ImageButton imageButton = (ImageButton) findViewById(R.id.list_Contact_Button);
        name = (EditText) findViewById(R.id.name_text);
        number = (EditText) findViewById(R.id.add_new_contact_number);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toast.makeText(AddContact.this,getString(R.string.adding_conact),Toast.LENGTH_SHORT).show();
               // intializing my database  helper class within the current context of AddContact to call the addEmergencyContact() method in the MyDatabaseHelper class
                MyDataBaseHelper myDataBaseHelper = new MyDataBaseHelper(AddContact.this);
                myDataBaseHelper.addEmergencyContact(name.getText().toString().trim(),number.getText().toString().trim());
            }
        });
        imageButton.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
              //  intent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
                startActivityForResult(intent,PICK_CONTACT);
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_CONTACT){
            Uri contactData = data.getData();
             Cursor cursor = getContentResolver().query(contactData,null,null,null,null);

//           Cursor cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,null,null,null);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && checkSelfPermission(Manifest.permission.READ_CONTACTS)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, 2000);
            }else{
              // Cursor cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,null,null);
                if(cursor.moveToFirst()){
                     String contactname = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.Contacts.DISPLAY_NAME));
                     this.name.setText(contactname);
                   // String contactname = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                  //  Toast.makeText(this,"You have picked:- "+contactname, Toast.LENGTH_LONG).show();
                    // String number = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.Contacts.HAS_PHONE_NUMBER));
                    String phonenumber= cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    this.number.setText(phonenumber);
                   // Toast.makeText(this,"Contact Number:- "+phonenumber, Toast.LENGTH_LONG).show();
                }
            }


        }

    }
}