package com.example.surakchya;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.provider.Telephony;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import static android.content.Context.LOCATION_SERVICE;

public class MessageService {
    final int SEND_SMS_PERMISSION_REQUEST_CODE =1;
    public LatLng latLng;
    private String Lat;
    private String Longitude;
    private Context context;
    MessageDatabaseHelper messageDatabaseHelper;
    MyDataBaseHelper myDataBaseHelper;
    CLocation location;
    ArrayList<String> contacts = new ArrayList<String>();
    private String messageContent;
    private LocationListener locationListener;
    private LocationManager locationManager;
    ArrayList<String> contactphone,message;
    EmergencyContact emergencyContact;
    MessageEditor messageEditor;
    private static final String TAG = "MessageService";
    private final long MIN_TIME = 1000; // 1 second
    private final long MIN_DIST = 5; // distance in meter (5 meters)
    // object of Latlng class for storing lattitude and longitude
   // public MessageService(String provider) {
     //   super(provider);
    //}

    /*
    public MessageService(String l, Context context) {
        super(l);
        this.context = context;
    }
     */

   /*
    public MessageService(LatLng latLng) {
      this.latLng = latLng;

    }
    */
    // added after
    /*
    public MessageService(String lat,String Long){
        this.Lat = lat;
        this.Longitude = Long;
//        this.latLng = new LatLng(Double.valueOf(this.Lat),Double.valueOf(this.Long));
    }
     */
   public MessageService(String lat,String Long,ArrayList<String> message,ArrayList<String> contactphone,Context context){
       this.Lat = lat;
       this.Longitude = Long;
       this.message = new ArrayList<String>();
       this.contactphone = new ArrayList<String>();
       this.context = context;
       for(String content: message){
           this.messageContent = content;
       }
      // this.message = message;
       for(String phone: contactphone){
           this.contactphone.add(phone);
       }
     //  this.contactphone=contactphone;
//        this.latLng = new LatLng(Double.valueOf(this.Lat),Double.valueOf(this.Long));
   }

    public String getLat() {
        return Lat;
    }

