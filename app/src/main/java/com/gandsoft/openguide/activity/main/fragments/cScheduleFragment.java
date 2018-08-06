package com.gandsoft.openguide.activity.main.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gandsoft.openguide.R;
import com.gandsoft.openguide.activity.main.adapter.TaskRecViewAdapter;
import com.gandsoft.openguide.activity.main.adapter.TaskRecViewPojo;

import java.util.ArrayList;


public class cScheduleFragment extends Fragment {
    RecyclerView recyclerView;
    TaskRecViewAdapter adapter;
    private ArrayList<TaskRecViewPojo> listContentArr = new ArrayList<>();
    private NestedScrollView scroller;
    static final int NUM_ITEMS = 5;
    private int a = 0;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_c_schedule, container, false);

        initComponent(view);
        initContent();
        initListener();

        scroller.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                Log.i("SCROOLLL X = ",String.valueOf(scrollX));
                Log.i("SCROOLLL Y = ",String.valueOf(scrollY));
                Log.i("SCROOLLL old X = ",String.valueOf(oldScrollX));
                Log.i("SCROOLLL old Y = ",String.valueOf(oldScrollY));
                if (scrollY == 330) {
                    ViewPager pager = (ViewPager) view.findViewById(R.id.pagerinfragmini);
                    a = 1;
                    pager.setAdapter(buildAdapter(a));

                    view.findViewById(R.id.pagerinfrag).setVisibility(View.GONE);
                    view.findViewById(R.id.pagerinfragmini).setVisibility(View.VISIBLE);
                } else{
                    ViewPager pager = (ViewPager) view.findViewById(R.id.pagerinfrag);
                    a = 0;
                    pager.setAdapter(buildAdapter(a));
                    view.findViewById(R.id.pagerinfrag).setVisibility(View.VISIBLE);
                    view.findViewById(R.id.pagerinfragmini).setVisibility(View.GONE);
                }
            }
        });


        //Method call for populating the view
        return view;
    }

    private void initComponent(View view) {
        scroller = (NestedScrollView) view.findViewById(R.id.infoNSVInfo);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycleView);

    }

    private void initContent() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new TaskRecViewAdapter(getActivity());
        populateRecyclerViewValues();
    }

    private void initListener() {

    }

    private void populateRecyclerViewValues() {
        /** This is where we pass the data to the adpater using POJO class.
         *  The for loop here is optional. I've just populated same data for 50 times.
         *  You can use a JSON object request to gather the required values and populate in the
         *  RecyclerView.
         * */
        for (int iter = 0; iter <= 50; iter++) {
            //Creating POJO class object
            TaskRecViewPojo pojoObject = new TaskRecViewPojo();
            //Values are binded using set method of the POJO class
            pojoObject.setContent("Content, number: " + iter);
            pojoObject.setTime("Time");
            //After setting the values, we add all the Objects to the array
            //Hence, listConentArr is a collection of Array of POJO objects
            listContentArr.add(pojoObject);
        }
        //We set the array to the adapter
        adapter.setListContent(listContentArr);
        //We in turn set the adapter to the RecyclerView
        recyclerView.setAdapter(adapter);
    }

    private PagerAdapter buildAdapter(int a) {
        return (new SampleAdapter(getActivity(), getChildFragmentManager(), a));
    }
}
