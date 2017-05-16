package com.menezes.tagthebus.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;

import static com.menezes.tagthebus.utils.Constants.FOLDER_NAME;
import static com.menezes.tagthebus.utils.Constants.THUMBSIZE;

/**
 * Created by cassiano.menezes on 16/05/2017.
 */

public class FileUtil {

    public static void deleteSelectedFile(String fileName) {
        String filePath = FOLDER_NAME + fileName;
        File file = new File(filePath);
        file.delete();
    }

    public static ArrayList<String> getDevicePhotos(File dir, String stationName) {
        ArrayList<String> photosArray = new ArrayList<>();
        if (dir.exists()) {
            File[] files = dir.listFiles();
            for (int i = 0; i < files.length; ++i) {
                File file = files[i];
                if (file.isDirectory()) {
                    getDevicePhotos(file, stationName);
                } else if (file.getName().contains(stationName)) {
                    photosArray.add(file.getName());
                    Log.d("FOUND_FILE::", file.getName());

                }
            }
        }
        return photosArray;

    }

    public static Bitmap getThumbnail (String filePath) {
        return ThumbnailUtils.extractThumbnail(BitmapFactory.decodeFile(filePath), THUMBSIZE, THUMBSIZE);
    }




}
