package com.azhansy.linky.base.MVP;

import java.lang.reflect.ParameterizedType;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by SHU on 2016/7/8.
 * retrofit  Service 基类
 */
public abstract class BaseRetrofitService<T> {
    public T api; //retrofit的API
    protected static final int DEFAULT_TIMEOUT = 5;
    protected Retrofit retrofit;

    public BaseRetrofitService(){
        this(true);
    }
    /**
     * @param isJson 返回的是不是Json格式，是就要设置为true
     */
    public BaseRetrofitService(boolean isJson) {
        //手动创建一个OkHttpClient并设置超时时间
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        if (isJson) {
            retrofit = new Retrofit.Builder()
                    .client(builder.build()) //关联OkHttpClient
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .baseUrl(getBaseUrl())
                    .build();
        } else {
            retrofit = new Retrofit.Builder()
                    .client(builder.build()) //关联OkHttpClient
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .baseUrl(getBaseUrl())
                    .build();
        }
        Class<T> tClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        api = retrofit.create(tClass);
    }


    /**
     * @return Service的主机地址
     */
    protected abstract String getBaseUrl();

}
