package com.menezes.tagthebus.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by cassiano.menezes on 13/05/2017.
 */

public class NearStation implements Parcelable {

    @SerializedName("id")
    private String id;
    @SerializedName("street_name")
    private String streetName;
    @SerializedName("city")
    private String city;
    @SerializedName("utm_x")
    private String utmX;
    @SerializedName("utm_y")
    private String utmY;
    @SerializedName("lat")
    private String lat;
    @SerializedName("lon")
    private String lon;
    @SerializedName("furniture")
    private String furniture;
    @SerializedName("buses")
    private String buses;
    @SerializedName("distance")
    private String distance;

    public String getId() {
        return id;
    }

    public String getStreetName() {
        return streetName;
    }

    public String getCity() {
        return city;
    }

    public String getUtmX() {
        return utmX;
    }

    public String getUtmY() {
        return utmY;
    }

    public String getLat() {
        return lat;
    }

    public String getLon() {
        return lon;
    }

    public String getFurniture() {
        return furniture;
    }

    public String getBuses() {
        return buses;
    }

    public String getDistance() {
        return distance;
    }

    protected NearStation(Parcel in) {
        id = in.readString();
        streetName = in.readString();
        city = in.readString();
        utmX = in.readString();
        utmY = in.readString();
        lat = in.readString();
        lon = in.readString();
        furniture = in.readString();
        buses = in.readString();
        distance = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(streetName);
        dest.writeString(city);
        dest.writeString(utmX);
        dest.writeString(utmY);
        dest.writeString(lat);
        dest.writeString(lon);
        dest.writeString(furniture);
        dest.writeString(buses);
        dest.writeString(distance);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<NearStation> CREATOR = new Creator<NearStation>() {
        @Override
        public NearStation createFromParcel(Parcel in) {
            return new NearStation(in);
        }

        @Override
        public NearStation[] newArray(int size) {
            return new NearStation[size];
        }
    };
}
