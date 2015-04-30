package com.cpp.elliot.cppparkingassistant;

import com.google.android.gms.maps.model.LatLng;
import com.parse.ParseClassName;
import com.parse.ParseObject;

@ParseClassName("RideStudent")
public class RideStudent extends ParseObject{
    private String description;
    private String gender;
    private LatLng location;

    public RideStudent(String description, String gender, LatLng location) {
        this.description = description;
        this.gender = gender;
        this.location = location;
    }
    public RideStudent(){
        this.description = "";
        this.gender = "Unknown Gender";
        this.location = new LatLng(34.0564,-117.8217);
    }
    public String getDescription() {
        return getString("Description");
    }
    public void setDescription(String description) {
        put("Description", description);
        this.description = description;
    }
    public String getGender() {
        return getString("Gender");
    }
    public void setGender(String gender) {
        put("Gender", gender);
        this.gender = gender;
    }
    public LatLng getLocation() {
        return location;
    }
    public double getLatitude(){
        return getDouble("Latitude");
    }
    public double getLongitude(){
        return getDouble("Longitude");
    }
    public void setLocation(LatLng location) {
        put("Latitude", location.latitude);
        put("Longitude", location.longitude);
        this.location = location;
    }
    public String toString(){
        return gender+", "+description+ ", " + location;
    }
}
