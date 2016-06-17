package com.azhansy.linky.base;

import com.azhansy.linky.base.MVP.MVPBasePresenter;

import rx.subscriptions.CompositeSubscription;

/**
 * Created by SHU on 2016/6/17.
 */
public class RxBasePresenter extends MVPBasePresenter {

    protected CompositeSubscription mSubscriptions = new CompositeSubscription();
    public void onDestroy() {
        mSubscriptions.unsubscribe();
    }
}
