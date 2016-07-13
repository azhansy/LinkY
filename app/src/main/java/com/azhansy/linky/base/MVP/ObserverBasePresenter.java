package com.azhansy.linky.base.MVP;

import android.content.Context;

import com.azhansy.linky.base.LinkApplication;

import java.lang.ref.WeakReference;

/**
 *
 */
public abstract class ObserverBasePresenter<T extends ObserverPresenterBaseUi> implements ObserverPresenter<T> {

    private WeakReference<T> mObserverUi;

    @Override
    public void attach(T ui) {
        if (ui != null) {
            mObserverUi = new WeakReference<>(ui);
        }
    }

    @Override
    public void detach(T ui) {
        if (mObserverUi != null) {
            mObserverUi.clear();
            mObserverUi = null;
        }
    }

    protected Context getContext() {
        Context context = null;
        T ui = getObserverUi();
        if (ui != null) {
            context = ui.getPresenterContext();
        }
        if (context == null) {
            context = LinkApplication.getInstance().getApplicationContext();
        }
        return context;
    }

    protected T getObserverUi() {
        return mObserverUi == null ? null : mObserverUi.get();
    }

    protected boolean isUiAttached() {
        return mObserverUi != null && mObserverUi.get() != null;
    }

    @SuppressWarnings(value="unchecked")
    protected <AU extends T> AU getActualUi() {
        if (!isUiAttached()) {
            return null;
        }

        ObserverPresenterBaseUi ui = getObserverUi();
        AU au;
        try {
            au = (AU) ui;
        } catch (Exception e) {
            au = null;
        }
        return au;
    }

}
