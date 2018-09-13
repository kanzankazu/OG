package com.gandsoft.openguide.activity.main.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.gandsoft.openguide.API.API;
import com.gandsoft.openguide.API.APIrequest.HomeContent.HomeContentCheckinRequestModel;
import com.gandsoft.openguide.API.APIrequest.HomeContent.HomeContentPostCaptionRequestModel;
import com.gandsoft.openguide.API.APIrequest.HomeContent.HomeContentPostImageCaptionRequestModel;
import com.gandsoft.openguide.API.APIrequest.HomeContent.HomeContentRequestModel;
import com.gandsoft.openguide.API.APIresponse.Event.EventTheEvent;
import com.gandsoft.openguide.API.APIresponse.HomeContent.HomeContentResponseModel;
import com.gandsoft.openguide.API.APIresponse.LocalBaseResponseModel;
import com.gandsoft.openguide.API.APIresponse.UserData.UserWalletDataResponseModel;
import com.gandsoft.openguide.IConfig;
import com.gandsoft.openguide.ISeasonConfig;
import com.gandsoft.openguide.R;
import com.gandsoft.openguide.activity.main.adapter.PostRecViewAdapter;
import com.gandsoft.openguide.database.SQLiteHelper;
import com.gandsoft.openguide.support.NetworkUtil;
import com.gandsoft.openguide.support.SessionUtil;
import com.gandsoft.openguide.support.SystemUtil;
import com.squareup.picasso.Picasso;

