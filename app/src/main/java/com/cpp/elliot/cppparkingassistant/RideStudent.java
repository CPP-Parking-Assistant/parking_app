package com.cpp.elliot.cppparkingassistant;

import com.google.android.gms.maps.model.LatLng;
import com.parse.ParseClassName;
import com.parse.ParseObject;

@ParseClassName("RideStudent")
public class RideStudent extends ParseObject{
    private String description;
    private String gender;
    private LatLng location;
    private String broncoId,date,time;

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
    public void setBroncoID(String broncoID){
        put("BroncoId",broncoID);
        this.broncoId = broncoID;
    }
    public void setDateAdded(String date){
        put("DateAdded",date);
        this.date = date;
    }
    public String getDateAdded(){
        return getString("DateAdded");
    }
    public void setTimeAdded(String time){
        put("TimeAdded",time);
        this.time = time;
    }
    public String getTimeAdded(){
        return getString("TimeAdded");
    }
    public String getBroncoId(){
        return getString("BroncoId");
    }
    public String toString(){
        return gender+", "+description;
    }
}
