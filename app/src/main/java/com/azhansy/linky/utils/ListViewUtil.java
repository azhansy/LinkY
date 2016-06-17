
package com.azhansy.linky.utils;

import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.annotation.SuppressLint;
import android.os.Build.VERSION;
import android.widget.ExpandableListView;
import android.widget.ListView;


public class ListViewUtil {

    public static boolean issmoothScrollListViewToTop(final ListView listView) {
        if (listView == null) {
            
        } else if (listView.getFirstVisiblePosition()== 0)
        {
            return true;
        }
        return false;
    }

    public static void smoothScrollListViewToTop(final ListView listView) {
        if (listView == null) {
            return;
        }
        smoothScrollListView(listView, 0);
        listView.postDelayed(new Runnable() {

            @Override
            public void run() {
                listView.setSelection(0);
            }
        }, 300);
    }

    @SuppressLint("NewApi")
    public static void smoothScrollListView(final ListView listView, int position) {
        try {
            if (VERSION.SDK_INT >= 11) {
                listView.smoothScrollToPositionFromTop(0, 0);
            } else {
                animationScroll(position, listView);
            }
        } catch (NoSuchMethodError e) {
            // TODO: handle exception
            animationScroll(position, listView);
        }

    }

    @SuppressLint("NewApi")
    public static void smoothScrollExpandListView(final ExpandableListView listView, int position) {
        try {
            if (VERSION.SDK_INT >= 11) {
                listView.smoothScrollToPositionFromTop(0, 0);
            } else {
                animationScroll(position, listView);
            }
        } catch (NoSuchMethodError e) {
            // TODO: handle exception
            animationScroll(position, listView);
        }

    }

    static void animationScroll(int position, final ListView listView) {
        listView.setSelection(position);
        ValueAnimator mValueAnimator = ValueAnimator.ofInt(listView.getFirstVisiblePosition(), position);
        mValueAnimator.addUpdateListener(new AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator arg0) {
                // TODO Auto-generated method stub
                listView.setSelection(
                        (Integer) arg0.getAnimatedValue());
            }
        });
        mValueAnimator.setDuration(listView
                .getFirstVisiblePosition() * 30);
        mValueAnimator.start();
    }

}
