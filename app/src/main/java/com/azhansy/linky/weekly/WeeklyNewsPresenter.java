package com.azhansy.linky.weekly;

import com.azhansy.linky.base.MVP.LoadBasePresenter;

/**
 * Created by SHU on 2016/7/13.
 */
public interface WeeklyNewsPresenter  extends LoadBasePresenter {
    void getNews();
    void getNewsDetailForUrl(String url);
}
