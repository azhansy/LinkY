package com.azhansy.linky.base;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.azhansy.linky.R;
import com.azhansy.linky.swipebackhelper.SwipeBackHelper;
import com.azhansy.linky.tedPermission.PermissionCheckHandler;
import com.azhansy.linky.tedPermission.PermissionCheckHelper;
import com.azhansy.linky.utils.AppManager;
import com.azhansy.linky.utils.DrawableUtil;
import com.azhansy.linky.utils.SharePreferenceUtil;
import com.azhansy.linky.utils.SystemBarTintManager;

import butterknife.ButterKnife;

/**
 * Created by SHU勇仔 on 2016/6/15.
 */
public abstract class BaseActivity extends AppCompatActivity {
    private static AppManager appManager = AppManager.getAppManager();
    private InputMethodManager mInputMethodManager;
    protected boolean isSupportFullScreenShowPicture = false;
    protected TextView mToolbarTitle;
    protected Toolbar mToolbar;
    protected SystemBarTintManager mTintManager;
    public boolean isNight;
    private PermissionCheckHandler mPermissionCheckHandler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //切换肤色 theme
        isNight = SharePreferenceUtil.isNight();
        setTheme(isNight ? R.style.AppNightTheme : R.style.AppTheme);
        appManager.addActivity(this);
        SwipeBackHelper.onCreate(this);
        SwipeBackHelper.getCurrentPage(this)
                .setSwipeBackEnable(true)
                .setSwipeEdgePercent(0.1f)//
                .setSwipeSensitivity(0.5f)
                .setSwipeRelateEnable(true);

        int layoutResId = getLayoutResource();
        if (layoutResId != 0) {
            setContentView(layoutResId);
        }

