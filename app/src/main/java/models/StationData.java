package models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by cassiano.menezes on 13/05/2017.
 */

public class StationData implements Parcelable {

    @SerializedName("nearstations")
    private List<NearStation> nearstations = null;

    public List<NearStation> getNearstations() {
        return nearstations;
    }

    protected StationData(Parcel in) {
        nearstations = in.createTypedArrayList(NearStation.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(nearstations);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<StationData> CREATOR = new Creator<StationData>() {
        @Override
        public StationData createFromParcel(Parcel in) {
            return new StationData(in);
        }

        @Override
        public StationData[] newArray(int size) {
            return new StationData[size];
        }
    };
}
