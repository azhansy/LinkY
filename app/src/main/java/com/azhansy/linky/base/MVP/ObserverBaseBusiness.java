//package com.azhansy.linky.base.MVP;
//
//import android.content.Context;
//import android.os.Handler;
//import android.os.Looper;
//
//import com.loopj.android.http.RequestParams;
//import com.yyw.proxy.base.BaseBusiness;
//
///**
// * 使用接口的形式的处理接口以及接口的回调
// *
// * Created by luo_shuai on 2015/9/19 17:07.
// */
//public abstract class ObserverBaseBusiness<T> extends BaseBusiness {
//
//    protected OnFinishListener<T> mOnFinishListener;
//
//
//    public ObserverBaseBusiness(Context context) {
//        this(new RequestParams(), context);
//    }
//
//    public ObserverBaseBusiness(RequestParams requestParams, Context context) {
//        super(requestParams, context);
//    }
//
//    public void setOnFinishListener(OnFinishListener<T> onFinishListener) {
//        mOnFinishListener = onFinishListener;
//    }
//
//    @Override
//    public final void onResponseSuccess(int statusCode, String responseString) {
//        T t = onParseSuccessResult(statusCode, responseString);
//        postToMainThread(t);
//    }
//
//    @Override
//    public final void onResponseFail(int statusCode, String errorMessage) {
//        T t = onParseFailResult(statusCode, errorMessage);
//        postToMainThread(t);
//    }
//
//    /**
//     * 在主线程中处理回调事件
//     */
//    protected void postToMainThread(final T t) {
//        if (mOnFinishListener == null) {
//            return;
//        }
//        if (isInMainThread()) {
//            mOnFinishListener.onFinish(t);
//        } else {
//            Handler handler = new Handler(Looper.getMainLooper());
//            handler.post(() -> mOnFinishListener.onFinish(t));
//        }
//
//    }
//
//    /**
//     * 解析服务端返回的结果，注意是在 子线程 中执行的
//     *
//     * 子类只需要解析结果，父类会在主线程自动调用回调接口
//     */
//    protected abstract T onParseSuccessResult(int statusCode, String responseString);
//
//    /**
//     * 网络库返回的一些错误，比如网络异常等，注意是在 子线程 中执行的
//     *
//     * 子类只需要解析结果，父类会在主线程自动调用回调接口
//     */
//    protected abstract T onParseFailResult(int statusCode, String errorMessage);
//
//    public static boolean isInMainThread() {
//        return Looper.myLooper() == Looper.getMainLooper();
//    }
//
//    public interface OnFinishListener<T> {
//        void onFinish(T t);
//    }
//}
