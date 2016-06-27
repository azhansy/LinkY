package com.azhansy.linky.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by SHU on 2016/6/27.
 * FragmentPagerAdapter 基类
 */
public abstract class BaseFragmentPagerAdapter extends FragmentPagerAdapter {
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private FragmentManager fragmentManager;
    public BaseFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
        fragmentManager = fm;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    protected String getString(int resId){
        return LinkApplication.getInstance().getString(resId);
    }
    protected void addFragment(Fragment fragment) {
        if (fragment != null) {
            mFragments.add(fragment);
        }
    }
    /**
     * 初始化所有fragment，并添加进list
     */
    public abstract void initFragments();
    /**
     * 保存时fragment tag前缀
     *
     * @return
     */
    protected abstract String getTagPrefix();
    /**
     * fragment的个数，使用常量
     *
     * @return
     */
    protected abstract int getFragmentCount();
    /**
     * 页面恢复数据时恢复fragment
     */
    public void restoreFragments(Bundle savedInstanceState) {
        for (int i = 0; i < getFragmentCount(); i++) {
            Fragment fragment = fragmentManager.getFragment(savedInstanceState, getTagPrefix() + i);
            addFragment(fragment);
        }
    }
    /**
     * onSaveInstanceState时保存fragment
     *
     * @param outState
     */
    public void saveFragments(Bundle outState) {
        try {
            for (int i = 0; i < getFragmentCount(); i++) {
                if (i < mFragments.size()) {
                    fragmentManager.putFragment(outState, getTagPrefix() + i, getItem(i));
                }
            }
        }catch (IllegalStateException e){

        }

    }
    /**
     * 移除fragment
     */
    public void clear() {
        if (fragmentManager != null) {
            for (Fragment fragment : mFragments) {
                if (fragment != null) {
                    String tag = fragment.getTag();
                    if (fragmentManager.findFragmentByTag(tag) != null) {
                        //System.out.println("NewsListFragment:remove fragment=" + tag);
                        fragmentManager.beginTransaction().remove(fragment).commitAllowingStateLoss();
                    }
                }
            }
        }
        mFragments.clear();
    }
}
