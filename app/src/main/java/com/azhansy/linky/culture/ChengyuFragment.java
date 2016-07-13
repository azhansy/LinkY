package com.azhansy.linky.culture;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.azhansy.linky.R;
import com.azhansy.linky.base.BaseRecyclerViewAdapter;
import com.azhansy.linky.base.MVP.MVPBaseFragment;
import com.azhansy.linky.culture.adapter.ChengyuAdapter;
import com.azhansy.linky.culture.model.ChengyuAnalysisModel;
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
        adapter.setOnItemClickListener((view, data, position) -> {
            //RecycleView点击事件
            ChengyuModel.ChengyuDetailModel detailModel = (ChengyuModel.ChengyuDetailModel) data;
            mPresenter.getChengyuAnalysis(detailModel.getId());
        });
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),2);
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
        mSwipeRefreshLayout.setOnRefreshListener(() -> {
            mPresenter.onRefresh();
        });
        getData("人");
        searchView.clearFocus();
        hideInput();
    }

    private void getData(String SearchKey) {
        mPresenter.getData(SearchKey);
        adapter.setHighLightText(SearchKey);
        refreshLoading();
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

    private void SnackbarToast(ChengyuAnalysisModel.ChengyuAnalysisModelDetail analysis){
        Snackbar snackbar = Snackbar.make(recyclerView, analysis.getContent(), Snackbar.LENGTH_LONG);
        snackbar.show();
        snackbar.setAction("Look", v -> {
            AlertDialog dialog = new AlertDialog.Builder(getActivity())
                    .setTitle("成语解析")
                    .setView(getDialogView(analysis))
                    .setPositiveButton("确定", null)
                    .create();
            dialog.show();
        });
    }

    private View getDialogView(ChengyuAnalysisModel.ChengyuAnalysisModelDetail analysis){
        View item = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_show_text, null);
        TextView name = (TextView) item.findViewById(R.id.id_name);
        TextView spell = (TextView) item.findViewById(R.id.id_spell);
        TextView content = (TextView) item.findViewById(R.id.id_content);
        TextView samples = (TextView) item.findViewById(R.id.id_samples);
        name.setText(analysis.getName());
        spell.setText(analysis.getSpell());
        content.setText(analysis.getContent());
        samples.setText(analysis.getSamples());
        return item;
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
    public void getDetailSuccess(ChengyuAnalysisModel.ChengyuAnalysisModelDetail detail) {
        stopLoading();
        SnackbarToast(detail);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.onDestroy();
        }
    }
}
