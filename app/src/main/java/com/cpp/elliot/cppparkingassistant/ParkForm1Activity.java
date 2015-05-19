package com.cpp.elliot.cppparkingassistant;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.parse.ParseInstallation;

public class ParkForm1Activity extends Activity {
    Button parkForm1Button;
    CheckBox maleCheck,femaleCheck;
    EditText parkForm1EditText;
    EditText broncoEditText3;
    String gender = "";
    ParkingStudent pStudent = new ParkingStudent();
    boolean genderChosen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.parkform1);
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
                Intent intent = new Intent(ParkForm1Activity.this, ParkForm2Activity.class);
                Bundle b = new Bundle();
                b.putString("gender", gender);
                b.putString("description2", parkForm1EditText.getText() + "");
                b.putString("broncoid", broncoEditText3.getText() + "");
                intent.putExtras(b);
                startActivity(intent);
            }
        });
    }
}