    public String getLongitude() {
        return Longitude;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

   /*
    @Override
    public double getLatitude() {
        return super.getLatitude();
    }
    public LatLng getLatLang(){
        return new LatLng(super.getLatitude(),super.getLongitude());
    }
    @Override
    public double getLongitude() {
        return super.getLongitude();
    }
    */
   // locationManager = (LocationManager) getLatLng(Context.LOCATION_SERVICE);


    void getInformation(){
       // messageDatabaseHelper = new MessageDatabaseHelper(this.context);
        //myDataBaseHelper = new MyDataBaseHelper(this.context);
        //    messageDatabaseHelper.getEmergencyMessage();
        /*
        emergencyContact = new EmergencyContact();
        emergencyContact.storeData();
        contactid = emergencyContact.getCustomerid();
        contactname = emergencyContact.getCustomername();
        contactphone = emergencyContact.getCustomerphone();
        messageEditor = new MessageEditor();
        message = messageEditor.getMessagecontent();

        /*
         */
        /*
        Cursor cursorMessage = messageDatabaseHelper.getEmergencyMessage();
        message = new ArrayList<String>();
        contactid = new ArrayList<String>();
        contactname = new ArrayList<String>();
        contactphone = new ArrayList<String>();
        if(cursorMessage.getCount() == 0){
            Toast.makeText(this.context,this.context.getString(R.string.no_data_found),Toast.LENGTH_SHORT);
        }else{
           while (cursorMessage.moveToNext()){
               message.add(cursorMessage.getString(1));
           }
        }


       Cursor cursor = myDataBaseHelper.getAllEmeregncyContacts();

       if(cursor.getCount() == 0){
           Toast.makeText(this.context,this.context.getString(R.string.no_data_found),Toast.LENGTH_SHORT);
       }else{
           while (cursor.moveToNext()){
               contactphone.add(cursor.getString(2));
           }
       }

         */

    }

    void getLocation(){
        /*
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                latLng = new LatLng(location.getLatitude(),location.getLongitude());
            }
        };
        locationManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);
        // check permission is required  here, but for now try catch is used for catching the security exception
        try{
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,MIN_TIME,MIN_DIST,locationListener);
        }catch (SecurityException e){
            e.printStackTrace();
        }
         */

        /*
         locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull android.location.Location location) {
                latLng = new LatLng(location.getLatitude(), location.getLongitude());
                setLatLng(latLng);
            }
        };

        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        // check permission is required  here, but for now try catch is used for catching the security exception
        try{
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,locationListener);
        }catch (SecurityException e){
            e.printStackTrace();
        }
         */
    }



    void sendMessage(){
        //  SmsManager smsManager = SmsManager.getDefault();
        // smsManager.sendTextMessage(phonenumber,null,Uri.parse(smsMessage.toString()).toString(),null,null);
       // getLocation();
       // getInformation();
        /*
        for(String content: message){
            this.messageContent = content;
        }
         */
        /* Working Code*/
      //  SmsManager smsManager = SmsManager.getDefault();
       // StringBuffer messageBody = new StringBuffer();
       // messageBody.append(this.messageContent);
      ///  messageBody.append(" ");
      //  messageBody.append("http://maps.google.com?q=");
       // messageBody.append(getLatLng().latitude);

       // messageBody.append(Lat);
       // messageBody.append(",");
       // messageBody.append(Longitude);
       // SmsManager smsManager = SmsManager.getDefault();
       // smsManager.sendTextMessage("9860903837",null, Uri.parse(messageBody.toString()).toString(),null,null);
//      /*----------------------------------------------*/
        try{
         if(this.Lat != null && this.Longitude != null){
             StringBuffer stringBuffer = new StringBuffer();
             stringBuffer.append(this.messageContent);
             stringBuffer.append(" ");
             stringBuffer.append("http://maps.googl.com?q=");
             stringBuffer.append(this.Lat);
             stringBuffer.append(",");
             stringBuffer.append(this.Longitude);
             SmsManager smsManager1 = SmsManager.getDefault();
             smsManager1.sendTextMessage("9841401956",null,Uri.parse(stringBuffer.toString()).toString(),null,null);
             Log.d(TAG,"MESSAGE SENT");
             /*
             if(checkPermission(Manifest.permission.SEND_SMS)){
                 SmsManager smsManager1 = SmsManager.getDefault();
                 try{
                     smsManager1.sendTextMessage("9841401956",null,Uri.parse(stringBuffer.toString()).toString(),null,null);
                 }catch (SecurityException e){
                     e.printStackTrace();
                 }

                 Log.d(TAG, "onMessage Sent: Phone Number: "+"9841401956");
             }else{
                 Log.d(TAG, "onMessage permission not provided");
             }
              */

             /*
             for(String contact: this.contactphone){
                 SmsManager smsManager1 = SmsManager.getDefault();
                 smsManager1.sendTextMessage(contact,null,Uri.parse(stringBuffer.toString()).toString(),null,null);
                 Log.d(TAG, "onMessage Sent: Phone Number: "+contact);
             }
              */
         }else{
             Toast.makeText(this.context,this.context.getString(R.string.no_data_found),Toast.LENGTH_SHORT);
             Log.d(TAG, "onMessage not Sent: Phone Number: "+"9841401956");
         }
        }catch (Exception e){
            e.printStackTrace();
        }
//
//        messageBody.append(getLatLng().longitude);
        /*
        try{
        if(contacts.size() != 0) {
            for (String contact : this.contactphone) {
                System.out.println(contact);
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(contact, null, Uri.parse(messageBody.toString()).toString(), null, null);
            }
        }
        }catch (Exception e){
            e.printStackTrace();
        }
         */

        /*
        if(checkPermission(Manifest.permission.SEND_SMS)){
            for (String contact: contacts) {
                System.out.println(contact);
                smsManager.sendTextMessage("9860903837",null,Uri.parse(messageBody.toString()).toString(),null,null);
            }
        }else{
            Toast.makeText(this.context,this.context.getString(R.string.message_not_sent),Toast.LENGTH_SHORT);

            // check permission in the main activity
            // ActivityCompat.requestPermissions(, new String[] {Manifest.permission.SEND_SMS},SEND_SMS_PERMISSION_REQUEST_CODE);
        }
         */
        //   smsMessage.append(",");
        //   smsMessage.append(Longitude);
        /*
        for (String contact: contacts) {
            System.out.println(contact);
            smsManager.sendTextMessage(contact,null,this.message,null,null);
        }
         */




       // String phoneNumber = "9860903837";
       // StringBuffer smsMessage = new StringBuffer();
       // SmsManager smsManager = SmsManager.getDefault();
      //  smsManager.sendTextMessage(phoneNumber,null, Uri.parse(smsMessage.toString()).toString(),null,null);
    }
    public boolean checkPermission(String permission){
        int check = ContextCompat.checkSelfPermission( this.context,permission);
        return (check == PackageManager.PERMISSION_GRANTED);
    }
     /*
    public static void main(String[] args) {

    }
      */

}
