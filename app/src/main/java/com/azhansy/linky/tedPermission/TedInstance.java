package com.azhansy.linky.tedPermission;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.azhansy.linky.R;
import com.azhansy.linky.tedPermission.busevent.TedPermissionEvent;

import de.greenrobot.event.EventBus;


/**
 * Created by TedPark on 16. 2. 17..
 */
public class TedInstance {

    public PermissionListener listener;
    public String[] permissions;
    public String rationaleMessage;
    public String denyMessage;
    public boolean hasSettingBtn = true;

    public String deniedCloseButtonText;
    public String rationaleConfirmText;
    Context context;


    public TedInstance(Context context) {

        this.context = context;

        deniedCloseButtonText = context.getString(R.string.tedpermission_close);
        rationaleConfirmText = context.getString(R.string.tedpermission_confirm);
    }


    public void checkPermissions() {
        // 已经有全部的权限了，不需要启动activity去检查了
        if (!checkPermissionsWhenStart()) {
            listener.onPermissionGranted();
            return;
        }

        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }

        // 防止页面闪烁
        View decorView = getDecorView();

        if (decorView != null && decorView.getWidth() > 0) {
            checkPermissionActual();
        } else {
            Handler handler = new Handler();
            handler.postDelayed(this::checkPermissionActual, 400);
        }

    }

    private View getDecorView() {
        if (context != null && context instanceof Activity) {
            return ((Activity) context).getWindow().getDecorView();
        }
        return null;
    }

    private void checkPermissionActual() {
        Intent intent = new Intent(context, TedPermissionActivity.class);
        intent.putExtra(TedPermissionActivity.EXTRA_PERMISSIONS, permissions);

        intent.putExtra(TedPermissionActivity.EXTRA_RATIONALE_MESSAGE, rationaleMessage);
        intent.putExtra(TedPermissionActivity.EXTRA_DENY_MESSAGE, denyMessage);
        intent.putExtra(TedPermissionActivity.EXTRA_PACKAGE_NAME, context.getPackageName());
        intent.putExtra(TedPermissionActivity.EXTRA_SETTING_BUTTON, hasSettingBtn);
        intent.putExtra(TedPermissionActivity.EXTRA_DENIED_DIALOG_CLOSE_TEXT, deniedCloseButtonText);
        intent.putExtra(TedPermissionActivity.EXTRA_RATIONALE_CONFIRM_TEXT, rationaleConfirmText);

        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    private boolean checkPermissionsWhenStart() {

        boolean needPermission = false;

        for (String permission : permissions) {

            if (ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                needPermission = true;
                break;
            }
        }

        return needPermission;

    }

    public void onEventMainThread(TedPermissionEvent event) {
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        if (event.hasPermission()) {
            listener.onPermissionGranted();
        } else {
            listener.onPermissionDenied(event.getDeniedPermissions());
        }
    }

}
