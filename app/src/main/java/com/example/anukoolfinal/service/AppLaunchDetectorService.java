package com.example.anukoolfinal.service;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.widget.Toast;

import java.util.List;

public class AppLaunchDetectorService extends AccessibilityService {

    private UsageStatsManager mUsageStatsManager;
    private PackageManager mPackageManager;
    private String mPackageName;

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        Toast.makeText(getApplicationContext(), "service start3", Toast.LENGTH_SHORT).show();
        if (event.getEventType() == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
            String packageName = event.getPackageName().toString();
            if (!packageName.equals(mPackageName)) {
                mPackageName = packageName;
                if (isAppRunning(packageName)) {
                    // App is already running
                    Log.d("AppLaunchDetector", "App is already running: " + packageName);
                } else {
                    // App is not running, launch it
                    Intent launchIntent = mPackageManager.getLaunchIntentForPackage(packageName);
                    if (launchIntent != null) {
                        startActivity(launchIntent);
                    } else {
                        Log.d("AppLaunchDetector", "Unable to launch app: " + packageName);
                    }
                }
            }
        }
    }

    @Override
    public void onInterrupt() {
        // Do nothing
    }

    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
        mUsageStatsManager = (UsageStatsManager) getSystemService(Context.USAGE_STATS_SERVICE);
        mPackageManager = getPackageManager();
        mPackageName = "";
        AccessibilityServiceInfo info = new AccessibilityServiceInfo();
        info.eventTypes = AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED;
        info.feedbackType = AccessibilityServiceInfo.FEEDBACK_GENERIC;
        setServiceInfo(info);
    }

    private boolean isAppRunning(String packageName) {
        long currentTime = System.currentTimeMillis();
        List<UsageStats> stats = mUsageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, currentTime - 1000 * 10, currentTime);
        if (stats == null) {
            return false;
        }
        for (UsageStats usageStats : stats) {
            if (usageStats.getPackageName().equals(packageName)) {
                return true;
            }
        }
        return false;
    }
}
