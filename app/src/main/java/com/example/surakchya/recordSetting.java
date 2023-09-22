package com.example.surakchya;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class recordSetting extends AppCompatActivity {
     SeekBar seekBar;
     AudioSettingDataBaseHelper audioSettingDataBaseHelper;
     Button setButton;
     TextView textView;
     int recordvalue;
     int previousValue;
     static final String TAG = "Audio Setting";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_setting);
        audioSettingDataBaseHelper = new AudioSettingDataBaseHelper(this);
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        textView = (TextView) findViewById(R.id.audioTxt);
        setButton = (Button) findViewById(R.id.setbutton);
        load();
        if(audioSettingDataBaseHelper.getAudioSetting().getCount() == 0){
            audioSettingDataBaseHelper.addAudioSetting(String.valueOf(2));
        }
        seekBar.setProgress(this.previousValue);
        textView.setText("Record Time: "+ this.previousValue + " minutes");

       // seekBar.setProgress(20);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                  textView.setText("Record time: "+ progress + " minutes");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
              recordvalue = seekBar.getProgress();
                Log.d(TAG,"Final Record value"+ recordvalue);
            }
        });
        setButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                audioSettingDataBaseHelper.updateAudioSetting(String.valueOf(1),String.valueOf(recordvalue));
            }
        });
    }


    void load(){
        Cursor cursor = audioSettingDataBaseHelper.getAudioSetting();
        if(cursor.getCount() == 0){
            Toast.makeText(this,getString(R.string.no_data_found),Toast.LENGTH_SHORT);
        }else{
            while (cursor.moveToNext()){
                this.previousValue =Integer.valueOf(cursor.getString(1));
            }
        }
    }
}