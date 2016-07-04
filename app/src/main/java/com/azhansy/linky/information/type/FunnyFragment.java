package com.azhansy.linky.information.type;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.azhansy.linky.R;
import com.azhansy.linky.base.BaseFragment;
import com.azhansy.linky.base.BaseWebViewFragment;
import com.azhansy.linky.information.MoreInformActivity;

import butterknife.Bind;

/**
 * Created by SHU on 2016/6/27.
 * 娱乐
 */
public class FunnyFragment extends BaseWebViewFragment {
//    @Bind(R.id.web_view)
//    WebView mWebView;
    public static FunnyFragment getInstance(){
        return new FunnyFragment();
    }

    @Override
    public String getWebViewUrl() {
        return "http://m.news.yule.com.cn";//中国娱乐网
    }


}
