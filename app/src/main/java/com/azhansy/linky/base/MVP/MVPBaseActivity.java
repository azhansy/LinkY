package com.azhansy.linky.base.MVP;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;


import com.azhansy.linky.base.BaseActivity;



/**
 * Created by jd on 2015/10/12.
 */
public abstract class MVPBaseActivity<Presenter extends MVPBasePresenter> extends BaseActivity {

    @Nullable
//    @Bind(R.id.loading_layout)
//    protected View mLoading;

    protected Presenter mPresenter;
    protected String mGid;

//    @Override
//    public int getLayoutResource() {
//        return R.layout.layout_of_common_title_and_loading;
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        hideLoading();

        mGid = getIntent().getStringExtra(IBaseUi.KEY_GID);

//        if (TextUtils.isEmpty(mGid)) {
//            mGid = DiskApplication.getInstance().getCurrentGid();
//        }

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

//    protected void showLoading() {
//        if (mLoading != null) {
//            mLoading.setVisibility(View.VISIBLE);
//        }
//    }
//
//    protected void hideLoading() {
//        if (mLoading != null) {
//            mLoading.setVisibility(View.GONE);
//        }
//    }

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

    public static void launch(Context context, Class<? extends MVPBaseActivity> cls) {
        launch(context, cls, null);
    }

    public static void launch(Context context, Class<? extends MVPBaseActivity> cls, String gid) {
        Intent intent = new Intent(context, cls);
        if (!TextUtils.isEmpty(gid)) {
            intent.putExtra(IBaseUi.KEY_GID, gid);
        }
        if (!(context instanceof Activity)) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        context.startActivity(intent);
    }

}
