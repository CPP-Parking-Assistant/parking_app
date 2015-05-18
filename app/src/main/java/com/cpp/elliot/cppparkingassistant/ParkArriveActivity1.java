package com.cpp.elliot.cppparkingassistant;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParsePush;
import com.parse.ParseQuery;

import java.util.List;

public class ParkArriveActivity1 extends Activity {
    Button parkArrive1Button;
    private String type,description,gender,notification;
    private double latitude, longitude;
    private String broncoid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.parkarrive1);
        Bundle b = getIntent().getExtras();
        type = b.getString("type");
        gender = b.getString("gender");
        description = b.getString("description2");
        latitude = b.getDouble("latitude");
        longitude = b.getDouble("longitude");
        notification = "Your Driver Has Arrived! They are a "+gender+" with description: "+description;
        if(type.equals("ride")) {
            ParseQuery<RideStudent> query1 = ParseQuery.getQuery("RideStudent");
            query1.whereExists("Latitude");
            query1.findInBackground(new FindCallback<RideStudent>() {
                @Override
                public void done(List<RideStudent> list, ParseException e) {
                    if (e == null) {
                        for (int i = 0; i < list.size(); i++) {
                            if (list.get(i).getLatitude() == latitude && list.get(i).getLongitude() == longitude) {
                                broncoid = list.get(i).getBroncoId();
                            }
                        }
                    }
                }
            });
        }
        else if(type.equals("leave")) {
            ParseQuery<LeavingStudent> query2 = ParseQuery.getQuery("LeavingStudent");
            query2.whereExists("Latitude");
            query2.findInBackground(new FindCallback<LeavingStudent>() {
                @Override
                public void done(List<LeavingStudent> list, ParseException e) {
                    if (e == null) {
                        for(int i = 0; i < list.size(); i++){
                            if(list.get(i).getLatitude() == latitude && list.get(i).getLongitude() == longitude)
                                broncoid = list.get(i).getBroncoId();
                        }
                    }
                }
            });
        }
        parkArrive1Button = (Button) findViewById(R.id.parkArrive1Button);
        parkArrive1Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendNotification();
                Intent intent = new Intent(ParkArriveActivity1.this, ThanksActivity1.class);
                startActivity(intent);
            }
         });
    }
    public void sendNotification(){
        try{
            ParseQuery pushQuery = ParseInstallation.getQuery();
            pushQuery.whereEqualTo("Bronco",broncoid);
            ParsePush push = new ParsePush();
            push.setQuery(pushQuery);
            push.setMessage(notification);
            push.sendInBackground();
            ParseInstallation.getCurrentInstallation().saveInBackground();
        }
        catch(Exception e){
            Log.e("Notification", "Notification Failed");
            e.printStackTrace();
        }
    }
}
