package com.gandsoft.openguide.view.main.fragments.aHomeActivityInFragment;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.gandsoft.openguide.ISeasonConfig;
import com.gandsoft.openguide.R;
import com.gandsoft.openguide.support.DateTimeUtil;
import com.gandsoft.openguide.support.PictureUtil;
import com.gandsoft.openguide.support.SessionUtil;

import java.io.File;
import java.text.SimpleDateFormat;

public class aHomePostImageCaptionActivity extends AppCompatActivity {
    private static final int REQ_CODE_TAKE_PHOTO_INTENT_ID_STANDART = 1;
    private Toolbar toolbar;
    private ActionBar actionbar;

    private ImageView mIvImagePostPicture;
    private ImageView mIvImagePostOpenCamera;
    private ImageView mIvImagePostSend;
    private EditText mEtImagePostWrite;

    private String accountId, eventId;
    private String imageFilePath;
    private String newImageFilePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a_home_post_image_caption);

        initToolbar("Post Image");

        //openCamera();

        initCheck();
        initParam();
        initComponent();
        initContent();
        initListener();
    }

    private void initToolbar(String title) {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        actionbar = getSupportActionBar();
        actionbar.setTitle(title);
    }

    private void initCheck() {
        if (SessionUtil.checkIfExist(ISeasonConfig.KEY_ACCOUNT_ID)) {
            accountId = SessionUtil.getStringPreferences(ISeasonConfig.KEY_ACCOUNT_ID, null);
        }
        if (SessionUtil.checkIfExist(ISeasonConfig.KEY_EVENT_ID)) {
            eventId = SessionUtil.getStringPreferences(ISeasonConfig.KEY_EVENT_ID, null);
        }
    }

    private void initComponent() {
        mIvImagePostPicture = (ImageView) findViewById(R.id.ivImagePostPicture);
        mIvImagePostOpenCamera = (ImageView) findViewById(R.id.ivImagePostOpenCamera);
        mIvImagePostSend = (ImageView) findViewById(R.id.ivImagePostSend);
        mEtImagePostWrite = (EditText) findViewById(R.id.etImagePostWrite);
    }

    private void initParam() {
        Intent bundle = getIntent();
        if (bundle.hasExtra(ISeasonConfig.INTENT_PARAM)) {
            imageFilePath = bundle.getStringExtra(ISeasonConfig.INTENT_PARAM);
            loadPic(imageFilePath);
            Log.d("Lihat", "initParam aHomePostImageCaptionActivity : " + imageFilePath);
        }
    }

    private void initContent() {

    }

    private void initListener() {
        mIvImagePostOpenCamera.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                openCamera();
            }
        });
        mIvImagePostSend.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra(ISeasonConfig.INTENT_PARAM, imageFilePath);
                intent.putExtra(ISeasonConfig.INTENT_PARAM2, mEtImagePostWrite.getText().toString().trim());
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });
    }

    private void openCamera() {
        if (!TextUtils.isEmpty(imageFilePath)) {
            PictureUtil.removeImageFromPathFile(imageFilePath);
        }

        String date = DateTimeUtil.dateToString(DateTimeUtil.currentDate(), new SimpleDateFormat("ddMMyy_HHmmss"));
        imageFilePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/picture_" + date + ".jpg";
        Uri imageUri = FileProvider.getUriForFile(getApplicationContext(), "com.gandsoft.openguide", new File(imageFilePath));
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(cameraIntent, REQ_CODE_TAKE_PHOTO_INTENT_ID_STANDART);
    }

    private void loadPic(String imageurl) {
        Glide.with(getApplicationContext())
                .load(new File(imageurl))
                .asBitmap()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        Bitmap resizeImage = PictureUtil.resizeImageBitmap(resource, 1080);
                        mIvImagePostPicture.setImageBitmap(resizeImage);
                    }
                });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_CODE_TAKE_PHOTO_INTENT_ID_STANDART && resultCode == Activity.RESULT_OK) {
            Uri imageUri1 = Uri.parse(imageFilePath);
            loadPic(imageUri1.getPath());
            Log.d("Lihat", "onActivityResult aHomePostImageCaptionActivity : " + imageUri1.getPath());
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
        if (item.getItemId() == R.id.action_close) {
            dialogQuit();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        dialogQuit();
    }

    private void dialogQuit() {
        // Munculkan alert dialog apabila user ingin keluar aplikasi
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Apakah Kamu Tidak Ingin Meneruskan Post Image?");
        alertDialogBuilder.setPositiveButton("Ya",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        if (!TextUtils.isEmpty(imageFilePath)) {
                            PictureUtil.removeImageFromPathFile(imageFilePath);
                        }

                        Intent intent = new Intent();
                        setResult(Activity.RESULT_CANCELED, intent);
                        finish();
                    }
                });
        // Pilihan jika NO
        alertDialogBuilder.setNegativeButton("Tidak",
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
