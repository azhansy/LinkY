package com.azhansy.linky.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.SearchView;
import android.util.AttributeSet;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.azhansy.linky.R;

import java.lang.reflect.Field;

/**
 * Created by SHU on 2016/7/6.
 * 自定义搜索框,使用如下
 * inflater.inflate(R.menu.menu_search,menu);
 * MenuItem searchItem = menu.findItem(R.id.action_search);
 * searchView = (CommonSearchView) MenuItemCompat.getActionView(searchItem);
 * ((CommonSearchView) MenuItemCompat.getActionView(searchItem)).setMaxWidth(Integer.MAX_VALUE);
 * searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
 *
 * @Override public boolean onQueryTextSubmit(String query) {
 * Logger.d("onQueryTextSubmit"+query);
 * return false;
 * }
 * @Override public boolean onQueryTextChange(String newText) {
 * Logger.d("onQueryTextChange"+newText);
 * return false;
 * }
 * });
 */
public class CommonSearchView extends SearchView implements Runnable {
    private OnQueryTextListener onQueryTextListener;
    private final int searchChangeEffect = 600;
    private String newText = "";
    EditText editText; //搜索框的EditText

    public CommonSearchView(Context context) {
        this(context, null);
    }

    public CommonSearchView(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.searchViewStyle);
    }

    public CommonSearchView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setQueryHint("搜索");
        setIconifiedByDefault(false);
        ImageView msg_icon = (ImageView) findViewById(R.id.search_mag_icon);
        msg_icon.setImageResource(R.mipmap.ic_menu_search);
        findViewById(R.id.search_edit_frame).setBackgroundResource(R.drawable.ab_transparent_common_solid);
        editText = (EditText) findViewById(R.id.search_src_text);
        editText.requestFocus();
        editText.setHintTextColor(Color.WHITE);

        super.setOnQueryTextListener(new OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (onQueryTextListener != null) {
                    return onQueryTextListener.onQueryTextSubmit(query);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                removeCallbacks(CommonSearchView.this);
                CommonSearchView.this.newText = newText;
                postDelayed(CommonSearchView.this, searchChangeEffect);
                return false;
            }
        });
        try {
            Field mCursorDrawableRes = TextView.class.getDeclaredField("mCursorDrawableRes");
            mCursorDrawableRes.setAccessible(true);
            mCursorDrawableRes.set(editText, R.drawable.shape_search_cursor); //This sets the cursor resource ID to 0 or @null which will make it visible on white background
        } catch (Exception e) {
        }
        requestFocus();
    }

    @Override
    public void run() {
        if (getContext() instanceof Activity) {
            if (((Activity) getContext()).isFinishing()) {
                return;
            }
        }
        if (onQueryTextListener != null) {
            onQueryTextListener.onQueryTextChange(newText);
        }
    }


    public static class SimpleQueryTextListener implements OnQueryTextListener {

        @Override
        public boolean onQueryTextSubmit(String query) {
            return false;
        }

        @Override
        public boolean onQueryTextChange(String newText) {
            return false;
        }
    }

    private OnSearchListener onSearchListener;

    public void setOnSearchListener(OnSearchListener onSearchListener) {
        this.onSearchListener = onSearchListener;
    }

    public String getText() {
        return editText.getText().toString();
    }

    public EditText getEditText() {
        return editText;
    }

    public interface OnSearchListener {
        void onSearch();
    }

    @Override
    public void setOnQueryTextListener(OnQueryTextListener listener) {
        this.onQueryTextListener = listener;
    }

    public EditText getInnerEdit() {
        return editText;
    }
}
