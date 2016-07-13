package com.azhansy.linky.blog.presenter;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.azhansy.linky.base.MVP.MVPBasePresenter;
import com.azhansy.linky.blog.BlogAdapter;
import com.azhansy.linky.blog.BlogDetailActivity;
import com.azhansy.linky.blog.BlogView;
import com.azhansy.linky.blog.model.BlogDetail;
import com.azhansy.linky.blog.model.BlogItem;
import com.azhansy.linky.blog.retrofit.BlogService;
import com.azhansy.linky.utils.JsoupParseUtil;
import com.azhansy.linky.utils.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by SHU on 2016/7/8.
 */
public class BlogListFragmentPrensenterImpl extends MVPBasePresenter implements BlogListFragmentPrensenter {
    private int page = 1;
    private String blogName;
    private Call<ResponseBody> call;
    private List<BlogItem> blogItemList = new ArrayList<>();
    private BlogAdapter blogAdapter;

    @Override
    public void initPresenter() {
        blogAdapter = new BlogAdapter(getContext());
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 1);
        BlogView view = getActualUi();
        if (view != null) {
            RecyclerView mRecycleView = view.getRecyclerView();
            mRecycleView.setLayoutManager(gridLayoutManager);
            mRecycleView.setAdapter(blogAdapter);
            mRecycleView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                private int lastItem;

                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
                    if (newState == RecyclerView.SCROLL_STATE_IDLE
                            && lastItem + 1 == mRecycleView.getAdapter().getItemCount() && blogAdapter.isHasMore) {
                        onLoad();
                    }
                }

                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    lastItem = gridLayoutManager.findLastVisibleItemPosition();
                }
            });
        }
        blogAdapter.setOnItemClickListener((view1, data, position) -> {
            BlogItem item = (BlogItem) data;
            BlogDetailActivity.launch(getContext(), item.getLink());
        });
    }

    @Override
    public void getBlogForName(String name) {
        page = 1;
        blogName = name;
        getBlogForName();
    }

    @Override
    public void getBlogDetailForUrl(String url) {
        Call<ResponseBody> responseBodyCall = BlogService.getInstance().api.getBlogDetail(url);
        responseBodyCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    BlogView view = getActualUi();
                    BlogDetail blogDetail = JsoupParseUtil.getBlogDetial(response.body().string());
                    view.LoadHtmlSuccess(blogDetail);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                BlogView view = getActualUi();
                view.LoadHtmlFailed("请求失败");
            }
        });
    }

    private void getBlogForName() {
        call = BlogService.getInstance().api.getBlogHtml(blogName, page);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    BlogView view = getActualUi();
                    if (page != 1) {
                        for (BlogItem blogItem : JsoupParseUtil.JsoupBlogParse(response.body().string())) {
                            blogItemList.add(blogItem);
                        }
                        blogAdapter.addAll(blogItemList);
                    } else {
                        blogItemList = JsoupParseUtil.JsoupBlogParse(response.body().string());
                        blogAdapter.replaceAll(blogItemList);
                    }
                    view.LoadHtmlSuccess(blogItemList);
                    Logger.d(response.body().string());
//                    blogAdapter.replaceAll(blogItemList);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                BlogView view = getActualUi();
                view.LoadHtmlFailed(t.toString());
                Logger.d(t.toString());
            }
        });
    }

    @Override
    public void onRefresh() {
        if (call != null) {
            call.cancel();
        }
        page = 1;
        getBlogForName();
    }

    @Override
    public void onLoad() {
        page++;
        getBlogForName();
    }
}
