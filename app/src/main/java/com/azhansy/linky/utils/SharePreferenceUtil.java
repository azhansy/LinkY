package com.azhansy.linky.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.azhansy.linky.base.BaseActivity;

/**
 * Created by SHU on 2016/6/25.
 */
public class SharePreferenceUtil {
    static SharedPreferences prefs;

    public static void init(Context context) {
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
    }
    private SharePreferenceUtil() {}
    public static final String SHARED_PREFERENCE_NAME = "link_y";
    public static final String SAVED_CHANNEL = "saved_channel";

    public static boolean isNight() {
        return prefs.getBoolean("isNight", false);
    }
    public static void setNight(Context context, boolean isNight) {
        prefs.edit().putBoolean("isNight", isNight).apply();
        if (context instanceof BaseActivity)
            ((BaseActivity) context).reload();
    }

    public static String getCityName(){
        return prefs.getString("cityName", "湛江");
    }

    public static void setCityName(String name){
        prefs.edit().putString("cityName", name).apply();
    }
}
