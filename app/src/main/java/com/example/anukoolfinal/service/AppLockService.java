package com.example.anukoolfinal.service;

import android.app.ActivityManager;
import android.app.Service;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.IBinder;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.anukoolfinal.App;
import com.example.anukoolfinal.LockActivity;
import com.example.anukoolfinal.receiver.AirplaneModeChangeReceiver;
import com.example.anukoolfinal.receiver.MyReceiver;
import com.example.anukoolfinal.utility.Preference;

import java.util.ArrayList;
import java.util.List;

public class AppLockService extends Service {

    private Handler handler;
    private Runnable runnable;

    @Override
    public void onCreate() {
        super.onCreate();
        // sendBroadcast(new Intent(this, MyReceiver.class).setAction("com.example.custombroadcastreceiver.ACTION"));

        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                checkRunningApp();
                handler.postDelayed(this, 1000);
            }
        };
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        handler.postDelayed(runnable, 1000);
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(runnable);
    }

    private void checkRunningApp() {
        Toast.makeText(getApplicationContext(), "service start", Toast.LENGTH_SHORT).show();

  /*     UsageStatsManager usageStatsManager = (UsageStatsManager) getSystemService(Context.USAGE_STATS_SERVICE);
        long endTime = System.currentTimeMillis();
        long startTime = endTime - 10000; // 10 seconds ago
        List<UsageStats> usageStatsList = usageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, startTime, endTime);

        for (UsageStats usageStats : usageStatsList) {
            if (!usageStats.getPackageName().equals("com.example.anukoolfinal")) {
                // App is currently running, show lock screen

                Intent i = new Intent(this, LockActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                break;
            }
        }*/


       // sendBroadcast(new Intent(this, MyReceiver.class).setAction("com.example.custombroadcastreceiver.ACTION"));

        ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);
        String topActivityPackageName = taskInfo.get(0).topActivity.getPackageName();
        if (!topActivityPackageName.equals("com.example.anukoolfinal")) {
           // Toast.makeText(getApplicationContext(), "service start2", Toast.LENGTH_SHORT).show();
            // App is currently running, show lock screen
           Intent intent = new Intent(App.getContext(), LockActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            App.getContext().startActivity(intent);

        }
    }




    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }



  /*  @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(getApplicationContext(), "onStartCommand ", Toast.LENGTH_SHORT).show();

        ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);
        String topActivityPackageName = taskInfo.get(0).topActivity.getPackageName();

        AirplaneModeChangeReceiver airplaneModeChangeReceiver = new AirplaneModeChangeReceiver();
        IntentFilter filter = new IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED);
        registerReceiver(airplaneModeChangeReceiver, filter);


        if (*//*topActivityPackageName.equals()*//*new Preference(getBaseContext()).appAdded(topActivityPackageName)) {
            Toast.makeText(getBaseContext(),"jai ho kam kiya",1);
            // App is launched
        } else {

            Toast.makeText(getBaseContext(),"jai ho kam  nahi kiya",1);
            // App is not launched
        }
        return START_STICKY;
    }

*/
}
