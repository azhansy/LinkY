package com.azhansy.linky.base;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import com.azhansy.linky.R;
import com.azhansy.linky.dialog.LoadDialogFragment;
import com.azhansy.linky.tedPermission.PermissionCheckHandler;
import com.azhansy.linky.tedPermission.PermissionCheckHelper;
import com.azhansy.linky.utils.DrawableUtil;
import com.azhansy.linky.utils.KeyboardUtil;

import butterknife.ButterKnife;

/**
 * Created by SHU on 2016/6/17.
 */
public abstract class BaseFragment extends Fragment {
    protected abstract int getLayoutResource();
    protected LoadDialogFragment  loadingDialog;//添加动画
    private PermissionCheckHandler mPermissionCheckHandler;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadingDialog = new LoadDialogFragment.Builder(this).build();
        initCheckPermissionHelper(getActivity());
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

    /**
     * 显示加载动画
     */
    public void showLoadingDialog() {
        if (loadingDialog != null && !loadingDialog.isShowing(this)) {
            loadingDialog.show(this);
        }
    }

    /**
     * 关闭加载动画
     */
    public void closeLoadingDialog() {
        if (loadingDialog != null) {
            loadingDialog.dismiss();
        }
    }
    // ===================== 6.0 权限适配相关代码 start ===================== //

    protected void initCheckPermissionHelper(Context context) {
        mPermissionCheckHandler = PermissionCheckHandler.get(context);
        mPermissionCheckHandler.addCheckMustPermissionExtraListener(mCheckMustPermissionExtraListener);
//        System.out.println("Fragment helper -> " + mPermissionCheckHandler + " " + this.getClass().getSimpleName());
    }

    protected void onMustPermissionGranted(PermissionCheckHelper helper, String permission, boolean end, boolean returnValue) {
    }

    protected void onMustPermissionDenied(PermissionCheckHelper helper, String permission, boolean returnValue) {
    }

    protected PermissionCheckHandler getPermissionCheckHandler() {
        return mPermissionCheckHandler;
    }

    /**
     * 检测单个权限
     */
    protected void checkUserPermission(String permission, @StringRes int denyMessageResId, PermissionCheckHelper.PermissionCheckListener l) {
        checkUserPermission(permission, getString(denyMessageResId), l);
    }

    /**
     * 检测单个权限
     */
    protected void checkUserPermission(String permission, String denyMessage, PermissionCheckHelper.PermissionCheckListener l) {
        if (TextUtils.isEmpty(permission)) {
            throw new IllegalArgumentException("permission can't be null");
        }
        if (l == null) {
            throw new IllegalArgumentException("permission listener can't be null");
        }
        PermissionCheckHelper.PermissionTask task = new PermissionCheckHelper.PermissionTask.Builder(getActivity(), permission)
                .setDenyMessage(denyMessage)
                .build();
        mPermissionCheckHandler.checkUserPermission(l, task);
    }

    private PermissionCheckHandler.CheckMustPermissionExtraListener mCheckMustPermissionExtraListener = new PermissionCheckHandler.CheckMustPermissionExtraListener() {
        @Override
        public void onMustPermissionGranted(PermissionCheckHelper helper, String permission, boolean end, boolean returnValue) {
            BaseFragment.this.onMustPermissionGranted(helper, permission, end, returnValue);
        }

        @Override
        public void onMustPermissionDenied(PermissionCheckHelper helper, String permission, boolean returnValue) {
            BaseFragment.this.onMustPermissionDenied(helper, permission, returnValue);
        }
    };

    // ===================== 6.0 权限适配相关代码 start ===================== //
}
