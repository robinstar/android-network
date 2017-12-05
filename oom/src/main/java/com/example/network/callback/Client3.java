package com.example.network.callback;

/**
 * Created by yueweiwei on 02/12/2017.
 */

class Client3 extends Client {

    private Callback callback = new InnerCallback();

    // 非静态内部类
    class InnerCallback implements Callback {

        @Override
        public void start() {
            Client3.this.onStart();
        }

        @Override
        public void done() {
            Client3.this.onDone();
        }
    }
}
