package com.example.network.timeout;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

class ConnectionTimeout implements Runnable {

    private int timeout = -1;
    private Logger logger;

    ConnectionTimeout() {
        this(-1);
    }

    ConnectionTimeout(int timeout) {
        this(timeout, null);
    }

    ConnectionTimeout(int timeout, Logger logger) {
        this.logger = logger;
        this.timeout = timeout;
    }

    public static void main(String[] args) {
        new Thread(new ConnectionTimeout()).start();
        new Thread(new ConnectionTimeout(2000)).start();
    }

    @Override
    public void run() {
        try {
            log("Started");

            final String URL_STR = "https://192.168.199.88:5050";
            log("Url " + URL_STR);

            URL url = new URL(URL_STR);
            URLConnection connection = url.openConnection();
            log("connection timeout " + connection.getConnectTimeout());

            if (timeout >= 0) {
                log("set connection timeout " + timeout);
                connection.setConnectTimeout(timeout);
                log("connection timeout " + connection.getConnectTimeout());
            }

            log("connecting " + Time.get());
            connection.connect();
        } catch (IOException e) {
            String log = String.format("%s, time %s", e.toString(), Time.get());
            log(log);
        }

        log("Done");
    }

    private void log(String log) {
        if (logger == null) {
            System.out.println(Thread.currentThread().getName() + ", " + log);
        } else {
            logger.log(Thread.currentThread().getName() + ", " + log);
        }
    }
}
