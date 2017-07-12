package com.flybits.android.samples.vanilla.templates;

import android.os.Parcel;
import android.os.Parcelable;

public class TextOnly implements Parcelable{

    public String title;
    public String description;

    public TextOnly() {}

    public TextOnly(Parcel in){
        title       = in.readString();
        description = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(description);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public TextOnly createFromParcel(Parcel in) {
            return new TextOnly(in);
        }

        public TextOnly[] newArray(int size) {
            return new TextOnly[size];
        }
    };

}
