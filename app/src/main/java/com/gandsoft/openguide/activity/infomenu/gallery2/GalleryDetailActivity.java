package com.gandsoft.openguide.activity.infomenu.gallery2;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.gandsoft.openguide.R;

import java.util.ArrayList;

public class GalleryDetailActivity extends AppCompatActivity {

    private GalleryDetailPagerAdapter mGalleryDetailPagerAdapter;

    public ArrayList<GalleryImageModel> models = new ArrayList<>();
    int posData;

    Toolbar toolbar;

    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        initComponent();
        initParam();
        initSession();
        initContent();
        initListener();

    }

    private void initComponent() {
        toolbar = (Toolbar) findViewById(R.id.tb_gallery_detail);
        mViewPager = (ViewPager) findViewById(R.id.vp_gallery_detail);
    }

    private void initParam() {
        models = getIntent().getParcelableArrayListExtra("models");
        posData = getIntent().getIntExtra("posData", 0);
    }

    private void initSession() {

    }

    private void initContent() {
        setSupportActionBar(toolbar);

        setTitle(models.get(posData).getUsername());

        mGalleryDetailPagerAdapter = new GalleryDetailPagerAdapter(getSupportFragmentManager(), models);
        mViewPager.setPageTransformer(true, new DepthPageTransformer());
        mViewPager.setAdapter(mGalleryDetailPagerAdapter);
        mViewPager.setOffscreenPageLimit(3);
        mViewPager.setCurrentItem(posData);
    }

    private void initListener() {
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                setTitle(models.get(position).getUsername());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }
}
