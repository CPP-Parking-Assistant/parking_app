package com.cpp.elliot.cppparkingassistant;

import com.google.android.gms.maps.model.LatLng;

public class RideStudent {
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
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getGender() {
        return gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }
    public LatLng getLocation() {
        return location;
    }
    public void setLocation(LatLng location) {
        this.location = location;
    }
    public String toString(){
        return gender+", "+description;
    }
}
