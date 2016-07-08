package com.azhansy.linky.retrofit;

import com.azhansy.linky.base.MVP.BaseRetrofitService;
import com.azhansy.linky.utils.Config;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by SHU on 2016/7/5.
 * 百度api Store 主机地址配置
 */
public class BaiDuStoreService extends BaseRetrofitService<BaiDuStoreApi>{

    @Override
    protected String getBaseUrl() {
        return Config.base_url_baidu;
    }

    //在访问Http Retrofit2时创建单例
    private static class SingletonHolder {
        private static final BaiDuStoreService INSTANCE = new BaiDuStoreService();
    }

    //获取单例
    public static BaiDuStoreService getInstance() {
        return SingletonHolder.INSTANCE;
    }
}
