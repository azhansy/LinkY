//package com.azhansy.linky.base;
//
//import android.content.Context;
//import android.util.SparseArray;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * 单个ViewHolder 直接使用。mList改用getList()，mList.get(position)改用getItem(position)
// */
//public abstract class SimpleOneViewHolderBaseAdapter<T> extends BaseAdapter {
//
//    protected Context mContext;
//
//    protected List<T> mList;
//
//    public SimpleOneViewHolderBaseAdapter(Context mContext) {
//        this.mContext = mContext;
//        mList = new ArrayList<>();
//    }
//
//
//    @Override
//    public int getCount() {
//        return mList.size();
//    }
//
//    @Override
//    public T getItem(int position) {
//        return position >= 0 && position < mList.size() ? mList.get(position) : null;
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return position;
//    }
//
//    public void addAll(List<T> elem) {
//        mList.addAll(elem);
//        notifyDataSetChanged();
//    }
//
//    public List<T> getList() {
//        return mList;
//    }
//
//    public void remove(T elem) {
//        mList.remove(elem);
//        notifyDataSetChanged();
//    }
//
//    public void remove(int index) {
//        mList.remove(index);
//        notifyDataSetChanged();
//    }
//
//    public void replaceAll(List<T> elem) {
//        if(elem!=null) {
//            mList.clear();
//            mList.addAll(elem);
//            notifyDataSetChanged();
//        }
//    }
//
//    public void add(T elem) {
//        mList.add(elem);
//        notifyDataSetChanged();
//    }
//
//    public void add(int index, T elem) {
//        mList.add(index, elem);
//        notifyDataSetChanged();
//    }
//
//    public void clear() {
//        mList.clear();
//        notifyDataSetChanged();
//    }
//
//    /**
//     * 使用该getItemView方法替换原来的getView方法，需要子类实现
//     *
//     * @param position
//     * @param convertView
//     * @param holder
//     * @return
//     */
//    public abstract View getView(int position, View convertView, ViewHolder holder);
//
//
//    /**
//     * 该方法需要子类实现，需要返回item布局的resource id
//     *
//     * @return
//     */
//    public abstract int getItemResource();
//
//    @SuppressWarnings("unchecked")
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        ViewHolder holder;
//        if (null == convertView) {
//            convertView = getConvertView(mContext, parent);
//            holder = new ViewHolder(convertView);
//            convertView.setTag(holder);
//        } else {
//            holder = (ViewHolder) convertView.getTag();
//        }
//        return getView(position, convertView, holder);
//    }
//
//    public View getConvertView(Context mContext, ViewGroup parent) {
//        return View.inflate(mContext, getItemResource(), null);
//    }
//
//
//    public static class ViewHolder {
//        private SparseArray<View> views = new SparseArray<View>();
//        private View convertView;
//
//        public ViewHolder(View convertView) {
//            this.convertView = convertView;
//        }
//
//        @SuppressWarnings("unchecked")
//        public <T extends View> T getView(int resId) {
//            View v = views.get(resId);
//            if (null == v && convertView!=null) {
//                v = convertView.findViewById(resId);
//                views.put(resId, v);
//            }
//            return (T) v;
//        }
//    }
//
//}
