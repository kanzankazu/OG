package com.gandsoft.openguide.activity.main.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gandsoft.openguides2.R;

public class cScheduleFragment extends Fragment {
    static final int NUM_ITEMS = 5;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater
                .inflate(R.layout.fragment_c_schedule, container, false);
        ViewPager pager=(ViewPager)view.findViewById(R.id.pagerinfrag);

        pager.setAdapter(buildAdapter());

        return view;
    }
    private PagerAdapter buildAdapter() {
        return(new SampleAdapter(getActivity(), getChildFragmentManager()));
    }
}
