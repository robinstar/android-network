package com.example.network;

import android.support.annotation.NonNull;

/**
 * Created by yueweiwei on 06/12/2017.
 * <p>
 * 定义一个通用的Callback
 */

public interface Callback<T> {

    void onRequestStart(String url);

    boolean isNetworkAvailable();

    void onCheckNetworkFailed();

    void onStartConnecting();

    void onConnectFailed();

    void onConnectSuccess();

    void onGetInputStream();

    void onGetInputStreamFailed();

    void onRequestProgress(int percentage);

    void onRequestResult(@NonNull T result);

    void onRequestError(@NonNull Throwable e);

    void onRequestCancel();

    void onRequestEnd();
}
