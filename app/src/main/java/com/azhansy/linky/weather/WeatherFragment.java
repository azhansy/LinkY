package com.azhansy.linky.weather;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.azhansy.linky.R;
import com.azhansy.linky.base.LinkApplication;
import com.azhansy.linky.base.MVP.MVPBaseFragment;
import com.azhansy.linky.citypicker.CityPickerActivity;
import com.azhansy.linky.utils.SharePreferenceUtil;
import com.azhansy.linky.utils.ToastUtil;
import com.azhansy.linky.weather.bean.ForecastBean;
import com.azhansy.linky.weather.bean.TodayBean;
import com.azhansy.linky.weather.presenter.WeatherPresenterImpl;
import com.azhansy.linky.weather.view.ViewToday;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by SHU on 2016/6/17.
 */
public class WeatherFragment extends MVPBaseFragment<WeatherPresenterImpl> implements ViewToday {
    @Bind(R.id.tv_curtemp)
    TextView mCurtemp;
    @Bind(R.id.tv_city)
    TextView mCity;
    @Bind(R.id.tv_date)
    TextView mDate;
    @Bind(R.id.tv_week)
    TextView mWeek;
    @Bind(R.id.tv_type)
    TextView mType;
    @Bind(R.id.tv_fengxiang)
    TextView mFengxiang;
    @Bind(R.id.tv_fengli)
    TextView mFengli;
    @Bind(R.id.rv_weather)
    RecyclerView mWeatherList;
    @Bind(R.id.toolbar_title)
    TextView mTitle;

    @OnClick({R.id.float_btn, R.id.appbar})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.float_btn:
                if (forecastBeanList != null) {
                    WeatherForecastActivity.launch(getActivity(), (ArrayList<ForecastBean>) forecastBeanList);
                }
                break;
            case R.id.appbar:
                Intent intent = new Intent(getActivity(), CityPickerActivity.class);
                startActivityForResult(intent, CityPickerActivity.RESULT_FIRST_USER);
                break;
        }
    }


    private List<ForecastBean> forecastBeanList;
    private RequestParams params;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_weather;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mTitle.setText("天气预报");
        mSwipeRefreshLayout.setOnRefreshListener(() -> {
            mPresenter.getData(params);
        });
        params = new RequestParams();
        params.put("cityname", SharePreferenceUtil.getCityName());
        mPresenter.getData(params);
        refreshLoading();
    }

    public static WeatherFragment getInstance() {
        return new WeatherFragment();
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
        mWeatherList.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        mWeatherList.setAdapter(adapter);
    }

    @Override
    public void setHead(String s) {
        mCity.setText(s);
        stopLoading();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CityPickerActivity.RESULT_FIRST_USER && resultCode == CityPickerActivity.RESULT_OK) {
            Bundle bundle = data.getExtras();
            String cityName = bundle.getString(CityPickerActivity.KEY_PICKED_CITY);
            if (cityName != null && !cityName.isEmpty()) {
                RequestParams params = new RequestParams();
                params.put("cityname", cityName);
                mPresenter.getData(params);
                mCity.setText(cityName);
                SharePreferenceUtil.setCityName(cityName);
            }
        }
    }

    @Override
    public void setTempData(TodayBean todayBean) {
        mCurtemp.setText(todayBean.getCurTemp());
        mDate.setText(todayBean.getDate());
        mWeek.setText(todayBean.getWeek());
        mType.setText(todayBean.getType());
        mFengxiang.setText(todayBean.getFengxiang());
        mFengli.setText(todayBean.getFengli());

        LinkApplication.getInstance().getRxBus().send(todayBean);

    }


    @Override
    public void setForecast(List<ForecastBean> forecast) {
        forecastBeanList = forecast;
    }

    @Override
    public void getDataFail() {
        ToastUtil.showToast(getActivity(), "请求失败，请稍后再试");
        stopLoading();
    }

    @Override
    public Context getPresenterContext() {
        return getActivity();
    }


}
