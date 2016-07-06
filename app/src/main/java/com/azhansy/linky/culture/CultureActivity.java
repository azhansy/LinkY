package com.azhansy.linky.culture;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;

import com.azhansy.linky.R;
import com.azhansy.linky.base.BaseActivity;
import com.azhansy.linky.culture.adapter.CultureFragmentAdapter;
import com.azhansy.linky.view.PagerSlidingIndicator;

import butterknife.Bind;

/**
 * Created by SHU on 2016/7/4.
 * 文化
 */
public class CultureActivity extends BaseActivity {
    @Bind(R.id.viewpager)
    ViewPager viewPager;
    @Bind(R.id.slid_culture)
    PagerSlidingIndicator slidingIndicator;

    @Override
    public int getLayoutResource() {
        return R.layout.activity_culture;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CultureFragmentAdapter adapter = new CultureFragmentAdapter(getSupportFragmentManager());
        if (savedInstanceState == null) {
            adapter.initFragments();
        }else {
            adapter.restoreFragments(savedInstanceState);
        }
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(1);
        slidingIndicator.setViewPager(viewPager);
    }

    public static void launch(Context context){
        Intent intent = new Intent(context, CultureActivity.class);
        context.startActivity(intent);
    }
}
