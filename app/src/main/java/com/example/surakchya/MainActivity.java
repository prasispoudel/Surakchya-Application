package com.example.surakchya;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {
    CLocation cLocation;
    final int SEND_SMS_PERMISSION_REQUEST_CODE =1;
    private LocationListener locationListener;
    private LocationManager locationManager;
    private static LatLng latLng;
    MessageService myLocation;
    private  static Location loc;
    private static String Lattitude;
    private static String Longitude;
    private final long MIN_TIME = 1000; // 1 second
    private final long MIN_DIST = 5; // distance in meter (5 meters)
    EditText number;
    EditText message;
    Button send;
    StringBuffer stringBuffer;
    MessageService messageService1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*
        // check for GPS permission
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1000);
        }
         */

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                loc =location;
                Lattitude = String.valueOf(loc.getLatitude());
                Longitude = String.valueOf(loc.getLongitude());

                latLng = new LatLng(location.getLatitude(),location.getLongitude());
               // intent.putExtra("location",String.valueOf(location));
               // messageService1 = new MessageService(location.toString(),MainActivity.this);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }
        };
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        // check permission is required  here, but for now try catch is used for catching the security exception
        try{
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,MIN_TIME,0,locationListener);
        }catch (SecurityException e){
            e.printStackTrace();
        }
        //locationListener = new LocationListener() {
         //   @Override
         //   public void onLocationChanged(@NonNull Location location) {
               // cLocation = new CLocation(location);
                // latLng = cLocation.getLatLng();
