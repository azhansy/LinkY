package com.azhansy.linky.ui;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.avos.avoscloud.AVAnalytics;
import com.azhansy.linky.R;
import com.azhansy.linky.base.BaseActivity;
import com.azhansy.linky.base.LinkApplication;
import com.azhansy.linky.blog.fragment.BlogFragment;
import com.azhansy.linky.culture.CultureActivity;
import com.azhansy.linky.information.InformActivity;
import com.azhansy.linky.joke.JokeFragment;
import com.azhansy.linky.login.LoginActivity;
import com.azhansy.linky.column.ChangeChannelActivity;
import com.azhansy.linky.rx.event.ChannelEvent;
import com.azhansy.linky.setting.SettingsActivity;
import com.azhansy.linky.utils.AppManager;
import com.azhansy.linky.utils.Config;
import com.azhansy.linky.utils.SharePreferenceUtil;
import com.azhansy.linky.utils.ToastUtil;
import com.azhansy.linky.weather.WeatherFragment;
import com.azhansy.linky.weather.bean.TodayBean;
import com.azhansy.linky.weekly.WeeklyNewsFragment;
import com.azhansy.linky.weekly.WeeklyNewsListFragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;
import rx.subscriptions.CompositeSubscription;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {

    @Bind(R.id.nav_view)
    NavigationView mNavView;
//    @Bind(R.id.tv_title)
//    TextView mTitle;
    private List<Fragment> fragmentList;
    private Fragment currentFragment;
    ArrayList<Config.Channel> savedChannelList;
    CompositeSubscription mSubscriptions;

    @Override
    public int getLayoutResource() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initSubscript();
        setSwipeBackEnable(false);
        mNavView.setNavigationItemSelectedListener(this);
        initNavChannel();
        AVAnalytics.trackAppOpened(getIntent());//统计应用的打开情况
    }

    private void initSubscript() {
        mSubscriptions = new CompositeSubscription();
        mSubscriptions.add(LinkApplication.getInstance().getRxBus().toObserverable()
                .subscribe(event -> {
                    if (event instanceof TodayBean) {
                        if (navView == null) return;
                        TodayBean today = (TodayBean) event;
                        navView.setText(today.getType() + " " + today.getCurTemp());
                    }
                    if (event instanceof ChannelEvent) {
                        initNavChannel();
                    }

                }, throwable -> {
                }));
    }

    private void initNavChannel() {
        getChannel();//获取本地的栏目
        addFragment();//初始化碎片
        initMenu(); //初始化菜单
    }

    //private WeeklyNewsListFragment weeklyNewsListFragment;
    private void addFragment() {
        if (fragmentList != null) {
            fragmentList.clear();
            fragmentList = null;
        }
        fragmentList = new ArrayList<>();
        for (Config.Channel channel : savedChannelList) {
            if (channel.toString().equals("JOKE")) {
                fragmentList.add(JokeFragment.getInstance());
                continue;
            }
            if (channel.toString().equals("WEATHER")) {
                fragmentList.add(WeatherFragment.getInstance());
                continue;
            }
            if (channel.toString().equals("BLOG")) {
                fragmentList.add(BlogFragment.getInstance());
//                continue;
            }
//            if (channel.toString().equals("WEEKLY")) {
//                if (weeklyNewsListFragment == null) {
//                    weeklyNewsListFragment = WeeklyNewsListFragment.getInstance();
//                }
//                fragmentList.add(weeklyNewsListFragment);
//                continue;
//            }
//            if (channel.toString().equals("NOVEL")) {
//                fragmentList.add(NovelFragment.getInstance());
//                continue;
//            }
//            if (channel.toString().equals("INFORMATION")) {
//                fragmentList.add(InformationFragment.getInstance());
//                continue;
//            }
        }
    }

    private TextView navView;
    private View header;

    private void initMenu() {
        Menu menu = mNavView.getMenu();
        if (header == null) {
            header = mNavView.inflateHeaderView(R.layout.nav_header_main);
            navView = (TextView) header.findViewById(R.id.tv_nav_weather);
            header.findViewById(R.id.cv_culture).setOnClickListener(v -> {
                closeDrawerLayout();
                CultureActivity.launch(this);
            });
        }
        menu.clear();
        for (int i = 0; i < fragmentList.size(); i++) {
            MenuItem menuItem = menu.add(0, i, 0, savedChannelList.get(i).getTitle());
            menuItem.setIcon(savedChannelList.get(i).getIcon());
            menuItem.setCheckable(true);
            if (i == 0) {
                menuItem.setChecked(true);
            }
        }
        mNavView.inflateMenu(R.menu.activity_main_drawer);
        switchFragment(fragmentList.get(0), getString(savedChannelList.get(0).getTitle()));
    }

    private void getChannel() {
        savedChannelList = new ArrayList<>();
        String savedChannel = getSharedPreferences(SharePreferenceUtil.SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE).getString(SharePreferenceUtil.SAVED_CHANNEL, null);
        if (TextUtils.isEmpty(savedChannel)) {
            Collections.addAll(savedChannelList, Config.Channel.values());
        } else {
            for (String s : savedChannel.split(",")) {
                Config.Channel channel = null;
                try {
                    channel = Config.Channel.valueOf(s);
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                }
                if (channel != null) {
                    savedChannelList.add(channel);
                }
            }
        }
    }

    private void switchFragment(Fragment fragment, String title) {
//        Slide slideTransition;
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            //Gravity.START部分机型崩溃java.lang.IllegalArgumentException: Invalid slide direction
//            slideTransition = new Slide(Gravity.LEFT);
//            slideTransition.setDuration(700);
//            fragment.setEnterTransition(slideTransition);
//            fragment.setExitTransition(slideTransition);
//        }
        if (currentFragment == null || !currentFragment.getClass().getName().equals(fragment.getClass().getName())) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment, fragment.getClass().getName()).commit();
            currentFragment = fragment;
//            setTitle(title);
        }
    }

    //    private void setTitle(String title){
//        mTitle.setText(title);
//    }
    public static void launch(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id < fragmentList.size() && savedChannelList != null) {
            switchFragment(fragmentList.get(id), getString(savedChannelList.get(id).getTitle()));
        }
        switch (id) {
            case R.id.nav_login:
                LoginActivity.launch(this);
                break;
            case R.id.nav_inform:
                InformActivity.launch(this);
                break;
            case R.id.nav_night:
                SharePreferenceUtil.setNight(this, !SharePreferenceUtil.isNight());
                finish();
                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.nav_setting:
//                closeDrawerLayout();
                SettingsActivity.launch(this);
                break;
            case R.id.nav_change:
                ChangeChannelActivity.launch(this);
                break;
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        assert drawer != null;
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
//        if (weeklyNewsListFragment != null && weeklyNewsListFragment.grade == 1) {
//           weeklyNewsListFragment.onBackPress();
//        }else {
        if (!closeDrawerLayout()) {
            exitBy2Click();
        }
//        }
    }

    /**
     * 关闭侧滑菜单，返回true
     *
     * @return 返回侧滑菜单是否已经关闭，执行关闭动作返回真
     */
    private boolean closeDrawerLayout() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        assert drawer != null;
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
            return true;
        }
        return false;
    }

    private static Boolean isExit = false; // used for exit by twice

    private void exitBy2Click() {
        Timer timer;
        if (!isExit) {
            isExit = true;
            ToastUtil.showToast(this, this.getResources().getString(R.string.press_twice_to_exit));
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    isExit = false;
                }
            }, 2000);
        } else {
            AppManager appManager = AppManager.getAppManager();
            appManager.ExitApp(this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSubscriptions.unsubscribe();
    }
}

