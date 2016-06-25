package com.azhansy.linky.settings;

import com.azhansy.linky.utils.Config;

import java.util.ArrayList;

/**
 * Created by SHU on 2016/6/25.
 */
public interface IChangeChannelPresenter {
    void getChannel();

    void saveChannel(ArrayList<Config.Channel> savedChannel);
}
