package com.azhansy.linky.weather.bean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/9/8 0008.
 */
public class RetDataBean {
    private String city; //当前城市
    private Long cityid; //对应的城市id
    private  TodayBean todayBean;//对象今天
    public List<ForecastBean> forecastBeanList;//未来4天天气预报
    public List<HistoryBean> historyBeanList;//前7天的天气预报

    public RetDataBean() {
    }

    public RetDataBean(String city, Long cityid) {
        this.city = city;
        this.cityid = cityid;
    }

    public RetDataBean(JSONObject jsonObject) {
        if (jsonObject == null)
            return;
        this.city = jsonObject.optString("city");
        this.cityid = jsonObject.optLong("cityid");

        JSONObject td = jsonObject.optJSONObject("today");
        this.todayBean = new TodayBean(td);

        ForecastBean forecastBean;
        JSONArray jsonArray = jsonObject.optJSONArray("forecast");
        if (jsonArray != null && jsonArray.length() > 0) {
            forecastBeanList = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                try {
                    forecastBean = new ForecastBean(jsonArray.getJSONObject(i));
                    forecastBeanList.add(forecastBean);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        HistoryBean historyBean;
        JSONArray jsonArray1 = jsonObject.optJSONArray("history");
        if (jsonArray1 != null && jsonArray1.length() > 0) {
            historyBeanList = new ArrayList<>();
            for (int i = 0; i < jsonArray1.length(); i++) {
                try {
                    historyBean = new HistoryBean(jsonArray1.getJSONObject(i));
                    historyBeanList.add(historyBean);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

    }
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Long getCityid() {
        return cityid;
    }

    public void setCityid(Long cityid) {
        this.cityid = cityid;
    }

    public TodayBean getTodayBean() {
        return todayBean;
    }

    public void setTodayBean(TodayBean todayBean) {
        this.todayBean = todayBean;
    }

    public List<ForecastBean> getForecastBeanList() {
        return forecastBeanList;
    }

    public void setForecastBeanList(List<ForecastBean> forecastBeanList) {
        this.forecastBeanList = forecastBeanList;
    }

    public List<HistoryBean> getHistoryBeanList() {
        return historyBeanList;
    }

    public void setHistoryBeanList(List<HistoryBean> historyBeanList) {
        this.historyBeanList = historyBeanList;
    }
}
