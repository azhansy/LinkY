package com.azhansy.linky.weekly;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.azhansy.linky.base.BaseWebViewFragment;

/**
 * Created by SHU on 2016/7/12.
 */
public class WeeklyNewsFragment extends BaseWebViewFragment {
    @Override
    public String getWebViewUrl() {
        return url;
    }

    private static String WEEKLYNEWSFRAGMENT="WeeklyNewsFragment";
    private String url;
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);
        url = getArguments().getString(WEEKLYNEWSFRAGMENT);
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        MenuItem item = menu.add(Menu.NONE,111,Menu.NONE,"打开");
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == 111) {
            setWeb(url);
        }
        return super.onOptionsItemSelected(item);
    }
    private void setWeb(String ur){
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        Uri content_url = Uri.parse(ur);
        intent.setData(content_url);
        startActivity(intent);
    }

    public static WeeklyNewsFragment getInstance(String url) {
        Bundle argment = new Bundle();
        argment.putString(WEEKLYNEWSFRAGMENT, url);
        WeeklyNewsFragment fragment = new WeeklyNewsFragment();
        fragment.setArguments(argment);

        return fragment;
    }
}
