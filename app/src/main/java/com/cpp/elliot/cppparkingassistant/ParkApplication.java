package com.cpp.elliot.cppparkingassistant;

import android.app.Application;
import com.parse.Parse;
import com.parse.ParseObject;

public class ParkApplication extends Application {
    @Override
    public void onCreate() {
        Parse.initialize(this, "HhRA2zwLkk89dnb4aeKXIqWuUuVxwiQvJ0opeJhC", "OkE1MPzaMvZWdq5FsaGmkOyNVHWiolTjtJK3P1sB");
        ParseObject.registerSubclass(RideStudent.class);
        ParseObject.registerSubclass(LeavingStudent.class);
        ParseObject.registerSubclass(ParkingStudent.class);
    }
}
