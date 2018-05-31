package org.digigeo.digigeo.Entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

@Entity
public class Cache implements Parcelable {

    @PrimaryKey (autoGenerate = true)
    @NonNull
    private int id;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "latitude")
    private double latitude;

    @ColumnInfo(name = "longitude")
    private double longitude;

    @ColumnInfo(name = "content")
    private String content;

    public void setName(String name) {
        this.name = name;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @NonNull
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getContent() {
        return content;
    }

    public static final Creator<Cache> CREATOR = new Creator<Cache>() {

        @Override
        public Cache createFromParcel(Parcel in) {
            return new Cache();
        }

        @Override
        public Cache[] newArray(int size) {
            return new Cache[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(content);
        dest.writeDouble(latitude);
        dest.writeDouble(longitude);
        dest.writeInt(id);
    }

    @Override
    public int describeContents() {
        return 0;
    }
}
