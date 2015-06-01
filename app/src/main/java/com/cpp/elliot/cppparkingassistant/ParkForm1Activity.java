package com.cpp.elliot.cppparkingassistant;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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

import java.util.Timer;
import java.util.TimerTask;

public class ParkForm1Activity extends ActionBarActivity {
    Button parkForm1Button;
    CheckBox maleCheck,femaleCheck;
    EditText parkForm1EditText;
    EditText broncoEditText3;
    String gender = "";
    String type = "";
    ParkingStudent pStudent = new ParkingStudent();
    LatLng location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.parkform1);
        setTitle("");
        Bundle b = getIntent().getExtras();
        type = b.getString("type");
        location = new LatLng(b.getDouble("latitude"),b.getDouble("longitude"));
        broncoEditText3 = (EditText) findViewById(R.id.broncoEditText3);
        parkForm1EditText = (EditText) findViewById(R.id.parkForm1EditText);
        parkForm1Button = (Button) findViewById(R.id.parkForm1Button);
        maleCheck = (CheckBox) findViewById(R.id.maleCheck);
        femaleCheck = (CheckBox) findViewById(R.id.femaleCheck);
        final ParseInstallation installation = ParseInstallation.getCurrentInstallation();
        if(installation.get("Bronco") != null){
            broncoEditText3.setText(installation.getString("Bronco"));
        }
        if(installation.get("Gender") != null){
            if(installation.getString("Gender").equals("Male")){
                maleCheck.setChecked(true);
            }
            else if(installation.getString("Gender").equals("Female")){
                femaleCheck.setChecked(true);
            }
        }
        parkForm1Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parkForm1Button.setTextColor(getResources().getColor(R.color.darkgreen));
                parkForm1Button.setBackgroundColor(getResources().getColor(R.color.gold));
                final Handler handler = new Handler();
                Timer t = new Timer();
                t.schedule(new TimerTask() {
                    public void run() {
                        handler.post(new Runnable() {
                            public void run() {
                                parkForm1Button.setTextColor(getResources().getColor(R.color.gold));
                                parkForm1Button.setBackgroundColor(getResources().getColor(R.color.darkgreen));
                            }
                        });
                    }
                }, 500);
                if (maleCheck.isChecked() && !femaleCheck.isChecked()) {
                    if(installation.get("Gender") == null)
                        installation.put("Gender", "Male");
                    pStudent.setGender("Male");
                    gender = "Male";
                } else if (!maleCheck.isChecked() && femaleCheck.isChecked()) {
                    if(installation.get("Gender") == null)
                        installation.put("Gender", "Male");
                    pStudent.setGender("Female");
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
                b.putDouble("latitude", location.latitude);
                b.putDouble("longitude", location.longitude);
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
        Intent intent = new Intent(ParkForm1Activity.this,Settings.class);
        startActivity(intent);
    }
    public void goHome(){
        Intent intent = new Intent(ParkForm1Activity.this,ParkingActivity.class);
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