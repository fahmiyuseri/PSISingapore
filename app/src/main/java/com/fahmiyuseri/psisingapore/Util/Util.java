package com.fahmiyuseri.psisingapore.Util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by IRSB on 14/3/2018.
 */

public class Util {
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        context = null;
        return (activeNetworkInfo != null && activeNetworkInfo.isConnected());
    }
}
