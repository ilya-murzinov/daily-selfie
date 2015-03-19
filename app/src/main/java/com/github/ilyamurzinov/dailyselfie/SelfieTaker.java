package com.github.ilyamurzinov.dailyselfie;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;

import java.util.Calendar;

/**
 * @author Ilya Murzinov [murz42@gmail.com]
 */
public class SelfieTaker {
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
    public static final String FILE_NAME = "file:///%s/%d%02d%02d_%02d%02d%02d.jpg";

    public void takeSelfie(Activity activity) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Calendar calendar = Calendar.getInstance();
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.parse(String.format(
                FILE_NAME,
                ImagesDAO.imagesDir.getAbsolutePath(),
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DATE),
                calendar.get(Calendar.HOUR),
                calendar.get(Calendar.MINUTE),
                calendar.get(Calendar.SECOND)
        )));
        activity.startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
    }
}
