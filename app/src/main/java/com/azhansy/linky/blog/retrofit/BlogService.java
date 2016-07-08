package com.azhansy.linky.blog.retrofit;

import com.azhansy.linky.base.MVP.BaseRetrofitService;
import com.azhansy.linky.utils.Config;

/**
 * Created by SHU on 2016/7/8.
 */
public class BlogService extends BaseRetrofitService<BlogApi> {

    public BlogService(boolean isJson) {
        super(isJson);
    }

    @Override
    protected String getBaseUrl() {
        return Config.base_url_blog;
    }

    //在访问HttpRetrofit2时创建单例
    private static class SingletonHolder {
        private static final BlogService INSTANCE = new BlogService(false);
    }

    //获取单例
    public static BlogService getInstance() {
        return SingletonHolder.INSTANCE;
    }
}
