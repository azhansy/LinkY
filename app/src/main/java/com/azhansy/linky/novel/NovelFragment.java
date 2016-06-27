package com.azhansy.linky.novel;

import com.azhansy.linky.R;
import com.azhansy.linky.base.BaseFragment;

/**
 * Created by SHU on 2016/6/27.
 */
public class NovelFragment extends BaseFragment {

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_novel;
    }
    public static NovelFragment getInstance(){
        return new NovelFragment();
    }
}
