package com.cpp.elliot.cppparkingassistant;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class ParkForm2Activity extends Activity {
    private GoogleMap map;
    private LatLng location;
    private LatLng markerLocation;
    private String description = "";
    private String type;
    private final LatLng CPP = new LatLng(34.0564,-117.8217);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.parkform2);
        deleteOvertime();
        map = ((MapFragment) getFragmentManager().findFragmentById(R.id.parkMap)).getMap();
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(CPP, 15);
        map.animateCamera(update);
        ParseQuery<RideStudent> query1 = ParseQuery.getQuery("RideStudent");
        query1.whereExists("Latitude");
        query1.findInBackground(new FindCallback<RideStudent>() {
            @Override
            public void done(List<RideStudent> list, ParseException e) {
                if (e == null) {
                    for (int i = 0; i < list.size(); i++) {
                        location = new LatLng(list.get(i).getLatitude(), list.get(i).getLongitude());
                        description = list.get(i).getGender() + ", " + list.get(i).getDescription();
                        map.addMarker(new MarkerOptions().position(location).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)).title(description));
                    }
                }
            }
        });
        ParseQuery<LeavingStudent> query2 = ParseQuery.getQuery("LeavingStudent");
        query2.whereExists("Latitude");
        query2.findInBackground(new FindCallback<LeavingStudent>() {
            @Override
            public void done(List<LeavingStudent> list, ParseException e) {
                if (e == null) {
                    for (int i = 0; i < list.size(); i++) {
                        location = new LatLng(list.get(i).getLatitude(), list.get(i).getLongitude());
                        description = list.get(i).getDescription();
                        map.addMarker(new MarkerOptions().position(location).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)).title(description));
                    }
                }
            }
        });
        checkEmptyMap();
        //Toast.makeText(ParkForm2Activity.this,b+"", Toast.LENGTH_SHORT).show();
        map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                markerLocation = marker.getPosition();
                marker.showInfoWindow();
                if (marker.getTitle().contains("Male") || marker.getTitle().contains("Female") || marker.getTitle().contains("Unknown Gender"))
                    type = "ride";
                else
                    type = "leave";
                return true;
            }
        });
        Button parkForm2Button = (Button) findViewById(R.id.parkForm2Button);
        parkForm2Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(ParkForm2Activity.this, ParkForm1Activity.class);
                    Bundle b = new Bundle();
                    b.putString("type", type);
                    b.putDouble("latitude", markerLocation.latitude);
                    b.putDouble("longitude", markerLocation.longitude);
                    intent.putExtras(b);
                    startActivity(intent);
                } catch (Exception e) {
                    Toast.makeText(ParkForm2Activity.this, "Please select a student or a parking spot", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    public void deleteOvertime(){
        ParseQuery<RideStudent> query1 = ParseQuery.getQuery("RideStudent");
        query1.whereExists("Latitude");
        query1.findInBackground(new FindCallback<RideStudent>() {
            @Override
            public void done(List<RideStudent> list, ParseException e) {
                if (e == null) {
                    for (int i = 0; i < list.size(); i++) {
                        if (!checkDate(list.get(i).getDateAdded())) {
                            list.get(i).deleteInBackground();
                            list.get(i).saveInBackground();
                        } else if (!checkTime(list.get(i).getTimeAdded())) {
                            list.get(i).deleteInBackground();
                            list.get(i).saveInBackground();
                        }
                    }
                }
            }
        });
        ParseQuery<LeavingStudent> query2 = ParseQuery.getQuery("LeavingStudent");
        query2.whereExists("Latitude");
        query2.findInBackground(new FindCallback<LeavingStudent>() {
            @Override
            public void done(List<LeavingStudent> list, ParseException e) {
                if (e == null) {
                    for (int i = 0; i < list.size(); i++) {
                        if (!checkDate(list.get(i).getDateAdded())) {
                            list.get(i).deleteInBackground();
                            list.get(i).saveInBackground();
                        } else if (!checkTime(list.get(i).getTimeAdded())) {
                            list.get(i).deleteInBackground();
                            list.get(i).saveInBackground();
                        }
                    }
                }
            }
        });
    }
    public void checkEmptyMap(){
        ParseQuery<RideStudent> query1 = ParseQuery.getQuery("RideStudent");
        query1.whereExists("Latitude");
        query1.findInBackground(new FindCallback<RideStudent>() {
            @Override
            public void done(List<RideStudent> list, ParseException e) {
                if (e == null) {
                    if(list.isEmpty()){
                        ParseQuery<LeavingStudent> query2 = ParseQuery.getQuery("LeavingStudent");
                        query2.whereExists("Latitude");
                        query2.findInBackground(new FindCallback<LeavingStudent>() {
                            @Override
                            public void done(List<LeavingStudent> list, ParseException e) {
                                if (e == null) {
                                    if(list.isEmpty()){
                                        alert();
                                    }
                                }
                            }
                        });
                    }
                }
            }
        });
    }
    public void alert(){
        AlertDialog.Builder builder = new AlertDialog.Builder(ParkForm2Activity.this);
        builder.setMessage("Uh Oh! There doesn't seem to be any parking spots or students at this time.");
        builder.setIcon(R.drawable.nopark);
        builder.setNegativeButton("Okay", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent2 = new Intent(ParkForm2Activity.this, ParkingActivity.class);
                startActivity(intent2);
            }
        });
        builder.show();
    }
    public boolean checkDate(String date){
        int month = getMonth(date);
        int day = getDay(date);
        int year = getYear(date);
        boolean bool = false;
        if(year == getCurrentYear()){
            if(month == getCurrentMonth()){
                if(day == getCurrentDay()){
                    bool = true;
                }
            }
        }
        return bool;
    }
    public boolean checkTime(String time){
        int hour = getHour(time);
        int minute = getMinute(time);
        boolean bool = false;
        if(hour == getCurrentHour()){
            if(getCurrentMinute()-minute <= 20){
                bool = true;
            }
        }
        else if(getCurrentHour() == hour+1){
            if(getCurrentMinute()-minute+60 <= 20){
                bool = true;
            }
        }
        return bool;
    }
    public int getMonth(String date){
        String str = "";
        for(int i = 0; i < date.length(); i++){
            if(date.charAt(i) == '-')
                break;
            else
                str += date.charAt(i);
        }
        return Integer.parseInt(str);
    }
    public int getDay(String date){
        String str = "";
        int index = date.indexOf('-')+1;
        for(int i = index; i < date.length(); i++){
            if(date.charAt(i) == '-')
                break;
            else
                str += date.charAt(i);
        }
        return Integer.parseInt(str);
    }
    public int getYear(String date){
        String str = "";
        int index = date.lastIndexOf('-')+1;
        for(int i = index; i < date.length(); i++){
            str += date.charAt(i);
        }
        return Integer.parseInt(str);
    }
    public int getHour(String date){
        String str = "";
        int index = date.indexOf(':');
        for(int i = 0; i < index; i++){
            str += date.charAt(i);
        }
        return Integer.parseInt(str);
    }
    public int getMinute(String date){
        String str = "";
        int index = date.indexOf(':')+1;
        for(int i = index; i < date.length(); i++){
            str += date.charAt(i);
        }
        return Integer.parseInt(str);
    }
    public int getCurrentMonth(){
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yy");
        String date = sdf.format(c.getTime());
        return getMonth(date);
    }
    public int getCurrentDay(){
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yy");
        String date = sdf.format(c.getTime());
        return getDay(date);
    }
    public int getCurrentYear(){
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yy");
        String date = sdf.format(c.getTime());
        return getYear(date);
    }
    public int getCurrentHour(){
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        String date = sdf.format(c.getTime());
        return getHour(date);
    }
    public int getCurrentMinute(){
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        String date = sdf.format(c.getTime());
        return getMinute(date);
    }
}
