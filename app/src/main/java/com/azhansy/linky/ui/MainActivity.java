package com.azhansy.linky.ui;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.azhansy.linky.R;
import com.azhansy.linky.base.BaseActivity;
import com.azhansy.linky.joke.JokeFragment;
import com.azhansy.linky.login.LoginActivity;
import com.azhansy.linky.swipebackhelper.SwipeBackHelper;
import com.azhansy.linky.utils.ToastUtil;
import com.azhansy.linky.weather.WeatherFragment;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {

    @Bind(R.id.nav_view)
    NavigationView mNavView;

//    WeatherFragment weatherFragment;
    JokeFragment jokeFragment;
    @Override
    public int getLayoutResource() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SwipeBackHelper.getCurrentPage(this).setSwipeBackEnable(false);
        mNavView.setNavigationItemSelectedListener(this);
        initMenu();
        initFragment();
    }

    private void initFragment() {
        if (jokeFragment == null) {
            jokeFragment = JokeFragment.getInstance();
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, jokeFragment, "weatherFragment").commit();
    }

    private void initMenu() {
        Menu menu = mNavView.getMenu();
        menu.clear();
        mNavView.inflateMenu(R.menu.activity_main_drawer);
    }

    public static void launch(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.nav_night:
//                Config.isNight = !Config.isNight;
                finish();
                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.nav_setting:
                LoginActivity.launch(this);
                break;
            case R.id.nav_change:
                break;
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        assert drawer != null;
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        assert drawer != null;
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            exitBy2Click();
        }
    }

    private static Boolean isExit = false; // used for exit by twice
    private void exitBy2Click(){
        Timer timer;
        if (!isExit) {
            isExit = true;
            ToastUtil.showToast(this,this.getResources().getString(R.string.press_twice_to_exit));
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    isExit = false;
                }
            }, 2000);
        }else {
            finish();
            System.exit(0);
        }
    }
}

