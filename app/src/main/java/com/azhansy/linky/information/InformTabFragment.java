package com.azhansy.linky.information;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

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
    @Bind(R.id.toolbar_title)
    TextView mTitle;
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
        setHasOptionsMenu(true);
        mTitle.setText("日常资讯");
        adapter = new InformTabFragmentPagerAdapter(getFragmentManager());
        adapter.initFragments();
//        mViewpager.setOffscreenPageLimit(2);
        mViewpager.setAdapter(adapter);
        mTabls.setupWithViewPager(mViewpager);
        mTabls.setTabMode(TabLayout.MODE_FIXED);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        MenuItem item = menu.add(Menu.NONE, 111, Menu.NONE, "更多");
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == 111) {
            MoreInformActivity.launch(getActivity());
        }
        return super.onOptionsItemSelected(item);
    }
}
