package com.azhansy.linky.blog.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.azhansy.linky.R;
import com.azhansy.linky.base.BaseFragment;
import com.azhansy.linky.blog.BlogAdapter;
import com.azhansy.linky.blog.model.BlogModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by 神奇勇士 on 2016/6/17.
 * 博客的Fragment
 */
public class BlogFragment extends BaseFragment {
    @Bind(R.id.rv_blog)
    RecyclerView mRecyclerView;
    private List<BlogModel> mBlogModelList;


    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_blog;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView.setLayoutManager(new GridLayoutManager(mActivity,1));
        mBlogModelList = new ArrayList<>();
        mRecyclerView.setAdapter(new BlogAdapter(mActivity,mBlogModelList));
    }
}
