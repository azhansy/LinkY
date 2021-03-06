package com.azhansy.linky.rx;

import rx.Observable;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;

/**
 * Created by SHU on 2016/6/17.
 */
public class RxBus {

    //private final PublishSubject<Object> _bus = PublishSubject.create();

    // If multiple threads are going to emit events to this
    // then it must be made thread-safe like this instead
    private final Subject<Object, Object> _bus = new SerializedSubject<>(PublishSubject.create());

    public void send(Object o) {
        _bus.onNext(o);
    }

    public Observable<Object> toObserverable() {
        return _bus;
    }

    public boolean hasObservers() {
        return _bus.hasObservers();
    }

//    // 单例RxBus
//    public static RxBus getDefault() {
//        RxBus rxBus = defaultInstance;
//        if (defaultInstance == null) {
//            synchronized (RxBus.class) {
//                rxBus = defaultInstance;
//                if (defaultInstance == null) {
//                    rxBus = new RxBus();
//                    defaultInstance = rxBus;
//                }
//            }
//        }
//        return rxBus;
//    }
}
