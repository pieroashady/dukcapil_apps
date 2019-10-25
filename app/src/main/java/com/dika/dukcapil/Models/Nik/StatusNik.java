package com.dika.dukcapil.Models.Nik;

import java.io.Serializable;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StatusNik implements Serializable, Parcelable
{

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("display_message")
    @Expose
    private String displayMessage;
    @SerializedName("payload")
    @Expose
    private Payload payload;
    public final static Parcelable.Creator<StatusNik> CREATOR = new Creator<StatusNik>() {


        @SuppressWarnings({
                "unchecked"
        })
        public StatusNik createFromParcel(Parcel in) {
            return new StatusNik(in);
        }

        public StatusNik[] newArray(int size) {
            return (new StatusNik[size]);
        }

    }
            ;
    private final static long serialVersionUID = -3315868003698178576L;

    protected StatusNik(Parcel in) {
        this.status = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.message = ((String) in.readValue((String.class.getClassLoader())));
        this.displayMessage = ((String) in.readValue((String.class.getClassLoader())));
        this.payload = ((Payload) in.readValue((Payload.class.getClassLoader())));
    }

    public StatusNik() {
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

    public String getDisplayMessage() {
        return displayMessage;
    }

    public void setDisplayMessage(String displayMessage) {
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