package com.gandsoft.openguide.view.main.fragments.aHomeActivityInFragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.gandsoft.openguide.API.API;
import com.gandsoft.openguide.API.APIrequest.HomeContent.HomeContentPostCommentDeleteRequestModel;
import com.gandsoft.openguide.API.APIrequest.HomeContent.HomeContentPostCommentGetRequestModel;
import com.gandsoft.openguide.API.APIrequest.HomeContent.HomeContentPostCommentSetRequestModel;
import com.gandsoft.openguide.API.APIresponse.HomeContent.HomeContentCommentModelParcelable;
import com.gandsoft.openguide.API.APIresponse.HomeContent.HomeContentPostCommentDeleteResponseModel;
import com.gandsoft.openguide.API.APIresponse.HomeContent.HomeContentPostCommentGetResponseModel;
import com.gandsoft.openguide.API.APIresponse.HomeContent.HomeContentPostCommentSetResponseModel;
import com.gandsoft.openguide.IConfig;
import com.gandsoft.openguide.ISeasonConfig;
import com.gandsoft.openguide.R;
import com.gandsoft.openguide.database.SQLiteHelper;
import com.gandsoft.openguide.support.AppUtil;
import com.gandsoft.openguide.support.InputValidUtil;
import com.gandsoft.openguide.support.NetworkUtil;
import com.gandsoft.openguide.support.SessionUtil;
import com.gandsoft.openguide.support.SystemUtil;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class aHomePostCommentActivity extends AppCompatActivity {
    SQLiteHelper db = new SQLiteHelper(this);

    private SwipeRefreshLayout srlCommentfvbi;
    private NestedScrollView nsvCommentfvbi;
    private RecyclerView rvCommentfvbi;
    private EditText etCommentPostfvbi;
    private ImageView ivCommentPostfvbi, ivCommentTsIconfvbi, ivCommentTsImagefvbi;
    private TextView tvCommentTsKeteranganfvbi, tvCommentTsTimefvbi, tvCommentTsUsernamefvbi;
    private Toolbar toolbar;
    private LinearLayout llLoadModecommentfvbi;

    private String accountId, eventId;
    private ArrayList<HomeContentCommentModelParcelable> models = new ArrayList<>();
    private HomeContentCommentModelParcelable model;
    private List<HomeContentPostCommentGetResponseModel> menuUi = new ArrayList<>();
    private HomeContentCommentAdapter adapter;
    private int position;
    private String total_comment;
    private boolean last_data;
    private String last_id;
    private String last_date;
    private String first_id;
    private String postId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a_home_post_comment);

        initComponent();
        initParam();
        initSession();
        initContent();
        initListener();
    }

    private void initComponent() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        rvCommentfvbi = (RecyclerView) findViewById(R.id.rvComment);
        etCommentPostfvbi = (EditText) findViewById(R.id.etCommentPost);
        ivCommentPostfvbi = (ImageView) findViewById(R.id.ivCommentPost);
        ivCommentTsIconfvbi = (ImageView) findViewById(R.id.ivCommentTsIcon);
        ivCommentTsImagefvbi = (ImageView) findViewById(R.id.ivCommentTsImage);
        tvCommentTsKeteranganfvbi = (TextView) findViewById(R.id.tvCommentTsKeterangan);
        tvCommentTsTimefvbi = (TextView) findViewById(R.id.tvCommentTsTime);
        tvCommentTsUsernamefvbi = (TextView) findViewById(R.id.tvCommentTsUsername);
        srlCommentfvbi = (SwipeRefreshLayout) findViewById(R.id.srlComment);
        nsvCommentfvbi = (NestedScrollView) findViewById(R.id.nsvComment);
        llLoadModecommentfvbi = (LinearLayout) findViewById(R.id.llLoadModecomment);
    }

    private void initParam() {
        Intent bundle = getIntent();
        if (bundle.hasExtra(ISeasonConfig.INTENT_PARAM)) {
            models = bundle.getParcelableArrayListExtra(ISeasonConfig.INTENT_PARAM);
            Log.d("Lihat", "initParam aHomePostCommentActivity : " + models.size());
        }
        if (bundle.hasExtra(ISeasonConfig.INTENT_PARAM2)) {
            position = bundle.getIntExtra(ISeasonConfig.INTENT_PARAM2, 0);
            Log.d("Lihat", "initParam aHomePostCommentActivity : " + models.size());
        }
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
        ActionBar ab = getSupportActionBar();
        ab.setTitle("Comment");

        model = models.get(0);
        postId = model.getId();

        tvCommentTsUsernamefvbi.setText(model.getUsername());
        tvCommentTsTimefvbi.setText(model.getDate_created());

        if (!TextUtils.isEmpty(model.getKeterangan())) {
            tvCommentTsKeteranganfvbi.setText(model.getKeterangan());
            tvCommentTsKeteranganfvbi.setVisibility(View.VISIBLE);
        } else if (!TextUtils.isEmpty(model.getNew_event())) {
            tvCommentTsKeteranganfvbi.setText(model.getNew_event());
            tvCommentTsKeteranganfvbi.setVisibility(View.GONE);
        } else {
            tvCommentTsKeteranganfvbi.setVisibility(View.GONE);
        }

        String stringImagePosted = AppUtil.validationStringImageIcon(aHomePostCommentActivity.this, model.getImage_posted(), model.getImage_posted_local(), false);
        String stringImageIcon = AppUtil.validationStringImageIcon(aHomePostCommentActivity.this, model.getImage_icon(), model.getImage_icon_local(), true);

        if (!TextUtils.isEmpty(model.getImage_posted())) {
            Glide.with(getApplicationContext())
                    .load(InputValidUtil.isLinkUrl(stringImagePosted) ? stringImagePosted : new File(stringImagePosted))
                    .asBitmap()
                    .thumbnail(0.1f)
                    .skipMemoryCache(false)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                            ivCommentTsImagefvbi.setImageBitmap(resource);
                            //String imageCachePath = PictureUtil.saveImageHomeContentImage(aHomePostCommentActivity.this, resource, model.getId() + "_image", eventId);
                            //db.saveHomeContentImage(imageCachePath, accountId, eventId, model.getId());
                        }
                    });
        } else {
            ivCommentTsImagefvbi.setVisibility(View.GONE);
        }
        Glide.with(getApplicationContext())
                .load(InputValidUtil.isLinkUrl(stringImageIcon) ? stringImageIcon : new File(stringImageIcon))
                .asBitmap()
                .thumbnail(0.1f)
                .skipMemoryCache(false)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        ivCommentTsIconfvbi.setImageBitmap(resource);
                        /*if (NetworkUtil.isConnected(getApplicationContext())) {
                            String imageCachePath = PictureUtil.saveImageHomeContentIcon(aHomePostCommentActivity.this, resource, model.getAccount_id() + "_icon", eventId);
                            db.saveHomeContentIcon(imageCachePath, accountId, eventId, model.getId());
                        }*/
                    }
                });

        adapter = new HomeContentCommentAdapter(this, menuUi, db.getTheEvent(eventId), db.getOneListEvent(eventId, accountId), accountId, eventId, new HomeContentCommentAdapter.HomeContentCommentListener() {
            @Override
            public void onDelete(String commentId, int position) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(aHomePostCommentActivity.this);
                alertDialogBuilder.setMessage("Are you sure to delete this post ?");
                alertDialogBuilder.setCancelable(true);
                alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        callDeleteComment(commentId, position);
                    }
                });
                alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                    }
                });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();

            }
        });
        rvCommentfvbi.setNestedScrollingEnabled(false);
        rvCommentfvbi.setAdapter(adapter);
        rvCommentfvbi.setLayoutManager(new LinearLayoutManager(this));

        callGetCommentList();
    }

    private void initListener() {
        ivCommentPostfvbi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (NetworkUtil.isConnected(getApplicationContext())) {
                    if (!InputValidUtil.isEmptyField(etCommentPostfvbi)) {
                        callSetComment();
                    } else {
                        InputValidUtil.errorET(etCommentPostfvbi, "This Empty");
                    }
                } else {
                    Snackbar.make(findViewById(android.R.id.content), "Check you connection", Snackbar.LENGTH_SHORT).show();
                }

            }
        });
        srlCommentfvbi.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                srlCommentfvbi.setRefreshing(false);
                callGetCommentList();//swipe refresh
            }
        });
        nsvCommentfvbi.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
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

                        llLoadModecommentfvbi.setVisibility(View.VISIBLE);

                        new Handler().postDelayed(new Runnable() {
                            public void run() {
                                //code here
                                nsvCommentfvbi.setNestedScrollingEnabled(false);
                                callGetCommentListMore();
                            }
                        }, 500);

                    } else {
                        Snackbar.make(findViewById(android.R.id.content), "no more data", Snackbar.LENGTH_SHORT).show();
                    }
                }

                /*int measuredHeight = homeIVEventBackgroundfvbi.getMeasuredHeight();
                int measuredHeight1 = homeLLWriteSomethingfvbi.getMeasuredHeight();
                int measuredHeight2 = 0;
                for (int i = 0; i < recyclerView.getChildCount(); i++) {
                    measuredHeight2 = measuredHeight2 + recyclerView.getChildAt(i).getMeasuredHeight();
                }
                heightRecycle = measuredHeight + measuredHeight1 + measuredHeight2;*/

                /*if (scrollY > measuredHeight + measuredHeight1 + measuredHeight2) {
                    homeFABHomeUpfvbi.setVisibility(View.VISIBLE);
                } else {
                    homeFABHomeUpfvbi.setVisibility(View.GONE);
                }*/
            }
        });

    }

    private void callGetCommentList() {
        if (srlCommentfvbi.isRefreshing()) {
            srlCommentfvbi.setRefreshing(false);
        }
        if (NetworkUtil.isConnected(getApplicationContext())) {
            ProgressDialog progressDialog = ProgressDialog.show(aHomePostCommentActivity.this, "Loading...", "Please Wait..", false, false);

            HomeContentPostCommentGetRequestModel rm = new HomeContentPostCommentGetRequestModel();
            rm.setEvent_id(eventId);
            rm.setPost_id(model.getId());
            rm.setAccount_id(accountId);
            rm.setId_comment("");
            rm.setDbver(String.valueOf(IConfig.DB_Version));

            API.dohomeContentPostCommentGetRet(rm).enqueue(new Callback<List<HomeContentPostCommentGetResponseModel>>() {
                @Override
                public void onResponse(Call<List<HomeContentPostCommentGetResponseModel>> call, Response<List<HomeContentPostCommentGetResponseModel>> response) {
                    progressDialog.dismiss();
                    if (response.isSuccessful()) {
                        List<HomeContentPostCommentGetResponseModel> models = response.body();
                        adapter.setData(models);
                        db.deleteAllDataComment(eventId, model.getId());
                        for (int i = 0; i < models.size(); i++) {
                            HomeContentPostCommentGetResponseModel responseModel = models.get(i);
                            db.saveComment(responseModel, eventId, model.getId());
                            /*if (db.isDataTableValueMultipleNull(SQLiteHelper.TableComment, SQLiteHelper.Key_Comment_EventId, SQLiteHelper.Key_Comment_Id, eventId, responseModel.getId())) {
                                db.saveComment(responseModel, eventId, model.getId());
                            } else {
                                db.saveComment(responseModel, eventId, model.getId());
                                //db.updateComment(responseModel, eventId, model.getId());
                            }*/
                        }

                        if (models.size() == 10) {
                            last_data = false;
                            last_id = models.get(models.size() - 1).getId();
                            first_id = models.get(0).getId();
                        } else {
                            last_data = true;
                            last_id = "";
                            last_date = "";
                            first_id = "";
                        }

                        if (models.size() != 0) {
                            for (int i = 0; i < models.size(); i++) {
                                HomeContentPostCommentGetResponseModel w = models.get(i);
                                total_comment = w.getTotal_comment();
                            }
                        } else {
                            total_comment = "0";
                        }
                    } else {
                        Log.d("Lihat", "onFailure aHomePostCommentActivity : " + response.message());
                        Snackbar.make(findViewById(android.R.id.content), "Failed Connection To Server", Snackbar.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<List<HomeContentPostCommentGetResponseModel>> call, Throwable t) {
                    progressDialog.dismiss();
                    //Crashlytics.logException(new Exception(t.getMessage()));
                    Log.d("Lihat", "onFailure aHomePostCommentActivity : " + t.getMessage());
                    Snackbar.make(findViewById(android.R.id.content), "Failed Connection To Server", Snackbar.LENGTH_SHORT).show();
                }
            });
        } else {
            ArrayList<HomeContentPostCommentGetResponseModel> comments = db.getComment10(eventId, model.getId());
            if (comments.size() != 0) {
                adapter.setData(comments);
                last_data = false;
                last_id = models.get(models.size() - 1).getId();
                first_id = models.get(0).getId();
            } else {
                Snackbar.make(findViewById(android.R.id.content), "No data", Snackbar.LENGTH_SHORT).show();
            }
        }

    }

    private void callGetCommentListMore() {
        if (NetworkUtil.isConnected(getApplicationContext())) {
            HomeContentPostCommentGetRequestModel rm = new HomeContentPostCommentGetRequestModel();
            rm.setEvent_id(eventId);
            rm.setPost_id(model.getId());
            rm.setAccount_id(accountId);
            rm.setId_comment(last_id);
            rm.setDbver(String.valueOf(IConfig.DB_Version));

            API.dohomeContentPostCommentGetRet(rm).enqueue(new Callback<List<HomeContentPostCommentGetResponseModel>>() {
                @Override
                public void onResponse(Call<List<HomeContentPostCommentGetResponseModel>> call, Response<List<HomeContentPostCommentGetResponseModel>> response) {
                    llLoadModecommentfvbi.setVisibility(View.GONE);
                    if (response.isSuccessful()) {
                        List<HomeContentPostCommentGetResponseModel> q = response.body();
                        adapter.addDatas(q);
                        if (q.size() == 10) {
                            last_data = false;
                            last_id = q.get(q.size() - 1).getId();
                            first_id = q.get(0).getId();
                        } else {
                            last_data = true;
                            last_id = "";
                            last_date = "";
                            first_id = "";
                        }
                        if (q.size() != 0) {
                            for (int i = 0; i < q.size(); i++) {
                                HomeContentPostCommentGetResponseModel w = q.get(i);
                                total_comment = w.getTotal_comment();
                            }
                        } else {
                            total_comment = "0";
                        }
                    } else {
                        Log.d("Lihat", "onFailure aHomePostCommentActivity : " + response.message());
                        Snackbar.make(findViewById(android.R.id.content), "Failed Connection To Server", Snackbar.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<List<HomeContentPostCommentGetResponseModel>> call, Throwable t) {
                    llLoadModecommentfvbi.setVisibility(View.GONE);
                    //Crashlytics.logException(new Exception(t.getMessage()));
                    Log.d("Lihat", "onFailure aHomePostCommentActivity : " + t.getMessage());
                    Snackbar.make(findViewById(android.R.id.content), "Failed Connection To Server", Snackbar.LENGTH_SHORT).show();
                }
            });
        } else {
            llLoadModecommentfvbi.setVisibility(View.GONE);
            Snackbar.make(findViewById(android.R.id.content), "Check you connection", Snackbar.LENGTH_SHORT).show();
        }

    }

    private void callSetComment() {
        SystemUtil.hideKeyBoard(this);

        /*Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String formattedDate = df.format(c.getTime());
        SimpleDateFormat dfGMT = new SimpleDateFormat("z");
        String formattedDateGMT = dfGMT.format(c.getTime()).substring(3, 6);*/

        Date date = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String formattedDate = df.format(date.getTime());
        SimpleDateFormat dfGMT = new SimpleDateFormat("z");
        String formattedDateGMT = dfGMT.format(date.getTime()).substring(3, 6);

        ProgressDialog progressDialog = ProgressDialog.show(aHomePostCommentActivity.this, "Loading...", "Please Wait..", false, false);

        HomeContentPostCommentSetRequestModel requestModel = new HomeContentPostCommentSetRequestModel();
        requestModel.setEvent_id(eventId);
        requestModel.setPost_id(model.getId());
        requestModel.setAccount_id(accountId);
        requestModel.setPost_comment(etCommentPostfvbi.getText().toString().trim());
        requestModel.setPost_time(formattedDate);
        //requestModel.setTimezone(formattedDateGMT);
        requestModel.setTimezone("");

        requestModel.setDbver(String.valueOf(IConfig.DB_Version));
        API.doHomeContentPostCommentRet(requestModel).enqueue(new Callback<List<HomeContentPostCommentSetResponseModel>>() {
            @Override
            public void onResponse(Call<List<HomeContentPostCommentSetResponseModel>> call, Response<List<HomeContentPostCommentSetResponseModel>> response) {
                progressDialog.dismiss();
                List<HomeContentPostCommentSetResponseModel> models = response.body();
                if (models.size() == 1) {

                    etCommentPostfvbi.setText("");

                    callGetCommentList();

                    for (int i1 = 0; i1 < models.size(); i1++) {
                        HomeContentPostCommentSetResponseModel q = models.get(i1);

                    /*HomeContentPostCommentGetResponseModel mo = new HomeContentPostCommentGetResponseModel();
                    mo.setId(q.getId());
                    mo.setAccount_id(q.getAccount_id());
                    mo.setTotal_comment(q.getTotal_comment());
                    mo.setImage_icon(q.getImage_icon());
                    mo.setFull_name(q.getFull_name());
                    mo.setRole_name(q.getRole_name());
                    mo.setPost_time(q.getPost_time());
                    mo.setPost_content(q.getPost_content());
                    adapter.addDataFirst(mo);*/
                    }
                }
            }

            @Override
            public void onFailure(Call<List<HomeContentPostCommentSetResponseModel>> call, Throwable t) {
                progressDialog.dismiss();
                Log.d("Lihat", "onFailure aHomePostCommentActivity : " + t.getMessage());
            }
        });

    }

    private void callDeleteComment(String commentId, int position) {
        Log.d("Lihat", "callDeleteComment aHomePostCommentActivity : " + commentId);
        Log.d("Lihat", "callDeleteComment aHomePostCommentActivity : " + position);

        if (NetworkUtil.isConnected(getApplicationContext())) {
            ProgressDialog progressDialog = ProgressDialog.show(aHomePostCommentActivity.this, "Loading...", "Please Wait..", false, false);

            HomeContentPostCommentDeleteRequestModel rm = new HomeContentPostCommentDeleteRequestModel();
            rm.setEvent_id(eventId);
            rm.setPost_id(model.getId());
            rm.setAccount_id(accountId);
            rm.setId_comment(commentId);
            rm.setDbver(String.valueOf(IConfig.DB_Version));

            API.dohomeContentPostCommentDeleteRet(rm).enqueue(new Callback<List<HomeContentPostCommentDeleteResponseModel>>() {
                @Override
                public void onResponse(Call<List<HomeContentPostCommentDeleteResponseModel>> call, Response<List<HomeContentPostCommentDeleteResponseModel>> response) {
                    progressDialog.dismiss();
                    if (response.isSuccessful()) {
                        List<HomeContentPostCommentDeleteResponseModel> modeld = response.body();
                        for (int i = 0; i < modeld.size(); i++) {
                            HomeContentPostCommentDeleteResponseModel q = modeld.get(i);
                            if (q.getStatus().equalsIgnoreCase(ISeasonConfig.SUCCESS)) {
                                //adapter.removeAt(position);
                                callGetCommentList();
                                Snackbar.make(findViewById(android.R.id.content), q.getStatus(), Snackbar.LENGTH_LONG).show();
                            } else {
                                Snackbar.make(findViewById(android.R.id.content), q.getStatus(), Snackbar.LENGTH_LONG).show();
                            }
                        }
                    } else {
                        Log.d("Lihat", "onFailure aHomePostCommentActivity : " + response.message());
                        Snackbar.make(findViewById(android.R.id.content), "Failed Connection To Server", Snackbar.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<List<HomeContentPostCommentDeleteResponseModel>> call, Throwable t) {
                    progressDialog.dismiss();
                    Log.d("Lihat", "onFailure aHomePostCommentActivity : " + t.getMessage());
                    Snackbar.make(findViewById(android.R.id.content), "Failed Connection To Server", Snackbar.LENGTH_SHORT).show();
                }
            });
        } else {
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
        onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        //dialogBack();
        Intent intent = new Intent();
        intent.putExtra(ISeasonConfig.INTENT_PARAM, position);
        intent.putExtra(ISeasonConfig.INTENT_PARAM2, total_comment);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }

    private void dialogBack() {
        // Munculkan alert dialog apabila user ingin keluar aplikasi
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Apakah Kamu Yakin Tidak Meneruskan Komentar?");
        alertDialogBuilder.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        finish();
                    }
                });
        // Pilihan jika NO
        alertDialogBuilder.setNegativeButton("No",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                    }
                });
        // Tampilkan alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}
