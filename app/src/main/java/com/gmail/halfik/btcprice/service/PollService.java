package com.gmail.halfik.btcprice.service;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.SystemClock;
import android.util.Log;


import com.gmail.halfik.btcprice.model.FetchData;
import com.gmail.halfik.btcprice.model.DataStorage;
import com.gmail.halfik.btcprice.widget.BtcPriceWidget;

import java.util.Map;

public class PollService extends IntentService
{
    public final static String PREF_NAME ="com.gmail.halfik.btcprice.service.PollService";
    public final static String SETTING_SERVICE_TIME = "service_time";

    private static final String TAG ="PollService";

    public static Intent newIntent(Context context){
        return new Intent(context, PollService.class);
    }

    public PollService(){
        super(TAG);
    }

    public  static void setServiceAlarm(Context context, boolean isOn){
        Intent i = PollService.newIntent(context);
        PendingIntent pi = PendingIntent.getService(context, 0, i, 0);
        Log.i(TAG, "setServiceAlarm: " + String.valueOf(isOn));

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        if (isOn){
            if (PollService.isServiceAlarmOn(context)){
                alarmManager.cancel(pi);
            }

            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP , SystemClock.elapsedRealtime(), getInterval(context), pi);
            Log.i(TAG, "Interval: " +  getInterval(context)/(60*1000));
        }else{
            alarmManager.cancel(pi);
            pi.cancel();
        }
    }

    public static SharedPreferences getSharedPreferences (Context context) {
        return context.getSharedPreferences(PREF_NAME, 0);
    }

    private static long getInterval(Context context){
        SharedPreferences settings = PollService.getSharedPreferences(context);
        //return 10 * 1000;
        return (long) settings.getInt(SETTING_SERVICE_TIME, 15) * 60 * 1000;
    }

    public static boolean isServiceAlarmOn(Context context){
        Intent i = PollService.newIntent(context);
        PendingIntent pi = PendingIntent.getService(context, 0, i, PendingIntent.FLAG_NO_CREATE);

        return pi != null;
    }

    @Override
    protected void onHandleIntent(Intent intent){
        if(!isNetworkAvaibleAndConnected()){
            return;
        }

        fetchData();
    }

    private void fetchData(){
        FetchData fd = new FetchData();
        Map<String, String> newData= fd.sync();

        DataStorage.putPrice(this, newData.get("last"));
        DataStorage.setLow(this, newData.get("low"));
        DataStorage.setHigh(this, newData.get("high"));

        Log.i(TAG, "New price: " + newData.get("last"));

        Intent newIntent = new Intent(this, BtcPriceWidget.class);
        newIntent.setAction(BtcPriceWidget.TOGGLE_POLLSERVICE);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, newIntent, 0);
        try{
            pendingIntent.send();
        }catch (Exception e){

        }
    }

    private boolean isNetworkAvaibleAndConnected(){
        ConnectivityManager cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);

        boolean isNetworkAvaible = cm.getActiveNetworkInfo() != null;
        boolean isNetworkConneted = isNetworkAvaible && cm.getActiveNetworkInfo().isConnected();

        return isNetworkConneted;
    }
}
