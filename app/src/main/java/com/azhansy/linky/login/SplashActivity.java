package com.azhansy.linky.login;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;

import com.azhansy.linky.ui.MainActivity;
import com.azhansy.linky.R;
import com.azhansy.linky.base.BaseActivity;
import com.azhansy.linky.swipebackhelper.SwipeBackHelper;

/**
 * Created by 神奇勇士 on 2016/6/16.
 */
public class SplashActivity extends BaseActivity {
    /**
     * 页面最小停留的时间
     */
    private static final long RETENTION_TIME = 2000;
    @Override
    public int getLayoutResource() {
        return R.layout.activity_splash;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SwipeBackHelper.getCurrentPage(this).setSwipeBackEnable(false);
        new Handler().postDelayed(() -> {
            MainActivity.launch(this);
            finish();
        }, RETENTION_TIME);
    }
}
