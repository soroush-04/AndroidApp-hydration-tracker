package com.example.final_project_water_tracker;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import java.util.Calendar;

public class AlarmReceiver extends BroadcastReceiver {
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onReceive(Context context, Intent intent) {
        if (!isTimeValidForNotification(intent)) return;

        CharSequence name = "water app channel";
        String description = "channel for water app";
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel channel = new NotificationChannel("water_app", name, importance);
        channel.setDescription(description);
        // Register the channel with the system; you can't change the importance
        // or other notification behaviors after this
        NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);

        Intent openAppIntent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, openAppIntent, PendingIntent.FLAG_IMMUTABLE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "water_app")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("Drink Water!")
                .setContentText("It's time to drink water")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);
        notificationManager.notify(0, builder.build());
    }

    private boolean isTimeValidForNotification(Intent intent) {
        int wakeUpMin = intent.getIntExtra("WakeUpMin", 0);
        int wakeUpHour = intent.getIntExtra("WakeUpHour", 0);
        int sleepMin = intent.getIntExtra("SleepMin", 0);
        int sleepHour = intent.getIntExtra("SleepHour", 0);

        Calendar calendarNow = Calendar.getInstance();

        Calendar calendarWakeUp = Calendar.getInstance();
        calendarWakeUp.setTimeInMillis(System.currentTimeMillis());
        calendarWakeUp.set(Calendar.HOUR_OF_DAY, wakeUpHour);
        calendarWakeUp.set(Calendar.MINUTE, wakeUpMin);

        Calendar calendarSleep = Calendar.getInstance();
        calendarSleep.setTimeInMillis(System.currentTimeMillis());
        calendarSleep.set(Calendar.HOUR_OF_DAY, sleepHour);
        calendarSleep.set(Calendar.MINUTE, sleepMin);

        return calendarNow.getTimeInMillis() >= calendarWakeUp.getTimeInMillis()
                && calendarNow.getTimeInMillis() <= calendarSleep.getTimeInMillis();
    }
}
