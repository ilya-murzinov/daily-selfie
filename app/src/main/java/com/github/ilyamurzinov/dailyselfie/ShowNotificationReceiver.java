package com.github.ilyamurzinov.dailyselfie;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * @author Ilya Murzinov
 */
public class ShowNotificationReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Intent showNotificationIntent = new Intent(context, MainActivity.class);
        PendingIntent showNotificationPendingIntent = PendingIntent.getActivity(context, 0, showNotificationIntent, 0);
        Notification notification = new Notification.Builder(context)
                //.setAutoCancel(true)
                .setContentTitle(context.getString(R.string.notification_title))
                .setContentText(context.getString(R.string.notification_message))
                .setContentIntent(showNotificationPendingIntent)
                .setSmallIcon(android.R.drawable.ic_menu_camera)
                .build();
        notification.flags = Notification.DEFAULT_LIGHTS | Notification.FLAG_AUTO_CANCEL;
        notificationManager.notify(1, notification);
    }
}
