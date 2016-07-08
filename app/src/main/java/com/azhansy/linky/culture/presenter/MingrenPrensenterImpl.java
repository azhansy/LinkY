package com.azhansy.linky.culture.presenter;

import com.azhansy.linky.base.BaseRetrofitPresenter;
import com.azhansy.linky.culture.MingrenView;
import com.azhansy.linky.culture.model.MingrenModel;
import com.azhansy.linky.retrofit.BaiDuStoreService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by SHU on 2016/7/4.
 */
public class MingrenPrensenterImpl extends BaseRetrofitPresenter implements MingrenPresenter {
    private String keyword;
    private int page;

    private List<MingrenModel.MingrenDetailModel> mingrenDetailModelList = new ArrayList<>();

    @Override
    public void getMingRen(String keyword) {
        page = 1;
        this.keyword = keyword;
        requestMingyan();
    }

    @Override
    public void onRefresh() {
        page = 1;
        requestMingyan();
    }

    @Override
    public void onLoad() {
        page++;
        requestMingyan();
    }

    private void requestMingyan() {
        Call<MingrenModel> call = BaiDuStoreService.getInstance().api.getMingren(keyword, page, "10");
        call.enqueue(new Callback<MingrenModel>() {
            @Override
            public void onResponse(Call<MingrenModel> call, Response<MingrenModel> response) {
                MingrenModel mingrenModel = response.body();
                MingrenView view = getActualUi();
                if (page != 1) {
                    for (MingrenModel.MingrenDetailModel mingrenDetailModel : mingrenModel.getResult()) {
                        mingrenDetailModelList.add(mingrenDetailModel);
                    }
                }else {
                    mingrenDetailModelList = mingrenModel.getResult();
                }
                view.getDataSuccess(mingrenDetailModelList);
            }

            @Override
            public void onFailure(Call<MingrenModel> call, Throwable t) {
                MingrenView view = getActualUi();
                view.getDataFailed("请求失败");
            }
        });
    }
}
