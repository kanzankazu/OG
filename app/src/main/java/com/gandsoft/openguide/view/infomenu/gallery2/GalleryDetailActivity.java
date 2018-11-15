package com.gandsoft.openguide.view.infomenu.gallery2;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.gandsoft.openguide.API.API;
import com.gandsoft.openguide.API.APIrequest.HomeContent.HomeContentPostLikeRequestModel;
import com.gandsoft.openguide.API.APIresponse.HomeContent.HomeContentCommentModelParcelable;
import com.gandsoft.openguide.API.APIresponse.LocalBaseResponseModel;
import com.gandsoft.openguide.IConfig;
import com.gandsoft.openguide.ISeasonConfig;
import com.gandsoft.openguide.R;
import com.gandsoft.openguide.database.SQLiteHelper;
import com.gandsoft.openguide.database.SQLiteHelperMethod;
import com.gandsoft.openguide.support.AppUtil;
import com.gandsoft.openguide.support.InputValidUtil;
import com.gandsoft.openguide.support.NetworkUtil;
import com.gandsoft.openguide.support.PictureUtil;
import com.gandsoft.openguide.support.SessionUtil;
import com.gandsoft.openguide.view.main.fragments.aHomeActivityInFragment.aHomePostCommentActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GalleryDetailActivity extends AppCompatActivity {

    private static final int REQ_CODE_COMMENT = 123;
    public ArrayList<GalleryImageModel> models = new ArrayList<>();
    SQLiteHelperMethod db = new SQLiteHelperMethod(this);
    int posData;
    Toolbar toolbar;
    private GalleryDetailPagerAdapter mGalleryDetailPagerAdapter;
    private ViewPager mViewPager;
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
        mViewPager = (ViewPager) findViewById(R.id.vp_gallery_detail);

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
            imageList.add(NetworkUtil.isConnected(getApplicationContext()) ? q.getImage_posted() : TextUtils.isEmpty(q.getImage_posted_local()) ? q.getImage_posted() : !PictureUtil.isFileExists(q.getImage_posted_local()) ? q.getImage_posted() : q.getImage_posted_local());
        }

        mGalleryDetailPagerAdapter = new GalleryDetailPagerAdapter(GalleryDetailActivity.this, imageList, mViewPager);
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
                if (NetworkUtil.isConnected(getApplicationContext())) {
                    Glide.with(getApplicationContext())
                            .load(model.getImage_posted())
                            .asBitmap()
                            .into(new SimpleTarget<Bitmap>() {
                                @Override
                                public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                                    PictureUtil.saveImageToPicture(GalleryDetailActivity.this, resource, db.getOneListEvent(eventId, accountId).getTitle(), model.getId());
                                }
                            });
                } else {
                    Snackbar.make(findViewById(android.R.id.content), "Check you connection", Snackbar.LENGTH_SHORT).show();
                }
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
        String s = AppUtil.validationStringImageIcon(GalleryDetailActivity.this, model.getImage_icon(), model.getImage_icon_local(), false);
        Glide.with(getApplicationContext())
                .load(InputValidUtil.isLinkUrl(s) ? s : new File(s))
                .asBitmap()
                .error(R.drawable.template_account_og)
                .placeholder(R.drawable.ic_action_name)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(false)
                .dontAnimate()
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
                if (NetworkUtil.isConnected(getApplicationContext())) {
                    int iLike = Integer.parseInt(model.getLike());
                    postLike(model.getLike(), model, iLike);
                } else {
                    Snackbar.make(findViewById(android.R.id.content), "Check you connection", Snackbar.LENGTH_SHORT).show();
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
        if (NetworkUtil.isConnected(getApplicationContext())) {
            ArrayList<HomeContentCommentModelParcelable> dataParam = new ArrayList<>();
            HomeContentCommentModelParcelable mode = new HomeContentCommentModelParcelable();
            mode.setId(model.getId());
            mode.setLike(model.getLike());
            mode.setAccount_id(model.getAccount_id());
            mode.setTotal_comment(model.getTotal_comment());
            mode.setStatus_like(model.getStatus_like());
            mode.setUsername(model.getUsername());
            //mode.setJabatan(model.getJabatan());
            //mode.setDate_created(model.getDate_created());
            mode.setImage_icon(model.getImage_icon());
            mode.setImage_icon_local(model.getImage_icon_local());
            mode.setImage_posted(model.getImage_posted());
            mode.setImage_posted_local(model.getImage_posted_local());
            //mode.setKeterangan(model.getKeterangan());
            //mode.setNew_event(model.getNew_event());
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
        } else {
            Snackbar.make(findViewById(android.R.id.content), "Check you connection", Snackbar.LENGTH_SHORT).show();
        }
    }

    public void postLike(String likes, GalleryImageModel model, int iLike) {
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
                            LocalBaseResponseModel responseModel = s.get(i);
                            if (responseModel.getStatus().equalsIgnoreCase("ok")) {
                                Log.d("Status ok", "ok");
                                if (model.getStatus_like() == 0) {
                                    tvDetailGalleryLikefvbi.setText(String.valueOf(iLike + 1));
                                    ivDetailGalleryLikefvbi.setImageResource(R.drawable.ic_love_fill);
                                    model.setStatus_like(1);
                                    model.setLike(String.valueOf(iLike + 1));
                                } else {
                                    tvDetailGalleryLikefvbi.setText(String.valueOf(iLike - 1));
                                    ivDetailGalleryLikefvbi.setImageResource(R.drawable.ic_love_empty);
                                    model.setStatus_like(0);
                                    model.setLike(String.valueOf(iLike - 1));
                                }
                            } else {
                                Log.d("Lihat", "onResponse GalleryDetailActivity : " + responseModel.getStatus());
                                Log.d("Lihat", "onResponse GalleryDetailActivity : " + responseModel.getMessage());
                            }
                        }
                    } else {
                        Log.d("Lihat", "onResponse HomeContentAdapter : " + response.message());
                        //Snackbar.make(findViewById(android.R.id.content), "Failed Connection To Server", Snackbar.LENGTH_LONG).show();
                        //Crashlytics.logException(new Exception(response.message()));
                    }
                } else {
                    Log.d("Lihat", "onResponse HomeContentAdapter : " + response.message());
                    //Snackbar.make(findViewById(android.R.id.content), "Failed Connection To Server", Snackbar.LENGTH_LONG).show();
                    //Crashlytics.logException(new Exception(response.message()));
                }
            }

            @Override
            public void onFailure(Call<List<LocalBaseResponseModel>> call, Throwable t) {
                //progressDialog.dismiss();
                Log.d("Lihat", "onFailure GalleryDetailActivity : " + t.getMessage());
                Snackbar.make(findViewById(android.R.id.content), "Failed Connection To Server", Snackbar.LENGTH_SHORT).show();
                /*Snackbar.make(findViewById(android.R.id.content), "Failed Connection To Server", Snackbar.LENGTH_SHORT).setAction("Reload", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                }).show();*/
                //Crashlytics.logException(new Exception(t.getMessage()));
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
