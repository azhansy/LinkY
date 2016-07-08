package com.azhansy.linky.blog.presenter;

import com.azhansy.linky.base.MVP.LoadBasePresenter;

/**
 * Created by SHU on 2016/7/8.
 */
public interface BlogListFragmentPrensenter extends LoadBasePresenter{
    void getBlogForName(String name);
    void getBlogDetailForUrl(String url);
}
