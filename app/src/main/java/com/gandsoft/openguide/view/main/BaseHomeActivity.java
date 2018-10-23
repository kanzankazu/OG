package com.gandsoft.openguide.view.main;

import android.app.Dialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.gandsoft.openguide.API.API;
import com.gandsoft.openguide.API.APIrequest.VerificationStatusLoginAppUserRequestModel;
import com.gandsoft.openguide.API.APIresponse.Event.EventCommitteeNote;
import com.gandsoft.openguide.API.APIresponse.Event.EventPlaceList;
import com.gandsoft.openguide.API.APIresponse.UserData.UserListEventResponseModel;
import com.gandsoft.openguide.API.APIresponse.VerificationStatusLoginAppUserResponseModel;
import com.gandsoft.openguide.IConfig;
import com.gandsoft.openguide.ISeasonConfig;
import com.gandsoft.openguide.R;
import com.gandsoft.openguide.view.ChangeEventActivity;
import com.gandsoft.openguide.view.infomenu.cInboxActivity;
import com.gandsoft.openguide.view.services.MyService;
import com.gandsoft.openguide.database.SQLiteHelper;
import com.gandsoft.openguide.support.AppUtil;
import com.gandsoft.openguide.support.DateTimeUtil;
import com.gandsoft.openguide.support.DeviceDetailUtil;
import com.gandsoft.openguide.support.HomeWatcher;
import com.gandsoft.openguide.support.NetworkUtil;
import com.gandsoft.openguide.support.NotifUtil;
import com.gandsoft.openguide.support.PictureUtil;
import com.gandsoft.openguide.support.ServiceUtil;
import com.gandsoft.openguide.support.SessionUtil;
import com.gandsoft.openguide.support.SystemUtil;
import com.google.firebase.auth.FirebaseAuth;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.gandsoft.openguide.database.SQLiteHelper.Key_Place_List_EventId;
import static com.gandsoft.openguide.database.SQLiteHelper.Key_Place_List_staticMap;
import static com.gandsoft.openguide.database.SQLiteHelper.TablePlaceList;

public class BaseHomeActivity extends AppCompatActivity {
    private static final int REQ_CODE_INBOX = 123;

    static final int NUM_ITEMS = 5;

    SQLiteHelper db = new SQLiteHelper(this);
    HomeWatcher mHomeWatcher = new HomeWatcher(this);

