package com.azhansy.linky.setting;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.azhansy.linky.base.BaseActivity;
import com.azhansy.linky.utils.Logger;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;

/**
 * Created by SHU on 2016/8/9.
 * 测试知识点
 */
public class TestActivity extends BaseActivity {
    Observer<String> observer;
    Observable<String> observable;
    @Override
    public int getLayoutResource() {
        return 0;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        createObserver();
        createObservable();
        observable.subscribe(observer);
    }

    /**
     * 创建观察者
     * @link http://gank.io/post/560e15be2dca930e00da1083
     */
    private void createObserver() {
        observer = new Observer<String>() {
            @Override
            public void onCompleted() {
                Logger.d("createObserver", "Completed!");
            }

            @Override
            public void onError(Throwable e) {
                Logger.d("createObserver", "Error!");
            }

            @Override
            public void onNext(String s) { //事件响应
                Logger.d("createObserver", "Item: " + s);
            }
        };
    }

    /**
     * 创建观察者的抽象类 Subscriber，跟观察者类似
     * 1、onStart(): 这是 Subscriber 增加的方法。它会在 subscribe 刚开始，而事件还未发送之前被调用，
     * 可以用于做一些准备工作，例如数据的清零或重置。这是一个可选方法，默认情况下它的实现为空。
     * 需要注意的是，如果对准备工作的线程有要求（例如弹出一个显示进度的对话框，这必须在主线程执行），
     * onStart() 就不适用了，因为它总是在 subscribe 所发生的线程被调用，而不能指定线程。
     * 要在指定的线程来做准备工作，可以使用 doOnSubscribe() 方法，具体可以在后面的文中看到。
     *
     * 2、unsubscribe(): 这是 Subscriber 所实现的另一个接口 Subscription 的方法，用于取消订阅。
     * 在这个方法被调用后，Subscriber 将不再接收事件。一般在这个方法调用前，
     * 可以使用 isUnsubscribed() 先判断一下状态。 unsubscribe() 这个方法很重要，因为在 subscribe() 之后，
     * Observable 会持有 Subscriber 的引用，这个引用如果不能及时被释放，将有内存泄露的风险。
     * 所以最好保持一个原则：要在不再使用的时候尽快在合适的地方（例如 onPause() onStop() 等方法中）
     * 调用 unsubscribe() 来解除引用关系，以避免内存泄露的发生。
     */
    private void createAbsObserver() {
        Subscriber<String> subscriber = new Subscriber<String>() {
            @Override
            public void onCompleted() {
                Logger.d("createAbsObserver", "Completed!");
            }

            @Override
            public void onError(Throwable e) {
                Logger.d("createAbsObserver", "Error!");
            }

            @Override
            public void onNext(String s) {
                Logger.d("createAbsObserver", "Item: " + s);
            }
        };
    }

    /**
     * 创建被观察者
     */
    private void createObservable(){
        observable = Observable.create(new Observable.OnSubscribe<String>() { //create() 方法是 RxJava 最基本的创造事件序列的方法
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("first");
                subscriber.onNext("second");
                subscriber.onNext("third");
                subscriber.onCompleted();
            }
        });
//        observable = Observable.just("Hello", "Hi", "Aloha");//将对象或者对象集合转换为一个会发射这些对象的Observable
        //将传入的数组或 Iterable 拆分成具体对象后，依次发送出来。
//        String[] words = {"Hello", "Hi", "Aloha"};
//        observable = Observable.from(words);
    }

    /**
     *  * subscribeOn(): 指定 subscribe() 所发生的线程，
     *  即 Observable.OnSubscribe 被激活时所处的线程。或者叫做事件产生的线程。
     *  * observeOn(): 指定 Subscriber 所运行在的线程。或者叫做事件消费的线程。
     *
     */

    public static void launch(Context context) {
        context.startActivity(new Intent(context, TestActivity.class));
    }
}
