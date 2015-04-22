package com.cpp.elliot.cppparkingassistant;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class RideActivity1 extends Activity {
    private LatLng myLocation;
    private Location location;
    private GoogleMap map;
    private final LatLng CPP = new LatLng(34.0564,-117.8217);
    Button rideButton3,locationButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rideform1);
        map = ((MapFragment) getFragmentManager().findFragmentById(R.id.rideMap)).getMap();
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(CPP, 15);
        map.animateCamera(update);
        map.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                map.addMarker(new MarkerOptions().position(latLng).title("My Location"));
            }
        });
        locationButton = (Button) findViewById(R.id.locationButton);
        locationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                location = map.getMyLocation();
                myLocation = new LatLng(location.getLatitude(),location.getLongitude());
                map.addMarker(new MarkerOptions().position(myLocation).title("My Location"));
            }
        });
        rideButton3 = (Button) findViewById(R.id.rideButton3);
        rideButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RideActivity1.this,RideActivity2.class);
                startActivity(intent);
            }
        });
    }
}
