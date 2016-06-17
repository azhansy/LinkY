package com.azhansy.linky.weather.bean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/9/8 0008.
 */
public class TodayBean {
    private String date; //当前日期
    private String week;    //当前星期几
    private String curTemp; //当前温度
    private int aqi;    //污染度
    private String fengxiang;   //风向
    private String fengli;      //风力
    private String hightemp;    //最高温度
    private String lowtemp;     //最低温度
    private String type;        //多云、阵雨等类型
    public List<IndexBean> IndexBeanList;//各类指数集

    public TodayBean() {
    }

    public TodayBean(String date, String week, String curTemp, int aqi, String fengxiang, String fengli, String hightemp, String lowtemp, String type) {
        this.date = date;
        this.week = week;
        this.curTemp = curTemp;
        this.aqi = aqi;
        this.fengxiang = fengxiang;
        this.fengli = fengli;
        this.hightemp = hightemp;
        this.lowtemp = lowtemp;
        this.type = type;
    }

    public TodayBean(JSONObject jsonObject) {
        if (jsonObject == null) {
            return;
        }
        this.date = jsonObject.optString("date");
        this.week = jsonObject.optString("week");
        this.curTemp = jsonObject.optString("curTemp");
        this.aqi = jsonObject.optInt("aqi");
        this.fengxiang = jsonObject.optString("fengxiang");
        this.fengli = jsonObject.optString("fengli");
        this.hightemp = jsonObject.optString("hightemp");
        this.lowtemp = jsonObject.optString("lowtemp");
        this.type = jsonObject.optString("type");

        IndexBean indexBean;
        JSONArray jsonArray = jsonObject.optJSONArray("index");
        if (jsonArray != null && jsonArray.length() > 0) {
            IndexBeanList = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                try {
                    indexBean = new IndexBean(jsonArray.getJSONObject(i));
                    IndexBeanList.add(indexBean);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public String getCurTemp() {
        return curTemp;
    }

    public void setCurTemp(String curTemp) {
        this.curTemp = curTemp;
    }

    public int getAqi() {
        return aqi;
    }

    public void setAqi(int aqi) {
        this.aqi = aqi;
    }

    public String getFengxiang() {
        return fengxiang;
    }

    public void setFengxiang(String fengxiang) {
        this.fengxiang = fengxiang;
    }

    public String getFengli() {
        return fengli;
    }

    public void setFengli(String fengli) {
        this.fengli = fengli;
    }

    public String getHightemp() {
        return hightemp;
    }

    public void setHightemp(String hightemp) {
        this.hightemp = hightemp;
    }

    public String getLowtemp() {
        return lowtemp;
    }

    public void setLowtemp(String lowtemp) {
        this.lowtemp = lowtemp;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<IndexBean> getIndexBeanList() {
        return IndexBeanList;
    }

    public void setIndexBeanList(List<IndexBean> indexBeanList) {
        this.IndexBeanList = indexBeanList;
    }
}
