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
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Settings extends ActionBarActivity {
    final private String broncoId = ParseInstallation.getCurrentInstallation().getString("Bronco");
    private Button deleteButton;
    private boolean found = false;
    ArrayList<String> id = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
        deleteButton = (Button) findViewById(R.id.DeleteButton);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("delete button", "pressed");
                deleteButton.setTextColor(getResources().getColor(R.color.gold));
                deleteButton.setBackgroundColor(getResources().getColor(R.color.darkgreen));
                final Handler handler = new Handler();
                Timer t = new Timer();
                t.schedule(new TimerTask() {
                    public void run() {
                        handler.post(new Runnable() {
                            public void run() {
                                deleteButton.setTextColor(getResources().getColor(R.color.darkgreen));
                                deleteButton.setBackgroundColor(getResources().getColor(R.color.gold));
                            }
                        });
                    }
                }, 500);
                checkFail();
                ParseQuery<RideStudent> query1 = ParseQuery.getQuery("RideStudent");
                query1.whereExists("Latitude");
                query1.findInBackground(new FindCallback<RideStudent>() {
                    @Override
                    public void done(List<RideStudent> list, ParseException e) {
                        if (e == null) {
                            for (int i = 0; i < list.size(); i++) {
                                if (list.get(i).getBroncoId().equals(broncoId)) {
                                    list.get(i).deleteInBackground();
                                    list.get(i).saveInBackground();
                                    Toast.makeText(Settings.this, "Delete Successful", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    }
                });
                ParseQuery<LeavingStudent> query2 = ParseQuery.getQuery("LeavingStudent");
                query2.whereExists("Latitude");
                query2.findInBackground(new FindCallback<LeavingStudent>() {
                    @Override
                    public void done(List<LeavingStudent> list, ParseException e) {
                        if (e == null) {
                            checkFail();
                            for (int i = 0; i < list.size(); i++) {
                                if (list.get(i).getBroncoId().equals(broncoId)) {
                                    list.get(i).deleteInBackground();
                                    list.get(i).saveInBackground();
                                    Toast.makeText(Settings.this, "Delete Successful", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    }
                });
            }
        });
    }
    public void checkFail(){
        ParseQuery<RideStudent> query1 = ParseQuery.getQuery("RideStudent");
        query1.whereExists("Latitude");
        query1.findInBackground(new FindCallback<RideStudent>() {
            @Override
            public void done(List<RideStudent> list, ParseException e) {
                if (e == null) {
                    for(int i = 0; i < list.size(); i++){
                        id.add(list.get(i).getBroncoId());
                    }
                    ParseQuery<LeavingStudent> query2 = ParseQuery.getQuery("LeavingStudent");
                    query2.whereExists("Latitude");
                    query2.findInBackground(new FindCallback<LeavingStudent>() {
                        @Override
                        public void done(List<LeavingStudent> list, ParseException e) {
                            if (e == null) {
                                for(int i = 0; i < list.size(); i++){
                                    id.add(list.get(i).getBroncoId());
                                }
                                if(!id.contains(broncoId)){
                                    Toast.makeText(Settings.this, "Delete Unuccessful", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    });
                }
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
        Log.i("Home", "Already in settings");
    }
    public void goHome(){
        Intent intent = new Intent(Settings.this,ParkingActivity.class);
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
