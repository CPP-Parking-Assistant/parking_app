package com.cpp.elliot.cppparkingassistant;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ParkArriveActivity2 extends Activity {
    Button parkArrive2Button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.parkarrive2);
        parkArrive2Button = (Button) findViewById(R.id.parkArrive2Button);
        parkArrive2Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ParkArriveActivity2.this,ThanksActivity1.class);
                startActivity(intent);
            }
        });
    }
}