//                cLocation.getLatLng().toString();
                /*
                stringBuffer.append("http://maps.google.com?q=");
                stringBuffer.append(String.valueOf(location.getLatitude()));
                stringBuffer.append(",");
                stringBuffer.append(String.valueOf(location.getLongitude()));
                 */
           //     myLocation = new MessageService(location);
            //    latLng = myLocation.getLatLang();
             //   myLocation.getAltitude();
            //    Lattitude = String.valueOf(latLng.latitude);
             //   Longitude = String.valueOf(latLng.longitude);
             ///   System.out.println(Lattitude);
             //   System.out.println("----------------");
             //   System.out.println(Longitude);
                // latLng = new LatLng(location.getLatitude(),location.getLongitude());
          //  }
       // };
        // check permission is required  here, but for now try catch is used for catching the security exception
        //try{
            // locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,MIN_TIME,MIN_DIST,locationListener);
         //   locationManager.requestLocationUpdates(locationManager.GPS_PROVIDER,MIN_TIME,MIN_DIST,locationListener);
        //}catch (SecurityException e){
         //   e.printStackTrace();
        //}
        Button Buttonemergency = (Button) findViewById(R.id.emer_on);
       Button FirstAid = (Button) findViewById(R.id.first_aid);
       Button HelpMe = (Button) findViewById(R.id.help_button);
       // UserGuide = (FloatingActionButton) findViewById(R.id.user_guide);
        Button Twowheeler = (Button) findViewById(R.id.button_Two_Wheeler);
        Button Settings = (Button) findViewById(R.id.buttonSettings);
        FloatingActionButton userGuide = findViewById(R.id.user_guide);
        Button EditMessage = (Button) findViewById(R.id.buttonEditMessage);
        Button maps = (Button) findViewById(R.id.buttonMap);
        Button emergencyContact = (Button) findViewById(R.id.buttonEmergencyContact);
        //if(checkPermission((Manifest.permission.SEND_SMS))){
          //  Buttonemergency.setEnabled(true);
        //}else{
            // ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.SEND_SMS},SEND_SMS_PERMISSION_REQUEST_CODE);
         //   ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS, Manifest.permission.READ_SMS}, PackageManager.PERMISSION_GRANTED);
        //}
        // locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        /*
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                latLng = new LatLng(location.getLatitude(),location.getLongitude());
                // Lattitude =  Double.toString(location.getLatitude());
                // Longitude = Double.toString(location.getLongitude());
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(@NonNull String provider) {

            }

            @Override
            public void onProviderDisabled(@NonNull String provider) {

            }
        };

        try{
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,MIN_TIME,MIN_DIST,locationListener);
        }catch (SecurityException e){
            e.printStackTrace();
        }
         */
        Buttonemergency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // new MainActivity().onSend();
               // MessageService messageService = new MessageService(LocationManager.GPS_PROVIDER);
                // messageService.sendMessage();
              //  MessageService messageService1 = new MessageService(lo,MainActivity.this);
                try{
                    Intent intent = new Intent(getApplicationContext(),EmergencyOn.class);
                    intent.putExtra("LOCATION",String.valueOf((Location) loc));
                  //  intent.putExtra("LOCATION",loc.toString());
                 //   loc.getLongitude();
                  //  intent.putExtra("LAT",loc.getLatitude());
                  //  intent.putExtra("LONG", loc.getLongitude());
                  //  loc.getLongitude();
                    startActivity(intent);
                   // messageService1.sendMessage();
                  //  MessageService messageService = new MessageService(LocationManager.GPS_PROVIDER);
                  //  messageService.sendMessage();
                   // onSend();
                    // Intent intent = new Intent(getApplicationContext(),EmergencyOn.class);
                    // startActivity(intent);
                    Log.d("ok","over here");
                }catch (Exception e){
                    // Loging the error message or exception
                    e.printStackTrace();
                    Log.d("Error","Error occured");
                    // Toast.makeText(this,"Message Sent!", Toast.LENGTH_LONG).show();
                }

            }
        });

        Twowheeler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),TwoWheelerMode.class);
                startActivity(intent);
            }
        });
        FirstAid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),FirstAid.class);
                startActivity(intent);
            }
        });

        Settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),SettingsApp.class);
                startActivity(intent);
            }
        });
        userGuide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),UserGuide.class);
                startActivity(intent);
            }
        });
        maps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),MapActivity.class);
                startActivity(intent);
            }
        });
        EditMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),MessageEditor.class);
                startActivity(intent);
            }
        });
        HelpMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Instruction.class);
                startActivity(intent);
            }
        });
        emergencyContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), EmergencyContact.class);
                startActivity(intent);
            }
        });
    }
    public void onSend(){
        // String phonenumber = number.getText().toString();
        // String smsMessage = message.getText().toString();
       // this.number = 9860903837;
       // this.message = "This is the message";
        /*
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                latLng = new LatLng(location.getLatitude(),location.getLongitude());
            }
        };
        // check permission is required  here, but for now try catch is used for catching the security exception
        try{
           // locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,MIN_TIME,MIN_DIST,locationListener);
            locationManager.requestLocationUpdates(locationManager.GPS_PROVIDER,MIN_TIME,MIN_DIST,locationListener);
        }catch (SecurityException e){
            e.printStackTrace();
        }
         */
       // cLocation = new CLocation(lo);

       //  cLocation = new CLocation(LocationManager.GPS_PROVIDER);
       // System.out.println(cLocation.getLatLng());
      //  MyLocation myLocation = new MyLocation();


      //  String phonenumber = "9860903837";
     //   StringBuffer smsMessage = new StringBuffer();
    //    smsMessage.append("http://maps.google.com?q=");
    //    smsMessage.append(Lattitude);
     //   smsMessage.append(",");
     //   smsMessage.append(Longitude);
        // String smsMessage = "Help me! my current location:- Longitude:"+Longitude+"Lattitude:-"+Lattitude+"";
    //    Log.d("OnSend","Inside on send");
    //    String message = Uri.parse(String.valueOf(smsMessage)).toString();






        // String uri = "http://maps.google.com/maps?saddr=" +Lattitude+","+Longitude;
        // String uri = "http://maps.google.com/maps?saddr=" +"-31"+","+"151";
        /*------------------------------------Debug: lattitide and longitude being intialized with null values */
        /*
        if( Lattitude != null && Longitude != null){
            String uri = "http://maps.google.com/maps?q=" +Lattitude+","+Longitude;
        }else{
            Toast.makeText(this,"Location not send",Toast.LENGTH_SHORT).show();
        }
         */
       //  String uri = "Location Not Shared!";
      // String uri = "http://maps.google.com/maps?q=" +"27.678282"+","+"85.342269";
      //  String uri = "http://maps.google.com/maps?q=" +Double.toString(latLng.latitude) +","+Double.toString(latLng.longitude);
     //  String uri = "http://maps.google.com/maps?q="+ Lattitude+ "," + Longitude;
      //  StringBuffer smsBody = new StringBuffer();
      //   smsBody.append(Uri.parse(smsMessage.toString()));

        /*
        if(phonenumber == null || phonenumber.length() ==0 ||
                smsMessage == null || smsMessage.length() == 0){
            return;
        }
         */




      //  SmsManager smsManager = SmsManager.getDefault();
       // smsManager.sendTextMessage(phonenumber,null,Uri.parse(smsMessage.toString()).toString(),null,null);
     ///   Log.d("message status","send");
     //   if(stringBuffer != null){
     //       smsManager.sendTextMessage(phonenumber,null,message,null,null);
     //   }else {
//            Toast.makeText(MainActivity.this,"Message not sent",Toast.LENGTH_LONG);

     //   }

        /*
        if(checkPermission(Manifest.permission.SEND_SMS)){
            // If permission is granted create the SMS manager object;
            Log.d("Message Sender","Inside Message Sender");
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phonenumber,smsMessage,null,null,null);
            Toast.makeText(this,"Message Sent!", Toast.LENGTH_LONG).show();
        }
         */
    }
    public boolean checkPermission(String permission){
        int check = ContextCompat.checkSelfPermission(this, permission);
        return (check == PackageManager.PERMISSION_GRANTED);
    }
    /*
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        // super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 1000){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this,"GPS permission enabled",Toast.LENGTH_SHORT);
            }else {
                finish();
            }
        }
    }
     */
}