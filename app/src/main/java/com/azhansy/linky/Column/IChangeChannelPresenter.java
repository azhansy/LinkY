package com.azhansy.linky.column;

import com.azhansy.linky.utils.Config;

import java.util.ArrayList;

/**
 * Created by SHU on 2016/6/25.
 */
public interface IChangeChannelPresenter {
    void getChannel();

    boolean saveChannel(ArrayList<Config.Channel> savedChannel);
}
