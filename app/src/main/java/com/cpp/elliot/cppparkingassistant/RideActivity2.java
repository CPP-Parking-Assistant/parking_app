package com.cpp.elliot.cppparkingassistant;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.google.android.gms.maps.model.LatLng;
import com.parse.ParseInstallation;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class RideActivity2 extends Activity {
    Button rideButton2;
    CheckBox maleCheck,femaleCheck;
    EditText rideEditText, broncoEditText;
    RideStudent rStudent = new RideStudent();
    boolean genderChosen = false;
    double lat,lng;
    LatLng location;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rideform2);
        Bundle b = getIntent().getExtras();
        lat = b.getDouble("lat");
        lng = b.getDouble("lng");
        location = new LatLng(lat,lng);
        rideEditText = (EditText) findViewById(R.id.rideEditText);
        broncoEditText = (EditText) findViewById(R.id.broncoEditText);
        rideButton2 = (Button) findViewById(R.id.rideButton2);
        maleCheck = (CheckBox) findViewById(R.id.rideMaleCheck);
        femaleCheck = (CheckBox) findViewById(R.id.rideFemaleCheck);
        ParseInstallation installation = ParseInstallation.getCurrentInstallation();
        if(installation.get("Bronco") != null){
            broncoEditText.setText(installation.getString("Bronco"));
        }
        rideButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (maleCheck.isChecked() && !femaleCheck.isChecked()) {
                    rStudent.setGender("Male");
                    genderChosen = true;
                }
                else if (!maleCheck.isChecked() && femaleCheck.isChecked()) {
                    rStudent.setGender("Female");
                    genderChosen = true;
                }
                else
                    rStudent.setGender("Unknown Gender");
                Calendar c = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yy");
                SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm");
                rStudent.setDateAdded(sdf.format(c.getTime()));
                rStudent.setTimeAdded(sdf2.format(c.getTime()));
                rStudent.setDescription(rideEditText.getText() + "");
                rStudent.setLocation(location);
                rStudent.setBroncoID(broncoEditText.getText() + "");
                rStudent.saveInBackground();
                Bundle b = new Bundle();
                b.putString("broncoId", broncoEditText.getText() + "");
                b.putString("description",rideEditText.getText() + "");
                Intent intent = new Intent(RideActivity2.this, ThanksActivity3.class);
                intent.putExtras(b);
                startActivity(intent);
            }
        });
    }
}
