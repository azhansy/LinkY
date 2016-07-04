package com.azhansy.linky.blog.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.azhansy.linky.R;
import com.azhansy.linky.base.BaseFragment;
import com.azhansy.linky.blog.adapter.BlogFragmentAdapter;

import butterknife.Bind;

/**
 * Created by 神奇勇士 on 2016/6/17.
 * 博客的Fragment
 */
public class BlogFragment extends BaseFragment {
    @Bind(R.id.tab_layout)
    TabLayout mTabls;
    @Bind(R.id.viewpager)
    ViewPager mViewPager;
    @Bind(R.id.toolbar_title)
    TextView mTitle;

    private BlogFragmentAdapter adapter;


    public static BlogFragment getInstance(){
        return new BlogFragment();
    }
    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_blog;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mTitle.setText("技术博客");
        adapter = new BlogFragmentAdapter(getFragmentManager());
        adapter.initFragments();
//        mViewPager.setOffscreenPageLimit(4);
        mViewPager.setAdapter(adapter);
        mTabls.setupWithViewPager(mViewPager);
        mTabls.setTabMode(TabLayout.MODE_SCROLLABLE);
    }
}
