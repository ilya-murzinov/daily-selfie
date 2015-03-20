package com.github.ilyamurzinov.dailyselfie.reciever;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.github.ilyamurzinov.dailyselfie.DailySelfieApplication;

/**
 * @author Ilya Murzinov
 */
public abstract class BaseReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        DailySelfieApplication.inject(this);
    }
}
