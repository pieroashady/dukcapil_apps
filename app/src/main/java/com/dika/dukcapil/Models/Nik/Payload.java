package com.dika.dukcapil.Models.Nik;

import java.io.Serializable;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Payload implements Serializable, Parcelable
{

    @SerializedName("dukcapil")
    @Expose
    private Dukcapil dukcapil;
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
    private final static long serialVersionUID = -5308509177186617560L;

    protected Payload(Parcel in) {
        this.dukcapil = ((Dukcapil) in.readValue((Dukcapil.class.getClassLoader())));
    }

    public Payload() {
    }

    public Dukcapil getDukcapil() {
        return dukcapil;
    }

    public void setDukcapil(Dukcapil dukcapil) {
        this.dukcapil = dukcapil;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(dukcapil);
    }

    public int describeContents() {
        return 0;
    }

}
