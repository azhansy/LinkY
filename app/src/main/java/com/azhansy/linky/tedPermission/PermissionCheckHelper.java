package com.azhansy.linky.tedPermission;

import android.content.Context;
import android.os.Handler;
import android.support.annotation.StringRes;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class PermissionCheckHelper {

    private Context mContext;
    private final List<PermissionTask> mPermissionTaskList;
    private Handler mHandler;

    private PermissionCheckListener mListener;

    private int mIndex = -1;

    public PermissionCheckHelper(Context context) {
        mContext = context;
        mPermissionTaskList = new ArrayList<>();
        mHandler = new Handler();
    }

    public void setListener(PermissionCheckListener listener) {
        mListener = listener;
    }

    public PermissionCheckHelper add(String permission, String denyMessage) {
        PermissionTask task = new PermissionTask.Builder(mContext, permission)
                .setDenyMessage(denyMessage)
                .build();
        mPermissionTaskList.add(task);
        return this;
    }

    public PermissionCheckHelper add(PermissionTask task) {
        mPermissionTaskList.add(task);
        return this;
    }

    public void start() {
        if (mPermissionTaskList.isEmpty()) {
            return;
        }
        loop();
    }

    public void postStart() {
        mHandler.post(this::start);
    }

    private void loop() {
        mIndex ++;
        if (mIndex >= mPermissionTaskList.size()) {
            return;
        }

        final PermissionTask task = mPermissionTaskList.get(mIndex);

        task.getTedPermission().setPermissionListener(new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                PermissionCheckListener listener = mListener;
                if (listener != null && !listener.onPermissionGranted(
                        PermissionCheckHelper.this,
                        task.getPermission(),
                        mIndex,
                        mPermissionTaskList.size(),
                        mIndex == mPermissionTaskList.size() - 1)) {
                    postLoop();
                }
            }

            @Override
            public void onPermissionDenied(ArrayList<String> deniedPermissions) {
                PermissionCheckListener listener = mListener;
                if (listener != null && !listener.onPermissionDenied(
                        PermissionCheckHelper.this,
                        task.getPermission(),
                        mIndex,
                        mPermissionTaskList.size())) {
                    postLoop();
                }
            }
        });
        task.getTedPermission().check();
    }

    private void postLoop() {
        mHandler.post(this::loop);
    }

    public static class PermissionTask {
        private final String permission;
        private final TedPermission tedPermission;

        public PermissionTask(String permission, TedPermission tedPermission) {
            this.permission = permission;
            this.tedPermission = tedPermission;
        }

        public String getPermission() {
            return permission;
        }

        public TedPermission getTedPermission() {
            return tedPermission;
        }

        public static class Builder {

            private Context context;
            private String permission;
            private String denyMessage;

            public Builder(Context context, String permission) {
                this.context = context;
                this.permission = permission;
            }

            public Builder setDenyMessage(@StringRes int resId, Object... args) {
                return setDenyMessage(context.getString(resId, args));
            }

            public Builder setDenyMessage(String denyMessage) {
                this.denyMessage = denyMessage;
                return this;
            }

            public PermissionTask build() {
                TedPermission permission = new TedPermission(context);
                permission.setPermissions(this.permission);
                permission.setDeniedMessage(this.denyMessage);
                return new PermissionTask(this.permission, permission);
            }
        }
    }

    public interface PermissionCheckListener {
        /**
         * 已授权
         * @param helper 授权用的工具类，一般不会用到它
         * @param permission 权限名称，即：Manifest.permission.xxx
         * @param index 可同时进行多个授权，index代表当前是第几个
         * @param total 可同时进行多个授权，total代表总共有多少个授权
         * @param end 是否为最后一个授权，如果需要在所有授权成功后执行某些操作，需要判断该值
         * @return 一般返回false，返回false会自动进行下一个授权，返回true会终止掉授权操作，接下来的操作需要用户自己处理
         */
        boolean onPermissionGranted(PermissionCheckHelper helper, String permission, int index, int total, boolean end);

        /**
         * 未授权，参数含义和上面一样
         */
        boolean onPermissionDenied(PermissionCheckHelper helper, String permission, int index, int total);
    }


}
