package com.azhansy.linky.information;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.azhansy.linky.R;
import com.azhansy.linky.base.BaseActivity;

/**
 * Created by SHU on 2016/7/4.
 * 资讯
 */
public class InformActivity extends BaseActivity {
    @Override
    public int getLayoutResource() {
        return R.layout.activity_base_fragment;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, InformTabFragment.getInstance()).commit();
    }

    public static void launch(Context context){
        Intent intent = new Intent(context, InformActivity.class);
        context.startActivity(intent);
    }
}
