package com.example.network.timeout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.Vector;

public class TimeoutActivity extends Activity implements Logger {

    public static final String EXTRA_CONNECTION_TIMEOUT = "ct";
    public static final String EXTRA_READ_TIMEOUT = "rt";
    public static final int UPDATE = 1001;

    private final Vector<String> logs = new Vector<>();
    private TextView logView;

    private Runnable runnable;
    private Handler mHandler = new MyHandler(this);

    private static class MyHandler extends Handler {
        WeakReference<TimeoutActivity> myActivity;

        MyHandler(TimeoutActivity activity) {
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

            TimeoutActivity activity = myActivity.get();
            if (activity != null) {
                activity.logs.add(obj.toString());
                activity.displayLog();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeout);
        logView = findViewById(R.id.textView);

        Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_CONNECTION_TIMEOUT)) {
            final int timeout = intent.getIntExtra(EXTRA_CONNECTION_TIMEOUT, -1);
            runnable = new ConnectionTimeout(timeout, this);
        } else if (intent.hasExtra(EXTRA_READ_TIMEOUT)) {
            final int timeout = intent.getIntExtra(EXTRA_READ_TIMEOUT, -1);
            runnable = new ReadTimeout(timeout, this);
        }
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

    @Override
    public void log(@NonNull String log) {
        mHandler.sendMessage(obtainLoggableMessage(log));
    }

    private Message obtainLoggableMessage(@NonNull String log) {
        Message message = Message.obtain();
        message.what = UPDATE;
        message.obj = log;
        return message;
    }

    /**
     * Called when the user taps the Perform button.
     */
    public void perform(View view) {
        if (runnable == null) {
            return;
        }

        view.setEnabled(false);
        new Thread(runnable).start();
    }
}
