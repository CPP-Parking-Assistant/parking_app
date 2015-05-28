package com.cpp.elliot.cppparkingassistant;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.util.Log;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class DeleteService extends Service {
    private AsyncTask<Void, Void, Void> task;
    @Override
    public void onCreate(){
        super.onCreate();
        task = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    deleteOvertime();
                }
                catch(Exception e){
                    Log.i("Delete info","nothing to delete");
                }
                return null;
            }
        };
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        task.cancel(true);
        Log.i("Task Cancelled", "True");
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        try {
            task.execute();
            Log.i("Task Executed", "True");
        }
        catch(Exception e){
            Log.i("Delete info","nothing to delete");
        }
        return START_NOT_STICKY;
    }
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    public void deleteOvertime(){
        ParseQuery<RideStudent> query1 = ParseQuery.getQuery("RideStudent");
        query1.whereExists("Latitude");
        query1.findInBackground(new FindCallback<RideStudent>() {
            @Override
            public void done(List<RideStudent> list, ParseException e) {
                if (e == null) {
                    for (int i = 0; i < list.size(); i++) {
                        if (!checkDate(list.get(i).getDateAdded())) {
                            list.get(i).deleteInBackground();
                            list.get(i).saveInBackground();
                        } else if (!checkTime(list.get(i).getTimeAdded())) {
                            list.get(i).deleteInBackground();
                            list.get(i).saveInBackground();
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
                    for (int i = 0; i < list.size(); i++) {
                        if (!checkDate(list.get(i).getDateAdded())) {
                            list.get(i).deleteInBackground();
                            list.get(i).saveInBackground();
                        } else if (!checkTime(list.get(i).getTimeAdded())) {
                            list.get(i).deleteInBackground();
                            list.get(i).saveInBackground();
                        }
                    }
                }
            }
        });
    }
    public boolean checkDate(String date){
        int month = getMonth(date);
        int day = getDay(date);
        int year = getYear(date);
        boolean bool = false;
        if(year == getCurrentYear()){
            if(month == getCurrentMonth()){
                if(day == getCurrentDay()){
                    bool = true;
                }
            }
        }
        return bool;
    }
    public boolean checkTime(String time){
        int hour = getHour(time);
        int minute = getMinute(time);
        boolean bool = false;
        if(hour == getCurrentHour()){
            if(getCurrentMinute()-minute <= 20){
                bool = true;
            }
        }
        else if(getCurrentHour() == hour+1){
            if(getCurrentMinute()-minute+60 <= 20){
                bool = true;
            }
        }
        return bool;
    }
    public int getMonth(String date){
        String str = "";
        for(int i = 0; i < date.length(); i++){
            if(date.charAt(i) == '-')
                break;
            else
                str += date.charAt(i);
        }
        return Integer.parseInt(str);
    }
    public int getDay(String date){
        String str = "";
        int index = date.indexOf('-')+1;
        for(int i = index; i < date.length(); i++){
            if(date.charAt(i) == '-')
                break;
            else
                str += date.charAt(i);
        }
        return Integer.parseInt(str);
    }
    public int getYear(String date){
        String str = "";
        int index = date.lastIndexOf('-')+1;
        for(int i = index; i < date.length(); i++){
            str += date.charAt(i);
        }
        return Integer.parseInt(str);
    }
    public int getHour(String date){
        String str = "";
        int index = date.indexOf(':');
        for(int i = 0; i < index; i++){
            str += date.charAt(i);
        }
        return Integer.parseInt(str);
    }
    public int getMinute(String date){
        String str = "";
        int index = date.indexOf(':')+1;
        for(int i = index; i < date.length(); i++){
            str += date.charAt(i);
        }
        return Integer.parseInt(str);
    }
    public int getCurrentMonth(){
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yy");
        String date = sdf.format(c.getTime());
        return getMonth(date);
    }
    public int getCurrentDay(){
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yy");
        String date = sdf.format(c.getTime());
        return getDay(date);
    }
    public int getCurrentYear(){
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yy");
        String date = sdf.format(c.getTime());
        return getYear(date);
    }
    public int getCurrentHour(){
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        String date = sdf.format(c.getTime());
        return getHour(date);
    }
    public int getCurrentMinute(){
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        String date = sdf.format(c.getTime());
        return getMinute(date);
    }
}
