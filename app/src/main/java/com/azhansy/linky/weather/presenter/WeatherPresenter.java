package com.azhansy.linky.weather.presenter;

import com.azhansy.linky.base.MVP.MVPBasePresenter;
import com.loopj.android.http.RequestParams;

/**
 * Created by SHU on 2015/9/9 0009.
 */
public interface WeatherPresenter {
    //请求网络数据
    void getData(RequestParams params);
}
