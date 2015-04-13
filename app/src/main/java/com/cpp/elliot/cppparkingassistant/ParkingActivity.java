package com.cpp.elliot.cppparkingassistant;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class ParkingActivity extends Activity {
    Button parkButton, rideButton;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        gotoMain();
        parkButton = (Button) findViewById(R.id.parkButton);
        rideButton = (Button) findViewById(R.id.rideButton);
        parkButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Toast.makeText(ParkingActivity.this, "Park Button!", Toast.LENGTH_SHORT).show();
                gotoLogin();
            }
        });
        rideButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ParkingActivity.this, "Ride Button!", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void gotoMain() { setContentView(R.layout.main_page); }
    private void gotoLogin(){ setContentView(R.layout.login); }
}
