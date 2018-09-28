package com.gandsoft.openguide.activity.main;

import android.app.Activity;
import android.app.Dialog;
import android.bluetooth.BluetoothHealthAppConfiguration;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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
import com.gandsoft.openguide.API.APIresponse.Event.EventCommitteeNote;
import com.gandsoft.openguide.ISeasonConfig;
import com.gandsoft.openguide.R;
import com.gandsoft.openguide.activity.ChangeEventActivity;
import com.gandsoft.openguide.activity.infomenu.cInboxActivity;
import com.gandsoft.openguide.database.SQLiteHelper;
import com.gandsoft.openguide.support.PictureUtil;
import com.gandsoft.openguide.support.SessionUtil;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class BaseHomeActivity extends AppCompatActivity {
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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_home);
        db = new SQLiteHelper(this);

        if (SessionUtil.checkIfExist(ISeasonConfig.KEY_ACCOUNT_ID)) {
            accountId = SessionUtil.getStringPreferences(ISeasonConfig.KEY_ACCOUNT_ID, null);
        }
        if (SessionUtil.checkIfExist(ISeasonConfig.KEY_EVENT_ID)) {
            eventId = SessionUtil.getStringPreferences(ISeasonConfig.KEY_EVENT_ID, null);
        }
        if (!db.isFirstIn(eventId)) {
            showFirstDialogEvent();
        }

        Log.d("Lihat", "onCreate BaseHomeActivity : " + db.isFirstIn(eventId));

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

    private int checkNotif() {
        ArrayList<EventCommitteeNote> wew = db.getCommiteNote(eventId);
        for (int i = 0; i < wew.size(); i++) {
            if (wew.get(i).getHas_been_opened().equals("0")) {
                a++;
            }
        }
        return a;
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
            Intent intent9 = new Intent(this, ChangeEventActivity.class);
            startActivity(intent9);
            finish();
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
}
