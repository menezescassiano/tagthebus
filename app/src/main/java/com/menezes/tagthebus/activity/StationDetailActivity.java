package com.menezes.tagthebus.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.menezes.tagthebus.R;
import com.menezes.tagthebus.camera.CameraActivity;

import java.io.File;

import butterknife.ButterKnife;
import butterknife.InjectView;

import static com.menezes.tagthebus.utils.Constants.FOLDER_NAME;

public class StationDetailActivity extends AppCompatActivity {

    private static int PHOTO_RESULT = 1;
    private static String STATION_NAME = "STATION_NAME";
    private static String STATION_ID = "STATION_ID";
    private String stationId;
    private String stationName;

    @InjectView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_station_detail);
        ButterKnife.inject(this);
        stationName = getIntent().getStringExtra(STATION_NAME);
        stationId = getIntent().getStringExtra(STATION_ID);
        toolbar.setTitle(stationName);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.camera_button);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StationDetailActivity.this, CameraActivity.class);
                intent.putExtra(STATION_ID, stationId);
                intent.putExtra(STATION_NAME, stationName);
                startActivityForResult(intent, PHOTO_RESULT);
            }
        });
        File appFolder = new File(FOLDER_NAME);
        findPhotos(appFolder);

    }

    public void findPhotos(File dir) {
        if (dir.exists()) {
            File[] files = dir.listFiles();
            for (int i = 0; i < files.length; ++i) {
                File file = files[i];
                if (file.isDirectory()) {
                    findPhotos(file);
                } else if (file.getName().contains(stationName)) {
                    getThumbnail(file.getPath());
                    Log.d("FOUND_FILE::", file.getName());

                }
            }
        }
    }

    private void getThumbnail(String pathName) {
        final int THUMBSIZE = 256;

        Bitmap thumbnail = ThumbnailUtils.
                extractThumbnail(BitmapFactory.decodeFile(pathName), THUMBSIZE, THUMBSIZE);
        Log.d("THUMBNAIL_PATH::", thumbnail.toString());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PHOTO_RESULT && resultCode == RESULT_OK) {

        }
    }

}
