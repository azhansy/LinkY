//package com.azhansy.linky.base;
//
//import android.content.Context;
//import android.os.HandlerThread;
//import android.text.TextUtils;
//
//import com.azhansy.linky.BuildConfig;
//import com.azhansy.linky.R;
//import com.azhansy.linky.utils.HttpUtil;
//import com.azhansy.linky.utils.Logger;
//import com.loopj.android.http.AsyncHttpClient;
//import com.loopj.android.http.RequestParams;
//import com.loopj.android.http.TextHttpResponseHandler;
//
//import cz.msebera.android.httpclient.Header;
//
///**
// * Created by SHU on 2016/6/17.
// */
//public abstract class BaseBusiness {
//    /* AsyncHttpResponseHandler的回调会在内部拥有的Handler所拥有的Looper所在线程中跑，回调中可能有耗时操作，
//            * 如数据库操作，所以在线程中而不是主线程中跑
//    */
//    private static HandlerThread responshHandlerThread;
//    protected RequestParams requestParams;
//
//
//    protected Context mContext;
//    public BaseBusiness(Context context) {
//        this(new RequestParams(), context);
//    }
//
//    public BaseBusiness(RequestParams requestParams, Context context) {
//        this.requestParams = requestParams;
//        this.mContext = context;
//        if (responshHandlerThread == null || !responshHandlerThread.isAlive()) {
//            Logger.i("responshHandlerThread", "responshHandlerThread...new HandlerThread...");
//            responshHandlerThread = new HandlerThread("response_handler_thread");
//            responshHandlerThread.start();
//        }
//    }
//
//    public enum HttpRequestType{
//        Get, Post
//    }
//    public abstract Object onResponseSuccess(int statusCode, String responseString);
//
//    public void onResponsePostExcute(Object object) {
//    }
//
//    public abstract void onResponseFail(int statusCode, String errorMessage);
//
//    public abstract String getUrl();
//
//    /**
//     * 用于标识当前请求任务，关联请求和返回数据 -jd
//     */
//    private String taskID = "";
//    public void execute(HttpRequestType type) {
//
//        taskID = System.currentTimeMillis() + "";
//        if (type == HttpRequestType.Get) {
//
////            Logger.d("====null exception===" + mContext + "=======" + getUrl() + "=======" + requestParams);
//            HttpUtil.get(mContext, getUrl(), requestParams, new TextHttpResponseHandler(responshHandlerThread.getLooper()) {
//
//                @Override
//                public void onStart() {
//                    super.onStart();
//                    if (BuildConfig.DEBUG) {
//                        StringBuffer sb = new StringBuffer("curl -X GET -H ");
////                        sb.append("\"User-Agent:" + HttpUtils.USER_AGENT_PARAM + "\"");
////                        sb.append(" -H ");
////                        sb.append("\"Accept-Language: zh-Hans, zh-Hant, en-us\" -H ");
////                        sb.append("\"Cookie: " + HttpUtils.getAsyncHttpClient().getHeader("Cookie") + "\" ");
//                        sb.append("\"" + AsyncHttpClient.getUrlWithQueryString(true, getUrl(), requestParams) + "\"");
//                        Logger.d("=====do get=====taskID[" + taskID + "]" + sb.toString());
//                    }
//                }
//
//                @Override
//                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
//                    if (BuildConfig.DEBUG) {
//                        Logger.d("=====do get=====taskID[" + taskID + "]" + "onFailure statusCode = [" + statusCode + "], headers = [" + headers + "], responseString==" + responseString);
//                        Logger.json(responseString);
//                    }
//
//
//                    onResponseFail(statusCode, checkOnResponseFailString(statusCode, responseString, throwable));
//                }
//
//                @Override
//                public void onSuccess(int statusCode, Header[] headers, byte[] responseBytes) {
//                    super.onSuccess(statusCode, headers, responseBytes);
//                    if (mDownLoadFileListener != null) {
//                        mDownLoadFileListener.onDownLoadFileSuccess(statusCode, responseBytes);
//                    }
//                }
//
//                @Override
//                public void onSuccess(int statusCode, Header[] headers, String responseString) {
//                    if (BuildConfig.DEBUG) {
//                        Logger.d("=====do get=====taskID[" + taskID + "]" + "onSuccess statusCode = [" + statusCode + "], headers = [" + headers + "], responseString==" + responseString);
//                        Logger.json(responseString);
//                    }
//
//
//                    if (!isError(responseString)) {
//                            onResponseSuccess(statusCode, responseString);
//                    }
//
//                }
//
//                @Override
//                public void onRetry(int retryNo) {
//                    Logger.d("========" + retryNo);
//                }
//            });
//        } else
//
//        {
//            HttpUtil.post(mContext, getUrl(), requestParams, new TextHttpResponseHandler(responshHandlerThread.getLooper()) {
//
//                @Override
//                public void onStart() {
//                    super.onStart();
//                    if (BuildConfig.DEBUG) {
//                        StringBuffer sb = new StringBuffer("curl -X GET -H ");
////                        sb.append("\"User-Agent:" + HttpUtil.USER_AGENT_PARAM + "\"");
//                        sb.append(" -H ");
//                        sb.append("\"Accept-Language: zh-Hans, zh-Hant, en-us\" -H ");
////                        sb.append("\"Cookie: " + HttpUtil.getAsyncHttpClient().getHeader("Cookie") + "\" ");
//                        sb.append("\"" + AsyncHttpClient.getUrlWithQueryString(true, getUrl(), requestParams) + "\"");
//                        Logger.d("=====do post=====taskID[" + taskID + "]" + sb.toString());
//                    }
//                }
//
//                @Override
//                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
//                    if (BuildConfig.DEBUG) {
//                        Logger.d("=====do post=====taskID[" + taskID + "]" + "onFailure statusCode = [" + statusCode + "], headers = [" + headers + "], responseString==" + responseString);
//                        Logger.json(responseString);
//                    }
//
//                    onResponseFail(statusCode, checkOnResponseFailString(statusCode, responseString, throwable));
//                }
//
//                @Override
//                public void onSuccess(int statusCode, Header[] headers, String responseString) {
//                    if (BuildConfig.DEBUG) {
//                        Logger.d("=====do post=====taskID[" + taskID + "]" + "onSuccess statusCode = [" + statusCode + "], headers = [" + headers + "], responseString==" + responseString);
//                        Logger.json(responseString);
//                    }
//                    if (!isError(responseString)) {
//
//                        onResponseSuccess(statusCode, responseString);
//                    }
//                }
//            });
//        }
//    }
//    public interface DownLoadListener {
//        boolean onDownLoadFileSuccess(int statusCode, byte[] bytes);
//    }
//
//    public void setDownLoadFileListener(DownLoadListener listener) {
//        this.mDownLoadFileListener = listener;
//    }
//    private DownLoadListener mDownLoadFileListener;
//
//    /**
//     * 是否产生出错码
//     *
//     * @param responseString
//     * @return
//     */
//    private  boolean isError(String responseString) {
//
//        return false;
//    }
//    /**
//     * onResponseFail 检查并返回指定的提示字符串
//     * @param statusCode
//     * @param responseString
//     * @param throwable
//     * @return
//     */
//    private String checkOnResponseFailString(int statusCode, String responseString, Throwable throwable) {
//        if (statusCode == 0 || TextUtils.isEmpty(responseString)) {
////            if (throwable instanceof SSLException) {// SSL类型的错误，导致网络访问问题；可能是系统日期时间不正确或证书有问题
////                responseString = mContext.getString(R.string.ssl_exception_message);
////            } else {
//            responseString = mContext.getString(R.string.network_exception_message);
////            }
//        }
//        if (statusCode >= 400) {
//            responseString = mContext.getString(R.string.api_request_error_msg, statusCode);
//        }
//        return responseString;
//    }
//}
