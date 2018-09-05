package com.gandsoft.openguide.activity.main.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
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
import com.gandsoft.openguide.API.APIrequest.HomeContent.HomeContentRequestModel;
import com.gandsoft.openguide.API.APIresponse.Event.EventTheEvent;
import com.gandsoft.openguide.API.APIresponse.HomeContent.HomeContentResponseModel;
import com.gandsoft.openguide.IConfig;
import com.gandsoft.openguide.ISeasonConfig;
import com.gandsoft.openguide.R;
import com.gandsoft.openguide.activity.main.adapter.PostRecViewAdapter;
import com.gandsoft.openguide.database.SQLiteHelper;
import com.gandsoft.openguide.support.SessionUtil;
import com.squareup.picasso.Picasso;

import org.sufficientlysecure.htmltextview.HtmlTextView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class aHomeFragment extends Fragment {


    private static final String TAG = "Lihat";
    private View view;
    private LinearLayout llLoadModefvbi;
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
    }

    private void initContent(ViewGroup container) {

        updateUi();

        adapter = new PostRecViewAdapter(getActivity(), menuUi);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        callHomeContentAPI();//content
    }

    private void callHomeContentAPI() {
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
                    last_id = models.get(9).getId();
                    last_date = models.get(9).getDate_created();
                    first_id = models.get(0).getId();
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

        llLoadModefvbi.setVisibility(View.VISIBLE);

        homeNSVHomefvbi.fullScroll(View.FOCUS_DOWN);

        new Handler().postDelayed(new Runnable() {
            public void run() {
                //code here
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
                        llLoadModefvbi.setVisibility(View.GONE);
                        if (response.isSuccessful()) {
                            List<HomeContentResponseModel> models = response.body();
                            adapter.addDatas(models);
                            last_id = models.get(9).getId();
                            last_date = models.get(9).getDate_created();
                            first_id = models.get(0).getId();
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
                        llLoadModefvbi.setVisibility(View.GONE);
                        Snackbar.make(getActivity().findViewById(android.R.id.content), t.getMessage(), Snackbar.LENGTH_LONG).show();
                    }
                });
            }
        }, 2000);

    }

    private void initListener(View view) {
        homeIVOpenCamerafvbi.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                startActivity(intent);
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

                    callHomeContentAPILoadMore();
                }
            }
        });
        homeBTapCheckInfvbi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(getActivity().findViewById(android.R.id.content), "tes", Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    private void updateUi() {
        EventTheEvent model = db.getTheEvent(eventId);
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
            Snackbar.make(getActivity().findViewById(android.R.id.content), "data kosong", Snackbar.LENGTH_LONG).setAction("Action", null).show();
        }
    }
}
