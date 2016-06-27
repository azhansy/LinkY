package com.azhansy.linky.information;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.azhansy.linky.R;
import com.azhansy.linky.base.BaseFragment;


/**
 * Created by SHU on 2016/6/27.
 */
public class InformationFragment extends BaseFragment{

    private InformTabFragment informTabFragment;

    public static InformationFragment getInstance(){
        return new InformationFragment();
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_base;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (savedInstanceState == null) {
            informTabFragment = InformTabFragment.getInstance();
            getFragmentManager().beginTransaction().replace(R.id.fragment_container, informTabFragment, "InformTabFragment").commit();
        }else {
            informTabFragment = (InformTabFragment) getFragmentManager().findFragmentByTag("InformTabFragment");
        }
    }
}
