package com.azhansy.linky.culture;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.azhansy.linky.R;
import com.azhansy.linky.base.BaseRecyclerViewAdapter;
import com.azhansy.linky.base.MVP.MVPBaseFragment;
import com.azhansy.linky.culture.adapter.ChengyuAdapter;
import com.azhansy.linky.culture.model.ChengyuModel;
import com.azhansy.linky.culture.presenter.ChengyuPrensenterImpl;
import com.azhansy.linky.utils.KeyboardUtil;
import com.azhansy.linky.utils.Logger;
import com.azhansy.linky.utils.ToastUtil;
import com.azhansy.linky.view.CommonSearchView;
import com.loopj.android.http.RequestParams;

import java.util.List;

import butterknife.Bind;

/**
 * Created by SHU on 2016/7/4.
 */
public class ChengyuFragment extends MVPBaseFragment<ChengyuPrensenterImpl> implements ChengyuView {
    @Bind(R.id.list_recycler_view)
    RecyclerView recyclerView;
    @Bind(R.id.search_view)
    CommonSearchView searchView;

    private ChengyuAdapter adapter;
    private String SearchKey="人";

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
        init();
        initSearchView();
    }

    private void init() {
        adapter = new ChengyuAdapter(getActivity());
        adapter.setOnRecycleViewItemClickListener(new BaseRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, Object data, int position) {
                ChengyuModel.ChengyuDetailModel detailModel = (ChengyuModel.ChengyuDetailModel) data;
            }
        });
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),1));
        recyclerView.setAdapter(adapter);
        mSwipeRefreshLayout.setOnRefreshListener(this::refresh);
        refresh();
    }

    private void refresh() {
        mPresenter.getData(SearchKey);
        adapter.setHighLightText(SearchKey);
        refreshLoading();
    }

    private void initSearchView() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (!TextUtils.isEmpty(query.trim())) {
                    SearchKey = query.trim();
                    hideInput();
                    refresh();
                    searchView.setVisibility(View.GONE);
                }

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (!TextUtils.isEmpty(newText.trim())) {
                    SearchKey = newText.trim();
                    refresh();
                }
                return false;
            }
        });
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
            toggleSearchView();
        }

        return super.onOptionsItemSelected(item);
    }
    private void toggleSearchView(){
        if (searchView.getVisibility() == View.VISIBLE) {
            searchView.clearFocus();
            hideInput();
            searchView.setVisibility(View.GONE);
        }else {
            searchView.requestFocus();
            showInput(searchView,200);
            searchView.setVisibility(View.VISIBLE);
        }
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
        stopLoading();
        adapter.replaceAll(list);
    }

    @Override
    public void getDataFailed(String error) {
        stopLoading();
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
