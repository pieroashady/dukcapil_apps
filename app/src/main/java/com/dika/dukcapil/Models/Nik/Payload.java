package com.dika.dukcapil.Models.Nik;

import java.io.Serializable;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Payload implements Serializable, Parcelable
{

    @SerializedName("data")
    @Expose
    private Data data;
    public final static Parcelable.Creator<Payload> CREATOR = new Creator<Payload>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Payload createFromParcel(Parcel in) {
            return new Payload(in);
        }

        public Payload[] newArray(int size) {
            return (new Payload[size]);
        }

    }
            ;
    private final static long serialVersionUID = 6264245368906258009L;

    protected Payload(Parcel in) {
        this.data = ((Data) in.readValue((Data.class.getClassLoader())));
    }

    public Payload() {
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(data);
    }

    public int describeContents() {
        return 0;
    }

}