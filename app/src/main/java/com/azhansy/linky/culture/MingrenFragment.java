package com.azhansy.linky.culture;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
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
import com.azhansy.linky.view.CommonSearchView;

import java.util.List;

import butterknife.Bind;

/**
 * Created by SHU on 2016/7/4.
 * 名人名言 fragment
 */
public class MingrenFragment extends MVPBaseFragment<MingrenPrensenterImpl> implements MingrenView {
    @Bind(R.id.list_recycler_view)
    RecyclerView recyclerView;
    @Bind(R.id.search_view)
    CommonSearchView searchView;
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
        initSearchView();
        init();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
    }
    private void init() {
        adapter = new MingrenAdapter(getActivity());
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),1);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            private int lastVisibleItem;
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (recyclerView.getAdapter() != null && newState == RecyclerView.SCROLL_STATE_IDLE
                        && lastVisibleItem + 1 == recyclerView.getAdapter().getItemCount() && adapter.isHasMore) {
                    mPresenter.onLoad();
                    refreshLoading();
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem = gridLayoutManager.findLastVisibleItemPosition();
            }
        });
        mSwipeRefreshLayout.setOnRefreshListener(() ->
                mPresenter.onRefresh()
        );
        getData("爱因斯坦");
        searchView.clearFocus();
        hideInput();
    }

    private void getData(String SearchKey) {
        mPresenter.getMingRen(SearchKey);
        adapter.setHighLightText(SearchKey);
        refreshLoading();
        showLoadingDialog();
    }
    private void initSearchView() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (!TextUtils.isEmpty(query.trim())) {
                    getData(query.trim());
                    hideInput();
                    searchView.setVisibility(View.GONE);
                }

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (!TextUtils.isEmpty(newText.trim())) {
                    getData(newText.trim());
                }
                return false;
            }
        });
    }
    private void toggleSearchView(){
        if (searchView.getVisibility() == View.VISIBLE) {
            searchView.setVisibility(View.GONE);
        }else {
            searchView.setVisibility(View.VISIBLE);
        }
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        MenuItem item = menu.add(Menu.NONE, 113, Menu.NONE, "搜索");
        item.setIcon(R.mipmap.ic_menu_search);
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == 113) {
            toggleSearchView();
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
        stopLoading();
        closeLoadingDialog();
        adapter.replaceAll(list);
    }

    @Override
    public void getDataFailed(String error) {
        stopLoading();
        closeLoadingDialog();
        ToastUtil.showToast(getActivity(),error);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
