package com.azhansy.linky.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;

/**
 * Created by SHU on 2016/6/30.
 */
public class ActionModeWebView extends WebView {
    public ActionModeWebView(Context context) {
        super(context);
    }

    public ActionModeWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Nullable
    @Override
    public ActionMode startActionMode(ActionMode.Callback callback) {
        CustomizedSelectActionModeCallback customizedSelectActionModeCallback = new CustomizedSelectActionModeCallback(
                callback);
        return startActionModeForChild(this, customizedSelectActionModeCallback);
    }

    public class CustomizedSelectActionModeCallback implements ActionMode.Callback {
        private ActionMode.Callback callback;

        public CustomizedSelectActionModeCallback(ActionMode.Callback callback) {
            this.callback = callback;
        }

        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            return callback.onCreateActionMode(mode, menu);
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            int size = menu.size();
            for (int i = 0; i < size; i++) {
                MenuItem menuItem = menu.getItem(i);
                final Drawable moreMenuDrawable = menuItem.getIcon();
                if (moreMenuDrawable != null) {
                    menuItem.setIcon(DrawableUtil.getThemeDrawable(getContext(), moreMenuDrawable));
                }
            }
            return callback.onPrepareActionMode(mode, menu);
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            return true;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            callback.onDestroyActionMode(mode);
        }
    }
}

