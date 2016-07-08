package com.azhansy.linky.blog;

import android.content.Context;

import com.azhansy.linky.R;
import com.azhansy.linky.base.BaseRecyclerViewAdapter;
import com.azhansy.linky.base.BaseRecyclerViewHolder;
import com.azhansy.linky.blog.model.BlogItem;

/**
 * Created by SHU on 2016/6/17.
 */
public class BlogAdapter extends BaseRecyclerViewAdapter<BlogItem> {

    public BlogAdapter(Context mContext) {
        super(mContext);
    }

    @Override
    public int getItemResource() {
        return R.layout.common_item__title_content;
    }

    @Override
    public void showData(BaseRecyclerViewHolder holder, int position) {
        holder.setText(R.id.tv_title, mList.get(position).getTitle());
//        holder.setText(R.id.tv_description, mList.get(position).getContent());
        holder.setText(R.id.tv_time, mList.get(position).getDate());
    }
}