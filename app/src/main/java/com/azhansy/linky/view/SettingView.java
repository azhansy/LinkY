package com.azhansy.linky.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.azhansy.linky.R;

/**
 * Created by SHU on 2016/7/1.
 * 设置页面的自定义控件
 */
public class SettingView extends RelativeLayout {
    /**
     * 标题
     */
    protected String mTitle;
    protected float mTitleSize;
    protected boolean hasArrow;
    protected Drawable mIcon;
    protected TextView titleTv;
    protected ImageView arrow;
    protected ImageView iconImg;
    protected final int DEFAULT_TITLE_SIZE = 16;
    
    public SettingView(Context context) {
        this(context,null);
    }

    public SettingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.SettingView,0,0);
        mTitle = ta.getString(R.styleable.SettingView_sv_title);
        mTitleSize = ta.getDimensionPixelSize(R.styleable.SettingView_sv_title_size, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, DEFAULT_TITLE_SIZE, context.getResources().getDisplayMetrics()));
        hasArrow = ta.getBoolean(R.styleable.SettingView_sv_has_arrow, true);
        mIcon = ta.getDrawable(R.styleable.SettingView_sv_icon);
        ta.recycle();

        inflate(context, R.layout.view_style_setting,this);
        titleTv = (TextView) findViewById(R.id.tv_title);
        arrow = (ImageView) findViewById(R.id.image_arrow);
        iconImg = (ImageView) findViewById(R.id.image_icon);

        titleTv.setText(mTitle);
        titleTv.setTextSize(TypedValue.COMPLEX_UNIT_PX,mTitleSize);
        iconImg.setImageDrawable(mIcon);
        if (hasArrow) {
            arrow.setVisibility(VISIBLE);
        }
    }

}
