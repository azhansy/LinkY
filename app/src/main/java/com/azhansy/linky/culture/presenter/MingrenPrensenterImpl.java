package com.azhansy.linky.culture.presenter;

import com.azhansy.linky.base.LinkApplication;
import com.azhansy.linky.base.RxBasePresenter;
import com.azhansy.linky.culture.MingrenView;
import com.azhansy.linky.culture.business.MingrenBusiness;
import com.azhansy.linky.culture.model.MingrenModel;
import com.azhansy.linky.utils.Logger;
import com.loopj.android.http.RequestParams;

/**
 * Created by SHU on 2016/7/4.
 */
public class MingrenPrensenterImpl extends RxBasePresenter implements MingrenPresenter {
    MingrenBusiness mingrenBusiness;
    public MingrenPrensenterImpl(){
        mingrenBusiness = new MingrenBusiness();
        mSubscriptions.add(LinkApplication.getInstance().getRxBus().toObserverable()
                .filter(event -> event instanceof MingrenModel)
                .subscribe(object ->{
                    MingrenView view = getActualUi();
                    MingrenModel bean = (MingrenModel) object;
                    if (view != null/* && jokeBeanHead != null*/) {
                        if (!bean.isState()) {
                            view.getDataFailed("请求出错");
                            return;
                        }
                        view.getDataSuccess(bean.getMingrenDetailModelList());
                    }
                },throwable -> {
                    Logger.d("throwable......");
                }));
    }
    @Override
    public void getData(RequestParams params) {
        mingrenBusiness.getData(params);
    }
}
