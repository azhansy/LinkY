package com.azhansy.linky.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by SHU on 2016/6/22.
 */
public abstract class BaseRecyclerViewAdapter<T> extends RecyclerView.Adapter<BaseRecyclerViewHolder> {
    protected Context mContext;
    protected List<T> mList;

    public BaseRecyclerViewAdapter(Context mContext) {
        this.mContext = mContext;
        mList = new ArrayList<>();
    }

    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(View view, Object data, int position);
    }
    public void setOnRecycleViewItemClickListener(OnItemClickListener lis){
        this.listener = lis;
    }

    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new BaseRecyclerViewHolder(getConvertView(mContext, parent));
    }

    public View getConvertView(Context mContext, ViewGroup parent) {
        return View.inflate(mContext, getItemResource(), null);
    }

    /**
     * 该方法需要子类实现，需要返回item布局的resource id
     *
     * @return
     */
    public abstract int getItemResource();

    @Override
    public void onBindViewHolder(BaseRecyclerViewHolder holder, int position) {
        showData(holder, position);
        holder.itemView.setOnClickListener(getOnClickListener(position));
    }

    public View.OnClickListener getOnClickListener(final int position) {
        return v -> {
            if (listener != null && v != null) {
                listener.onItemClick(v, mList.get(position), position);
            }
        };
    }

    public abstract void showData(BaseRecyclerViewHolder holder, int position);

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void addAll(List<T> elem) {
        mList.addAll(elem);
        notifyDataSetChanged();
    }
//    public void addList(List<T> elem){
//        for (T t : elem) {
//            mList.add(t);
//        }
//        notifyDataSetChanged();
//    }

    public List<T> getList() {
        return mList;
    }

    public void remove(T elem) {
        mList.remove(elem);
        notifyDataSetChanged();
    }

    public void remove(int index) {
        mList.remove(index);
        notifyDataSetChanged();
    }

    public void replaceAll(List<T> elem) {
        if (elem != null) {
            mList.clear();
            mList.addAll(elem);
            notifyDataSetChanged();
        }
    }

    public void add(T elem) {
        mList.add(elem);
        notifyDataSetChanged();
    }

    public void add(int index, T elem) {
        mList.add(index, elem);
        notifyDataSetChanged();
    }

    public void clear() {
        mList.clear();
        notifyDataSetChanged();
    }
}
