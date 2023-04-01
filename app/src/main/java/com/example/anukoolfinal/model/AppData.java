package com.example.anukoolfinal.model;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

public class AppData {
    public String appName;
    public String appPackageName;

    public AppData(String appName, String appPackageName, String appIconName) {
        this.appName = appName;
        this.appPackageName = appPackageName;
        this.appIconName = appIconName;
    }

    public String appIconName;

}
