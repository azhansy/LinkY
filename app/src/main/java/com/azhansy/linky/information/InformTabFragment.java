package com.azhansy.linky.information;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.azhansy.linky.R;
import com.azhansy.linky.base.BaseFragment;

import butterknife.Bind;

/**
 * Created by SHU on 2016/6/27.
 */
public class InformTabFragment extends BaseFragment {
    @Bind(R.id.sliding_tabs)
    TabLayout mTabls;
    @Bind(R.id.viewpager)
    ViewPager mViewpager;
    private InformTabFragmentPagerAdapter adapter;

    public static InformTabFragment getInstance(){
        return new InformTabFragment();
    }
    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_tab_infor;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = new InformTabFragmentPagerAdapter(getFragmentManager());
        adapter.initFragments();
        mViewpager.setAdapter(adapter);
        mTabls.setupWithViewPager(mViewpager);
        mTabls.setTabMode(TabLayout.MODE_FIXED);
    }
}
