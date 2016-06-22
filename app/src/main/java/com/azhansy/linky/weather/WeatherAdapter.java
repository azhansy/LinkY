package com.azhansy.linky.weather;

import android.content.Context;

import com.azhansy.linky.R;
import com.azhansy.linky.base.BaseRecyclerViewAdapter;
import com.azhansy.linky.base.BaseRecyclerViewHolder;
import com.azhansy.linky.weather.bean.IndexBean;


/**
 * Created by SHU on 2016/6/17.
 */
public class WeatherAdapter extends BaseRecyclerViewAdapter<IndexBean> {

    public WeatherAdapter(Context mContext) {
        super(mContext);
    }

    @Override
    public int getItemResource() {
        return R.layout.list_item_weather;
    }

    @Override
    public void showData(BaseRecyclerViewHolder holder, int position) {
        IndexBean indexBean = mList.get(position);
        holder.setText(R.id.tv_listname, indexBean.getName());
        holder.setText(R.id.tv_index,indexBean.getIndex());
        holder.setText(R.id.tv_details, indexBean.getDetails());
    }
}