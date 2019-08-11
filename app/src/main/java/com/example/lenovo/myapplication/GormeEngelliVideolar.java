package com.example.lenovo.myapplication;

import android.os.Parcel;
import android.os.Parcelable;

public class GormeEngelliVideolar implements Parcelable {  // Bir sınıfı obje gibi aktarmak istersek Parcelable kullanırız.

    private String videoUrl;
    private String userId;

    public String getVideoUrl() {
        return videoUrl;
    }

    public GormeEngelliVideolar(String videoUrl, String userId) {
        this.userId = userId;
        this.videoUrl = videoUrl;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    protected GormeEngelliVideolar(Parcel in) {

        videoUrl=in.readString();
        userId = in.readString();
    }

    public static final Creator<GormeEngelliVideolar> CREATOR = new Creator<GormeEngelliVideolar>() {
        @Override
        public GormeEngelliVideolar createFromParcel(Parcel in) {
            return new GormeEngelliVideolar(in);
        }

        @Override
        public GormeEngelliVideolar[] newArray(int size) {
            return new GormeEngelliVideolar[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(videoUrl);
        dest.writeString(userId);

    }
}