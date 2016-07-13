package com.azhansy.linky.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.telephony.TelephonyManager;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.TextView;


import com.azhansy.linky.R;
import com.azhansy.linky.citypicker.utils.ToastUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by luo_shuai on 2015/8/8 10:13.
 */
public class Utils {

    /**
     * 移除View上的ViewTreeObserver，兼容方法
     */
    public static void removeViewTreeObserver(View view, ViewTreeObserver.OnGlobalLayoutListener l) {
        if (view != null && view.getViewTreeObserver() != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                view.getViewTreeObserver().removeOnGlobalLayoutListener(l);
            } else {
                view.getViewTreeObserver().removeGlobalOnLayoutListener(l);
            }
        }
    }


    /**
     * 进入拨号页面
     *
     * @param mobile 手机号
     */
    public static void dialMobile(Context context, String mobile) {
        Uri uri = Uri.parse("tel:" + mobile);
        Intent intent = new Intent(Intent.ACTION_DIAL, uri);
        context.startActivity(intent);
    }

    public static SpannableString getSpannableString(String text, String queryString, int colorResource) {
        int startIndex = text.toLowerCase().indexOf(queryString.toLowerCase());
        int endIndex = startIndex + queryString.length();
        SpannableString spanText = new SpannableString(text);
        if (startIndex > -1) {
            spanText.setSpan(new ForegroundColorSpan(colorResource), startIndex, endIndex, 0);
        }
        return spanText;
    }

    public static int getPrimaryColor(Context context) {
        TypedValue value = new TypedValue();
        context.getTheme().resolveAttribute(R.attr.colorPrimary, value, true);
        return value.data;
    }

    public static int getColorByAttr(Context context, int attr) {
        TypedValue value = new TypedValue();
        context.getTheme().resolveAttribute(attr, value, true);
        return value.data;
    }

    public static String getDeviceId(Context context) {
        final TelephonyManager tm = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);

        final String tmDevice;

        tmDevice = "" + tm.getDeviceId();
        String uniqueId = tmDevice;
        return uniqueId;

    }


    /**
     * 通过包名检查程序是否已经在手机安装
     *
     * @param context
     * @param packageName
     * @return
     */
    public static boolean isAppInstalled(Context context, String packageName) {
        final PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
        List<String> pName = new ArrayList<String>();
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                pName.add(pn);
            }
        }
        return pName.contains(packageName);
    }


    /**
     * \
     * 将火星坐标系转换成百度坐标系
     *
     * @param gg_lat
     * @param gg_lon
     * @return
     */
    public static double[] bd_encrypt(double gg_lat, double gg_lon) {
        double x_pi = 3.14159265358979324 * 3000.0 / 180.0;
        double bd_lat, bd_lon;
        double x = gg_lon, y = gg_lat;
        double z = Math.sqrt(x * x + y * y) + 0.00002 * Math.sin(y * x_pi);
        double theta = Math.atan2(y, x) + 0.000003 * Math.cos(x * x_pi);
        bd_lon = z * Math.cos(theta) + 0.0065;
        bd_lat = z * Math.sin(theta) + 0.006;
        double[] result = new double[]{bd_lat, bd_lon};
        return result;
    }

    /**
     * 将百度坐标系转换成火星坐标系
     *
     * @param bd_lat
     * @param bd_lon
     * @return
     */
    public static double[] bd_decrypt(double bd_lat, double bd_lon) {
        double x_pi = 3.14159265358979324 * 3000.0 / 180.0;
        double gg_lat, gg_lon;
        double x = bd_lon - 0.0065, y = bd_lat - 0.006;
        double z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * x_pi);
        double theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * x_pi);
        gg_lon = z * Math.cos(theta);
        gg_lat = z * Math.sin(theta);
        double[] result = new double[]{gg_lat, gg_lon};
        return result;
    }

    /**
     * 二、从resource中的raw文件夹中获取文件并读取数据（资源文件只能读不能写）
     *
     * @param fileInRaw
     * @return
     */
    public static String readFromRaw(Context context, int fileInRaw) {
        InputStream in = context.getResources().openRawResource(fileInRaw);
        StringBuffer strbuffer = new StringBuffer();
        String line;
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(in));
            while ((line = reader.readLine()) != null) {
                strbuffer.append(line).append("\r\n");
            }
        } catch (IOException e) {

        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return strbuffer.toString();
    }


    public static int getColorPrimary(Context context) {
        TypedValue value = new TypedValue();
        context.getTheme().resolveAttribute(R.attr.colorPrimary, value, true);
        return context.getResources().getColor(value.resourceId);
    }

    public static int getColorPrimaryDark(Context context) {
        TypedValue value = new TypedValue();
        context.getTheme().resolveAttribute(R.attr.colorPrimaryDark, value, true);
        return context.getResources().getColor(value.resourceId);
    }

    public static int convertDpToPixel(Context context, float dp) {
        float density = context.getResources().getDisplayMetrics().density;
        return Math.round(dp * density);
    }

    public static float convertPixelsToDp(Context context, float px) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return px / (metrics.densityDpi / 160f);
    }

    public static int adjustAlpha(int color, float factor) {
        int alpha = Math.round(Color.alpha(color) * factor);
        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);
        return Color.argb(alpha, red, green, blue);
    }

    public static Bitmap drawableToBitmap(Drawable drawable) {
        Bitmap bitmap = null;

        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if (bitmapDrawable.getBitmap() != null) {
                return bitmapDrawable.getBitmap();
            }
        }

        if (drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888); // Single color bitmap will be created of 1x1 pixel
        } else {
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    public static String getStackTrace(Throwable t) {
        if (t == null) {
            return null;
        }
        StringWriter sw = new StringWriter();
        t.printStackTrace(new PrintWriter(sw));
        return sw.toString();
    }


    public static String getEditText(TextView editText) {
        if (TextUtils.isEmpty(editText.getText())) {
            return "";
        } else {
            return editText.getText().toString();
        }
    }
    /**
     * 描述：根据手机的分辨率从 dp 的单位 转成为 px(像素)
     *
     * @param context
     * @param dpValue dip的值
     * @return
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }


    public static int dp2px(Context context, int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, dp, context.getResources().getDisplayMetrics());
    }

    /**
     * 获取状态栏高度
     * @param context
     * @return
     */
    public static int getBarHeight(Context context){
        Class<?> c = null;
        Object obj = null;
        Field field = null;
        int x = 0, sbar = 38;//默认为38，貌似大部分是这样的

        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            sbar = context.getResources().getDimensionPixelSize(x);

        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return sbar;
    }
    /**
     * 获取屏幕高度
     */
    public static int getDisplayHeight(Context context){
        WindowManager windowManager = ((Activity)context).getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        return  display.getHeight();
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static void setClipboard(String text, Context mContext) {
        setClipboard(text, mContext, false);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static void setClipboard(String text, Context mContext, boolean isUrlCopy) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
            android.text.ClipboardManager clipboard = (android.text.ClipboardManager) mContext
                    .getSystemService(Context.CLIPBOARD_SERVICE);
            clipboard.setText(text);
        } else {
            ClipboardManager clipboard = (ClipboardManager) mContext
                    .getSystemService(Context.CLIPBOARD_SERVICE);
            clipboard.setText(text);
        }
        if (mContext instanceof Activity) {
            if (isUrlCopy) {
                ToastUtils.showToast(mContext, "复制成功");
            } else {
                ToastUtils.showToast(mContext, "复制成功");
            }
        }
    }
}
