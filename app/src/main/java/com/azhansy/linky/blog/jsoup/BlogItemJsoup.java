package com.azhansy.linky.blog.jsoup;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SHU on 2016/7/7.
 */
public class BlogItemJsoup {
//    public List<NewsItem> getNewsItems(int newsType, int curPage)
//            throws CommonException {
//        List<NewsItem> newsItems = new ArrayList<>();
//        String url = UrlUtil.getUrl(newsType, curPage);
//        String htmlStr = DataUtil.doGet(url);
//        NewsItem item = null;
//        Document doc = Jsoup.parse(htmlStr);
//        Elements units = doc.getElementsByClass("unit");
//        for (int i = 0; i < units.size(); i++) {
//            item = new NewsItem();
//            item.setNewsType(newsType);
//            Element unit = units.get(i);
//            Element h1 = unit.getElementsByTag("h1").get(0);
//            Element ha = h1.child(0);
//            item.setTitle(h1.text());
//            item.setLink(ha.attr("href"));
//            Element h4 = unit.getElementsByTag("h4").get(0);
//            Element ago = h4.getElementsByClass("ago").get(0);
//            item.setDate(ago.text());
//            Element dl_ele = unit.getElementsByTag("dl").get(0);
//            Element dt_ele = dl_ele.child(0);
//            try {
//                // 可能没有图片
//                Element img_ele = dt_ele.child(0);
//                String imgLink = img_ele.child(0).attr("src");
//                item.setImgLink(imgLink);
//            } catch (IndexOutOfBoundsException e) {
//            }
//
//            Element dd_ele = dl_ele.child(1);
//            item.setContent(dd_ele.text());
//            newsItems.add(item);
//        }
//
//        return newsItems;
//    }
}
