package com.cpp.elliot.cppparkingassistant;

import com.google.android.gms.maps.model.LatLng;
import com.parse.ParseClassName;
import com.parse.ParseObject;

@ParseClassName("LeavingStudent")
public class LeavingStudent extends ParseObject{
    private String description;
    private LatLng location;

    public LeavingStudent(String car, LatLng location) {
        this.description = car;
        this.location = location;
    }
    public LeavingStudent(){
        this.description = "";
        this.location = new LatLng(34.0564,-117.8217);
    }
    public String getDescription() {
        return getString("Description");
    }
    public void setDescription(String description) {
        put("Description",description);
        this.description = description;
    }
    public LatLng getLocation() {
        return location;
    }
    public void setLocation(LatLng location) {
        put("Latitude", location.latitude);
        put("Longitude", location.longitude);
        this.location = location;
    }
    public double getLatitude(){
        return getDouble("Latitude");
    }
    public double getLongitude(){
        return getDouble("Longitude");
    }
    public String toString(){
        return description + ", "+location;
    }
}
