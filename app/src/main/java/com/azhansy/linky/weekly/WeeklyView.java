package com.azhansy.linky.weekly;

import com.azhansy.linky.base.MVP.IBaseUi;

import java.util.List;

/**
 * Created by SHU on 2016/7/13.
 */
public interface WeeklyView  extends IBaseUi{
    void LoadHtmlSuccess(List<WeeklyModel> list);
    void LoadHtmlSuccess(WeeklyModel blogDetail);
    void LoadHtmlFailed(String error);
}
