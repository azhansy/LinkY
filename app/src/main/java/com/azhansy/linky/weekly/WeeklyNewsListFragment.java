package com.azhansy.linky.weekly;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.azhansy.linky.R;
import com.azhansy.linky.base.MVP.MVPBaseFragment;
import com.azhansy.linky.utils.ToastUtil;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by SHU on 2016/7/13.
 */
public class WeeklyNewsListFragment extends MVPBaseFragment<WeeklyNewsPresenterImpl> implements WeeklyView {
    @Bind(R.id.toolbar_title)
    TextView mTitle;
    @Bind(R.id.list_recycler_view)
    RecyclerView mRecycleView;
    private WeeklyNewsAdapter weeklyAdapter;

    @OnClick(R.id.float_btn)
    void OnClick() {
        mRecycleView.smoothScrollToPosition(0);
    }

    public int grade;//是否点击进入详情列表

    public static WeeklyNewsListFragment getInstance() {
        return new WeeklyNewsListFragment();
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.layout_of_recycle;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mTitle.setText("Android周报");
        grade = 0;
        weeklyAdapter = new WeeklyNewsAdapter(getActivity());
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 1);
        mRecycleView.setLayoutManager(gridLayoutManager);
        mRecycleView.setAdapter(weeklyAdapter);
        mRecycleView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            private int lastItem;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE
                        && lastItem + 1 == mRecycleView.getAdapter().getItemCount() && weeklyAdapter.isHasMore) {
                    if (grade != 0) {
                        return;
                    }
                    refreshLoading();
                    mPresenter.onLoad();
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastItem = gridLayoutManager.findLastVisibleItemPosition();
            }
        });
        mPresenter.getNews();
        mSwipeRefreshLayout.setOnRefreshListener(() -> {
                    if (grade != 0) {
                        return;
                    }
                    mPresenter.onRefresh();
                }
        );
        weeklyAdapter.setOnItemClickListener((view1, data, position) -> {
            WeeklyModel item = (WeeklyModel) data;
            if (grade == 0) {
                grade++;
                mPresenter.getWeeklyList(item.getUrl());
                mTitle.setText(item.getTitle());
            } else {
                WeeklyNewsDetailActivity.launch(getActivity(), item.getUrl());
            }
        });
        showLoadingDialog();
    }

    public void onBackPress() {
        if (mTitle == null) {
            grade = 0;
            return;
        }
        if (grade != 0) {
            showLoadingDialog();
            grade--;
            mTitle.setText("Android周报");
            mPresenter.getNews();
        }
    }

    @Override
    protected boolean usePresenter() {
        return true;
    }

    @Override
    protected WeeklyNewsPresenterImpl createPresenter() {
        return new WeeklyNewsPresenterImpl();
    }

    @Override
    public Context getPresenterContext() {
        return getActivity();
    }

    @Override
    public void LoadHtmlSuccess(List<WeeklyModel> list) {
        stopLoading();
        closeLoadingDialog();
        weeklyAdapter.replaceAll(list);
    }

    @Override
    public void LoadHtmlSuccess(WeeklyModel model) {

    }

    @Override
    public void LoadHtmlFailed(String error) {
        stopLoading();
        closeLoadingDialog();
        ToastUtil.showToast(getActivity(), error);
    }
}
