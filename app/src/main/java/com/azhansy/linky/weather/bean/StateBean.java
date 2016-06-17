package com.azhansy.linky.weather.bean;

import org.json.JSONObject;

/**
 * Created by Administrator on 2015/9/8 0008.
 */
public class StateBean {
    private int errNum; //返回服务器数据，0表示成功，-1白表示失败
    private String errMsg; //返回的信息，success表示成功
    private  RetDataBean retDataBean;//对象-返回的信息
    public StateBean() {
    }

    public StateBean(JSONObject jsonObject) {
        this.errNum = jsonObject.optInt("errNum");
        this.errMsg = jsonObject.optString("errMsg");
        JSONObject retData = jsonObject.optJSONObject("retData");
        this.retDataBean = new RetDataBean(retData);
    }
    public int getErrNum() {
        return errNum;
    }

    public void setErrNum(int errNum) {
        this.errNum = errNum;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public RetDataBean getRetDataBean() {
        return retDataBean;
    }

    public void setRetDataBean(RetDataBean retDataBean) {
        this.retDataBean = retDataBean;
    }
}
