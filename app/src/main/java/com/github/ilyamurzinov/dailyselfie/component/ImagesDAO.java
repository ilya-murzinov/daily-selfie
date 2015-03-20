package com.github.ilyamurzinov.dailyselfie.component;

import android.net.Uri;
import android.os.Environment;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * @author Ilya Murzinov [murz42@gmail.com]
 */
public class ImagesDAO {
    private static final String FOLDER_NAME = "selfies";
    public static final String FILE_NAME = "file:///%s/%d%02d%02d_%02d%02d%02d.jpg";

    private File imagesDir;

    public ImagesDAO() {
        imagesDir = new File(
                Environment.getExternalStorageDirectory() +
                        File.separator +
                        FOLDER_NAME
        );

        if (!imagesDir.exists()) {
            imagesDir.mkdir();
        }
    }

    public Uri generateUriForNewFile() {
        Calendar calendar = Calendar.getInstance();
        String uri = String.format(
                FILE_NAME,
                imagesDir.getAbsolutePath(),
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DATE),
                calendar.get(Calendar.HOUR),
                calendar.get(Calendar.MINUTE),
                calendar.get(Calendar.SECOND)
        );

        return  Uri.parse(uri);
    }

    public List<File> getImages() {
        return getListFiles(imagesDir);
    }

    public void delete(File file) {
        file.delete();
    }

    private static List<File> getListFiles(File parentDir) {
        ArrayList<File> inFiles = new ArrayList<>();
        File[] files = parentDir.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    inFiles.addAll(getListFiles(file));
                } else {
                    inFiles.add(file);
                }
            }
        }
        return inFiles;
    }
}
