package com.cpp.elliot.cppparkingassistant;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class ParkingActivity extends Activity {
    Button parkButton, rideButton, leaveButton, parkForm1Button;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_page);
        parkButton = (Button) findViewById(R.id.parkButton);
        rideButton = (Button) findViewById(R.id.rideButton);
        leaveButton = (Button) findViewById(R.id.leaveButton);
        parkForm1Button = (Button) findViewById(R.id.parkForm1Button);

        parkButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ParkingActivity.this, ParkForm1Activity.class);
                startActivity(intent);
            }
        });
        rideButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ParkingActivity.this, "Ride Button!", Toast.LENGTH_SHORT).show();
            }
        });
        leaveButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(ParkingActivity.this, LeaveActivity.class);
                startActivity(intent);
            }
        });
    }
}
