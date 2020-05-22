package com.example.stapemotion.model;

import android.os.Parcel;
import android.os.Parcelable;

public class DayEmotion implements Parcelable {

    private String date;

    private float happy;

    private float unhappy;

    private float normal;

    public DayEmotion() {
    }

    protected DayEmotion(Parcel in) {
        date = in.readString();
        happy = in.readFloat();
        unhappy = in.readFloat();
        normal = in.readFloat();
    }

    public static final Creator<DayEmotion> CREATOR = new Creator<DayEmotion>() {
        @Override
        public DayEmotion createFromParcel(Parcel in) {
            return new DayEmotion(in);
        }

        @Override
        public DayEmotion[] newArray(int size) {
            return new DayEmotion[size];
        }
    };

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public float getHappy() {
        return happy;
    }

    public void setHappy(float happy) {
        this.happy = happy;
    }

    public float getUnhappy() {
        return unhappy;
    }

    public void setUnhappy(float unhappy) {
        this.unhappy = unhappy;
    }

    public float getNormal() {
        return normal;
    }

    public void setNormal(float normal) {
        this.normal = normal;
    }

    public DayEmotion(String date, float happy, float unhappy, float normal) {
        this.date = date;
        this.happy = happy;
        this.unhappy = unhappy;
        this.normal = normal;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(date);
        parcel.writeFloat(happy);
        parcel.writeFloat(unhappy);
        parcel.writeFloat(normal);
    }
}
