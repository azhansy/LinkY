package com.azhansy.linky.culture.adapter;

import android.content.Context;

import com.azhansy.linky.R;
import com.azhansy.linky.base.BaseRecyclerViewAdapter;
import com.azhansy.linky.base.BaseRecyclerViewHolder;
import com.azhansy.linky.culture.model.MingrenModel;

/**
 * Created by SHU on 2016/7/4.
 */
public class MingrenAdapter extends BaseRecyclerViewAdapter<MingrenModel.MingrenDetailModel> {
    public MingrenAdapter(Context mContext) {
        super(mContext);
    }

    @Override
    public int getItemResource() {
        return R.layout.item_ming_ren;
    }

    @Override
    public void showData(BaseRecyclerViewHolder holder, int position) {
        holder.setText(R.id.tv_famous_name, mList.get(position).getFamous_name(), getHighLightText());
        holder.setText(R.id.tv_famous_saying, mList.get(position).getFamous_saying(), getHighLightText());
    }
}
