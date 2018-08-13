package com.gandsoft.openguide.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.FutureTarget;
import com.gandsoft.openguide.API.API;
import com.gandsoft.openguide.API.APIrequest.UserData.UserDataRequestModel;
import com.gandsoft.openguide.API.APIresponse.UserData.UserDataResponseModel;
import com.gandsoft.openguide.API.APIresponse.UserData.UserListEventResponseModel;
import com.gandsoft.openguide.API.APIresponse.UserData.UserWalletDataResponseModel;
import com.gandsoft.openguide.IConfig;
import com.gandsoft.openguide.ISeasonConfig;
import com.gandsoft.openguide.R;
import com.gandsoft.openguide.database.SQLiteHelper;
import com.gandsoft.openguide.support.NetworkUtil;
import com.gandsoft.openguide.support.SessionUtil;

import java.io.File;
import java.util.List;
import java.util.concurrent.ExecutionException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangeEventActivity extends AppCompatActivity {
    SQLiteHelper db = new SQLiteHelper(this);

    private ImageView ceIVUserPicfvbi;
    private TextView ceTVUserNamefvbi, ceTVUserIdfvbi, ceTVInfofvbi, ceTVVersionAppfvbi;
    private LinearLayout ceLLOngoingEventfvbi, ceLLPastEventfvbi;
    private RecyclerView ceRVOngoingEventfvbi, ceRVPastEventfvbi;
    private String appVersionName, appVersionCode, appName, appPkg;
    private Button ceBUserAccountfvbi;
    private String accountid;
    private SwipeRefreshLayout srlChangeEventActivityfvbi;
    /**/
    private int version_data_user;
    private List<UserListEventResponseModel> menuUi;
    private ChangeEventOnGoingAdapter adapterOnGoing;
    private ChangeEventPastAdapter adapterPast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_event);

        accountid = SessionUtil.getStringPreferences(ISeasonConfig.KEY_ACCOUNT_ID, null);

        initComponent();
        initContent();
        initListener();
    }

    private void initComponent() {
        srlChangeEventActivityfvbi = (SwipeRefreshLayout) findViewById(R.id.srlChangeEventActivity);
        ceIVUserPicfvbi = (ImageView) findViewById(R.id.ceIVUserPic);
        ceTVUserNamefvbi = (TextView) findViewById(R.id.ceTVUserName);
        ceTVUserIdfvbi = (TextView) findViewById(R.id.ceTVUserId);
        ceTVInfofvbi = (TextView) findViewById(R.id.ceTVInfo);
        ceTVVersionAppfvbi = (TextView) findViewById(R.id.ceTVVersionApp);
        ceBUserAccountfvbi = (Button) findViewById(R.id.ceBUserAccount);
        ceLLOngoingEventfvbi = (LinearLayout) findViewById(R.id.ceLLOngoingEvent);
        ceLLPastEventfvbi = (LinearLayout) findViewById(R.id.ceLLPastEvent);
        ceRVOngoingEventfvbi = (RecyclerView) findViewById(R.id.ceRVOngoingEvent);
        ceRVPastEventfvbi = (RecyclerView) findViewById(R.id.ceRVPastEvent);
    }

    private void initContent() {
        initRecycleView();

        if (SessionUtil.checkIfExist(ISeasonConfig.KEY_ACCOUNT_ID)) {
            if (accountid != null) {
                getUserDataValidation();
            }
        }

        initVersionDataUser();

        customText(ceTVInfofvbi);

        //getversion
        try {
            appVersionName = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
            appVersionCode = String.valueOf(getPackageManager().getPackageInfo(getPackageName(), 0).versionCode);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        appName = getResources().getString(R.string.app_name);
        appPkg = this.getBaseContext().getPackageName();
        ceTVVersionAppfvbi.setText(getString(R.string.app_name) + " - v " + appVersionName + "." + appVersionCode + "" +
                "\n Powered by Gandsoft");
    }

    private void initListener() {
        ceBUserAccountfvbi.setOnClickListener(this::Onclick);
        srlChangeEventActivityfvbi.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getUserDataValidation();
            }
        });
    }

    private void getUserDataValidation() {
        if (NetworkUtil.isConnected(this)) {
            getUserDataDo(accountid);
        } else {
            if (db.checkDataTable(SQLiteHelper.TableUserData, SQLiteHelper.KEY_UserData_accountId, accountid)) {
                updateRecycleView(db.getAllListEvent(accountid));
                updateUserInfo(db.getUserData(accountid));
                Snackbar.make(findViewById(android.R.id.content), "Memunculkan Data Offline", Snackbar.LENGTH_LONG).show();
            } else {
                Snackbar.make(findViewById(android.R.id.content), "Data Bermasalah", Snackbar.LENGTH_LONG).show();
            }
        }
    }

    private void getUserDataDo(String accountid) {
        if (srlChangeEventActivityfvbi.isRefreshing()) {
            srlChangeEventActivityfvbi.setRefreshing(false);
        }

        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setTitle("Get data from server");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        // show it
        progressDialog.show();

        //ProgressDialog progressDialog = ProgressDialog.show(ChangeEventActivity.this, "Loading...", "Please Wait..", true, false);

        new Handler().postDelayed(new Runnable() {
            public void run() {
                //code here
                API.doUserDataRet(new UserDataRequestModel(accountid, IConfig.DB_Version, version_data_user)).enqueue(new Callback<List<UserDataResponseModel>>() {
                    @Override
                    public void onResponse(Call<List<UserDataResponseModel>> call, Response<List<UserDataResponseModel>> response) {
                        progressDialog.dismiss();
                        if (response.isSuccessful()) {

                            List<UserDataResponseModel> userDataResponseModels = response.body();
                            for (int i = 0; i < userDataResponseModels.size(); i++) {
                                UserDataResponseModel model = userDataResponseModels.get(i);
                                if (db.checkDataTable(SQLiteHelper.TableUserData, SQLiteHelper.KEY_UserData_accountId, accountid)) {
                                    db.updateUserData(model, accountid);
                                } else {
                                    db.saveUserData(model);
                                }
                                updateUserInfo(userDataResponseModels);
                                //db.saveImageUserToLocal(getImgCachePath(model.getImage_url()),accountid);

                                List<UserListEventResponseModel> userListEventResponseModels = model.getList_event();
                                for (int j = 0; j < userListEventResponseModels.size(); j++) {
                                    UserListEventResponseModel model1 = userListEventResponseModels.get(j);
                                    if (db.checkDataTable(SQLiteHelper.TableListEvent, SQLiteHelper.KEY_ListEvent_eventId, model1.event_id)) {
                                        db.updateListEvent(model1);
                                    } else {
                                        db.saveListEvent(model1, accountid);
                                    }
                                    updateRecycleView(userListEventResponseModels);
                                    //db.saveImageListEventToLocal(getImgCachePath(model1.getBackground()),getImgCachePath(model1.getLogo()),model1);

                                    List<UserWalletDataResponseModel> userWalletDataResponseModels = model1.getWallet_data();
                                    for (int n = 0; n < userWalletDataResponseModels.size(); n++) {
                                        UserWalletDataResponseModel model2 = userWalletDataResponseModels.get(n);
                                        if (db.checkDataTableKeyMultiple(SQLiteHelper.TableWallet, SQLiteHelper.KEY_Wallet_sort, SQLiteHelper.KEY_Wallet_eventId, model2.getSort(), model1.getEvent_id())) {
                                            db.updateWalletData(model2, model1.getEvent_id());
                                        } else {
                                            db.saveWalletData(model2, accountid, model1.getEvent_id());
                                        }
                                    }
                                }
                            }
                        } else {
                            Log.e("Lihat", "onResponse ChangeEventActivity : " + response.message());
                        }
                    }

                    @Override
                    public void onFailure(Call<List<UserDataResponseModel>> call, Throwable t) {
                        progressDialog.dismiss();
                        Snackbar.make(findViewById(android.R.id.content), "Tidak Dapat terhubung dengan server", Snackbar.LENGTH_LONG).setAction("Reload", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                getUserDataValidation();
                            }
                        }).show();
                        updateRecycleView(db.getAllListEvent(accountid));
                        updateUserInfo(db.getUserData(accountid));
                        Log.e("Lihat", "onFailure ChangeEventActivity : " + t.getMessage(), t);
                    }
                });

            }
        }, 1000);

    }

    private void initVersionDataUser() {
        if (db.checkDataTableNull(SQLiteHelper.TableGlobalData, SQLiteHelper.KEY_GlobalData_version_data_user)) {
            version_data_user = 0;
        } else {
            version_data_user = db.getVersionDataIdUser(accountid);
        }
    }

    private void initRecycleView() {
        adapterOnGoing = new ChangeEventOnGoingAdapter(this, menuUi);
        adapterPast = new ChangeEventPastAdapter(this, menuUi);
        ceRVOngoingEventfvbi.setNestedScrollingEnabled(false);
        ceRVPastEventfvbi.setNestedScrollingEnabled(false);
        ceRVOngoingEventfvbi.setAdapter(adapterOnGoing);
        ceRVPastEventfvbi.setAdapter(adapterPast);
        ceRVOngoingEventfvbi.setLayoutManager(new LinearLayoutManager(this));
        ceRVPastEventfvbi.setLayoutManager(new LinearLayoutManager(this));
    }

    private void updateUserInfo(List<UserDataResponseModel> models) {
        if (models.size() == 1) {
            for (int i = 0; i < models.size(); i++) {
                UserDataResponseModel model = models.get(i);
                ceTVUserNamefvbi.setText(model.getFull_name());
                ceTVUserIdfvbi.setText(model.getAccount_id());
                Glide.with(getApplicationContext())
                        .load(model.getImage_url())
                        .placeholder(R.drawable.template_account_og)
                        .error(R.drawable.template_account_og)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(ceIVUserPicfvbi);
            }
        }
    }

    private void updateRecycleView(List<UserListEventResponseModel> models) {
        for (int i = 0; i < models.size(); i++) {
            UserListEventResponseModel model = models.get(i);
            Log.d("Lihat", "updateRecycleView ChangeEventActivity : " + models.size());
            Log.d("Lihat", "updateRecycleView ChangeEventActivity : " + model.getStatus());
            if (model.getStatus().equalsIgnoreCase("PAST EVENT")) {
                ceLLPastEventfvbi.setVisibility(View.VISIBLE);

            } else if (model.getStatus().equalsIgnoreCase("ON GOING")) {
                ceLLOngoingEventfvbi.setVisibility(View.VISIBLE);
            }
        }
        adapterPast.setData(models);
        adapterPast.notifyDataSetChanged();
        adapterOnGoing.setData(models);
        adapterOnGoing.notifyDataSetChanged();

    }

    private void Onclick(View view) {
        if (view == ceBUserAccountfvbi) {
            startActivity(AccountActivity.getActIntent(ChangeEventActivity.this));
        }
    }

    private void customText(TextView view) {
        SpannableStringBuilder spanTxt = new SpannableStringBuilder("To create an event, please contact our phone number: ");

        spanTxt.append("+62 21 53661536");
        spanTxt.setSpan(new ClickableSpan() {
                            @Override
                            public void onClick(View widget) {
                                Toast.makeText(getApplicationContext(), "+62 21 53661536 Clicked", Toast.LENGTH_SHORT).show();
                                String phone = "+622153661536";
                                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
                                startActivity(intent);
                            }
                        },
                spanTxt.length() - "+62 21 53661536".length(),
                spanTxt.length(),
                0);

        spanTxt.append(" or by email at: ");
        spanTxt.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorPrimary)), 32, spanTxt.length(), 0);

        spanTxt.append("hello@gandsoft.com");
        spanTxt.setSpan(new ClickableSpan() {
                            @Override
                            public void onClick(View widget) {
                                Toast.makeText(getApplicationContext(), "hello@gandsoft.com Clicked", Toast.LENGTH_SHORT).show();
                                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + "hello@gandsoft.com")); //alamat email tujuan
                                //emailIntent.putExtra(Intent.EXTRA_SUBJECT, ); //subject email
                                startActivity(Intent.createChooser(emailIntent, "Pilih Aplikasi Email"));
                            }
                        },
                spanTxt.length() - "hello@gandsoft.com".length(),
                spanTxt.length(),
                0);

        view.setMovementMethod(LinkMovementMethod.getInstance());
        view.setText(spanTxt, TextView.BufferType.SPANNABLE);
    }

    public static Intent getActIntent(Activity activity) {
        return new Intent(activity, ChangeEventActivity.class);
    }

    private String getImgCachePath(String url) {
        FutureTarget<File> futureTarget = Glide.with(getBaseContext())
                .load(url)
                .downloadOnly(com.bumptech.glide.request.target.Target.SIZE_ORIGINAL, 100);//-1,-1 Real size
        try {
            File file = futureTarget.get();
            String path = file.getAbsolutePath();
            return path;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }
}
