package com.cpp.elliot.cppparkingassistant;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParsePush;
import com.parse.SaveCallback;

public class ThanksActivity3 extends ActionBarActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ridethankyou);
        Bundle b = getIntent().getExtras();
        String broncoId = b.getString("broncoId");
        String description = b.getString("description");
        ParseInstallation installation = ParseInstallation.getCurrentInstallation();
        installation.put("Bronco", broncoId);
        installation.put("description",description);
        installation.saveInBackground();
        try {
            ParsePush.subscribeInBackground(broncoId, new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    if (e == null)
                        Log.d("com.parse.push", "Successfully subscribed to broncoid");
                    else
                        Log.e("com.parse.push", "failed to subscribe", e);
                }
            });
        }
        catch (Exception e){
            Log.e("channel error","couldn't subscribe to channel using broncoID");
        }
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
        Intent intent = new Intent(ThanksActivity3.this,Settings.class);
        startActivity(intent);
    }
    public void goHome(){
        Intent intent = new Intent(ThanksActivity3.this,ParkingActivity.class);
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
