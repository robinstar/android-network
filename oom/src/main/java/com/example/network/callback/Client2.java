package com.example.network.callback;

/**
 * Created by yueweiwei on 02/12/2017.
 */

class Client2 extends Client {

    // 匿名内部类
    private Callback callback = new Callback() {
        @Override
        public void start() {
            Client2.this.onStart();
        }

        @Override
        public void done() {
            Client2.this.onDone();
        }
    };

}
