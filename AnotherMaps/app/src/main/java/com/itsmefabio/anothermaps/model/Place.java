package com.itsmefabio.anothermaps.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.squareup.moshi.Json;

public class Place implements Parcelable {
    @Json(name = "place_id")
    public String id;
    @Json(name = "formatted_address")
    public String formattedAddress;
    @Json(name = "geometry")
    public Geometry geometry;

    public Place(String id, String formattedAddress, Geometry geometry) {
        this.id = id;
        this.formattedAddress = formattedAddress;
        this.geometry = geometry;
    }

    protected Place(Parcel in) {
        id = in.readString();
        formattedAddress = in.readString();
        geometry = in.readParcelable(Geometry.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(formattedAddress);
        dest.writeParcelable(geometry, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Place> CREATOR = new Creator<Place>() {
        @Override
        public Place createFromParcel(Parcel in) {
            return new Place(in);
        }

        @Override
        public Place[] newArray(int size) {
            return new Place[size];
        }
    };
}
