package com.azhansy.linky.culture.business;

import com.azhansy.linky.base.LinkApplication;
import com.azhansy.linky.culture.model.ChengyuModel;
import com.azhansy.linky.utils.Config;
import com.azhansy.linky.utils.HttpUtil;
import com.azhansy.linky.utils.Logger;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * Created by SHU on 2016/7/4.
 * http://apis.baidu.com/avatardata/chengyu/search
 */
public class ChengyuBusiness {
    /*
    dtype 返回数据的格式,xml或json，默认json
    keyword 查找关键字
    page 请求页数，默认page=1
    rows 返回记录条数，默认rows=20,最大50

     */
    public void getData(RequestParams params){
        HttpUtil.getAsyncHttpClient().addHeader("apikey","a9d2b14d7c12c30cc05401795d474144");
        HttpUtil.get(Config.cheng_search_host,params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Logger.d(response.toString());
                ChengyuModel bean = new ChengyuModel(response);
                bean.setState(true);
                LinkApplication.getInstance().getRxBus().send(bean);

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Logger.d(responseString.toString());
                ChengyuModel jokeBeanHead = new ChengyuModel();
                jokeBeanHead.setState(false);
                LinkApplication.getInstance().getRxBus().send(jokeBeanHead);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Logger.d(errorResponse.toString()); //errorResponse为null....
                ChengyuModel jokeBeanHead = new ChengyuModel();
                jokeBeanHead.setState(false);
                LinkApplication.getInstance().getRxBus().send(jokeBeanHead);
            }
        });
    }
}
