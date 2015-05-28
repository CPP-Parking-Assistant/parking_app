package com.cpp.elliot.cppparkingassistant;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.google.android.gms.maps.model.LatLng;
import com.parse.ParseInstallation;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class RideActivity2 extends ActionBarActivity {
    Button rideButton2;
    CheckBox maleCheck,femaleCheck;
    EditText rideEditText, broncoEditText;
    RideStudent rStudent = new RideStudent();
    double lat,lng;
    LatLng location;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rideform2);
        Bundle b = getIntent().getExtras();
        lat = b.getDouble("lat");
        lng = b.getDouble("lng");
        location = new LatLng(lat,lng);
        rideEditText = (EditText) findViewById(R.id.rideEditText);
        broncoEditText = (EditText) findViewById(R.id.broncoEditText);
        rideButton2 = (Button) findViewById(R.id.rideButton2);
        maleCheck = (CheckBox) findViewById(R.id.rideMaleCheck);
        femaleCheck = (CheckBox) findViewById(R.id.rideFemaleCheck);
        final ParseInstallation installation = ParseInstallation.getCurrentInstallation();
        if(installation.get("Bronco") != null){
            broncoEditText.setText(installation.getString("Bronco"));
        }
        if(installation.get("Gender") != null){
            if(installation.getString("Gender").equals("Male")){
                maleCheck.setChecked(true);
            }
            else if(installation.getString("Gender").equals("Female")){
                femaleCheck.setChecked(true);
            }
        }
        rideButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (maleCheck.isChecked() && !femaleCheck.isChecked()) {
                    if(installation.get("Gender") == null)
                        installation.put("Gender", "Male");
                    rStudent.setGender("Male");
                }
                else if (!maleCheck.isChecked() && femaleCheck.isChecked()) {
                    if(installation.get("Gender") == null)
                        installation.put("Gender","Female");
                    rStudent.setGender("Female");
                }
                else
                    rStudent.setGender("Unknown Gender");
                Calendar c = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yy");
                SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm");
                rStudent.setDateAdded(sdf.format(c.getTime()));
                rStudent.setTimeAdded(sdf2.format(c.getTime()));
                rStudent.setDescription(rideEditText.getText() + "");
                rStudent.setLocation(location);
                rStudent.setBroncoID(broncoEditText.getText() + "");
                rStudent.saveInBackground();
                Bundle b = new Bundle();
                b.putString("broncoId", broncoEditText.getText() + "");
                b.putString("description",rideEditText.getText() + "");
                Intent intent = new Intent(RideActivity2.this, ThanksActivity3.class);
                intent.putExtras(b);
                startActivity(intent);
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.parking_activity_actions, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                openSettings();
                return true;
            case R.id.action_home:
                goHome();
                return true;
            case R.id.action_exit:
                exit();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public void openSettings(){
        Intent intent = new Intent(RideActivity2.this,Settings.class);
        startActivity(intent);
    }
    public void goHome(){
        Intent intent = new Intent(RideActivity2.this,ParkingActivity.class);
        startActivity(intent);
    }
    public void exit(){
        this.finish();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
