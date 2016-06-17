package com.azhansy.linky.weather.bean;

import org.json.JSONObject;

/**
 * Created by Administrator on 2015/9/8 0008.
 */
public class IndexBean {
    private String name; //防晒感冒等指数名称
    private String code;  //某指数对应的代码
    private String ind; //程度中等、适宜、不适宜等
    private String details; //建议行为
    private String otherName; //其他的叫法

    public IndexBean() {
    }

    public IndexBean(String name, String code, String index, String details, String otherName) {
        this.name = name;
        this.code = code;
        this.ind = index;
        this.details = details;
        this.otherName = otherName;
    }

    public IndexBean(JSONObject jsonObject) {
        this.name = jsonObject.optString("name");
        this.code = jsonObject.optString("code");
        this.ind = jsonObject.optString("index");
        this.details = jsonObject.optString("details");
        this.otherName = jsonObject.optString("otherName");
    }
    public String getOtherName() {
        return otherName;
    }

    public void setOtherName(String otherName) {
        this.otherName = otherName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getIndex() {
        return ind;
    }

    public void setIndex(String index) {
        this.ind = index;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
