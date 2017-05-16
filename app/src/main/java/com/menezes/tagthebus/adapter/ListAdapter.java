package com.menezes.tagthebus.adapter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.menezes.tagthebus.R;

import java.util.ArrayList;

/**
 * Created by cassiano.menezes on 15/05/2017.
 */

public class ListAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final ArrayList<Bitmap> thumbnails;
    private final ArrayList<String> photosNames;
    public ListAdapter(Activity context, ArrayList<String> photosNames, ArrayList<Bitmap> thumbnails) {
        super(context, R.layout.station_list_item, photosNames);
        this.context = context;
        this.thumbnails = thumbnails;
        this.photosNames = photosNames;

    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.station_list_item, null, true);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.txt);

        ImageView imageView = (ImageView) rowView.findViewById(R.id.img);
        txtTitle.setText(photosNames.get(position));
        imageView.setImageBitmap(thumbnails.get(position));
        return rowView;
    }
}