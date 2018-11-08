package com.gandsoft.openguide.view.infomenu.gallery2;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.gandsoft.openguide.API.API;
import com.gandsoft.openguide.API.APIrequest.Gallery.GalleryRequestModel;
import com.gandsoft.openguide.API.APIresponse.HomeContent.HomeContentResponseModel;
import com.gandsoft.openguide.IConfig;
import com.gandsoft.openguide.ISeasonConfig;
import com.gandsoft.openguide.R;
import com.gandsoft.openguide.database.SQLiteHelper;
import com.gandsoft.openguide.presenter.widget.CustomScrollView;
import com.gandsoft.openguide.support.NetworkUtil;
import com.gandsoft.openguide.support.SessionUtil;
import com.gandsoft.openguide.support.SystemUtil;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GalleryActivity extends AppCompatActivity {
    SQLiteHelper db = new SQLiteHelper(this);

    private GalleryAdapter rvGalleryAdapter;
    private RecyclerView rvGalleryfvbi;
    private CustomScrollView nsvGalleryfvbi;
    private SwipeRefreshLayout srlGalleryfvbi;
    private LinearLayout llLoadModeGalleryfvbi;

    private Toolbar toolbar;
    private ActionBar ab;
    private ArrayList<GalleryImageModel> data = new ArrayList<>();
    private List<HomeContentResponseModel> menuUi = new ArrayList<>();

    private String accountId, eventId;
    private String last_id = "";
    private String first_id = "";
    private String last_date = "";
    private boolean last_data = false;
    private boolean isNoMoreShow = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        initSession();
        initComponent();
        initContent();
        initListener();
    }

    public void initSession() {
        if (SessionUtil.checkIfExist(ISeasonConfig.KEY_ACCOUNT_ID)) {
            accountId = SessionUtil.getStringPreferences(ISeasonConfig.KEY_ACCOUNT_ID, null);
        }
        if (SessionUtil.checkIfExist(ISeasonConfig.KEY_EVENT_ID)) {
            eventId = SessionUtil.getStringPreferences(ISeasonConfig.KEY_EVENT_ID, null);
        }
    }

    private void initComponent() {
        rvGalleryfvbi = (RecyclerView) findViewById(R.id.rvGallery);
        nsvGalleryfvbi = (CustomScrollView) findViewById(R.id.nsvGallery);
        srlGalleryfvbi = (SwipeRefreshLayout) findViewById(R.id.srlGallery);
        llLoadModeGalleryfvbi = (LinearLayout) findViewById(R.id.llLoadModeGallery);
    }

    private void initContent() {

        initToolbar("Gallery");

        rvGalleryAdapter = new GalleryAdapter(GalleryActivity.this, menuUi, eventId, accountId, new GalleryAdapter.GalleryAdapterListener() {
            @Override
            public void onDetailClick(int position) {
                moveToGalleryDetail(position);
            }
        });
        //rvGalleryAdapter.setHasStableIds(true);
        rvGalleryfvbi.setNestedScrollingEnabled(false);
        rvGalleryfvbi.setAdapter(rvGalleryAdapter);
        //rvGalleryfvbi.setHasFixedSize(true);
        //rvGalleryfvbi.setItemViewCacheSize(50);
        //rvGalleryfvbi.setDrawingCacheEnabled(true);
        //rvGalleryfvbi.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        rvGalleryfvbi.setLayoutManager(new GridLayoutManager(this, 3));

        getGalleryData();
    }

    private void initListener() {
        srlGalleryfvbi.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getGalleryData();
            }
        });

        nsvGalleryfvbi.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
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
                    if (NetworkUtil.isConnected(getApplicationContext())) {
                        if (!last_data) {
                            llLoadModeGalleryfvbi.setVisibility(View.VISIBLE);
                            nsvGalleryfvbi.setEnableScrolling(false);

                            new Handler().postDelayed(new Runnable() {
                                public void run() {
                                    //code here
                                    nsvGalleryfvbi.fullScroll(View.FOCUS_DOWN);
                                }
                            }, 500);

                            new Handler().postDelayed(new Runnable() {
                                public void run() {
                                    //code here
                                    getGalleryDataLoadMode();
                                }
                            }, 2000);
                        } else {
                            if (!isNoMoreShow) {
                                Snackbar.make(findViewById(android.R.id.content), "no more data", Snackbar.LENGTH_SHORT).show();
                                isNoMoreShow = true;
                            }
                        }
                    } else {
                        Snackbar.make(findViewById(android.R.id.content), "Check you connection", Snackbar.LENGTH_SHORT).show();
                    }

                }

                /*int measuredHeight = homeIVEventBackgroundfvbi.getMeasuredHeight();
                int measuredHeight1 = homeLLWriteSomethingfvbi.getMeasuredHeight();
                int measuredHeight2 = 0;
                for (int i = 0; i < 4; i++) {
                    measuredHeight2 = measuredHeight2 + recyclerView.getChildAt(i).getMeasuredHeight();
                }*/

                /*if (scrollY > measuredHeight + measuredHeight1 + measuredHeight2) {
                    homeFABHomeUpfvbi.setVisibility(View.VISIBLE);
                } else {
                    homeFABHomeUpfvbi.setVisibility(View.GONE);
                }*/
            }
        });
    }

    private void moveToGalleryDetail(int position) {
        Intent intent = new Intent(GalleryActivity.this, GalleryDetailActivity.class);
        intent.putParcelableArrayListExtra("models", data);
        intent.putExtra("posData", position);
        startActivity(intent);

    }

    public void initToolbar(String title) {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ab = getSupportActionBar();
        ab.setTitle(title);
    }

    private void getGalleryData() {
        if (srlGalleryfvbi.isRefreshing()) {
            srlGalleryfvbi.setRefreshing(false);
        }

        if (NetworkUtil.isConnected(getApplicationContext())) {
            GalleryRequestModel requestModel = new GalleryRequestModel();

            requestModel.setDbver(String.valueOf(IConfig.DB_Version));
            requestModel.setId_event(eventId);
            requestModel.setPhonenumber(accountId);
            requestModel.setKondisi("up");
            requestModel.setLastid("");
            requestModel.setFirstid("");

            ProgressDialog progressDialog = SystemUtil.showProgress(GalleryActivity.this, "Get data from server", "Please Wait...", false);

            API.doGalleryRet(requestModel).enqueue(new Callback<List<HomeContentResponseModel>>() {
                @Override
                public void onResponse(Call<List<HomeContentResponseModel>> call, Response<List<HomeContentResponseModel>> response) {
                    SystemUtil.hideProgress(progressDialog, 2000);
                    if (response.isSuccessful()) {
                        List<HomeContentResponseModel> models = response.body();
                        db.deleteAllDataGallery(eventId);
                        for (int i = 0; i < models.size(); i++) {
                            HomeContentResponseModel model = models.get(i);
                            db.saveGallery(model, eventId);
                            /*if (db.isDataTableValueMultipleNull(SQLiteHelper.TableGallery, SQLiteHelper.Key_Gallery_eventId, SQLiteHelper.Key_Gallery_galleryId, eventId, model.getId())) {
                                db.saveGallery(model, eventId);
                            } else {
                                db.saveGallery(model, eventId);
                            }*/

                            GalleryImageModel model1 = new GalleryImageModel();
                            model1.setId(model.getId());
                            model1.setLike(model.getLike());
                            model1.setAccount_id(model.getAccount_id());
                            model1.setTotal_comment(model.getTotal_comment());
                            model1.setStatus_like(model.getStatus_like());
                            model1.setUsername(model.getUsername());
                            model1.setCaption(model.getCaption());
                            model1.setImage_posted(model.getImage_posted());
                            model1.setImage_posted_local(model.getImage_posted_local());
                            model1.setImage_icon(model.getImage_icon());
                            model1.setImage_icon_local(model.getImage_icon_local());
                            model1.setEvent_id(eventId);
                            data.add(model1);
                        }

                        rvGalleryAdapter.setData(models);
                        if (models.size() == 18) {
                            last_data = false;
                            last_id = models.get(models.size() - 1).getId();
                            first_id = models.get(0).getId();
                        } else {
                            last_data = true;
                            last_id = "";
                            first_id = "";
                        }
                    } else {
                        Snackbar.make(findViewById(android.R.id.content), response.message(), Snackbar.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<List<HomeContentResponseModel>> call, Throwable t) {
                    SystemUtil.hideProgress(progressDialog, 2000);
                    Snackbar.make(findViewById(android.R.id.content), t.getMessage(), Snackbar.LENGTH_LONG).show();
                }
            });
        } else {
            ArrayList<HomeContentResponseModel> galleryTop = db.getGallery18(eventId);
            if (galleryTop.size() != 0) {
                rvGalleryAdapter.setData(galleryTop);
                last_data = false;
                last_id = galleryTop.get(galleryTop.size() - 1).getId();
                first_id = galleryTop.get(0).getId();

                Log.d("Lihat", "getGalleryData GalleryActivity : " + galleryTop.size());

                for (int i = 0; i < galleryTop.size(); i++) {
                    HomeContentResponseModel model = galleryTop.get(i);
                    GalleryImageModel model1 = new GalleryImageModel();
                    model1.setId(model.getId());
                    model1.setLike(model.getLike());
                    model1.setAccount_id(model.getAccount_id());
                    model1.setTotal_comment(model.getTotal_comment());
                    model1.setStatus_like(model.getStatus_like());
                    model1.setUsername(model.getUsername());
                    model1.setCaption(model.getCaption());
                    model1.setImage_posted(model.getImage_posted());
                    model1.setImage_posted_local(model.getImage_posted_local());
                    model1.setImage_icon(model.getImage_icon());
                    model1.setImage_icon_local(model.getImage_icon_local());
                    model1.setEvent_id(eventId);
                    data.add(model1);
                }
            } else {
                Snackbar.make(findViewById(android.R.id.content), "No data", Snackbar.LENGTH_SHORT).show();
            }
        }
    }

    private void getGalleryDataLoadMode() {
        if (NetworkUtil.isConnected(getApplicationContext())) {
            GalleryRequestModel requestModel = new GalleryRequestModel();
            requestModel.setDbver(String.valueOf(IConfig.DB_Version));
            requestModel.setId_event(eventId);
            requestModel.setPhonenumber(accountId);
            requestModel.setKondisi("down");
            requestModel.setLastid(last_id);
            requestModel.setFirstid(first_id);

            API.doGalleryRet(requestModel).enqueue(new Callback<List<HomeContentResponseModel>>() {
                @Override
                public void onResponse(Call<List<HomeContentResponseModel>> call, Response<List<HomeContentResponseModel>> response) {
                    new Handler().postDelayed(new Runnable() {
                        public void run() {
                            //code here
                            llLoadModeGalleryfvbi.setVisibility(View.GONE);
                            nsvGalleryfvbi.setEnableScrolling(true);
                        }
                    }, 2000);
                    if (response.isSuccessful()) {
                        List<HomeContentResponseModel> models = response.body();
                        for (int i = 0; i < models.size(); i++) {
                            HomeContentResponseModel model = models.get(i);
                            if (db.isDataTableValueMultipleNull(SQLiteHelper.TableGallery, SQLiteHelper.Key_Gallery_eventId, SQLiteHelper.Key_Gallery_galleryId, eventId, model.getId())) {
                                db.saveGallery(model, eventId);
                            } else {
                                db.updateGallery(model, eventId);
                            }

                            GalleryImageModel model1 = new GalleryImageModel();
                            model1.setId(model.getId());
                            model1.setLike(model.getLike());
                            model1.setAccount_id(model.getAccount_id());
                            model1.setTotal_comment(model.getTotal_comment());
                            model1.setStatus_like(model.getStatus_like());
                            model1.setUsername(model.getUsername());
                            model1.setCaption(model.getCaption());
                            model1.setImage_posted(model.getImage_posted());
                            model1.setImage_posted_local(model.getImage_posted_local());
                            model1.setImage_icon(model.getImage_icon());
                            model1.setImage_icon_local(model.getImage_icon_local());
                            model1.setEvent_id(eventId);
                            data.add(model1);
                        }

                        rvGalleryAdapter.addDatas(models);
                        if (models.size() == 18) {
                            last_data = false;
                            last_id = models.get(models.size() - 1).getId();
                            first_id = models.get(0).getId();
                        } else {
                            last_data = true;
                            last_id = "";
                            first_id = "";
                        }

                    } else {
                        Snackbar.make(findViewById(android.R.id.content), response.message(), Snackbar.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<List<HomeContentResponseModel>> call, Throwable t) {
                    llLoadModeGalleryfvbi.setVisibility(View.GONE);
                    nsvGalleryfvbi.setEnableScrolling(true);
                    Snackbar.make(findViewById(android.R.id.content), t.getMessage(), Snackbar.LENGTH_LONG).show();
                }
            });
        } else {
            llLoadModeGalleryfvbi.setVisibility(View.GONE);
            nsvGalleryfvbi.setEnableScrolling(true);
            Snackbar.make(findViewById(android.R.id.content), "Check you connection", Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }

}
