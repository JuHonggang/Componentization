package com.sxu.commonlibrary.datasource.preference;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import java.util.Set;

/*******************************************************************************
 * 对于单个或少量数据, 提倡使用putXXX保存数据,
 * 如果保存多个数据,提倡使用putXXXNoCommit进行保存, 最后调用commit进行持久化。
 *
 * Author: Freeman
 *
 *******************************************************************************/
public class PreferencesUtils {

    private static SharedPreferences preferences;
    private static SharedPreferences.Editor editor;
    private static String PREFERENCE_NAME = "SXU";
    private static Context context;

    public static void init(Context cxt) {
        init(cxt, PREFERENCE_NAME);
    }

    public static void init(Context cxt, String preferenceName) {
        context = cxt;
        PREFERENCE_NAME = preferenceName;
        preferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        editor = preferences.edit();
    }

    public static boolean putString(String name, String value) {
        editor.putString(name, value);
        return editor.commit();
    }

    public static void putStringNoCommit(String name, String value) {
        editor.putString(name, value);
    }

    public static String getString(String key) {
        return preferences.getString(key, "");
    }

    public static String getString(String key,String defaultValue) {
        return preferences.getString(key, defaultValue);
    }

    public static boolean putBoolean(String name, boolean value) {
        editor.putBoolean(name, value);
        return editor.commit();
    }

    public static void putBooleanNoCommit(String name, boolean value) {
        editor.putBoolean(name, value);
    }

    public static boolean getBoolean(String key) {
        return preferences.getBoolean(key, false);
    }

    public static boolean getBoolean(String key, boolean defaultValue) {
        return preferences.getBoolean(key, defaultValue);
    }

    public static boolean putInt(String name, int value) {
        editor.putInt(name, value);
        return editor.commit();
    }

    public static void putIntNoCommit(String name, int value) {
        editor.putInt(name, value);
    }

    public static int getInt(String key) {
        return preferences.getInt(key, 0);
    }

    public static boolean putLong(String name, long value) {
        editor.putLong(name, value);
        return editor.commit();
    }

    public static void putLongNoCommit(String name, long value) {
        editor.putLong(name, value);
    }

    public static long getLong(String key) {
        return preferences.getLong(key, 0);
    }

    public static boolean putFloat(String name, float value) {
        editor.putFloat(name, value);
        return editor.commit();
    }

    public static void putFloatNoCommit(String name, float value) {
        editor.putFloat(name, value);
    }

    public static float getFloat(String key) {
        return preferences.getFloat(key, 0);
    }

    public static boolean putStringSet(String name, Set<String> value) {
        editor.putStringSet(name, value);
        return editor.commit();
    }

    public static void putStringSetNoCommit(String name, Set<String> value) {
        editor.putStringSet(name, value);
    }

    public static Set<String> getStringSet(String key) {
        return preferences.getStringSet(key, null);
    }

    public static void commit() {
        editor.apply();
    }
}
