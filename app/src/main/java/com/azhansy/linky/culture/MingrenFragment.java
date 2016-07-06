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
        mPresenter.getMingRen("爱因斯坦","10");
        adapter = new MingrenAdapter(getActivity());
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),1));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        MenuItem item = menu.add(Menu.NONE, 111, Menu.NONE, "搜索");
        item.setIcon(R.mipmap.ic_menu_search);
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == 111) {
            ToastUtil.showToast(getActivity(),"搜索");
        }

        return super.onOptionsItemSelected(item);
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
    }
}
