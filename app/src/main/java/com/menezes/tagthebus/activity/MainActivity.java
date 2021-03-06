package com.menezes.tagthebus.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

import com.menezes.tagthebus.R;
import com.menezes.tagthebus.camera.CameraActivity;
import com.menezes.tagthebus.models.NearStation;
import com.menezes.tagthebus.models.StationResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.menezes.tagthebus.services.RetrofitClient;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.menezes.tagthebus.utils.Constants.STATION_ID;
import static com.menezes.tagthebus.utils.Constants.STATION_NAME;

public class MainActivity extends AppCompatActivity {

    @InjectView(R.id.stationsList)
    ListView listView;

    @InjectView(R.id.progressBar)
    ProgressBar progressBar;

    public ArrayList<String> stationsNames;

    //this is hardcoded to ensure we all can test the same location
    private String latitude = "41.3985182";
    private String longitude = "2.1917991";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        progressBar.setVisibility(VISIBLE);
        checkPermissions();

    }

    private void checkPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            } else {
                getStationInfo();
            }
        } else {
            getStationInfo();
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
        Call<StationResponse> accountCall = new RetrofitClient().getModel().getStationsInfo(latitude, longitude);
        accountCall.enqueue(new Callback<StationResponse>() {
            @Override
            public void onResponse(Call<StationResponse> call, Response<StationResponse> response) {
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
                Intent intent = new Intent(MainActivity.this, StationDetailActivity.class);
                intent.putExtra(STATION_ID, stationResponse.getData().getNearstations().get(position).getId());
                intent.putExtra(STATION_NAME, stationResponse.getData().getNearstations().get(position).getStreetName());
                startActivity(intent);
            }
        });
        progressBar.setVisibility(GONE);
    }

    private ArrayList<String> getStationsNames(StationResponse stationResponse) {
        ArrayList<String> stations = new ArrayList<>();
        for (NearStation station : stationResponse.getData().getNearstations()) {
            stations.add(station.getStreetName());
        }
        return stations;
    }

}
