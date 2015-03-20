package com.github.ilyamurzinov.dailyselfie.component;

import android.app.NotificationManager;
import android.content.Context;

import com.github.ilyamurzinov.dailyselfie.activity.MainActivity;
import com.github.ilyamurzinov.dailyselfie.fragment.GalleryFragment;
import com.github.ilyamurzinov.dailyselfie.fragment.SelfieFragment;
import com.github.ilyamurzinov.dailyselfie.reciever.AlarmRegisteringBootReceiver;
import com.github.ilyamurzinov.dailyselfie.reciever.ShowNotificationReceiver;

import dagger.Module;
import dagger.Provides;

/**
 * @author Ilya Murzinov [murz42@gmail.com]
 */
@Module(injects = {
        AlarmRegisteringBootReceiver.class,
        ShowNotificationReceiver.class,
        MainActivity.class,
        GalleryFragment.class,
        SelfieFragment.class
})
public class DailySelfieModule {
    private final ImagesDAO imagesDAO = new ImagesDAO();
    private final BitmapHelper bitmapHelper = new BitmapHelper();
    private final AlarmHelper alarmHelper;
    private final NotificationManager notificationManager;

    public DailySelfieModule(Context context) {
        alarmHelper = new AlarmHelper(context);
        notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
    }

    @Provides
    public ImagesDAO getImagesDAO() {
        return imagesDAO;
    }

    @Provides
    public BitmapHelper getBitmapHelper() {
        return bitmapHelper;
    }

    @Provides
    public AlarmHelper getAlarmHelper() {
        return alarmHelper;
    }

    @Provides
    public NotificationManager getNotificationManager() {
        return notificationManager;
    }
}
