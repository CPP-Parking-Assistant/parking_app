package com.cpp.elliot.cppparkingassistant;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LeaveActivity2 extends Activity {
    Button leavingButton2;
    EditText leaveEditText;
    LeavingStudent lStudent = new LeavingStudent();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.leavingform2);
        leaveEditText = (EditText) findViewById(R.id.leaveEditText);
        leavingButton2 = (Button) findViewById(R.id.leavingButton2);
        leavingButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lStudent.setCar(leaveEditText.getText()+"");
                Toast.makeText(LeaveActivity2.this, lStudent.toString(), Toast.LENGTH_LONG).show();
                Intent intent = new Intent(LeaveActivity2.this, ThanksActivity2.class);
                startActivity(intent);
            }
        });
    }
}
