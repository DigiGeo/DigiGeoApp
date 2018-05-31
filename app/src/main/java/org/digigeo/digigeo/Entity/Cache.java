package org.digigeo.digigeo.Entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

@Entity
public class Cache implements Parcelable {

    @PrimaryKey(autoGenerate = true)
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

    @Ignore
    public Cache(String name, double latitude, double longitude, String content) {

        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.content = content;
    }

    public Cache() {
        //empty constructor for parcelable
    }

    public void setId(@NonNull int id) {
        this.id = id;
    }

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

    public static Cache[] populateData() {
        return new Cache[]{
                new Cache("test1", 47.6992, -122.3334, "North Seattle College is pretty cool I guess."),
                new Cache("test2", 47.6062, -122.3321, "Downtown seattle has so much construction."),
                new Cache("test3", 47.2529, -122.4443, "Tacoma may smell bad by the interstate, but has some great neighborhoods on the north end."),
                new Cache("test4", 47.6101, -122.2015, "Bellevue only exists to make peoples commutes a headache on the east side.")
        };
    }
}
