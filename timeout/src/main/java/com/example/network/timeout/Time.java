package com.example.network.timeout;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by yueweiwei on 04/12/2017.
 */

final class Time {

    private static final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd, HH:mm:ss", Locale.getDefault());

    static String get() {
        return formatter.format(new Date(System.currentTimeMillis()));
    }
}
