package com.azhansy.linky.blog;

import android.support.v7.widget.RecyclerView;

import com.azhansy.linky.base.MVP.IBaseUi;
import com.azhansy.linky.blog.model.BlogDetail;
import com.azhansy.linky.blog.model.BlogItem;

import java.util.List;

/**
 * Created by SHU on 2016/7/8.
 */
public interface BlogView extends IBaseUi {
    void LoadHtmlSuccess(List<BlogItem> list);
    void LoadHtmlSuccess(BlogDetail blogDetail);
    void LoadHtmlFailed(String error);

    RecyclerView getRecyclerView();
}
