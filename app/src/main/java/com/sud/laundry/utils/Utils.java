package com.sud.laundry.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.widget.Toast;

import com.sud.laundry.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.TimeoutException;

public class Utils {

    public static String TAG = "Utils";

    public static String getDateTime() {

        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String strDate = sdf.format(c.getTime());
        return strDate;

    }

    public static String getDateTimeHHMM() {

        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy\nHH:mm");
        String strDate = sdf.format(c.getTime());
        return strDate;

    }


    public static long getMilliseconds() {
        long time = System.currentTimeMillis();

        return time;

    }

    public static long TimeDiffence(String timemis) {
        long timeLong = 0;
        if (timemis != null && timemis.length() > 0) {

            timeLong = Long.valueOf(timemis);
            return Utils.getMilliseconds() - timeLong;

        } else {
            return timeLong;
        }
    }

    public static void toastSuccessMessage(Context context, String message) {

        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    public static void toastErrorMessage(Context context, String message) {

        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    public static void toastWarringMessage(Context context, String message) {

        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    public static String fetchErrorMessage(Context context, Throwable throwable) {
        String errorMsg = context.getResources().getString(R.string.error_msg_unknown);

        if (!isNetworkConnected(context)) {
            errorMsg = context.getResources().getString(R.string.error_msg_no_internet);
        } else if (throwable instanceof TimeoutException) {
            errorMsg = context.getResources().getString(R.string.error_msg_timeout);
        }

        return errorMsg;
    }

    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }


}
