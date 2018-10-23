package com.gandsoft.openguide.activity.infomenu.gallery2;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.gandsoft.openguide.API.API;
import com.gandsoft.openguide.API.APIrequest.HomeContent.HomeContentPostLikeRequestModel;
import com.gandsoft.openguide.API.APIresponse.HomeContent.HomeContentCommentModelParcelable;
import com.gandsoft.openguide.API.APIresponse.LocalBaseResponseModel;
import com.gandsoft.openguide.IConfig;
import com.gandsoft.openguide.ISeasonConfig;
import com.gandsoft.openguide.R;
import com.gandsoft.openguide.activity.main.fragments.aHomeActivityInFragment.aHomePostCommentActivity;
import com.gandsoft.openguide.database.SQLiteHelper;
import com.gandsoft.openguide.support.PictureUtil;
import com.gandsoft.openguide.support.SessionUtil;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GalleryDetailActivity extends AppCompatActivity {

    SQLiteHelper db = new SQLiteHelper(this);

    private static final int REQ_CODE_COMMENT = 123;
    private GalleryDetailPagerAdapter mGalleryDetailPagerAdapter;

    public ArrayList<GalleryImageModel> models = new ArrayList<>();
    int posData;

    Toolbar toolbar;

    private ImageViewTouchViewPager mViewPager;
    private ScaleImageView zivDetailGalleryfvbi;
    private LinearLayout llDetailGalleryCommentfvbi, llDetailGalleryLikefvbi;
    private TextView tvDetailGalleryCommentfvbi, tvDetailGalleryClosefvbi, tvDetailGalleryLikefvbi, tvDetailGalleryUsernamefvbi, tvDetailGalleryCaptionfvbi;
    private ImageView ivDetailGalleryLikefvbi, ivDetailGalleryIconfvbi;
    private ImageButton ibDetailGalleryDownloadFilefvbi;

    private String accountId;
    private String eventId;
    private GalleryImageModel model;

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
        ibDetailGalleryDownloadFilefvbi = (ImageButton) findViewById(R.id.ibDetailGalleryDownloadFile);
        tvDetailGalleryClosefvbi = (TextView) findViewById(R.id.tvDetailGalleryClose);
    }

    private void initParam() {
        models = getIntent().getParcelableArrayListExtra("models");
        posData = getIntent().getIntExtra("posData", 0);
    }

    private void initSession() {
        if (SessionUtil.checkIfExist(ISeasonConfig.KEY_ACCOUNT_ID)) {
            accountId = SessionUtil.getStringPreferences(ISeasonConfig.KEY_ACCOUNT_ID, null);
        }
        if (SessionUtil.checkIfExist(ISeasonConfig.KEY_EVENT_ID)) {
            eventId = SessionUtil.getStringPreferences(ISeasonConfig.KEY_EVENT_ID, null);
        }
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

        ibDetailGalleryDownloadFilefvbi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Glide.with(getApplicationContext())
                        .load(model.getImage_posted())
                        .asBitmap()
                        .into(new SimpleTarget<Bitmap>() {
                            @Override
                            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                                PictureUtil.saveImageToPicture(GalleryDetailActivity.this, resource, db.getOneListEvent(eventId, accountId).getTitle(), model.getId());
                            }
                        });

            }
        });

        tvDetailGalleryClosefvbi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void updateUi(int position) {
        model = models.get(position);
        setTitle(model.getUsername());
        tvDetailGalleryCommentfvbi.setText(model.getTotal_comment());
        tvDetailGalleryLikefvbi.setText(model.getLike());
        tvDetailGalleryUsernamefvbi.setText(model.getUsername());
        tvDetailGalleryCaptionfvbi.setText(model.getCaption());
        Glide.with(getApplicationContext())
                .load(model.getImage_icon())
                .asBitmap()
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        Bitmap bitmap = PictureUtil.resizeImageBitmap(resource, 720);
                        ivDetailGalleryIconfvbi.setImageBitmap(bitmap);
                    }
                });
        if (model.getStatus_like() != 0) {
            ivDetailGalleryLikefvbi.setImageResource(R.drawable.ic_love_fill);
        } else {
            ivDetailGalleryLikefvbi.setImageResource(R.drawable.ic_love_empty);
        }

        if (Integer.parseInt(db.getTheEvent(eventId).getCommentpost_status()) == 1) {
            llDetailGalleryCommentfvbi.setVisibility(View.VISIBLE);
        } else {
            llDetailGalleryCommentfvbi.setVisibility(View.GONE);
        }

        llDetailGalleryLikefvbi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int iLike = Integer.parseInt(model.getLike());
                if (model.getStatus_like() == 0) {
                    tvDetailGalleryLikefvbi.setText(String.valueOf(iLike + 1));
                    ivDetailGalleryLikefvbi.setImageResource(R.drawable.ic_love_fill);
                    model.setStatus_like(1);
                    model.setLike(String.valueOf(iLike + 1));
                    postLike(model.getLike(), model);
                } else {
                    tvDetailGalleryLikefvbi.setText(String.valueOf(iLike - 1));
                    ivDetailGalleryLikefvbi.setImageResource(R.drawable.ic_love_empty);
                    model.setStatus_like(0);
                    model.setLike(String.valueOf(iLike - 1));
                    postLike(model.getLike(), model);
                }
            }
        });

        llDetailGalleryCommentfvbi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveToAHomeComment(model, position);
            }
        });
    }

    private void moveToAHomeComment(GalleryImageModel model, int position) {
        ArrayList<HomeContentCommentModelParcelable> dataParam = new ArrayList<>();
        HomeContentCommentModelParcelable mode = new HomeContentCommentModelParcelable();
        mode.setId(model.getId());
        mode.setLike(model.getLike());
        mode.setAccount_id(model.getAccount_id());
        mode.setTotal_comment(model.getTotal_comment());
        mode.setStatus_like(model.getStatus_like());
        mode.setUsername(model.getUsername());
        mode.setImage_icon(model.getImage_icon());
        mode.setImage_posted(model.getImage_posted());
        if (dataParam.size() > 0) {
            dataParam.clear();
            dataParam.add(mode);
        } else {
            dataParam.add(mode);
        }
        Intent intent = new Intent(this, aHomePostCommentActivity.class);
        intent.putParcelableArrayListExtra(ISeasonConfig.INTENT_PARAM, dataParam);
        intent.putExtra(ISeasonConfig.INTENT_PARAM2, position);
        startActivityForResult(intent, REQ_CODE_COMMENT);
    }

    public void postLike(String likes, GalleryImageModel model) {
        HomeContentPostLikeRequestModel requestModel = new HomeContentPostLikeRequestModel();
        requestModel.setAccount_id(accountId);
        requestModel.setEvent_id(eventId);
        requestModel.setId_content(model.getId());
        requestModel.setVal_like(likes);
        requestModel.setDbver(String.valueOf(IConfig.DB_Version));
        if (model.getStatus_like() == 1) {
            requestModel.setStatus_like("0");
        } else if (model.getStatus_like() == 0) {
            requestModel.setStatus_like("1");
        }

        API.doHomeContentPostLikeRet(requestModel).enqueue(new Callback<List<LocalBaseResponseModel>>() {
            @Override
            public void onResponse(Call<List<LocalBaseResponseModel>> call, Response<List<LocalBaseResponseModel>> response) {
                if (response.isSuccessful()) {
                    List<LocalBaseResponseModel> s = response.body();
                    if (s.size() == 1) {
                        for (int i = 0; i < s.size(); i++) {
                            LocalBaseResponseModel model = s.get(i);
                            if (model.getStatus().equalsIgnoreCase("ok")) {
                                Log.d("Status ok", "ok");

                            } else {
                            }
                        }
                    } else {
                    }
                } else {
                }
            }

            @Override
            public void onFailure(Call<List<LocalBaseResponseModel>> call, Throwable t) {
                Log.d("tmassage", String.valueOf(t));
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_CODE_COMMENT && resultCode == Activity.RESULT_OK) {
            int position = data.getIntExtra(ISeasonConfig.INTENT_PARAM, 0);
            String totalComment = data.getStringExtra(ISeasonConfig.INTENT_PARAM2);
            tvDetailGalleryCommentfvbi.setText(totalComment);
            models.get(position).setTotal_comment(totalComment);
        }
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
