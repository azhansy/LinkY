package com.azhansy.linky.blog.adapter;

import android.support.v4.app.FragmentManager;

import com.azhansy.linky.base.BaseFragmentPagerAdapter;
import com.azhansy.linky.blog.fragment.BlogPersonFragment;
import com.azhansy.linky.blog.model.BlogModel;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by SHU on 2016/6/30.
 */
public class BlogFragmentAdapter extends BaseFragmentPagerAdapter {
    private List<BlogModel> blogModelList;
    public BlogFragmentAdapter(FragmentManager fm) {
        super(fm);
        initTitle();
    }
    private void initTitle() {
        blogModelList = new ArrayList<>();
//        blogModelList.add(new BlogModel("郭霖","http://blog.csdn.net/guolin_blog"));  //给重定向了，访问直接回到CSDN首页了。。。。
        blogModelList.add(new BlogModel("张兴业","http://m.blog.csdn.net/blog/index?username=xyz_lmn"));
        blogModelList.add(new BlogModel("鸿洋","http://m.blog.csdn.net/blog/index?username=lmj623565791"));
        blogModelList.add(new BlogModel("任玉刚","http://m.blog.csdn.net/blog/index?username=singwhatiwanna"));
        blogModelList.add(new BlogModel("张涛","http://www.kymjs.com"));
        blogModelList.add(new BlogModel("泡在网上的日子","http://www.jcodecraeer.com/plus/list.php?tid=18"));
//        blogModelList.add(new BlogModel("安卓开发周报","http://www.jcodecraeer.com/a/androidweekly"));
//        blogModelList.add(new BlogModel("格比飞勇","http://blog.csdn.net/azhansy"));
    }
    @Override
    public void initFragments() {
        if (blogModelList != null) {
            for (BlogModel blogModel : blogModelList) {
                addFragment(BlogPersonFragment.getInstance(blogModel.getUrl()));
            }
        }
    }

    @Override
    protected String getTagPrefix() {
        return "BlogFragmentAdapter:";
    }

    @Override
    protected int getFragmentCount() {
        return blogModelList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return blogModelList.get(position).getName();
    }
}
