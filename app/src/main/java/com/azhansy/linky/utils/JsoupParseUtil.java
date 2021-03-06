package com.azhansy.linky.utils;

import com.azhansy.linky.blog.model.BlogDetail;
import com.azhansy.linky.blog.model.BlogItem;
import com.azhansy.linky.weekly.WeeklyModel;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SHU on 2016/7/8.
 * 网页Jsoup解析类
 */
public class JsoupParseUtil {
    /**
     * 博客解析
     *
     * @param htmlStr
     * @return
     */
    public static List<BlogItem> JsoupBlogParse(String htmlStr) {
        List<BlogItem> blogItemList = new ArrayList<>();
        Document doc = Jsoup.parse(htmlStr);
        Elements articleList = null;
        try {
            articleList = doc.getElementsByClass("blog_list");
            for (Element element : articleList) {
                BlogItem item = new BlogItem();
                Element link_title = element.getElementsByTag("dt").get(0);
                item.setTitle(link_title.text());
                item.setLink(link_title.child(0).attr("href"));
                Element manage = element.getElementsByTag("dd").get(0).getElementsByTag("em").get(0);
                item.setDate(manage.text());
//                Element description = element.getElementsByClass("article_description").get(0);
//                item.setContent(description.text());
                blogItemList.add(item);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
//        try {
//            articleList = doc.getElementsByClass("article_item");
//            for (Element element : articleList) {
//                BlogItem item = new BlogItem();
//                Element link_title = element.getElementsByClass("article_title").get(0).getElementsByTag("h1").get(0).getElementsByClass("link_title").get(0);
//                item.setTitle(link_title.text());
//                item.setLink(link_title.child(0).attr("href"));
//                Element manage = element.getElementsByClass("article_manage").get(0);
//                item.setDate(manage.getElementsByClass("link_postdate").text());
//                Element description = element.getElementsByClass("article_description").get(0);
//                item.setContent(description.text());
//                blogItemList.add(item);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        return blogItemList;
    }

    /**
     * @param htmlStr 博客详情
     * @return
     */
    public static BlogDetail getBlogDetial(String htmlStr) {
        BlogDetail blogDetail = new BlogDetail();
        Document doc = Jsoup.parse(htmlStr);
        // 获得文章中的第一个detail
        Element detail = doc.select("div.blog_article_c").first();  //class blog_article_c clearfix
        blogDetail.setTexts(detail.html());
//        //获取title
        Element title = detail.getElementsByClass("blog_article_c").get(0).getElementsByClass("article_t").get(0);
        blogDetail.setTitle(title.text());
        return blogDetail;
    }

    public static final String WEB_FRAME = "<html ><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"></head>" +
            "<div class=\"article_c\"><span>%s</span></div><br />";


    public static List<WeeklyModel> JsoupWeeklyParse(String htmlStr) {
        List<WeeklyModel> weeklyModelList = new ArrayList<>();
        Document doc = Jsoup.parse(htmlStr);
        Elements weeklyList = null;
        try {
            weeklyList = doc.getElementsByClass("tag-androiddevweekly");
            for (Element element : weeklyList) {
                WeeklyModel weekly = new WeeklyModel();
                Element title = element.getElementsByClass("post-header").get(0).getElementsByClass("post-title").get(0);
                weekly.setTitle(title.text());
                weekly.setUrl(title.child(0).attr("href"));
                Element content = element.getElementsByClass("post-excerpt").get(0).child(0);
                weekly.setContent(content.text());
                Element date = element.getElementsByClass("post-meta").get(0).getElementsByClass("post-date").get(0);
                weekly.setTime(date.text());
                weeklyModelList.add(weekly);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return weeklyModelList;
    }

    /**
     * @param htmlStr 开发一期周报详情列表
     * @return
     */
    public static List<WeeklyModel> getWeeklyModelList(String htmlStr) {
        List<WeeklyModel> weeklyModelList = new ArrayList<>();
        Document doc = Jsoup.parse(htmlStr);
        // 获得文章中的第一个detail
        try {
            Elements more = doc.getElementsByClass("post-content").get(0).getElementsByTag("h3");
            for (int i = 0; i < more.size(); i++) {
                Elements detailList = doc.getElementsByClass("post-content").get(0).getElementsByTag("ol").get(i).getElementsByTag("li");
                for (Element element1 : detailList) {
                    WeeklyModel detail1 = new WeeklyModel();
                    if (element1.getElementsByTag("p").size() == 0) {
                        detail1.setTitle(element1.text());
                        detail1.setContent(element1.text());
                        detail1.setUrl(element1.child(0).attr("href"));
                    }else {
                        if (element1.getElementsByTag("p").size() == 1) {
                            Element title = element1.getElementsByTag("p").get(0).child(0);
                            detail1.setTitle(title.text());
                            detail1.setContent(title.text());
                            detail1.setUrl(title.attr("href"));
                        }else {
                            Element title = element1.getElementsByTag("p").get(0).child(0);
                            detail1.setTitle(title.text());
                            detail1.setUrl(title.attr("href"));
                            detail1.setContent(element1.getElementsByTag("p").get(1) == null ? title.text() : element1.getElementsByTag("p").get(1).text());
                        }
                    }
                    weeklyModelList.add(detail1);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return weeklyModelList;
    }
}
