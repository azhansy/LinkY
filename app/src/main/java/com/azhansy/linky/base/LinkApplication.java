package com.azhansy.linky.base;

import android.app.Application;
import android.content.Context;

import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.AVObject;
import com.azhansy.linky.rx.RxBus;
import com.azhansy.linky.model.UserModel;
import com.azhansy.linky.utils.SharePreferenceUtil;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

/**
 * Created by 神奇勇士 on 2016/6/15.
 */
public class LinkApplication extends Application {
    private static Context context;
    private static LinkApplication INSTANCE;
    private RefWatcher mRefWatcher;
    private RxBus bus ;
    @Override
    public void onCreate() {
        super.onCreate();
        SharePreferenceUtil.init(this);
        mRefWatcher = LeakCanary.install(this);
        INSTANCE = this;
        bus = new RxBus();
        context = getApplicationContext();
        AVObject.registerSubclass(UserModel.class);
        AVOSCloud.initialize(this, "uIj3m9mKpcIOsJOH65i8xC6v-gzGzoHsz", "eyKl3G9qdrAiU52mmFTgXjjB");
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
