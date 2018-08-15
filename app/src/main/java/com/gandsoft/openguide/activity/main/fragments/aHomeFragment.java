package com.gandsoft.openguide.activity.main.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gandsoft.openguide.IConfig;
import com.gandsoft.openguide.ISeasonConfig;
import com.gandsoft.openguide.R;
import com.gandsoft.openguide.activity.main.adapter.PostRecViewAdapter;
import com.gandsoft.openguide.activity.main.adapter.PostRecViewPojo;
import com.gandsoft.openguide.activity.main.adapter.PostRecViewPojoDummy;
import com.gandsoft.openguide.database.SQLiteHelper;
import com.gandsoft.openguide.support.SessionUtil;

import java.util.ArrayList;
import java.util.List;

public class aHomeFragment extends Fragment {
    SQLiteHelper db = new SQLiteHelper(getActivity());

    private static final String TAG = "Lihat";
    private View view;
    private LinearLayout llLoadModefvbi;
    private RecyclerView recyclerView;
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
    private List<PostRecViewPojo> menuUi = new ArrayList<>();
    private int page = 0;
    private String accountId, eventId;
    private int version_data_event;

    public aHomeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_a_home, container, false);

        accountId = SessionUtil.getStringPreferences(ISeasonConfig.KEY_ACCOUNT_ID, null);
        eventId = SessionUtil.getStringPreferences(ISeasonConfig.KEY_EVENT_ID, null);

        initComponent();
        initContent(container);
        initListener(view);

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
        recyclerView = (RecyclerView) view.findViewById(R.id.homeRVEvent);
        llLoadModefvbi = (LinearLayout) view.findViewById(R.id.llLoadMode);
    }

    private void initContent(ViewGroup container) {

        initVersionDataEvent();

        adapter = new PostRecViewAdapter(menuUi);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setAdapter(adapter);
        adapter.setData(PostRecViewPojoDummy.generyData(page));
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //adapter.notifyDataSetChanged();
        //populateRecyclerViewValues();
    }

    private void initListener(View view) {
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
                adapter.replaceData(PostRecViewPojoDummy.generyData(page));
                //adapter.notifyDataSetChanged();
            }
        });
        homeNSVHomefvbi.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY > oldScrollY) {
                    //Log.i(TAG, "Scroll DOWN");
                }
                if (scrollY < oldScrollY) {
                    //Log.i(TAG, "Scroll UP");
                }
                if (scrollY == 0) {
                    //Log.i(TAG, "TOP SCROLL");
                }
                if (scrollY == (v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight())) {
                    llLoadModefvbi.setVisibility(View.VISIBLE);
                    new Handler().postDelayed(new Runnable() {
                        public void run() {
                            //code here
                            homeNSVHomefvbi.fullScroll(View.FOCUS_DOWN);
                        }
                    }, 500);
                    Log.i(TAG, "BOTTOM SCROLL");
                    new Handler().postDelayed(new Runnable() {
                        public void run() {
                            //code here
                            if (PostRecViewPojoDummy.hasMore(page)) {
                                adapter.addDatas(PostRecViewPojoDummy.generyData(++page));
                                llLoadModefvbi.setVisibility(View.GONE);
                            } else {
                                Snackbar.make(getActivity().findViewById(android.R.id.content), "tidak ada data", Snackbar.LENGTH_LONG).show();
                                llLoadModefvbi.setVisibility(View.GONE);
                            }
                        }
                    }, 2000);
                }
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
    }

    private void initVersionDataEvent() {
        if (db.isDataTableValueNull(SQLiteHelper.TableListEvent, SQLiteHelper.KEY_ListEvent_version_data, eventId)) {
            version_data_event = IConfig.DB_Version;
        } else {
            version_data_event = db.getVersionDataIdEvent(eventId);
        }
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
