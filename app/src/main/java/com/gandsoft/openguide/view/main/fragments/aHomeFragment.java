package com.gandsoft.openguide.view.main.fragments;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.GpsSatellite;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.gandsoft.openguide.API.API;
import com.gandsoft.openguide.API.APIrequest.GetCheckDistanceLocationRequestModel;
import com.gandsoft.openguide.API.APIrequest.HomeContent.HomeContentCheckinRequestModel;
import com.gandsoft.openguide.API.APIrequest.HomeContent.HomeContentPostCaptionDeleteRequestModel;
import com.gandsoft.openguide.API.APIrequest.HomeContent.HomeContentPostCaptionSetRequestModel;
import com.gandsoft.openguide.API.APIrequest.HomeContent.HomeContentPostImageCaptionRequestModel;
import com.gandsoft.openguide.API.APIrequest.HomeContent.HomeContentRequestModel;
import com.gandsoft.openguide.API.APIresponse.Event.EventTheEvent;
import com.gandsoft.openguide.API.APIresponse.GetCheckDistanceLocationResponseModel;
import com.gandsoft.openguide.API.APIresponse.HomeContent.HomeContentCommentModelParcelable;
import com.gandsoft.openguide.API.APIresponse.HomeContent.HomeContentPostCaptionDeleteResponseModel;
import com.gandsoft.openguide.API.APIresponse.HomeContent.HomeContentResponseModel;
import com.gandsoft.openguide.API.APIresponse.LocalBaseResponseModel;
import com.gandsoft.openguide.API.APIresponse.UserData.GetListUserEventResponseModel;
import com.gandsoft.openguide.API.APIresponse.UserData.UserListEventResponseModel;
import com.gandsoft.openguide.API.APIresponse.UserData.UserWalletDataResponseModel;
import com.gandsoft.openguide.IConfig;
import com.gandsoft.openguide.ISeasonConfig;
import com.gandsoft.openguide.R;
import com.gandsoft.openguide.database.SQLiteHelper;
import com.gandsoft.openguide.database.SQLiteHelperMethod;
import com.gandsoft.openguide.presenter.widget.CustomScrollView;
import com.gandsoft.openguide.support.AppUtil;
import com.gandsoft.openguide.support.DateTimeUtil;
import com.gandsoft.openguide.support.InputValidUtil;
import com.gandsoft.openguide.support.NetworkUtil;
import com.gandsoft.openguide.support.PictureUtil;
import com.gandsoft.openguide.support.SessionUtil;
import com.gandsoft.openguide.support.SystemUtil;
import com.gandsoft.openguide.view.main.adapter.HomeContentAdapter;
import com.gandsoft.openguide.view.main.fragments.aHomeActivityInFragment.aHomePostCommentActivity;
import com.gandsoft.openguide.view.main.fragments.aHomeActivityInFragment.aHomePostImageCaptionActivity;

import org.sufficientlysecure.htmltextview.HtmlTextView;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.LOCATION_SERVICE;
import static android.support.v4.content.PermissionChecker.checkSelfPermission;

public class aHomeFragment extends Fragment {
    private static final int REQ_CODE_COMMENT = 13;
    private static final int REQ_CODE_TAKE_PHOTO_INTENT_ID_STANDART = 1;
    private static final int REQ_CODE_POST_IMAGE = 12;
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10;
    private static final long MIN_TIME_BW_UPDATES = 1000 * 30;
    SQLiteHelperMethod db;
    private View view;
    private SwipeRefreshLayout homeSRLHomefvbi;
    private CustomScrollView homeNSVHomefvbi;
    private RecyclerView recyclerView;
    private LinearLayout homeLLWriteSomethingfvbi;
    private LinearLayout llLoadMode2fvbi;
    private LinearLayout homeLLLoadingfvbi;
    private ImageView homeIVOpenCamerafvbi;
    private ImageView homeIVEventfvbi;
    private ImageView homeIVEventBackgroundfvbi;
    private ImageView homeIVShareSomethingfvbi;
    private TextView homeTVTitleEventfvbi;
    private TextView homeTVDescEventfvbi;
    private HtmlTextView homeHtmlTVTitleEventfvbi;
    private HtmlTextView homeHtmlTVDescEventfvbi;
    private EditText homeETWritePostCreatefvbi;
    private Button homeBTapCheckInfvbi;
    private WebView homeWVTitleEventfvbi;
    private FloatingActionButton homeFABHomeUpfvbi;
    private UserWalletDataResponseModel walletData;
    private EventTheEvent theEventModel = null;
    private UserListEventResponseModel oneListEventModel = null;
    private GetListUserEventResponseModel userData = null;
    private HomeContentAdapter adapter;
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
    private boolean isNew;
    private String newPostImgPath;
    private boolean newPostImgExists;
    private double mylat, mylng;
    private LocationManager locationManager;
    private boolean isLocationFound;
    private boolean isGPSEnabled;
    private boolean isNetworkEnabled;
    private Location location;
    private GpsStatus gpsStatus;
    private boolean isTesting;
    private boolean isNoMoreShow = false;
    private String imageFilePath;
    private Uri imageUri;

