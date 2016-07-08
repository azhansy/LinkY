package com.azhansy.linky.information.type;

import com.azhansy.linky.base.BaseWebViewFragment;

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
