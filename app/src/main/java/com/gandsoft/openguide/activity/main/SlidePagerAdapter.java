package com.gandsoft.openguide.activity.main;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.gandsoft.openguide.activity.main.fragments.bWalletFragment;
import com.gandsoft.openguide.activity.main.fragments.cScheduleFragment;
import com.gandsoft.openguide.activity.main.fragments.dAboutFragment;
import com.gandsoft.openguide.activity.main.fragments.eInfoFragment;

import static com.gandsoft.openguide.activity.main.BaseHomeActivity.NUM_ITEMS;

public class SlidePagerAdapter extends FragmentStatePagerAdapter {

    private int mNumOfTabs;

    public SlidePagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new RootFragment();
        } else if (position == 1) {
            return new bWalletFragment();
        } else if (position == 2) {
            return new cScheduleFragment();
        } else if (position == 3) {
            return new dAboutFragment();
        } else if (position == 4) {
            return new eInfoFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return NUM_ITEMS;
    }
}
