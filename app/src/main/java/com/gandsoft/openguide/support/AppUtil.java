package com.gandsoft.openguide.support;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;

import com.gandsoft.openguide.ISeasonConfig;
import com.gandsoft.openguide.activity.LoginActivity;
import com.gandsoft.openguide.activity.services.MyService;
import com.gandsoft.openguide.database.SQLiteHelper;
import com.google.firebase.auth.FirebaseAuth;

public class AppUtil {

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
}
