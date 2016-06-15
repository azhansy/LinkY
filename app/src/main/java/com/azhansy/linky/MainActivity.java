package com.azhansy.linky;


import android.os.Bundle;
import android.support.annotation.Nullable;

import com.azhansy.linky.base.BaseActivity;
import com.azhansy.linky.login.LoginActivity;
import com.azhansy.linky.swipebackhelper.SwipeBackHelper;

import butterknife.OnClick;

public class MainActivity extends BaseActivity {


    @OnClick(R.id.btn)
    void onClick(){
        LoginActivity.launch(this);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SwipeBackHelper.getCurrentPage(this).setSwipeBackEnable(false);
    }

    @Override
    public int getLayoutResource() {
        return R.layout.activity_main;
    }
}
