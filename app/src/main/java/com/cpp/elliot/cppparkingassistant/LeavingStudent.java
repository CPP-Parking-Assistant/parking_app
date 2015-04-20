package com.cpp.elliot.cppparkingassistant;

import com.google.android.gms.maps.model.LatLng;

public class LeavingStudent {
    private String car;
    private LatLng location;

    public LeavingStudent(String car, LatLng location) {
        this.car = car;
        this.location = location;
    }
    public LeavingStudent(){
        this.car = "";
        this.location = new LatLng(34.0564,-117.8217);
    }
    public String getCar() {
        return car;
    }
    public void setCar(String car) {
        this.car = car;
    }
    public LatLng getLocation() {
        return location;
    }
    public void setLocation(LatLng location) {
        this.location = location;
    }
    public String toString(){
        return car;
    }
}
