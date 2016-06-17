package com.azhansy.linky.weather.presenter;

import com.azhansy.linky.Rx.RxBus;
import com.azhansy.linky.base.LinkApplication;
import com.azhansy.linky.base.RxBasePresenter;
import com.azhansy.linky.utils.Logger;
import com.azhansy.linky.weather.bean.RetDataBean;

import rx.Subscription;
import rx.functions.Action1;

/**
 * Created by SHU on 2015/9/9 0009.
 */
public class WeatherPresenterImpl extends RxBasePresenter implements WeatherPresenter {
    @Override
    public void getData() {
        mSubscriptions.add(LinkApplication.getInstance().getRxBus().toObserverable()
                .filter(event -> event instanceof RetDataBean)
                .subscribe(retDataBean -> {
                    Logger.d("getdata......");
                }, throwable -> {

                }));
    }
}
