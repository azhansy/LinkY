package com.azhansy.linky.culture.presenter;

import com.azhansy.linky.base.LinkApplication;
import com.azhansy.linky.base.RxBasePresenter;
import com.azhansy.linky.culture.ChengyuView;
import com.azhansy.linky.culture.business.ChengyuBusiness;
import com.azhansy.linky.culture.model.ChengyuAnalysisModel;
import com.azhansy.linky.culture.model.ChengyuModel;
import com.azhansy.linky.retrofit.BaiDuStoreService;
import com.azhansy.linky.utils.Logger;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by SHU on 2016/7/4.
 */
public class ChengyuPrensenterImpl extends RxBasePresenter implements ChengyuPresenter {
    ChengyuBusiness chengyuBusiness;
    private int page;
    private String mWordKey;
    private List<ChengyuModel.ChengyuDetailModel> chengyuDetailModelList = new ArrayList<>();

    public ChengyuPrensenterImpl(){
        chengyuBusiness = new ChengyuBusiness();
        mSubscriptions.add(LinkApplication.getInstance().getRxBus().toObserverable()
                .filter(event -> event instanceof ChengyuModel)
                .subscribe(object ->{
                    ChengyuView view = getActualUi();
                    ChengyuModel bean = (ChengyuModel) object;
                    if (view != null/* && jokeBeanHead != null*/) {
                        if (!bean.isState()) {
                            view.getDataFailed("请求出错");
                            return;
                        }
                        if (page != 1){
                            for (ChengyuModel.ChengyuDetailModel detailModel : bean.getModelList()) {
                                chengyuDetailModelList.add(detailModel);
                            }
                        }else {
                            chengyuDetailModelList = bean.getModelList();
                        }
                        view.getDataSuccess(chengyuDetailModelList);
                    }
                },throwable -> {
                    Logger.d("throwable......");
                }));
    }
    @Override
    public void getData(String wordKey) {
        page = 1;
        mWordKey = wordKey;
        requestData();
    }
    private void requestData(){
        RequestParams params = new RequestParams();
        params.put("keyWord",mWordKey);
        params.put("page",page);
        params.put("rows","30");
        chengyuBusiness.getData(params);
    }

    @Override
    public void getChengyuAnalysis(String id) {
        Call<ChengyuAnalysisModel> call = BaiDuStoreService.getInstance().baiDuStoreApi.getChengyuDetail(id);
        call.enqueue(new Callback<ChengyuAnalysisModel>() {
            @Override
            public void onResponse(Call<ChengyuAnalysisModel> call, Response<ChengyuAnalysisModel> response) {
                Logger.d(response.body().getResult().toString());
                ChengyuView view = getActualUi();
                ChengyuAnalysisModel model = response.body();
                view.getDetailSuccess(model.getResult());
            }

            @Override
            public void onFailure(Call<ChengyuAnalysisModel> call, Throwable t) {
                ChengyuView view = getActualUi();
                view.getDataFailed("请求出错");
            }
        });
    }

    @Override
    public void onRefresh() {
        page = 1;
        requestData();
    }

    @Override
    public void onLoad() {
        page++;
        requestData();
    }
}
