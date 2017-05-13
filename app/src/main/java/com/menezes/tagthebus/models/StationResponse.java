package com.menezes.tagthebus.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by cassiano.menezes on 13/05/2017.
 */

public class StationResponse implements Parcelable {


    @SerializedName("data")
    private StationData data;

    public StationData getData() {
        return data;
    }

    protected StationResponse(Parcel in) {
        data = in.readParcelable(StationData.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(data, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<StationResponse> CREATOR = new Creator<StationResponse>() {
        @Override
        public StationResponse createFromParcel(Parcel in) {
            return new StationResponse(in);
        }

        @Override
        public StationResponse[] newArray(int size) {
            return new StationResponse[size];
        }
    };
}
