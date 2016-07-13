package com.azhansy.linky.joke;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.azhansy.linky.R;
import com.azhansy.linky.base.BaseRecyclerViewAdapter;
import com.azhansy.linky.base.MVP.MVPBaseFragment;
import com.azhansy.linky.joke.bean.JokeBean;
import com.azhansy.linky.joke.bean.JokeBeanHead;
import com.azhansy.linky.joke.presenter.JokePresenterImpl;
import com.azhansy.linky.utils.ToastUtil;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by SHU on 2016/6/22.
 */
public class JokeFragment extends MVPBaseFragment<JokePresenterImpl> implements JokeView, BaseRecyclerViewAdapter.OnItemClickListener {
    @Bind(R.id.list_recycler_view)
    RecyclerView mRecyView;
    @Bind(R.id.float_btn)
    FloatingActionButton mFloatBtn;
    @Bind(R.id.toolbar_title)
    TextView mTitle;

    @OnClick(R.id.float_btn)
    void OnClick() {
        mRecyView.smoothScrollToPosition(0);
    }

    private JokeAdapter jokeAdapter;
    private int page;

    public static JokeFragment getInstance() {
        return new JokeFragment();
    }

    @Override
    protected boolean usePresenter() {
        return true;
    }

    @Override
    protected JokePresenterImpl createPresenter() {
        return new JokePresenterImpl();
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.layout_of_recycle;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initToolbar();
        init();
    }

    private void initToolbar() {
        mTitle.setText("笑话全集");
    }

    private void init() {
        jokeAdapter = new JokeAdapter(getActivity());
        jokeAdapter.setOnItemClickListener(this);
//        mFloatBtn.setVisibility(View.VISIBLE);
        mSwipeRefreshLayout.setOnRefreshListener(() -> {
            page = 1;
            getData();
        });

        mRecyView.setHasFixedSize(true);//can't change item size
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 1);
        mRecyView.setLayoutManager(gridLayoutManager);
        mRecyView.setItemAnimator(new DefaultItemAnimator());
        mRecyView.setAdapter(jokeAdapter);
        mRecyView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            protected int lastVisibleItem;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (mRecyView.getAdapter() != null
                        && newState == RecyclerView.SCROLL_STATE_IDLE
                        && lastVisibleItem + 1 == mRecyView.getAdapter()
                        .getItemCount() && jokeAdapter.isHasMore) {
                   refreshLoading();
                    page++;
                    mPresenter.getData(page);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int arg0, int arg1) {
                super.onScrolled(recyclerView, arg0, arg1);
                lastVisibleItem = gridLayoutManager.findLastVisibleItemPosition();
            }
        });
        page = 1;
        getData();
    }

    private void getData() {
        mPresenter.getData(page);
        refreshLoading();
        showLoadingDialog();

    }

    @Override
    public Context getPresenterContext() {
        return getActivity();
    }


    @Override
    public void addListData(JokeBeanHead jokeBeanHead) {
       stopLoading();
        closeLoadingDialog();
        if (page == 1) {
            jokeAdapter.replaceAll(jokeBeanHead.getJokeBeanList());
        } else {
            jokeAdapter.addAll(jokeBeanHead.getJokeBeanList());
        }
    }

    @Override
    public void getDataFailed(String error) {
      stopLoading();
        closeLoadingDialog();
        ToastUtil.showToast(getActivity(), error);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.onDestroy();
        }
    }

    @Override
    public void onItemClick(View view, Object data, int position) {
        JokeDetailActivity.Launch(getActivity(), (ArrayList<JokeBean>) jokeAdapter.getList(), position);
    }
}
