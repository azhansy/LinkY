package com.azhansy.linky.joke.business;

import com.azhansy.linky.base.LinkApplication;
import com.azhansy.linky.joke.bean.JokeBean;
import com.azhansy.linky.joke.bean.JokeBeanHead;
import com.azhansy.linky.utils.Config;
import com.azhansy.linky.utils.HttpUtil;
import com.azhansy.linky.utils.Logger;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * Created by SHU on 2016/6/22.
 */
public class JokeBusiness{

    public void getJokeData(RequestParams params){
        HttpUtil.getAsyncHttpClient().addHeader("apikey","a9d2b14d7c12c30cc05401795d474144");
        HttpUtil.get(Config.joke_host,params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Logger.d(response.toString());
                JokeBeanHead bean = new JokeBeanHead(response);
                bean.setState(true);
                LinkApplication.getInstance().getRxBus().send(bean);

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Logger.d(responseString.toString());
                JokeBeanHead jokeBeanHead = new JokeBeanHead();
                jokeBeanHead.setState(false);
                LinkApplication.getInstance().getRxBus().send(jokeBeanHead);
            }
        });
    }
}
