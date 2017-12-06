package com.example.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;

/**
 * Created by yueweiwei on 05/12/2017.
 */

public class NetworkController {

    @NonNull
    private Context context;

    public NetworkController(@NonNull Context context) {
        this.context = context;
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager == null) {
            return false;
        }

        NetworkInfo active = connectivityManager.getActiveNetworkInfo();
        return active != null && active.isAvailable() && active.isConnected();
    }
}
