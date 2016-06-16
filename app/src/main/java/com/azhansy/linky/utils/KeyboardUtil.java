package com.azhansy.linky.utils;

import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;

import static android.content.Context.INPUT_METHOD_SERVICE;


/**
 * Keyboard utilities
 */
public class KeyboardUtil {

    public static void show(final View view, long delay) {
        if (view == null) {
            return;
        }
        InputMethodManager manager = (InputMethodManager) view.getContext().getSystemService(INPUT_METHOD_SERVICE);
        if (view.getWidth() == 0 && view.getHeight() == 0) {
            view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    Utils.removeViewTreeObserver(view, this);
                    if (delay <= 0) {
                        manager.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
                    } else {
                        view.postDelayed(() -> manager.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT), delay);
                    }
                }
            });
        } else {
            if (delay <= 0) {
                manager.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
            } else {
                view.postDelayed(() -> manager.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT), delay);
            }
        }

    }

    public static void hide(final View view) {
        if (view == null) {
            return;
        }
        InputMethodManager manager = (InputMethodManager) view.getContext().getSystemService(INPUT_METHOD_SERVICE);
        if (manager != null) {
            manager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

} 