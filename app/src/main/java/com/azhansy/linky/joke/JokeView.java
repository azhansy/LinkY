package com.azhansy.linky.joke;

import com.azhansy.linky.base.MVP.IBaseUi;
import com.azhansy.linky.joke.bean.JokeBean;
import com.azhansy.linky.joke.bean.JokeBeanHead;

import java.util.List;

/**
 * Created by SHU on 2016/6/22.
 */
public interface JokeView extends IBaseUi{

//    void setAdapter(JokeAdapter adapter);

    void addListData(JokeBeanHead jokeBeanHead);

    void getDataFailed(String error);
}
