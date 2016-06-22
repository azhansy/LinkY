package com.azhansy.linky.joke;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.azhansy.linky.R;
import com.azhansy.linky.base.MVP.MVPBaseFragment;
import com.azhansy.linky.joke.presenter.JokePresenterImpl;

import butterknife.Bind;

/**
 * Created by SHU on 2016/6/22.
 */
public class JokeFragment extends MVPBaseFragment<JokePresenterImpl> implements JokeView {
    @Bind(R.id.list_recycler_view)
    RecyclerView list;

    public static JokeFragment getInstance(){
        return  new JokeFragment();
    }
    @Override
    protected boolean usePresenter() {
        return true;
    }

    @Override
    protected JokePresenterImpl createPresenter() {
        return new JokePresenterImpl();
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.layout_of_recycle;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter.getData();
    }

    @Override
    public Context getPresenterContext() {
        return getActivity();
    }

    @Override
    public void setAdapter(JokeAdapter adapter) {
        list.setLayoutManager(new GridLayoutManager(getContext(),1));
        list.setAdapter(adapter);
    }
}
