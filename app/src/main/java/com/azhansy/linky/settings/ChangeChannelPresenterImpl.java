package com.azhansy.linky.settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.azhansy.linky.utils.Config;
import com.azhansy.linky.utils.SharePreferenceUtil;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by SHU on 2016/6/25.
 */
public class ChangeChannelPresenterImpl implements IChangeChannelPresenter {
    private IChangeChannel mIChangeChannel;
    private SharedPreferences mSharedPreferences;
    private ArrayList<Config.Channel> savedChannelList;
    private ArrayList<Config.Channel> dismissChannelList;

    public ChangeChannelPresenterImpl(IChangeChannel changeChannel, Context context) {
        mIChangeChannel = changeChannel;
        mSharedPreferences = context.getSharedPreferences(SharePreferenceUtil.SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE);
        savedChannelList = new ArrayList<>();
        dismissChannelList = new ArrayList<>();
    }

    @Override
    public void getChannel() {
        String savedChannel = mSharedPreferences.getString(SharePreferenceUtil.SAVED_CHANNEL, null);
        if (TextUtils.isEmpty(savedChannel)) {
            Collections.addAll(savedChannelList, Config.Channel.values());
        } else {
            for (String s : savedChannel.split(",")) {
                savedChannelList.add(Config.Channel.valueOf(s));
            }
        }
        for (Config.Channel channel : Config.Channel.values()) {
            if (!savedChannelList.contains(channel)) {
                dismissChannelList.add(channel);
            }
        }
        mIChangeChannel.showChannel(savedChannelList, dismissChannelList);
    }

    @Override
    public void saveChannel(ArrayList<Config.Channel> savedChannel) {
        StringBuilder stringBuffer = new StringBuilder();
        for (Config.Channel channel : savedChannel) {
            stringBuffer.append(channel.name()).append(",");
        }
        mSharedPreferences.edit().putString(SharePreferenceUtil.SAVED_CHANNEL, stringBuffer.toString()).apply();
    }
}
