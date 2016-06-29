package com.azhansy.linky.joke;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.azhansy.linky.joke.bean.JokeBean;

import java.util.List;

/**
 * Created by SHU on 2016/6/29.
 */
public class JokeFragmentAdapter extends FragmentPagerAdapter {
    private List<JokeDetailFragment> mList;

    public JokeFragmentAdapter(FragmentManager fm, List<JokeDetailFragment> list) {
        super(fm);
        this.mList = list;
    }

    @Override
    public Fragment getItem(int position) {
        return mList.get(position);
    }

    @Override
    public int getCount() {
        return mList.size();
    }
}
