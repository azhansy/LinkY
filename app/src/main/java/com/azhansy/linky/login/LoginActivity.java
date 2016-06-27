package com.azhansy.linky.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.widget.EditText;

import com.azhansy.linky.ui.MainActivity;
import com.azhansy.linky.R;
import com.azhansy.linky.base.BaseActivity;
import com.azhansy.linky.swipebackhelper.SwipeBackHelper;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by SHU勇仔 on 2016/6/15.
 */
public class LoginActivity extends BaseActivity {
    @Bind(R.id.edit_username)
    EditText mUsername;
    @Bind(R.id.edit_password)
    EditText mPassword;
    @Bind(R.id.edit_username_layout)
    TextInputLayout mUsernameLayout;

    @OnClick(R.id.btn_login)
    void onClick(){
        MainActivity.launch(this);
    }
    @Override
    public int getLayoutResource() {
        return R.layout.activity_login;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }



    public static void launch(Context context){
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

}
