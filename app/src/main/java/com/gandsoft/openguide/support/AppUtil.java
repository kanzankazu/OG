package com.gandsoft.openguide.support;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;

import com.gandsoft.openguide.ISeasonConfig;
import com.gandsoft.openguide.database.SQLiteHelper;
import com.gandsoft.openguide.view.LoginActivity;
import com.gandsoft.openguide.view.services.MyService;
import com.google.firebase.auth.FirebaseAuth;

public class AppUtil {

    public static void signOutUserOtherDevice(Activity activity, SQLiteHelper db, Class<?> targetClass, @Nullable String accountId, boolean showSnackBar) {
        new AlertDialog.Builder(activity)
                .setTitle("Informasi")
                .setMessage("Akun anda digunakan oleh perangkat lain, anda akan logout otomatis.")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        AppUtil.signOutFull(activity, db, showSnackBar, accountId);
                        ServiceUtil.stopSevice(activity, targetClass);
                        NotifUtil.clearNotificationAll(activity);
                    }
                })
                .show();
    }

    public static void signOutFull(Activity activity, SQLiteHelper db, boolean showSnackBar, @Nullable String accountId) {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.signOut();
        if (showSnackBar) {
            Snackbar.make(activity.findViewById(android.R.id.content), "Success Sign Out", Snackbar.LENGTH_SHORT).show();
        }
        activity.startActivity(new Intent(activity, LoginActivity.class));
        activity.finishAffinity();
        //SessionUtil.setBoolPreferences(ISeasonConfig.KEY_IS_HAS_LOGIN, false);
        //SessionUtil.deleteKeyPreferences(ISeasonConfig.KEY_ACCOUNT_ID);
        //SessionUtil.deleteKeyPreferences(ISeasonConfig.KEY_EVENT_ID);
        SessionUtil.removeAllSharedPreferences();

        if (!TextUtils.isEmpty(accountId)) {
            db.setUserStillIn(accountId, true);
        }

    }

    public static void outEventFull(Activity activity, Class<?> targetClass, int idNotif) {
        NotificationManager notificationManager = (NotificationManager) activity.getSystemService(Context.NOTIFICATION_SERVICE);
        Intent intent9 = new Intent(activity, targetClass);
        activity.startActivity(intent9);
        activity.finish();
        SessionUtil.deleteKeyPreferences(ISeasonConfig.KEY_EVENT_ID);

        if (isNotificationVisible(activity, idNotif)) {
            //notificationManager.cancel(idNotif);
            notificationManager.cancelAll();
        }

        activity.stopService(new Intent(activity, MyService.class));
    }

    private static boolean isNotificationVisible(Context context, int idNotif) {
        Intent notificationIntent = new Intent(context, context.getClass());
        PendingIntent test = PendingIntent.getActivity(context, idNotif, notificationIntent, PendingIntent.FLAG_NO_CREATE);
        return test != null;
    }

    public static String validationStringImageIcon(Activity context, String s1Url, String s2Path, boolean isPreferUrl) {
        if (isPreferUrl) {
            return NetworkUtil.isConnected(context) ? s1Url : !validationStringLocalExist(s2Path) ? s1Url : s2Path;
        } else {
            return !validationStringLocalExist(s2Path) ? s1Url : s2Path;
        }
    }

    public static Boolean validationStringLocalExist(String s2Path) {
        if (!TextUtils.isEmpty(s2Path)) {
            if (PictureUtil.isFileExists(s2Path)) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
}
