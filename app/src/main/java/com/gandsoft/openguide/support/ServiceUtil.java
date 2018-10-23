package com.gandsoft.openguide.support;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class ServiceUtil {

    public static void startService(Activity activity, Class<?> targetClass) {
        if (isMyServiceRunning(activity, targetClass))
            activity.startService(new Intent(activity, targetClass));
    }

    public static void stopSevice(Activity activity, Class<?> targetClass) {
        activity.stopService(new Intent(activity, targetClass));
    }

    public static boolean isMyServiceRunning(Activity activity, Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) activity.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                Log.i("Service already", "running");
                return true;
            }
        }
        Log.i("Service not", "running");
        return false;
    }

    public static boolean isMyServiceRunning(Context context, Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                Log.i("Service already", "running");
                return true;
            }
        }
        Log.i("Service not", "running");
        return false;
    }
}
