package com.azhansy.linky.weekly;

import com.azhansy.linky.base.MVP.BaseRetrofitService;
import com.azhansy.linky.utils.Config;

/**
 * Created by SHU on 2016/7/13.
 */
public class WeeklyService extends BaseRetrofitService<WeeklyApi> {
    public WeeklyService(boolean isJson) {
        super(isJson);
    }
    @Override
    protected String getBaseUrl() {
        return Config.base_url_weekly;
    }

    //在访问HttpRetrofit2时创建单例
    private static class SingletonHolder {
        private static final WeeklyService INSTANCE = new WeeklyService(false);
    }

    //获取单例
    public static WeeklyService getInstance() {
        return SingletonHolder.INSTANCE;
    }
}
