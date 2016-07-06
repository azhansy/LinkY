package com.azhansy.linky.culture.adapter;

import android.content.Context;

import com.azhansy.linky.R;
import com.azhansy.linky.base.BaseRecyclerViewAdapter;
import com.azhansy.linky.base.BaseRecyclerViewHolder;
import com.azhansy.linky.culture.model.ChengyuModel;

/**
 * Created by SHU on 2016/7/4.
 */
public class ChengyuAdapter extends BaseRecyclerViewAdapter<ChengyuModel.ChengyuDetailModel> {
    public ChengyuAdapter(Context mContext) {
        super(mContext);
    }

    @Override
    public int getItemResource() {
        return R.layout.item_cheng_yu;
    }

    @Override
    public void showData(BaseRecyclerViewHolder holder, int position) {
        holder.setText(R.id.tv_name,mList.get(position).getName(),getHighLightText());
    }
}
