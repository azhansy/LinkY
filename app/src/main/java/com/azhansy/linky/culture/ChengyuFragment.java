package com.azhansy.linky.culture;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.azhansy.linky.R;
import com.azhansy.linky.base.MVP.MVPBaseFragment;
import com.azhansy.linky.culture.adapter.ChengyuAdapter;
import com.azhansy.linky.culture.model.ChengyuModel;
import com.azhansy.linky.culture.presenter.ChengyuPrensenterImpl;
import com.azhansy.linky.utils.ToastUtil;
import com.loopj.android.http.RequestParams;

import java.util.List;

import butterknife.Bind;

/**
 * Created by SHU on 2016/7/4.
 */
public class ChengyuFragment extends MVPBaseFragment<ChengyuPrensenterImpl> implements ChengyuView {
    @Bind(R.id.list_recycler_view)
    RecyclerView recyclerView;

    private ChengyuAdapter adapter;

    public static ChengyuFragment getInstance(){
        return new ChengyuFragment();
    }
    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_cheng_yu;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);
        RequestParams params = new RequestParams();
        params.put("keyWord","人");
        mPresenter.getData(params);
        adapter = new ChengyuAdapter(getActivity());
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),1));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        MenuItem item = menu.add(Menu.NONE, 123, Menu.NONE, "搜索");
        item.setIcon(R.mipmap.ic_menu_search);
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == 123) {
            ToastUtil.showToast(getActivity(),"搜索");
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public Context getPresenterContext() {
        return getActivity();
    }

    @Override
    protected boolean usePresenter() {
        return true;
    }

    @Override
    protected ChengyuPrensenterImpl createPresenter() {
        return new ChengyuPrensenterImpl();
    }

    @Override
    public void getDataSuccess(List<ChengyuModel.ChengyuDetailModel> list) {
        adapter.replaceAll(list);
    }

    @Override
    public void getDataFailed(String error) {
        ToastUtil.showToast(getActivity(), error);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.onDestroy();
        }
    }
}
