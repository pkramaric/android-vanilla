package com.flybits.android.samples.vanilla.templates;

import android.os.Parcel;
import android.os.Parcelable;

import com.flybits.android.kernel.models.LocalizedValue;

public class TextOnly implements Parcelable{

    public LocalizedValue txtTitle;
    public LocalizedValue txtDescription;

    public TextOnly() {}

    public TextOnly(Parcel in){
        txtTitle        = in.readParcelable(LocalizedValue.class.getClassLoader());
        txtDescription  = in.readParcelable(LocalizedValue.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(txtTitle, flags);
        dest.writeParcelable(txtDescription, flags);
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
