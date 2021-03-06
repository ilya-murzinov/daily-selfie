package com.github.ilyamurzinov.dailyselfie.component;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.github.ilyamurzinov.dailyselfie.reciever.ShowNotificationReceiver;

import java.util.Calendar;

/**
 * @author Ilya Murzinov
 */
public class AlarmHelper {
    private static boolean isSet;

    private PendingIntent alarmPendingIntent;

    private final Context context;

    public AlarmHelper(Context context) {
        this.context = context;
        Intent alarmIntent = new Intent(context, ShowNotificationReceiver.class);
        alarmPendingIntent = PendingIntent.getBroadcast(context, 0, alarmIntent, 0);
    }

    public void setAlarm() {
        if (!isSet) {
            AlarmManager alarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());
            calendar.set(Calendar.MINUTE, calendar.get(Calendar.MINUTE) + 2);
            calendar.set(Calendar.SECOND, 0);

            alarmMgr.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                    2 * 60 * 1000, alarmPendingIntent);
            isSet = true;
        }
    }
}
