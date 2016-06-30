package com.azhansy.linky.setting;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;

import com.azhansy.linky.R;
import com.azhansy.linky.base.BaseActivity;
import com.azhansy.linky.login.LoginActivity;
import com.azhansy.linky.utils.DataCleanManagerUtil;
import com.azhansy.linky.utils.ToastUtil;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by SHU on 2016/6/27.
 * 设置类
 */
public class SettingsActivity extends BaseActivity {
    @OnClick({R.id.btn_exit, R.id.btn_clear_cache, R.id.btn_check_update, R.id.btn_about})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_exit:
                LoginActivity.launch(this);
                break;
            case R.id.btn_clear_cache:
                try {
                    String data = DataCleanManagerUtil.getTotalCacheSize(this);

                    AlertDialog dialog = new AlertDialog.Builder(this)
                            .setTitle("确定清除大小为 " + data + " 的缓存？")
                            .setPositiveButton("确定", (dialog1, which) -> {
                                DataCleanManagerUtil.cleanSharedPreference(SettingsActivity.this);
                                ToastUtil.showToast(this, "清除完成");
                            })
                            .setNegativeButton("取消", null)
                            .create();
                    dialog.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
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

    @Override
    public int getLayoutResource() {
        return R.layout.activity_of_settings;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public static void launch(Context context) {
        Intent intent = new Intent(context, SettingsActivity.class);
        context.startActivity(intent);
    }
}
