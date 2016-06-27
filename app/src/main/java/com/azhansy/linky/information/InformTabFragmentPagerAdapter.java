package com.azhansy.linky.information;

import android.support.v4.app.FragmentManager;

import com.azhansy.linky.base.BaseFragmentPagerAdapter;
import com.azhansy.linky.information.type.FunnyFragment;
import com.azhansy.linky.information.type.MartailFragment;
import com.azhansy.linky.information.type.SportFragment;

import java.util.ArrayList;

/**
 * Created by SHU on 2016/6/27.
 */
public class InformTabFragmentPagerAdapter extends BaseFragmentPagerAdapter {
    private ArrayList<String> mTitles;
    public InformTabFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
        initTitle();
    }

    private void initTitle() {
        mTitles = new ArrayList<>();
        mTitles.add("娱乐");
        mTitles.add("军事");
        mTitles.add("体育");
    }

    @Override
    public void initFragments() {
        addFragment(FunnyFragment.getInstance());
        addFragment(MartailFragment.getInstance());
        addFragment(SportFragment.getInstance());
    }

    @Override
    protected String getTagPrefix() {
        return "InformTabFragmentPagerAdapter:";
    }

    @Override
    protected int getFragmentCount() {
        return getCount();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles.get(position);
    }
}
