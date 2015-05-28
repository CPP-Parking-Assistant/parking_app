package com.cpp.elliot.cppparkingassistant;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParsePush;
import com.parse.ParseQuery;

import java.util.List;

public class ParkArriveActivity1 extends ActionBarActivity {
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
        if(type.equals("ride")) {
            ParseQuery<RideStudent> query1 = ParseQuery.getQuery("RideStudent");
            query1.whereExists("Latitude");
            query1.findInBackground(new FindCallback<RideStudent>() {
                @Override
                public void done(List<RideStudent> list, ParseException e) {
                    if (e == null) {
                        for (int i = 0; i < list.size(); i++) {
                            if (list.get(i).getBroncoId().equals(broncoid)) {
                                list.get(i).deleteInBackground();
                                list.get(i).saveInBackground();
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
                            if(list.get(i).getBroncoId().equals(broncoid)) {
                                list.get(i).deleteInBackground();
                                list.get(i).saveInBackground();
                            }
                        }
                    }
                }
            });
        }
        ParseQuery<ParkingStudent> query3 = ParseQuery.getQuery("ParkingStudent");
        query3.whereExists("BroncoId");
        query3.findInBackground(new FindCallback<ParkingStudent>() {
            @Override
            public void done(List<ParkingStudent> list, ParseException e) {
                if (e == null) {
                    for (int i = 0; i < list.size(); i++) {
                        if (list.get(i).getBroncoId().equals(broncoid)) {
                            list.get(i).deleteInBackground();
                            list.get(i).saveInBackground();
                        }
                    }
                }
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.parking_activity_actions, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                openSettings();
                return true;
            case R.id.action_home:
                goHome();
                return true;
            case R.id.action_exit:
                exit();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public void openSettings(){
        Intent intent = new Intent(ParkArriveActivity1.this,Settings.class);
        startActivity(intent);
    }
    public void goHome(){
        Intent intent = new Intent(ParkArriveActivity1.this,ParkingActivity.class);
        startActivity(intent);
    }
    public void exit(){
        this.finish();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
