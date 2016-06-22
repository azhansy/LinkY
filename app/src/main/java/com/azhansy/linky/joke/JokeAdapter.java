package com.azhansy.linky.joke;

import android.content.Context;

import com.azhansy.linky.R;
import com.azhansy.linky.base.BaseRecyclerViewAdapter;
import com.azhansy.linky.base.BaseRecyclerViewHolder;
import com.azhansy.linky.joke.bean.JokeBean;

/**
 * Created by SHU on 2016/6/22.
 */
public class JokeAdapter extends BaseRecyclerViewAdapter<JokeBean> {

    public JokeAdapter(Context mContext) {
        super(mContext);
    }

    @Override
    public int getItemResource() {
        return R.layout.list_item_joke;
    }

    @Override
    public void showData(BaseRecyclerViewHolder holder, int position) {
        JokeBean jokeBean = mList.get(position);
        holder.setText(R.id.tv_title, jokeBean.getTitle());
        holder.setText(R.id.tv_time, jokeBean.getTime());
        holder.setText(R.id.tv_details, jokeBean.getText());
    }
}
