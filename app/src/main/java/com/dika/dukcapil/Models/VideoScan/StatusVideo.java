package com.dika.dukcapil.Models.VideoScan;

import java.io.Serializable;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StatusVideo implements Serializable, Parcelable
{

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private Object message;
    @SerializedName("display_message")
    @Expose
    private Boolean displayMessage;
    @SerializedName("payload")
    @Expose
    private Payload payload;
    public final static Parcelable.Creator<StatusVideo> CREATOR = new Creator<StatusVideo>() {


        @SuppressWarnings({
                "unchecked"
        })
        public StatusVideo createFromParcel(Parcel in) {
            return new StatusVideo(in);
        }

        public StatusVideo[] newArray(int size) {
            return (new StatusVideo[size]);
        }

    }
            ;
    private final static long serialVersionUID = 6286831444607341284L;

    protected StatusVideo(Parcel in) {
        this.status = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.message = ((Object) in.readValue((Object.class.getClassLoader())));
        this.displayMessage = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.payload = ((Payload) in.readValue((Payload.class.getClassLoader())));
    }

    public StatusVideo() {
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }

    public Boolean getDisplayMessage() {
        return displayMessage;
    }

    public void setDisplayMessage(Boolean displayMessage) {
        this.displayMessage = displayMessage;
    }

    public Payload getPayload() {
        return payload;
    }

    public void setPayload(Payload payload) {
        this.payload = payload;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(status);
        dest.writeValue(message);
        dest.writeValue(displayMessage);
        dest.writeValue(payload);
    }

    public int describeContents() {
        return 0;
    }

}