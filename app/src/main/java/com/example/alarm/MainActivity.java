package com.example.alarm;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.Calendar;
import java.util.Calendar;
public class MainActivity extends AppCompatActivity {

    TimePicker alarmTimepicker;
    PendingIntent pendingIntent;
    AlarmManager alarmManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        alarmTimepicker=(TimePicker) findViewById(R.id.timePicker);
        alarmManager =(AlarmManager) getSystemService(ALARM_SERVICE);


    }
    //onToggleClicked() method Implement
    public void OnToggleClicked(View view){
        long time;
        if (((ToggleButton)view).isChecked()) {
            Toast.makeText(MainActivity.this, "ALARM ON", Toast.LENGTH_SHORT).show();
            Calendar calendar =Calendar.getInstance();

            //calender set current time
            calendar.set(Calendar.HOUR_OF_DAY,alarmTimepicker.getHour());
            calendar.set(Calendar.MINUTE, alarmTimepicker.getMinute());

            //using intent i have class AlarmReceiver class which in inherits
            //BroadcastReceiver
            Intent intent =new Intent(this,AlarmReceiver.class);

            //we call broadcast using pendingIntent
            pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_MUTABLE);


            time =(calendar.getTimeInMillis()-(calendar.getTimeInMillis() % 60000));
            if (System.currentTimeMillis() > time) {
                //setting time Am and Pm
                if (Calendar.AM_PM == 0)
                    time = time + (1000 * 60 * 60 * 12);
                else
                    time =time+(1000*60*60*24);

            }
            //Alarm ring continue toggle button is off
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,time,1000,pendingIntent);


        }else {
            alarmManager.cancel(pendingIntent);
            Toast.makeText(MainActivity.this,"ALARM OFF",Toast.LENGTH_SHORT).show();
        }
    }
}