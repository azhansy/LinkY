package com.azhansy.linky.joke.bean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SHU on 2016/6/22.
 */
public class JokeBeanHead {
    /**
     * "showapi_res_code": 0,
     * "showapi_res_error": "",
     * "showapi_res_body": {
     * "allPages": 1495,
     * "ret_code": 0,
     * "contentlist": [
     * {
     */
    private List<JokeBean> jokeBeanList;// = new ArrayList<>();
    private int allPages;
    private int currentPage;
    private int ret_code;
    private int maxResult;
    private String allNum;

    private boolean state;

    public JokeBeanHead(){

    }
    public JokeBeanHead(JSONObject object) {//"showapi_res_body"
        if (object == null) {
            return;
        }
        try {
            JSONObject jsonObject = object.getJSONObject("showapi_res_body");
            object  = jsonObject;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        this.allPages = object.optInt("allPages");
        this.currentPage = object.optInt("currentPage");
        this.ret_code = object.optInt("ret_code");
        this.maxResult = object.optInt("maxResult");
        this.allNum = object.optString("allNum");

        JSONArray array = object.optJSONArray("contentlist");
        if (array != null) {
            jokeBeanList = new ArrayList<>();
            for (int i = 0; i < array.length(); i++){
                try {
                    jokeBeanList.add(new JokeBean((JSONObject) array.get(i)));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public List<JokeBean> getJokeBeanList() {
        return jokeBeanList;
    }

    public void setJokeBeanList(List<JokeBean> jokeBeanList) {
        this.jokeBeanList = jokeBeanList;
    }

    public int getAllPages() {
        return allPages;
    }

    public void setAllPages(int allPages) {
        this.allPages = allPages;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getRet_code() {
        return ret_code;
    }

    public void setRet_code(int ret_code) {
        this.ret_code = ret_code;
    }

    public int getMaxResult() {
        return maxResult;
    }

    public void setMaxResult(int maxResult) {
        this.maxResult = maxResult;
    }

    public String getAllNum() {
        return allNum;
    }

    public void setAllNum(String allNum) {
        this.allNum = allNum;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }
}
