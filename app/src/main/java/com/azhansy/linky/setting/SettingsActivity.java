package com.azhansy.linky.setting;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.azhansy.linky.R;
import com.azhansy.linky.base.BaseActivity;

/**
 * Created by SHU on 2016/6/27.
 * 设置类
 */
public class SettingsActivity extends BaseActivity {
    @Override
    public int getLayoutResource() {
        return R.layout.activity_of_settings;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public static void launch(Context context){
        Intent intent = new Intent(context, SettingsActivity.class);
        context.startActivity(intent);
    }
}
