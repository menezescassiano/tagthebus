package com.menezes.tagthebus.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.menezes.tagthebus.R;
import com.menezes.tagthebus.adapter.ListAdapter;
import com.menezes.tagthebus.camera.CameraActivity;
import com.menezes.tagthebus.utils.FileUtil;

import java.io.File;
import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

import static com.menezes.tagthebus.utils.Constants.FILE_PATH;
import static com.menezes.tagthebus.utils.Constants.FOLDER_NAME;
import static com.menezes.tagthebus.utils.Constants.STATION_ID;
import static com.menezes.tagthebus.utils.Constants.STATION_NAME;
import static com.menezes.tagthebus.utils.Constants.THUMBSIZE;
import static com.menezes.tagthebus.utils.FileUtil.*;
import static com.menezes.tagthebus.utils.FileUtil.deleteSelectedFile;
import static com.menezes.tagthebus.utils.FileUtil.getDevicePhotos;

public class StationDetailActivity extends AppCompatActivity {

    private static int PHOTO_RESULT = 1;
    private String stationId;
    private String stationName;
    private ArrayList<Bitmap> thumbnailsArray = new ArrayList<>();
    private ArrayList<String> photosNamesArray = new ArrayList<>();
    private ListAdapter adapter;

    @InjectView(R.id.toolbar)
    Toolbar toolbar;

    @InjectView(R.id.stationPhotosList)
    ListView stationPhotosList;

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
        createList();

    }

    private void createList() {
        adapter = new ListAdapter(StationDetailActivity.this, photosNamesArray, thumbnailsArray);
        stationPhotosList.setAdapter(adapter);
        stationPhotosList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(StationDetailActivity.this, PhotoActivity.class);
                intent.putExtra(FILE_PATH, photosNamesArray.get(position));
                startActivity(intent);
                //Toast.makeText(StationDetailActivity.this, "You Clicked at " + photosNamesArray.get(position++), Toast.LENGTH_SHORT).show();
            }
        });

        stationPhotosList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                showAlertDialog(position);
                return true;
            }
        });
    }

    public void findPhotos(File dir) {
        photosNamesArray = getDevicePhotos(dir, stationName);
        for (String photoName : photosNamesArray) {
            thumbnailsArray.add(getThumbnail(dir + "/" + photoName));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PHOTO_RESULT/* && resultCode == RESULT_OK*/) {
            refreshPhotosList();
        }
    }

    private void refreshPhotosList() {
        File appFolder = new File(FOLDER_NAME);
        photosNamesArray.clear();
        thumbnailsArray.clear();
        adapter.clear();
        adapter.notifyDataSetChanged();
        findPhotos(appFolder);
        createList();
    }

    public void showAlertDialog(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getString(R.string.delete_photo_message))
                .setTitle(getString(R.string.delete_photo_title))
                .setCancelable(false)
                .setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        deleteSelectedFile(photosNamesArray.get(position));
                        refreshPhotosList();
                    }
                }).setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

}
