package com.azhansy.linky.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.util.TypedValue;
import android.view.View;

import com.azhansy.linky.R;


/**
 * 主题drawable工具类
 */
public class DrawableUtil {


    public static Drawable getThemeDrawable(Context mContext, int drawableRes) {
        return getThemeDrawable(mContext, ContextCompat.getDrawable(mContext, drawableRes));
    }


    public static Drawable getWhiteDrawable(Drawable drawable) {
        if (drawable == null) {
            return null;
        }
        drawable.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
        return drawable;
    }

    public static Drawable getBlackDrawable(Drawable drawable) {
        if (drawable == null) {
            return null;
        }
        drawable.setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_ATOP);
        return drawable;
    }

    public static Drawable getThemeDrawable(Context mContext, Drawable drawable, int attr) {
        if (drawable == null) {
            return null;
        }
        drawable.setColorFilter(getThemeColor(mContext, attr), PorterDuff.Mode.SRC_ATOP);

        return drawable;
    }

    public static Drawable getThemeDrawable(Context mContext, int drawableRes, int attr) {
        Drawable drawable = ContextCompat.getDrawable(mContext, drawableRes);
        if (drawable == null) {
            return null;
        }
        drawable.setColorFilter(getThemeColor(mContext, attr), PorterDuff.Mode.SRC_ATOP);

        return drawable;
    }

    public static Drawable getThemeDrawable(Context mContext, Drawable drawable) {
        if (drawable == null) {
            return null;
        }
        drawable.setColorFilter(getPrimaryColor(mContext), PorterDuff.Mode.SRC_ATOP);

        return drawable;
    }

    public static Drawable getThemeDrawableDark(Context mContext, Drawable drawable) {
        if (drawable == null) {
            return null;
        }
        drawable.setColorFilter(getPrimaryDarkColor(mContext), PorterDuff.Mode.SRC_ATOP);
        return drawable;
    }


    public static int getThemeColor(Context mContext, int attr) {
        TypedValue typedValue = new TypedValue();
        mContext.getTheme().resolveAttribute(attr, typedValue, true);
        return mContext.getResources().getColor(typedValue.resourceId);
    }

    public static int getPrimaryColor(Context mContext) {
        TypedValue typedValue = new TypedValue();
        mContext.getTheme().resolveAttribute(R.attr.colorPrimary, typedValue, true);
        return mContext.getResources().getColor(typedValue.resourceId);
    }

    public static int getResourceId(Context context, int attr) {
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(attr, typedValue, true);
        return typedValue.resourceId;
    }

    public static int getPrimaryDarkColor(Context mContext) {
        TypedValue typedValue = new TypedValue();
        mContext.getTheme().resolveAttribute(R.attr.colorPrimaryDark, typedValue, true);
        return mContext.getResources().getColor(typedValue.resourceId);
    }

    /**
     * 获取点击效果的drawable
     * <p>
     * 颜色值传0，代表使用主题色
     *
     * @param normalColor       正常效果的颜色
     * @param pressColor        按下的颜色
     * @param checkColor        check的颜色
     * @param selectColor       select的颜色
     * @param cornerDip         角度
     * @param strokeWidthDip    描边的宽度
     * @param normalStrokeColor 正常状态描边的颜色
     * @param otherStrokeColor  其他状态描边的颜色
     */
    public static StateListDrawable getThemeShape(Context context, int normalColor, int pressColor, int checkColor, int selectColor, int cornerDip,
                                                  int strokeWidthDip, int normalStrokeColor, int otherStrokeColor) {
        TypedValue value = new TypedValue();

        strokeWidthDip = Math.max(0, strokeWidthDip);

        if (cornerDip < 0) {
            cornerDip = 4;
        }

        context.getTheme().resolveAttribute(R.attr.colorPrimary, value, true);

        if (normalColor == -1) {
            normalColor = value.data;
        }

        if (normalStrokeColor == -1) {
            normalStrokeColor = value.data;
        }

        context.getTheme().resolveAttribute(R.attr.colorPrimaryDark, value, true);

        if (pressColor == -1) {
            pressColor = value.data;
        }

        if (checkColor <= -1) {
            checkColor = value.data;
        }

        if (selectColor == -1) {
            selectColor = value.data;
        }

        if (otherStrokeColor == -1) {
            otherStrokeColor = value.data;
        }

        GradientDrawable normalDrawable = buildGradientDrawable(context, normalColor, cornerDip, strokeWidthDip, normalStrokeColor);

        GradientDrawable pressDrawable = buildGradientDrawable(context, pressColor, cornerDip, strokeWidthDip, otherStrokeColor);
        GradientDrawable checkDrawable = buildGradientDrawable(context, checkColor, cornerDip, strokeWidthDip, otherStrokeColor);
        GradientDrawable selectDrawable = buildGradientDrawable(context, selectColor, cornerDip, strokeWidthDip, otherStrokeColor);

        StateListDrawable states = new StateListDrawable();
        states.addState(new int[]{android.R.attr.state_pressed}, pressDrawable);
        states.addState(new int[]{android.R.attr.state_checked}, checkDrawable);
        states.addState(new int[]{android.R.attr.state_selected}, selectDrawable);
        states.addState(new int[]{}, normalDrawable);

        return states;
    }

    private static GradientDrawable buildGradientDrawable(Context context, int color, int cornerDip, int strokeWidthDip, int strokeColor) {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setColor(color);
        drawable.setCornerRadius(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, cornerDip, context.getResources().getDisplayMetrics()));
        drawable.setStroke((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, strokeWidthDip, context.getResources().getDisplayMetrics()), strokeColor);
        return drawable;
    }

    public static void setBackgroundDrawable(View view, Drawable drawable) {
        if (view == null) {
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            view.setBackground(drawable);
        } else {
            view.setBackgroundDrawable(drawable);
        }
    }

    public static Bitmap drawableToBitmap(Drawable drawable) {
        Bitmap bitmap = Bitmap.createBitmap(
                drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(),
                drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                        : Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        //canvas.setBitmap(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return bitmap;

    }

    /**
     * 日程/记事/资讯 分类item背景
     */
    public static StateListDrawable generateCommonTypeDrawable(Context mContext) {
        StateListDrawable stateListDrawable = new StateListDrawable();
        Drawable normalDrawable = buildGradientDrawable(mContext, 0xFFF2F2F2, 17, 0, 0);
        Drawable selectedDrawable = buildGradientDrawable(mContext, getPrimaryColor(mContext), 17, 0, 0);
        stateListDrawable.addState(new int[]{android.R.attr.state_pressed}, selectedDrawable);
        stateListDrawable.addState(new int[]{android.R.attr.state_selected}, selectedDrawable);
        stateListDrawable.addState(new int[]{}, normalDrawable);
        return stateListDrawable;
    }

    /**
     * 资讯不选分类，虚线框样式
     */
//    public static StateListDrawable generateCommonTypeDashDrawable(Context mContext) {
//        StateListDrawable stateListDrawable = new StateListDrawable();
//        Drawable normalDrawable = ContextCompat.getDrawable(mContext, R.drawable.shape_news_no_choose_type_rect_dash);
//        Drawable selectedDrawable = buildGradientDrawable(mContext, getPrimaryColor(mContext), 17, 0, 0);
//        stateListDrawable.addState(new int[]{android.R.attr.state_pressed}, selectedDrawable);
//        stateListDrawable.addState(new int[]{android.R.attr.state_selected}, selectedDrawable);
//        stateListDrawable.addState(new int[]{}, normalDrawable);
//        return stateListDrawable;
//    }

    /**
     * 资讯添加分类，主题线框样式
     */
    public static StateListDrawable generateCommonTypeAddDrawable(Context mContext) {
        StateListDrawable stateListDrawable = new StateListDrawable();
        Drawable normalDrawable = buildGradientDrawable(mContext, 0, 17, 1, getPrimaryColor(mContext));
        Drawable selectedDrawable = buildGradientDrawable(mContext, getPrimaryColor(mContext), 17, 0, 0);
        stateListDrawable.addState(new int[]{android.R.attr.state_pressed}, selectedDrawable);
        stateListDrawable.addState(new int[]{android.R.attr.state_selected}, selectedDrawable);
        stateListDrawable.addState(new int[]{}, normalDrawable);
        return stateListDrawable;
    }
}
