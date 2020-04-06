package com.digi.datepickerdemo;

import android.app.Application;
import android.content.Context;

public class CalenderApp extends Application {

    public static Context appContext;

    @Override
    public void onCreate() {
        super.onCreate();
        appContext = this;
    }
}
