package com.gandsoft.openguide.activity.infomenu.gallery2;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.gandsoft.openguide.R;
import com.gandsoft.openguide.support.PictureUtil;

import java.util.ArrayList;

public class GalleryDetailActivity extends AppCompatActivity {

    private GalleryDetailPagerAdapter mGalleryDetailPagerAdapter;

    public ArrayList<GalleryImageModel> models = new ArrayList<>();
    int posData;

    Toolbar toolbar;

    private ImageViewTouchViewPager mViewPager;
    private ScaleImageView zivDetailGalleryfvbi;
    private LinearLayout llDetailGalleryCommentfvbi, llDetailGalleryLikefvbi;
    private TextView tvDetailGalleryCommentfvbi, tvDetailGalleryLikefvbi, tvDetailGalleryUsernamefvbi, tvDetailGalleryCaptionfvbi;
    private ImageView ivDetailGalleryLikefvbi, ivDetailGalleryIconfvbi;

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
        mViewPager = (ImageViewTouchViewPager) findViewById(R.id.vp_gallery_detail);

        llDetailGalleryCommentfvbi = (LinearLayout) findViewById(R.id.llDetailGalleryComment);
        llDetailGalleryLikefvbi = (LinearLayout) findViewById(R.id.llDetailGalleryLike);
        tvDetailGalleryCommentfvbi = (TextView) findViewById(R.id.tvDetailGalleryComment);
        tvDetailGalleryLikefvbi = (TextView) findViewById(R.id.tvDetailGalleryLike);
        tvDetailGalleryUsernamefvbi = (TextView) findViewById(R.id.tvDetailGalleryUsername);
        tvDetailGalleryCaptionfvbi = (TextView) findViewById(R.id.tvDetailGalleryCaption);
        ivDetailGalleryLikefvbi = (ImageView) findViewById(R.id.ivDetailGalleryLike);
        ivDetailGalleryIconfvbi = (ImageView) findViewById(R.id.ivDetailGalleryIcon);
    }

    private void initParam() {
        models = getIntent().getParcelableArrayListExtra("models");
        Log.d("Lihat", "initParam GalleryDetailActivity : " + models.get(0).getImage_posted());
        posData = getIntent().getIntExtra("posData", 0);
        Log.d("Lihat", "initParam GalleryDetailActivity : " + posData);
    }

    private void initSession() {

    }

    private void initContent() {
        setSupportActionBar(toolbar);

        ArrayList<String> imageList = new ArrayList<>();
        for (int i = 0; i < models.size(); i++) {
            GalleryImageModel q = models.get(i);
            imageList.add(q.getImage_posted());
        }

        mGalleryDetailPagerAdapter = new GalleryDetailPagerAdapter(imageList, mViewPager);
        mViewPager.setPageTransformer(true, new DepthPageTransformer());
        mViewPager.setAdapter(mGalleryDetailPagerAdapter);
        mViewPager.setOffscreenPageLimit(0);
        mViewPager.setCurrentItem(posData);

        updateUi(posData);
    }

    private void initListener() {
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                updateUi(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    private void updateUi(int position) {
        GalleryImageModel model = models.get(position);
        setTitle(model.getUsername());
        tvDetailGalleryCommentfvbi.setText(model.getTotal_comment());
        tvDetailGalleryLikefvbi.setText(model.getLike());
        tvDetailGalleryUsernamefvbi.setText(model.getUsername());
        tvDetailGalleryCaptionfvbi.setText(model.getCaption());
        Glide.with(GalleryDetailActivity.this)
                .load(model.getImage_icon())
                .asBitmap()
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        Bitmap bitmap = PictureUtil.resizeImageBitmap(resource, 720);
                        ivDetailGalleryIconfvbi.setImageBitmap(bitmap);
                    }
                });
        if (Integer.parseInt(model.getStatus_like()) != 0) {
            ivDetailGalleryLikefvbi.setImageResource(R.drawable.ic_love_fill);
        } else {
            ivDetailGalleryLikefvbi.setImageResource(R.drawable.ic_love_empty);
        }

        llDetailGalleryLikefvbi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int iLike = Integer.parseInt(model.getLike());
                if (Integer.parseInt(model.getStatus_like()) == 0) {
                    tvDetailGalleryLikefvbi.setText(String.valueOf(iLike + 1));
                    ivDetailGalleryLikefvbi.setImageResource(R.drawable.ic_love_fill);
                    model.setStatus_like(String.valueOf(1));
                    model.setLike(String.valueOf(iLike + 1));
                } else {
                    tvDetailGalleryLikefvbi.setText(String.valueOf(iLike - 1));
                    ivDetailGalleryLikefvbi.setImageResource(R.drawable.ic_love_empty);
                    model.setStatus_like(String.valueOf(0));
                    model.setLike(String.valueOf(iLike - 1));
                }
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
        onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
