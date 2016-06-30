package com.azhansy.linky.information.type;

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
import com.azhansy.linky.base.BaseFragment;
import com.azhansy.linky.base.BaseWebViewFragment;

import butterknife.Bind;

/**
 * Created by SHU on 2016/6/27.
 * 军事
 */
public class MartailFragment extends BaseWebViewFragment {
//    @Bind(R.id.web_view)
//    WebView mWebView;
    public static MartailFragment getInstance(){
        return new MartailFragment();
    }

    @Override
    public String getWebViewUrl() {
        return "http://mil.sina.cn";
    }
   /* @Override
    protected int getLayoutResource() {
        return R.layout.fragment_martial;
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        String urlBlog = "http://mil.sina.cn";
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
    }*/
}
