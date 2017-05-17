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
    private String date;
    private String time;

    public ListAdapter(Activity context, ArrayList<String> photosNames, ArrayList<Bitmap> thumbnails) {
        super(context, R.layout.station_list_item, photosNames);
        this.context = context;
        this.thumbnails = thumbnails;
        this.photosNames = photosNames;

    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.station_list_item, null, true);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.photo_name);
        TextView txtDate = (TextView) rowView.findViewById(R.id.photo_date);
        TextView txtTime = (TextView) rowView.findViewById(R.id.photo_time);

        ImageView imageView = (ImageView) rowView.findViewById(R.id.image);
        txtTitle.setText(photosNames.get(position));
        imageView.setImageBitmap(thumbnails.get(position));
        setDateAndTime(photosNames.get(position));
        txtDate.setText(date);
        txtTime.setText(time);
        return rowView;
    }

    private void setDateAndTime(String photoName){
        String[] strSplit = photoName.split(":");
        String[] strSplitDate = strSplit[1].split("_");

        String strYear = strSplitDate[0].substring(0,4);
        String strMonth = strSplitDate[0].substring(4,6);
        String strDay = strSplitDate[0].substring(6,8);
        date = strDay + "/" + strMonth + "/" + strYear;

        String strHour = strSplitDate[1].substring(0,2);
        String strMin = strSplitDate[1].substring(2,4);
        time = strHour + "h" + strMin + "min";
    }
}