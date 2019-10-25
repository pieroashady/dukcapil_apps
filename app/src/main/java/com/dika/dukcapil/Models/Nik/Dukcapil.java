package com.dika.dukcapil.Models.Nik;

import java.io.Serializable;
import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Dukcapil implements Serializable, Parcelable
{

    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("data")
    @Expose
    private List<Datum> data = null;
    public final static Parcelable.Creator<Dukcapil> CREATOR = new Creator<Dukcapil>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Dukcapil createFromParcel(Parcel in) {
            return new Dukcapil(in);
        }

        public Dukcapil[] newArray(int size) {
            return (new Dukcapil[size]);
        }

    }
            ;
    private final static long serialVersionUID = 760536655537162862L;

    protected Dukcapil(Parcel in) {
        this.msg = ((String) in.readValue((String.class.getClassLoader())));
        in.readList(this.data, (com.dika.dukcapil.Models.Nik.Datum.class.getClassLoader()));
    }

    public Dukcapil() {
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<Datum> getData() {
        return data;
    }

    public void setData(List<Datum> data) {
        this.data = data;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(msg);
        dest.writeList(data);
    }

    public int describeContents() {
        return 0;
    }

}