package com.example.android.playitloudapp;

import android.app.Application;
import android.content.Context;

/**
 * Created by anu on 23/5/17.
 */

public class MyApplication extends Application {

    public static Context sMyApplicationContext;
    @Override
    public void onCreate() {
        super.onCreate();
        sMyApplicationContext = this;
    }
}
