package com.azhansy.linky.retrofit;

import com.azhansy.linky.culture.model.ChengyuAnalysisModel;
import com.azhansy.linky.culture.model.MingrenModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by SHU on 2016/7/5.
 */
public interface BaiDuStoreApi {
    /*
     *http://apistore.baidu.com/apiworks/servicedetail/1756.html
     * 通过关键字 名人名言查询
     */
    @Headers("apikey: a9d2b14d7c12c30cc05401795d474144")
    @GET("avatardata/mingrenmingyan/lookup")
    Call<MingrenModel> getMingren(@Query("keyword") String keyword, @Query("page") int page, @Query("rows") String rows);

    @Headers("apikey: a9d2b14d7c12c30cc05401795d474144")
    @GET("avatardata/chengyu/lookup")
    Call<ChengyuAnalysisModel> getChengyuDetail(@Query("id") String id);
}
