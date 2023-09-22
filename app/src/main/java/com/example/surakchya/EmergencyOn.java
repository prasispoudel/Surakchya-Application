package com.example.surakchya;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.SystemClock;
import android.provider.Settings;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import static android.os.Environment.getExternalStorageDirectory;

public class EmergencyOn extends AppCompatActivity implements SensorEventListener {
    private static final String TAG = "EmergencyOn";
    private static final String LOG_TAG = "AudioRecordTest";
    private static final int REQUEST_RECORD_AUDIO_PERMISSION = 200;
    final int SEND_SMS_PERMISSION_REQUEST_CODE = 1;
    private static String fileName = null;
    //  private RecordButton recordButton = null;
    private MediaRecorder recorder = null;
    MessageService messageService;
    // private PlayButton   playButton = null;
    private MediaPlayer player = null;
    private SensorManager sensorManager;
    Sensor accelerometer;
    MediaRecorder mediaRecorder;
    Sensor gravity;
    // Location location;
    Location location;
    String loc;
    private String Lat;
    private String Long;
    LatLng latLng;
    private LocationManager locationManager;
    private LocationListener locationListener;
    MessageDatabaseHelper messageDatabaseHelper;
    AudioSettingDataBaseHelper audioSettingDataBaseHelper;
    MyDataBaseHelper myDataBaseHelper;
    EmergencyLogDatabaseHelper emergencyLogDatabaseHelper;
    ArrayList<String> contactid,contactname,contactphone,message;
    private int audiolength;
    private String MessageContent;
    private boolean locationReceived = true;
    SmsManager smsManager;
    private String recordFile;
    // Requesting permission to RECORD_AUDIO
    private boolean permissionToRecordAccepted = false;
    private String[] permissions = {Manifest.permission.RECORD_AUDIO};

    @Override
    public void onSensorChanged(SensorEvent event) {
      //  Log.d(TAG, "onSensorChanged: X: " + event.values[0] + "Y: " + event.values[1] + "Z: " + event.values[2]);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency_on);
        locationReceived = false;
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(EmergencyOn.this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        /*
        if (getIntent().hasExtra("LOCATION")) {
            getIntent().getStringExtra("LOCATION");
            loc = getIntent().getStringExtra("LOCATION");
        }
         */
        messageDatabaseHelper = new MessageDatabaseHelper(EmergencyOn.this);
        myDataBaseHelper = new MyDataBaseHelper(EmergencyOn.this);
        audioSettingDataBaseHelper = new AudioSettingDataBaseHelper(this);
      //  emergencyLogDatabaseHelper = new EmergencyLogDatabaseHelper(EmergencyOn.this);
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
        Log.d(TAG, "Starting to fetch contacts and emergency message");
         getInformation();
        Log.d(TAG, "Fetch completed");
        Log.d(TAG, "Fetching User location...");
        EmergencyOn emergencyOn = new EmergencyOn();
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                Lat = String.valueOf(location.getLatitude());
                Long = String.valueOf(location.getLongitude());
                latLng = new LatLng(location.getLatitude(),location.getLongitude());
                messageService = new MessageService(String.valueOf(location.getLatitude()), String.valueOf(location.getLongitude()), message, contactphone, EmergencyOn.this);
                Log.d(TAG, "onLocationChaged: Lattitude: " + messageService.getLat() + " Longitude: " + messageService.getLongitude());
                Log.d(TAG, "Calling the message service..");
                emergencyOn.location = location;
                emergencyOn.sendEmergencyMessage(location,message,contactphone);
                if(locationReceived == false){
                    String contentMsg = null;
                    LatLng loc = new LatLng(location.getLatitude(),location.getLongitude());
                    Log.d(TAG,"LOCATION RECORDED");
                    emergencyLogDatabaseHelper = new EmergencyLogDatabaseHelper(EmergencyOn.this);
                    for(String content: message){
                        contentMsg = content;
                    }
                    for(String contact: contactphone){
                        emergencyLogDatabaseHelper.addEmergencyLog(contact,contentMsg,String.valueOf(loc.latitude),String.valueOf(loc.longitude));
                        Log.d(TAG,"Log saved in Database");
                    }
                }
                locationReceived = true;
                Log.d(TAG, "Call to message services completed");
            }

            @Override
            public void onProviderEnabled(@NonNull String provider) {

            }

            @Override
            public void onProviderDisabled(@NonNull String provider) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }
        };


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.INTERNET, Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 10);
            return;
        } else {
            manageLocation();
        }
        if(ActivityCompat.checkSelfPermission(this,Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[]{Manifest.permission.SEND_SMS},20);
        }

        if(ActivityCompat.checkSelfPermission(this,Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[]{Manifest.permission.RECORD_AUDIO}, 20);
        }


        startRecording();
        new CountDownTimer(60000,1000){

            @Override
            public void onTick(long millisUntilFinished) {
                Log.d(TAG,"Recording session remaining: -" + millisUntilFinished/ 1000);
                //if(millisUntilFinished == 0){
                //    Log.d(TAG,"seconds remaining: -" + millisUntilFinished/ 1000);
               // }
            }

            @Override
            public void onFinish() {
               stopRecording();
                Log.d(TAG,"Finished");
            }
        }.start();

        /*
        new CountDownTimer(this.audiolength,1000){
            @Override
            public void onTick(long millisUntilFinished) {
                Log.d(TAG,"Recording session remaining: -" + millisUntilFinished/ 1000);
            }

            @Override
            public void onFinish() {
                stopRecording();
                Log.d(TAG,"Finished");
            }
        }.start();
         */
       /*
        new  TimerTask(){

            @Override
            public void run() {

            }
        }.run();
        */
        Log.d(TAG,"OKOK");
        // SystemClock.sleep(5000);

    //    stopRecording();
        //  Log.e(TAG,"OK");

        // messageService.getLat();
        /*
        if (checkPermission((Manifest.permission.SEND_SMS))) {
            //  Buttonemergency.setEnabled(true);
            messageService.getLat();
            messageService.getLongitude();
            StringBuffer messageBody = new StringBuffer();
           // messageBody.append(this.message);
           // messageBody.append(" ");
            messageBody.append("http://maps.google.com?q=");
            // messageBody.append(getLatLng().latitude);
            messageBody.append(messageService.getLat());
            messageBody.append(",");
            messageBody.append(messageService.getLongitude());
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage("9860903837",null, Uri.parse(messageBody.toString()).toString(),null,null);
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, SEND_SMS_PERMISSION_REQUEST_CODE);
        }
         */

        //   ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS, Manifest.permission.READ_SMS}, PackageManager.PERMISSION_GRANTED);
        //}
        //  messageService = new MessageService(new LatLng(Double.valueOf(Lat),Double.valueOf(Long)));
        //  messageService = new MessageService(Lat,Long);
        //messageService.getLatLng();
        // messageService.getLat();
