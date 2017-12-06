package com.example.network.basic;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.example.network.Callback;
import com.example.network.LogActivity;
import com.example.network.NetworkController;
import com.example.network.R;
import com.example.network.Time;

public class NetworkActivity extends LogActivity implements Callback<String> {

    @SuppressWarnings("SpellCheckingInspection")
    private static final String URL = "https://www.baidu.com";

    private WorkThread workThread;
    private NetworkController networkController = new NetworkController(this);
    private Time time;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        time = new Time();
        findViewById(R.id.perform).setVisibility(View.VISIBLE);
    }

    @Override
    protected void onDestroy() {
        if (workThread != null) {
            workThread.cancel(true);
        }
        super.onDestroy();
    }

    @Override
    public void perform(View view) {
        view.setEnabled(false);

        if (!isNetworkAvailable()) {
            onCheckNetworkFailed();
            onRequestEnd();
            return;
        }

        final Worker worker = new Worker(URL, this);
        workThread = new WorkThread(worker);
        workThread.start();
    }

    @Override
    public void onRequestStart(String url) {
        log(time.get() + " 开始访问 " + url);
    }

    @Override
    public boolean isNetworkAvailable() {
        return networkController.isNetworkAvailable();
    }

    @Override
    public void onCheckNetworkFailed() {
        log(time.get() + " 网络不可用");
    }

    @Override
    public void onStartConnecting() {
        log(time.get() + " 开始连接");
    }

    @Override
    public void onConnectFailed() {
        log(time.get() + " 连接失败");
    }

    @Override
    public void onConnectSuccess() {
        log(time.get() + " 连接成功");
    }

    @Override
    public void onGetInputStreamFailed() {
        log(time.get() + " 读取失败");
    }

    @Override
    public void onGetInputStream() {
        log(time.get() + " 读取成功，正在解析数据");
    }

    @Override
    public void onRequestProgress(int percentage) {
        log(time.get() + " 完成 " + percentage + "%");
    }

    @Override
    public void onRequestResult(@NonNull String result) {
        log(time.get() + " 结果：" + result);
    }

    @Override
    public void onRequestError(@NonNull Throwable e) {
        log(time.get() + " 错误：" + e.toString());
    }

    @Override
    public void onRequestCancel() {
        log(time.get() + " 操作被取消");
    }

    @Override
    public void onRequestEnd() {
        log(time.get() + " 请求结束");
    }
}
