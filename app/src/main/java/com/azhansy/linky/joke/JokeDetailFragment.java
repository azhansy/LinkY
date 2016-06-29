package com.azhansy.linky.joke;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.widget.TextView;

import com.azhansy.linky.R;
import com.azhansy.linky.base.BaseFragment;
import com.azhansy.linky.joke.bean.JokeBean;
import com.azhansy.linky.swipebackhelper.SwipeBackHelper;

import butterknife.Bind;


/**
 * Created by SHU on 2016/6/29.
 * 笑话详情
 */
public class JokeDetailFragment extends BaseFragment {
    public static String JOKEDETAILFRAGMENT_JOKEBEAN = "JOKEBEAN";
    @Bind(R.id.tv_title)
    TextView mTitle;
    @Bind(R.id.tv_content)
    TextView mContent;

    private JokeBean mJokeBean;
    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_of_joke_detail;
    }

    public static JokeDetailFragment getInstance(JokeBean jokeBean){
        Bundle arguments = new Bundle();
        arguments.putParcelable(JokeDetailFragment.JOKEDETAILFRAGMENT_JOKEBEAN, jokeBean);
        JokeDetailFragment detailFragment = new JokeDetailFragment();
        detailFragment.setArguments(arguments);
        return detailFragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mJokeBean = getArguments().getParcelable(JOKEDETAILFRAGMENT_JOKEBEAN);
        if (mJokeBean != null) {
            mContent.setText(Html.fromHtml(mJokeBean.getText()));
            mTitle.setText(mJokeBean.getTitle());
        }
    }
}