//        messageService.sendMessage();
        // messageService = new MessageService();
        //  Log.d("OK",messageService.getLatLng().toString());
        // messageService.getLatLng();
        // messageService.sendMessage();
        // messageService = new MessageService()
        // CLocation  cLocation = new CLocation(LocationManager.GPS_PROVIDER);
        // cLocation.getLatLng();
        // messageService = new MessageService(cLocation.getLocation(),EmergencyOn.this);
//       new MessageService(latLng).sendMessage();
//        messageService.sendMessage();
        /*
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        gravity = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);
        sensorManager.registerListener(EmergencyOn.this,gravity, (int) SensorManager.SENSOR_DELAY_NORMAL);
         */
        /*
         */
        // Audio
        // Record to the external cache directory for visibility
      //  fileName = getExternalCacheDir().getAbsolutePath();
     //  fileName += "/audiorecordtest.3gp";

      //  ActivityCompat.requestPermissions(this, permissions, REQUEST_RECORD_AUDIO_PERMISSION);

        LinearLayout ll = new LinearLayout(this);
        /*
        RecordButton recordButton = new RecordButton(this);
        ll.addView(recordButton,
                new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        0));
        playButton = new PlayButton(this);
        ll.addView(playButton,
                new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        0));
        setContentView(ll);
         */


       // SmsManager smsManager = SmsManager.getDefault();
       // smsManager.sendTextMessage("9860903837",null,Uri.parse(stringBuffer.toString()).toString(),null,null);
       // Log.d(TAG,"Message sent");
        /*
        if(checkPermission(Manifest.permission.SEND_SMS)){
            SmsManager smsManager1 = SmsManager.getDefault();
            try{
                smsManager1.sendTextMessage("9841401956",null,Uri.parse(stringBuffer.toString()).toString(),null,null);
            }catch (SecurityException e){
                e.printStackTrace();
            }
        }
         */
        /*
        for(String content: message){
            this.MessageContent = content;
        }
        this.locationReceived = false;
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(this.MessageContent);
        stringBuffer.append(" ");
        stringBuffer.append("http://maps.googl.com?q=");
        // stringBuffer.append(this.latLng.latitude);
        // stringBuffer.append(location.getLatitude());
        Log.d(TAG,"Location:- "+Lat);
        stringBuffer.append(loc);
        stringBuffer.append(",");
        stringBuffer.append(location.getLongitude());
         */
        smsManager = SmsManager.getDefault();
       // smsManager.sendTextMessage("9841401956",null,"This is the message.",null,null);








    }
    // String  recordPath = Environment.getExternalStorageDirectory().toString()+"/surakchya.3gp";
    void startRecording() {
       // String  recordPath = getExternalStorageDirectory().toString()+"/surakchya.3gp";
        String recordPath = this.getExternalFilesDir("/").getAbsolutePath();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_dd_hh_mm_ss", Locale.CANADA);
        Date now = new Date();
        recordFile = "Recording_"+formatter.format(now) + ".3gp";
       // String recordPath =  this.getExternalFilesDir("/").getAbsolutePath();
       mediaRecorder = new MediaRecorder();
      //  mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
    //  mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        //  Log.d(TAG,"Recording Started");
      mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);
        //  mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
       // mediaRecorder.setOutputFile(recordPath+ "/"+ recordPath);
       // mediaRecorder.setOutputFile(fileName);
        mediaRecorder.setOutputFile(recordPath + "/"+ recordFile);
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
      //  mediaRecorder.prepare();
      //  mediaRecorder.start();
        try {
            mediaRecorder.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mediaRecorder.start();
        Long start = new Date().getTime();
        // SystemClock.sleep(50000);
        Log.d(TAG,"Time:- "+ start);
        // stopRecording();
        // new TimerTask().
      //  stopRecording();

    }
    void stopRecording(){
        mediaRecorder.stop();
        mediaRecorder.release();
        mediaRecorder = null;
    }

    void sendEmergencyMessage(Location location, ArrayList<String> message,ArrayList<String> contactphone){
        for(String content: message){
            this.MessageContent = content;
        }
        if(location != null){
           // this.locationReceived = false;
            String lat= String.valueOf(location.toString().substring(12,22));
            String lon = String.valueOf(location.toString().substring(23,33));
            Log.d(TAG,"Location:- "+location);
            LatLng cordinates = new LatLng(location.getLatitude(),location.getLongitude());
           // String finalmessage = this.MessageContent+" http://maps.google.com?q="+lat+","+lon;
           // Uri.parse(finalmessage);
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append(this.MessageContent);
            stringBuffer.append(" ");
            stringBuffer.append("http://maps.google.com?q=");
           // stringBuffer.append("http://maps.google.com?q="+"37.421998333333335"+","+"-122.08400000000002");
           // stringBuffer.append(this.latLng.latitude);
         //   stringBuffer.append("27.523545845");
            //stringBuffer.append(lat.toCharArray());
           stringBuffer.append(cordinates.latitude);
           // stringBuffer.append(String.valueOf(cordinates.latitude));
            stringBuffer.append(",");
           // stringBuffer.append("85.32750656828284");
            stringBuffer.append(cordinates.longitude);
             //stringBuffer.append(lon.toCharArray());
            // stringBuffer.append(location.getLongitude());
           // stringBuffer.append(this.latLng.longitude);
           // Log.d(TAG,"String buffer: "+stringBuffer);
            String finalmessage = Uri.parse(stringBuffer.toString()).toString();
           // Log.d(TAG,"Parced String: "+finalmessage);
            for(String contact: contactphone){
                Log.d(TAG,"Contact:  "+contact);
                SmsManager smsManager = SmsManager.getDefault();
               // smsManager.sendTextMessage(contact,null,Uri.parse(stringBuffer.toString()).toString(),null,null);
                smsManager.sendTextMessage(contact,null,finalmessage,null,null);
                // emergencyLogDatabaseHelper = new EmergencyLogDatabaseHelper(EmergencyOn.this);
             // new EmergencyLogDatabaseHelper(EmergencyOn.this).addEmergencyLog(contact,this.MessageContent,String.valueOf(cordinates.latitude),String.valueOf(cordinates.longitude));
             // emergencyLogDatabaseHelper.addEmergencyLog(contact,this.MessageContent,String.valueOf(cordinates.latitude),String.valueOf(cordinates.longitude));
             //   emergencyLogDatabaseHelper = new EmergencyLogDatabaseHelper(EmergencyOn.this);
          //  emergencyLogDatabaseHelper.getAllEmergencyLog();
           //   new EmergencyLogDatabaseHelper(EmergencyOn.this).addEmergencyLog("9860903837","Emergency Message","25.23232","-121.65131");
             //   emergencyLogDatabaseHelper.addEmergencyLog("9860903837","Emergency Message","25.23232","-121.65131");
                Log.d(TAG,"Message sent to: "+contact);
                this.locationReceived = false;
            }
        }else{
            // locationListener.onLocationChanged(this.location);
            //this.locationReceived = true;
           // try {
           //     locationManager.requestLocationUpdates("gps", 0, 0, locationListener);
                // locationListener.onLocationChanged();
           // } catch (SecurityException e) {
           //     e.printStackTrace();
           // }
            Log.d(TAG,"LAT AND LONG ARE NULL");

        }
        this.locationReceived = true;
    }


    void getInformation(){
        Cursor cursorMessage = messageDatabaseHelper.getEmergencyMessage();
        message = new ArrayList<String>();
        contactid = new ArrayList<String>();
        contactname = new ArrayList<String>();
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

        Cursor cursor1 = audioSettingDataBaseHelper.getAudioSetting();
        if(cursor1.getCount() == 0 ){
            Toast.makeText(this,this.getString(R.string.no_data_found),Toast.LENGTH_SHORT);
        }else {
            while (cursor1.moveToNext()){
                this.audiolength = Integer.valueOf(cursor1.getString(1));
            }
        }

    }












    /*
    private void onRecord(boolean start) {
        if (start) {
            startRecording();
        } else {
            stopRecording();
        }
    }

    private void onPlay(boolean start) {
        if (start) {
            startPlaying();
        } else {
            stopPlaying();
        }
    }
    private void startPlaying() {
        player = new MediaPlayer();
        try {
            player.setDataSource(fileName);
            player.prepare();
            player.start();
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
        }
    }
    @Override
    public void onStop() {
        super.onStop();
        if (recorder != null) {
            recorder.release();
            recorder = null;
        }

        if (player != null) {
            player.release();
            player = null;
        }
    }
    private void stopPlaying() {
        player.release();
        player = null;
    }

    private void startRecording() {
        recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        recorder.setOutputFile(fileName);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            recorder.prepare();
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
        }

        recorder.start();
    }

    private void stopRecording() {
        recorder.stop();
        recorder.release();
        recorder = null;
    }

    class RecordButton extends androidx.appcompat.widget.AppCompatButton {
        boolean mStartRecording = true;

        OnClickListener clicker = new OnClickListener() {
            public void onClick(View v) {
                onRecord(mStartRecording);
                if (mStartRecording) {
                    setText("Stop recording");
                } else {
                    setText("Start recording");
                }
                mStartRecording = !mStartRecording;
            }
        };

        public RecordButton(Context ctx) {
            super(ctx);
            setText("Start recording");
            setOnClickListener(clicker);
        }
    }

    class PlayButton extends androidx.appcompat.widget.AppCompatButton {
        boolean mStartPlaying = true;

        View.OnClickListener clicker = new View.OnClickListener() {
            public void onClick(View v) {
                onPlay(mStartPlaying);
                if (mStartPlaying) {
                    setText("Stop playing");
                } else {
                    setText("Start playing");
                }
                mStartPlaying = !mStartPlaying;
            }
        };

        public PlayButton(Context ctx) {
            super(ctx);
            setText("Start playing");
            setOnClickListener(clicker);
        }
    }
    */

    /*
    public boolean checkPermission(String permission) {
        int check = ContextCompat.checkSelfPermission(this, permission);
        return (check == PackageManager.PERMISSION_GRANTED);
    }
     */

    @Override
    public void onRequestPermissionsResult ( int requestCode, @NonNull String[] permissions,
                                             @NonNull int[] grantResults){
        switch (requestCode) {
            case 10:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    manageLocation();
                    startRecording();
                }
                break;
            case 20:
                if(grantResults.length>0 && grantResults[1] == PackageManager.PERMISSION_GRANTED){
                    getInformation();
                }
        }
    }

    private void manageLocation () {
        try {
            locationManager.requestLocationUpdates("gps", 0, 100, locationListener);
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

}