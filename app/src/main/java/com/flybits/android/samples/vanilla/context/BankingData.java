package com.flybits.android.samples.vanilla.context;

import android.os.Parcel;
import android.os.Parcelable;

import com.flybits.context.models.ContextData;

import org.json.JSONException;
import org.json.JSONObject;

public class BankingData extends ContextData implements Parcelable {

    public long accountBalance = -1;
    public String segmentation = "";
    public String creditCard = "";

    //MUST HAVE AN EMPTY CONSTRUCTOR
    public BankingData(){}


    //MUST HAVE AN EMPTY CONSTRUCTOR
    public BankingData(Parcel in){
        accountBalance         = in.readLong();
        segmentation    = in.readString();
        creditCard      = in.readString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BankingData that = (BankingData) o;

        if (accountBalance != that.accountBalance) return false;
        if (segmentation != null ? !segmentation.equals(that.segmentation) : that.segmentation != null)
            return false;
        return creditCard != null ? creditCard.equals(that.creditCard) : that.creditCard == null;

    }

    @Override
    public void fromJson(String json) {
        try {
            JSONObject jsonObj  = new JSONObject(json);
            if (accountBalance > 0)
                accountBalance             = jsonObj.getLong("accountBalance");

            if (segmentation.length() > 0)
                segmentation        = jsonObj.getString("segmentation");

            if (creditCard.length() > 0)
                creditCard          = jsonObj.getString("creditCard");
        }catch (JSONException exception){}
    }

    @Override
    public String getPluginID() {
        return "ctx.project.banking";
    }

    @Override
    public String toJson() {
        JSONObject object=new JSONObject();
        try {
            if (accountBalance > 0)
                object.put("accountBalance", accountBalance);

            if (segmentation.length() > 0)
                object.put("segmentation", segmentation);

            if (creditCard.length() > 0)
                object.put("creditCard", creditCard);
        }catch (JSONException exception){}
        return object.toString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(accountBalance);
        dest.writeString(segmentation);
        dest.writeString(creditCard);
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