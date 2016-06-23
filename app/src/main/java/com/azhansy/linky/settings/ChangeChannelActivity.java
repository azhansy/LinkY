package com.azhansy.linky.settings;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;

import com.azhansy.linky.R;
import com.azhansy.linky.base.BaseActivity;

/**
 * Created by SHU on 2016/6/23.
 * 更改栏目
 */
public class ChangeChannelActivity extends BaseActivity {

    @Override
    public int getLayoutResource() {
        return R.layout.activity_change_channel;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mToolbarTitle.setText("更改栏目");
    }

    public static void launch(Context context){
        Intent intent = new Intent(context, ChangeChannelActivity.class);
        context.startActivity(intent);
    }
}
