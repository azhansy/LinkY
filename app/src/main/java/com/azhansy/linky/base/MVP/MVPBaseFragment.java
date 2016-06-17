package com.azhansy.linky.base.MVP;

import android.os.Bundle;
import android.view.View;


import com.azhansy.linky.base.BaseFragment;



/**
 * Created by jd on 2015/10/12.
 * modified by ohdroid 2015年11月10日10:17:41
 */
public abstract class MVPBaseFragment<Presenter extends MVPBasePresenter> extends BaseFragment {

//    @Bind(R.id.loading_layout)
//    protected View mLoading;

    protected Presenter mPresenter;
//    protected String mGid;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /**
         * TODO 并未用到GID，若有后续需求，再进行更改
         */
//        if (getArguments() != null) {
//            mGid = getArguments().getString(INewsBaseUi.KEY_GID);
//        }
//        if (TextUtils.isEmpty(mGid)) {
//            mGid = YYWCloudOfficeApplication.getInstance().getCurrentGid();
//        }

        if (usePresenter()) {
            mPresenter = createPresenter();
            mPresenter.attach(getBaseUi());
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        hideLoading();
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
     * MVPBaseFragment implement Ui
     */
    @SuppressWarnings(value = "unchecked")
    protected <Ui extends IBaseUi> Ui getBaseUi() {
        return (Ui) this;
    }
}
