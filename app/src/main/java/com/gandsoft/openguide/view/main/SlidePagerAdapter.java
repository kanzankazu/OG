package com.gandsoft.openguide.view.main;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.gandsoft.openguide.view.main.fragments.aHomeFragment;
import com.gandsoft.openguide.view.main.fragments.bWalletFragment;
import com.gandsoft.openguide.view.main.fragments.cScheduleFragment;
import com.gandsoft.openguide.view.main.fragments.dAboutFragment;
import com.gandsoft.openguide.view.main.fragments.eChatFragment;
import com.gandsoft.openguide.view.main.fragments.fInfoFragment;

import static com.gandsoft.openguide.view.main.BaseHomeActivity.NUM_ITEMS;

public class SlidePagerAdapter extends FragmentStatePagerAdapter {

    private int mNumOfTabs;

    public SlidePagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new aHomeFragment();
        } else if (position == 1) {
            return new bWalletFragment();
        } else if (position == 2) {
            return new cScheduleFragment();
        } else if (position == 3) {
            return new dAboutFragment();
        } else if (position == 4) {
            return new eChatFragment();
        }else if (position == 5) {
            return new fInfoFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
