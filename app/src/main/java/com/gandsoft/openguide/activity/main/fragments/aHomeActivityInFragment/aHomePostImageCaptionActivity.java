package com.gandsoft.openguide.activity.main.fragments.aHomeActivityInFragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.gandsoft.openguide.API.API;
import com.gandsoft.openguide.API.APIrequest.HomeContent.HomeContentPostImageCaptionRequestModel;
import com.gandsoft.openguide.API.APIresponse.LocalBaseResponseModel;
import com.gandsoft.openguide.ISeasonConfig;
import com.gandsoft.openguide.R;
import com.gandsoft.openguide.support.SessionUtil;
import com.gandsoft.openguide.support.SystemUtil;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class aHomePostImageCaptionActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private ActionBar actionbar;

    private ImageView mIvImagePostPicture, mIvImagePostOpenCamera, mIvImagePostSend;
    private EditText mEtImagePostWrite;

    private String accountId,eventId;
    private String uniqueId = null;
    private String base64pic= "a";

    private Uri imageUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a_home_post_image_caption);

        summonToolbar("Post Image");
        if(base64pic.equals("a")){
            ContentValues values = new ContentValues();
            values.put(MediaStore.Images.Media.TITLE, "New Picture");
            values.put(MediaStore.Images.Media.DESCRIPTION, "From your Camera");
            imageUri = getContentResolver().insert(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            startActivityForResult(intent, 1);
        }

        initCheck();
        initComponent();
        initContent();
        initListener();
    }

    private void summonToolbar(String title){
        toolbar  = findViewById(R.id.toolbar);
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
        mIvImagePostPicture = findViewById(R.id.ivImagePostPicture);
        mIvImagePostOpenCamera = findViewById(R.id.ivImagePostOpenCamera);
        mIvImagePostSend = findViewById(R.id.ivImagePostSend);

        mEtImagePostWrite = findViewById(R.id.etImagePostWrite);
    }

    private void initContent() {

    }

    private void initListener() {
        mIvImagePostOpenCamera.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.TITLE, "New Picture");
                values.put(MediaStore.Images.Media.DESCRIPTION, "From your Camera");
                imageUri = getContentResolver().insert(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                startActivityForResult(intent, 1);
            }
        });
        mIvImagePostSend.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                postImageCaption();
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.TITLE, "New Picture");
                values.put(MediaStore.Images.Media.DESCRIPTION, "From your Camera");
                imageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                startActivityForResult(intent, 1);
            }
        });
    }

    private void postImageCaption(){
        if(uniqueId == null) {
            uniqueId = UUID.randomUUID().toString();
        }


        HomeContentPostImageCaptionRequestModel requestModel = new HomeContentPostImageCaptionRequestModel();
        requestModel.setId_event(eventId);
        requestModel.setAccount_id(accountId);
        requestModel.setId_postedhome(uniqueId);
        requestModel.setCaptions(mEtImagePostWrite.getText().toString());
        requestModel.setGmt_date("");
        requestModel.setDate_post("");
        requestModel.setImagedata(base64pic);
        requestModel.setDegree("");
        requestModel.setDbver("3");
        Log.d("uuidnya",uniqueId);


        API.doHomeContentPostImageCaptionRet(requestModel).enqueue(new Callback<List<LocalBaseResponseModel>>() {
            @Override
            public void onResponse(Call<List<LocalBaseResponseModel>> call, Response<List<LocalBaseResponseModel>> response) {
                if (response.isSuccessful()) {
                    List<LocalBaseResponseModel> s = response.body();
                    if (s.size() == 1) {
                        for (int i = 0; i < s.size(); i++) {
                            LocalBaseResponseModel model = s.get(i);
                            if (model.getStatus().equalsIgnoreCase("ok")) {
                                Snackbar.make(findViewById(android.R.id.content), "Image Post Tersimpan", Snackbar.LENGTH_LONG).show();
                                finish();
                            } else {
                                Snackbar.make(findViewById(android.R.id.content), "Image Post Bad Response", Snackbar.LENGTH_LONG).show();

                                finish();
                            }
                        }
                    } else {
                        Snackbar.make(findViewById(android.R.id.content), "Image Post Data Tidak Sesuai", Snackbar.LENGTH_LONG).show();

                        finish();
                    }
                } else {
                    Snackbar.make(findViewById(android.R.id.content), response.message(), Snackbar.LENGTH_LONG).show();

                    finish();

                }
            }

            @Override
            public void onFailure(Call<List<LocalBaseResponseModel>> call, Throwable t) {
                Snackbar.make(findViewById(android.R.id.content), t.getMessage(), Snackbar.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            Bitmap bitmap = null;
            Bitmap rotatedBitmap = null;
            Bitmap resizedRotatedBitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                String imageurl = String.valueOf(imageUri);
                Log.d("image uri ",imageurl);
                rotatedBitmap = SystemUtil.rotateImage(bitmap, 90);

                createDirectoryAndSaveFile(rotatedBitmap,"temp.png");
                mIvImagePostPicture.setImageBitmap(rotatedBitmap);

                resizedRotatedBitmap = SystemUtil.resizeImage(rotatedBitmap,720);

                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                resizedRotatedBitmap.compress(Bitmap.CompressFormat.PNG, 0, byteArrayOutputStream);
                byte[] byteArray = byteArrayOutputStream.toByteArray();

                base64pic = Base64.encodeToString(byteArray, Base64.DEFAULT);
                if(base64pic.isEmpty() || base64pic.equals("a")){
                    finish();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void createDirectoryAndSaveFile(Bitmap imageToSave, String fileName) {
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/.Gandsoft/" + eventId+"/"+ fileName);
        if (file.exists()) {
            file.delete();
        }
        try {
            FileOutputStream out = new FileOutputStream(file);
            imageToSave.compress(Bitmap.CompressFormat.PNG, 0, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
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
