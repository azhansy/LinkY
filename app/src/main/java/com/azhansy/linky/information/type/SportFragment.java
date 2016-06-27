package com.azhansy.linky.information.type;

import com.azhansy.linky.R;
import com.azhansy.linky.base.BaseFragment;

/**
 * Created by SHU on 2016/6/27.
 * 娱乐
 */
public class SportFragment extends BaseFragment {
    public static SportFragment getInstance(){
        return new SportFragment();
    }
    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_sport;
    }
}
