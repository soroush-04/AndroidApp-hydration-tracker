package com.example.final_project_water_tracker;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class NotificationActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    public String frequencyUnit;

    private NumberPicker picker;
    private TimePicker wakeUpPicker;
    private TimePicker sleepPicker;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        if (checkSelfPermission(Manifest.permission.SCHEDULE_EXACT_ALARM) != PackageManager.PERMISSION_GRANTED) {
            Log.d("NotificationActivity", "permission not granted.");
        }

        wakeUpPicker = findViewById(R.id.wakeUpPicker);
        sleepPicker = findViewById(R.id.sleepPicker);
        picker = findViewById(R.id.number_picker);
        Spinner spinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.frequency_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        (findViewById(R.id.set)).setOnClickListener(this::onClickSet);
        (findViewById(R.id.cancel)).setOnClickListener(this::onClickCancel);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        frequencyUnit = (String) parent.getItemAtPosition(position);
        if (frequencyUnit.equals("Seconds") || frequencyUnit.equals("Minutes")) {
            picker.setMinValue(1);
            picker.setMaxValue(60);
        } else {
            picker.setMinValue(1);
            picker.setMaxValue(24);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void onClickSet(View v) {
        Context context = getApplicationContext();
        AlarmManager alarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra("WakeUpMin", wakeUpPicker.getMinute());
        intent.putExtra("WakeUpHour", wakeUpPicker.getHour());
        intent.putExtra("SleepMin", sleepPicker.getMinute());
        intent.putExtra("SleepHour", sleepPicker.getHour());
        PendingIntent alarmIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_IMMUTABLE);
        alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP, Calendar.getInstance().getTimeInMillis(), getRepeatFreqMillis(), alarmIntent);

        Intent intent2 = new Intent(getApplicationContext(), homePage.class);
        startActivity(intent2);
    }

    private void onClickCancel(View v) {
        finish();
    }

    private long getRepeatFreqMillis() {
        long ret = 1000;
        ret *= picker.getValue();
        if (frequencyUnit.equals("Minutes")) {
            ret *= 60;
        } else if (frequencyUnit.equals("Hours")) {
            ret *= 3600;
        }
        return ret;
    }
}