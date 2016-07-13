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

import com.azhansy.linky.R;
import com.azhansy.linky.dialog.LoadDialogFragment;
import com.azhansy.linky.utils.DrawableUtil;
import com.azhansy.linky.utils.KeyboardUtil;

import butterknife.ButterKnife;

/**
 * Created by SHU on 2016/6/17.
 */
public abstract class BaseFragment extends Fragment {
    protected abstract int getLayoutResource();
    protected LoadDialogFragment loadingDialog;//添加动画

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadingDialog = new LoadDialogFragment.Builder(this).build();
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
    public void showInput(View view,long delay){
        KeyboardUtil.show(view,delay);
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
    public void showLoadingDialog() {
        if (loadingDialog != null && !loadingDialog.isShowing(this)) {
            loadingDialog.show(this);
        }
    }

    public void closeLoadingDialog() {
        if (loadingDialog != null) {
            loadingDialog.dismiss();
        }
    }
}
