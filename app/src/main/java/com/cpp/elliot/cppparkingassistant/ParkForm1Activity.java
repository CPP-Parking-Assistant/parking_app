package com.cpp.elliot.cppparkingassistant;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class ParkForm1Activity extends Activity {
    Button parkForm1Button;
    CheckBox maleCheck,femaleCheck;
    EditText parkForm1EditText;
    ParkingStudent pStudent = new ParkingStudent();
    boolean genderChosen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.parkform1);
        parkForm1EditText = (EditText) findViewById(R.id.parkForm1EditText);
        parkForm1Button = (Button) findViewById(R.id.parkForm1Button);
        maleCheck = (CheckBox) findViewById(R.id.maleCheck);
        femaleCheck = (CheckBox) findViewById(R.id.femaleCheck);
        parkForm1Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (maleCheck.isChecked() && !femaleCheck.isChecked()) {
                    pStudent.setGender("Male");
                    genderChosen = true;
                }
                else if (!maleCheck.isChecked() && femaleCheck.isChecked()) {
                    pStudent.setGender("Female");
                    genderChosen = true;
                }
                else
                    pStudent.setGender("Unknown Gender");
                pStudent.setDescription(parkForm1EditText.getText()+"");
                Toast.makeText(ParkForm1Activity.this, pStudent.toString(), Toast.LENGTH_LONG).show();
                Intent intent = new Intent(ParkForm1Activity.this, ParkForm2Activity.class);
                startActivity(intent);
            }
        });
    }
}