        final Drawable upArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_mtrl_am_alpha);

        if (upArrow != null && getSupportActionBar() != null) {
            upArrow.setColorFilter(DrawableUtil.getThemeColor(this, R.attr.actionMenuTextColor), PorterDuff.Mode.SRC_ATOP);
            getSupportActionBar().setHomeAsUpIndicator(upArrow);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(android.R.color.black));
        }
        initCheckPermissionHelper();
    }
    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
        View v = findViewById(R.id.toolbar);
        if (v != null) {
            mToolbar = (Toolbar) v;
            setSupportActionBar(mToolbar);
            mToolbarTitle = (TextView) v.findViewById(R.id.toolbar_title);
            if (mToolbarTitle != null && getSupportActionBar() != null) {
                getSupportActionBar().setDisplayShowTitleEnabled(false);
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setHomeButtonEnabled(true);
                getSupportActionBar().setIcon(R.drawable.app_divider_drawable);
            }

            mToolbar.setNavigationOnClickListener(v1 -> {
                if (!onNavigationClick()) {
                    finishActivity();
                }
            });

        }

    }
    @Override
    protected void onPause() {
        super.onPause();
        hideInput();
    }
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        int size = menu.size();
        for (int i = 0; i < size; i++) {
            MenuItem menuItem = menu.getItem(i);
            final Drawable moreMenuDrawable = menuItem.getIcon();

            if (moreMenuDrawable != null) {
                menuItem.setIcon(DrawableUtil.getThemeDrawable(this, moreMenuDrawable));

            }
        }

        return super.onPrepareOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            if (!onNavigationClick()) {
                finish();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        SwipeBackHelper.onPostCreate(this);
        if (!isChild()) {
            onTitleChanged(getTitle(), getTitleColor());
        }
        if (isSupportFullScreenShowPicture) {
            if (mTintManager != null) {
                mTintManager.removeStatusBarView();
                mTintManager = null;
            }
            initTintManagerIfNeed();
        }
    }
    /**
     * 如果需要初始化着色管理器
     */
    protected void initTintManagerIfNeed() {
        if (mTintManager == null) {
            if (isAllowTranslucentStatusOrNavigation()) {
                setTranslucentStatus(true);
            }

            mTintManager = new SystemBarTintManager(this, null);
            setDefaultStatusBarTintColor();
            setDefaultContentViewPadding();
        }
    }
    protected void setDefaultStatusBarTintColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setStatusBarTintColor(DrawableUtil.getPrimaryDarkColor(this));
        } else {
            setStatusBarTintColor(getResources().getColor(android.R.color.black));
        }
    }
    /**
     * 设置状态栏颜色
     */
    public void setStatusBarTintColor(int color) {
        setStatusBarTintEnabled(true);
        mTintManager.setTintColor(color);
        mTintManager.setTintAlpha(1f);
    }

    /**
     * 是否允许状态栏着色
     */
    protected void setStatusBarTintEnabled(boolean enable) {
        initTintManagerIfNeed();

        if (mTintManager != null) {
            mTintManager.setStatusBarTintEnabled(enable);
        }
    }
    private View mContentView;

    protected void setDefaultContentViewPadding() {
        if (!isAllowTranslucentStatusOrNavigation()) {
            return;
        }
        initTintManagerIfNeed();

        if (mContentView == null) {
            mContentView = findViewById(android.R.id.content);
        }
        if (mContentView != null) {
            int top = mContentView.getPaddingTop();
            SystemBarTintManager.SystemBarConfig config = mTintManager.getConfig();
            if (mTintManager.isStatusBarAvailable()) {
                top = config.getStatusBarHeight();
            }
            setContentViewPaddingTop(top);
        }
    }

    protected void setContentViewPaddingTop(int paddingTop) {
        if (!isAllowTranslucentStatusOrNavigation()) {
            return;
        }
        initTintManagerIfNeed();
//
        if (!mTintManager.isStatusBarAvailable()) {
            return;
        }
        if (mContentView == null) {
            mContentView = findViewById(android.R.id.content);
        }
        if (mContentView != null) {
            int left = mContentView.getPaddingLeft();
            int right = mContentView.getPaddingRight();
            int bottom = mContentView.getPaddingBottom();
            mContentView.setPadding(left, paddingTop, right, bottom);
        }

    }
    /**
     * 4.4以上，系统，设置状态栏颜色
     */
    public static boolean isAllowTranslucentStatusOrNavigation() {
        final int sdkVersion = Build.VERSION.SDK_INT;
        return sdkVersion >= Build.VERSION_CODES.KITKAT;
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    protected boolean setTranslucentStatus(boolean on) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window win = getWindow();
            WindowManager.LayoutParams winParams = win.getAttributes();
            final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
            if (on) {
                winParams.flags |= bits;
            } else {
                winParams.flags &= ~bits;
            }
            win.setAttributes(winParams);

            return on;
        }
        return false;
    }
    @Override
    protected void onTitleChanged(CharSequence title, int color) {
        super.onTitleChanged(title, color);
        if (mToolbarTitle != null && !TextUtils.isEmpty(title) && getSupportActionBar() != null) {
            mToolbarTitle.setText(title);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //为了解决有菜单键会弹起一个白色的菜单的问题
        if (keyCode == KeyEvent.KEYCODE_MENU) return true;
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        ButterKnife.unbind(this);
        appManager.removeActivity(this);
        SwipeBackHelper.onDestroy(this);
        mPermissionCheckHandler.onDestroy();
        super.onDestroy();
    }

    /**
     * 点击返回按钮默认是管理页面，在该方法中可以自定义处理
     *
     * @return 返回true就不执行关闭页面的操作
     */
    protected boolean onNavigationClick() {
        return false;
    }
    public void hideInput() {
        initInputMethodManager();
        if (mInputMethodManager != null && mInputMethodManager.isActive()
                && getCurrentFocus() != null) {
            mInputMethodManager.hideSoftInputFromWindow(getCurrentFocus()
                    .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    public void hideInput(View view) {
        initInputMethodManager();
        if (mInputMethodManager != null && mInputMethodManager.isActive()
                && view != null) {
            mInputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    public boolean isInputShowing() {
        initInputMethodManager();
        return (mInputMethodManager != null && mInputMethodManager.isActive());
    }

    public void showInput() {
        initInputMethodManager();
        if (mInputMethodManager != null && getCurrentFocus() != null && !mInputMethodManager.isActive()) {
            mInputMethodManager.showSoftInput(getCurrentFocus(), InputMethodManager.SHOW_IMPLICIT);
        }
    }

    public void showInput(View view) {
        initInputMethodManager();
        if (mInputMethodManager != null && view != null) {
            mInputMethodManager.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
        }
    }

   /* protected void showLoadDialog() {
        if (mLoadDialog == null) {
            mLoadDialog = new LoadDialog(this);
        }
        if (!mLoadDialog.isShowing()) {
            mLoadDialog.show();
        }
    }

    protected void dismissLoadDialog() {
        if (mLoadDialog != null && mLoadDialog.isShowing()) {
            mLoadDialog.dismiss();
        }
    }*/

    public void finishActivity() {
        onBackPressed();
    }

    private void initInputMethodManager() {
        if (mInputMethodManager == null) {
            mInputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        }
    }

    /**
     * 布局文件
     */
    public abstract int getLayoutResource();

    /**
     * 设置是否滑动返回
     */
    protected void setSwipeBackEnable(boolean enable){
        SwipeBackHelper.getCurrentPage(this).setSwipeBackEnable(enable); //设置不给滑动
    }

    public void reload() {
        Intent intent = getIntent();
        //一个Activity跳转到另一个Activity的结束和开始动画
        overridePendingTransition(0, 0);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        finish();
        overridePendingTransition(0, 0);
        startActivity(intent);
    }
    // ===================== 6.0 权限适配相关代码 start ===================== //

    protected void initCheckPermissionHelper() {
        mPermissionCheckHandler = PermissionCheckHandler.get(this);
        mPermissionCheckHandler.setCheckMustPermissionListener(mCheckMustPermissionListener);
        mPermissionCheckHandler.onCreate();
//        System.out.println("Activity helper -> " + mPermissionCheckHandler + " " + this.getClass().getSimpleName());
    }

    /**
     * APP必须需要的权限被允许
     *
     * @param permission 权限
     * @param end        是否是最后一个权限
     * @return 如果为true，后续的权限将不会再被检测
     */
    protected boolean onMustPermissionGranted(PermissionCheckHelper helper, String permission, boolean end) {
        return false;
    }

    /**
     * APP必须需要的权限被拒绝
     */
    protected boolean onMustPermissionDenied(PermissionCheckHelper helper, String permission) {
        AppManager.getAppManager().ExitApp(this); // 结束应用
        return true;
    }

    protected PermissionCheckHandler getPermissionCheckHandler() {
        return mPermissionCheckHandler;
    }

    protected void checkUserPermission(String permission, String denyMessage, PermissionCheckHelper.PermissionCheckListener l) {
        if (TextUtils.isEmpty(permission)) {
            throw new IllegalArgumentException("permission can't be null");
        }
        if (l == null) {
            throw new IllegalArgumentException("permission listener can't be null");
        }
        PermissionCheckHelper.PermissionTask task = new PermissionCheckHelper.PermissionTask.Builder(this, permission)
                .setDenyMessage(denyMessage)
                .build();
        mPermissionCheckHandler.checkUserPermission(l, task);
    }

    private PermissionCheckHandler.CheckMustPermissionListener mCheckMustPermissionListener = new PermissionCheckHandler.CheckMustPermissionListener() {
        @Override
        public boolean onMustPermissionGranted(PermissionCheckHelper helper, String permission, boolean end) {
            return BaseActivity.this.onMustPermissionGranted(helper, permission, end);
        }

        @Override
        public boolean onMustPermissionDenied(PermissionCheckHelper helper, String permission) {
            return BaseActivity.this.onMustPermissionDenied(helper, permission);
        }
    };

    /**
     * 检查是否开启某项权限
     * <p><b>注：6.0之前的权限是在安装的时候声明的，所以在运行时默认都是有权限的，如果通过手动禁止则通过捕获异常来提醒权限<b/><p/>
     */
    protected boolean hasUserPermission(String permission) {
        return ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED;
    }

    // ===================== 6.0 权限适配相关代码 start ===================== //
}
