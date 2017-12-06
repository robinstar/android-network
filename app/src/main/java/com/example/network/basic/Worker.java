package com.example.network.basic;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Process;

import com.example.network.Callback;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.URL;

import static com.example.network.basic.Worker.WorkerHandler.CANCEL;
import static com.example.network.basic.Worker.WorkerHandler.CONNECT_FAILED;
import static com.example.network.basic.Worker.WorkerHandler.CONNECT_SUCCESS;
import static com.example.network.basic.Worker.WorkerHandler.END;
import static com.example.network.basic.Worker.WorkerHandler.ERROR;
import static com.example.network.basic.Worker.WorkerHandler.GET_INPUT_STEAM_FAILED;
import static com.example.network.basic.Worker.WorkerHandler.GET_INPUT_STEAM_SUCCESS;
import static com.example.network.basic.Worker.WorkerHandler.NETWORK_UNAVAILABLE;
import static com.example.network.basic.Worker.WorkerHandler.PROGRESS;
import static com.example.network.basic.Worker.WorkerHandler.RESULT;
import static com.example.network.basic.Worker.WorkerHandler.START;
import static com.example.network.basic.Worker.WorkerHandler.START_CONNECTING;

/**
 * Created by yueweiwei on 06/12/2017.
 */

class Worker implements Runnable {

    private WorkerHandler handler;
    private String url;
    private Callback<String> callback;

    static class WorkerHandler extends Handler {
        static final int START = 1;
        static final int NETWORK_UNAVAILABLE = 2;
        static final int START_CONNECTING = 3;
        static final int CONNECT_FAILED = 4;
        static final int CONNECT_SUCCESS = 5;
        static final int GET_INPUT_STEAM_FAILED = 6;
        static final int GET_INPUT_STEAM_SUCCESS = 7;
        static final int PROGRESS = 8;
        static final int RESULT = 9;
        static final int ERROR = 10;
        static final int CANCEL = 11;
        static final int END = 12;

        WeakReference<com.example.network.Callback<String>> callback;

        private WorkerHandler(com.example.network.Callback<String> callback, Looper looper) {
            super(looper);
            this.callback = new WeakReference<>(callback);
        }

        private void removeCallback() {
            callback.clear();
        }

        @Override
        public void handleMessage(Message msg) {
            com.example.network.Callback<String> callback = this.callback.get();
            if (callback == null) {
                return;
            }

            switch (msg.what) {
                case START:
                    callback.onRequestStart((String) msg.obj);
                    break;
                case NETWORK_UNAVAILABLE:
                    callback.onCheckNetworkFailed();
                    break;
                case START_CONNECTING:
                    callback.onStartConnecting();
                    break;
                case CONNECT_FAILED:
                    callback.onConnectFailed();
                    break;
                case CONNECT_SUCCESS:
                    callback.onConnectSuccess();
                    break;
                case GET_INPUT_STEAM_FAILED:
                    callback.onGetInputStreamFailed();
                    break;
                case GET_INPUT_STEAM_SUCCESS:
                    callback.onGetInputStream();
                    break;
                case PROGRESS:
                    callback.onRequestProgress(msg.arg1);
                    break;
                case RESULT:
                    callback.onRequestResult((String) msg.obj);
                    break;
                case ERROR:
                    callback.onRequestError((Throwable) msg.obj);
                    break;
                case CANCEL:
                    callback.onRequestCancel();
                    break;
                case END:
                    callback.onRequestEnd();
                    break;

            }
        }
    }

    Worker(String url, Callback<String> callback) {
        this.url = url;
        this.callback = callback;
        this.handler = new WorkerHandler(callback, Looper.getMainLooper());
    }

    @Override
    public void run() {
        Message message = Message.obtain();
        message.what = START;
        message.obj = url;
        handler.sendMessage(message);

        if (!callback.isNetworkAvailable()) {
            handler.sendEmptyMessage(NETWORK_UNAVAILABLE);
            handler.sendEmptyMessage(END);
            return;
        }

        Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);
        boolean connected = false;

        try {
            URL url = new URL(this.url);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5 * 1000);
            conn.setReadTimeout(5 * 1000);

            handler.sendEmptyMessage(START_CONNECTING);
            conn.connect();
            connected = true;
            handler.sendEmptyMessage(CONNECT_SUCCESS);

            final int code = conn.getResponseCode();
            if (code != HttpURLConnection.HTTP_OK) {
                Throwable e = new IOException("response error, code " + code);
                message = Message.obtain();
                message.what = ERROR;
                message.obj = e;
                handler.sendMessage(message);
                return;
            }

            InputStream is = conn.getInputStream();
            handler.sendEmptyMessage(GET_INPUT_STEAM_SUCCESS);

            Reader reader = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(reader);
            String result = br.readLine();

            message = Message.obtain();
            message.what = PROGRESS;
            message.arg1 = 100;
            handler.sendMessage(message);

            br.close();
            reader.close();
            is.close();

            message = Message.obtain();
            message.what = RESULT;
            message.obj = result;
            handler.sendMessage(message);
        } catch (SocketException e) {
            handler.sendEmptyMessage(CONNECT_FAILED);
        } catch (SocketTimeoutException e) {
            if (connected) {
                handler.sendEmptyMessage(GET_INPUT_STEAM_FAILED);
            } else {
                handler.sendEmptyMessage(CONNECT_FAILED);
            }
        } catch (IOException e) {
            message = Message.obtain();
            message.what = ERROR;
            message.obj = e;
            handler.sendMessage(message);
        } finally {
            handler.sendEmptyMessage(END);
        }
    }

    void cancel() {
        handler.sendEmptyMessage(CANCEL);
        handler.sendEmptyMessage(END);
        callback = null;
        handler.removeCallback();
    }
}
