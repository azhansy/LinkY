package com.azhansy.linky.weather.bean;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

/**
 * Created by Administrator on 2015/9/8 0008.
 */
public class ForecastBean implements Parcelable{
    private String date;  //未来的日期
    private String week;  //未来是星期几
    private String fengxiang; //风向
    private String fengli;  //风力
    private String hightemp;  //最高温度
    private String lowtemp;   //最低温度
    private String type;   //多云、阵雨等类型

    public ForecastBean() {
    }

    public ForecastBean(String date, String week, String fengxiang, String fengli, String hightemp, String lowtemp, String type) {
        this.date = date;
        this.week = week;
        this.fengxiang = fengxiang;
        this.fengli = fengli;
        this.hightemp = hightemp;
        this.lowtemp = lowtemp;
        this.type = type;
    }

    public ForecastBean(JSONObject jsonObject) {
        this.date = jsonObject.optString("date");
        this.week = jsonObject.optString("week");
        this.fengxiang = jsonObject.optString("fengxiang");
        this.fengli = jsonObject.optString("fengli");
        this.hightemp = jsonObject.optString("hightemp");
        this.lowtemp = jsonObject.optString("lowtemp");
        this.type = jsonObject.optString("type");
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.date);
        dest.writeString(this.week);
        dest.writeString(this.fengxiang);
        dest.writeString(this.fengli);
        dest.writeString(this.hightemp);
        dest.writeString(this.lowtemp);
        dest.writeString(this.type);
    }

    public static final Parcelable.Creator<ForecastBean> CREATOR = new Creator<ForecastBean>() {
        @Override
        public ForecastBean createFromParcel(Parcel source) {
            ForecastBean forecastBean = new ForecastBean();
            forecastBean.setDate(source.readString());
            forecastBean.setWeek(source.readString());
            forecastBean.setFengxiang(source.readString());
            forecastBean.setFengli(source.readString());
            forecastBean.setHightemp(source.readString());
            forecastBean.setLowtemp(source.readString());
            forecastBean.setType(source.readString());
            return forecastBean;
        }

        @Override
        public ForecastBean[] newArray(int size) {
            return new ForecastBean[0];
        }
    };
}
