package com.azhansy.linky.culture;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.azhansy.linky.R;
import com.azhansy.linky.base.MVP.MVPBaseFragment;
import com.azhansy.linky.culture.adapter.MingrenAdapter;
import com.azhansy.linky.culture.model.MingrenModel;
import com.azhansy.linky.culture.presenter.MingrenPrensenterImpl;
import com.azhansy.linky.utils.ToastUtil;
import com.loopj.android.http.RequestParams;

import java.util.List;

import butterknife.Bind;

/**
 * Created by SHU on 2016/7/4.
 */
public class MingrenFragment extends MVPBaseFragment<MingrenPrensenterImpl> implements MingrenView {
    @Bind(R.id.list_recycler_view)
    RecyclerView recyclerView;

    private MingrenAdapter adapter;

    public static MingrenFragment getInstance(){
        return new MingrenFragment();
    }
    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_ming_ren;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RequestParams params = new RequestParams();
        params.put("keyword","爱因斯坦");
        params.put("rows","10");
        mPresenter.getData(params);
        adapter = new MingrenAdapter(getActivity());
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),1));
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected boolean usePresenter() {
        return true;
    }

    @Override
    protected MingrenPrensenterImpl createPresenter() {
        return new MingrenPrensenterImpl();
    }

    @Override
    public Context getPresenterContext() {
        return getActivity();
    }

    @Override
    public void getDataSuccess(List<MingrenModel.MingrenDetailModel> list) {
        adapter.replaceAll(list);
    }

    @Override
    public void getDataFailed(String error) {
        ToastUtil.showToast(getActivity(),error);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.onDestroy();
        }
    }
}
