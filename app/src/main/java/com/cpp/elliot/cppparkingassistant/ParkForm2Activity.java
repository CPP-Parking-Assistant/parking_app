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

import java.util.List;

public class ParkForm2Activity extends Activity {
    private GoogleMap map;
    private LatLng location;
    private LatLng markerLocation;
    private String description = "";
    /*
    private String description2 = "";
    private String gender;
    private String broncoid;
    */
    private String type;
    private boolean flag = false;
    private final LatLng CPP = new LatLng(34.0564,-117.8217);
    Button parkForm2Button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.parkform2);
        map = ((MapFragment) getFragmentManager().findFragmentById(R.id.parkMap)).getMap();
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(CPP, 15);
        map.animateCamera(update);
        ParseQuery<RideStudent> query1 = ParseQuery.getQuery("RideStudent");
        query1.whereExists("Latitude");
        query1.findInBackground(new FindCallback<RideStudent>() {
            @Override
            public void done(List<RideStudent> list, ParseException e) {
                if (e == null) {
                    if(list.size() == 0){
                        flag = true;
                    }
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
                    if(list.size() == 0 && flag)
                        alert();
                    for(int i = 0; i < list.size(); i++){
                        if(checkDate(list.get(i).getDateAdded())){
                            //do stuff
                        }
                    }
                    for (int i = 0; i < list.size(); i++) {
                        location = new LatLng(list.get(i).getLatitude(), list.get(i).getLongitude());
                        description = list.get(i).getDescription();
                        map.addMarker(new MarkerOptions().position(location).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)).title(description));
                    }
                }
            }
        });
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
        parkForm2Button = (Button) findViewById(R.id.parkForm2Button);
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
                }
                catch(Exception e){
                    Toast.makeText(ParkForm2Activity.this, "Please select a student or a parking spot", Toast.LENGTH_LONG).show();
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
    // 5-20-15
    public boolean checkDate(String date){
        int month = getMonth(date);
        int day = getDay(date);
        int year = getYear(date);
        

        return true;
    }
    public boolean checkTime(String time){


        return true;
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
}
