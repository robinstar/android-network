package com.example.network;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by yueweiwei on 04/12/2017.
 */

public final class Time {

    private final SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss.SSS", Locale.getDefault());
    private long lastCalledTime = 0;

    public String get() {
        final long now = System.currentTimeMillis();
        final String time = formatter.format(new Date(now));

        final String cost;
        if (lastCalledTime > 0) {
            cost = String.valueOf(now - lastCalledTime);
        } else {
            cost = null;
        }
        lastCalledTime = now;

        if (cost != null) {
            return time + " " + cost + "ms";
        }

        return time;
    }
}
