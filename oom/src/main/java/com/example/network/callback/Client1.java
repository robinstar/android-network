package com.example.network.callback;

/**
 * Created by yueweiwei on 02/12/2017.
 */

class Client1 extends Client implements Callback {

    @Override
    public void start() {
        this.onStart();
    }

    @Override
    public void done() {
        this.onDone();
    }
}
