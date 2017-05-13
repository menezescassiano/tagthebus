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

public class CameraActivity extends AppCompatActivity {

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private Uri uriSavedImage;
    private String timeStamp;
    private static String FOLDER_NAME = "TagTheBus";
    private String pictureId;
    private String fileFormat = ".png";
    private static String STATION_ID = "STATION_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pictureId = getIntent().getStringExtra(STATION_ID) + "_";
        dispatchTakePictureIntent();
    }

    @SuppressLint("SimpleDateFormat")
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File imagesFolder = new File(Environment.getExternalStorageDirectory(), FOLDER_NAME);
        imagesFolder.mkdirs();

        File image = new File(imagesFolder, pictureId + timeStamp + fileFormat);
        uriSavedImage = Uri.fromFile(image);

        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, uriSavedImage);

        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            getThumbnail();
            /*Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            System.out.println(imageBitmap.toString());
            ImageView.setImageBitmap(imageBitmap);*/
        }
    }

    private void getThumbnail() {
        final int THUMBSIZE = 256;

        Bitmap thumbnail = ThumbnailUtils.
                extractThumbnail(BitmapFactory.decodeFile(uriSavedImage.getPath()), THUMBSIZE, THUMBSIZE);
        System.out.println(thumbnail.toString());
    }
}
