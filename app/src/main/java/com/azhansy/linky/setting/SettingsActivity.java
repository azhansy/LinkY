package com.azhansy.linky.setting;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.azhansy.linky.R;
import com.azhansy.linky.base.BaseActivity;
import com.azhansy.linky.login.LoginActivity;
import com.azhansy.linky.rx.RxBus;
import com.azhansy.linky.ui.MainActivity;
import com.azhansy.linky.utils.AppManager;
import com.azhansy.linky.utils.DataCleanManagerUtil;
import com.azhansy.linky.utils.Logger;
import com.azhansy.linky.utils.ToastUtil;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by SHU on 2016/6/27.
 * 设置类
 */
public class SettingsActivity extends BaseActivity {
    @Bind(R.id.tv_cache_size)
    TextView mCacheSize;
    @Bind(R.id.scroll)
    NestedScrollView scroll;
    @Bind(R.id.btn_exit)
    TextView btnExit;
    @Bind(R.id.btn_cancel)
    TextView btnCancel;

    @Override
    public int getLayoutResource() {
        return R.layout.activity_of_settings;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            dataCache = DataCleanManagerUtil.getTotalCacheSize(this);
            mCacheSize.setText(dataCache);
        } catch (Exception e) {
            e.printStackTrace();
        }
        initClick();
    }

    private void initClick() {
    }

    @OnClick({R.id.btn_exit, R.id.btn_clear_cache, R.id.btn_check_update, R.id.btn_about})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_exit:
                onExitLogin();
//                AlertDialog d = new AlertDialog.Builder(this)
//                        .setTitle("确定退出？")
//                        .setPositiveButton(getString(R.string.ok), (dialog1, which) -> {
//                            LoginActivity.launch(this);
//                            finish();
//                            AppManager.getAppManager().finishActivity(MainActivity.class);
//                        })
//                        .setNegativeButton(getString(R.string.cancel), null)
//                        .create();
//                d.show();
                break;
            case R.id.btn_clear_cache:
                AlertDialog dialog = new AlertDialog.Builder(this)
                        .setTitle("确定清除大小为 " + dataCache + " 的缓存？")
                        .setPositiveButton("确定", (dialog1, which) -> {
                            DataCleanManagerUtil.cleanTotalCacheSize(this);
                            ToastUtil.showToast(this, "清除完成");
                            dataCache = "0K";
                            mCacheSize.setText(dataCache);
                        })
                        .setNegativeButton("取消", null)
                        .create();
                dialog.show();
                break;
            case R.id.btn_check_update:
                AlertDialog dial = new AlertDialog.Builder(this)
                        .setTitle("暂时没有更新的哦")
                        .setPositiveButton("确定", (dialog1, which) -> {
                            DataCleanManagerUtil.cleanSharedPreference(SettingsActivity.this);
                        })
                        .setNegativeButton("取消", null)
                        .create();
                dial.show();
                break;
            case R.id.btn_about:
                AboutActivity.launch(this);
                break;

        }
    }

    private void onExitLogin() {
        onClickBottomSheet(scroll);
    }

    private void onClickBottomSheet(View view) {
//        BottomSheetDialog dialog = new BottomSheetDialog(this);
//        dialog.setContentView(R.layout.exit_item);
//        dialog.show();
        BottomSheetBehavior behavior = BottomSheetBehavior.from(scroll);
        if (behavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
            behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        } else {
            behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        }
        behavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                Logger.d("azhansy","onStateChanged....");
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                Logger.d("azhansy","onSlide....");
            }
        });
    }

    @OnClick(R.id.btn_test)
    void onClick() {
        TestActivity.launch(this);
    }

    private String dataCache = "0K";


    public static void launch(Context context) {
        Intent intent = new Intent(context, SettingsActivity.class);
        context.startActivity(intent);
    }
}
