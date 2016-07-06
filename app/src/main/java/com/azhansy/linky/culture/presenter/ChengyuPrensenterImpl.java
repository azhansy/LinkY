package com.azhansy.linky.culture.presenter;

import com.azhansy.linky.base.LinkApplication;
import com.azhansy.linky.base.RxBasePresenter;
import com.azhansy.linky.culture.ChengyuView;
import com.azhansy.linky.culture.business.ChengyuBusiness;
import com.azhansy.linky.culture.model.ChengyuModel;
import com.azhansy.linky.utils.Logger;
import com.loopj.android.http.RequestParams;

/**
 * Created by SHU on 2016/7/4.
 */
public class ChengyuPrensenterImpl extends RxBasePresenter implements ChengyuPresenter {
    ChengyuBusiness chengyuBusiness;
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
                        view.getDataSuccess(bean.getModelList());
                    }
                },throwable -> {
                    Logger.d("throwable......");
                }));
    }
    @Override
    public void getData(String wordKey) {
        RequestParams params = new RequestParams();
        params.put("keyWord",wordKey);
        chengyuBusiness.getData(params);
    }
}
