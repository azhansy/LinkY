package com.azhansy.linky.weather.business;


import com.azhansy.linky.base.LinkApplication;
import com.azhansy.linky.utils.Config;
import com.azhansy.linky.utils.HttpUtil;
import com.azhansy.linky.utils.Logger;
import com.azhansy.linky.weather.bean.RetDataBean;
import com.azhansy.linky.weather.bean.StateBean;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * Created by SHU on 2016/6/17.
 */
public class WeatherBusiness {

    public void getData(RequestParams params){
        HttpUtil.getAsyncHttpClient().addHeader("apikey","a9d2b14d7c12c30cc05401795d474144");
        HttpUtil.get(Config.weather_host,params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Logger.d(response.toString());
                StateBean stateBean = new StateBean(response);
                LinkApplication.getInstance().getRxBus().send(stateBean.getRetDataBean());

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                LinkApplication.getInstance().getRxBus().send(new RetDataBean());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                LinkApplication.getInstance().getRxBus().send(new RetDataBean());
            }
        });
    }

}
