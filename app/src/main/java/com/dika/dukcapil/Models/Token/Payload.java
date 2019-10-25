package com.dika.dukcapil.Models.Token;

import java.io.Serializable;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Payload implements Serializable, Parcelable
{

    @SerializedName("token")
    @Expose
    private String token;
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
    private final static long serialVersionUID = -8868910946623506721L;

    protected Payload(Parcel in) {
        this.token = ((String) in.readValue((String.class.getClassLoader())));
    }

    public Payload() {
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(token);
    }

    public int describeContents() {
        return 0;
    }

}