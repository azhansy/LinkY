package com.azhansy.linky.weather;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.azhansy.linky.R;
import com.azhansy.linky.base.MVP.MVPBaseFragment;
import com.azhansy.linky.weather.bean.TodayBean;
import com.azhansy.linky.weather.presenter.WeatherPresenterImpl;
import com.azhansy.linky.weather.view.ViewToday;
import com.loopj.android.http.RequestParams;

import butterknife.Bind;

/**
 * Created by SHU on 2016/6/17.
 */
public class WeatherFragment extends MVPBaseFragment<WeatherPresenterImpl> implements ViewToday {
    @Bind(R.id.tv_curtemp)
    TextView mCurtemp;
    @Bind(R.id.tv_city)
    TextView mCity;
    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_weather;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RequestParams  params = new RequestParams();
        params.put("cityid","101281001");
        mPresenter.getData(params);
    }

    public static WeatherFragment getInstance(){
        return  new WeatherFragment();
    }

    @Override
    protected boolean usePresenter() {
        return true;
    }

    @Override
    protected WeatherPresenterImpl createPresenter() {
        return new WeatherPresenterImpl();
    }

    @Override
    public void setIndexData(WeatherAdapter adapter) {

    }

    @Override
    public void setHead(String s) {
        mCity.setText(s);
    }

    @Override
    public void setTempData(TodayBean todayBean) {

    }

    @Override
    public Context getPresenterContext() {
        return mActivity;
    }
}
