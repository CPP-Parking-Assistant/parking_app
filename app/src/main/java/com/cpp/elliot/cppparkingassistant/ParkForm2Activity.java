package com.cpp.elliot.cppparkingassistant;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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
import com.parse.ParseInstallation;
import com.parse.ParseQuery;

import java.util.List;

public class ParkForm2Activity extends Activity {
    private GoogleMap map;
    private LatLng location;
    private LatLng markerLocation;
    private String description = "";
    private String description2 = "";
    private String gender;
    private String type;
    private String broncoid;
    private final LatLng CPP = new LatLng(34.0564,-117.8217);
    Button parkForm2Button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.parkform2);
        Bundle b = getIntent().getExtras();
        description2 = b.getString("description2");
        gender = b.getString("gender");
        broncoid = b.getString("broncoid");
        ParseInstallation installation = ParseInstallation.getCurrentInstallation();
        installation.put("Bronco",broncoid);
        installation.saveInBackground();
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
        map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                markerLocation = marker.getPosition();
                marker.showInfoWindow();
                if(marker.getTitle().contains("Male") || marker.getTitle().contains("Female") || marker.getTitle().contains("Unknown Gender"))
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
                Intent intent = new Intent(ParkForm2Activity.this, ParkArriveActivity1.class);
                Bundle b2 = new Bundle();
                b2.putString("type",type);
                b2.putString("gender", gender);
                b2.putString("bronco",broncoid);
                b2.putString("description2", description2);
                b2.putDouble("latitude", markerLocation.latitude);
                b2.putDouble("longitude",markerLocation.longitude);
                intent.putExtras(b2);
                startActivity(intent);
            }
        });
    }
}
