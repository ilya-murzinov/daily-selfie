package com.github.ilyamurzinov.dailyselfie;

import android.content.Context;

import dagger.Module;
import dagger.Provides;

/**
 * @author Ilya Murzinov [murz42@gmail.com]
 */
@Module(injects = {
        AlarmRegisteringBootReceiver.class,
        MainActivity.class,
        GalleryFragment.class,
        SelfieFragment.class
}, library = true)
public class DailySelfieModule {
    private final Context context;
    private final ImagesDAO imagesDAO = new ImagesDAO();
    private final BitmapHelper bitmapHelper = new BitmapHelper();
    private final AlarmHelper alarmHelper;
    private final SelfieTaker selfieTaker = new SelfieTaker();

    public DailySelfieModule(Context context) {
        this.context = context;
        alarmHelper = new AlarmHelper(context);
    }

    @Provides
    public Context getContext() {
        return context;
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
    public SelfieTaker getSelfieTaker() {
        return selfieTaker;
    }
}
