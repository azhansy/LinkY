package com.azhansy.linky.column;

import com.azhansy.linky.utils.Config;

import java.util.ArrayList;

/**
 * Created by SHU on 2016/6/25.
 */
public interface IChangeChannel {
    void showChannel(ArrayList<Config.Channel> savedChannel, ArrayList<Config.Channel> otherChannel);
}
