package com.gandsoft.openguide.activity.main.fragments.aHomeActivityInFragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
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
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.gandsoft.openguide.API.APIresponse.HomeContent.HomeContentCommentModel;
import com.gandsoft.openguide.ISeasonConfig;
import com.gandsoft.openguide.R;
import com.gandsoft.openguide.support.SessionUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class aHomePostCommentActivity extends AppCompatActivity {
    private RecyclerView rvCommentfvbi;
    private EditText etCommentPostfvbi;
    private ImageView ivCommentPostfvbi, ivCommentTsIconfvbi, ivCommentTsImagefvbi;
    private TextView tvCommentTsKeteranganfvbi, tvCommentTsTimefvbi, tvCommentTsUsernamefvbi;
    private Toolbar toolbar;
    private ActionBar ab;

    private String accountId, eventId;
    private ArrayList<HomeContentCommentModel> models = new ArrayList<>();
    private HomeContentCommentModel model;

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
        ab = getSupportActionBar();
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
    }

    private void initListener() {
        ivCommentPostfvbi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Calendar c = Calendar.getInstance();
                SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                String formattedDate = df.format(c.getTime());
                SimpleDateFormat dfGMT = new SimpleDateFormat("z");
                String formattedDateGMT = dfGMT.format(c.getTime());
                Log.d("Lihat", "onClick aHomePostCommentActivity : " + formattedDate);
                Log.d("Lihat", "onClick aHomePostCommentActivity : " + formattedDateGMT);
                Log.d("Lihat", "onClick aHomePostCommentActivity : " + formattedDateGMT.substring(3, 6));

                /*ProgressDialog progressDialog = ProgressDialog.show(aHomePostCommentActivity.this, "Loading...", "Please Wait..", false, false);

                HomeContentPostCommentRequestModel requestModel = new HomeContentPostCommentRequestModel();
                requestModel.setEvent_id(eventId);
                requestModel.setPost_id(model.getId());
                requestModel.setAccount_id(accountId);
                requestModel.setPost_comment(etCommentPostfvbi.getText().toString().trim());
                requestModel.setPost_time("");
                requestModel.setTimezone("+07");
                requestModel.setDbver(String.valueOf(IConfig.DB_Version));
                API.doHomeContentPostCommentRet(requestModel).enqueue(new Callback<List<LocalBaseResponseModel>>() {
                    @Override
                    public void onResponse(Call<List<LocalBaseResponseModel>> call, Response<List<LocalBaseResponseModel>> response) {
                        progressDialog.dismiss();
                    }

                    @Override
                    public void onFailure(Call<List<LocalBaseResponseModel>> call, Throwable t) {
                        progressDialog.dismiss();
                    }
                });*/
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
