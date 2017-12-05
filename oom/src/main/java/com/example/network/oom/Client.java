package com.example.network.oom;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by yueweiwei on 02/12/2017.
 */

class Client {

    private int[] data;

    Client() {
        data = new int[1024 * 1024];
    }

    void work() {
        Worker.get().work(new Observer() {
            @Override
            public void update(Observable observable, Object o) {
                System.out.println("work done, callback = [" + o + "]");
                // observable.deleteObserver(this);
            }
        });
    }
}
