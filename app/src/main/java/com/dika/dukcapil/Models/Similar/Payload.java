package com.dika.dukcapil.Models.Similar;

import java.io.Serializable;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Payload implements Serializable, Parcelable
{

    @SerializedName("similarity")
    @Expose
    private String similarity;
    @SerializedName("rectangled")
    @Expose
    private String rectangled;
    @SerializedName("cropped")
    @Expose
    private String cropped;
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
    private final static long serialVersionUID = 1348887945003689515L;

    protected Payload(Parcel in) {
        this.similarity = ((String) in.readValue((String.class.getClassLoader())));
        this.rectangled = ((String) in.readValue((String.class.getClassLoader())));
        this.cropped = ((String) in.readValue((String.class.getClassLoader())));
    }

    public Payload() {
    }

    public String getSimilarity() {
        return similarity;
    }

    public void setSimilarity(String similarity) {
        this.similarity = similarity;
    }

    public String getRectangled() {
        return rectangled;
    }

    public void setRectangled(String rectangled) {
        this.rectangled = rectangled;
    }

    public String getCropped() {
        return cropped;
    }

    public void setCropped(String cropped) {
        this.cropped = cropped;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(similarity);
        dest.writeValue(rectangled);
        dest.writeValue(cropped);
    }

    public int describeContents() {
        return 0;
    }

}
