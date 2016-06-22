package com.azhansy.linky.joke;

import com.azhansy.linky.base.MVP.IBaseUi;

/**
 * Created by SHU on 2016/6/22.
 */
public interface JokeView extends IBaseUi{

    void setAdapter(JokeAdapter adapter);
}
