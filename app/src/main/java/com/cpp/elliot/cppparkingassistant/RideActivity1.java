package com.cpp.elliot.cppparkingassistant;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Timer;
import java.util.TimerTask;

public class RideActivity1 extends ActionBarActivity {
    private LatLng myLocation;
    private LatLng rideLocation;
    private LocationManager lm;
    private GoogleMap map;
    private final LatLng CPP = new LatLng(34.0564,-117.8217);
    private Button rideButton3,locationButton;
    double lat,lng;
    String msg = "Weak Network signal. Try again in a moment";
    String msg2 = "Please give a location";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rideform1);
        setTitle("");
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
                locationButton.setTextColor(getResources().getColor(R.color.darkgreen));
                locationButton.setBackgroundColor(getResources().getColor(R.color.gold));
                final Handler handler = new Handler();
                Timer t = new Timer();
                t.schedule(new TimerTask() {
                    public void run() {
                        handler.post(new Runnable() {
                            public void run() {
                                locationButton.setTextColor(getResources().getColor(R.color.gold));
                                locationButton.setBackgroundColor(getResources().getColor(R.color.darkgreen));
                            }
                        });
                    }
                }, 500);
                map.clear();
                try {
                    map.addMarker(new MarkerOptions().position(myLocation).title("My Location")).showInfoWindow();
                    rideLocation = myLocation;
                } catch (Exception e) {
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
                    rideButton3.setTextColor(getResources().getColor(R.color.darkgreen));
                    rideButton3.setBackgroundColor(getResources().getColor(R.color.gold));
                    final Handler handler = new Handler();
                    Timer t = new Timer();
                    t.schedule(new TimerTask() {
                        public void run() {
                            handler.post(new Runnable() {
                                public void run() {
                                    rideButton3.setTextColor(getResources().getColor(R.color.gold));
                                    rideButton3.setBackgroundColor(getResources().getColor(R.color.darkgreen));
                                }
                            });
                        }
                    }, 500);
                    intent.putExtras(b);
                    startActivity(intent);
                }
                catch(Exception e){
                    Toast.makeText(RideActivity1.this, msg2, Toast.LENGTH_LONG).show();
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
        Intent intent = new Intent(RideActivity1.this,Settings.class);
        startActivity(intent);
    }
    public void goHome(){
        Intent intent = new Intent(RideActivity1.this,ParkingActivity.class);
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
