package com.azhansy.linky.blog.model;

/**
 * Created by SHU on 2016/7/7.
 */
public class BlogItem {
    private int id;
    //标题
    private String title;
    //链接
    private String link;
    //内容
    private String Content;
    //发布时间
    private String date;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "BlogItem{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", link='" + link + '\'' +
                ", Content='" + Content + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
