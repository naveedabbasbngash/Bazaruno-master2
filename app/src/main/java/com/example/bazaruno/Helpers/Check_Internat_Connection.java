package com.example.bazaruno.Helpers;

import android.content.Context;
import android.net.ConnectivityManager;

public class Check_Internat_Connection {

    Context context;

    public Check_Internat_Connection(Context context) {
        this.context = context;
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }
}
