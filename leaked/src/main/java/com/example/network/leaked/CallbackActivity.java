package com.example.network.leaked;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.Observable;
import java.util.Observer;

public class CallbackActivity extends Activity implements Observer {

    private static final String TAG = CallbackActivity.class.getSimpleName();
    public static final String EXTRA_LEAK = "leak";

    private boolean leak = false;
    private Worker worker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_callback);
        leak = getIntent().getBooleanExtra(EXTRA_LEAK, false);
        log("CallbackActivity.onCreate");

        Observer implemented = this;
        Observer anonymous = anonymousObserver;
        Observer inner = new InnerObserver();
        Observer stat = new StaticObserver(this);
        worker = new Worker(implemented, anonymous, inner, stat);
        new Thread(worker).start();
    }

    @Override
    protected void onDestroy() {
        if (!leak) {
            worker.removeCallbacks();
        }

        log("CallbackActivity.onDestroy");
        super.onDestroy();
        System.gc();
    }

    // Callback一般会更新UI，这个空方法供每个Callback调用。
    private void updateUI() {
        // no-op
    }

    /**
     * Called when the uer taps the Finish button.
     */
    public void finish(View view) {
        finish();
    }

    @Override
    public void update(Observable o, Object arg) {
        log("CallbackActivity.update " + arg);
        updateUI();
    }


    private Observer anonymousObserver = new Observer() {
        @Override
        public void update(Observable o, Object arg) {
            log("AnonymousObserver.update " + arg);
            updateUI();
        }
    };

    private class InnerObserver implements Observer {

        @Override
        public void update(Observable o, Object arg) {
            log("InnerObserver.update " + arg);
            updateUI();
        }
    }

    private static class StaticObserver implements Observer {

        private CallbackActivity activity;

        private StaticObserver(CallbackActivity activity) {
            this.activity = activity;
        }

        @Override
        public void update(Observable o, Object arg) {
            activity.log("StaticObserver.update " + arg);
            activity.updateUI();
        }
    }

    private void log(String log) {
        Log.d(TAG, log);
    }
}
