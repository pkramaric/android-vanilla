package com.flybits.android.samples.vanilla.templates;

import android.os.Parcel;
import android.os.Parcelable;

public class TextWithImage implements Parcelable{

    public String title;
    public String description;
    public String img;

    public TextWithImage() {}

    public TextWithImage(Parcel in){
        title       = in.readString();
        description = in.readString();
        img         = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(description);
        dest.writeString(img);
    }

    public static final Creator CREATOR = new Creator() {
        public TextWithImage createFromParcel(Parcel in) {
            return new TextWithImage(in);
        }

        public TextWithImage[] newArray(int size) {
            return new TextWithImage[size];
        }
    };

}
