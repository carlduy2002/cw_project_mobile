package com.example.cw_project_mobile.Object;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Observations implements Parcelable {
    private int obser_id;
    private String obser_name, obser_description, obser_img, date;

    public Observations() {
    }

    protected Observations(Parcel in) {
        obser_id = in.readInt();
        obser_name = in.readString();
        obser_description = in.readString();
        obser_img = in.readString();
        date = in.readString();
    }

    public static final Creator<Observations> CREATOR = new Creator<Observations>() {
        @Override
        public Observations createFromParcel(Parcel in) {
            return new Observations(in);
        }

        @Override
        public Observations[] newArray(int size) {
            return new Observations[size];
        }
    };

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getObser_img() {
        return obser_img;
    }

    public void setObser_img(String obser_img) {
        this.obser_img = obser_img;
    }

    public int getObser_id() {
        return obser_id;
    }

    public void setObser_id(int obser_id) {
        this.obser_id = obser_id;
    }

    public String getObser_name() {
        return obser_name;
    }

    public void setObser_name(String obser_name) {
        this.obser_name = obser_name;
    }

    public String getObser_description() {
        return obser_description;
    }

    public void setObser_description(String obser_description) {
        this.obser_description = obser_description;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(obser_id);
        dest.writeString(obser_name);
        dest.writeString(obser_description);
        dest.writeString(obser_img);
        dest.writeString(date);
    }
}
