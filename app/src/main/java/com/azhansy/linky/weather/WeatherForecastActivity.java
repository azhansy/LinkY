package com.azhansy.linky.weather;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.azhansy.linky.R;
import com.azhansy.linky.base.BaseActivity;
import com.azhansy.linky.weather.adapter.ForecastAdapter;
import com.azhansy.linky.weather.bean.ForecastBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by SHU on 2016/6/29.
 */
public class WeatherForecastActivity extends BaseActivity {
    public static String MFORECASTLIST = "mForecastList";

    @Bind(R.id.list_recycler_view)
    RecyclerView mRecycleView;

    private ForecastAdapter mForecastAdapter;
    private List<ForecastBean>  mForecastList;

    @Override

    public int getLayoutResource() {
        return R.layout.activity_of_forecast_weather;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {
        mForecastList = getIntent().getParcelableArrayListExtra(MFORECASTLIST);
        mForecastAdapter = new ForecastAdapter(this);
        mForecastAdapter.replaceAll(mForecastList);
        mRecycleView.setLayoutManager(new GridLayoutManager(this,1));
        mRecycleView.setAdapter(mForecastAdapter);
    }

    public static void launch(Context context,ArrayList<ForecastBean> list) {
        Intent intent = new Intent(context, WeatherForecastActivity.class);
        intent.putParcelableArrayListExtra(MFORECASTLIST, list);
        context.startActivity(intent);
    }
}
