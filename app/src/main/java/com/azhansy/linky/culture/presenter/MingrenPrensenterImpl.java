package com.azhansy.linky.culture.presenter;

import com.azhansy.linky.base.BaseRetrofitPresenter;
import com.azhansy.linky.culture.MingrenView;
import com.azhansy.linky.culture.model.MingrenModel;
import com.azhansy.linky.retrofit.BaiDuStoreService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by SHU on 2016/7/4.
 */
public class MingrenPrensenterImpl extends BaseRetrofitPresenter implements MingrenPresenter {
    @Override
    public void getMingRen(String keyword, String page) {
        Call<MingrenModel> call = BaiDuStoreService.getInstance().baiDuStoreApi.getMingren(keyword, page, "10");
        call.enqueue(new Callback<MingrenModel>() {
            @Override
            public void onResponse(Call<MingrenModel> call, Response<MingrenModel> response) {
                MingrenModel mingrenModel = response.body();
                MingrenView view = getActualUi();
                view.getDataSuccess(mingrenModel.getResult());
            }

            @Override
            public void onFailure(Call<MingrenModel> call, Throwable t) {
                MingrenView view = getActualUi();
                view.getDataFailed("请求失败");
            }
        });
    }
}
