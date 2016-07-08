package com.azhansy.linky.blog.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.azhansy.linky.R;
import com.azhansy.linky.base.BaseRecyclerViewAdapter;
import com.azhansy.linky.base.MVP.MVPBaseFragment;
import com.azhansy.linky.blog.BlogAdapter;
import com.azhansy.linky.blog.BlogDetailActivity;
import com.azhansy.linky.blog.BlogView;
import com.azhansy.linky.blog.model.BlogDetail;
import com.azhansy.linky.blog.model.BlogItem;
import com.azhansy.linky.blog.presenter.BlogListFragmentPrensenterImpl;
import com.azhansy.linky.utils.ToastUtil;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by SHU on 2016/6/30.
 * 个人博客
 */
public class BlogListFragment extends MVPBaseFragment<BlogListFragmentPrensenterImpl>  implements BlogView {
    public static String STRINGURL = "BlogListFragment";
    @Bind(R.id.list_recycler_view)
    RecyclerView mRecycleView;
    private BlogAdapter blogAdapter;
    @OnClick(R.id.float_btn)
    void OnClick() {
        mRecycleView.smoothScrollToPosition(0);
    }
    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_blog_list;
    }

    public static BlogListFragment getInstance(String name){
        Bundle arguments = new Bundle();
        arguments.putString(BlogListFragment.STRINGURL,name);
        BlogListFragment fragment = new BlogListFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String name =getArguments().getString(BlogListFragment.STRINGURL);
        if (TextUtils.isEmpty(name)) {
            name = "azhansy";
        }
        blogAdapter = new BlogAdapter(getActivity());
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 1);
        mRecycleView.setLayoutManager(gridLayoutManager);
        mRecycleView.setAdapter(blogAdapter);
        mRecycleView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            private int lastItem;
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE
                        && lastItem + 1 == mRecycleView.getAdapter() .getItemCount() && blogAdapter.isHasMore) {
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
        mPresenter.getBlogForName(name);
        mSwipeRefreshLayout.setOnRefreshListener(() -> mPresenter.onRefresh());
        blogAdapter.setOnRecycleViewItemClickListener((view1, data, position) -> {
            BlogItem item = (BlogItem) data;
            BlogDetailActivity.launch(getActivity(),item.getLink());
        });
    }

    @Override
    protected boolean usePresenter() {
        return true;
    }

    @Override
    protected BlogListFragmentPrensenterImpl createPresenter() {
        return new BlogListFragmentPrensenterImpl();
    }

    @Override
    public Context getPresenterContext() {
        return getActivity();
    }

    @Override
    public void LoadHtmlSuccess(List<BlogItem> list) {
        stopLoading();
        blogAdapter.replaceAll(list);
    }

    @Override
    public void LoadHtmlSuccess(BlogDetail blogDetail) {

    }

    @Override
    public void LoadHtmlFailed(String error) {
        stopLoading();
        ToastUtil.showToast(getActivity(),error);
    }
}
