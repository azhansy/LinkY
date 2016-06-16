package com.azhansy.linky;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.azhansy.linky.base.BaseActivity;
import com.azhansy.linky.login.LoginActivity;
import com.azhansy.linky.swipebackhelper.SwipeBackHelper;

import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @Override
    public int getLayoutResource() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SwipeBackHelper.getCurrentPage(this).setSwipeBackEnable(false);
    }

    public static void launch(Context context){
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

}