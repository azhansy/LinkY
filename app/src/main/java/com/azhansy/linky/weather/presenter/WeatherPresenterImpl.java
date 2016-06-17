package com.azhansy.linky.weather.presenter;

import com.azhansy.linky.base.LinkApplication;
import com.azhansy.linky.base.RxBasePresenter;
import com.azhansy.linky.utils.Logger;
import com.azhansy.linky.weather.bean.RetDataBean;
import com.azhansy.linky.weather.business.WeatherBusiness;
import com.azhansy.linky.weather.view.ViewToday;
import com.loopj.android.http.RequestParams;


/**
 * Created by SHU on 2015/9/9 0009.
 */
public class WeatherPresenterImpl extends RxBasePresenter implements WeatherPresenter {
    WeatherBusiness mWeatherBusiness;

    public WeatherPresenterImpl(){
        mWeatherBusiness = new WeatherBusiness();
    }
    @Override
    public void getData(RequestParams params) {
        mSubscriptions.add(LinkApplication.getInstance().getRxBus().toObserverable()
                .filter(event -> event instanceof RetDataBean)
                .subscribe(retDataBean -> {
                    ViewToday viewToday = getActualUi();
                    RetDataBean reBean = ((RetDataBean)retDataBean);
                    if (viewToday != null && reBean != null) {
                        viewToday.setHead(reBean.getCity());
                        viewToday.setTempData(reBean.getTodayBean());
                    }
                }, throwable -> {
                    Logger.d("throwable......");
                }));
        mWeatherBusiness.getData(params);
    }
}
