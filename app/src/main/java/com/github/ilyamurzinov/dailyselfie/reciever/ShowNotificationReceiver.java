package com.github.ilyamurzinov.dailyselfie.reciever;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.github.ilyamurzinov.dailyselfie.R;
import com.github.ilyamurzinov.dailyselfie.activity.MainActivity;

import javax.inject.Inject;

/**
 * @author Ilya Murzinov
 */
public class ShowNotificationReceiver extends BaseReceiver {

    @Inject
    public NotificationManager notificationManager;

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);

        Intent showNotificationIntent = new Intent(context, MainActivity.class);
        PendingIntent showNotificationPendingIntent =
                PendingIntent.getActivity(context, 0, showNotificationIntent, PendingIntent.FLAG_CANCEL_CURRENT);
        Notification notification = new Notification.Builder(context)
                .setContentTitle(context.getString(R.string.notification_title))
                .setContentText(context.getString(R.string.notification_message))
                .setContentIntent(showNotificationPendingIntent)
                .setSmallIcon(android.R.drawable.ic_menu_camera)
                .build();
        notification.flags |= Notification.DEFAULT_LIGHTS | Notification.FLAG_AUTO_CANCEL;

        notificationManager.notify(1, notification);
    }
}
