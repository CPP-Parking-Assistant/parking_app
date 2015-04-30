package com.cpp.elliot.cppparkingassistant;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.maps.model.LatLng;

public class LeaveActivity2 extends Activity {
    Button leavingButton2;
    EditText leaveEditText;
    LatLng location;
    double lat,lng;
    LeavingStudent lStudent = new LeavingStudent();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.leavingform2);
        Bundle b = getIntent().getExtras();
        lat = b.getDouble("lat");
        lng = b.getDouble("lng");
        location = new LatLng(lat,lng);
        leaveEditText = (EditText) findViewById(R.id.leaveEditText);
        leavingButton2 = (Button) findViewById(R.id.leavingButton2);
        leavingButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lStudent.setDescription(leaveEditText.getText()+"");
                lStudent.setLocation(location);
                lStudent.saveInBackground();
                Intent intent = new Intent(LeaveActivity2.this, ThanksActivity2.class);
                startActivity(intent);
            }
        });
    }
}
