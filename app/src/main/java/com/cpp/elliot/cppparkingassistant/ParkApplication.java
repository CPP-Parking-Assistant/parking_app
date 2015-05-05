package com.cpp.elliot.cppparkingassistant;

import android.app.Application;
import android.util.Log;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParsePush;
import com.parse.SaveCallback;

public class ParkApplication extends Application {
    @Override
    public void onCreate() {
        Parse.initialize(this, "HhRA2zwLkk89dnb4aeKXIqWuUuVxwiQvJ0opeJhC", "OkE1MPzaMvZWdq5FsaGmkOyNVHWiolTjtJK3P1sB");
        ParseObject.registerSubclass(RideStudent.class);
        ParseObject.registerSubclass(LeavingStudent.class);
        ParseObject.registerSubclass(ParkingStudent.class);
        ParsePush.subscribeInBackground("", new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null)
                    Log.d("com.parse.push", "Successfully subscribed to broadcast");
                else
                    Log.e("com.parse.push", "failed to subscribe for push notifications", e);
            }
        });
    }
}
