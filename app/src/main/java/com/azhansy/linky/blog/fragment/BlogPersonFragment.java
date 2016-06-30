package com.azhansy.linky.blog.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.azhansy.linky.R;
import com.azhansy.linky.base.BaseFragment;

import butterknife.Bind;

/**
 * Created by SHU on 2016/6/30.
 * 个人博客
 */
public class BlogPersonFragment extends BaseFragment{
    public static String STRINGURL = "BlogPersonFragment";

    @Bind(R.id.web_view)
    WebView mWebView;

    @Override
    protected int getLayoutResource() {
        return R.layout.layout_common_web_view;
    }

    public static BlogPersonFragment getInstance(String url){
        Bundle arguments = new Bundle();
        arguments.putString(BlogPersonFragment.STRINGURL,url);
        BlogPersonFragment blogPersonFragment = new BlogPersonFragment();
        blogPersonFragment.setArguments(arguments);
        return blogPersonFragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        String urlBlog =getArguments().getString(BlogPersonFragment.STRINGURL);
        if (urlBlog == null || urlBlog.isEmpty()) {
            urlBlog = "http://blog.csdn.net/azhansy";
        }
        mWebView.loadUrl(urlBlog);
        initWebView();
    }

    private void initWebView() {
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        mWebView.setWebViewClient(new WebViewClient());
        mWebView.setOnKeyListener(new View.OnKeyListener(){

            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()) {
                    handler.sendEmptyMessage(1);
                    return true;
                }
                return false;
            }

        });
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message message) {
            switch (message.what) {
                case 1:{
                    webViewGoBack();
                }break;
            }
        }
    };
    private void webViewGoBack(){
        mWebView.goBack();
    }
}
