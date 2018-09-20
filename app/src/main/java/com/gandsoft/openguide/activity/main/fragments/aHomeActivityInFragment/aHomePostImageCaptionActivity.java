package com.gandsoft.openguide.activity.main.fragments.aHomeActivityInFragment;

import android.app.Activity;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.gandsoft.openguide.ISeasonConfig;
import com.gandsoft.openguide.R;
import com.gandsoft.openguide.support.PictureUtil;
import com.gandsoft.openguide.support.SessionUtil;

import java.io.File;

public class aHomePostImageCaptionActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private ActionBar actionbar;

    private ImageView mIvImagePostPicture;
    private ImageView mIvImagePostOpenCamera;
    private ImageView mIvImagePostSend;
    private EditText mEtImagePostWrite;

    private String accountId, eventId;
    private String base64;
    private String imageurl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a_home_post_image_caption);

        initToolbar("Post Image");

        openCamera();
        /*if (bitmap == null) {
            ContentValues values = new ContentValues();
            values.put(MediaStore.Images.Media.TITLE, "New Picture");
            values.put(MediaStore.Images.Media.DESCRIPTION, "From your Camera");
            imageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            startActivityForResult(intent, 1);
        }*/

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
            imageurl = bundle.getStringExtra(ISeasonConfig.INTENT_PARAM);
            loadPic(imageurl);
        }
    }

    private void initContent() {

    }

    private void initListener() {
        mIvImagePostOpenCamera.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                openCamera();
                /*ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.TITLE, "New Picture");
                values.put(MediaStore.Images.Media.DESCRIPTION, "From your Camera");
                imageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                startActivityForResult(intent, 1);*/
            }
        });
        mIvImagePostSend.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra(ISeasonConfig.INTENT_PARAM, imageurl);
                intent.putExtra(ISeasonConfig.INTENT_PARAM2, mEtImagePostWrite.getText().toString().trim());
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });
    }

    private void openCamera() {
        /*if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT) {
            imageFilePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/picture.jpg";
            Log.d("Lihat", "openCamera aHomePostImageCaptionActivity : " + imageFilePath);
            File imageFile = new File(imageFilePath);
            Uri imageFileUri = Uri.fromFile(imageFile); // convert path to Uri
            Log.d("Lihat", "openCamera aHomePostImageCaptionActivity : " + imageFileUri);
            Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            cameraIntent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, imageFileUri);
            startActivityForResult(cameraIntent, REQ_CODE_TAKE_PHOTO_INTENT_ID_KITKAT);
        } else {
            Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(cameraIntent, REQ_CODE_TAKE_PHOTO_INTENT_ID);
        }*/

        /*ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "New Picture");
        values.put(MediaStore.Images.Media.DESCRIPTION, "From your Camera");
        imageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, REQ_CODE_TAKE_PHOTO_INTENT_ID_STANDART);*/
    }

    private void loadPic(String imageurl) {
        Log.d("Lihat", "onActivityResult aHomePostImageCaptionActivity : " + imageurl);
        Glide.with(getApplicationContext())
                .load(new File(imageurl))
                .asBitmap()
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        Bitmap resizeImage = PictureUtil.resizeImage(resource, 1080);
                        mIvImagePostPicture.setImageBitmap(resizeImage);
                        base64 = PictureUtil.bitmapToBase64(resizeImage, Bitmap.CompressFormat.JPEG, 100);
                    }
                });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /*if (requestCode == REQ_CODE_TAKE_PHOTO_INTENT_ID_STANDART && resultCode == Activity.RESULT_OK) {
            String imageurl = PictureUtil.getPath(imageUri, this);
            Log.d("Lihat", "onActivityResult aHomePostImageCaptionActivity : " + imageurl);
            mIvImagePostPicture.setScaleType(ImageView.ScaleType.FIT_CENTER);
            Glide.with(getApplicationContext())
                    .load(new File(imageurl))
                    .asBitmap()
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                            Bitmap resizeImage = PictureUtil.resizeImage(resource, 1080);
                            mIvImagePostPicture.setImageBitmap(resizeImage);
                            base64 = PictureUtil.bitmapToBase64(resizeImage, Bitmap.CompressFormat.JPEG, 100);
                        }
                    });
        }*/

        /*if (requestCode == REQ_CODE_TAKE_PHOTO_INTENT_ID) {
            if (resultCode == RESULT_OK) {
                Uri selectedImage = PictureUtil.getUriFromResult(this, resultCode, data.getData());
                Log.d("Lihat", "onActivityResult aHomePostImageCaptionActivity : " + selectedImage);

                try {
                    //save Base64
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                    Bitmap storedata = SystemUtil.resizeImage(bitmap, sizePic);
                    base64 = PictureUtil.bitmapToBase64(storedata, Bitmap.CompressFormat.JPEG, compressPic);
                    Log.d("Lihat", "onActivityResult aHomePostImageCaptionActivity : " + base64);

                    //showImage
                    showPic(base64);
                } catch (Exception e) {
                    Log.d("Lihat", "onActivityResult aHomePostImageCaptionActivity : " + e.getMessage());
                }
            }
        } else if (requestCode == REQ_CODE_TAKE_PHOTO_INTENT_ID_KITKAT) {
            if (resultCode == RESULT_OK) {
                try {
                    BitmapFactory.Options bmpFactoryOptions = new BitmapFactory.Options();
                    bmpFactoryOptions.inJustDecodeBounds = false;

                    //save Base64
                    Bitmap bitmap = BitmapFactory.decodeFile(imageFilePath, bmpFactoryOptions);
                    Bitmap storedata = SystemUtil.resizeImage(bitmap, sizePic);
                    base64 = PictureUtil.bitmapToBase64(storedata, Bitmap.CompressFormat.JPEG, compressPic);
                    Log.d("Lihat", "onActivityResult aHomePostImageCaptionActivity : " + base64);

                    //showImage
                    showPic(base64);
                } catch (Exception e) {
                    Log.d("Lihat", "onActivityResult aHomePostImageCaptionActivity : " + e.getMessage());
                }

            }
        }*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_activity, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        // Munculkan alert dialog apabila user ingin keluar aplikasi
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Apakah Kamu Tidak Ingin Meneruskan Post Image?");
        alertDialogBuilder.setPositiveButton("Ya",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return super.onOptionsItemSelected(item);
    }
}
