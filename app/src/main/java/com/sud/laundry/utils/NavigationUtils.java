package com.sud.laundry.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.core.app.ShareCompat;

@SuppressWarnings("WeakerAccess")
public class NavigationUtils {

    public static void startActivity(Context context, Intent intent){
        context.startActivity(intent);
    }
    public static void startActivity(Context context, Class clazz){
        Intent intent = createIntent(context, clazz);
        startActivity(context, intent);
    }

    public static void startActivity(Context context, Class clazz, int flags){
        Intent intent = createIntentWithFlags(context, clazz, flags);
        startActivity(context, intent);
    }


    public static void startActivity(Context context, Class clazz, int flags, int userId){
        Intent intent = createIntentWithFlags(context, clazz, flags);
        intent.putExtra(KeyData.KEY_USER_ID, userId);
        startActivity(context, intent);
    }
    public static void startActivity(Context context, Class clazz, int flags, String userId){
        Intent intent = createIntentWithFlags(context, clazz, flags);
        intent.putExtra(KeyData.KEY_STRING_DATA, userId);
        startActivity(context, intent);
    }

    @NonNull
    public static Intent createIntentWithFlags(Context context, Class clazz, int flags){
        Intent intent = createIntent(context, clazz);
        intent.setFlags(flags);
        return intent;
    }

    @NonNull
    public static Intent createIntent(Context context, Class clazz){
        Intent intent = new Intent(context, clazz);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        return intent;
    }

    public static void shareMyApp(Activity context, String refercode ) {

        String link = "https://play.google.com/store/apps/details?id="+context.getPackageName() +"&refercode="+ refercode;

//        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
//        sharingIntent.setType("text/*");
//        sharingIntent.putExtra(Intent.EXTRA_TEXT, link);
//        context.startActivity(Intent.createChooser(sharingIntent, "Share Via"));
//
        try {
            ShareCompat.IntentBuilder.from(context)
                    .setType("text/plain")
                    .setChooserTitle("Share Code")
                    .setText(link)
                    .startChooser();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
