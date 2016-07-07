package com.azhansy.linky.base.MVP;

/**
 * Created by SHU on 2016/7/7.
 * 有加载与刷新功能的
 */
public interface LoadBasePresenter {
    void onRefresh();
    void onLoad();
}
