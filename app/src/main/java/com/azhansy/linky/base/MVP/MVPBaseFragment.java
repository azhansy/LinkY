package com.azhansy.linky.base.MVP;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;


import com.azhansy.linky.R;
import com.azhansy.linky.base.BaseFragment;

import butterknife.Bind;


/**
 *
 */
public abstract class MVPBaseFragment<Presenter extends MVPBasePresenter> extends BaseFragment {

    @Bind(R.id.swipe_refresh_layout)
    protected SwipeRefreshLayout mSwipeRefreshLayout;

    protected Presenter mPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (usePresenter()) {
            mPresenter = createPresenter();
            mPresenter.attach(getBaseUi());
        }

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (mSwipeRefreshLayout != null) {
            mSwipeRefreshLayout.setColorSchemeColors(getActivity().getResources().getColor(R.color.colorPrimary), getResources().getColor(R.color.colorPrimaryDark));
            mSwipeRefreshLayout.setEnabled(true);
        }
        stopLoading();
    }

    @Override
    public void onDestroy() {
        if (mPresenter != null) {
            mPresenter.detach(getBaseUi());
            mPresenter = null;
        }
        super.onDestroy();
    }

    protected void refreshLoading() {
        if (mSwipeRefreshLayout != null && !mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(true);
        }
    }
//
    protected void stopLoading() {
        if (mSwipeRefreshLayout != null  && mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }

    /**
     * 是否需要使用Presenter
     */
    protected abstract boolean usePresenter();

    protected abstract Presenter createPresenter();

    /**
     * 如果需要使用Presenter，这里也需要返回相应的Ui对象
     * MVPBaseFragment implement Ui
     */
    @SuppressWarnings(value = "unchecked")
    protected <Ui extends IBaseUi> Ui getBaseUi() {
        return (Ui) this;
    }
}
