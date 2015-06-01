package com.cpp.elliot.cppparkingassistant;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class ParkForm2Activity extends ActionBarActivity {
    private GoogleMap map;
    private LatLng location;
    private LatLng markerLocation;
    private String description = "";
    private String type;
    private final LatLng CPP = new LatLng(34.0564,-117.8217);
    private Button parkForm2Button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.parkform2);
        setTitle("");
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
                    parkForm2Button.setTextColor(getResources().getColor(R.color.darkgreen));
                    parkForm2Button.setBackgroundColor(getResources().getColor(R.color.gold));
                    final Handler handler = new Handler();
                    Timer t = new Timer();
                    t.schedule(new TimerTask() {
                        public void run() {
                            handler.post(new Runnable() {
                                public void run() {
                                    parkForm2Button.setTextColor(getResources().getColor(R.color.gold));
                                    parkForm2Button.setBackgroundColor(getResources().getColor(R.color.darkgreen));
                                }
                            });
                        }
                    }, 500);
                    intent.putExtras(b);
                    startActivity(intent);
                } catch (Exception e) {
                    Toast.makeText(ParkForm2Activity.this, "Please select a student or a parking spot", Toast.LENGTH_LONG).show();
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
        Intent intent = new Intent(ParkForm2Activity.this,Settings.class);
        startActivity(intent);
    }
    public void goHome(){
        Intent intent = new Intent(ParkForm2Activity.this,ParkingActivity.class);
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
