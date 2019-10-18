package com.dika.dukcapil.Models.VideoScan;

import java.io.Serializable;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Snapshot implements Serializable, Parcelable
{

    @SerializedName("second")
    @Expose
    private Integer second;
    @SerializedName("base64")
    @Expose
    private String base64;
    public final static Parcelable.Creator<Snapshot> CREATOR = new Creator<Snapshot>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Snapshot createFromParcel(Parcel in) {
            return new Snapshot(in);
        }

        public Snapshot[] newArray(int size) {
            return (new Snapshot[size]);
        }

    }
            ;
    private final static long serialVersionUID = 7654248475492921038L;

    protected Snapshot(Parcel in) {
        this.second = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.base64 = ((String) in.readValue((String.class.getClassLoader())));
    }

    public Snapshot() {
    }

    public Integer getSecond() {
        return second;
    }

    public void setSecond(Integer second) {
        this.second = second;
    }

    public String getBase64() {
        return base64;
    }

    public void setBase64(String base64) {
        this.base64 = base64;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(second);
        dest.writeValue(base64);
    }

    public int describeContents() {
        return 0;
    }

}