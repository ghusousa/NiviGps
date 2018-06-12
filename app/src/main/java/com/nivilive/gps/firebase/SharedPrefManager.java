package com.nivilive.gps.firebase;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import static com.nivilive.gps.data.Prefs.PREF_KEY_FIREBASE_TOKEN;


public class SharedPrefManager {
    private static final String SHARED_PREF_NAME = "FCMSharedPref";
    private static final String TAG_TOKEN = "tagtoken";

    private static SharedPrefManager mInstance;
    private static Context mCtx;

    private SharedPrefManager(Context context) {
        mCtx = context;
    }

    public static synchronized SharedPrefManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefManager(context);
        }
        return mInstance;
    }

    public static void saveBoolPreferences(Context context, String key,
                                           boolean value) {
        SharedPreferences faves = PreferenceManager.getDefaultSharedPreferences(context);

        SharedPreferences.Editor editor = faves.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public static boolean getBoolPreferences(Context context, String key) {
        SharedPreferences myPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        boolean rtn = myPrefs.getBoolean(key, true);

        return rtn;
    }

    //this method will save the device token to shared preferences
    public boolean saveDeviceToken(String token) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mCtx);
        //    SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(PREF_KEY_FIREBASE_TOKEN, token);
        editor.apply();
        return true;
    }

    //this method will fetch the device token from shared preferences
    public String getDeviceToken() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mCtx);
        return sharedPreferences.getString(PREF_KEY_FIREBASE_TOKEN, null);
    }
}
