package com.azhansy.linky.base.MVP;

/**
 *
 * Created by luo_shuai on 2015/9/23 13:52.
 */
public interface ObserverPresenter<T extends ObserverPresenterBaseUi> {

    void attach(T ui);

    void detach(T ui);

}
