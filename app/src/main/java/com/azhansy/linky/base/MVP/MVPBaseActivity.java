package com.azhansy.linky.base.MVP;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;


import com.azhansy.linky.base.BaseActivity;


/**
 */
public abstract class MVPBaseActivity<Presenter extends MVPBasePresenter> extends BaseActivity {

    protected Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (usePresenter()) {
            mPresenter = createPresenter();
            mPresenter.attach(getBaseUi());
        }

    }

    @Override
    public void onDestroy() {
        if (mPresenter != null) {
            mPresenter.detach(getBaseUi());
            mPresenter = null;
        }
        super.onDestroy();
    }

    /**
     * 是否需要使用Presenter
     */
    protected abstract boolean usePresenter();

    protected abstract Presenter createPresenter();

    /**
     * 如果需要使用Presenter，这里也需要返回相应的Ui对象
     * MVPBaseActivity implement Ui
     */
    @SuppressWarnings(value = "unchecked")
    protected <Ui extends IBaseUi> Ui getBaseUi() {
        return (Ui) this;
    }

}
