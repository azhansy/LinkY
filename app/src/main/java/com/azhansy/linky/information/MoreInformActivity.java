package com.azhansy.linky.information;

import android.content.Context;
import android.content.Intent;

import com.azhansy.linky.R;
import com.azhansy.linky.base.BaseActivity;

/**
 * Created by SHU on 2016/7/4.
 * 更多资讯
 */
public class MoreInformActivity extends BaseActivity {
    @Override
    public int getLayoutResource() {
        return R.layout.fragment_base;
    }

    public static void launch(Context context){
        Intent intent = new Intent(context, MoreInformActivity.class);
        context.startActivity(intent);
    }
}
