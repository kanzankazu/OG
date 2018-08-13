package com.gandsoft.openguide.activity.main;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.gandsoft.openguide.API.API;
import com.gandsoft.openguide.API.APIrequest.Event.EventDataRequestModel;
import com.gandsoft.openguide.API.APIresponse.Event.EventAbout;
import com.gandsoft.openguide.API.APIresponse.Event.EventDataContact;
import com.gandsoft.openguide.API.APIresponse.Event.EventDataContactList;
import com.gandsoft.openguide.API.APIresponse.Event.EventDataResponseModel;
import com.gandsoft.openguide.API.APIresponse.Event.EventImportanInfo;
import com.gandsoft.openguide.API.APIresponse.Event.EventPlaceList;
import com.gandsoft.openguide.API.APIresponse.Event.EventScheduleList;
import com.gandsoft.openguide.API.APIresponse.Event.EventTheEvent;
import com.gandsoft.openguide.ISeasonConfig;
import com.gandsoft.openguide.R;
import com.gandsoft.openguide.database.SQLiteHelper;
import com.gandsoft.openguide.support.NetworkUtil;
import com.gandsoft.openguide.support.SessionUtil;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BaseHomeActivity extends AppCompatActivity {
    SQLiteHelper db = new SQLiteHelper(this);

    static final int NUM_ITEMS = 5;

    ViewPager mPager;
    SlidePagerAdapter mPagerAdapter;
    private TabLayout tabLayout;
    private ActionBar ab;
    private String[] titleTab = new String[]{"Home", "Wallet", "Schedule", "About the Event", "Important Information"};
    private int[] iconTab = new int[]{R.drawable.ic_tab_home, R.drawable.ic_tab_wallet, R.drawable.ic_tab_schedule, R.drawable.ic_tab_about, R.drawable.ic_tab_info};
    private Toolbar toolbar;
    private String accountId, eventId;
    private int version_data_event;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_home);

        accountId = SessionUtil.getStringPreferences(ISeasonConfig.KEY_ACCOUNT_ID, null);
        eventId = SessionUtil.getStringPreferences(ISeasonConfig.KEY_EVENT_ID, null);

        initComponent();
        initContent();
        initListener();
    }

    private void initComponent() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        tabLayout = (TabLayout) findViewById(R.id.tlFiltSortHot);
        mPager = (ViewPager) findViewById(R.id.pager);
    }

    private void initContent() {

        getEventDataValid();

        initVersionDataEvent();

        initActionBar();
        initTablayoutViewpager();
    }

    private void initListener() {
        mPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mPager.setCurrentItem(tab.getPosition());
                ab.setTitle(titleTab[tab.getPosition()]);
                int tabIconColor = ContextCompat.getColor(BaseHomeActivity.this, R.color.colorPrimaryDark);
                tab.getIcon().setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                int tabIconColor = ContextCompat.getColor(BaseHomeActivity.this, R.color.grey);
                tab.getIcon().setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }

    private void getEventDataValid() {
        if (NetworkUtil.isConnected(BaseHomeActivity.this)) {
            getEventDataDo();
        } else {
            if (db.checkDataTable(SQLiteHelper.TableListEvent, SQLiteHelper.KEY_ListEvent_eventId, eventId)) {
                Snackbar.make(findViewById(android.R.id.content), "Memunculkan Data Offline", Snackbar.LENGTH_LONG).show();
            } else {
                Snackbar.make(findViewById(android.R.id.content), "Data Bermasalah", Snackbar.LENGTH_LONG).show();
            }
        }
    }

    private void getEventDataDo() {
        EventDataRequestModel requestModel = new EventDataRequestModel();
        requestModel.setDbver("3");
        requestModel.setId_event(eventId);
        requestModel.setPass("");
        requestModel.setPhonenumber(accountId);
        requestModel.setVersion_data(String.valueOf(version_data_event));

        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setTitle("Get data from server");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        // show it
        progressDialog.show();

        API.doEventDataRet(requestModel).enqueue(new Callback<List<EventDataResponseModel>>() {
            @Override
            public void onResponse(Call<List<EventDataResponseModel>> call, Response<List<EventDataResponseModel>> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    for (int i = 0; i < response.body().size(); i++) {
                        EventDataResponseModel model = response.body().get(i);
                        db.setOneKey(SQLiteHelper.TableGlobalData, SQLiteHelper.KEY_GlobalData_version_data_event, model.getEvent_id());

                        /*the event*/
                        for (int i1 = 0; i1 < model.getThe_event().size(); i1++) {
                            EventTheEvent theEvent = model.getThe_event().get(i1);

                        }


                        for (int i2 = 0; i2 < model.getPlace_list().size(); i2++) {
                            Map<String, List<EventPlaceList>> stringListMap = model.getPlace_list().get(i2);
                            /*Log.d("Lihat", "onResponse BaseHomeActivity stringListMap : " + stringListMap);
                            Iterator<String> iterator = stringListMap.keySet().iterator();
                            Log.d("Lihat", "onResponse BaseHomeActivity iterator : " + iterator);
                            while (iterator.hasNext()) {
                                String key = (String) iterator.next();
                                Log.d("Lihat", "onResponse BaseHomeActivity key : " + key);
                                List<EventPlaceList> value = (List<EventPlaceList>) stringListMap.get(key);
                                Log.d("Lihat", "onResponse BaseHomeActivity value.size : " + value.size());
                                Log.d("Lihat", "onResponse BaseHomeActivity value : " + value);
                                Log.d("Lihat", "onResponse BaseHomeActivity value.get(0) : " + value.get(0));
                                for (int i22 = 0; i22 < value.size(); i22++) {
                                    EventPlaceList placeList = value.get(i22);
                                    Log.d("Lihat", "onResponse BaseHomeActivity : " + placeList);
                                    List<EventPlaceListData> q = placeList.getPlaceListDataList();
                                    Log.d("Lihat", "onResponse BaseHomeActivity : " + q);
                                    *//*for (int i23 = 0; i23 < q.size(); i23++) {
                                        EventPlaceListData eventPlaceListData = placeList.getPlaceListDataList().get(i23);
                                        Log.d("Lihat", "onResponse BaseHomeActivity : " + eventPlaceListData.getTitle());
                                    }*//*
                                }
                            }*/
                            for (Map.Entry<String, List<EventPlaceList>> entry : stringListMap.entrySet()) {
                                String key = entry.getKey();
                                List<EventPlaceList> values = entry.getValue();
                                Log.d("Lihat", "onResponse BaseHomeActivity : " + key);
                                Log.d("Lihat", "onResponse BaseHomeActivity values.size : " + values.size());
                                Log.d("Lihat", "onResponse BaseHomeActivity values.get : " + values.get(0));
                                Log.d("Lihat", "onResponse BaseHomeActivity values.get. : " + values.get(0).getPlaceListDataList());
                                Log.d("Lihat", "onResponse BaseHomeActivity values : " + values);
                            }
                        }


                        for (int i3 = 0; i3 < model.getImportan_info().size(); i3++) {
                            EventImportanInfo importanInfo = model.getImportan_info().get(i3);
                        }

                        for (int i4 = 0; i4 < model.getData_contact().size(); i4++) {
                            EventDataContact dataContact = model.getData_contact().get(i4);

                            for (int i41 = 0; i41 < dataContact.getContact_list().size(); i41++) {
                                EventDataContactList dataContactList = dataContact.getContact_list().get(i41);

                            }
                        }

                        for (int i5 = 0; i5 < model.getAbout().size(); i5++) {
                            EventAbout eventAbout = model.getAbout().get(i5);
                        }

                        for (int i6 = 0; i6 < model.getSchedule_list().size(); i6++) {
                            EventScheduleList scheduleList = model.getSchedule_list().get(i6);
                        }


                    }
                } else {
                    Log.d("Lihat", "onResponse BaseHomeActivity : " + response.message());
                }
            }

            @Override
            public void onFailure(Call<List<EventDataResponseModel>> call, Throwable t) {
                progressDialog.dismiss();
                Log.e("Lihat", "onFailure BaseHomeActivity : " + t.getMessage());
            }
        });
    }

    private void initVersionDataEvent() {
        if (db.checkDataTableNull(SQLiteHelper.TableGlobalData, SQLiteHelper.KEY_GlobalData_version_data_event)) {
            version_data_event = 0;
        } else {
            version_data_event = db.getVersionDataIdEvent(eventId);
        }
    }

    private void showFirstDialogEvent() {
        new AlertDialog.Builder(BaseHomeActivity.this)
                .setTitle("INFO")
                .setMessage("Test")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //code here
                    }
                })
                .show();
    }

    private void initActionBar() {
        setSupportActionBar(toolbar);
        ab = getSupportActionBar();
        ab.setTitle(titleTab[0]);
    }

    private void initTablayoutViewpager() {
        int tabIconColorSelect = ContextCompat.getColor(this, R.color.colorPrimary);
        int tabIconColorUnSelect = ContextCompat.getColor(this, R.color.grey);
        for (int i = 0; i < titleTab.length; i++) {
            tabLayout.addTab(tabLayout.newTab().setIcon(iconTab[i]));
            if (i == 0) {
                tabLayout.getTabAt(i).getIcon().setColorFilter(tabIconColorSelect, PorterDuff.Mode.SRC_IN);
            } else {
                tabLayout.getTabAt(i).getIcon().setColorFilter(tabIconColorUnSelect, PorterDuff.Mode.SRC_IN);
            }
        }
        /*tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_tab_home));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_tab_wallet));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_tab_schedule));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_tab_about));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_tab_info));
        tabLayout.getTabAt(0).getIcon().setColorFilter(tabIconColorSelect, PorterDuff.Mode.SRC_IN);
        tabLayout.getTabAt(1).getIcon().setColorFilter(tabIconColorUnSelect, PorterDuff.Mode.SRC_IN);
        tabLayout.getTabAt(2).getIcon().setColorFilter(tabIconColorUnSelect, PorterDuff.Mode.SRC_IN);
        tabLayout.getTabAt(3).getIcon().setColorFilter(tabIconColorUnSelect, PorterDuff.Mode.SRC_IN);
        tabLayout.getTabAt(4).getIcon().setColorFilter(tabIconColorUnSelect, PorterDuff.Mode.SRC_IN);*/
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        mPagerAdapter = new SlidePagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        mPager.setAdapter(mPagerAdapter);
        mPager.setCurrentItem(0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    public static Intent getActIntent(Activity activity) {
        return new Intent(activity, BaseHomeActivity.class);
    }
}
