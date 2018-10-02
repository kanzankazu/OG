package com.gandsoft.openguide.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;

import com.gandsoft.openguide.ISeasonConfig;
import com.gandsoft.openguide.R;
import com.gandsoft.openguide.activity.main.BaseHomeActivity;
import com.gandsoft.openguide.support.SessionUtil;

import io.supercharge.shimmerlayout.ShimmerLayout;

public class SplashscreenActivity extends LocalBaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);
        ShimmerLayout shimmerText = (ShimmerLayout) findViewById(R.id.shimmer_logo);
        shimmerText.startShimmerAnimation();

        if (SessionUtil.checkIfExist(ISeasonConfig.KEY_EVENT_ID)) {
            moveToBaseHome();
        } else {
            initComponent();
            initContent();
            initListener();
        }

    }

    private void moveToBaseHome() {
        Intent intent = new Intent(SplashscreenActivity.this, BaseHomeActivity.class);
        startActivity(intent);
        finish();
    }

    private void initComponent() {

    }

    private void initContent() {

        notifControl();

        new Handler().postDelayed(new Runnable() {
            public void run() {
                //code here
                moveToIntro();
                SessionUtil.setBoolPreferences(ISeasonConfig.KEY_IS_SPLASH, true);
            }
        }, 2000);
    }

    private void initListener() {

    }

    private void moveToIntro() {
        startActivity(IntroActivity.getActIntent(SplashscreenActivity.this));
        finish();
    }

    private void notifControl() {
        if (getIntent().getExtras() != null) {
            int number = 0;
            for (String key : getIntent().getExtras().keySet()) {
                Log.d("Lihat", "notifControl SplashscreenActivity key " + number + ": " + key);
                String value = getIntent().getExtras().getString(key);
                Log.d("Lihat", "notifControl SplashscreenActivity value " + number + ": " + value);

                /*if (key.equalsIgnoreCase(ISeasonConfig.KEY_IS_UPDATE_NOTIF)) {
                    if (value != null) {
                        if (value.equalsIgnoreCase("true")) {
                            SessionManager.setBoolPreferences(KEY_IS_UPDATE, true);
                        }
                    }
                }
                if (key.equalsIgnoreCase(ISeasonConfig.KEY_IS_UPDATE_INFO_NOTIF)) {
                    if (ValidUtil.isEmptyField(value)) {
                        SessionManager.setStringPreferences(KEY_IS_UPDATE_INFO, value);
                    }
                }*/

                number++;
            }
        }
    }
}
