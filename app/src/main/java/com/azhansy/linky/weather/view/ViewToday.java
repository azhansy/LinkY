package com.azhansy.linky.weather.view;


import com.azhansy.linky.base.MVP.IBaseUi;
import com.azhansy.linky.weather.WeatherAdapter;
import com.azhansy.linky.weather.bean.ForecastBean;
import com.azhansy.linky.weather.bean.TodayBean;

import java.util.List;

/**
 * Created by Administrator on 2015/9/11 0011.
 */
public interface ViewToday extends IBaseUi{
    //显示感冒等指数在主界面
    void setIndexData(WeatherAdapter adapter);

    //设置标题
    void setHead(String s);

    //显示地区、温度界面
    void setTempData(TodayBean todayBean);

    void setForecast(List<ForecastBean> forecast);

    void getDataFail();


}
