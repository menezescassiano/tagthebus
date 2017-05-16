package com.menezes.tagthebus.camera;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.menezes.tagthebus.utils.Constants.FOLDER_NAME;
import static com.menezes.tagthebus.utils.Constants.STATION_NAME;

public class CameraActivity extends AppCompatActivity {

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private Uri uriSavedImage;
    private String timeStamp;
    private String pictureName;
    private String fileFormat = ".png";
    private static final String DATE_FORMAT = "yyyyMMdd_HHmmss";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pictureName = getIntent().getStringExtra(STATION_NAME) + "_";
        dispatchTakePictureIntent();
    }

    @SuppressLint("SimpleDateFormat")
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        timeStamp = new SimpleDateFormat(DATE_FORMAT).format(new Date());
        File imagesFolder = new File(FOLDER_NAME);
        imagesFolder.mkdirs();

        File image = new File(imagesFolder, pictureName + timeStamp + fileFormat);
        uriSavedImage = Uri.fromFile(image);

        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, uriSavedImage);

        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            finish();
        }
    }
}
