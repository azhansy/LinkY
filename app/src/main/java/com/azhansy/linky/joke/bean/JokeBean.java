package com.azhansy.linky.joke.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.azhansy.linky.joke.business.JokeBusiness;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by SHU on 2016/6/22.
 */
public class JokeBean implements Parcelable {
    private String time;
    private String text;
    private String title;
    private int type;

    public JokeBean(JSONObject object) {
        if (object == null) {
            return;
        }
        this.time = object.optString("ct");
        this.text = object.optString("text");
        this.title = object.optString("title");
        this.type = object.optInt("type");
    }

    public JokeBean() {
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.time);
        dest.writeString(this.text);
        dest.writeString(this.title);
        dest.writeInt(this.type);
    }

    private JokeBean(Parcel in) {
        this.time = in.readString();
        this.text = in.readString();
        this.title = in.readString();
        this.type = in.readInt();
    }
    public static final Parcelable.Creator<JokeBean> CREATOR = new Creator<JokeBean>() {
        @Override
        public JokeBean createFromParcel(Parcel source) {
            JokeBean jokeBean = new JokeBean();
            jokeBean.setTime(source.readString());
            jokeBean.setText(source.readString());
            jokeBean.setTitle(source.readString());
            jokeBean.setType(source.readInt());
            return jokeBean;
        }

        @Override
        public JokeBean[] newArray(int size) {
            return new JokeBean[0];
        }
    };


}
