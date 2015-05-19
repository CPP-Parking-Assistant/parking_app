package com.cpp.elliot.cppparkingassistant;

import com.parse.ParseClassName;
import com.parse.ParseObject;

@ParseClassName("ParkingStudent")
public class ParkingStudent extends ParseObject{
    private String description;
    private String gender;
    private String broncoId;

    public ParkingStudent(String description, String gender) {
        this.description = description;
        this.gender = gender;
    }
    public ParkingStudent(){
        this.description = "";
        this.gender = "";
        this.broncoId = "";
    }
    public String getDescription() {
        return getString("Description");
    }
    public String getGender() {
        return getString("Gender");
    }
    public void setDescription(String description) {
        put("Description",description);
        this.description = description;
    }
    public void setGender(String gender) {
        put("Gender",gender);
        this.gender = gender;
    }
    public void setBroncoId(String broncoId){
        put("BroncoId",broncoId);
        this.broncoId = broncoId;
    }
    public String getBroncoId(){
        return getString("BroncoId");
    }
    @Override
    public String toString(){
        return "Your driver is a "+gender+" with description: "+description;
    }
}
