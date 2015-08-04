package com.sthelper.sthelper.util;

import android.content.SharedPreferences;

import com.sthelper.sthelper.SApp;

/**
 * Created by luffy on 15/7/2.
 */
public class SPUtil {

    public static void save(String key, int value) {
        SharedPreferences preferences = SApp.getInstance().preferences;
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public static void save(String key, String value) {
        SharedPreferences preferences = SApp.getInstance().preferences;
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static void save(String key, boolean value) {
        SharedPreferences preferences = SApp.getInstance().preferences;
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public static int getInt(String key) {
        SharedPreferences preferences = SApp.getInstance().preferences;
        int value = preferences.getInt(key, 0);
        return value;
    }

    public static String getString(String key) {
        SharedPreferences preferences = SApp.getInstance().preferences;
        String value = preferences.getString(key, "");
        return value;
    }

    public static boolean getSBoolean(String key) {
        SharedPreferences preferences = SApp.getInstance().preferences;
        boolean value = preferences.getBoolean(key, false);
        return value;
    }

    public static void clean() {
        SharedPreferences preferences = SApp.getInstance().preferences;
        SharedPreferences.Editor editor = preferences.edit();
        editor.commit();

    }


}
