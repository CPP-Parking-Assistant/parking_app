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
import android.widget.EditText;

import com.google.android.gms.maps.model.LatLng;
import com.parse.ParseInstallation;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

public class LeaveActivity2 extends ActionBarActivity {
    Button leavingButton2;
    EditText leaveEditText,broncoEditText2;
    LatLng location;
    double lat,lng;
    LeavingStudent lStudent = new LeavingStudent();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.leavingform2);
        setTitle("");
        Bundle b = getIntent().getExtras();
        lat = b.getDouble("lat");
        lng = b.getDouble("lng");
        location = new LatLng(lat,lng);
        leaveEditText = (EditText) findViewById(R.id.leaveEditText);
        broncoEditText2 = (EditText) findViewById(R.id.broncoEditText2);
        leavingButton2 = (Button) findViewById(R.id.leavingButton2);
        ParseInstallation installation = ParseInstallation.getCurrentInstallation();
        if(installation.get("Bronco") != null){
            broncoEditText2.setText(installation.getString("Bronco"));
        }
        leavingButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                leavingButton2.setTextColor(getResources().getColor(R.color.darkgreen));
                leavingButton2.setBackgroundColor(getResources().getColor(R.color.gold));
                final Handler handler = new Handler();
                Timer t = new Timer();
                t.schedule(new TimerTask() {
                    public void run() {
                        handler.post(new Runnable() {
                            public void run() {
                                leavingButton2.setTextColor(getResources().getColor(R.color.gold));
                                leavingButton2.setBackgroundColor(getResources().getColor(R.color.darkgreen));
                            }
                        });
                    }
                }, 500);
                Calendar c = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yy");
                SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm");
                lStudent.setDateAdded(sdf.format(c.getTime()));
                lStudent.setTimeAdded(sdf2.format(c.getTime()));
                lStudent.setDescription(leaveEditText.getText() + "");
                lStudent.setLocation(location);
                lStudent.setBroncoID(broncoEditText2.getText() + "");
                lStudent.saveInBackground();
                Bundle b = new Bundle();
                b.putString("broncoId", broncoEditText2.getText() + "");
                b.putString("description", leaveEditText.getText() + "");
                Intent intent = new Intent(LeaveActivity2.this, ThanksActivity2.class);
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
        Intent intent = new Intent(LeaveActivity2.this,Settings.class);
        startActivity(intent);
    }
    public void goHome(){
        Intent intent = new Intent(LeaveActivity2.this,ParkingActivity.class);
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
