package com.azhansy.linky.weather;

import android.content.Context;
import android.view.View;

import com.azhansy.linky.base.SimpleOneViewHolderBaseAdapter;
import com.azhansy.linky.weather.bean.TodayBean;

/**
 * Created by SHU on 2016/6/17.
 */
public class WeatherAdapter extends SimpleOneViewHolderBaseAdapter<TodayBean> {
    public WeatherAdapter(Context mContext) {
        super(mContext);
    }

    @Override
    public View getView(int position, View convertView, ViewHolder holder) {
        return null;
    }

    @Override
    public int getItemResource() {
        return 0;
    }
}
