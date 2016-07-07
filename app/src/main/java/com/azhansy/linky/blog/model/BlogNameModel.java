package com.azhansy.linky.blog.model;

/**
 * Created by SHU on 2016/6/17.
 */
public class BlogNameModel {
    private String name;
    private String url;


    public BlogNameModel(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
