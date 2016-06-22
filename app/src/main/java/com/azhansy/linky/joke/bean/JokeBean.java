package com.azhansy.linky.joke.bean;

import com.azhansy.linky.joke.business.JokeBusiness;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by SHU on 2016/6/22.
 */
public class JokeBean {
    private String time;
    private String text;
    private String title;
    private int type;

    public JokeBean(JSONObject object){
        if (object == null) {
            return;
        }
        this.time = object.optString("ct");
        this.text = object.optString("text");
        this.title = object.optString("title");
        this.type = object.optInt("type");

    }
    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
