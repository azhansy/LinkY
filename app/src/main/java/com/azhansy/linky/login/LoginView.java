package com.azhansy.linky.login;

import com.avos.avoscloud.AVUser;
import com.azhansy.linky.base.MVP.IBaseUi;

/**
 * Created by SHU on 2016/6/28.
 */
public interface LoginView extends IBaseUi {
    void onLoginSuccess(AVUser user);
    void onLoginFailed(String msg);
    void onSignSuccess(AVUser user);
    void onSignFailed(String msg);
}
