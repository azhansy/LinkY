package com.azhansy.linky.weekly;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebView;

import com.azhansy.linky.R;
import com.azhansy.linky.base.MVP.MVPBaseFragment;
import com.azhansy.linky.base.MVP.MVPBasePresenter;

import butterknife.Bind;

/**
 * Created by SHU on 2016/7/12.
 */
public class WeeklyNewsFragment extends MVPBaseFragment {
    private static final String WEEKLYNEWSFRAGMENT = "";
    @Bind(R.id.web_view)
    WebView mWebView;

    @Override
    protected boolean usePresenter() {
        return false;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String url = getStringExtra(WEEKLYNEWSFRAGMENT);
        if (!TextUtils.isEmpty(url)) {
            mWebView.loadUrl(url);//http://androidweekly.cn/
        }
    }

    @Override
    protected MVPBasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_week_news;
    }

    public static WeeklyNewsFragment getInstance(String url) {
        Bundle argment = new Bundle();
        argment.putString(WEEKLYNEWSFRAGMENT, url);
        WeeklyNewsFragment fragment = new WeeklyNewsFragment();
        fragment.setArguments(argment);

        return fragment;
    }
}
