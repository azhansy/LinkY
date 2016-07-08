package com.azhansy.linky.blog.retrofit;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * Created by SHU on 2016/7/8.
 */
public interface BlogApi {
    String URL = "{url}";

    //http://m.blog.csdn.net/ blog/index?username=azhansy
    @GET("blog/index")
    Call<ResponseBody> getBlogHtml(@Query("username") String username, @Query("page") int page);

    //http://m.blog.csdn.net/ article/details?id=50865621
    @GET()
    Call<ResponseBody> getBlogDetail(@Url String url);  //@Url 直接拼接网址
}
