package com.cpp.elliot.cppparkingassistant;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class RideActivity1 extends Activity {
    private LatLng myLocation;
    private LatLng rideLocation;
    private LocationManager lm;
    private GoogleMap map;
    private final LatLng CPP = new LatLng(34.0564,-117.8217);
    Button rideButton3,locationButton;
    double lat,lng;
    String msg = "Weak Network signal. Try again in a moment";
    String msg2 = "Please give a location";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rideform1);
        map = ((MapFragment) getFragmentManager().findFragmentById(R.id.rideMap)).getMap();
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(CPP, 15);
        map.animateCamera(update);
        map.addMarker(new MarkerOptions().position(CPP).title("CPP")).setVisible(false);
        lm = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        LocationListener ls = new LocationListener() {
            public void onLocationChanged(Location location) {
                myLocation = new LatLng(location.getLatitude(),location.getLongitude());
            }
            public void onStatusChanged(String provider, int status, Bundle extras) {}
            public void onProviderEnabled(String provider) {}
            public void onProviderDisabled(String provider) {}
        };
        lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, ls);
        map.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                map.clear();
                map.addMarker(new MarkerOptions().position(latLng).title("Where I'll Be")).showInfoWindow();
                rideLocation = latLng;
            }
        });
        locationButton = (Button) findViewById(R.id.locationButton);
        locationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                map.clear();
                try {
                    map.addMarker(new MarkerOptions().position(myLocation).title("My Location")).showInfoWindow();
                    rideLocation = myLocation;
                }
                catch(Exception e){
                    Toast.makeText(RideActivity1.this, msg, Toast.LENGTH_LONG).show();
                }
            }
        });
        rideButton3 = (Button) findViewById(R.id.rideButton3);
        rideButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    lat = rideLocation.latitude;
                    lng = rideLocation.longitude;
                    Intent intent = new Intent(RideActivity1.this, RideActivity2.class);
                    Bundle b = new Bundle();
                    b.putDouble("lat", lat);
                    b.putDouble("lng", lng);
                    intent.putExtras(b);
                    startActivity(intent);
                }
                catch(Exception e){
                    Toast.makeText(RideActivity1.this, msg2, Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
