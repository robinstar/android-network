package com.example.network.timeout;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.net.URLConnection;

class ReadTimeout implements Runnable {

    private int timeout = -1;
    private Logger logger;

    ReadTimeout() {
        this(-1);
    }

    ReadTimeout(int timeout) {
        this(timeout, null);
    }

    ReadTimeout(int timeout, Logger logger) {
        this.timeout = timeout;
        this.logger = logger;
    }

    public static void main(String[] args) {
        new Thread(new ReadTimeout()).start();
        new Thread(new ReadTimeout(2000)).start();
    }

    @Override
    public void run() {
        try {
            log("Started");

            final String URL_STR = "http://10.6.8.252:4000/timeout";
            log("Url " + URL_STR);

            URL url = new URL(URL_STR);
            URLConnection connection = url.openConnection();

            log("read timeout " + connection.getReadTimeout());

            if (timeout >= 0) {
                log("set read timeout " + timeout);
                connection.setReadTimeout(timeout);
                log("read timeout " + connection.getReadTimeout());
            }

            log("connecting " + Time.get());
            connection.connect();
            log("connected " + Time.get());

            log("getting input stream " + Time.get());
            InputStream inputStream = connection.getInputStream();
            log("reading input " + Time.get());

            Reader reader = new InputStreamReader(inputStream);
            BufferedReader br = new BufferedReader(reader);
            log(br.readLine());
            br.close();
            inputStream.close();
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
