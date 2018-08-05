package com.gandsoft.openguide.activity.main.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gandsoft.openguide.R;
import com.gandsoft.openguide.activity.main.adapter.TaskRecViewAdapter;
import com.gandsoft.openguide.activity.main.adapter.TaskRecViewPojo;

import java.util.ArrayList;

public class cScheduleXFragment extends Fragment {
    public cScheduleXFragment(){ }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cx_fact, container, false);

        return view;
    }

}