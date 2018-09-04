package com.gandsoft.openguide.activity.infomenu.gallery2;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.gandsoft.openguide.API.API;
import com.gandsoft.openguide.API.APIrequest.Gallery.GalleryRequestModel;
import com.gandsoft.openguide.API.APIrequest.UserUpdate.UserUpdateRequestModel;
import com.gandsoft.openguide.API.APIresponse.Event.EventCommitteeNote;
import com.gandsoft.openguide.API.APIresponse.Event.EventImportanInfo;
import com.gandsoft.openguide.API.APIresponse.Gallery.GalleryResponseModel;
import com.gandsoft.openguide.API.APIresponse.UserData.UserDataResponseModel;
import com.gandsoft.openguide.API.APIresponse.UserData.UserListEventResponseModel;
import com.gandsoft.openguide.API.APIresponse.UserUpdate.UserUpdateResponseModel;
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

    private String accountId, eventId;
    private ArrayList<String> parentItems = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ab = getSupportActionBar();
        ab.setTitle("Gallery");


        if (SessionUtil.checkIfExist(ISeasonConfig.KEY_ACCOUNT_ID)) {
            accountId = SessionUtil.getStringPreferences(ISeasonConfig.KEY_ACCOUNT_ID, null);
        }
        if (SessionUtil.checkIfExist(ISeasonConfig.KEY_EVENT_ID)) {
            eventId = SessionUtil.getStringPreferences(ISeasonConfig.KEY_EVENT_ID, null);
        }
        GalleryRequestModel requestModel = new GalleryRequestModel();
        API.doGalleryRet(requestModel).enqueue(new Callback<List<GalleryResponseModel>>() {
            @Override
            public void onResponse(Call<List<GalleryResponseModel>> call, Response<List<GalleryResponseModel>> response) {
                if (response.isSuccessful()) {
                    List<GalleryResponseModel> galleryResponseModels = response.body();
                    for (int j = 0; j < galleryResponseModels.size(); j++) {
                        GalleryResponseModel model = galleryResponseModels.get(j);
                        if (db.isDataTableValueNull(SQLiteHelper.TableGallery, SQLiteHelper.Key_Gallery_galleryId, model.getId())) {
                            db.saveGallery(model, model.getId());
                        } else {
                        }
                    }
                    Log.d("brasil", response.message());
                } else {
                    Log.d("gagal", response.message());
                }
            }


            @Override
            public void onFailure(Call<List<GalleryResponseModel>> call, Throwable t) {
                Log.d("gagal failur", t.getMessage());
            }
        });

        ArrayList<GalleryResponseModel> models = db.getGallery();
        if (models != null) {
            for (int i = 0; i < models.size(); i++) {
                GalleryResponseModel model = models.get(i);
                ImageModel imageModel = new ImageModel();
                imageModel.setName("Image " + i);
                imageModel.setUrl(String.valueOf(Html.fromHtml(model.getImage_posted())));
                data.add(imageModel);
            }
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mRecyclerView = (RecyclerView) findViewById(R.id.list);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        mRecyclerView.setHasFixedSize(true);


        mAdapter = new GalleryAdapter(GalleryActivity.this, data);
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
