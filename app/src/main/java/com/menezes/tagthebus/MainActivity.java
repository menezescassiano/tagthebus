package com.menezes.tagthebus;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

import com.menezes.tagthebus.camera.CameraActivity;
import com.menezes.tagthebus.models.NearStation;
import com.menezes.tagthebus.models.StationResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.menezes.tagthebus.services.RetrofitClient;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class MainActivity extends AppCompatActivity {

    @InjectView(R.id.stationsList)
    ListView listView;

    @InjectView(R.id.progressBar)
    ProgressBar progressBar;

    public ArrayList<String> stationsNames;
    private static String STATION_ID = "STATION_ID";
    //private List<ClientInfo> clientInfos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        progressBar.setVisibility(VISIBLE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            } else {
                getStationInfo();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getStationInfo();
                } else {
                    finish();
                }
                return;
            }
        }
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

    private void setStationsList(final StationResponse stationResponse) {
        stationsNames = getStationsNames(stationResponse);
        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, stationsNames);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, CameraActivity.class);
                intent.putExtra(STATION_ID, stationResponse.getData().getNearstations().get(position).getId());
                startActivity(intent);
            }
        });
    }

    private ArrayList<String> getStationsNames(StationResponse stationResponse) {
        ArrayList<String> stations = new ArrayList<>();
        for (NearStation station : stationResponse.getData().getNearstations()) {
            stations.add(station.getStreetName());
        }
        return stations;
    }

}
