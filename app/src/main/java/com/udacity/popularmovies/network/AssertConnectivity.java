package com.udacity.popularmovies.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

public class AssertConnectivity {

    private static Context mContext;

    public AssertConnectivity(Context context) {
        this.mContext = context;
    }


    // Check if the phone / device has connectivity.
    public static boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) mContext.getSystemService(mContext.CONNECTIVITY_SERVICE);

        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    // Toast error message for connectivity issue
    public static void errorConnectMessage(String message) {
        Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();
    }

}