    public aHomeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_a_home, container, false);

        db = new SQLiteHelperMethod(getActivity());

        accountId = SessionUtil.getStringPreferences(ISeasonConfig.KEY_ACCOUNT_ID, null);
        eventId = SessionUtil.getStringPreferences(ISeasonConfig.KEY_EVENT_ID, null);

        initComponent();
        initContent(container);
        initListener(view);

        return view;
    }

    private void initComponent() {
        homeLLLoadingfvbi = (LinearLayout) view.findViewById(R.id.homeLLLoading);
        homeFABHomeUpfvbi = (FloatingActionButton) view.findViewById(R.id.homeFABHomeUp);
        homeSRLHomefvbi = (SwipeRefreshLayout) view.findViewById(R.id.homeSRLHome);
        homeNSVHomefvbi = (CustomScrollView) view.findViewById(R.id.homeNSVHome);
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
        llLoadMode2fvbi = (LinearLayout) view.findViewById(R.id.llLoadMode2);
        homeLLWriteSomethingfvbi = (LinearLayout) view.findViewById(R.id.homeLLWriteSomething);
    }

    private void initContent(ViewGroup container) {
        locationManager = (LocationManager) getActivity().getSystemService(LOCATION_SERVICE);

        theEventModel = db.getTheEvent(eventId);
        userData = db.getOneUserData(accountId);
        oneListEventModel = db.getOneListEvent(eventId, accountId);

        updateEventInfo();//init content

        checkTheCheckIn();

        adapter = new HomeContentAdapter(getActivity(), menuUi, getContext(), eventId, accountId, theEventModel, oneListEventModel, new HomeContentAdapter.HomeContentListener() {
            @Override
            public void onCommentClick(HomeContentResponseModel model, int position) {
                moveToAHomeComment(model, position);
            }

            @Override
            public void onDeletePostClick(String id, int position) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
                alertDialogBuilder.setMessage("Are you sure to delete this post ?");
                alertDialogBuilder.setCancelable(true);
                alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        callPostCaptionDelete(id, position);
                    }
                });
                alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                    }
                });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();

            }
        });
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

        if (!isLocationFound) {
            getGpsLocation();
        }
    }

    private void initListener(View view) {
        homeIVOpenCamerafvbi.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (NetworkUtil.isConnected(getActivity())) {
                    if (Integer.parseInt(theEventModel.getAddpost_status()) == 0) {
                        dialogNoPostPermission();
                    } else {
                        openCamera();
                    }
                } else {
                    SystemUtil.showToast(getActivity(), "Check you connection", Toast.LENGTH_SHORT, Gravity.TOP);
                }
            }
        });
        homeIVShareSomethingfvbi.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (NetworkUtil.isConnected(getActivity())) {
                    if (!InputValidUtil.isEmptyField(homeETWritePostCreatefvbi)) {
                        if (Integer.parseInt(theEventModel.getAddpost_status()) == 0) {
                            dialogNoPostPermission();
                        } else {
                            callPostCaption();
                        }
                    } else {
                        InputValidUtil.errorET(homeETWritePostCreatefvbi, "This Empty");
                    }
                } else {
                    SystemUtil.showToast(getActivity(), "Check you connection", Toast.LENGTH_SHORT, Gravity.TOP);
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
                    if (NetworkUtil.isConnected(getActivity())) {
                        if (!last_data) {

                            llLoadMode2fvbi.setVisibility(View.VISIBLE);
                            homeNSVHomefvbi.setEnableScrolling(false);

                            new Handler().postDelayed(new Runnable() {
                                public void run() {
                                    //code here
                                    homeNSVHomefvbi.setNestedScrollingEnabled(false);
                                    callHomeContentAPILoadMore();
                                }
                            }, 500);

                        } else {
                            if (!isNoMoreShow) {
                                SystemUtil.showToast(getActivity(), "no more data", Toast.LENGTH_SHORT, Gravity.TOP);
                                isNoMoreShow = true;
                            }
                        }
                    } else {
                        SystemUtil.showToast(getActivity(), "Check you connection", Toast.LENGTH_SHORT, Gravity.TOP);
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
                    if (mylat != 0.0 || mylng != 0.0) {
                        callCheckDistanceCheckin(false);//
                    } else {
                        Log.e("Lihat", "onClick aHomeFragment : " + "Lokasi Anda Tidak Terdeteksi");
                        //SystemUtil.showToast(getActivity(), "Lokasi Anda Tidak Terdeteksi", Toast.LENGTH_SHORT,Gravity.TOP);
                    }
                } else {
                    SystemUtil.showToast(getActivity(), "Check you connection", Toast.LENGTH_SHORT, Gravity.TOP);// Set your own toast  message
                }
            }
        });
        homeFABHomeUpfvbi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                homeNSVHomefvbi.smoothScrollTo(0, 0);
            }
        });

        //testAPI
        homeIVEventfvbi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (NetworkUtil.isConnected(getActivity())) {
                    if (mylat != 0.0 || mylng != 0.0) {
                        callCheckDistanceCheckin(true);//
                    } else {
                        SystemUtil.showToast(getActivity(), "Lokasi Anda Tidak Terdeteksi", Toast.LENGTH_SHORT, Gravity.TOP);
                    }
                } else {
                    SystemUtil.showToast(getActivity(), "jaringan internet tidak tersedia", Toast.LENGTH_SHORT, Gravity.TOP);// Set your own toast  message
                }
            }
        });
    }

    private void dialogNoPostPermission() {
        new AlertDialog.Builder(getActivity())
                .setTitle("Information")
                .setMessage("You not have permission to post content")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //code here
                    }
                })
                .show();
    }

    private void openCamera() {
        if (!TextUtils.isEmpty(imageFilePath)) {
            PictureUtil.removeImageFromPathFile(imageFilePath);
        }

        String date = DateTimeUtil.dateToString(DateTimeUtil.currentDate(), new SimpleDateFormat("ddMMyy_HHmmss"));
        imageFilePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/picture_" + date + ".jpg";
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT) {
            imageUri = Uri.fromFile(new File(imageFilePath));
            //imageUri = FileProvider.getUriForFile(getActivity(), "com.gandsoft.openguide", new File(imageFilePath));
            Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            cameraIntent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, imageUri);
            startActivityForResult(cameraIntent, REQ_CODE_TAKE_PHOTO_INTENT_ID_STANDART);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            imageUri = FileProvider.getUriForFile(getActivity(), "com.gandsoft.openguide", new File(imageFilePath));
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            cameraIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            startActivityForResult(cameraIntent, REQ_CODE_TAKE_PHOTO_INTENT_ID_STANDART);
        } else {
            //OLD
            /*ContentValues values = new ContentValues();
            values.put(MediaStore.Images.Media.TITLE, "picture");
            values.put(MediaStore.Images.Media.DESCRIPTION, "From your Camera");
            imageUri = getActivity().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE_SECURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            startActivityForResult(intent, REQ_CODE_TAKE_PHOTO_INTENT_ID_STANDART);*/

            imageUri = FileProvider.getUriForFile(getActivity(), "com.gandsoft.openguide", new File(imageFilePath));
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            cameraIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            startActivityForResult(cameraIntent, REQ_CODE_TAKE_PHOTO_INTENT_ID_STANDART);
        }
    }

    private void updateEventInfo() {
        if (theEventModel != null) {
            homeHtmlTVTitleEventfvbi.setHtml(theEventModel.getEvent_name());
            homeTVTitleEventfvbi.setText(Html.fromHtml(theEventModel.getEvent_name()));
            homeTVDescEventfvbi.setText("" +
                    "" + Html.fromHtml(theEventModel.getEvent_location()) + "\n" +
                    "" + theEventModel.getDate_event() + "\n" +
                    "" + Html.fromHtml(theEventModel.getWeather()) + "");

            UserListEventResponseModel oneListEvent = db.getOneListEvent(eventId, accountId);
            String logo = AppUtil.validationStringImageIcon(getActivity(), oneListEvent.getLogo(), oneListEvent.getLogo_local(), false);
            String background = AppUtil.validationStringImageIcon(getActivity(), oneListEvent.getBackground(), oneListEvent.getBackground_local(), false);

            Glide.with(getActivity())
                    .load(InputValidUtil.isLinkUrl(logo) ? logo : new File(logo))
                    .asBitmap()
                    .error(R.drawable.template_account_og)
                    .placeholder(R.drawable.ic_action_name)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(false)
                    .dontAnimate()
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                            homeIVEventfvbi.setImageBitmap(resource);
                            if (NetworkUtil.isConnected(getActivity())) {
                                String imageCachePath = PictureUtil.saveImageLogoBackIcon(getActivity(), resource, eventId + "_Logo_image");
                                db.saveEventLogoPicture(imageCachePath, accountId, eventId);
                            }
                        }
                    });
            Glide.with(getActivity())
                    .load(InputValidUtil.isLinkUrl(background) ? background : new File(background))
                    .asBitmap()
                    .error(R.drawable.template_account_og)
                    .placeholder(R.drawable.ic_action_name)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(false)
                    .dontAnimate()
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                            homeIVEventBackgroundfvbi.setImageBitmap(resource);
                            if (NetworkUtil.isConnected(getActivity())) {
                                String imageCachePath = PictureUtil.saveImageLogoBackIcon(getActivity(), resource, eventId + "_Background_image");
                                db.saveEventBackgroundPicture(imageCachePath, accountId, eventId);
                            }
                        }
                    });

            /*if (Integer.parseInt(theEventModel.getAddpost_status()) == 0) {
                homeLLWriteSomethingfvbi.setVisibility(View.GONE);
            }*/
        } else {
            SystemUtil.showToast(getActivity(), "data kosong", Toast.LENGTH_LONG, Gravity.TOP);
        }
    }

    private void checkTheCheckIn() {

        /*INIT VALIDATE HAS CHECKIN*/
        /*Log.d("Lihat", "initContent aHomeFragment : " + "\\");// = \
        Log.d("Lihat", "initContent aHomeFragment : " + "\\\\");// = \\
        Log.d("Lihat", "initContent aHomeFragment : " + "\"");// = "
        Log.d("Lihat", "initContent aHomeFragment : " + "\\\"");// = \"*/

        walletData = db.getWalletDataIdCard(eventId);
        String replace1 = walletData.getBody_wallet().replace("\\", " ");
        String replace2 = replace1.replace("\"", " ");
        String replace3 = replace2.replace("status-checkin , ", "status-checkin,");
        String replace4 = replace3.replace("status-feedback , ", "status-feedback,");
        //Log.d("Lihat", "initContent aHomeFragment replace4.matches : " + (replace4.matches("(?i).*status-checkin,0.*")));
        //Log.d("Lihat", "initContent aHomeFragment replace4.matches : " + (replace4.matches("(?i).*status-checkin,1.*")));

        /*INIT VALIDATE DATE*/
        Date currentDate = DateTimeUtil.currentDate();
        //Log.d("Lihat", "checkTheCheckIn aHomeFragment currentDate: " + currentDate);

        //
        /*Date day3BeforecurrentDate = DateTimeUtil.addDays(currentDate, -3);
        Log.d("Lihat", "checkTheCheckIn aHomeFragment day3BeforecurrentDate: " + day3BeforecurrentDate);
        Date day7AftercurrentDate = DateTimeUtil.addDays(currentDate, 7);
        Log.d("Lihat", "checkTheCheckIn aHomeFragment day7AftercurrentDate: " + day7AftercurrentDate);

        Log.d("Lihat", "checkTheCheckIn aHomeFragment DateTimeUtil.isNowAfterDate(day3BeforecurrentDate: " + DateTimeUtil.isNowAfterDate(day3BeforecurrentDate));
        Log.d("Lihat", "checkTheCheckIn aHomeFragment DateTimeUtil.isNowAfterDate(day7AftercurrentDate: " + DateTimeUtil.isNowAfterDate(day7AftercurrentDate));
        Log.d("Lihat", "checkTheCheckIn aHomeFragment DateTimeUtil.isNowBeforeDate(day3BeforecurrentDate: " + DateTimeUtil.isNowBeforeDate(day3BeforecurrentDate));
        Log.d("Lihat", "checkTheCheckIn aHomeFragment DateTimeUtil.isNowBeforeDate(day7AftercurrentDate: " + DateTimeUtil.isNowBeforeDate(day7AftercurrentDate));

        Log.d("Lihat", "checkTheCheckIn aHomeFragment DateTimeUtil.isWithinDaysFuture(DateTimeUtil: " + DateTimeUtil.isWithinDaysFuture(DateTimeUtil.stringToDate("20180929", new SimpleDateFormat("yyyyMMdd")), 1));//28
        Log.d("Lihat", "checkTheCheckIn aHomeFragment DateTimeUtil.isWithinDaysFuture(DateTimeUtil: " + DateTimeUtil.isWithinDaysFuture(DateTimeUtil.stringToDate("20180929", new SimpleDateFormat("yyyyMMdd")), 2));//29
        Log.d("Lihat", "checkTheCheckIn aHomeFragment DateTimeUtil.isWithinDaysFuture(DateTimeUtil: " + DateTimeUtil.isWithinDaysFuture(DateTimeUtil.stringToDate("20180929", new SimpleDateFormat("yyyyMMdd")), 3));//30
        Log.d("Lihat", "checkTheCheckIn aHomeFragment DateTimeUtil.isWithinDaysFuture(DateTimeUtil: " + DateTimeUtil.isWithinDaysFuture(DateTimeUtil.stringToDate("20181112", new SimpleDateFormat("yyyyMMdd")), 3));//30
        Log.d("Lihat", "checkTheCheckIn aHomeFragment DateTimeUtil.isWithinDaysFuture(DateTimeUtil: " + DateTimeUtil.isWithinDaysFuture(DateTimeUtil.stringToDate("20181112", new SimpleDateFormat("yyyyMMdd")), 100));//30

        Log.d("Lihat", "checkTheCheckIn aHomeFragment DateTimeUtil.isWithinDaysPast(DateTimeUtil: " + DateTimeUtil.isWithinDaysPast(DateTimeUtil.stringToDate("20180924", new SimpleDateFormat("yyyyMMdd")), -1));//26
        Log.d("Lihat", "checkTheCheckIn aHomeFragment DateTimeUtil.isWithinDaysPast(DateTimeUtil: " + DateTimeUtil.isWithinDaysPast(DateTimeUtil.stringToDate("20180924", new SimpleDateFormat("yyyyMMdd")), -2));//25
        Log.d("Lihat", "checkTheCheckIn aHomeFragment DateTimeUtil.isWithinDaysPast(DateTimeUtil: " + DateTimeUtil.isWithinDaysPast(DateTimeUtil.stringToDate("20180924", new SimpleDateFormat("yyyyMMdd")), -3));//24
        Log.d("Lihat", "checkTheCheckIn aHomeFragment DateTimeUtil.isWithinDaysPast(DateTimeUtil: " + DateTimeUtil.isWithinDaysPast(DateTimeUtil.stringToDate("20180924", new SimpleDateFormat("yyyyMMdd")), -4));//23
        Log.d("Lihat", "checkTheCheckIn aHomeFragment DateTimeUtil.isWithinDaysPast(DateTimeUtil: " + DateTimeUtil.isWithinDaysPast(DateTimeUtil.stringToDate("20180924", new SimpleDateFormat("yyyyMMdd")), -5));//22

        Log.d("Lihat", "checkTheCheckIn aHomeFragment DateTimeUtil.getDates1: " + DateTimeUtil.getDates(new Date(),DateTimeUtil.stringToDate("20181112",new SimpleDateFormat("yyyyMMdd"))));
        Log.d("Lihat", "checkTheCheckIn aHomeFragment DateTimeUtil.getDates2: " + DateTimeUtil.getDates(new Date(),DateTimeUtil.stringToDate("20180929",new SimpleDateFormat("yyyyMMdd"))));

        Log.d("Lihat", "checkTheCheckIn aHomeFragment : " + DateTimeUtil.getDayBetween2Date(new Date(),DateTimeUtil.stringToDate("20181112",new SimpleDateFormat("yyyyMMdd"))));*/
        //
        String startDate = db.getOneListEvent(eventId, accountId).getStart_date();//"yyyyMMdd"
        String startDateYearMonth = startDate.substring(0, 6);//"yyyyMM"
        //Log.d("Lihat", "checkTheCheckIn aHomeFragment startDateYearMonth: " + startDateYearMonth);

        String dateStartEnd = db.getOneListEvent(eventId, accountId).getDate();//"dd-dd mm yyyy"
        String[] dates = dateStartEnd.split(" ");
        String daysEvent = dates[0];
        String[] daysEvents = daysEvent.split("-");
        String startDay = daysEvents[0];
        String startDay1 = startDay.length() == 1 ? "0" + startDay : startDay;
        String endDay = daysEvents[1];
        String endDay1 = endDay.length() == 1 ? "0" + endDay : endDay;
        //Log.d("Lihat", "checkTheCheckIn aHomeFragment startDay1: " + startDay1);
        //Log.d("Lihat", "checkTheCheckIn aHomeFragment endDay1: " + endDay1);

        String startDayComplete = startDateYearMonth + startDay1 + " 00:00:00";//"yyyyMMdd HH:mm:ss"
        //Log.d("Lihat", "checkTheCheckIn aHomeFragment startDayComplete: " + startDayComplete);
        Date startDayCompleteD = DateTimeUtil.stringToDate(startDayComplete, new SimpleDateFormat("yyyyMMdd HH:mm:ss"));
        //Log.d("Lihat", "checkTheCheckIn aHomeFragment startDayCompleteD: " + startDayCompleteD);
        String endDayComplete = startDateYearMonth + endDay1 + " 23:59:59";//"yyyyMMdd HH:mm:ss"
        //Log.d("Lihat", "checkTheCheckIn aHomeFragment endDayComplete: " + endDayComplete);
        Date endDayCompleteD = DateTimeUtil.stringToDate(endDayComplete, new SimpleDateFormat("yyyyMMdd HH:mm:ss"));
        //Log.d("Lihat", "checkTheCheckIn aHomeFragment endDayCompleteD: " + endDayCompleteD);

        Date day3BeforeStart = DateTimeUtil.addDays(startDayCompleteD, -3);
        //Log.d("Lihat", "checkTheCheckIn aHomeFragment day3BeforeStart: " + day3BeforeStart);
        Date day7AfterEnd = DateTimeUtil.addDays(endDayCompleteD, 7);
        //Log.d("Lihat", "checkTheCheckIn aHomeFragment day7AfterEnd: " + day7AfterEnd);

        /*EXECUTE*/
        if (replace4.matches("(?i).*status-checkin,1.*") || !DateTimeUtil.isBetween2Date(day3BeforeStart, endDayCompleteD)) {
            homeBTapCheckInfvbi.setVisibility(View.GONE);
        }
    }

    private void callCheckDistanceCheckin(boolean isTesting) {
        GetCheckDistanceLocationRequestModel requestModel = new GetCheckDistanceLocationRequestModel();
        requestModel.setEvent_id(eventId);
        requestModel.setAccount_id(accountId);
        requestModel.setMy_latitude(String.valueOf(mylat));
        requestModel.setMy_longtitude(String.valueOf(mylng));
        requestModel.setDbver(String.valueOf(IConfig.DB_Version));

        API.doGetCheckDistanceLocationRet(requestModel).enqueue(new Callback<List<GetCheckDistanceLocationResponseModel>>() {
            @Override
            public void onResponse(Call<List<GetCheckDistanceLocationResponseModel>> call, Response<List<GetCheckDistanceLocationResponseModel>> response) {
                if (!isTesting) {
                    //progressDialog.dismiss();
                    if (response.isSuccessful()) {
                        List<GetCheckDistanceLocationResponseModel> mods = response.body();
                        if (mods.size() == 1) {
                            for (int i = 0; i < mods.size(); i++) {
                                GetCheckDistanceLocationResponseModel mod1 = mods.get(i);
                                if (mod1.getStatus().equalsIgnoreCase(ISeasonConfig.SUCCESS) && mod1.getIsrange()) {
                                    callCheckIn();
                                }
                            }
                        } else {
                            Log.d("Lihat", "onResponse aHomeFragment : " + response.message());
                            //SystemUtil.showToast(getApplicationContext(), "error data", Snackbar.LENGTH_LONG,Gravity.TOP);
                            //Crashlytics.logException(new Exception(response.message()));
                        }
                    } else {
                        Log.d("Lihat", "onResponse aHomeFragment : " + response.message());
                        //SystemUtil.showToast(getApplicationContext(), "Failed Connection To Server", Toast.LENGTH_SHORT,Gravity.TOP);
                        //Crashlytics.logException(new Exception(response.message()));
                    }
                }
            }

            @Override
            public void onFailure(Call<List<GetCheckDistanceLocationResponseModel>> call, Throwable t) {
                //progressDialog.dismiss();
                Log.d("Lihat", "onFailure aHomeFragment : " + t.getMessage());
                SystemUtil.showToast(getActivity(), "Failed Connection To Server", Toast.LENGTH_SHORT, Gravity.TOP);
                /*SystemUtil.showToast(getApplicationContext(), "Failed Connection To Server", Snackbar.LENGTH_SHORT).setAction("Reload", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                }).show();*/
                //Crashlytics.logException(new Exception(t.getMessage()));
            }
        });
    }

    private void callCheckIn() {
        if (NetworkUtil.isConnected(getActivity())) {
            HomeContentCheckinRequestModel requestModel = new HomeContentCheckinRequestModel();
            requestModel.setId_event(eventId);
            requestModel.setPhonenumber(accountId);
            requestModel.setDbver("3");
            //requestModel.setDate_gmt(formattedDateGMT);
            requestModel.setDate_gmt("+07");
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
                                    SystemUtil.showToast(getActivity(), "Tersimpan", Toast.LENGTH_LONG, Gravity.TOP);

                                    callHomeContentAPI();//checkin
                                } else {
                                    SystemUtil.showToast(getActivity(), "Bad Response", Toast.LENGTH_LONG, Gravity.TOP);
                                }
                            }
                        } else {
                            SystemUtil.showToast(getActivity(), "Data Tidak Sesuai", Toast.LENGTH_LONG, Gravity.TOP);
                        }
                    } else {
                        SystemUtil.showToast(getActivity(), response.message(), Toast.LENGTH_LONG, Gravity.TOP);
                    }
                }

                @Override
                public void onFailure(Call<List<LocalBaseResponseModel>> call, Throwable t) {
                    SystemUtil.showToast(getActivity(), t.getMessage(), Toast.LENGTH_LONG, Gravity.TOP);
                }
            });
        } else {
            SystemUtil.showToast(getActivity(), "Check Your Connection", Toast.LENGTH_SHORT, Gravity.TOP);
        }

    }

    private void callHomeContentAPI() {
        if (NetworkUtil.isConnected(getActivity())) {
            deleteExistImg();

            //deleteImageFile(event_Id);
            db.deleleDataByKey(SQLiteHelper.TableHomeContent, SQLiteHelper.Key_HomeContent_EventId, eventId);

            homeLLLoadingfvbi.setVisibility(View.VISIBLE);
            homeNSVHomefvbi.setEnableScrolling(false);

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

                    new Handler().postDelayed(new Runnable() {
                        public void run() {
                            //code here
                            homeLLLoadingfvbi.setVisibility(View.GONE);
                            homeNSVHomefvbi.setEnableScrolling(true);
                        }
                    }, 3000);

                    if (response.isSuccessful()) {
                        List<HomeContentResponseModel> models = response.body();
                        db.deleteAllDataHomeContent(eventId);
                        for (int i1 = 0; i1 < models.size(); i1++) {
                            HomeContentResponseModel responseModel = models.get(i1);
                            db.saveHomeContent(responseModel, eventId);
                            /*if (db.isDataTableValueMultipleNull(SQLiteHelper.TableHomeContent, SQLiteHelper.Key_HomeContent_EventId, SQLiteHelper.Key_HomeContent_id, eventId, responseModel.getId())) {
                                db.saveHomeContent(responseModel, eventId);
                            } else {
                                db.saveHomeContent(responseModel, eventId);
                            }*/
                        }

                        adapter.setData(models);
                        if (models.size() == 10) {
                            last_data = false;
                            last_id = models.get(models.size() - 1).getId();
                            last_date = models.get(models.size() - 1).getDate_created();
                            first_id = models.get(0).getId();
                        } else {
                            last_data = true;
                            last_id = "";
                            last_date = "";
                            first_id = "";
                        }
                    } else {
                        SystemUtil.showToast(getActivity(), "Failed Connection To Server", Toast.LENGTH_SHORT, Gravity.TOP);
                    }
                }

                @Override
                public void onFailure(Call<List<HomeContentResponseModel>> call, Throwable t) {

                    new Handler().postDelayed(new Runnable() {
                        public void run() {
                            //code here
                            homeLLLoadingfvbi.setVisibility(View.GONE);
                            homeNSVHomefvbi.setEnableScrolling(true);
                        }
                    }, 3000);

                    //progressDialog.dismiss();
                    Log.d("Lihat", "onFailure aHomeFragment : " + t.getMessage());
                    SystemUtil.showToast(getActivity(), "Failed Connection To Server", Toast.LENGTH_SHORT, Gravity.TOP);
                    /*SystemUtil.showToast(getApplicationContext(), "Failed Connection To Server", Snackbar.LENGTH_SHORT).setAction("Reload", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                        }
                    }).show();*/
                    //Crashlytics.logException(new Exception(t.getMessage()));
                }
            });
        } else {
            ArrayList<HomeContentResponseModel> homeContent = db.getHomeContent10(eventId);
            if (homeContent.size() != 0) {
                adapter.setData(homeContent);
                last_data = false;
                last_id = homeContent.get(homeContent.size() - 1).getId();
                last_date = homeContent.get(homeContent.size() - 1).getDate_created();
                first_id = homeContent.get(0).getId();

                Log.d("Lihat", "callHomeContentAPI aHomeFragment : " + homeContent.size());

            } else {
                SystemUtil.showToast(getActivity(), "No data", Toast.LENGTH_SHORT, Gravity.TOP);
            }
        }
    }

    private void callHomeContentAPILoadMore() {
        if (NetworkUtil.isConnected(getActivity())) {
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

                    new Handler().postDelayed(new Runnable() {
                        public void run() {
                            llLoadMode2fvbi.setVisibility(View.GONE);
                            homeNSVHomefvbi.setEnableScrolling(true);
                        }
                    }, 3000);

                    if (response.isSuccessful()) {
                        List<HomeContentResponseModel> models = response.body();
                        for (int i1 = 0; i1 < models.size(); i1++) {
                            HomeContentResponseModel responseModel = models.get(i1);
                            if (db.isDataTableValueMultipleNull(SQLiteHelper.TableHomeContent, SQLiteHelper.Key_HomeContent_EventId, SQLiteHelper.Key_HomeContent_id, eventId, responseModel.getId())) {
                                db.saveHomeContent(responseModel, eventId);
                            } else {
                                db.updateHomeContent(responseModel, eventId);
                            }
                        }

                        adapter.addDatas(models);
                        if (models.size() == 10) {
                            last_data = false;
                            last_id = models.get(models.size() - 1).getId();
                            last_date = models.get(models.size() - 1).getDate_created();
                            first_id = models.get(0).getId();
                        } else {
                            last_data = true;
                            last_id = "";
                            last_date = "";
                            first_id = "";
                        }
                    } else {
                        homeNSVHomefvbi.setNestedScrollingEnabled(true);
                        SystemUtil.showToast(getActivity(), "Failed Connection To Server", Toast.LENGTH_SHORT, Gravity.TOP);
                    }
                }

                @Override
                public void onFailure(Call<List<HomeContentResponseModel>> call, Throwable t) {

                    new Handler().postDelayed(new Runnable() {
                        public void run() {
                            llLoadMode2fvbi.setVisibility(View.GONE);
                            homeNSVHomefvbi.setEnableScrolling(true);
                        }
                    }, 3000);

                    //progressDialog.dismiss();
                    Log.d("Lihat", "onFailure aHomeFragment : " + t.getMessage());
                    SystemUtil.showToast(getActivity(), "Failed Connection To Server", Toast.LENGTH_SHORT, Gravity.TOP);
                    /*SystemUtil.showToast(getApplicationContext(), "Failed Connection To Server", Snackbar.LENGTH_SHORT).setAction("Reload", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                        }
                    }).show();*/
                    //Crashlytics.logException(new Exception(t.getMessage()));
                }
            });
        } else {
            llLoadMode2fvbi.setVisibility(View.GONE);
            homeNSVHomefvbi.setEnableScrolling(true);
            SystemUtil.showToast(getActivity(), "Check you connection", Toast.LENGTH_SHORT, Gravity.TOP);
        }

    }

    private void callPostCaption() {
        HomeContentPostCaptionSetRequestModel requestModel = new HomeContentPostCaptionSetRequestModel();
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
                                SystemUtil.showToast(getActivity(), "Post Terkirim", Toast.LENGTH_LONG, Gravity.TOP);
                                callHomeContentAPI();
                                updateEventInfo();//onresponse callPostCaption
                                homeETWritePostCreatefvbi.setText("");
                                SystemUtil.hideKeyBoard(getActivity());
                            } else {
                                SystemUtil.showToast(getActivity(), "Post Bad Response", Toast.LENGTH_LONG, Gravity.TOP);
                            }
                        }
                    } else {
                        SystemUtil.showToast(getActivity(), "Post Data Tidak Sesuai", Toast.LENGTH_LONG, Gravity.TOP);
                    }
                } else {
                    SystemUtil.showToast(getActivity(), response.message(), Toast.LENGTH_LONG, Gravity.TOP);
                }
            }

            @Override
            public void onFailure(Call<List<LocalBaseResponseModel>> call, Throwable t) {
                SystemUtil.showToast(getActivity(), t.getMessage(), Toast.LENGTH_LONG, Gravity.TOP);
            }
        });

    }

    private void callPostCaptionDelete(String id, int position) {
        if (NetworkUtil.isConnected(getActivity())) {
            ProgressDialog progressDialog = ProgressDialog.show(getActivity(), "Loading...", "Please Wait..", false, false);

            HomeContentPostCaptionDeleteRequestModel rm = new HomeContentPostCaptionDeleteRequestModel();
            rm.setEvent_id(eventId);
            rm.setPost_id(id);
            rm.setAccount_id(accountId);
            rm.setDbver(String.valueOf(IConfig.DB_Version));

            API.dohomeContentPostDeleteRet(rm).enqueue(new Callback<List<HomeContentPostCaptionDeleteResponseModel>>() {
                @Override
                public void onResponse(Call<List<HomeContentPostCaptionDeleteResponseModel>> call, Response<List<HomeContentPostCaptionDeleteResponseModel>> response) {
                    progressDialog.dismiss();
                    if (response.isSuccessful()) {
                        List<HomeContentPostCaptionDeleteResponseModel> q = response.body();
                        if (q.size() == 1) {
                            if (q.get(0).getStatus().equalsIgnoreCase(ISeasonConfig.SUCCESS)) {
                                //
                                adapter.removeAt(position);
                            } else {
                                SystemUtil.showToast(getActivity(), response.message(), Toast.LENGTH_LONG, Gravity.TOP);
                            }
                        }
                    } else {
                        Log.d("Lihat", "onFailure aHomeFragment : " + response.message());
                        SystemUtil.showToast(getActivity(), "Failed Connection To Server", Toast.LENGTH_SHORT, Gravity.TOP);
                    }
                }

                @Override
                public void onFailure(Call<List<HomeContentPostCaptionDeleteResponseModel>> call, Throwable t) {
                    progressDialog.dismiss();
                    Log.d("Lihat", "onFailure aHomeFragment : " + t.getMessage());
                    SystemUtil.showToast(getActivity(), "Failed Connection To Server", Toast.LENGTH_SHORT, Gravity.TOP);
                }
            });

        } else {
            SystemUtil.showToast(getActivity(), "Check you connection", Toast.LENGTH_SHORT, Gravity.TOP);
        }
    }

    private void callPostImageCaption(String imgUrl, String contentPic, String base64) {

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
        //requestModel.setGmt_date(formattedDateGMT);
        requestModel.setGmt_date("+07");
        requestModel.setDate_post(formattedDate);
        requestModel.setImagedata(base64);
        requestModel.setDegree("ANDROID");
        requestModel.setDbver("3");

        addSingleTopAdapter(imgUrl, contentPic, uniqueId, eventId, accountId, isNew);

        API.doHomeContentPostImageCaptionRet(requestModel).enqueue(new Callback<List<LocalBaseResponseModel>>() {
            @Override
            public void onResponse(Call<List<LocalBaseResponseModel>> call, Response<List<LocalBaseResponseModel>> response) {
                if (response.isSuccessful()) {
                    List<LocalBaseResponseModel> models = response.body();
                    for (LocalBaseResponseModel model : models) {
                        if (model.getStatus().equalsIgnoreCase(ISeasonConfig.OK)) {
                            //
                            adapter.changeDataFirstHasUploaded();
                        } else {
                            SystemUtil.showToast(getActivity(), model.getMessage(), Toast.LENGTH_LONG, Gravity.TOP);
                        }
                    }
                } else {
                    Log.d("Lihat", "onFailure aHomeFragment : " + response.message());
                    SystemUtil.showToast(getActivity(), "Failed Connection To Server", Toast.LENGTH_SHORT, Gravity.TOP);
                }
            }

            @Override
            public void onFailure(Call<List<LocalBaseResponseModel>> call, Throwable t) {
                Log.d("Lihat", "onFailure aHomeFragment : " + t.getMessage());
                SystemUtil.showToast(getActivity(), "Failed Connection To Server", Toast.LENGTH_SHORT, Gravity.TOP);
                adapter.removeDataFirst();
            }
        });
    }

    private void addSingleTopAdapter(String imgUrl, String contentPic, String uniqueId, String eventId, String accountId, boolean isNew) {

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
        model.setImage_posted(imgUrl);
        model.setImage_posted_local("");
        if (!TextUtils.isEmpty(contentPic)) {
            model.setKeterangan(contentPic);
        } else {
            model.setKeterangan("");
        }
        model.setNew_event("");
        model.setIs_new(true);

        adapter.addDataFirst(model);
    }

    private void moveToAHomePostImage(String imageurl) {
        Intent intent = new Intent(getActivity(), aHomePostImageCaptionActivity.class);
        intent.putExtra(ISeasonConfig.INTENT_PARAM, imageurl);
        startActivityForResult(intent, REQ_CODE_POST_IMAGE);
    }

    private void moveToAHomeComment(HomeContentResponseModel model, int position) {
        if (NetworkUtil.isConnected(getActivity())) {
            ArrayList<HomeContentCommentModelParcelable> dataParam = new ArrayList<>();
            HomeContentCommentModelParcelable mode = new HomeContentCommentModelParcelable();
            mode.setId(model.getId());
            mode.setLike(model.getLike());
            mode.setAccount_id(model.getAccount_id());
            mode.setTotal_comment(model.getTotal_comment());
            mode.setStatus_like(model.getStatus_like());
            mode.setUsername(model.getUsername());
            mode.setJabatan(model.getJabatan());
            mode.setDate_created(model.getDate_created());
            mode.setImage_icon(model.getImage_icon());
            mode.setImage_icon_local(model.getImage_icon_local());
            mode.setImage_posted(model.getImage_posted());
            mode.setImage_posted_local(model.getImage_posted_local());
            mode.setKeterangan(model.getKeterangan());
            mode.setKeterangan(model.getEvent());
            mode.setNew_event(model.getNew_event());
            if (dataParam.size() > 0) {
                dataParam.clear();
                dataParam.add(mode);
            } else {
                dataParam.add(mode);
            }
            Intent intent = new Intent(getActivity(), aHomePostCommentActivity.class);
            intent.putParcelableArrayListExtra(ISeasonConfig.INTENT_PARAM, dataParam);
            intent.putExtra(ISeasonConfig.INTENT_PARAM2, position);
            startActivityForResult(intent, REQ_CODE_COMMENT);
        } else {
            SystemUtil.showToast(getActivity(), "Check you connection", Toast.LENGTH_SHORT, Gravity.TOP);
        }
    }

    private void getGpsLocation() {
        LocationListener clubbing = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

                mylat = location.getLatitude();
                mylng = location.getLongitude();
                locationManager.removeUpdates(this);
                //etDataKoordinatNamaJalan.setText(GeocoderUtil.getAddressFromlatlng(SearchActivity.this, mylat, mylng));
                //SystemUtil.showToast(SearchActivity.this, R.string.gps_found, Toast.LENGTH_LONG);
                // toast.setGravity(Gravity.TOP, 0, 0);
                // toast.show();

                isLocationFound = true;
                int i = 1;

                if (gpsStatus != null) {
                    Iterable<GpsSatellite> satellites = gpsStatus.getSatellites();
                    Iterator<GpsSatellite> sat = satellites.iterator();
                    i--;
                    while (sat.hasNext()) {
                        sat.next();
                        i++;
                    }
                }
                //tvDataKoordinatSat.setText("Sat : " + i);
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {
                //startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            }
        };

        if (checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (!isGPSEnabled && !isNetworkEnabled) {
                SystemUtil.showToast(getActivity(), "no network provider is enabled", Toast.LENGTH_SHORT, Gravity.TOP);
                isLocationFound = true;
            } else {
                if (isNetworkEnabled) {
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, clubbing);
                    Log.d("Network", "Network");
                    if (locationManager != null) {
                        location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        if (location != null) {
                            mylat = location.getLatitude();
                            mylng = location.getLongitude();
                        } else {
                            //SystemUtil.showToast(getActivity(), "Can't Reach GPS", Toast.LENGTH_SHORT, Gravity.TOP);
                            Log.d("Lihat", "getGpsLocation aHomeFragment : " + "Can't Reach location via network");
                        }
                    } else {
                        //SystemUtil.showToast(getActivity(), "Can't Reach GPS", Toast.LENGTH_SHORT, Gravity.TOP);
                        Log.d("Lihat", "getGpsLocation aHomeFragment : " + "Can't Reach locationManager via network");
                    }
                }
                // if GPS Enabled get lat/long using GPS Services
                if (isGPSEnabled) {
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, clubbing);
                    Log.d("GPS Enabled", "GPS Enabled");
                    if (locationManager != null) {
                        location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                        if (location != null) {
                            mylat = location.getLatitude();
                            mylng = location.getLongitude();
                        } else {
                            //SystemUtil.showToast(getActivity(), "Can't Reach GPS", Toast.LENGTH_SHORT, Gravity.TOP);
                            Log.d("Lihat", "getGpsLocation aHomeFragment : " + "Can't Reach location via GPS");
                        }
                    } else {
                        //SystemUtil.showToast(getActivity(), "Can't Reach GPS", Toast.LENGTH_SHORT, Gravity.TOP);
                        Log.d("Lihat", "getGpsLocation aHomeFragment : " + "Can't Reach locationManager via GPS");
                    }
                }
            }
            gpsStatus = locationManager.getGpsStatus(null);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_CODE_TAKE_PHOTO_INTENT_ID_STANDART && resultCode == Activity.RESULT_OK) {
            //OLD
            //String imagePath = PictureUtil.getImagePathFromUri(getActivity(), imageUri);
            Uri imageUri1 = Uri.parse(imageFilePath);
            moveToAHomePostImage(imageUri1.getPath());
        } else if (requestCode == REQ_CODE_POST_IMAGE) {
            if (resultCode == RESULT_OK) {
                String contentPic;
                if (data.hasExtra(ISeasonConfig.INTENT_PARAM) && data.hasExtra(ISeasonConfig.INTENT_PARAM2)) {
                    newPostImgPath = data.getStringExtra(ISeasonConfig.INTENT_PARAM);
                    newPostImgExists = true;
                    Log.d("Lihat", "onActivityResult aHomeFragment : " + newPostImgPath);
                    contentPic = data.getStringExtra(ISeasonConfig.INTENT_PARAM2);

                    Glide.with(getActivity())
                            .load(new File(newPostImgPath))
                            .asBitmap()
                            .error(R.drawable.template_account_og)
                            .placeholder(R.drawable.ic_action_name)
                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                            .skipMemoryCache(false)
                            .dontAnimate()
                            .into(new SimpleTarget<Bitmap>() {
                                @Override
                                public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                                    Bitmap resizeImage = PictureUtil.resizeImageBitmap(resource, 1080);
                                    String base64 = PictureUtil.bitmapToBase64(resizeImage, Bitmap.CompressFormat.JPEG, 100);
                                    callPostImageCaption(newPostImgPath, contentPic, base64);
                                }
                            });
                }
            }
        } else if (requestCode == REQ_CODE_COMMENT && resultCode == Activity.RESULT_OK && NetworkUtil.isConnected(getActivity())) {
            int position = data.getIntExtra(ISeasonConfig.INTENT_PARAM, 0);
            String totalComment = data.getStringExtra(ISeasonConfig.INTENT_PARAM2);
            adapter.changeTotalComment(position, totalComment);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        deleteExistImg();
    }

    private void deleteExistImg() {
        if (newPostImgExists) {
            PictureUtil.removeImageFromPathFile(newPostImgPath);
            newPostImgExists = false;
        }
    }
}