    ViewPager mPager;
    SlidePagerAdapter mPagerAdapter;
    private TabLayout tabLayout;
    private ActionBar ab;
    private String[] titleTab = new String[]{"Home", "Wallet", "Schedule", "About the Event", "Important Information"};
    private int[] iconTab = new int[]{R.drawable.ic_tab_home, R.drawable.ic_tab_wallet, R.drawable.ic_tab_schedule, R.drawable.ic_tab_about, R.drawable.ic_tab_info};
    private Toolbar toolbar;
    private String accountId, eventId;
    private int version_data_event;
    private boolean doubleBackToExitPressedOnce;
    ImageView imviewdial;
    int a = 0;
    private NotificationManager notificationManager;
    private MenuItem menuItem;
    private TextView tvMenuItemBadge;
    private FirebaseAuth mAuth;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_home);

        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        clearNotif();

        if (!SystemUtil.isMyServiceRunning(BaseHomeActivity.this, MyService.class)) {
            ServiceUtil.startService(BaseHomeActivity.this, MyService.class);
        }

        initSession();
        initParam();
        initComponent();
        initContent();
        initListener();
        if (NetworkUtil.isConnected(getApplicationContext())) {
            initStaticMaps();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        clearNotif();
    }

    private void initComponent() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        tabLayout = (TabLayout) findViewById(R.id.tlFiltSortHot);
        mPager = (ViewPager) findViewById(R.id.pager);
    }

    private void initSession() {
        if (SessionUtil.checkIfExist(ISeasonConfig.KEY_ACCOUNT_ID)) {
            accountId = SessionUtil.getStringPreferences(ISeasonConfig.KEY_ACCOUNT_ID, null);
        }
        if (SessionUtil.checkIfExist(ISeasonConfig.KEY_EVENT_ID)) {
            eventId = SessionUtil.getStringPreferences(ISeasonConfig.KEY_EVENT_ID, null);
        } else {
            AppUtil.outEventFull(this, ChangeEventActivity.class, ISeasonConfig.ID_NOTIF);
        }
    }

    private void initParam() {

    }

    private void initContent() {
        if (!db.isFirstIn(eventId)) {
            showFirstDialogEvent();
        }

        //getAPIVerivyUser();

        mAuth = FirebaseAuth.getInstance();

        initActionBar();
        initTablayoutViewpager();

        mHomeWatcher.setOnHomePressedListener(new HomeWatcher.OnHomePressedListener() {
            @Override
            public void onHomePressed() {
                Log.d("Lihat", "onHomePressed BaseHomeActivity : " + "home");
                setNotif();
            }

            @Override
            public void onHomeLongPressed() {
            }
        });
        mHomeWatcher.startWatch();

        getAPIVerivyUser();

        initLoopCheck();

    }

    private void initLoopCheck() {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (!db.isUserStillIn(accountId)) {
                            AppUtil.signOutUserOtherDevice(BaseHomeActivity.this, db, MyService.class, accountId, true); //check eveery 30sec
                        }
                    }
                });
            }
        }, DateTimeUtil.MINUTE_MILLIS / 4, DateTimeUtil.MINUTE_MILLIS / 2);
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
        mPager.setOffscreenPageLimit(4);
        mPager.setCurrentItem(0);
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

    private void showFirstDialogEvent() {

        String wew = db.getTheEvent(eventId).getWelcome_note();
        Document doc = Jsoup.parse(wew);
        Elements img = doc.select("img");
        String urlsd = img.attr("abs:src");

        Glide.with(getApplicationContext())
                .load(urlsd)
                .asBitmap()
                .fitCenter()
                .skipMemoryCache(false)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        Dialog dialogOpeningEvent;
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            dialogOpeningEvent = new Dialog(BaseHomeActivity.this, android.R.style.Theme_DeviceDefault_Light_Dialog);
                        } else {
                            dialogOpeningEvent = new Dialog(BaseHomeActivity.this);
                        }
                        dialogOpeningEvent.requestWindowFeature(Window.FEATURE_NO_TITLE);//untuk tidak ada title
                        dialogOpeningEvent.setContentView(R.layout.custom_dialog);
                        dialogOpeningEvent.setCancelable(false);
                        dialogOpeningEvent.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));//untuk menghilangkan background
                        dialogOpeningEvent.setTitle("");

                        WindowManager.LayoutParams layoutparams = new WindowManager.LayoutParams();
                        layoutparams.copyFrom(dialogOpeningEvent.getWindow().getAttributes());
                        layoutparams.width = WindowManager.LayoutParams.WRAP_CONTENT;//ukuran lebar layout
                        layoutparams.height = WindowManager.LayoutParams.WRAP_CONTENT;//ukuran tinggi layout

                        ImageView ivDialWelcomOpeningEvent = (ImageView) dialogOpeningEvent.findViewById(R.id.ivDialWelcom);
                        Button btn_cancelOpeningEvent = (Button) dialogOpeningEvent.findViewById(R.id.btn_cancel);

                        Bitmap resizeImageBitmap = PictureUtil.resizeImageBitmap(resource, 1080);
                        ivDialWelcomOpeningEvent.setImageBitmap(resizeImageBitmap);

                        btn_cancelOpeningEvent.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                db.updateOneKey(SQLiteHelper.TableListEvent, SQLiteHelper.KEY_ListEvent_eventId, eventId, SQLiteHelper.KEY_ListEvent_IsFirstIn, String.valueOf(1));
                                dialogOpeningEvent.dismiss();
                            }
                        });

                        dialogOpeningEvent.show();
                        dialogOpeningEvent.getWindow().setAttributes(layoutparams);
                    }
                });
    }

    private void clearNotif() {
        NotifUtil.clearNotification(this, ISeasonConfig.ID_NOTIF);
    }

    private void getAPIVerivyUser() {
        VerificationStatusLoginAppUserRequestModel requestModel = new VerificationStatusLoginAppUserRequestModel();
        requestModel.setAccount_id(accountId);
        requestModel.setDevice_app(DeviceDetailUtil.getAllDataPhone2(BaseHomeActivity.this));
        requestModel.setDbver(String.valueOf(IConfig.DB_Version));

        API.doVerificationStatusLoginAppUserRet(requestModel).enqueue(new Callback<List<VerificationStatusLoginAppUserResponseModel>>() {
            @Override
            public void onResponse(Call<List<VerificationStatusLoginAppUserResponseModel>> call, Response<List<VerificationStatusLoginAppUserResponseModel>> response) {
                //progressDialog.dismiss();
                if (response.isSuccessful()) {
                    List<VerificationStatusLoginAppUserResponseModel> model = response.body();
                    if (model.size() == 0) {
                        AppUtil.signOutUserOtherDevice(BaseHomeActivity.this, db, MyService.class, accountId, true);
                    }
                } else {
                    Log.d("Lihat", "onFailure BaseHomeActivity : " + response.message());
                    //Crashlytics.logException(new Exception(response.message()));
                    Snackbar.make(findViewById(android.R.id.content), "Failed Connection To Server", Snackbar.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<VerificationStatusLoginAppUserResponseModel>> call, Throwable t) {
                //progressDialog.dismiss();
                //Crashlytics.logException(new Exception(t.getMessage()));
                Log.d("Lihat", "onFailure BaseHomeActivity : " + t.getMessage());
                Snackbar.make(findViewById(android.R.id.content), "Failed Connection To Server", Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    private void setNotif() {
        UserListEventResponseModel oneListEvent = db.getOneListEvent(eventId, accountId);
        String title = oneListEvent.getTitle();
        String date = oneListEvent.getDate();

        Intent intent = new Intent(this, BaseHomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, ISeasonConfig.ID_NOTIF, intent, PendingIntent.FLAG_ONE_SHOT);

        NotifUtil.setNotification(this, "Openguide", title + " , " + date, R.drawable.ic_og_logo, R.mipmap.ic_launcher, true, pendingIntent, ISeasonConfig.ID_NOTIF);
    }

    private void initStaticMaps() {
        ArrayList<EventPlaceList> placeLists = db.getPlaceList(eventId);

        if (!db.isDataTableValueMultipleNotNull(TablePlaceList, Key_Place_List_EventId, Key_Place_List_staticMap, eventId)) {
            Double longitude = 0.0;
            Double latitude = 0.0;
            Double count = 0.0;
            for (int iPlace = 0; iPlace < placeLists.size(); iPlace++) {
                EventPlaceList placeList = placeLists.get(iPlace);
                if (Double.parseDouble(placeList.getLatitude()) != 0 && Double.parseDouble(placeList.getLongitude()) != 0) {
                    longitude = Double.valueOf(placeList.getLongitude()) + longitude;
                    latitude = Double.valueOf(placeList.getLatitude()) + latitude;
                    count++;
                }
            }
            Double longitudePass = longitude / count;
            Double latitudePass = latitude / count;

            String link = "http://static-maps.yandex.ru/1.x/?lang=en_US&ll=" + longitudePass + "," + latitudePass + "&size=450,450&z=10&l=map&pt=";
            StringBuilder sb = new StringBuilder();
            for (int iPlace = 0; iPlace < placeLists.size(); iPlace++) {
                EventPlaceList placeList = placeLists.get(iPlace);
                if (Double.parseDouble(placeList.getLatitude()) != 0 && Double.parseDouble(placeList.getLongitude()) != 0) {
                    if (iPlace == 1) {
                        String params = "" + placeList.getLongitude() + "," + placeList.getLatitude() + ",pm2rdl" + iPlace + "";
                        sb.append(params);
                    } else {
                        String params = "~" + placeList.getLongitude() + "," + placeList.getLatitude() + ",pm2rdl" + iPlace + "";
                        sb.append(params);
                    }
                }
            }

            Log.d("Lihat", "initStaticMaps aMapActivity static map: " + link + sb);

            Glide.with(getApplicationContext())
                    .load(link + sb)
                    .asBitmap()
                    .placeholder(R.drawable.template_account_og)
                    .skipMemoryCache(false)
                    .error(R.drawable.template_account_og)
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                            String address = PictureUtil.saveImageStaticMaps(resource, "staticMaps" + eventId);
                            db.savePlaceListStaticMaps(eventId, address);
                        }
                    });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_base_home, menu);

        menuItem = menu.findItem(R.id.action_settings);

        //View actionView = MenuItemCompat.getActionView(menuItem);
        View actionView = menuItem.getActionView();
        tvMenuItemBadge = (TextView) actionView.findViewById(R.id.cart_badge);
        tvMenuItemBadge.setText("");

        setupBadge();

        actionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOptionsItemSelected(menuItem);
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings: {
                String title = "Inbox";
                Intent intent = new Intent(this, cInboxActivity.class);
                intent.putExtra("TITLE", title);
                startActivityForResult(intent, REQ_CODE_INBOX);
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupBadge() {

        ArrayList<EventCommitteeNote> commiteNote = db.getCommiteNote(eventId);
        int noteIsOpen = db.getCommiteHasBeenOpened(eventId);
        if (commiteNote.size() != 0) {
            if (commiteNote.size() == noteIsOpen) {
                tvMenuItemBadge.setVisibility(View.GONE);
            } else {
                tvMenuItemBadge.setVisibility(View.VISIBLE);
                //menuItem.setVisible(true);
            }
        } else {
            tvMenuItemBadge.setVisibility(View.GONE);
            //menuItem.setVisible(false);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (REQ_CODE_INBOX == requestCode && resultCode == RESULT_OK) {
            recreate();
        }
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            finish();
            setNotif();
        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Tekan sekali lagi untuk keluar", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHomeWatcher.stopWatch();
    }
}
