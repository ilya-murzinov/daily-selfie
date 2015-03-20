package com.github.ilyamurzinov.dailyselfie.reciever;

import android.content.Context;
import android.content.Intent;

import com.github.ilyamurzinov.dailyselfie.component.AlarmHelper;

import javax.inject.Inject;

/**
 * @author Ilya Murzinov
 */
public class AlarmRegisteringBootReceiver extends BaseReceiver {

    @Inject
    public AlarmHelper alarmHelper;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
            alarmHelper.setAlarm();
        }
    }
}
