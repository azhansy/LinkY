package com.azhansy.linky.retrofit;

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
public class BaiDuStoreService {
    private static final int DEFAULT_TIMEOUT = 5;
    public Retrofit retrofit;
    public BaiDuStoreApi baiDuStoreApi;
    public BaiDuStoreService(){
        //手动创建一个OkHttpClient并设置超时时间
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        retrofit = new Retrofit.Builder()
                .client(builder.build()) //关联OkHttpClient
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(Config.base_url)
                .build();
        baiDuStoreApi = retrofit.create(BaiDuStoreApi.class);
    }
    //在访问HttpRetrofit2时创建单例
    private static class SingletonHolder {
        private static final BaiDuStoreService INSTANCE = new BaiDuStoreService();
    }

    //获取单例
    public static BaiDuStoreService getInstance() {
        return SingletonHolder.INSTANCE;
    }
}
