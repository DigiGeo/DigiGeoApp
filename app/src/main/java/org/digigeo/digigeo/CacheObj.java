package org.digigeo.digigeo;

public class CacheObj {

    private String content;
    private String name;
    private double latitude;
    private double longitude;

    public CacheObj(String name, String content, double latitude, double longitude) {
        
        this.name = name;
        this.content = content;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return "CacheObj{" +
                "content='" + content + '\'' +
                ", name='" + name + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }
}
