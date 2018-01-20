package com.bulletin.theinvincible.bulletin;

import android.os.Parcel;
import android.os.Parcelable;

public class ModelString implements Parcelable{

    public String authorName;
    public String headline;
    public String publishedTime;
    public String newsDetail;

    protected ModelString(Parcel in) {
        authorName = in.readString();
        headline = in.readString();
        publishedTime = in.readString();
        newsDetail = in.readString();
    }

    public static final Parcelable.Creator<ModelString> CREATOR = new Parcelable.Creator<ModelString>() {
        @Override
        public ModelString createFromParcel(Parcel in) {
            return new ModelString(in);
        }

        @Override
        public ModelString[] newArray(int size) {
            return new ModelString[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(authorName);
        parcel.writeString(headline);
        parcel.writeString(publishedTime);
        parcel.writeString(newsDetail);
    }
}
