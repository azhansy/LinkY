package com.azhansy.linky.utils;

import com.azhansy.linky.R;

/**
 * Created by SHU on 2016/6/17.
 */
public class Config {
//    https://api.heweather.com/x3/weather?cityid=CN101010200&key=552e2498ab174e6584febd7c5aa242f8

    //百度天气预报接口
    public static final String weather_host = "http://apis.baidu.com/apistore/weatherservice/recentweathers";
    //笑话接口
    public static final String joke_host = "http://apis.baidu.com/showapi_open_bus/showapi_joke/joke_text";


    public enum Channel {
        JOKE(R.string.fragment_joke_title, R.mipmap.logo),
        WEATHER( R.string.fragment_weather_title, R.mipmap.logo),
        BLOG(R.string.fragment_blog_title, R.mipmap.logo),
        NOVEL(R.string.fragment_novel_title,R.mipmap.logo),
        INFORMATION(R.string.fragment_information_title,R.mipmap.logo); //与技术博客页面显示有冲突。。。为啥

        private int title;
        private int icon;

        Channel(int title, int icon) {
            this.title = title;
            this.icon = icon;
        }

        public int getTitle() {
            return title;
        }

        public void setTitle(int title) {
            this.title = title;
        }

        public int getIcon() {
            return icon;
        }

        public void setIcon(int icon) {
            this.icon = icon;
        }
    }
}
