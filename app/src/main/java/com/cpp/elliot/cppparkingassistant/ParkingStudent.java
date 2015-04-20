package com.cpp.elliot.cppparkingassistant;

public class ParkingStudent {
    private String description;
    private String gender;

    public ParkingStudent(String description, String gender) {
        this.description = description;
        this.gender = gender;
    }
    public ParkingStudent(){
        this.description = "";
        this.gender = "";
    }
    public String getDescription() {
        return description;
    }
    public String getGender() {
        return gender;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }
    @Override
    public String toString(){
        return "Your driver is a "+gender+" with description: "+description;
    }
}
