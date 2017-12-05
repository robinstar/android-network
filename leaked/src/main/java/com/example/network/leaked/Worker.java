package com.example.network.leaked;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by yueweiwei on 04/12/2017.
 */

class Worker extends Observable implements Runnable {

    Worker(Observer... observers) {
        for (Observer o : observers) {
            addObserver(o);
        }
    }

    @Override
    public void run() {
        setChanged();
        notifyObservers("started");

        for (int i = 0; i < 100; i++) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            setChanged();
            notifyObservers((i + 1) + "%");
        }

        setChanged();
        notifyObservers("done");
    }

    void removeCallbacks() {
        deleteObservers();
    }
}



