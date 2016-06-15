package com.azhansy.linky.utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;


import java.io.File;

/**
 * Created by ZHANSY
 * 邮箱：azhansy@hotmail.com
 */
public class CommonUtil {

    public static String getAppVersionName(Context context) {
        String versionName = "";
        try {
            // ---get the package info---
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versionName = pi.versionName;
//            versioncode = pi.versionCode;
            if (versionName == null || versionName.length() <= 0) {
                return "";
            }
        } catch (Exception e) {
            Log.e("VersionInfo", "Exception", e);
        }
        return versionName;
    }
    public static boolean hasSDcard(){
        String sdStatus = Environment.getExternalStorageState();
        if (sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用
            return true;
        }
        return false;
    }
    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

//    /**
//     * @return 返回本地 SharedPreferences
//     */
//    public static SharedPreferences getSharedPreferences(){
//        SharedPreferences sp = OMApplication.getInstance().getSharedPreferences("zhansy", OMApplication.getInstance().MODE_APPEND);
//        return  sp;
//
//    }

    /**
     * 下载路径 地址  内存卡
     */
    public static final String hostFile = Environment.getExternalStorageDirectory().getPath() + "/azhansy";
//    /**
//     * 组装请求URL
//     *
//     * @param stringID
//     * @return 请求地址
//     */
//    public static String appendRequesturl(int... stringID){
//        int[] ids = stringID;
//        StringBuffer sb = new StringBuffer();
//        sb.append(host);
//        for(int resourse : ids){
//            sb.append(OMApplication.getInstance().getString(resourse));
//        }
//        return sb.toString();
//    }

//    public static void openFile(File file){
//
//        Intent intent = new Intent("android.intent.action.VIEW");
//        intent.addCategory("android.intent.category.DEFAULT");
//        intent.addFlags (Intent.FLAG_ACTIVITY_NEW_TASK);
//        Uri uri = Uri.fromFile(file);
//        intent.setDataAndType (uri, "application/pdf");
//        OMApplication.getInstance().startActivity(intent);
//    }
}
