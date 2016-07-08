package com.azhansy.linky.blog.presenter;

import com.azhansy.linky.base.MVP.MVPBasePresenter;
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

    private void getBlogForName(){
        call = BlogService.getInstance().api.getBlogHtml(blogName, page);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    BlogView view = getActualUi();
//                    view.LoadHtmlSuccess(response.body().string());
                    if (page != 1) {
                        for (BlogItem blogItem : JsoupParseUtil.JsoupParse(response.body().string())) {
                            blogItemList.add(blogItem);
                        }
                    }else {
                        blogItemList =  JsoupParseUtil.JsoupParse(response.body().string());
                    }
                    view.LoadHtmlSuccess(blogItemList);
                    Logger.d(response.body().string());
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
