package com.github.ilyamurzinov.dailyselfie;

import android.os.Environment;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Ilya Murzinov [murz42@gmail.com]
 */
public class ImagesDAO {
    private static final String FOLDER_NAME = "selfies";

    public static File imagesDir;

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

    public List<File> getImages() {
        return getListFiles(imagesDir);
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
