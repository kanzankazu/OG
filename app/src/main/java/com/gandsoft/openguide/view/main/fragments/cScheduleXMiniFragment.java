package com.gandsoft.openguide.view.main.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gandsoft.openguide.R;

public class cScheduleXMiniFragment extends Fragment {
    public cScheduleXMiniFragment(){ }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cx_fact_mini, container, false);
        return view;
    }
}