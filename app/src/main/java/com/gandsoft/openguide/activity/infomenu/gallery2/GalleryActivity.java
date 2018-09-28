package com.gandsoft.openguide.activity.infomenu.gallery2;

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
import com.gandsoft.openguide.API.APIresponse.Gallery.GalleryResponseModel;
import com.gandsoft.openguide.IConfig;
import com.gandsoft.openguide.ISeasonConfig;
import com.gandsoft.openguide.R;
import com.gandsoft.openguide.database.SQLiteHelper;
import com.gandsoft.openguide.support.SessionUtil;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GalleryActivity extends AppCompatActivity {
    SQLiteHelper db = new SQLiteHelper(this);

    private GalleryAdapter rvGalleryAdapter;
    private RecyclerView rvGalleryfvbi;
    private NestedScrollView nsvGalleryfvbi;
    private SwipeRefreshLayout srlGalleryfvbi;
    private LinearLayout llLoadModeGalleryfvbi;

    private Toolbar toolbar;
    private ActionBar ab;
    private ArrayList<GalleryImageModel> data = new ArrayList<>();
    private List<GalleryResponseModel> menuUi = new ArrayList<>();

    private String accountId, eventId;
    private String last_id = "";
    private String first_id = "";
    private String last_date = "";
    private boolean last_data = false;

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
        nsvGalleryfvbi = (NestedScrollView) findViewById(R.id.nsvGallery);
        srlGalleryfvbi = (SwipeRefreshLayout) findViewById(R.id.srlGallery);
        llLoadModeGalleryfvbi = (LinearLayout) findViewById(R.id.llLoadModeGallery);
    }

    private void initContent() {

        initToolbar("Gallery");

        rvGalleryAdapter = new GalleryAdapter(GalleryActivity.this, menuUi, eventId);
        rvGalleryfvbi.setNestedScrollingEnabled(false);
        rvGalleryfvbi.setAdapter(rvGalleryAdapter);
        //rvGalleryfvbi.setHasFixedSize(true);
        rvGalleryfvbi.setLayoutManager(new GridLayoutManager(this, 3));

        getGalleryData();
    }

    private void initListener() {
        rvGalleryfvbi.addOnItemTouchListener(new GalleryRecyclerItemClickListener(this, new GalleryRecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(GalleryActivity.this, GalleryDetailActivity.class);
                intent.putParcelableArrayListExtra("models", data);
                intent.putExtra("posData", position);
                startActivity(intent);
            }
        }));

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
                    if (!last_data) {
                        llLoadModeGalleryfvbi.setVisibility(View.VISIBLE);

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
                        Snackbar.make(findViewById(android.R.id.content), "Sudah tidak ada data", Snackbar.LENGTH_LONG).show();
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

        GalleryRequestModel requestModel = new GalleryRequestModel();

        requestModel.setDbver(String.valueOf(IConfig.DB_Version));
        requestModel.setId_event(eventId);
        requestModel.setPhonenumber(accountId);
        requestModel.setKondisi("up");
        requestModel.setLastid("");
        requestModel.setFirstid("");

        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setTitle("Get data from server");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();

        API.doGalleryRet(requestModel).enqueue(new Callback<List<GalleryResponseModel>>() {
            @Override
            public void onResponse(Call<List<GalleryResponseModel>> call, Response<List<GalleryResponseModel>> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    List<GalleryResponseModel> models = response.body();
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

                    for (int i = 0; i < models.size(); i++) {
                        GalleryResponseModel model = models.get(i);
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
                        model1.setStatus_like(Integer.parseInt(model.getStatus_like()));
                        model1.setUsername(model.getUsername());
                        model1.setCaption(model.getCaption());
                        model1.setImage_posted(model.getImage_posted());
                        model1.setImage_icon(model.getImage_icon());
                        model1.setImage_postedLocal(model.getImage_postedLocal());
                        model1.setImage_iconLocal(model.getImage_iconLocal());
                        model1.setEvent_id(eventId);
                        data.add(model1);
                    }
                } else {
                    Snackbar.make(findViewById(android.R.id.content), response.message(), Snackbar.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<GalleryResponseModel>> call, Throwable t) {
                progressDialog.dismiss();
                Snackbar.make(findViewById(android.R.id.content), t.getMessage(), Snackbar.LENGTH_LONG).show();
            }
        });
    }

    private void getGalleryDataLoadMode() {
        //code here
        GalleryRequestModel requestModel = new GalleryRequestModel();

        Log.d("Lihat", "run GalleryActivity last_id : " + last_id);
        Log.d("Lihat", "run GalleryActivity first_id : " + first_id);

        requestModel.setDbver(String.valueOf(IConfig.DB_Version));
        requestModel.setId_event(eventId);
        requestModel.setPhonenumber(accountId);
        requestModel.setKondisi("down");
        requestModel.setLastid(last_id);
        requestModel.setFirstid(first_id);

        API.doGalleryRet(requestModel).enqueue(new Callback<List<GalleryResponseModel>>() {
            @Override
            public void onResponse(Call<List<GalleryResponseModel>> call, Response<List<GalleryResponseModel>> response) {
                llLoadModeGalleryfvbi.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    List<GalleryResponseModel> models = response.body();
                    rvGalleryAdapter.addDatas(models);
                    Log.d("Lihat", "onResponse GalleryActivity : " + models.size());
                    if (models.size() == 18) {
                        last_data = false;
                        last_id = models.get(models.size() - 1).getId();
                        first_id = models.get(0).getId();
                    } else {
                        last_data = true;
                        last_id = "";
                        first_id = "";
                    }

                    for (int i = 0; i < models.size(); i++) {
                        GalleryResponseModel model = models.get(i);
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
                        model1.setStatus_like(Integer.parseInt(model.getStatus_like()));
                        model1.setUsername(model.getUsername());
                        model1.setCaption(model.getCaption());
                        model1.setImage_posted(model.getImage_posted());
                        model1.setImage_icon(model.getImage_icon());
                        model1.setImage_postedLocal(model.getImage_postedLocal());
                        model1.setImage_iconLocal(model.getImage_iconLocal());
                        model1.setEvent_id(eventId);
                        data.add(model1);
                    }
                } else {
                    Snackbar.make(findViewById(android.R.id.content), response.message(), Snackbar.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<GalleryResponseModel>> call, Throwable t) {
                llLoadModeGalleryfvbi.setVisibility(View.GONE);
                Snackbar.make(findViewById(android.R.id.content), t.getMessage(), Snackbar.LENGTH_LONG).show();
            }
        });


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
