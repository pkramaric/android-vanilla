package com.flybits.android.samples.vanilla.templates;

import android.os.Parcel;
import android.os.Parcelable;

public class ImageOnly implements Parcelable{

    public String url;

    public ImageOnly() {}

    public ImageOnly(Parcel in){
        url       = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(url);
    }

    public static final Creator CREATOR = new Creator() {
        public ImageOnly createFromParcel(Parcel in) {
            return new ImageOnly(in);
        }

        public ImageOnly[] newArray(int size) {
            return new ImageOnly[size];
        }
    };

}
