package com.azhansy.linky.utils;

import com.azhansy.linky.blog.model.BlogDetail;
import com.azhansy.linky.blog.model.BlogItem;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SHU on 2016/7/8.
 */
public class JsoupParseUtil {
    /**
     * 博客解析
     * @param htmlStr
     * @return
     */
    public static List<BlogItem> JsoupParse(String htmlStr){
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
            "<div class=\"article_c\"><span>%s</span></div><br />" ;
}
