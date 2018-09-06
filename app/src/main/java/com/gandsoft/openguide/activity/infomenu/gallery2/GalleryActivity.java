package com.gandsoft.openguide.activity.infomenu.gallery2;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.gandsoft.openguide.API.API;
import com.gandsoft.openguide.API.APIrequest.Gallery.GalleryRequestModel;
import com.gandsoft.openguide.API.APIresponse.Gallery.GalleryResponseModel;
import com.gandsoft.openguide.API.APIresponse.UserData.UserDataResponseModel;
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

    GalleryAdapter mAdapter;
    RecyclerView mRecyclerView;

    private Toolbar toolbar;
    private ActionBar ab;
    ArrayList<ImageModel> data = new ArrayList<>();
    ArrayList<String> url = new ArrayList<>();

    private GalleryAdapter adapter;
    private List<GalleryResponseModel> menuUi = new ArrayList<>();

    private String accountId, eventId;
    private String isKondisi = "up";
    private String last_id = "";
    private String first_id = "";
    private String last_date = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        checkSession();
        summonToolbar("Gallery");
        getGalleryData();

        initComponent();
        initContent();
        initListener();

        for(int i = 0; i<db.getGallery(eventId).size();i++) {
            ImageModel imageModel = new ImageModel();
            imageModel.setName(db.getGallery(eventId).get(i).getUsername());
            imageModel.setCaption(db.getGallery(eventId).get(i).getCaption());
            imageModel.setStatlike(db.getGallery(eventId).get(i).getStatus_like());
            imageModel.setLike(db.getGallery(eventId).get(i).getLike());
            imageModel.setTotcom(db.getGallery(eventId).get(i).getTotal_comment());
            imageModel.setUrl(db.getGallery(eventId).get(i).getImage_posted());
            data.add(imageModel);
        }

        mRecyclerView = (RecyclerView) findViewById(R.id.list);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        mRecyclerView.setHasFixedSize(true);

        mAdapter = new GalleryAdapter(GalleryActivity.this, data,eventId);
        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this,
                new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Intent intent = new Intent(GalleryActivity.this, DetailActivity.class);
                        intent.putParcelableArrayListExtra("data", data);
                        intent.putExtra("pos", position);
                        startActivity(intent);
                    }
                }));
    }
    private void initComponent(){

    }
    private void initContent(){}
    private void initListener(){}
    private void getGalleryData() {
        GalleryRequestModel requestModel = new GalleryRequestModel();

        requestModel.setDbver("3");
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
                    last_id = models.get(9).getId();
                    first_id = models.get(0).getId();
                    for(int i=0;i<models.size();i++) {
                        GalleryResponseModel model = models.get(i);
                        if (db.isDataTableValueMultipleNull(SQLiteHelper.TableGallery, SQLiteHelper.Key_Gallery_eventId,
                                SQLiteHelper.Key_Gallery_galleryId, eventId, model.getId())) {
                            db.saveGallery(model, eventId);
                        } else {
                            db.updateGallery(model, eventId);
                        }
/*                        Log.d("model value", String.valueOf(models.get(i).getImage_posted()));
                        ImageModel imageModel = new ImageModel();
                        imageModel.setName("Image " + i);
                        imageModel.setUrl("http://api.openguides.id:3000/get_list_image?im=24c8b523-3a53-2e7e-1dda-005b83d4fca9_dataimage_&s=event/0c800db4-25ae-11e8-803d-0606d457636e/2018317/7");
                        data.add(imageModel);*/
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

    public void checkSession(){
        if (SessionUtil.checkIfExist(ISeasonConfig.KEY_ACCOUNT_ID)) {
            accountId = SessionUtil.getStringPreferences(ISeasonConfig.KEY_ACCOUNT_ID, null);
        }
        if (SessionUtil.checkIfExist(ISeasonConfig.KEY_EVENT_ID)) {
            eventId = SessionUtil.getStringPreferences(ISeasonConfig.KEY_EVENT_ID, null);
        }
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
