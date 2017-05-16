package com.menezes.tagthebus.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.menezes.tagthebus.R;
import com.squareup.picasso.Picasso;

import java.io.File;

import butterknife.ButterKnife;
import butterknife.InjectView;

import static com.menezes.tagthebus.utils.Constants.FILE_PATH;
import static com.menezes.tagthebus.utils.Constants.FOLDER_NAME;

public class PhotoActivity extends AppCompatActivity {

    @InjectView(R.id.image_view)
    ImageView imageView;

    private String filePath = FOLDER_NAME;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
        ButterKnife.inject(this);
        filePath = filePath.concat("/" + getIntent().getStringExtra(FILE_PATH));
        loadPhoto();

    }

    private void loadPhoto() {
        Picasso.with(this).load(new File(filePath)).into(imageView);
    }
}
