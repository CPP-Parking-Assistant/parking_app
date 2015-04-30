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
import com.google.android.gms.maps.model.LatLng;

public class ParkForm2Activity extends Activity {
    private GoogleMap map;
    private final LatLng CPP = new LatLng(34.0564,-117.8217);
    Button parkForm2Button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.parkform2);
        map = ((MapFragment) getFragmentManager().findFragmentById(R.id.parkMap)).getMap();
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(CPP,15);
        map.animateCamera(update);
        parkForm2Button = (Button) findViewById(R.id.parkForm2Button);
        parkForm2Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ParkForm2Activity.this,ParkArriveActivity1.class);
                startActivity(intent);
            }
        });
    }
}
