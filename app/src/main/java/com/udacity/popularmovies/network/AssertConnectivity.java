package com.udacity.popularmovies.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;


import com.udacity.popularmovies.R;

public class AssertConnectivity {

    private static Context context;

    public AssertConnectivity(Context context) {
        this.context = context;
    }


    // Check if the phone / device has connectivity.
    public static boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);

        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    // Toast error message for connectivity issue
    public static void errorConnectMessage(String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

}
