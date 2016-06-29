package com.azhansy.linky.joke;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;

import com.azhansy.linky.R;
import com.azhansy.linky.base.BaseActivity;
import com.azhansy.linky.joke.bean.JokeBean;
import com.azhansy.linky.swipebackhelper.SwipeBackHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by SHU on 2016/6/29.
 */
public class JokeDetailActivity extends BaseActivity {
    public static String JOKEBEANLIST = "JokeBeanList";
    public static String CURRENTPOSITION = "current_position";
    @Bind(R.id.viewpager)
    ViewPager mViewPager;

    private JokeFragmentAdapter jokeFragmentAdapter;
    private List<JokeDetailFragment> mJokeFragmentList;
    private List<JokeBean> mJokeBeanList;
    private int mCurrentPosition;

    @Override
    public int getLayoutResource() {
        return R.layout.activity_joke_detail;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SwipeBackHelper.getCurrentPage(JokeDetailActivity.this).setSwipeBackEnable(false);
        mJokeBeanList = getIntent().getParcelableArrayListExtra(JOKEBEANLIST);
        mCurrentPosition = getIntent().getIntExtra(CURRENTPOSITION,0);
        if (mJokeBeanList == null) return;
        mJokeFragmentList = new ArrayList<>();
        for (JokeBean jokeBean : mJokeBeanList) {
            mJokeFragmentList.add(JokeDetailFragment.getInstance(jokeBean));
        }
        jokeFragmentAdapter = new JokeFragmentAdapter(getSupportFragmentManager(), mJokeFragmentList);
        mViewPager.setAdapter(jokeFragmentAdapter);
        mViewPager.setCurrentItem(mCurrentPosition);
    }

    public static void Launch(Context context, ArrayList<JokeBean> list,int position) {
        Intent intent = new Intent(context, JokeDetailActivity.class);
        intent.putParcelableArrayListExtra(JokeDetailActivity.JOKEBEANLIST, list);
        intent.putExtra(CURRENTPOSITION, position);
        context.startActivity(intent);
    }
}
