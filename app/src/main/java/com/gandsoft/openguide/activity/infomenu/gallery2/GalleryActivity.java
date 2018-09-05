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
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.gandsoft.openguide.API.API;
import com.gandsoft.openguide.API.APIrequest.Event.EventDataRequestModel;
import com.gandsoft.openguide.API.APIrequest.Gallery.GalleryRequestModel;
import com.gandsoft.openguide.API.APIrequest.UserData.UserDataRequestModel;
import com.gandsoft.openguide.API.APIrequest.UserUpdate.UserUpdateRequestModel;
import com.gandsoft.openguide.API.APIresponse.Event.EventAbout;
import com.gandsoft.openguide.API.APIresponse.Event.EventCommitteeNote;
import com.gandsoft.openguide.API.APIresponse.Event.EventDataContact;
import com.gandsoft.openguide.API.APIresponse.Event.EventDataContactList;
import com.gandsoft.openguide.API.APIresponse.Event.EventDataResponseModel;
import com.gandsoft.openguide.API.APIresponse.Event.EventEmergencies;
import com.gandsoft.openguide.API.APIresponse.Event.EventImportanInfo;
import com.gandsoft.openguide.API.APIresponse.Event.EventPlaceList;
import com.gandsoft.openguide.API.APIresponse.Event.EventScheduleListDate;
import com.gandsoft.openguide.API.APIresponse.Event.EventScheduleListDateDataList;
import com.gandsoft.openguide.API.APIresponse.Event.EventSurroundingArea;
import com.gandsoft.openguide.API.APIresponse.Event.EventTheEvent;
import com.gandsoft.openguide.API.APIresponse.Gallery.GalleryResponseModel;
import com.gandsoft.openguide.API.APIresponse.UserData.UserDataResponseModel;
import com.gandsoft.openguide.API.APIresponse.UserData.UserListEventResponseModel;
import com.gandsoft.openguide.API.APIresponse.UserData.UserWalletDataResponseModel;
import com.gandsoft.openguide.API.APIresponse.UserUpdate.UserUpdateResponseModel;
import com.gandsoft.openguide.IConfig;
import com.gandsoft.openguide.ISeasonConfig;
import com.gandsoft.openguide.R;
import com.gandsoft.openguide.database.SQLiteHelper;
import com.gandsoft.openguide.support.NetworkUtil;
import com.gandsoft.openguide.support.SessionUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        checkSession();
        summonToolbar("Gallery");
        getAPIGalleryValid(eventId);

        ArrayList<GalleryResponseModel> models = db.getGallery(eventId);
        if (models != null) {
            for (int i = 0; i < models.size(); i++) {
                GalleryResponseModel model = models.get(i);
                ImageModel imageModel = new ImageModel();
                imageModel.setName("Image " + i);
                imageModel.setUrl(String.valueOf(Html.fromHtml(model.getImage_posted())));
                data.add(imageModel);
            }
        }

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

    private void getAPIGalleryValid(String eventId) {
        getAPIGalleryDataDo(eventId, accountId);
        if (SQLiteHelper.TableGallery.isEmpty()) {
            Snackbar.make(findViewById(android.R.id.content), "Belum ada data", Snackbar.LENGTH_SHORT).show();
            if (NetworkUtil.isConnected(this)) {
                getAPIGalleryDataDo(eventId, accountId);
            }
        } else {
            Snackbar.make(findViewById(android.R.id.content), "Memunculkan Data", Snackbar.LENGTH_SHORT).show();
            if (NetworkUtil.isConnected(this)) {
                getAPIGalleryDataDo(eventId, accountId);
            }
        }
    }

    private void getAPIGalleryDataDo(String eventId, String accountId) {
        GalleryRequestModel requestModel = new GalleryRequestModel();
        requestModel.setDbver("3");
        requestModel.setId_event(eventId);
        requestModel.setPhonenumber(accountId);
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
                    for (int i = 0; i < models.size(); i++) {
                        GalleryResponseModel model = models.get(i);
                        if (SQLiteHelper.TableGallery.isEmpty()) {
                            db.saveGallery(model, eventId);
                        } else {
                            db.updateGallery(model, eventId);
                        }
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
