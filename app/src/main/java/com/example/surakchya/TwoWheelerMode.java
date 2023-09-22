
package com.example.surakchya;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.util.DataUtils;
import com.google.android.gms.maps.model.LatLng;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Formatter;
import java.util.Locale;
import java.util.Random;
import java.util.TimerTask;




public class TwoWheelerMode extends AppCompatActivity implements LocationListener, SensorEventListener, AlertDialog.AlertDiaglogListner {

    // adding switch compat for the unit conversion switch
    SwitchCompat switch_unit;
    TextView tv_speed;
    private static final String TAG = "TwoWheelerMode";
    TwoWheelerMode twoWheelerMode;
    // Adding instance of the accelerometer and gyroscope sensor into the TwoWheelerMode Class
      private Accelerometer accelerometer2;
    Sensor accelerometer;
    private Gyroscope gyroscope;
    MessageDatabaseHelper messageDatabaseHelper;
    MyDataBaseHelper myDataBaseHelper;
    EmergencyLogDatabaseHelper emergencyLogDatabaseHelper;
    private LocationManager locationManager;
    private LocationListener locationListener;
    SensorManager sensorManager;
    public boolean count = true;
    // float speed;
    double lat_old = 0.0;
    double lon_old = 0.0;
    double lat_new;
    double lon_new;
    double time = 10;
    double speed = 0.0;
    static final Double EARTH_RADIUS = 6371.00;
    static final float ALPHA = 0.25f;
    protected float[] gravSensorVals;
    private float grav[] = new float[3];
    private float RTmp[] = new float[9];
    float[] gravity = new float[3];
    float[] linear_acceleration = new float[3];
    private boolean Gsensor = false;
    private boolean Pressed = false;
    LatLng latLng;
    ArrayList<String> message, contactphone;
    String messageContent;
    Double start;
    Double end;
    double vx,vy,vz;
    double finalvelocity;
    boolean sendMessage = true;
    private boolean locationReceived = true;
    //    Timestamp timestamp = Timestamp.valueOf(String.format("%04d-%02d-%02d 00:00:00"));
    Timestamp timestamp = new Timestamp(System.currentTimeMillis());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two_wheeler_mode);
        messageDatabaseHelper = new MessageDatabaseHelper(TwoWheelerMode.this);
        myDataBaseHelper = new MyDataBaseHelper(TwoWheelerMode.this);
        emergencyLogDatabaseHelper = new EmergencyLogDatabaseHelper(TwoWheelerMode.this);
        switch_unit = findViewById(R.id.switch_unit);
        tv_speed = findViewById(R.id.tv_speed);

        // check for GPS permission
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1000);
        } else {
        }
        getInformation();
        twoWheelerMode = new TwoWheelerMode();
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onLocationChanged(@NonNull Location location) {
                lat_new = location.getLatitude();
                lon_new = location.getLongitude();
                twoWheelerMode.latLng = new LatLng(location.getLatitude(), location.getLongitude());
                Log.d(TAG, "Location: " + location.getLatitude() + "," + location.getLongitude());
            }
        };
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 5, locationListener);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(TwoWheelerMode.this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        gyroscope = new Gyroscope(this);

        gyroscope.setListener(new Gyroscope.Listener() {
            @Override
            public void onRotation(float rx, float ry, float rz) {
                if (rz > 1.0f) {
                    System.out.println("Up Right");
                    getWindow().getDecorView().setBackgroundColor(Color.GREEN);
                } else if (rz < -1.0f) {
                    System.out.println("Down Faced");
                    getWindow().getDecorView().setBackgroundColor(Color.YELLOW);
                }
            }
        });


    }

    public void openDialog() {
        AlertDialog alertDialog = new AlertDialog();
        alertDialog.show(getSupportFragmentManager(), "Alert Dialog");

        new CountDownTimer(20000,1000){
            @Override
            public void onTick(long millisUntilFinished) {}

            @Override
            public void onFinish() {
                if(twoWheelerMode.sendMessage = true){
                    SendAlertMessage();
                }else {
                    Toast.makeText(TwoWheelerMode.this,"Emergency message was cancelled",Toast.LENGTH_SHORT);
                }

            }
        }.start();

    }

    @Override
    public void onYesClicked() {
        Toast.makeText(TwoWheelerMode.this, "Yes Button pressed", Toast.LENGTH_SHORT);
        SendAlertMessage();
    }

    @Override
    public void onNoClicked() {
          twoWheelerMode.sendMessage = false;
    }


    @Override
    public void onLocationChanged(@NonNull Location location) {

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


    @SuppressLint("MissingPermission")
    private void doStuff() {
        // type casting from object to Location Manager type(object casting to object of location Manager class which extends java.lang.Object)
        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        // null check for object of location manager
        if (locationManager != null) {
            // calling  requestLocationUpdates() method using the object of Location Manager class to get current location of the android device
            // user permission of required before accessing the location, so check of permission is mandatory
            // for now the possible Security Exception has been supressed.
            /*
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                 //
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
             */
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 100, locationListener);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        // super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1000) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                doStuff();
                Log.d(TAG, "OK");
            } else {
                finish();
            }
        }
    }

    private boolean useMetricUnits() {
        return switch_unit.isChecked();
    }

    public void SendAlertMessage() {
        if (twoWheelerMode.latLng != null) {
            SmsManager smsManager = SmsManager.getDefault();
            StringBuffer stringBuffer = new StringBuffer();
            this.messageContent = message.get(1);
            stringBuffer.append(this.messageContent);
            stringBuffer.append(" ");
            stringBuffer.append("http://maps.google.com?q=");
            stringBuffer.append(twoWheelerMode.latLng.latitude);
            stringBuffer.append(",");
            stringBuffer.append(twoWheelerMode.latLng.longitude);
            String finalmessage = Uri.parse(stringBuffer.toString()).toString();
            for (String contact : contactphone) {
                smsManager.sendTextMessage(contact, null, finalmessage, null, null);
                if(String.valueOf(this.latLng.latitude) != null && String.valueOf(this.latLng.latitude) != null){
                    emergencyLogDatabaseHelper.addEmergencyLog(contact,finalmessage,String.valueOf(this.latLng.latitude),String.valueOf(this.latLng.longitude));
                }else {
                    Toast.makeText(this, "No Location", Toast.LENGTH_SHORT);
                }

            }
        } else {
            Toast.makeText(this, "No Location", Toast.LENGTH_SHORT);
        }

    }




     /*
    public  void Start(SensorEvent event){
        this.start =  Math.atan(event.values[0] / (Math.sqrt((event.values[1] * event.values[1]) + (event.values[2] * event.values[2]))));
    }
    public void End(SensorEvent event){
        this.end =   Math.atan(event.values[0] / (Math.sqrt((event.values[1] * event.values[1]) + (event.values[2] * event.values[2]))));

        Log.d(TAG,"Change in tilt angle: - "+ String.valueOf(this.start - this.end));
    }
      */

    public void getInformation(){
        Cursor cursorMessage = messageDatabaseHelper.getEmergencyMessage();
        message = new ArrayList<String>();
        contactphone = new ArrayList<String>();
        if(cursorMessage.getCount() == 0){
            Toast.makeText(this,this.getString(R.string.no_data_found),Toast.LENGTH_SHORT);
        }else{
            while (cursorMessage.moveToNext()){
                message.add(cursorMessage.getString(1));
                Log.d(TAG, "message: " + message);
            }
        }

        Cursor cursor = myDataBaseHelper.getAllEmeregncyContacts();

        if(cursor.getCount() == 0){
            Toast.makeText(this,this.getString(R.string.no_data_found),Toast.LENGTH_SHORT);
        }else{
            while (cursor.moveToNext()){
                contactphone.add(cursor.getString(2));
            }
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        // alpha is calculated as t / (t + dT)
        // with t, the low-pass filter's time-constant
        // and dT, the event delivery rate
        final float alpha = 0.8f;

        // Isolate the force of gravity with the low-pass filter.
        gravity[0] = alpha * gravity[0] + (1 - alpha) * event.values[0];
        gravity[1] = alpha * gravity[1] + (1 - alpha) * event.values[1];
        gravity[2] = alpha * gravity[2] + (1 - alpha) * event.values[2];

        // Remove the gravity contribution with the high-pass filter.
        linear_acceleration[0] = event.values[0] - gravity[0];
        linear_acceleration[1] = event.values[1] - gravity[1];
        linear_acceleration[2] = event.values[2] - gravity[2];

        //   Log.d(TAG,"Tilt: "+ Math.atan(linear_acceleration[0]/(Math.sqrt((linear_acceleration[1]*linear_acceleration[1])+(linear_acceleration[2]*linear_acceleration[2])))));
        if (linear_acceleration[0] > 1.0f) {
            getWindow().getDecorView().setBackgroundColor(Color.RED);
        } else if (linear_acceleration[1] < -1.0f) {
            getWindow().getDecorView().setBackgroundColor(Color.BLUE);
        }
        double actualAcceleration = Math.sqrt((linear_acceleration[0] * linear_acceleration[0]) + (linear_acceleration[1] * linear_acceleration[1]) + (linear_acceleration[2] * linear_acceleration[2]));
        double gForce = actualAcceleration / 9.81;
      //  tv_speed.setText(String.valueOf(gForce));
        Log.d(TAG,"G Force: "+gForce);

        new CountDownTimer(5000,1000){
            @Override
            public void onTick(long millisUntilFinished) {
              //  Log.d(TAG, "Time ticking..." + millisUntilFinished / 1000);
            }

            @Override
            public void onFinish() {
                double change = actualAcceleration - Math.sqrt((linear_acceleration[0] * linear_acceleration[0]) + (linear_acceleration[1] * linear_acceleration[1]) + (linear_acceleration[2] * linear_acceleration[2]));
                finalvelocity = change/5;
                // double finalvelocity = change / 5;
               // Log.d(TAG, "Change in Velocity:- " + finalvelocity);
                tv_speed.setText(String.valueOf(finalvelocity));

                // ((this.finalvelocity * 1000)/(60*60))
            }
        }.start();

        if(gForce > 5 && this.finalvelocity > 2){

            Toast.makeText(this,"Accident Detected", Toast.LENGTH_SHORT);
            Log.d(TAG,"Accident Detected due to G-force: "+gForce+" and final velocity "+this.finalvelocity);
            openDialog();
            // SendAlertMessage();
        }
    }
    


    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}


