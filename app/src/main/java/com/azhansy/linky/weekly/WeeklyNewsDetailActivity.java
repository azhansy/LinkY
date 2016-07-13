package com.azhansy.linky.weekly;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.azhansy.linky.R;
import com.azhansy.linky.base.BaseActivity;

/**
 * Created by SHU on 2016/7/13.
 */
public class WeeklyNewsDetailActivity extends BaseActivity {
    private static final String WEEKLYNEWSDETAILACTIVITY="WeeklyNewsDetailActivity";


    @Override
    public int getLayoutResource() {
        return R.layout.activity_base_fragment;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String url = getIntent().getStringExtra(WEEKLYNEWSDETAILACTIVITY);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, WeeklyNewsFragment.getInstance(url)).commit();
    }

    public static void launch(Context context, String url) {
        Intent intent = new Intent(context, WeeklyNewsDetailActivity.class);
        intent.putExtra(WEEKLYNEWSDETAILACTIVITY, url);
        context.startActivity(intent);
    }

}
