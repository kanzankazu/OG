package com.gandsoft.openguide.activity.main.fragments.aHomeActivityInFragment;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.gandsoft.openguide.API.APIresponse.HomeContent.HomeContentResponseModel;
import com.gandsoft.openguide.R;

import java.util.ArrayList;
import java.util.List;

public class aHomePostCommentActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private ActionBar ab;
    private ImageView mIvCommentPostIcon, mIvCommentPostImage,mIvCommentSend;
    private TextView mTvCommentPostUsername, mTvCommentPostTime, mTvCommentPostKeterangan;
    private EditText mEtCommentAdd;
    private int position;
    private List<HomeContentResponseModel> models = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a_home_post_comment);

        summonToolbar("Comments");
        initComponent();
        initContent();
    }

    private void initComponent() {
        position = getIntent().getIntExtra("pos", 0);
        mIvCommentPostIcon = findViewById(R.id.ivCommentPostIcon);
        mIvCommentPostImage = findViewById(R.id.ivCommentPostImage);
        mIvCommentSend = findViewById(R.id.ivCommentSend);

        mTvCommentPostUsername = findViewById(R.id.tvCommentPostUsername);
        mTvCommentPostTime = findViewById(R.id.tvCommentPostTime);
        mTvCommentPostKeterangan = findViewById(R.id.tvCommentPostKeterangan);

        mEtCommentAdd = findViewById(R.id.etCommentAdd);
    }

    private void initContent(){

        HomeContentResponseModel model = models.get(position);
        mTvCommentPostUsername.setText(model.getUsername());
        mTvCommentPostTime.setText(model.getDate_created());
        mTvCommentPostKeterangan.setText(model.getKeterangan());





    }

    public void summonToolbar(String title){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ab = getSupportActionBar();
        ab.setTitle(title);
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
