package com.azhansy.linky.information.type;

import com.azhansy.linky.R;
import com.azhansy.linky.base.BaseFragment;

/**
 * Created by SHU on 2016/6/27.
 * 军事
 */
public class MartailFragment extends BaseFragment {
    public static MartailFragment getInstance(){
        return new MartailFragment();
    }
    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_martial;
    }
}
