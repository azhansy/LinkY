package com.azhansy.linky.blog.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.azhansy.linky.R;
import com.azhansy.linky.base.MVP.MVPBaseFragment;
import com.azhansy.linky.blog.BlogView;
import com.azhansy.linky.blog.model.BlogDetail;
import com.azhansy.linky.blog.model.BlogItem;
import com.azhansy.linky.blog.presenter.BlogListFragmentPrensenterImpl;
import com.azhansy.linky.utils.JsoupParseUtil;
import com.azhansy.linky.utils.ToastUtil;

import java.util.List;

import butterknife.Bind;

/**
 * Created by SHU on 2016/6/30.
 * 个人博客
 */
public class BlogDetailFragment extends MVPBaseFragment<BlogListFragmentPrensenterImpl> implements BlogView{
    public static String STRINGURL = "BlogDetailFragment";

    @Bind(R.id.web_view)
    WebView mWebView;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_blog_detail;
    }

    public static BlogDetailFragment getInstance(String url){
        Bundle arguments = new Bundle();
        arguments.putString(BlogDetailFragment.STRINGURL,url);
        BlogDetailFragment blogDetailFragment = new BlogDetailFragment();
        blogDetailFragment.setArguments(arguments);
        return blogDetailFragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        String urlBlog =getArguments().getString(BlogDetailFragment.STRINGURL);
        if (urlBlog != null) {
            refreshLoading();
            mPresenter.getBlogDetailForUrl(urlBlog);
        }
        initWebView();
        mSwipeRefreshLayout.setOnRefreshListener(() ->
                mPresenter.getBlogDetailForUrl(urlBlog)
        );
    }

    private void initWebView() {
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setLoadWithOverviewMode(true);
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

    @Override
    protected boolean usePresenter() {
        return true;
    }

    @Override
    protected BlogListFragmentPrensenterImpl createPresenter() {
        return new BlogListFragmentPrensenterImpl();
    }

    @Override
    public void LoadHtmlSuccess(List<BlogItem> list) {

    }

    @Override
    public void LoadHtmlSuccess(BlogDetail blogDetail) {
        stopLoading();
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(formatHtml(JsoupParseUtil.WEB_FRAME,blogDetail.getTexts()));
        mWebView.loadData(stringBuffer.toString(), "text/html; charset=UTF-8", null);
        getActivity().setTitle(blogDetail.getTitle());
    }
    /**
     * 格式化html
     *
     * @param frame 框架
     * @param texts 内容
     * @return
     */
    private String formatHtml(String frame, String texts) {
        return String.format(frame,texts);
    }
    @Override
    public void LoadHtmlFailed(String error) {
        stopLoading();
        ToastUtil.showToast(getActivity(),error);
    }

    @Override
    public RecyclerView getRecyclerView() {
        return null;
    }

    @Override
    public Context getPresenterContext() {
        return getActivity();
    }
}
