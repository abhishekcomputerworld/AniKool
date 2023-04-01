package com.example.anukoolfinal.utility;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;

import com.example.anukoolfinal.model.AppData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AppUtility {

    private List<AppData> f3044e;

     /* for (item in data) {
        if (!sharedPreference.appAdded(item.activityInfo.packageName)) {
            packList.add(
                    AppData(
                            item.loadLabel(context?.packageManager).toString(),
                    item.activityInfo.packageName,
                    ImageUtil.convert(item.loadIcon(context?.packageManager).toBitmap())                    )
                )
        }
    }*/

    private Object f3045f = new Object();

    private AppUtility(Context context) {
        synchronized (this.f3045f) {
            this.f3044e = new ArrayList();
            List<ResolveInfo> b = getApps(context);
            for (int i = 0; i < b.size(); i++) {
                ActivityInfo activityInfo = b.get(i).activityInfo;
                this.f3044e.add(
                        new AppData(
                        b.get(i).loadLabel(context.getPackageManager()).toString(),
                        activityInfo.packageName,
                        null));
            }
        }
    }

    public static synchronized List<ResolveInfo> getApps(Context context) {
        List<ResolveInfo> queryIntentActivities;
        int i = 0;
        int i2 = 0;
        synchronized (AppUtility.class) {
            PackageManager packageManager = context.getPackageManager();
            Intent intent = new Intent("android.intent.action.MAIN", (Uri) null);
            intent.addCategory("android.intent.category.LAUNCHER");
            queryIntentActivities = packageManager.queryIntentActivities(intent, 0);
            Intent intent2 = new Intent("android.intent.action.MAIN", (Uri) null);
            intent2.addCategory("android.intent.category.HOME");
            intent2.addCategory("android.intent.category.DEFAULT");
            List<ResolveInfo> queryIntentActivities2 = packageManager.queryIntentActivities(intent2, 0);
            if (queryIntentActivities2 != null) {
                while (i2 < queryIntentActivities2.size()) {
                    ResolveInfo resolveInfo = queryIntentActivities2.get(i2);
                    if (!(resolveInfo == null || resolveInfo.activityInfo == null || resolveInfo.activityInfo.name == null)) {
                        if (resolveInfo.activityInfo.packageName == null) {
                            i = i2;
                        } else if (resolveInfo.activityInfo.name.equals("com.android.settings.FallbackHome") || resolveInfo.activityInfo.packageName.equals(context.getPackageName())) {
                            queryIntentActivities2.remove(resolveInfo);
                            i = i2 - 1;
                        }
                        i2 = i + 1;
                    }
                    i = i2;
                    // i2 = i + 1;
                }
                queryIntentActivities.addAll(queryIntentActivities2);
            }
            try {
                ResolveInfo resolveInfo2 = new ResolveInfo();
                resolveInfo2.activityInfo = packageManager.getActivityInfo(new ComponentName("com.android.packageinstaller", "com.android.packageinstaller.PackageInstallerActivity"), 0);
                queryIntentActivities.add(resolveInfo2);
            } catch (PackageManager.NameNotFoundException e) {
            }
            try {
                ResolveInfo resolveInfo3 = new ResolveInfo();
                resolveInfo3.activityInfo = packageManager.getActivityInfo(new ComponentName("com.google.android.packageinstaller", "com.android.packageinstaller.PackageInstallerActivity"), 0);
                queryIntentActivities.add(resolveInfo3);
            } catch (PackageManager.NameNotFoundException e2) {
            }
            if (Build.VERSION.SDK_INT > 15 && m3586a(context, "com.sec.android.app.controlpanel")) {
                try {
                    ResolveInfo resolveInfo4 = new ResolveInfo();
                    resolveInfo4.activityInfo = packageManager.getActivityInfo(new ComponentName("com.sec.android.app.controlpanel", "com.sec.android.app.controlpanel.activity.JobManagerActivity"), 0);
                    queryIntentActivities.add(resolveInfo4);
                } catch (PackageManager.NameNotFoundException e3) {
                }
            }
        }
        return  queryIntentActivities;
    }
    public static boolean m3586a(Context context, String str) {
        try {
            context.getPackageManager().getPackageInfo(str, 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

}
