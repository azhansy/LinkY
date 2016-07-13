package com.azhansy.linky.weekly;

import android.content.Context;

import com.azhansy.linky.R;
import com.azhansy.linky.base.BaseRecyclerViewAdapter;
import com.azhansy.linky.base.BaseRecyclerViewHolder;

/**
 * Created by SHU on 2016/7/13.
 */
public class WeeklyNewsAdapter extends BaseRecyclerViewAdapter<WeeklyModel> {
    public WeeklyNewsAdapter(Context mContext) {
        super(mContext);
    }

    @Override
    public int getItemResource() {
        return R.layout.item_time_title_content;
    }

    @Override
    public void showData(BaseRecyclerViewHolder holder, int position) {
        holder.setText(R.id.tv_title, mList.get(position).getTitle());
        holder.setText(R.id.tv_description, mList.get(position).getContent());
        holder.setText(R.id.tv_time, mList.get(position).getTime());
    }
}
