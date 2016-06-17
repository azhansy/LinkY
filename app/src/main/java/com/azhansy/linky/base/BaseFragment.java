package com.azhansy.linky.base;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ListView;

import com.azhansy.linky.R;
import com.azhansy.linky.utils.DrawableUtil;
import com.azhansy.linky.utils.ListViewUtil;

import butterknife.ButterKnife;

/**
 * Created by SHU on 2016/6/17.
 */
public abstract class BaseFragment extends Fragment{
    protected abstract int getLayoutResource();
    protected Activity mActivity;   //全局的activity的引用，避免内存回收时导致getActivity()为null的情况

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity=getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        int resLayoutId = getLayoutResource();
        if (resLayoutId != 0) {
            View view = inflater.inflate(resLayoutId, container, false);
            ButterKnife.bind(this, view);
            return view;
        }
        return null;
    }

    public void hideInput() {
        if (getActivity() == null || getActivity().getCurrentFocus() == null) {
            return;
        }
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getActivity().getCurrentFocus()
                .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }
    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        int size = menu.size();
        for (int i = 0; i < size; i++) {
            MenuItem menuItem = menu.getItem(i);
            final Drawable moreMenuDrawable = menuItem.getIcon();

            if (moreMenuDrawable != null) {
                menuItem.setIcon(DrawableUtil.getThemeDrawable(getActivity(), moreMenuDrawable, R.attr.actionMenuTextColor));

            }
        }

        super.onPrepareOptionsMenu(menu);
    }
    /**
     * 检查activity是否存在
     *
     * @return
     */
    public boolean checkActivity() {
        Activity activity = getActivity();
        return isAdded() && activity != null;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }






    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    /**
     * Get string extra from activity's intent
     *
     * @param name
     * @return extra
     */
    protected String getStringExtra(final String name) {
        Activity activity = getActivity();
        if (activity != null)
            return activity.getIntent().getStringExtra(name);
        else
            return null;
    }



    /**
     * 如果存在ListView的点击往上顶
     */
    public void onListViewTop() {
        ListView mListView = (ListView) getView().findViewById(getSupportSmoothScrollToTopListViewId());
        if (mListView != null) {
            ListViewUtil.smoothScrollListViewToTop(mListView);
        }
    }

    protected int getSupportSmoothScrollToTopListViewId() {
        /**
         * TODO 这里移植过来的时候要报错，且不太明白以前的用意，所以这里返回空的
         */
        return 0;
    }

    public ListView getSupportSmoothScrollToTopListView() {
        return null;
    }
}
