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

public class ParkForm1Activity extends Activity {
    Button parkForm1Button;
    CheckBox maleCheck,femaleCheck;
    EditText parkForm1EditText;
    EditText broncoEditText3;
    String gender = "";
    String type = "";
    ParkingStudent pStudent = new ParkingStudent();
    LatLng location;
    boolean genderChosen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.parkform1);
        Bundle b = getIntent().getExtras();
        type = b.getString("type");
        location = new LatLng(b.getDouble("latitude"),b.getDouble("longitude"));
        broncoEditText3 = (EditText) findViewById(R.id.broncoEditText3);
        parkForm1EditText = (EditText) findViewById(R.id.parkForm1EditText);
        parkForm1Button = (Button) findViewById(R.id.parkForm1Button);
        maleCheck = (CheckBox) findViewById(R.id.maleCheck);
        femaleCheck = (CheckBox) findViewById(R.id.femaleCheck);
        ParseInstallation installation = ParseInstallation.getCurrentInstallation();
        if(installation.get("Bronco") != null){
            broncoEditText3.setText(installation.getString("Bronco"));
        }
        parkForm1Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (maleCheck.isChecked() && !femaleCheck.isChecked()) {
                    pStudent.setGender("Male");
                    genderChosen = true;
                    gender = "Male";
                } else if (!maleCheck.isChecked() && femaleCheck.isChecked()) {
                    pStudent.setGender("Female");
                    genderChosen = true;
                    gender = "Female";
                } else
                    pStudent.setGender("Unknown Gender");
                pStudent.setBroncoId(broncoEditText3.getText() + "");
                pStudent.setDescription(parkForm1EditText.getText() + "");
                pStudent.saveInBackground();
                ParseInstallation installation = ParseInstallation.getCurrentInstallation();
                if(installation.get("Bronco") == null) {
                    installation.put("Bronco", broncoEditText3.getText() + "");
                    installation.put("description", parkForm1EditText.getText() + "");
                    installation.saveInBackground();
                }
                Intent intent = new Intent(ParkForm1Activity.this, ParkArriveActivity1.class);
                Bundle b = new Bundle();
                b.putString("type",type);
                b.putString("gender", gender);
                b.putString("description2", parkForm1EditText.getText() + "");
                b.putDouble("latitude",location.latitude);
                b.putDouble("longitude",location.longitude);
                intent.putExtras(b);
                startActivity(intent);
            }
        });
    }
}
