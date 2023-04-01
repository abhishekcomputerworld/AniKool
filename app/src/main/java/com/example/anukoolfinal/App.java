package com.example.anukoolfinal;

import android.app.Application;
import android.content.Context;

public class App extends Application {

    protected static Context context = null;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    public static Context getContext() {
        return context;
    }

}