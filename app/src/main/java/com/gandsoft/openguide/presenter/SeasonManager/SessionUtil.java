package com.gandsoft.openguide.presenter.SeasonManager;

import android.content.Context;
import android.content.SharedPreferences;

import com.gandsoft.openguide.App;
import com.gandsoft.openguide.IConfig;
import com.google.gson.Gson;

import static com.gandsoft.openguide.ISeasonConfig.ERROR_RETRIEVAL;

/**
 * Created by kanzan on 3/20/2018.
 */

public class SessionUtil implements IConfig {
    //SHAREDPREFERENCE
    Context mContext;
    private static final int PRIVATE_MODE = 0;

    private static SharedPreferences pref = App.getContext().getSharedPreferences(PACKAGE_NAME, PRIVATE_MODE);
    private static SharedPreferences.Editor editor = pref.edit();
    //private SharedPreferences pref ;
    //private SharedPreferences.Editor editor ;

    /*public static SessionUtil() {
        this.mContext = App.getContext();
        pref = mContext.getSharedPreferences(IConfig.PACKAGE_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }*/

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

    public static String loadData(String key) {
        return pref.getString(key, ERROR_RETRIEVAL);
    }

    public static boolean checkIfExist(String key) {
        return pref != null ? pref.contains(key) : false;
    }

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

    public static void removeAllSharedPreferences() {
        pref.edit();
        editor.clear();
        editor.commit();
    }

    public static void removeAllPreferences() {
        editor.clear().apply();
    }
}
