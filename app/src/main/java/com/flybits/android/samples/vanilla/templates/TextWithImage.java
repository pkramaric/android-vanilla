package com.flybits.android.samples.vanilla.templates;

import android.os.Parcel;
import android.os.Parcelable;

import com.flybits.android.kernel.models.LocalizedValue;

public class TextWithImage implements Parcelable{

    public LocalizedValue txtTitle;
    public LocalizedValue txtDescription;
    public String img;

    public TextWithImage() {}

    public TextWithImage(Parcel in){
        txtTitle        = in.readParcelable(LocalizedValue.class.getClassLoader());
        txtDescription  = in.readParcelable(LocalizedValue.class.getClassLoader());
        img             = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(txtTitle, flags);
        dest.writeParcelable(txtDescription, flags);
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
