package com.example.network;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.Vector;

/**
 * Created by yueweiwei on 04/12/2017.
 */

public abstract class LogActivity extends Activity {

    public static final int UPDATE = 1001;

    private static class MyHandler extends Handler {
        WeakReference<LogActivity> myActivity;

        MyHandler(LogActivity activity) {
            this.myActivity = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            if (msg.what != UPDATE) {
                return;
            }

            Object obj = msg.obj;
            if (obj == null) {
                return;
            }

            LogActivity activity = myActivity.get();
            if (activity != null) {
                activity.logs.add(obj.toString());
                activity.displayLog();
            }
        }
    }

    private final Vector<String> logs = new Vector<>();

    private TextView logView;
    private Handler mHandler = new MyHandler(this);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);

        logView = findViewById(R.id.textView);
    }

    @Override
    protected void onDestroy() {
        mHandler.removeMessages(UPDATE);
        super.onDestroy();
    }

    /**
     * Called when the user taps on the Perform button.
     */
    public void perform(View view) {
    }

    /**
     * Called when the user taps on the Action button.
     */
    public void action(View view) {
    }

    private void displayLog() {
        StringBuilder builder = new StringBuilder();
        for (String log : logs) {
            builder.append(log);
            builder.append("\n");
        }
        builder.deleteCharAt(builder.length() - 1);
        logView.setText(builder.toString());
    }

    protected void log(@NonNull String log) {
        System.out.println(log);
        mHandler.sendMessage(obtainLoggableMessage(log));
    }

    private Message obtainLoggableMessage(@NonNull String log) {
        Message message = Message.obtain();
        message.what = UPDATE;
        message.obj = log;
        return message;
    }

}
