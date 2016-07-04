package com.azhansy.linky.culture;

import com.azhansy.linky.base.MVP.IBaseUi;
import com.azhansy.linky.culture.model.ChengyuModel;

import java.util.List;

/**
 * Created by SHU on 2016/7/4.
 */
public interface ChengyuView extends IBaseUi {
    void getDataSuccess(List<ChengyuModel.ChengyuDetailModel> list);
    void getDataFailed(String error);
}
