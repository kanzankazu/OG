package com.gandsoft.openguide.activity.main.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.gandsoft.openguide.R;
import com.gandsoft.openguide.activity.main.adapter.PostRecViewAdapter;
import com.gandsoft.openguide.activity.main.adapter.PostRecViewPojoDummy;
import com.gandsoft.openguide.presenter.widget.LoadMoreRecyclerView;

public class aHomeFragment extends Fragment {
    private static final String TAG = "Lihat";
    private View view;
    private LoadMoreRecyclerView recyclerView;
    private SwipeRefreshLayout homeSRLHomefvbi;
    private ImageView homeIVOpenCamerafvbi;
    private NestedScrollView homeNSVHomefvbi;
    private ImageView homeIVEventfvbi;
    private ImageView homeIVShareSomethingfvbi;
    private TextView homeTVTitleEventfvbi;
    private TextView homeTVDescEventfvbi;
    private Button homeBTapCheckInfvbi;
    private EditText homeETWritePostCreatefvbi;
    /**/
    private PostRecViewAdapter adapter;
    private int page = 0;

    public aHomeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_a_home, container, false);

        initComponent();
        initContent(container);
        initListener();

        return view;
    }

    private void initComponent() {
        homeSRLHomefvbi = (SwipeRefreshLayout) view.findViewById(R.id.homeSRLHome);
        homeNSVHomefvbi = (NestedScrollView) view.findViewById(R.id.homeNSVHome);
        homeIVEventfvbi = (ImageView) view.findViewById(R.id.homeIVEvent);
        homeIVShareSomethingfvbi = (ImageView) view.findViewById(R.id.homeIVShareSomething);
        homeIVOpenCamerafvbi = (ImageView) view.findViewById(R.id.homeIVOpenCamera);
        homeTVTitleEventfvbi = (TextView) view.findViewById(R.id.homeTVTitleEvent);
        homeTVDescEventfvbi = (TextView) view.findViewById(R.id.homeTVDescEvent);
        homeETWritePostCreatefvbi = (EditText) view.findViewById(R.id.homeETWritePostCreate);
        homeBTapCheckInfvbi = (Button) view.findViewById(R.id.homeBTapCheckIn);
        recyclerView = (LoadMoreRecyclerView) view.findViewById(R.id.homeRVEvent);
    }

    private void initContent(ViewGroup container) {
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(container.getContext()));
        adapter = new PostRecViewAdapter(container.getContext(), PostRecViewPojoDummy.generyData(page));
        recyclerView.setAdapter(adapter);
        recyclerView.setAutoLoadMoreEnable(true);
        adapter.notifyDataSetChanged();
        //populateRecyclerViewValues();
    }

    private void initListener() {
        homeIVOpenCamerafvbi.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                startActivity(intent);
            }
        });
        homeSRLHomefvbi.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                homeSRLHomefvbi.setRefreshing(false);
                page = 0;
                adapter.setData(PostRecViewPojoDummy.generyData(page));
                recyclerView.setAutoLoadMoreEnable(PostRecViewPojoDummy.hasMore(page));
                adapter.notifyDataSetChanged();
            }
        });
        /*recyclerView.setLoadMoreListener(new LoadMoreRecyclerView.LoadMoreListener() {
            @Override
            public void onLoadMore() {
                recyclerView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        homeSRLHomefvbi.setRefreshing(false);
                        adapter.addDatas(PostRecViewPojoDummy.generyData(++page));
                        recyclerView.notifyMoreFinish(PostRecViewPojoDummy.hasMore(page));
                    }
                }, 1000);
            }
        });*/
        homeNSVHomefvbi.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY > oldScrollY) {
                    Log.i(TAG, "Scroll DOWN");
                }
                if (scrollY < oldScrollY) {
                    Log.i(TAG, "Scroll UP");
                }
                if (scrollY == 0) {
                    Log.i(TAG, "TOP SCROLL");
                }
                if (scrollY == (v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight())) {
                    Log.i(TAG, "BOTTOM SCROLL");
                    homeSRLHomefvbi.setRefreshing(false);
                    adapter.addDatas(PostRecViewPojoDummy.generyData(++page));
                    recyclerView.notifyMoreFinish(PostRecViewPojoDummy.hasMore(page));

                }
            }
        });
    }

    /*private void populateRecyclerViewValues() {
        for (int iter = 0; iter <= 50; iter++) {
            PostRecViewPojo pojoObject = new PostRecViewPojo();
            pojoObject.setName("User Name");
            pojoObject.setContent("Content, number: " + iter);
            pojoObject.setTime("Time");
            listContentArr.add(pojoObject);
        }
        //We set the array to the adapter
        adapter.setListContent(listContentArr);
        //We in turn set the adapter to the RecyclerView
        recyclerView.setAdapter(adapter);
    }*/
}
