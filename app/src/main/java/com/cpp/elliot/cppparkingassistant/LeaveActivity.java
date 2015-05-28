package com.cpp.elliot.cppparkingassistant;

import android.content.Intent;
import android.os.Bundle;
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

public class LeaveActivity extends ActionBarActivity {
    private GoogleMap map;
    private final LatLng CPP = new LatLng(34.0564,-117.8217);
    Button leavingButton;
    LatLng location;
    double lat,lng;
    String msg = "Please give a location";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.leavingform);
        map = ((MapFragment) getFragmentManager().findFragmentById(R.id.leaveMap)).getMap();
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(CPP, 15);
        map.animateCamera(update);
        map.addMarker(new MarkerOptions().position(CPP).title("CPP")).setVisible(false);
        map.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                map.clear();
                map.addMarker(new MarkerOptions().position(latLng).title("My Parking Location")).showInfoWindow();
                location = latLng;
            }
        });
        leavingButton = (Button) findViewById(R.id.leavingButton);
        leavingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    lat = location.latitude;
                    lng = location.longitude;
                    Intent intent = new Intent(LeaveActivity.this, LeaveActivity2.class);
                    Bundle b = new Bundle();
                    b.putDouble("lat", lat);
                    b.putDouble("lng", lng);
                    intent.putExtras(b);
                    startActivity(intent);
                }
                catch(Exception e){
                    Toast.makeText(LeaveActivity.this, msg, Toast.LENGTH_LONG).show();
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
        Intent intent = new Intent(LeaveActivity.this,Settings.class);
        startActivity(intent);
    }
    public void goHome(){
        Intent intent = new Intent(LeaveActivity.this,ParkingActivity.class);
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
