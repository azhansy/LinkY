package com.azhansy.linky.culture;

import com.azhansy.linky.base.MVP.IBaseUi;
import com.azhansy.linky.culture.model.ChengyuModel;
import com.azhansy.linky.culture.model.MingrenModel;

import java.util.List;

/**
 * Created by SHU on 2016/7/4.
 */
public interface MingrenView extends IBaseUi {
    void getDataSuccess(List<MingrenModel.MingrenDetailModel> list);
    void getDataFailed(String error);
}
