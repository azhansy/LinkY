package com.azhansy.linky.blog.adapter;

import android.support.v4.app.FragmentManager;

import com.azhansy.linky.base.BaseFragmentPagerAdapter;
import com.azhansy.linky.blog.fragment.BlogListFragment;
import com.azhansy.linky.blog.model.BlogNameModel;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by SHU on 2016/6/30.
 */
public class BlogFragmentAdapter extends BaseFragmentPagerAdapter {
    private List<BlogNameModel> blogNameModelList;
    public BlogFragmentAdapter(FragmentManager fm) {
        super(fm);
        initTitle();
    }
    private void initTitle() {
        blogNameModelList = new ArrayList<>();
//        blogNameModelList.add(new BlogNameModel("郭霖","guolin_blog")); //重定向了。。。
//        blogNameModelList.add(new BlogNameModel("CSDN资讯",""));
        blogNameModelList.add(new BlogNameModel("张兴业","xyz_lmn"));
        blogNameModelList.add(new BlogNameModel("鸿洋","lmj623565791"));
        blogNameModelList.add(new BlogNameModel("任玉刚","singwhatiwanna"));
        blogNameModelList.add(new BlogNameModel("小巫","wwj_748"));
        blogNameModelList.add(new BlogNameModel("赵四","jiangwei0910410003"));
        blogNameModelList.add(new BlogNameModel("树勇","azhansy"));
//        blogNameModelList.add(new BlogNameModel("张涛","http://www.kymjs.com"));
//        blogNameModelList.add(new BlogNameModel("泡在网上的日子","http://www.jcodecraeer.com/plus/list.php?tid=18"));
//        blogNameModelList.add(new BlogNameModel("安卓开发周报","http://www.androidweekly.cn"));
//        blogNameModelList.add(new BlogNameModel("格比飞勇","http://blog.csdn.net/azhansy"));//http://blog.csdn.net/azhansy/article/list/1
        //wwj_748 小巫
//        jiangwei0910410003 赵四
//        http://m.blog.csdn.net/blog/index?username=xyz_lmn"   http://m.blog.csdn.net/article/details?id=50865621  blog_article
    }
    @Override
    public void initFragments() {
        if (blogNameModelList != null) {
            for (BlogNameModel blogNameModel : blogNameModelList) {
                addFragment(BlogListFragment.getInstance(blogNameModel.getUrl()));
            }
        }
    }

    @Override
    protected String getTagPrefix() {
        return "BlogFragmentAdapter:";
    }

    @Override
    protected int getFragmentCount() {
        return blogNameModelList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return blogNameModelList.get(position).getName();
    }
}
