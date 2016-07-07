package com.azhansy.linky.culture.presenter;


import com.azhansy.linky.base.MVP.LoadBasePresenter;

/**
 * Created by SHU on 2016/7/4.
 */
public interface ChengyuPresenter extends LoadBasePresenter {
    void getData(String wordKey);

    void getChengyuAnalysis(String id);


}
