package com.github.ilyamurzinov.dailyselfie;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * @author Ilya Murzinov
 */
public class AlarmRegisteringBootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
            AlarmHelper helper = new AlarmHelper(context);
            helper.setAlarm();
        }
    }
}
