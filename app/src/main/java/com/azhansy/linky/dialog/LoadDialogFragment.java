package com.azhansy.linky.dialog;


import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import com.azhansy.linky.R;

/**
 * Created by SHU on 2016/7/13.
 * 加载动画dialog
 */
public class LoadDialogFragment extends DialogFragment {
    private ViewGroup mRootLayout; //容器
    private ImageView mCircleView; //转动的圆圈
    // 对话框是否可以取消
    private boolean mCancelable = true;
    // 点击对话框外部是否可以取消
    private boolean mOutsideCancelable = false;
    // 在对话框显示的时候按返回键的监听事件
    private OnPressBackListener mOnPressBackListener;
    private String mTag;
    private boolean mIsAdded = false;
    /**
     * 默认圆圈动画时间，单位毫秒
     */
    private static final long DEFAULT_ANIM_DURATION = 800;
    private Long mAnimDuration;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.view_load_dialog,container);
        mRootLayout = (ViewGroup) view.findViewById(R.id.dialog_root_layout);
        mCircleView = (ImageView) view.findViewById(R.id.dialog_circle_image_view);
        mAnimDuration = DEFAULT_ANIM_DURATION;
        setLoadingAimationDuration(mAnimDuration);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL,R.style.dialog);
    }

    /**
     * 设置动画
     * @param defaultAnimDuration 一圈旋转动画时间
     */
    private void setLoadingAimationDuration(long defaultAnimDuration) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(mCircleView, "rotation", 0, 359);
        animator.setDuration(defaultAnimDuration);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setRepeatMode(ValueAnimator.RESTART);
        animator.setInterpolator(new LinearInterpolator());
        animator.start();
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Dialog dialog = getDialog();
        if (dialog != null) {
            dialog.setCancelable(mCancelable);
            dialog.setCanceledOnTouchOutside(mOutsideCancelable);
        }
        setupListener();
    }

    private void setupListener() {
        if (mOnPressBackListener != null) {
            View view = this.getView();
            Dialog dialog = getDialog();
            if (view != null && dialog != null) {
                dialog.setCancelable(true);
                dialog.setCanceledOnTouchOutside(false);
                view.setFocusableInTouchMode(true);
                view.setOnKeyListener(new View.OnKeyListener() {

                    @Override
                    public boolean onKey(View v, int keyCode, KeyEvent event) {
                        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP && mOnPressBackListener != null) {
                            mOnPressBackListener.onPressBack(LoadDialogFragment.this);
                            return true;
                        }
                        return false;
                    }
                });
            }
        }
    }
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public boolean isShowing(Fragment fragment) {
        if (fragment == null) {
            return true;
        }
        FragmentManager manager = fragment.getChildFragmentManager();
        Fragment f = manager.findFragmentByTag(mTag);
        return f != null && f.isAdded() && mIsAdded;
    }
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void show(final Fragment fragment) {
        if (mTag == null || fragment == null || mIsAdded) {
            return;
        }
        if (!isShowing(fragment)) {
            showInternal(fragment.getChildFragmentManager(), mTag);
        }
    }
    private void showInternal(FragmentManager manager, String tag) {
        try {
            super.show(manager, tag);
            mIsAdded = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void cancel() {
        dismiss();
    }

    @Override
    public void dismiss() {
        if (mRootLayout != null) {
            mRootLayout.removeView(mCircleView);
        }
        try {
            super.dismiss();
        } catch (Exception e) {
            e.printStackTrace();
            dismissAllowingStateLoss();
        }
    }

    @Override
    public void dismissAllowingStateLoss() {
        if (mRootLayout != null) {
            mRootLayout.removeView(mCircleView);
        }
        try {
            super.dismissAllowingStateLoss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static class Builder{
        private boolean cancelable = true;
        private boolean outsideCancelable = false;
        private long duration;
        private Object tagObj;
        public Builder(Object tag){
            this.tagObj = tag;
        }
        public Builder cancelable(boolean cancelable) {
            this.cancelable = cancelable;
            return this;
        }
        public Builder outsideCancelable(boolean outsideCancelable) {
            this.outsideCancelable = outsideCancelable;
            return this;
        }
        /**
         * 设置对话框上动画的周期，单位毫秒
         * @param duration
         * @return
         */
        public Builder duration(long duration) {
            this.duration = duration;
            return this;
        }
        public LoadDialogFragment build() {
            LoadDialogFragment fragment = new LoadDialogFragment();
            fragment.mTag = getTag(tagObj);
            fragment.mAnimDuration = duration;
            fragment.mCancelable = cancelable;
            fragment.mOutsideCancelable = outsideCancelable;
            return fragment;
        }
        private String getTag(Object tagObj) {
            if (tagObj == null) {
                return null;
            }
            return tagObj.getClass().getName() + '@' + Integer.toHexString(tagObj.hashCode());
        }
    }



    public interface OnPressBackListener {
        void onPressBack(LoadDialogFragment fragment);
    }
}
