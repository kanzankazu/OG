package com.gandsoft.openguide.activity.main.fragments.aHomeActivityInFragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
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
import com.gandsoft.openguide.support.SessionUtil;
import com.gandsoft.openguide.support.SystemUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
            /*if (models.size() > 0) {
                models.clear();
                models = bundle.getParcelableArrayListExtra(ISeasonConfig.INTENT_PARAM);
            } else {
                models = bundle.getParcelableArrayListExtra(ISeasonConfig.INTENT_PARAM);
            }*/
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

        tvCommentTsUsernamefvbi.setText(model.getUsername());
        tvCommentTsTimefvbi.setText(model.getDate_created());

        if (!TextUtils.isEmpty(model.getKeterangan()) || !TextUtils.isEmpty(model.getEvent())) {
            tvCommentTsKeteranganfvbi.setText(model.getKeterangan() + "" + model.getEvent());
            tvCommentTsKeteranganfvbi.setVisibility(View.VISIBLE);
        } else {
            tvCommentTsKeteranganfvbi.setVisibility(View.GONE);
        }

        Glide.with(this)
                .load(model.getImage_icon())
                .placeholder(R.drawable.template_account_og)
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .error(R.drawable.template_account_og)
                .into(ivCommentTsIconfvbi);

        if (!TextUtils.isEmpty(model.getImage_posted())) {
            Glide.with(this)
                    .load(model.getImage_posted())
                    .placeholder(R.drawable.template_account_og)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .error(R.drawable.template_account_og)
                    .into(ivCommentTsImagefvbi);
        } else {
            ivCommentTsImagefvbi.setVisibility(View.GONE);
        }

        adapter = new HomeContentCommentAdapter(this, menuUi, db.getTheEvent(eventId), db.getOneListEvent(eventId), accountId, eventId, new HomeContentCommentAdapter.HomeContentCommentListener() {
            @Override
            public void onDelete(String commentId, int position) {
                callDeleteComment(commentId, position);
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

                callSetComment();


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
                        }, 1000);

                    } else {
                        Snackbar.make(findViewById(android.R.id.content), "Sudah tidak ada data", Snackbar.LENGTH_SHORT).show();
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
                    List<HomeContentPostCommentGetResponseModel> q = response.body();
                    adapter.setData(q);
                    if (q.size() == 10) {
                        last_data = false;
                        last_id = q.get(q.size() - 1).getId();
                        Log.d("Lihat", "onResponse aHomePostCommentActivity : " + last_id);
                        first_id = q.get(0).getId();
                        Log.d("Lihat", "onResponse aHomePostCommentActivity : " + first_id);
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
                progressDialog.dismiss();
                //Crashlytics.logException(new Exception(t.getMessage()));
                Log.d("Lihat", "onFailure aHomePostCommentActivity : " + t.getMessage());
                Snackbar.make(findViewById(android.R.id.content), "Failed Connection To Server", Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    private void callGetCommentListMore() {
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
        requestModel.setTimezone("+07");

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
