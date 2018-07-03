package com.eyedog.piececode;

import android.app.Application;

/**
 * created by jw200 at 2018/7/3 15:49
 **/
public class EyeApplication extends Application {

    public static volatile EyeApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }
}
