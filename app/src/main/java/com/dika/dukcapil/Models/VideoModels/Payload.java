package com.dika.dukcapil.Models.VideoModels;

import java.io.Serializable;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Payload implements Serializable, Parcelable
{

    @SerializedName("thumbnails")
    @Expose
    private Thumbnails thumbnails;
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
    private final static long serialVersionUID = -6125118151450476495L;

    protected Payload(Parcel in) {
        this.thumbnails = ((Thumbnails) in.readValue((Thumbnails.class.getClassLoader())));
    }

    public Payload() {
    }

    public Thumbnails getThumbnails() {
        return thumbnails;
    }

    public void setThumbnails(Thumbnails thumbnails) {
        this.thumbnails = thumbnails;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(thumbnails);
    }

    public int describeContents() {
        return 0;
    }

}
