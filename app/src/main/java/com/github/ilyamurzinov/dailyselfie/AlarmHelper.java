package com.github.ilyamurzinov.dailyselfie;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;

/**
 * @author Ilya Murzinov
 */
public class AlarmHelper {
    private static PendingIntent alarmPendingIntent;

    private final Context context;

    public AlarmHelper(Context context) {
        this.context = context;
        Intent alarmIntent = new Intent(context, ShowNotificationReceiver.class);
        alarmPendingIntent = PendingIntent.getBroadcast(context, 0, alarmIntent, 0);
    }

    public void setAlarm(int minutes) {
        AlarmManager alarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmMgr.cancel(alarmPendingIntent);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.MINUTE, 11);

        alarmMgr.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                minutes * 60 * 1000, alarmPendingIntent);
    }
}
