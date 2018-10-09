package com.gandsoft.openguide.activity.main.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.gandsoft.openguide.API.APIresponse.Event.EventScheduleListDateDataList;
import com.gandsoft.openguide.ISeasonConfig;
import com.gandsoft.openguide.R;
import com.gandsoft.openguide.activity.main.adapter.SchedulePagerAdapter;
import com.gandsoft.openguide.activity.main.adapter.ScheduleRecycleviewAdapter;
import com.gandsoft.openguide.database.SQLiteHelper;
import com.gandsoft.openguide.support.DateTimeUtil;
import com.gandsoft.openguide.support.ListArrayUtil;
import com.gandsoft.openguide.support.SessionUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class cScheduleFragment extends Fragment {
    private static final int REQ_CODE_QNA = 123;
    private SQLiteHelper db;
    private ArrayList<String> scheduleDates;
    private ArrayList<EventScheduleListDateDataList> scheduleListPerDate;
    private ImageView[] ivIndicatorPromo;

    private RecyclerView recyclerView;
    private ScheduleRecycleviewAdapter adapter;
    private NestedScrollView infoNSVInfofvbi;
    private ViewPager pagerBig;
    private ViewPager pagerSmall;
    private LinearLayout llScheduleDotsfvbi;

    private SchedulePagerAdapter pagerAdapterBig;
    private SchedulePagerAdapter pagerAdapterSmall;
    private String accountId, eventId;
    private String group_code;
    private int iPagerCount;
    private boolean isBigVP;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_c_schedule, container, false);

        accountId = SessionUtil.getStringPreferences(ISeasonConfig.KEY_ACCOUNT_ID, null);
        eventId = SessionUtil.getStringPreferences(ISeasonConfig.KEY_EVENT_ID, null);

        db = new SQLiteHelper(getActivity());

        initComponent(view);
        initContent();
        initListener(view);

        //Method call for populating the view
        return view;
    }

    private void initComponent(View view) {
        infoNSVInfofvbi = (NestedScrollView) view.findViewById(R.id.infoNSVInfo);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycleView);
        pagerBig = (ViewPager) view.findViewById(R.id.pagerinfrag);
        pagerSmall = (ViewPager) view.findViewById(R.id.pagerinfragmini);
        llScheduleDotsfvbi = (LinearLayout) view.findViewById(R.id.llScheduleDots);
    }

    private void initContent() {

        group_code = "groupcode_" + db.getOneListEvent(eventId).getGroup_code();
        scheduleDates = db.getScheduleListDate(group_code);

        pagerAdapterBig = new SchedulePagerAdapter(getActivity(), 0, scheduleDates);
        pagerBig.setAdapter(pagerAdapterBig);
        pagerBig.setOffscreenPageLimit(0);
        pagerAdapterSmall = new SchedulePagerAdapter(getActivity(), 1, scheduleDates);
        pagerSmall.setAdapter(pagerAdapterSmall);
        pagerSmall.setOffscreenPageLimit(0);

        addBottomDots();

        scheduleListPerDate = db.getScheduleListPerDate(group_code, scheduleDates.get(pagerBig.getCurrentItem()));
        adapter = new ScheduleRecycleviewAdapter(getActivity(), scheduleListPerDate, new ScheduleRecycleviewAdapter.ScheduleListener() {
            @Override
            public void onClickQNA(String link) {
                Intent intent = new Intent(getActivity(), cScheduleQNAActivity.class);
                intent.putExtra(ISeasonConfig.INTENT_PARAM, link);
                startActivityForResult(intent, REQ_CODE_QNA);
                //finish();
            }
        }, scheduleDates, group_code);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        checkDate(scheduleDates);

        adapter.notifyDataSetChanged();

    }

    private void checkDate(ArrayList<String> scheduleDates) {
        //code here
        String currentDate = DateTimeUtil.dateToString(DateTimeUtil.currentDate(), new SimpleDateFormat("EEEE dd MMMM yyyy"));
        Log.d("Lihat", "checkDate cScheduleFragment : " + currentDate);
        Log.d("Lihat", "checkDate cScheduleFragment : " + scheduleDates);

        if (ListArrayUtil.isListContainString(scheduleDates, currentDate)) {
            Log.d("Lihat", "checkDate cScheduleFragment : " + ListArrayUtil.isListContainString(scheduleDates, currentDate));
            int posCurrDate = ListArrayUtil.getPosStringInList(scheduleDates, currentDate);
            ArrayList<String> scheduleTimes = db.getScheduleListTime(group_code, scheduleDates.get(posCurrDate));
            Log.d("Lihat", "checkDate cScheduleFragment : " + scheduleTimes);
            //code here
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    //code here
                    pagerBig.setCurrentItem(posCurrDate);
                    //code here
                    for (int i = 0; i < scheduleTimes.size(); i++) {
                        String time = scheduleTimes.get(i);
                        if (time.contains("-")) {
                            String[] split = time.split("-");
                            if (DateTimeUtil.isBetween2Time(split[0], split[1])) {
                                int finalI = i;
                                new Handler().postDelayed(new Runnable() {
                                    public void run() {
                                        //code here
                                        int measuredHeight = pagerBig.getMeasuredHeight();
                                        int measuredHeight1 = llScheduleDotsfvbi.getMeasuredHeight();
                                        int measuredHeight2 = 0;
                                        for (int i2 = 0; i2 < finalI - 1; i2++) {
                                            measuredHeight2 = measuredHeight2 + recyclerView.getChildAt(i2).getMeasuredHeight();
                                        }
                                        int heightTotal = measuredHeight + measuredHeight1 + measuredHeight2;
                                        infoNSVInfofvbi.smoothScrollTo(0, heightTotal);

                                        Log.d("Lihat", "checkDate cScheduleFragment : " + finalI);
                                    }
                                }, 1000);
                            }
                        }
                    }
                }
            }, 1000);

        }

    }

    private void initListener(View view) {
        infoNSVInfofvbi.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                Log.i("SCROOLLL X = ", String.valueOf(scrollX));
                Log.i("SCROOLLL Y = ", String.valueOf(scrollY));
                Log.i("SCROOLLL old X = ", String.valueOf(oldScrollX));
                Log.i("SCROOLLL old Y = ", String.valueOf(oldScrollY));
                if (scrollY <= pagerBig.getMeasuredHeight()) {
                    showPager(0);
                } else {
                    showPager(1);
                }

                /*Log.d("Lihat", "onScrollChange cScheduleFragment : " + pagerBig.getMeasuredHeight());
                Log.d("Lihat", "onScrollChange cScheduleFragment : " + llScheduleDotsfvbi.getMeasuredHeight());
                Log.d("Lihat", "onScrollChange cScheduleFragment : " + recyclerView.getChildCount());
                Log.d("Lihat", "onScrollChange cScheduleFragment : " + v.getChildCount());
                Log.d("Lihat", "onScrollChange cScheduleFragment : " + v.getChildAt(0));
                Log.d("Lihat", "onScrollChange cScheduleFragment : " + v.getChildAt(1));
                Log.d("Lihat", "onScrollChange cScheduleFragment : " + v.getChildAt(0).getId());
                Log.d("Lihat", "onScrollChange cScheduleFragment : " + v.getChildAt(0).getMeasuredHeight());*/

                /*if (v.getChildAt(v.getChildCount() - 1) != null){
                    if ((scrollY >= (v.getChildAt(v.getChildCount() - 1).getMeasuredHeight() - v.getMeasuredHeight())) && scrollY > oldScrollY) {
                        //code to fetch more data for endless scrolling
                        Log.d("Lihat", "onScrollChange cScheduleFragment : " + );
                    }
                }*/
            }
        });
        pagerBig.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                pagerSmall.setCurrentItem(position);
                adapter.setData(db.getScheduleListPerDate(group_code, scheduleDates.get(position)));
                for (int i = 0; i < iPagerCount; i++) {
                    ivIndicatorPromo[i].setImageDrawable(getResources().getDrawable(R.drawable.nonselected_item));
                }
                ivIndicatorPromo[position].setImageDrawable(getResources().getDrawable(R.drawable.selected_item));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        pagerSmall.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                pagerBig.setCurrentItem(position);
                /*adapter.setData(db.getScheduleListPerDate(eventId, scheduleDates.get(position)));
                for (int i = 0; i < iPagerCount; i++) {
                    ivIndicatorPromo[i].setImageDrawable(getResources().getDrawable(R.drawable.nonselected_item));
                }
                ivIndicatorPromo[position].setImageDrawable(getResources().getDrawable(R.drawable.selected_item));*/
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void addBottomDots() {
        iPagerCount = scheduleDates.size();
        ivIndicatorPromo = new ImageView[iPagerCount];
        for (int i = 0; i < iPagerCount; i++) {
            ivIndicatorPromo[i] = new ImageView(getActivity());
            ivIndicatorPromo[i].setImageDrawable(getResources().getDrawable(R.drawable.nonselected_item));
            //ivIndicatorPromo[i].setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(10, 0, 10, 0);
            llScheduleDotsfvbi.addView(ivIndicatorPromo[i], params);
            ivIndicatorPromo[0].setImageDrawable(getResources().getDrawable(R.drawable.selected_item));
            //ivIndicatorPromo[0].setBackgroundColor(getResources().getColor(R.color.colorAccent));
        }

        /*dots = new TextView[headervalues.length];

        int[] colorsActive = getResources().getIntArray(R.array.array_dot_active);
        int[] colorsInactive = getResources().getIntArray(R.array.array_dot_inactive);

        llScheduleDotsfvbi.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(getActivity());
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            //dots[i].setTextColor(colorsInactive[currentPage]);
            dots[i].setTextColor(getResources().getColor(R.color.grey));
            llScheduleDotsfvbi.addView(dots[i]);
        }

        if (dots.length > 0)
            //dots[currentPage].setTextColor(colorsActive[currentPage]);
            dots[currentPage].setTextColor(getResources().getColor(R.color.colorPrimaryDark));*/
    }

    private void showPager(int pager) {
        if (pager == 0) {
            pagerBig.setVisibility(View.VISIBLE);
            pagerSmall.setVisibility(View.GONE);
        } else if (pager == 1) {
            pagerBig.setVisibility(View.VISIBLE);
            pagerSmall.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_CODE_QNA) {
            if (resultCode == Activity.RESULT_OK) {

            } else {

            }
        } else {

        }
    }
}
