package com.gandsoft.openguide.activity.main;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.gandsoft.openguides2.fragments.bWalletFragment;
import com.gandsoft.openguides2.fragments.cScheduleFragment;
import com.gandsoft.openguides2.fragments.dAboutFragment;
import com.gandsoft.openguides2.fragments.eInfoFragment;

public class BaseHomeActivity extends AppCompatActivity {
	static final int NUM_ITEMS = 5;

	ViewPager mPager;
	SlidePagerAdapter mPagerAdapter;
    Button btn1,btn2,btn3,btn4,btn5;

    @Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_base_home);

        mPager = (ViewPager) findViewById(R.id.pager);
		mPagerAdapter = new SlidePagerAdapter(getSupportFragmentManager());
		mPager.setAdapter(mPagerAdapter);
    }

	public class SlidePagerAdapter extends FragmentPagerAdapter {
		SlidePagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
		    if (position == 0){
                return new RootFragment();
            }
			else if(position==1){
                return new bWalletFragment();
            }
			else if(position==2){
                return new cScheduleFragment();
            }
			else if(position==3){
                return new dAboutFragment();
            }
            else if(position==4){
                return new eInfoFragment();
            }
            return null;
		}

		@Override
		public int getCount() {
			return NUM_ITEMS;
		}
	}
}
