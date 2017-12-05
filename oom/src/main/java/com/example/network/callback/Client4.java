package com.example.network.callback;

/**
 * Created by yueweiwei on 02/12/2017.
 */

class Client4 extends Client {

    private Callback callback = new InnerCallback(this);

    // 静态内部类，和单独的.java文件一样。
    static class InnerCallback implements Callback {

        private Client client;

        InnerCallback(Client client) {
            this.client = client;
        }


        @Override
        public void start() {
            client.onStart();
        }

        @Override
        public void done() {
            client.onDone();
        }
    }
}
