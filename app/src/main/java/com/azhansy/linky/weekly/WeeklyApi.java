package com.azhansy.linky.weekly;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Url;

/**
 * Created by SHU on 2016/7/13.
 */
public interface WeeklyApi {
    @GET("page/{page}")
    Call<ResponseBody> getWeeklyNews(@Path("page") int page);

    @GET()
    Call<ResponseBody> getWeeklyNewsDetail(@Url String url);
}
