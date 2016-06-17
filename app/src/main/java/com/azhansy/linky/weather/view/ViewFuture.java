package com.azhansy.linky.weather.view;


import com.azhansy.linky.weather.bean.ForecastBean;

import java.util.List;

/**
 * Created by Administrator on 2015/9/12 0012.
 */
public interface ViewFuture {
    void setFutureData(List<ForecastBean> list);
}
