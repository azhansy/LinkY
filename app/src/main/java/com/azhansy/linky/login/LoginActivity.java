package com.azhansy.linky.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.widget.EditText;

import com.avos.avoscloud.AVUser;
import com.azhansy.linky.base.MVP.MVPBaseActivity;
import com.azhansy.linky.base.MVP.MVPBasePresenter;
import com.azhansy.linky.model.UserModel;
import com.azhansy.linky.ui.MainActivity;
import com.azhansy.linky.R;
import com.azhansy.linky.base.BaseActivity;
import com.azhansy.linky.swipebackhelper.SwipeBackHelper;
import com.azhansy.linky.utils.ToastUtil;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by SHU勇仔 on 2016/6/15.
 */
public class LoginActivity extends MVPBaseActivity<LoginPresenterImpl> implements LoginView {
    @Bind(R.id.edit_username)
    EditText mUsername;
    @Bind(R.id.edit_password)
    EditText mPassword;
    @Bind(R.id.edit_username_layout)
    TextInputLayout mUsernameLayout;

    @OnClick({R.id.btn_login, R.id.btn_register})
    void onClick(View view) {
        String userName = mUsername.getText().toString().trim();
        String password = mPassword.getText().toString().trim();
        switch (view.getId()) {
            case R.id.btn_login:
                if (checkMsg(userName, password)) {
                    mPresenter.login(userName, password);
                }
                break;
            case R.id.btn_register:
                if (checkMsg(userName, password)) {
                    mPresenter.sign(userName, password);
                }
                break;
        }
    }

    @Override
    public int getLayoutResource() {
        return R.layout.activity_login;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSwipeBackEnable(false);
    }

    @Override
    protected boolean usePresenter() {
        return true;
    }

    @Override
    protected LoginPresenterImpl createPresenter() {
        return new LoginPresenterImpl();
    }


    private boolean checkMsg(String userName, String password) {
        if (userName.isEmpty()) {
            ToastUtil.showToast(this, "请输入用户名");
            return false;
        }
        if (password.isEmpty() || password.length() < 6) {
            ToastUtil.showToast(this, "请输入6位及以上有效密码");
            return false;
        }
        return true;
    }

    public static void launch(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onLoginSuccess(AVUser user) {
        ToastUtil.showToast(this,"登陆成功");
        MainActivity.launch(this);
        finish();
    }

    @Override
    public void onLoginFailed(String msg) {
        ToastUtil.showToast(this,"登陆失败："+msg);
    }

    @Override
    public void onSignSuccess(AVUser user) {
        ToastUtil.showToast(this,"注册成功");
    }

    @Override
    public void onSignFailed(String msg) {
        ToastUtil.showToast(this,"注册失败："+msg);
    }


    @Override
    public Context getPresenterContext() {
        return this;
    }
}
