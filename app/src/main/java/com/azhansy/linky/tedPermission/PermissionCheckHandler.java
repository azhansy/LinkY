package com.azhansy.linky.tedPermission;

import android.Manifest;
import android.content.Context;
import android.os.Handler;
import android.util.SparseArray;


import com.azhansy.linky.R;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class PermissionCheckHandler {

    private static SparseArray<PermissionCheckHandler> sPermissionArray = new SparseArray<>(10);

    public static PermissionCheckHandler get(Context context) {
        if (context == null) {
            throw new IllegalArgumentException("context can't be null.");
        }
        final Integer key = generateArrayKey(context);
        PermissionCheckHandler handler = sPermissionArray.get(key);
        if (handler == null) {
            handler = new PermissionCheckHandler(context);
            sPermissionArray.put(key, handler);
        }
        return handler;
    }

    private static Integer generateArrayKey(Context context) {
        return context.hashCode();
    }

    private Context mContext;

    private Handler mHandler;

    // 防止频繁检查必须权限
    private boolean mCheckMustPermissionAvailable = true;

    // 是否可以检查必须权限之外的权限了
    private boolean mCheckUserPermissionAvailable = false;

    // 需要检查的用户权限
    private Runnable mPendingUserPermissionCheckRunnable;

    private CheckMustPermissionListener mCheckMustPermissionListener;

    private List<CheckMustPermissionExtraListener> mCheckMustPermissionExtraListeners;

    PermissionCheckHandler(Context context) {
        mContext = context;
        mHandler = new Handler();
        mCheckMustPermissionExtraListeners = new ArrayList<>();
    }

    public void setCheckMustPermissionListener(CheckMustPermissionListener listener) {
        mCheckMustPermissionListener = listener;
    }

    public void addCheckMustPermissionExtraListener(CheckMustPermissionExtraListener listener) {
        mCheckMustPermissionExtraListeners.add(listener);
    }

    public void onCreate() {
        onResume();
    }

    public void onResume() {
        if (mCheckMustPermissionAvailable) {
            mCheckMustPermissionAvailable = false;
            mCheckUserPermissionAvailable = false;
            checkMustPermission();
        }
    }

    public void onStop() {
        mCheckMustPermissionAvailable = true;
        mCheckUserPermissionAvailable = false;
    }

    public void onDestroy() {
        sPermissionArray.remove(generateArrayKey(mContext));
    }

    public void checkMustPermission() {
        checkMustPermissionActual();
    }

    /**
     * 用户是否可以检查自己所需要的权限（除开必须权限之外的权限）
     */
    public boolean checkUserPermissionAvailable() {
        return mCheckUserPermissionAvailable;
    }

    /**
     * 检查用户权限
     */
    public void checkUserPermission(final PermissionCheckHelper.PermissionCheckListener listener,
                                    final PermissionCheckHelper.PermissionTask... tasks) {
        if (tasks == null || tasks.length == 0) {
            return;
        }
        Runnable runnable = () -> {
            PermissionCheckHelper helper = new PermissionCheckHelper(mContext);
            for (PermissionCheckHelper.PermissionTask task : tasks) {
                helper.add(task);
            }
            helper.setListener(listener);
            helper.start();
        };
        executeUserPermissionCheckTask(runnable);
    }

    private void executeUserPermissionCheckTask(Runnable runnable) {
        if (runnable == null) {
            return;
        }
        if (checkUserPermissionAvailable()) {
            mHandler.post(runnable);
        } else {
            mPendingUserPermissionCheckRunnable = runnable;
        }
    }

    /**
     * 检测APP必须需要的权限
     */
    protected void checkMustPermissionActual() {
        PermissionCheckHelper helper = new PermissionCheckHelper(mContext);
        helper.add(Manifest.permission.WRITE_EXTERNAL_STORAGE, mContext.getString(R.string.permission_sdcard_message));
//                .add(Manifest.permission.READ_PHONE_STATE, mContext.getString(R.string.permission_phone_message));
        helper.setListener(new PermissionCheckHelper.PermissionCheckListener() {
            @Override
            public boolean onPermissionGranted(PermissionCheckHelper helper, String permission, int index, int total, boolean end) {
                return onMustPermissionGranted(helper, permission, end);
            }

            @Override
            public boolean onPermissionDenied(PermissionCheckHelper helper, String permission, int index, int total) {
                return onMustPermissionDenied(helper, permission);
            }
        });
        helper.start();
    }

    /**
     * APP必须需要的权限被允许
     * @param permission 权限
     * @param end 是否是最后一个权限
     * @return 如果为true，后续的权限将不会再被检测
     */
    protected boolean onMustPermissionGranted(PermissionCheckHelper helper, String permission, boolean end) {
        mCheckUserPermissionAvailable = end;
        // 申请用户权限
        if (checkUserPermissionAvailable()) {
            mHandler.post(() -> {
                executeUserPermissionCheckTask(mPendingUserPermissionCheckRunnable);
                mPendingUserPermissionCheckRunnable = null;
            });
        }
        boolean returnValue = mCheckMustPermissionListener != null && mCheckMustPermissionListener.onMustPermissionGranted(helper, permission, end);
        for (CheckMustPermissionExtraListener listener : mCheckMustPermissionExtraListeners) {
            listener.onMustPermissionGranted(helper, permission, end, returnValue);
        }
        return returnValue;
    }

    /**
     * APP必须需要的权限被拒绝
     */
    protected boolean onMustPermissionDenied(PermissionCheckHelper helper, String permission) {
        boolean returnValue = mCheckMustPermissionListener != null && mCheckMustPermissionListener.onMustPermissionDenied(helper, permission);
        for (CheckMustPermissionExtraListener listener : mCheckMustPermissionExtraListeners) {
            listener.onMustPermissionDenied(helper, permission, returnValue);
        }
        return returnValue;
    }

    /**
     * Activity使用的listener
     */
    public interface CheckMustPermissionListener {
        boolean onMustPermissionGranted(PermissionCheckHelper helper, String permission, boolean end);
        boolean onMustPermissionDenied(PermissionCheckHelper helper, String permission);
    }

    public interface CheckMustPermissionExtraListener {
        /**
         *
         * @param returnValue {@link CheckMustPermissionListener#onMustPermissionGranted(PermissionCheckHelper, String, boolean)}
         * 中的返回值。
         */
        void onMustPermissionGranted(PermissionCheckHelper helper, String permission, boolean end, boolean returnValue);

        /**
         *
         * @param returnValue {@link CheckMustPermissionListener#onMustPermissionDenied(PermissionCheckHelper, String)}
         * 中的返回值。
         */
        void onMustPermissionDenied(PermissionCheckHelper helper, String permission, boolean returnValue);
    }

}
