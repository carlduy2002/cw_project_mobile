package com.example.cw_project_mobile.Object;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Hikes implements Parcelable{
    private int id;
    private String hike_name, hike_location, hike_length, hike_description, parking, level;
    private String hike_date;
    private String hike_image;



    public Hikes() {
    }

    protected Hikes(Parcel in) {
        id = in.readInt();
        hike_name = in.readString();
        hike_location = in.readString();
        hike_length = in.readString();
        hike_description = in.readString();
        parking = in.readString();
        level = in.readString();
        hike_date = in.readString();
        hike_image = in.readString();
    }

    public static final Creator<Hikes> CREATOR = new Creator<Hikes>() {
        @Override
        public Hikes createFromParcel(Parcel in) {
            return new Hikes(in);
        }

        @Override
        public Hikes[] newArray(int size) {
            return new Hikes[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHike_image() {
        return hike_image;
    }

    public void setHike_image(String hike_image) {
        this.hike_image = hike_image;
    }

    public String getHike_name() {
        return hike_name;
    }

    public void setHike_name(String hike_name) {
        this.hike_name = hike_name;
    }

    public String getHike_location() {
        return hike_location;
    }

    public void setHike_location(String hike_location) {
        this.hike_location = hike_location;
    }

    public String getHike_length() {
        return hike_length;
    }

    public void setHike_length(String hike_length) {
        this.hike_length = hike_length;
    }

    public String getHike_description() {
        return hike_description;
    }

    public void setHike_description(String hike_description) {
        this.hike_description = hike_description;
    }

    public String getParking() {
        return parking;
    }

    public void setParking(String parking) {
        this.parking = parking;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getHike_date() {
        return hike_date;
    }

    public void setHike_date(String hike_date) {
        this.hike_date = hike_date;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(hike_name);
        dest.writeString(hike_location);
        dest.writeString(hike_length);
        dest.writeString(hike_description);
        dest.writeString(parking);
        dest.writeString(level);
        dest.writeString(hike_date);
        dest.writeString(hike_image);
    }
}
