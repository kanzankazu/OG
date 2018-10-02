package com.gandsoft.openguide.activity.main;

import android.app.Activity;
import android.app.Dialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.gandsoft.openguide.API.APIresponse.UserData.UserListEventResponseModel;
import com.gandsoft.openguide.ISeasonConfig;
import com.gandsoft.openguide.R;
import com.gandsoft.openguide.activity.infomenu.cInboxActivity;
import com.gandsoft.openguide.database.SQLiteHelper;
import com.gandsoft.openguide.support.HomeWatcher;
import com.gandsoft.openguide.support.PictureUtil;
import com.gandsoft.openguide.support.SessionUtil;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class BaseHomeActivity extends AppCompatActivity {
    private static final int ID_NOTIF = 0;
    SQLiteHelper db = new SQLiteHelper(this);

    static final int NUM_ITEMS = 5;

    ViewPager mPager;
    SlidePagerAdapter mPagerAdapter;
    private Activity activity;
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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_home);

        db = new SQLiteHelper(this);
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (isNotificationVisible()) {
            notificationManager.cancel(ID_NOTIF);
        }

        initComponent();
        initSession();
        initParam();
        initContent();
        initListener();

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
        }
    }

    private void initParam() {

    }

    private void initContent() {
        Log.d("Lihat", "onCreate BaseHomeActivity : " + db.isFirstIn(eventId));
        if (!db.isFirstIn(eventId)) {
            showFirstDialogEvent();
        }

        initActionBar();
        initTablayoutViewpager();

        HomeWatcher mHomeWatcher = new HomeWatcher(this);
        mHomeWatcher.setOnHomePressedListener(new HomeWatcher.OnHomePressedListener() {
            @Override
            public void onHomePressed() {
                Log.d("Lihat", "onHomePressed BaseHomeActivity : " + "home");
                makeNotifyApps();
            }
            @Override
            public void onHomeLongPressed() {
            }
        });
        mHomeWatcher.startWatch();
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

        String wew = db.getTheEvent(eventId).welcome_note;
        Document doc = Jsoup.parse(wew);
        Elements img = doc.select("img");
        String urlsd = img.attr("abs:src");

        Glide.with(this)
                .load(urlsd)
                .asBitmap()
                .fitCenter()
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        /*if (checkNotif() > 0) {
            inflater.inflate(R.menu.menu_main2, menu);
        } else {
            inflater.inflate(R.menu.menu_main, menu);
        }*/
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String title = "Notification";
        Intent intent = new Intent(this, cInboxActivity.class);
        intent.putExtra("TITLE", title);
        startActivity(intent);
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            finish();
            makeNotifyApps();
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

    /*@Override
    public void onBackPressed() {

    }*/

    private boolean isNotificationVisible() {
        Intent notificationIntent = new Intent(this, BaseHomeActivity.class);
        PendingIntent test = PendingIntent.getActivity(this, ID_NOTIF, notificationIntent, PendingIntent.FLAG_NO_CREATE);
        return test != null;
    }

    private void makeNotifyApps() {
        UserListEventResponseModel oneListEvent = db.getOneListEvent(eventId);
        String title = oneListEvent.getTitle();
        String date = oneListEvent.getDate();

        Intent intent = new Intent(this, BaseHomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, ID_NOTIF, intent, PendingIntent.FLAG_ONE_SHOT);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setContentTitle("Openguides")
                .setContentText(title + " , " + date)
                .setSmallIcon(R.drawable.ic_love_fill)
                .setLargeIcon(BitmapFactory.decodeResource(this.getResources(), R.mipmap.ic_launcher))
                .setAutoCancel(true)
                .setOngoing(true)
                .setContentIntent(pendingIntent);

        notificationManager.notify(ID_NOTIF, notificationBuilder.build());
    }
}
