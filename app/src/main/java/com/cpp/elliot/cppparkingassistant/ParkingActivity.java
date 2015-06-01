package com.cpp.elliot.cppparkingassistant;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.util.Timer;
import java.util.TimerTask;


public class ParkingActivity extends ActionBarActivity {
    Button parkButton, rideButton, leaveButton;
    Intent serviceIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_page);
        setTitle("");
        serviceIntent = new Intent(ParkingActivity.this, DeleteService.class);
        startService(serviceIntent);
        parkButton = (Button) findViewById(R.id.parkButton);
        rideButton = (Button) findViewById(R.id.rideButton);
        leaveButton = (Button) findViewById(R.id.leaveButton);
        parkButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                parkButton.setTextColor(getResources().getColor(R.color.darkgreen));
                parkButton.setBackgroundColor(getResources().getColor(R.color.gold));
                final Handler handler = new Handler();
                Timer t = new Timer();
                t.schedule(new TimerTask() {
                    public void run() {
                        handler.post(new Runnable() {
                            public void run() {
                                parkButton.setTextColor(getResources().getColor(R.color.gold));
                                parkButton.setBackgroundColor(getResources().getColor(R.color.darkgreen));
                            }
                        });
                    }
                }, 500);
                Intent intent = new Intent(ParkingActivity.this, ParkForm2Activity.class);
                stopService(serviceIntent);
                startActivity(intent);
            }
        });
        rideButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rideButton.setTextColor(getResources().getColor(R.color.darkgreen));
                rideButton.setBackgroundColor(getResources().getColor(R.color.gold));
                final Handler handler = new Handler();
                Timer t = new Timer();
                t.schedule(new TimerTask() {
                    public void run() {
                        handler.post(new Runnable() {
                            public void run() {
                                rideButton.setTextColor(getResources().getColor(R.color.gold));
                                rideButton.setBackgroundColor(getResources().getColor(R.color.darkgreen));
                            }
                        });
                    }
                }, 500);
                Intent intent = new Intent(ParkingActivity.this,RideActivity1.class);
                stopService(serviceIntent);
                startActivity(intent);

            }
        });
        leaveButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                leaveButton.setTextColor(getResources().getColor(R.color.darkgreen));
                leaveButton.setBackgroundColor(getResources().getColor(R.color.gold));
                final Handler handler = new Handler();
                Timer t = new Timer();
                t.schedule(new TimerTask() {
                    public void run() {
                        handler.post(new Runnable() {
                            public void run() {
                                leaveButton.setTextColor(getResources().getColor(R.color.gold));
                                leaveButton.setBackgroundColor(getResources().getColor(R.color.darkgreen));
                            }
                        });
                    }
                }, 500);
                Intent intent = new Intent(ParkingActivity.this, LeaveActivity.class);
                stopService(serviceIntent);
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
        Intent intent = new Intent(ParkingActivity.this,Settings.class);
        startActivity(intent);
    }
    public void goHome(){
        Log.i("Home", "Already home");
    }
    public void exit(){
        this.finish();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
