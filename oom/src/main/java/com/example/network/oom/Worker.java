package com.example.network.oom;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by yueweiwei on 02/12/2017.
 */

class Worker extends Observable {

    private Worker() {
    }

    private static class Instance {
        private static final Worker INSTANCE = new Worker();
    }

    static Worker get() {
        return Instance.INSTANCE;
    }

    void work(Observer o) {
        addObserver(o);
        /*
        try {
            Thread.sleep(10L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        */
        o.update(this, o);
        System.out.println("Callback count " + countObservers());
    }
}
