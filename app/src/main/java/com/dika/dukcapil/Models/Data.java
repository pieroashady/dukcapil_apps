package com.dika.dukcapil.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Data implements Serializable, Parcelable
{

    @SerializedName("nik")
    @Expose
    private String nik;
    @SerializedName("is_id_card")
    @Expose
    private Boolean isIdCard;
    @SerializedName("is_photo_inside")
    @Expose
    private Boolean isPhotoInside;
    @SerializedName("base64_photo")
    @Expose
    private String base64Photo;
    @SerializedName("base64_signature")
    @Expose
    private String base64Signature;
    public final static Creator<Data> CREATOR = new Creator<Data>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Data createFromParcel(Parcel in) {
            return new Data(in);
        }

        public Data[] newArray(int size) {
            return (new Data[size]);
        }

    }
            ;
    private final static long serialVersionUID = -4868955875891012643L;

    protected Data(Parcel in) {
        this.nik = ((String) in.readValue((String.class.getClassLoader())));
        this.isIdCard = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.isPhotoInside = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.base64Photo = ((String) in.readValue((String.class.getClassLoader())));
        this.base64Signature = ((String) in.readValue((String.class.getClassLoader())));
    }

    public Data() {
    }

    public String getNik() {
        return nik;
    }

    public void setNik(String nik) {
        this.nik = nik;
    }

    public Boolean getIsIdCard() {
        return isIdCard;
    }

    public void setIsIdCard(Boolean isIdCard) {
        this.isIdCard = isIdCard;
    }

    public Boolean getIsPhotoInside() {
        return isPhotoInside;
    }

    public void setIsPhotoInside(Boolean isPhotoInside) {
        this.isPhotoInside = isPhotoInside;
    }

    public String getBase64Photo() {
        return base64Photo;
    }

    public void setBase64Photo(String base64Photo) {
        this.base64Photo = base64Photo;
    }

    public String getBase64Signature() {
        return base64Signature;
    }

    public void setBase64Signature(String base64Signature) {
        this.base64Signature = base64Signature;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(nik);
        dest.writeValue(isIdCard);
        dest.writeValue(isPhotoInside);
        dest.writeValue(base64Photo);
        dest.writeValue(base64Signature);
    }

    public int describeContents() {
        return 0;
    }

}