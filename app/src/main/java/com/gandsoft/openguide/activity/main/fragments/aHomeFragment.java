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
import android.text.TextUtils;
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
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.gandsoft.openguide.API.API;
import com.gandsoft.openguide.API.APIrequest.HomeContent.HomeContentCheckinRequestModel;
import com.gandsoft.openguide.API.APIrequest.HomeContent.HomeContentPostCaptionRequestModel;
import com.gandsoft.openguide.API.APIrequest.HomeContent.HomeContentPostImageCaptionRequestModel;
import com.gandsoft.openguide.API.APIrequest.HomeContent.HomeContentRequestModel;
import com.gandsoft.openguide.API.APIresponse.Event.EventTheEvent;
import com.gandsoft.openguide.API.APIresponse.HomeContent.HomeContentResponseModel;
import com.gandsoft.openguide.API.APIresponse.LocalBaseResponseModel;
import com.gandsoft.openguide.API.APIresponse.UserData.UserDataResponseModel;
import com.gandsoft.openguide.API.APIresponse.UserData.UserListEventResponseModel;
import com.gandsoft.openguide.API.APIresponse.UserData.UserWalletDataResponseModel;
import com.gandsoft.openguide.IConfig;
import com.gandsoft.openguide.ISeasonConfig;
import com.gandsoft.openguide.R;
import com.gandsoft.openguide.activity.main.adapter.PostRecViewAdapter;
import com.gandsoft.openguide.activity.main.fragments.aHomeActivityInFragment.aHomePostImageCaptionActivity;
import com.gandsoft.openguide.database.SQLiteHelper;
import com.gandsoft.openguide.support.NetworkUtil;
import com.gandsoft.openguide.support.PictureUtil;
import com.gandsoft.openguide.support.SessionUtil;
import com.gandsoft.openguide.support.SystemUtil;
import com.squareup.picasso.Picasso;

import org.sufficientlysecure.htmltextview.HtmlTextView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;

public class aHomeFragment extends Fragment {
    SQLiteHelper db;

    private static final int REQ_CODE_TAKE_PHOTO_INTENT_ID_STANDART = 1;
    private static final int REQ_CODE_POST_IMAGE = 12;

    private View view;
    private SwipeRefreshLayout homeSRLHomefvbi;
    private NestedScrollView homeNSVHomefvbi;
    private RecyclerView recyclerView;
    private LinearLayout llLoadModefvbi;
    private LinearLayout homeLLWriteSomethingfvbi;
    private LinearLayout llLoadMode2fvbi;
    private ImageView homeIVOpenCamerafvbi;
    private ImageView homeIVEventfvbi;
    private ImageView homeIVEventBackgroundfvbi;
    private ImageView homeIVShareSomethingfvbi;
    private TextView homeTVTitleEventfvbi;
    private TextView homeTVDescEventfvbi;
    private TextView homeTVOpenCamerafvbi;
    private TextView homeTVOpenGalleryfvbi;
    private HtmlTextView homeHtmlTVTitleEventfvbi;
    private HtmlTextView homeHtmlTVDescEventfvbi;
    private EditText homeETWritePostCreatefvbi;
    private Button homeBTapCheckInfvbi;
    private WebView homeWVTitleEventfvbi;
    private FloatingActionButton homeFABHomeUpfvbi;

    private UserWalletDataResponseModel walletData;
    private EventTheEvent theEventModel = null;
    private UserListEventResponseModel oneListEventModel = null;
    private UserDataResponseModel userData = null;
    private PostRecViewAdapter adapter;

    private List<HomeContentResponseModel> menuUi = new ArrayList<>();
    private String accountId, eventId;
    private String isKondisi = "up";
    private String last_id = "";
    private String first_id = "";
    private String last_date = "";
    private int version_data_event;
    private boolean last_data = false;
    private String formattedDate;
    private String formattedDateGMT;
    private Uri imageUri;
    private String base64;

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
        theEventModel = db.getTheEvent(eventId);
        userData = db.getOneUserData(accountId);
        oneListEventModel = db.getOneListEvent(eventId);

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

        adapter = new PostRecViewAdapter(getActivity(), menuUi, getContext(), eventId, accountId, theEventModel, oneListEventModel);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        homeETWritePostCreatefvbi.setEnabled(true);

