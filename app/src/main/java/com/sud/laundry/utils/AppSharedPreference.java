package com.sud.laundry.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class AppSharedPreference {

	private static AppSharedPreference instance;
	private SharedPreferences preferences, fcmPref;
	
	public static final String PREF_NAME="com-grocery";
	public static final String FCM_NAME = "com-fcm-token";
	public static final String FCM_KEY = "fcm-key";

	public static final String SP_USER_ID = "user_id";
	public static final String SP_USER_NAME = "user_name";
	public static final String SP_PROFILE_URL = "profile_url";
	public static final String SP_TOKEN = "Token_api";

	public static final String SP_PIN_CODE= "pin_code";

	public static final String SP_SBU_DIVISION= "sub_division";

	public static final String SP_CART_VALUE = "cart_value";

	public static final String SP_LOGIN_OR_NOT = "login-not";
	public static final String SP_WEIGHT_SUM = "Weight_sum";
	public static final String SP_TOTAL_ALERT = "Total_Alert";
	public static final String SP_AlertValue = "alert_value";
	public static final String KEY_NOTIFY_STATUS = "alert_value";


	private AppSharedPreference(Context context) {
		preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
		fcmPref = context.getSharedPreferences(FCM_NAME, Context.MODE_PRIVATE);
	}

	public static AppSharedPreference getInstance(Context context) {
		if (instance == null)
			instance = new AppSharedPreference(context);
		return instance;
	}

	public void setFirebaseToken(String token) {
		fcmPref.edit().putString(FCM_KEY, token).apply();
	}

	public String getFcmKey() {
		return fcmPref.getString(FCM_KEY, "");
	}

	public void setString(String key, String value) {
		SharedPreferences.Editor editor = preferences.edit();
		editor.putString(key, value);
		editor.apply();
	}


	public String getString(String key, String defaultStr) {
		return preferences.getString(key, defaultStr);
	}

	private static void checkForNullKey( String key) {
		if (key == null) {
			throw new NullPointerException();
		}
	}

	public void setBoolean(String key , boolean value) {
        SharedPreferences.Editor prefsEditor = preferences.edit();
        prefsEditor.putBoolean(key, value);
        prefsEditor.apply();
	}
	
	
	public Boolean getBoolean(String key, boolean defaultVal) {
		boolean prefName = defaultVal;
		try {

        prefName = preferences.getBoolean(key, defaultVal);

		} catch (Exception e) {
			System.out.println("getBoolean error: "+key);
		}
        return prefName;                          
	}
	
	public void setInteger(String key , int value) {

        SharedPreferences.Editor prefsEditor = preferences.edit();
        prefsEditor.putInt(key, value);
        prefsEditor.apply();
	}
	
	
	public int getInteger(String key, int defaultVal) {
        return preferences.getInt(key, defaultVal);
	}
	
	public void putLong(String key , long value){
        SharedPreferences.Editor prefsEditor = preferences.edit();
        prefsEditor.putLong(key, value);
        prefsEditor.apply();
	}

	public long getLong(String key, long defaultVal) {
        return preferences.getLong(key, defaultVal);
	}
	
	public void removeKey( String key) {
        SharedPreferences.Editor prefsEditor = preferences.edit();
        prefsEditor.remove(key);
        prefsEditor.apply();
	}


}
