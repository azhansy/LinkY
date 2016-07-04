package com.azhansy.linky.culture.adapter;

import android.support.v4.app.FragmentManager;

import com.azhansy.linky.base.BaseFragmentPagerAdapter;
import com.azhansy.linky.culture.ChengyuFragment;
import com.azhansy.linky.culture.MingrenFragment;

/**
 * Created by SHU on 2016/7/4.
 */
public class CultureFragmentAdapter extends BaseFragmentPagerAdapter {
    public CultureFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public void initFragments() {
        addFragment(ChengyuFragment.getInstance());
        addFragment(MingrenFragment.getInstance());
    }

    @Override
    protected String getTagPrefix() {
        return "CultureFragmentAdapter:";
    }

    @Override
    protected int getFragmentCount() {
        return 2;
    }
}
