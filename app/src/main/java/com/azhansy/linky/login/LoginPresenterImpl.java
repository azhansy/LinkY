package com.azhansy.linky.login;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.LogInCallback;
import com.avos.avoscloud.SignUpCallback;
import com.azhansy.linky.base.RxBasePresenter;


/**
 * Created by SHU on 2016/6/28.
 */
public class LoginPresenterImpl extends RxBasePresenter implements LoginPresenter {
    @Override
    public void login(String name, String pass) {
//        mSubscriptions.add(LinkApplication.getInstance().getRxBus().toObserverable()
//                .filter(event -> event instanceof UserModel)
//                .subscribe(object -> {
//
//                }, throwable -> {
//                    Logger.d("throwable......");
//                }));
        AVUser.logInInBackground(name, pass, new LogInCallback<AVUser>() {
            @Override
            public void done(AVUser avUser, AVException e) {
                LoginView view = getActualUi();
                if (view == null) return;
                if (avUser != null) {
                    view.onLoginSuccess(avUser);
                }else {
                    view.onLoginFailed(e.toString());
                }
            }
        });
    }

    @Override
    public void sign(String name, String pass) {
        AVUser user = new AVUser();// 新建 AVUser 对象实例
        user.setUsername(name);// 设置用户名
        user.setPassword(pass);// 设置密码
//        user.setEmail("tom@leancloud.cn");// 设置邮箱
        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(AVException e) {
                LoginView view = getActualUi();
                if (view == null) return;
                if (e == null) {
                    // 注册成功
                    view.onSignSuccess(user);
                } else {
                    // 失败的原因可能有多种，常见的是用户名已经存在。
                    view.onSignFailed(e.toString());
                }
            }
        });
    }
}
