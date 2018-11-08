package com.gandsoft.openguide.support;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.gandsoft.openguide.App;
import com.gandsoft.openguide.IConfig;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.gandsoft.openguide.ISeasonConfig.ERROR_RETRIEVAL;

/**
 * Created by kanzan on 3/20/2018.
 */

public class SessionUtil implements IConfig {
    private static final int PRIVATE_MODE = 0;
    private static SharedPreferences pref = App.getContext().getSharedPreferences(PACKAGE_NAME, PRIVATE_MODE);
    private static SharedPreferences.Editor editor = pref.edit();
    Context mContext;

    public static String loadData(String key) {
        return pref.getString(key, ERROR_RETRIEVAL);
    }

    public static boolean checkIfExist(String key) {
        return pref != null ? pref.contains(key) : false;
    }

    public static void checkForNullKey(String key) {
        if (key == null) {
            throw new NullPointerException();
        }
    }

    public static void checkForNullValue(String value) {
        if (value == null) {
            throw new NullPointerException();
        }
    }

    /**/
    public static void setIntPreferences(String key, int value) {
        editor.putInt(key, value);
        editor.commit();
    }

    public static int getIntPreferences(String key, int defaultValue) {
        pref.edit();
        return pref.getInt(key, defaultValue);
    }

    public static void setStringPreferences(String key, String value) {
        editor.putString(key, value);
        editor.commit();
    }

    public static String getStringPreferences(String key, String defaultValue) {
        pref.edit();
        return pref.getString(key, defaultValue);
    }

    public static void setBoolPreferences(String key, Boolean value) {
        editor.putBoolean(key, value);
        editor.commit();
    }

    public static boolean getBoolPreferences(String key, boolean defaultValue) {
        pref.edit();
        return pref.getBoolean(key, defaultValue);
    }

    public static void setDoublePreferences(String key, double value) {
        editor.putLong(key, Double.doubleToRawLongBits(value));
        editor.commit();
    }

    public static double getDoublePreferences(String key, double doubleValue) {
        pref.edit();
        return Double.longBitsToDouble(pref.getLong(key, Double.doubleToLongBits(doubleValue)));
    }

    public static void saveListString(String key, List<String> stringList) {
        checkForNullKey(key);
        String[] myStringList = stringList.toArray(new String[stringList.size()]);
        pref.edit().putString(key, TextUtils.join("‚=‚", myStringList)).apply();
    }

    public static ArrayList<String> loadListString(String key) {
        return new ArrayList<String>(Arrays.asList(TextUtils.split(pref.getString(key, ""), "‚=‚")));
    }

    public static void saveListInt(String key, ArrayList<Integer> intList) {
        checkForNullKey(key);
        Integer[] myIntList = intList.toArray(new Integer[intList.size()]);
        pref.edit().putString(key, TextUtils.join("‚=‚", myIntList)).apply();
    }

    public static ArrayList<Integer> loadListInt(String key) {
        String[] myList = TextUtils.split(pref.getString(key, ""), "‚=‚");
        ArrayList<String> arrayToList = new ArrayList<String>(Arrays.asList(myList));
        ArrayList<Integer> newList = new ArrayList<Integer>();

        for (String item : arrayToList)
            newList.add(Integer.parseInt(item));

        return newList;
    }

    public static void removeKeyPreferences(String key) {
        pref.edit();
        editor.remove(key);
        editor.commit();
    }

    public static void removeAllSharedPreferences() {
        pref.edit();
        editor.clear();
        editor.commit();
    }

    public boolean saveData(Object model, String key) {
        if (pref == null) {
            new SessionUtil();
        }
        try {
            saveModelAsGson(key, model);
        } catch (Exception e) {
            //Log.e("save err: ", e.getMessage());
            return false;
        } finally {
            return true;
        }
    }

    private void saveModelAsGson(String key, Object model) {
        pref.edit();
        Gson gson = new Gson();
        String json = gson.toJson(model);
        editor.putString(key, json);
        editor.commit();
    }

    /*public static void removeAllPreferences() {
        editor.clear().apply();
    }*/
}
