package com.example.network.leaked;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;

public class MessageActivity extends Activity {

    private static final String TAG = MessageActivity.class.getSimpleName();
    public static final int ANONYMOUS_CLASS_MESSAGE = 1001;
    public static final int INNER_CLASS_MESSAGE = 1002;
    public static final int STATIC_CLASS_MESSAGE = 1003;

    public static final String EXTRA_LEAK = "leak";

    private boolean leak = false;

    private Handler mInnerHandler = new InnerHandler();
    private Handler mStaticHandler = new StaticHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        leak = getIntent().getBooleanExtra(EXTRA_LEAK, false);
        log("MessageActivity.onCreate");

        mInnerHandler.sendEmptyMessageDelayed(INNER_CLASS_MESSAGE, 1000);
    }

    @Override
    protected void onDestroy() {
        if (!leak) {
            mAnonymousHandler.removeMessages(ANONYMOUS_CLASS_MESSAGE);
            mInnerHandler.removeMessages(INNER_CLASS_MESSAGE);
            mStaticHandler.removeMessages(STATIC_CLASS_MESSAGE);
        }

        log("MessageActivity.onDestroy");
        super.onDestroy();
    }

    /**
     * Called when the uer taps the Finish button.
     */
    public void finish(View view) {
        finish();
    }

    private Handler mAnonymousHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == ANONYMOUS_CLASS_MESSAGE) {
                log("handleMessage in Anonymous Handler.");
                mInnerHandler.sendEmptyMessageDelayed(INNER_CLASS_MESSAGE, 1000);
                System.gc();
            }
        }
    };

    private class InnerHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == INNER_CLASS_MESSAGE) {
                log("handleMessage in Inner Handler.");
                mStaticHandler.sendEmptyMessageDelayed(STATIC_CLASS_MESSAGE, 1000);
                System.gc();
            }
        }
    }

    private static class StaticHandler extends Handler {

        private MessageActivity activity;

        private StaticHandler(MessageActivity activity) {
            this.activity = activity;
        }

        @Override
        public void handleMessage(Message msg) {
            activity.log("handleMessage in Static Handler.");
            activity.mAnonymousHandler.sendEmptyMessageDelayed(ANONYMOUS_CLASS_MESSAGE, 1000);
            System.gc();
        }
    }


    private void log(String log) {
        Log.d(TAG, log);
    }

}