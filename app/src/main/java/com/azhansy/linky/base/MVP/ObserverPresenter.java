package com.azhansy.linky.base.MVP;

/**
 *
 */
public interface ObserverPresenter<T extends ObserverPresenterBaseUi> {

    void attach(T ui);

    void detach(T ui);

}
