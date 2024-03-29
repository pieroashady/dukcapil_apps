package com.dika.dukcapil.Models.Token;

import java.io.Serializable;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Status implements Serializable, Parcelable
{

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("display_message")
    @Expose
    private Boolean displayMessage;
    @SerializedName("payload")
    @Expose
    private Payload payload;
    public final static Parcelable.Creator<Status> CREATOR = new Creator<Status>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Status createFromParcel(Parcel in) {
            return new Status(in);
        }

        public Status[] newArray(int size) {
            return (new Status[size]);
        }

    }
            ;
    private final static long serialVersionUID = -8803618052375963270L;

    protected Status(Parcel in) {
        this.status = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.message = ((String) in.readValue((String.class.getClassLoader())));
        this.displayMessage = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.payload = ((Payload) in.readValue((Payload.class.getClassLoader())));
    }

    public Status() {
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
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