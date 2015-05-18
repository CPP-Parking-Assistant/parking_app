package com.cpp.elliot.cppparkingassistant;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParsePush;
import com.parse.SaveCallback;

public class ThanksActivity2 extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.leavethankyou);
        Bundle b = getIntent().getExtras();
        String broncoId = b.getString("broncoId");
        ParseInstallation installation = ParseInstallation.getCurrentInstallation();
        installation.put("Bronco", broncoId);
        installation.saveInBackground();
        try {
            ParsePush.subscribeInBackground(broncoId, new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    if (e == null)
                        Log.d("com.parse.push", "Successfully subscribed to broncoid");
                    else
                        Log.e("com.parse.push", "failed to subscribe for push notifications", e);
                }
            });
        }
        catch (Exception e){
            Log.e("channel error","couldnt subscribe to channel using broncoID");
        }
    }
}
