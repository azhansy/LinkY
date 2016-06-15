package com.azhansy.linky.base;

import android.app.Application;
import android.content.Context;

/**
 * Created by 神奇勇士 on 2016/6/15.
 */
public class LinkApplication extends Application {
    private static Context context;
    private static LinkApplication INSTANCE;
    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;
        context = getApplicationContext();
    }

    public static Context getContext(){
        return context;
    }

    public static LinkApplication getInstance() {
        return INSTANCE;
    }
}
