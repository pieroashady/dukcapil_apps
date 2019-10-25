package com.dika.dukcapil.Models.VideoModels;

import java.io.Serializable;
import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Thumbnails implements Serializable, Parcelable
{

    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("snapshots")
    @Expose
    private List<Snapshot> snapshots = null;
    public final static Parcelable.Creator<Thumbnails> CREATOR = new Creator<Thumbnails>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Thumbnails createFromParcel(Parcel in) {
            return new Thumbnails(in);
        }

        public Thumbnails[] newArray(int size) {
            return (new Thumbnails[size]);
        }

    }
            ;
    private final static long serialVersionUID = -409856495804669903L;

    protected Thumbnails(Parcel in) {
        this.msg = ((String) in.readValue((String.class.getClassLoader())));
        in.readList(this.snapshots, (Snapshot.class.getClassLoader()));
    }

    public Thumbnails() {
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<Snapshot> getSnapshots() {
        return snapshots;
    }

    public void setSnapshots(List<Snapshot> snapshots) {
        this.snapshots = snapshots;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(msg);
        dest.writeList(snapshots);
    }

    public int describeContents() {
        return 0;
    }

}