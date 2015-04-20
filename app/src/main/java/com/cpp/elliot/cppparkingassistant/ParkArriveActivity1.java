package com.cpp.elliot.cppparkingassistant;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ParkArriveActivity1 extends Activity {
    Button parkArrive1Button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.parkarrive1);
        parkArrive1Button = (Button) findViewById(R.id.parkArrive1Button);
        parkArrive1Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ParkArriveActivity1.this,ThanksActivity1.class);
                startActivity(intent);
            }
         });
    }
}
