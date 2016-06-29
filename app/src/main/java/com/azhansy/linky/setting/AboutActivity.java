package com.azhansy.linky.setting;

import android.content.Context;
import android.content.Intent;

import com.azhansy.linky.R;
import com.azhansy.linky.base.BaseActivity;

/**
 * Created by SHU on 2016/6/29.
 */
public class AboutActivity extends BaseActivity {

    @Override
    public int getLayoutResource() {
        return R.layout.activity_me_about;
    }

    public static void launch(Context context){
        Intent intent = new Intent(context, AboutActivity.class);
        context.startActivity(intent);
    }
}
