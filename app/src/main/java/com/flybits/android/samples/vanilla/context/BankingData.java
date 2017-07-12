package com.flybits.android.samples.vanilla.context;

import android.os.Parcel;
import android.os.Parcelable;

import com.flybits.context.models.ContextData;

import org.json.JSONException;
import org.json.JSONObject;

public class BankingData extends ContextData implements Parcelable {

    public long balance;
    public String segmentation;
    public String creditcard;

    //MUST HAVE AN EMPTY CONSTRUCTOR
    public BankingData(){}


    //MUST HAVE AN EMPTY CONSTRUCTOR
    public BankingData(Parcel in){
        balance         = in.readLong();
        segmentation    = in.readString();
        creditcard      = in.readString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BankingData that = (BankingData) o;

        if (balance != that.balance) return false;
        if (segmentation != null ? !segmentation.equals(that.segmentation) : that.segmentation != null)
            return false;
        return creditcard != null ? creditcard.equals(that.creditcard) : that.creditcard == null;

    }

    @Override
    public void fromJson(String json) {
        try {
            JSONObject jsonObj  = new JSONObject(json);
            balance             = jsonObj.getLong("balance");
            segmentation        = jsonObj.getString("segmentation");
            creditcard          = jsonObj.getString("creditcard");
        }catch (JSONException exception){}
    }

    @Override
    public String toJson() {
        JSONObject object=new JSONObject();
        try {
            object.put("balance", balance);
            object.put("segmentation", segmentation);
            object.put("creditcard", creditcard);
        }catch (JSONException exception){}
        return object.toString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(balance);
        dest.writeString(segmentation);
        dest.writeString(creditcard);
    }

    @Override
    public String toString(){
        return toJson();
    }

    public static final Creator<BankingData> CREATOR = new Creator<BankingData>() {
        public BankingData createFromParcel(Parcel in) {
            return new BankingData(in);
        }

        public BankingData[] newArray(int size) {
            return new BankingData[size];
        }
    };
}