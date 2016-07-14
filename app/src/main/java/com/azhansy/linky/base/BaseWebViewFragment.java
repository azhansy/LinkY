package com.azhansy.linky.base;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.azhansy.linky.R;

import butterknife.Bind;

/**
 * Created by SHU on 2016/6/30.
 */
public abstract class BaseWebViewFragment extends BaseFragment {
    @Bind(R.id.web_view)
    WebView mWebView;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_of_base_webview;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initWebView();
    }

    public abstract String getWebViewUrl();

    private void initWebView() {
        String urlBlog =getWebViewUrl();
        if (urlBlog.isEmpty()) {
            urlBlog = "http://blog.csdn.net/azhansy";
        }
        mWebView.loadUrl(urlBlog);
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        mWebView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                showLoadingDialog();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                closeLoadingDialog();
            }
        });
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
