package com.cpp.elliot.cppparkingassistant;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class RideActivity2 extends Activity {
    Button rideButton2;
    CheckBox maleCheck,femaleCheck;
    EditText rideEditText;
    RideStudent rStudent = new RideStudent();
    boolean genderChosen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rideform2);
        rideEditText = (EditText) findViewById(R.id.rideEditText);
        rideButton2 = (Button) findViewById(R.id.rideButton2);
        maleCheck = (CheckBox) findViewById(R.id.rideMaleCheck);
        femaleCheck = (CheckBox) findViewById(R.id.rideFemaleCheck);
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
                rStudent.setDescription(rideEditText.getText()+"");
                Toast.makeText(RideActivity2.this, rStudent.toString(), Toast.LENGTH_LONG).show();
                Intent intent = new Intent(RideActivity2.this, ThanksActivity3.class);
                startActivity(intent);
            }
        });
    }
}
