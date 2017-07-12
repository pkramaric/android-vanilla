package com.flybits.android.samples.vanilla.context;

import android.os.Parcel;
import android.os.Parcelable;

import com.flybits.context.models.ContextData;

import org.json.JSONException;
import org.json.JSONObject;

public class RestaurantData extends ContextData implements Parcelable {

    public String dietary;
    public long price;
    public long calorie;

    //MUST HAVE AN EMPTY CONSTRUCTOR
    public RestaurantData(){}


    //MUST HAVE AN EMPTY CONSTRUCTOR
    public RestaurantData(Parcel in){
        dietary         = in.readString();
        price           = in.readLong();
        calorie         = in.readLong();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RestaurantData that = (RestaurantData) o;

        if (price != that.price) return false;
        if (calorie != that.calorie) return false;
        return dietary != null ? dietary.equals(that.dietary) : that.dietary == null;
    }

    @Override
    public void fromJson(String json) {
        try {
            JSONObject jsonObj  = new JSONObject(json);
            price               = jsonObj.getLong("price");
            dietary             = jsonObj.getString("dietary");
            calorie             = jsonObj.getLong("calorie");
        }catch (JSONException exception){}
    }

    @Override
    public String toJson() {
        JSONObject object=new JSONObject();
        try {
            object.put("dietary", dietary);
            object.put("price", price);
            object.put("calorie", calorie);
        }catch (JSONException exception){}
        return object.toString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(dietary);
        dest.writeLong(price);
        dest.writeLong(calorie);
    }

    @Override
    public String toString(){
        return toJson();
    }

    public static final Creator<RestaurantData> CREATOR = new Creator<RestaurantData>() {
        public RestaurantData createFromParcel(Parcel in) {
            return new RestaurantData(in);
        }

        public RestaurantData[] newArray(int size) {
            return new RestaurantData[size];
        }
    };
}