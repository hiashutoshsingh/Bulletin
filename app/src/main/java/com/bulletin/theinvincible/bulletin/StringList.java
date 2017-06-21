package com.bulletin.theinvincible.bulletin;

import android.os.Parcel;
import android.os.Parcelable;

public class StringList implements Parcelable{

    public String authorName;
    public String headline;
    public String publishedTime;
    public String newsDetail;

    protected StringList(Parcel in) {
        authorName = in.readString();
        headline = in.readString();
        publishedTime = in.readString();
        newsDetail = in.readString();
    }

    public static final Parcelable.Creator<StringList> CREATOR = new Parcelable.Creator<StringList>() {
        @Override
        public StringList createFromParcel(Parcel in) {
            return new StringList(in);
        }

        @Override
        public StringList[] newArray(int size) {
            return new StringList[size];
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
