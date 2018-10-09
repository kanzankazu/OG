package com.gandsoft.openguide.support;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.Snackbar;

import com.gandsoft.openguide.ISeasonConfig;
import com.gandsoft.openguide.activity.LoginActivity;
import com.gandsoft.openguide.database.SQLiteHelper;
import com.google.firebase.auth.FirebaseAuth;

public class AppUtil {

    public static void signOutFull(Activity activity, SQLiteHelper db, boolean isUIRunning) {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.signOut();
        if (isUIRunning){
            Snackbar.make(activity.findViewById(android.R.id.content), "Success Sign Out", Snackbar.LENGTH_SHORT).show();
        }
        activity.startActivity(new Intent(activity, LoginActivity.class));
        activity.finishAffinity();
        SessionUtil.setBoolPreferences(ISeasonConfig.KEY_IS_HAS_LOGIN, false);
        SessionUtil.deleteKeyPreferences(ISeasonConfig.KEY_ACCOUNT_ID);
        db.deleteAllDataUser();
        db.deleteAllDataListEvent();
        db.deleteAllDataWallet();
    }
}
