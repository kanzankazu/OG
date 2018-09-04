package com.gandsoft.openguide.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;

import com.gandsoft.openguide.ISeasonConfig;
import com.gandsoft.openguide.R;
import com.gandsoft.openguide.support.SessionUtil;

import io.supercharge.shimmerlayout.ShimmerLayout;

public class SplashscreenActivity extends LocalBaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);
        ShimmerLayout shimmerText = (ShimmerLayout) findViewById(R.id.shimmer_logo);
        shimmerText.startShimmerAnimation();

            initComponent();
            initContent();
            initListener();

    }

    private void initComponent() {

    }

    private void initContent() {
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
}