import org.sufficientlysecure.htmltextview.HtmlTextView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class aHomeFragment extends Fragment {
    private static final String TAG = "Lihat";
    private View view;
    private LinearLayout llLoadModefvbi, homeLLWriteSomethingfvbi;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout homeSRLHomefvbi;
    private ImageView homeIVOpenCamerafvbi;
    private NestedScrollView homeNSVHomefvbi;
    private ImageView homeIVEventfvbi, homeIVEventBackgroundfvbi;
    private ImageView homeIVShareSomethingfvbi;
    private TextView homeTVTitleEventfvbi;
    private TextView homeTVDescEventfvbi;
    private Button homeBTapCheckInfvbi;
    private EditText homeETWritePostCreatefvbi;
    private WebView homeWVTitleEventfvbi;
    private FloatingActionButton homeFABHomeUpfvbi;
    /**/
    private PostRecViewAdapter adapter;
    private List<HomeContentResponseModel> menuUi = new ArrayList<>();
    private int page = 0;
    private String accountId, eventId;
    private int version_data_event;
    SQLiteHelper db;
    private HtmlTextView homeHtmlTVTitleEventfvbi;
    private HtmlTextView homeHtmlTVDescEventfvbi;
    private String isKondisi = "up";
    private String last_id = "";
    private String first_id = "";
    private String last_date = "";
    private boolean last_data = false;

    //Post
    private int i = 0;
    private TextView homeTVOpenCamerafvbi, homeTVOpenGalleryfvbi;
    private String base64pic = "";
    private LinearLayout llLoadMode2fvbi;
    private int heightRecycle = 0;
    private Uri imageUri;
    private UserWalletDataResponseModel walletData;


    public aHomeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_a_home, container, false);

        db = new SQLiteHelper(getActivity());

        accountId = SessionUtil.getStringPreferences(ISeasonConfig.KEY_ACCOUNT_ID, null);
        eventId = SessionUtil.getStringPreferences(ISeasonConfig.KEY_EVENT_ID, null);

        initComponent();
        initContent(container);
        initListener(view);

        return view;
    }

    private void initComponent() {
        homeTVOpenCamerafvbi = view.findViewById(R.id.homeTVOpenCamera);
        homeTVOpenGalleryfvbi = view.findViewById(R.id.homeTVOpenGallery);
        homeFABHomeUpfvbi = (FloatingActionButton) view.findViewById(R.id.homeFABHomeUp);
        homeSRLHomefvbi = (SwipeRefreshLayout) view.findViewById(R.id.homeSRLHome);
        homeNSVHomefvbi = (NestedScrollView) view.findViewById(R.id.homeNSVHome);
        homeIVEventfvbi = (ImageView) view.findViewById(R.id.homeIVEvent);
        homeIVEventBackgroundfvbi = (ImageView) view.findViewById(R.id.homeIVEventBackground);
        homeIVShareSomethingfvbi = (ImageView) view.findViewById(R.id.homeIVShareSomething);
        homeIVOpenCamerafvbi = (ImageView) view.findViewById(R.id.homeIVOpenCamera);
        homeTVTitleEventfvbi = (TextView) view.findViewById(R.id.homeTVTitleEvent);
        homeHtmlTVTitleEventfvbi = (HtmlTextView) view.findViewById(R.id.homeHtmlTVTitleEvent);
        homeHtmlTVDescEventfvbi = (HtmlTextView) view.findViewById(R.id.homeHtmlTVDescEvent);
        homeWVTitleEventfvbi = (WebView) view.findViewById(R.id.homeWVTitleEvent);
        homeTVDescEventfvbi = (TextView) view.findViewById(R.id.homeTVDescEvent);
        homeETWritePostCreatefvbi = (EditText) view.findViewById(R.id.homeETWritePostCreate);
        homeBTapCheckInfvbi = (Button) view.findViewById(R.id.homeBTapCheckIn);
        recyclerView = (RecyclerView) view.findViewById(R.id.homeRVEvent);
        llLoadModefvbi = (LinearLayout) view.findViewById(R.id.llLoadMode);
        llLoadMode2fvbi = (LinearLayout) view.findViewById(R.id.llLoadMode2);
        homeLLWriteSomethingfvbi = (LinearLayout) view.findViewById(R.id.homeLLWriteSomething);
    }

    private void initContent(ViewGroup container) {
        updateUi();//init content

        /*Log.d("Lihat", "initContent aHomeFragment : " + "\\");// = \
        Log.d("Lihat", "initContent aHomeFragment : " + "\\\\");// = \\
        Log.d("Lihat", "initContent aHomeFragment : " + "\"");// = "
        Log.d("Lihat", "initContent aHomeFragment : " + "\\\"");// = \"*/

        walletData = db.getWalletDataIdCard(eventId);
        Log.d("Lihat", "initContent aHomeFragment : " + walletData.getBody_wallet());
        String replace1 = walletData.getBody_wallet().replace("\\", " ");
        String replace2 = replace1.replace("\"", " ");
        String replace3 = replace2.replace("status-checkin , ", "status-checkin,");
        String replace4 = replace3.replace("status-feedback , ", "status-feedback,");
        Log.d("Lihat", "initContent aHomeFragment replace4.matches : " + (replace4.matches("(?i).*status-checkin,0.*")));
        Log.d("Lihat", "initContent aHomeFragment replace4.matches : " + (replace4.matches("(?i).*status-checkin,1.*")));
        if (replace4.matches("(?i).*status-checkin,1.*")) {
            homeBTapCheckInfvbi.setVisibility(View.GONE);
        }

        adapter = new PostRecViewAdapter(getActivity(), menuUi, getContext(), eventId, accountId);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        homeETWritePostCreatefvbi.setEnabled(true);

        callHomeContentAPI();//content
    }

    private void initListener(View view) {
        homeIVOpenCamerafvbi.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.TITLE, "New Picture");
                values.put(MediaStore.Images.Media.DESCRIPTION, "From your Camera");
                imageUri = getActivity().getContentResolver().insert(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                startActivityForResult(intent, 1);
            }
        });
        homeTVOpenCamerafvbi.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.TITLE, "New Picture");
                values.put(MediaStore.Images.Media.DESCRIPTION, "From your Camera");
                imageUri = getActivity().getContentResolver().insert(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                startActivityForResult(intent, 1);
            }
        });
        homeTVOpenGalleryfvbi.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 2);
            }
        });
        homeIVShareSomethingfvbi.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (i == 0) {
                    postCaption();
                } else if (i == 1) {
                    postImageCaption();
                }
            }
        });
        homeSRLHomefvbi.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                homeSRLHomefvbi.setRefreshing(false);
                callHomeContentAPI();//swipe refresh
            }
        });
        homeNSVHomefvbi.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
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

                        llLoadMode2fvbi.setVisibility(View.VISIBLE);

                        new Handler().postDelayed(new Runnable() {
                            public void run() {
                                //code here
                                callHomeContentAPILoadMore();
                            }
                        }, 1000);

                    } else {
                        Snackbar.make(getActivity().findViewById(android.R.id.content), "Sudah tidak ada data", Snackbar.LENGTH_LONG).show();
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
        homeBTapCheckInfvbi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (NetworkUtil.isConnected(getActivity())) {
                    postCheckIn();
                } else {
                    Snackbar.make(getActivity().findViewById(android.R.id.content), "Check Your Connection", Snackbar.LENGTH_LONG).show();
                }

            }
        });
        homeFABHomeUpfvbi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                homeNSVHomefvbi.smoothScrollTo(0, 0);
            }
        });
    }

    public void callHomeContentAPI() {
        deleteImage(eventId);

        db.deleleDataByKey(SQLiteHelper.TableHomeContent, SQLiteHelper.Key_HomeContent_EventId, eventId);

        ProgressDialog progressDialog = ProgressDialog.show(getActivity(), "Loading...", "Please Wait..", false, false);

        HomeContentRequestModel model = new HomeContentRequestModel();
        model.setPhonenumber(accountId);
        model.setId_event(eventId);
        model.setDbver(String.valueOf(IConfig.DB_Version));
        model.setKondisi("up");
        model.setLast_date("");
        model.setLastid("");
        model.setFirstid("");

        API.doHomeContentDataRet(model).enqueue(new Callback<List<HomeContentResponseModel>>() {
            @Override
            public void onResponse(Call<List<HomeContentResponseModel>> call, Response<List<HomeContentResponseModel>> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    List<HomeContentResponseModel> models = response.body();
                    adapter.setData(models);
                    if (models.size() == 10) {
                        last_data = false;
                        last_id = models.get(models.size() - 1).getId();
                        last_date = models.get(models.size() - 1).getDate_created();
                        first_id = models.get(0).getId();
                    } else {
                        Snackbar.make(getActivity().findViewById(android.R.id.content), "Data Terakhir", Snackbar.LENGTH_LONG).show();
                        last_data = true;
                        last_id = "";
                        last_date = "";
                        first_id = "";
                    }
                    for (int i1 = 0; i1 < models.size(); i1++) {
                        HomeContentResponseModel responseModel = models.get(i1);
                        if (db.isDataTableValueMultipleNull(SQLiteHelper.TableHomeContent, SQLiteHelper.Key_HomeContent_EventId, SQLiteHelper.Key_HomeContent_id, eventId, responseModel.getId())) {
                            db.saveHomeContent(responseModel, eventId);
                        } else {
                            db.updateHomeContent(responseModel, eventId);
                        }
                    }
                } else {
                    Snackbar.make(getActivity().findViewById(android.R.id.content), response.message(), Snackbar.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<HomeContentResponseModel>> call, Throwable t) {
                progressDialog.dismiss();
                Snackbar.make(getActivity().findViewById(android.R.id.content), t.getMessage(), Snackbar.LENGTH_LONG).show();
            }
        });
    }

    private void callHomeContentAPILoadMore() {

        HomeContentRequestModel model = new HomeContentRequestModel();
        model.setPhonenumber(accountId);
        model.setId_event(eventId);
        model.setDbver(String.valueOf(IConfig.DB_Version));
        model.setKondisi("down");
        model.setLast_date(last_date);
        model.setLastid(last_id);
        model.setFirstid(first_id);

        API.doHomeContentDataRet(model).enqueue(new Callback<List<HomeContentResponseModel>>() {
            @Override
            public void onResponse(Call<List<HomeContentResponseModel>> call, Response<List<HomeContentResponseModel>> response) {
                llLoadMode2fvbi.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    List<HomeContentResponseModel> models = response.body();
                    adapter.addDatas(models);
                    if (models.size() >= 10) {
                        last_data = false;
                        last_id = models.get(models.size() - 1).getId();
                        last_date = models.get(models.size() - 1).getDate_created();
                        first_id = models.get(0).getId();
                    } else {
                        Snackbar.make(getActivity().findViewById(android.R.id.content), "Data Terakhir", Snackbar.LENGTH_SHORT).show();
                        last_data = true;
                        last_id = "";
                        last_date = "";
                        first_id = "";
                    }
                    for (int i1 = 0; i1 < models.size(); i1++) {
                        HomeContentResponseModel responseModel = models.get(i1);
                        if (db.isDataTableValueMultipleNull(SQLiteHelper.TableHomeContent, SQLiteHelper.Key_HomeContent_EventId, SQLiteHelper.Key_HomeContent_id, eventId, responseModel.getId())) {
                            db.saveHomeContent(responseModel, eventId);
                        } else {
                            db.updateHomeContent(responseModel, eventId);
                        }
                    }
                } else {
                    Snackbar.make(getActivity().findViewById(android.R.id.content), response.message(), Snackbar.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<HomeContentResponseModel>> call, Throwable t) {
                llLoadMode2fvbi.setVisibility(View.GONE);
                Snackbar.make(getActivity().findViewById(android.R.id.content), t.getMessage(), Snackbar.LENGTH_LONG).show();
            }
        });


    }

    private void deleteImage(String eventIds) {
        db.deleleDataByKey(SQLiteHelper.TableGallery, SQLiteHelper.Key_Gallery_eventId, eventIds);

        File dir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/.Gandsoft/" + eventIds);
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                new File(dir, children[i]).delete();
            }
        }
    }

    private void updateUi() {
        EventTheEvent model = db.getTheEvent(eventId);
        Log.d("Lihat", "updateUi aHomeFragment getAddpost_status : " + model.getAddpost_status());
        Log.d("Lihat", "updateUi aHomeFragment getDeletepost_status : " + model.getDeletepost_status());
        Log.d("Lihat", "updateUi aHomeFragment getCommentpost_status : " + model.getCommentpost_status());
        if (model != null) {
            homeHtmlTVTitleEventfvbi.setHtml(model.getEvent_name());
            homeTVTitleEventfvbi.setText(Html.fromHtml(model.getEvent_name()));
            homeTVDescEventfvbi.setText("" +
                    "" + Html.fromHtml(model.getEvent_location()) + "\n" +
                    "" + model.getDate_event() + "\n" +
                    "" + Html.fromHtml(model.getWeather()) + "");
            Picasso.with(getActivity())
                    .load(model.getLogo())
                    .placeholder(R.drawable.template_account_og)
                    .error(R.drawable.template_account_og)
                    .into(homeIVEventfvbi);
            Glide.with(getActivity())
                    .load(model.getBackground())
                    .placeholder(R.drawable.template_account_og)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .error(R.drawable.template_account_og)
                    .into(homeIVEventBackgroundfvbi);
        } else {
            Snackbar.make(getActivity().findViewById(android.R.id.content), "models kosong", Snackbar.LENGTH_LONG).setAction("Action", null).show();
        }
    }

    private void postCheckIn() {
        HomeContentCheckinRequestModel requestModel = new HomeContentCheckinRequestModel();
        requestModel.setId_event(eventId);
        requestModel.setPhonenumber(accountId);
        requestModel.setDbver("3");
        requestModel.setDate_gmt("");
        requestModel.setDate_post("");

        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setTitle("Upload data baru..");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        // show it
        progressDialog.show();

        API.doHomeContentCheckinRet(requestModel).enqueue(new Callback<List<LocalBaseResponseModel>>() {
            @Override
            public void onResponse(Call<List<LocalBaseResponseModel>> call, Response<List<LocalBaseResponseModel>> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    List<LocalBaseResponseModel> s = response.body();
                    if (s.size() == 1) {
                        for (int i = 0; i < s.size(); i++) {
                            LocalBaseResponseModel model = s.get(i);
                            if (model.getStatus().equalsIgnoreCase("ok")) {
                                Snackbar.make(getActivity().findViewById(android.R.id.content), "Tersimpan", Snackbar.LENGTH_LONG).show();
                            } else {
                                Snackbar.make(getActivity().findViewById(android.R.id.content), "Bad Response", Snackbar.LENGTH_LONG).show();
                            }
                        }
                    } else {
                        Snackbar.make(getActivity().findViewById(android.R.id.content), "Data Tidak Sesuai", Snackbar.LENGTH_LONG).show();
                    }
                } else {
                    Snackbar.make(getActivity().findViewById(android.R.id.content), response.message(), Snackbar.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<LocalBaseResponseModel>> call, Throwable t) {
                Snackbar.make(getActivity().findViewById(android.R.id.content), t.getMessage(), Snackbar.LENGTH_LONG).show();
            }
        });
    }

    private void postCaption() {
        HomeContentPostCaptionRequestModel requestModel = new HomeContentPostCaptionRequestModel();
        requestModel.setId_event(eventId);
        requestModel.setAccount_id(accountId);
        requestModel.setCaptions(homeETWritePostCreatefvbi.getText().toString());
        requestModel.setDbver("3");
        requestModel.setGmt_date("");
        requestModel.setDate_post("");

        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setTitle("Upload data baru..");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        // show it
        progressDialog.show();

        API.doHomeContentPostCaptionRet(requestModel).enqueue(new Callback<List<LocalBaseResponseModel>>() {
            @Override
            public void onResponse(Call<List<LocalBaseResponseModel>> call, Response<List<LocalBaseResponseModel>> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    List<LocalBaseResponseModel> s = response.body();
                    if (s.size() == 1) {
                        for (int i = 0; i < s.size(); i++) {
                            LocalBaseResponseModel model = s.get(i);
                            if (model.getStatus().equalsIgnoreCase("ok")) {
                                Snackbar.make(getActivity().findViewById(android.R.id.content), "Post Terkirim", Snackbar.LENGTH_LONG).show();
                                callHomeContentAPI();
                                updateUi();//onresponse postCaption
                                homeETWritePostCreatefvbi.setText("");
                                SystemUtil.hideKeyBoard(getActivity());
                            } else {
                                Snackbar.make(getActivity().findViewById(android.R.id.content), "Post Bad Response", Snackbar.LENGTH_LONG).show();
                            }
                        }
                    } else {
                        Snackbar.make(getActivity().findViewById(android.R.id.content), "Post Data Tidak Sesuai", Snackbar.LENGTH_LONG).show();
                    }
                } else {
                    Snackbar.make(getActivity().findViewById(android.R.id.content), response.message(), Snackbar.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<LocalBaseResponseModel>> call, Throwable t) {
                Snackbar.make(getActivity().findViewById(android.R.id.content), t.getMessage(), Snackbar.LENGTH_LONG).show();
            }
        });
    }

    private void postImageCaption() {
        HomeContentPostImageCaptionRequestModel requestModel = new HomeContentPostImageCaptionRequestModel();
        requestModel.setId_event(eventId);
        requestModel.setAccount_id(accountId);
        requestModel.setId_postedhome("");
        requestModel.setCaptions("");
        requestModel.setGmt_date("");
        requestModel.setDate_post("");
        requestModel.setImagedata("");
        requestModel.setDegree("");
        requestModel.setDbver("3");

        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setTitle("Upload data baru..");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        // show it
        progressDialog.show();

        API.doHomeContentPostImageCaptionRet(requestModel).enqueue(new Callback<List<LocalBaseResponseModel>>() {
            @Override
            public void onResponse(Call<List<LocalBaseResponseModel>> call, Response<List<LocalBaseResponseModel>> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    List<LocalBaseResponseModel> s = response.body();
                    if (s.size() == 1) {
                        for (int i = 0; i < s.size(); i++) {
                            LocalBaseResponseModel model = s.get(i);
                            if (model.getStatus().equalsIgnoreCase("ok")) {
                                Snackbar.make(getActivity().findViewById(android.R.id.content), "Image Post Tersimpan", Snackbar.LENGTH_LONG).show();
                                callHomeContentAPI();
                                updateUi();// onresponse postimageCaption
                            } else {
                                Snackbar.make(getActivity().findViewById(android.R.id.content), "Image Post Bad Response", Snackbar.LENGTH_LONG).show();
                            }
                        }
                    } else {
                        Snackbar.make(getActivity().findViewById(android.R.id.content), "Image Post Data Tidak Sesuai", Snackbar.LENGTH_LONG).show();
                    }
                } else {
                    Snackbar.make(getActivity().findViewById(android.R.id.content), response.message(), Snackbar.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<LocalBaseResponseModel>> call, Throwable t) {
                Snackbar.make(getActivity().findViewById(android.R.id.content), t.getMessage(), Snackbar.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            Bitmap thumbnail = null;
            try {
                thumbnail = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), imageUri);
                String imageurl = String.valueOf(imageUri);
                Log.d("image uri ", imageurl);
                createDirectoryAndSaveFile(thumbnail, "temp.jpg");

                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                thumbnail.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                byte[] byteArray = byteArrayOutputStream.toByteArray();

                base64pic = Base64.encodeToString(byteArray, Base64.DEFAULT);
                Log.d("bes64 ", base64pic);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (requestCode == 2 && resultCode == Activity.RESULT_OK) {
            onSelectGallery(data);
        }

    }

    private void createDirectoryAndSaveFile(Bitmap imageToSave, String fileName) {
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/.Gandsoft/" + eventId + "/" + fileName);
        if (file.exists()) {
            file.delete();
        }
        try {
            FileOutputStream out = new FileOutputStream(file);
            imageToSave.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void onSelectGallery(Intent data) {
        Uri selectedImageUri = data.getData();
        String[] projection = {MediaStore.MediaColumns.DATA};
        @SuppressWarnings("deprecation")
        Cursor cursor = getActivity().managedQuery(selectedImageUri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        cursor.moveToFirst();

        String selectedImagePath = cursor.getString(column_index);

        Bitmap bm;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(selectedImagePath, options);
        final int REQUIRED_SIZE = 200;
        int scale = 1;
        while (options.outWidth / scale / 2 >= REQUIRED_SIZE && options.outHeight / scale / 2 >= REQUIRED_SIZE)
            scale *= 2;
        options.inSampleSize = scale;
        options.inJustDecodeBounds = false;
        bm = BitmapFactory.decodeFile(selectedImagePath, options);

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();

        base64pic = Base64.encodeToString(byteArray, Base64.DEFAULT);
        Log.d("bes64 ", base64pic);
    }
}