        callHomeContentAPI();//content

        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        formattedDate = df.format(c.getTime());
        SimpleDateFormat dfGMT = new SimpleDateFormat("z");
        formattedDateGMT = dfGMT.format(c.getTime()).substring(3, 6);

    }

    private void initListener(View view) {
        homeIVOpenCamerafvbi.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                openCamera();
            }
        });
        homeTVOpenGalleryfvbi.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 2);
            }
        });
        homeIVShareSomethingfvbi.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                postCaption();
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

        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "New Picture");
        values.put(MediaStore.Images.Media.DESCRIPTION, "From your Camera");
        imageUri = getActivity().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, REQ_CODE_TAKE_PHOTO_INTENT_ID_STANDART);
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
                    if (models.size() == 10) {
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
        Log.d("Lihat", "updateUi aHomeFragment getAddpost_status : " + theEventModel.getAddpost_status());
        Log.d("Lihat", "updateUi aHomeFragment getDeletepost_status : " + theEventModel.getDeletepost_status());
        Log.d("Lihat", "updateUi aHomeFragment getCommentpost_status : " + theEventModel.getCommentpost_status());
        if (theEventModel != null) {
            homeHtmlTVTitleEventfvbi.setHtml(theEventModel.getEvent_name());
            homeTVTitleEventfvbi.setText(Html.fromHtml(theEventModel.getEvent_name()));
            homeTVDescEventfvbi.setText("" +
                    "" + Html.fromHtml(theEventModel.getEvent_location()) + "\n" +
                    "" + theEventModel.getDate_event() + "\n" +
                    "" + Html.fromHtml(theEventModel.getWeather()) + "");
            Picasso.with(getActivity())
                    .load(theEventModel.getLogo())
                    .placeholder(R.drawable.template_account_og)
                    .error(R.drawable.template_account_og)
                    .into(homeIVEventfvbi);
            Glide.with(getActivity())
                    .load(theEventModel.getBackground())
                    .placeholder(R.drawable.template_account_og)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .error(R.drawable.template_account_og)
                    .into(homeIVEventBackgroundfvbi);

            if (Integer.parseInt(theEventModel.getAddpost_status()) == 0) {
                homeLLWriteSomethingfvbi.setVisibility(View.GONE);
            }
        } else {
            Snackbar.make(getActivity().findViewById(android.R.id.content), "data kosong", Snackbar.LENGTH_LONG).setAction("Action", null).show();
        }
    }

    private void postCheckIn() {
        HomeContentCheckinRequestModel requestModel = new HomeContentCheckinRequestModel();
        requestModel.setId_event(eventId);
        requestModel.setPhonenumber(accountId);
        requestModel.setDbver("3");
        requestModel.setDate_gmt(formattedDateGMT);
        requestModel.setDate_post(formattedDate);

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
        requestModel.setGmt_date(formattedDateGMT);
        requestModel.setDate_post(formattedDate);

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

    private void postImageCaption(String base64Pic, String contentPic) {

        String uniqueId = UUID.randomUUID().toString();
        HomeContentPostImageCaptionRequestModel requestModel = new HomeContentPostImageCaptionRequestModel();
        requestModel.setId_event(eventId);
        requestModel.setAccount_id(accountId);
        requestModel.setId_postedhome(uniqueId);
        if (!TextUtils.isEmpty(contentPic)) {
            requestModel.setCaptions(contentPic);
        } else {
            requestModel.setCaptions("");
        }
        requestModel.setGmt_date(formattedDateGMT);
        requestModel.setDate_post(formattedDate);
        requestModel.setImagedata(base64Pic);
        requestModel.setDegree("ANDROID");
        requestModel.setDbver("3");

        addSingleTop(base64Pic, contentPic, uniqueId, eventId, accountId);

        API.doHomeContentPostImageCaptionRet(requestModel).enqueue(new Callback<List<LocalBaseResponseModel>>() {
            @Override
            public void onResponse(Call<List<LocalBaseResponseModel>> call, Response<List<LocalBaseResponseModel>> response) {
                if (response.isSuccessful()) {
                    List<LocalBaseResponseModel> models = response.body();
                    for (LocalBaseResponseModel model : models) {
                        if (model.getStatus().equalsIgnoreCase(ISeasonConfig.OK)) {
                            //

                        } else {
                            Snackbar.make(getActivity().findViewById(android.R.id.content), model.getMessage(), Snackbar.LENGTH_LONG).show();
                        }
                    }
                } else {
                    Log.d("Lihat", "onFailure aHomeFragment : " + response.message());
                    Snackbar.make(getActivity().findViewById(android.R.id.content), "Failed Connection To Server", Snackbar.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<LocalBaseResponseModel>> call, Throwable t) {
                Log.d("Lihat", "onFailure aHomeFragment : " + t.getMessage());
                Snackbar.make(getActivity().findViewById(android.R.id.content), "Failed Connection To Server", Snackbar.LENGTH_SHORT).show();
                adapter.removeDataFirst();
            }
        });
    }

    private void addSingleTop(String base64Pic, String contentPic, String uniqueId, String eventId, String accountId) {

        HomeContentResponseModel model = new HomeContentResponseModel();
        model.setId(uniqueId);
        model.setLike("0");
        model.setAccount_id(accountId);
        model.setTotal_comment("0");
        model.setStatus_like(0);
        model.setUsername(userData.getFull_name());
        model.setJabatan("");
        model.setDate_created(formattedDate);
        model.setImage_icon(userData.getImage_url());
        model.setImage_icon_local("");
        model.setImage_posted(base64Pic);
        model.setImage_posted_local("");
        if (!TextUtils.isEmpty(contentPic)) {
            model.setKeterangan(contentPic);
        } else {
            model.setKeterangan("");
        }
        model.setEvent("");

        adapter.addDataFirst(model);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /*if (requestCode == 1 && resultCode == RESULT_OK && null != data) {
            Bitmap thumbnail = null;
            try {
                thumbnail = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), imageUri);
                String imageurl = String.valueOf(imageUri);
                Log.d("image uri ", imageurl);
                createDirectoryAndSaveFile(thumbnail, "temp.jpg");

                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                thumbnail.compress(Bitmap.CompressFormat.PNG, 10, byteArrayOutputStream);
                byte[] byteArray = byteArrayOutputStream.toByteArray();

                base64pic = Base64.encodeToString(byteArray, Base64.DEFAULT);
                Log.d("bes64 ", base64pic);
                startActivity(new Intent(getActivity(), aHomePostImageCaptionActivity.class).putExtra("base64", base64pic));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (requestCode == 2 && resultCode == RESULT_OK) {
            onSelectGallery(data);
        } else */
        if (requestCode == REQ_CODE_POST_IMAGE) {
            if (resultCode == RESULT_OK) {
                String imgUrl;
                String contentPic;
                if (data.hasExtra(ISeasonConfig.INTENT_PARAM) && data.hasExtra(ISeasonConfig.INTENT_PARAM2)) {
                    imgUrl = data.getStringExtra(ISeasonConfig.INTENT_PARAM);
                    contentPic = data.getStringExtra(ISeasonConfig.INTENT_PARAM2);

                    Glide.with(getActivity())
                            .load(new File(imgUrl))
                            .asBitmap()
                            .into(new SimpleTarget<Bitmap>() {
                                @Override
                                public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                                    Bitmap resizeImage = PictureUtil.resizeImage(resource, 1080);
                                    base64 = PictureUtil.bitmapToBase64(resizeImage, Bitmap.CompressFormat.JPEG, 100);
                                    postImageCaption(base64, contentPic);
                                }
                            });
                }
            }
        } else if (requestCode == REQ_CODE_TAKE_PHOTO_INTENT_ID_STANDART && resultCode == Activity.RESULT_OK) {
            String imageurl = PictureUtil.getPath(imageUri, getActivity());
            moveToAHomePostImage(imageurl);

        }
    }

    private void moveToAHomePostImage(String imageurl) {
        Intent intent = new Intent(getActivity(), aHomePostImageCaptionActivity.class);
        intent.putExtra(ISeasonConfig.INTENT_PARAM, imageurl);
        startActivityForResult(intent, REQ_CODE_POST_IMAGE);
    }

    private void createDirectoryAndSaveFile(Bitmap imageToSave, String fileName) {
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/.Gandsoft/" + eventId + "/" + fileName);
        if (file.exists()) {
            file.delete();
        }
        try {
            FileOutputStream out = new FileOutputStream(file);
            imageToSave.compress(Bitmap.CompressFormat.JPEG, 10, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void onSelectGallery(Intent data) {
        /*Uri selectedImageUri = data.getData();
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
        bm.compress(Bitmap.CompressFormat.PNG, 10, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        base64pic = Base64.encodeToString(byteArray, Base64.DEFAULT);

        Log.d("bes64 ", base64pic);*/
    }
}


