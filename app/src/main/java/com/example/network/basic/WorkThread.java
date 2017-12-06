package com.example.network.basic;

/**
 * Created by yueweiwei on 06/12/2017.
 */

class WorkThread extends Thread {

    private Worker worker;

    WorkThread(Worker worker) {
        super(worker);
        this.worker = worker;
    }

    void cancel(boolean interrupt) {
        if (interrupt) {
            interrupt();
        }
        worker.cancel();
    }
}
