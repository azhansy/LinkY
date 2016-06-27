package com.azhansy.linky.base;

import android.app.Application;
import android.content.Context;

import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.AVObject;
import com.azhansy.linky.Rx.RxBus;

/**
 * Created by 神奇勇士 on 2016/6/15.
 */
public class LinkApplication extends Application {
    private static Context context;
    private static LinkApplication INSTANCE;
    private RxBus bus ;
    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;
        bus = new RxBus();
        context = getApplicationContext();
        AVOSCloud.initialize(this, "uIj3m9mKpcIOsJOH65i8xC6v-gzGzoHsz", "eyKl3G9qdrAiU52mmFTgXjjB");
//        AVObject testObject = new AVObject("TestObject");
//        testObject.put("foo", "bar");
//        testObject.saveInBackground();
    }
    public RxBus getRxBus(){
        return bus;
    }
    public static Context getContext(){
        return context;
    }

    public static LinkApplication getInstance() {
        return INSTANCE;
    }
}
