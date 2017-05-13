package com.menezes.tagthebus;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import models.NearStation;
import models.StationResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import services.RetrofitClient;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class MainActivity extends AppCompatActivity {

    @InjectView(R.id.stationsList)
    ListView listView;

    @InjectView(R.id.progressBar)
    ProgressBar progressBar;

    public ArrayList<String> stationsNames;
    //private List<ClientInfo> clientInfos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        progressBar.setVisibility(VISIBLE);
        getStationInfo();

    }

    private void getStationInfo() {
        Call<StationResponse> accountCall = new RetrofitClient().getModel().getStationsInfo("41.3985182", "2.1917991");
        accountCall.enqueue(new Callback<StationResponse>() {
            @Override
            public void onResponse(Call<StationResponse> call, Response<StationResponse> response) {
                progressBar.setVisibility(GONE);
                StationResponse stationResponse = response.body();
                setStationsList(stationResponse);
            }

            @Override
            public void onFailure(Call<StationResponse> call, Throwable t) {
                progressBar.setVisibility(GONE);
                Toast.makeText(MainActivity.this, "Connection error. Try again later", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setStationsList(StationResponse stationResponse) {
        stationsNames = getStationsNames(stationResponse);
        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, stationsNames);
        listView.setAdapter(adapter);
    }

    private ArrayList<String> getStationsNames(StationResponse stationResponse) {
        ArrayList<String> stations = new ArrayList<>();
        for (NearStation station : stationResponse.getData().getNearstations()) {
            stations.add(station.getStreetName());
        }
        return stations;
    }

    private static final int FILE_SELECT_CODE = 0;

    private void showFileChooser() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        try {
            startActivityForResult(Intent.createChooser(intent, "Select a File to Upload"), FILE_SELECT_CODE);
        } catch (android.content.ActivityNotFoundException ex) {
            // Potentially direct the user to the Market with a Dialog
            Toast.makeText(this, "Please install a File Manager.",
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case FILE_SELECT_CODE:
                if (resultCode == RESULT_OK) {
                    Uri uri = data.getData();
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                        System.out.print(bitmap.toString());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                 /*   // Get the Uri of the selected file
                    String fpath = data.getDataString();
                    Uri uri = data.getData();
                    String fileName = Uri.parse(uri.toString()).getLastPathSegment();

                    //File file = new File(uri.getPath());
                    try {
                        //FileInputStream fileInputStream = new FileInputStream(file);
                        File file = File.createTempFile(fileName, null, getCacheDir());
                        System.out.print(file.getPath());
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Log.d("TAG", "File Uri: " + uri.toString());
                    // Get the path
                    //String path = FileUtils.getPath(this, uri);
                   // Log.d("TAG", "File Path: " + path);
                    // Get the file instance
                    // File file = new File(path);
                    // Initiate the upload*/
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}
