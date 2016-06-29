package com.azhansy.linky.weather.adapter;

import android.content.Context;

import com.azhansy.linky.R;
import com.azhansy.linky.base.BaseRecyclerViewAdapter;
import com.azhansy.linky.base.BaseRecyclerViewHolder;
import com.azhansy.linky.weather.bean.ForecastBean;

/**
 * Created by SHU on 2016/6/29.
 */
public class ForecastAdapter extends BaseRecyclerViewAdapter<ForecastBean> {
    public ForecastAdapter(Context mContext) {
        super(mContext);
    }

    @Override
    public int getItemResource() {
        return R.layout.item_forecast;
    }

    @Override
    public void showData(BaseRecyclerViewHolder holder, int position) {
        ForecastBean forecastBean = mList.get(position);
        holder.setText(R.id.tv_date, forecastBean.getDate());
        holder.setText(R.id.tv_week, forecastBean.getWeek());
        holder.setText(R.id.tv_type, forecastBean.getType());
        holder.setText(R.id.tv_temp, forecastBean.getLowtemp()+"-"+forecastBean.getHightemp());
        holder.setText(R.id.tv_fengxiang, forecastBean.getFengxiang());
        holder.setText(R.id.tv_fengli, forecastBean.getFengli());
    }
}
