package com.gandsoft.openguide.view.main.fragments;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.gandsoft.openguide.API.APIresponse.UserData.GetListUserEventResponseModel;
import com.gandsoft.openguide.ISeasonConfig;
import com.gandsoft.openguide.R;
import com.gandsoft.openguide.database.SQLiteHelperMethod;
import com.gandsoft.openguide.support.AppUtil;
import com.gandsoft.openguide.support.InputValidUtil;
import com.gandsoft.openguide.support.ListArrayUtil;
import com.gandsoft.openguide.support.NetworkUtil;
import com.gandsoft.openguide.support.PictureUtil;
import com.gandsoft.openguide.support.SessionUtil;
import com.gandsoft.openguide.view.AccountActivity;
import com.gandsoft.openguide.view.ChangeEventActivity;
import com.gandsoft.openguide.view.infomenu.aMapActivity;
import com.gandsoft.openguide.view.infomenu.cInboxActivity;
import com.gandsoft.openguide.view.infomenu.dComitteContactActivity;
import com.gandsoft.openguide.view.infomenu.eEmergenciesActivity;
import com.gandsoft.openguide.view.infomenu.fPracticalInfoActivity;
import com.gandsoft.openguide.view.infomenu.gSurroundingAreaActivity;
import com.gandsoft.openguide.view.infomenu.gallery2.GalleryActivity;
import com.gandsoft.openguide.view.infomenu.hFeedbackActivity;
import com.gandsoft.openguide.view.main.BaseHomeActivity;
import com.gandsoft.openguide.view.main.adapter.InfoListViewAdapter;
import com.gandsoft.openguide.view.main.adapter.InfoListviewModel;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.gandsoft.openguide.database.SQLiteHelper.Key_AreaNew_EventId;
import static com.gandsoft.openguide.database.SQLiteHelper.Key_Contact_List_EventId;
import static com.gandsoft.openguide.database.SQLiteHelper.Key_Emergencie_EventId;
import static com.gandsoft.openguide.database.SQLiteHelper.Key_Important_InfoNew_EventId;
import static com.gandsoft.openguide.database.SQLiteHelper.TableAreaNew;
import static com.gandsoft.openguide.database.SQLiteHelper.TableContactList;
import static com.gandsoft.openguide.database.SQLiteHelper.TableEmergencie;
import static com.gandsoft.openguide.database.SQLiteHelper.TableImportantInfoNew;

public class fInfoFragment extends Fragment implements InfoListViewAdapter.ListAdapterListener {
    private static final int REQ_CODE_INBOX = 123;
    private static final int REQ_CODE_ACCOUNT = 1234;
    private static final int ID_NOTIF = 0;
    Button bMyPro;
    InfoListViewAdapter adapter;
    SQLiteHelperMethod db;
    String infoMenu[] = {
            "Map",
            "Gallery",
            "Inbox",
            "Comitee Contact",
            "Emergencies",
            "Practical Information",
            "Surrounding Area",
            "Feedback",
            "Change Event"
    };
    int infoPic[] = {
            R.drawable.ic_option_location,
            R.drawable.ic_option_image,
            R.drawable.ic_option_inbox,
            R.drawable.ic_option_contact,
            R.drawable.ic_option_emergency,
            R.drawable.ic_option_information,
            R.drawable.ic_option_surrouncding_area,
            R.drawable.ic_option_feedback,
            R.drawable.ic_option_event_change
    };
    private RecyclerView rvMenufvbi;
    private ImageView ivInfoUserImagefvbi;
    private TextView tvInfoFullNamefvbi, tvInfoUserNamefvbi, tvInfoUserPhoneNumberfvbi;
    private Button button;
    private String accountId, eventId;
    private int version_data_event;
    private List<InfoListviewModel> listviewModels = new ArrayList<>();
    private NotificationManager notificationManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        db = new SQLiteHelperMethod(getActivity());
        View view = inflater.inflate(R.layout.fragment_f_info, container, false);

        accountId = SessionUtil.getStringPreferences(ISeasonConfig.KEY_ACCOUNT_ID, null);
        eventId = SessionUtil.getStringPreferences(ISeasonConfig.KEY_EVENT_ID, null);

        notificationManager = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);

        initComponent(view);
        initContent();
        initListener();

        return view;
    }

    private void initComponent(View view) {
        button = (Button) view.findViewById(R.id.button);
        rvMenufvbi = (RecyclerView) view.findViewById(R.id.rvMenu);

        ivInfoUserImagefvbi = (ImageView) view.findViewById(R.id.ivInfoUserImage);
        tvInfoUserNamefvbi = (TextView) view.findViewById(R.id.tvInfoUserName);
        tvInfoUserPhoneNumberfvbi = (TextView) view.findViewById(R.id.tvInfoUserPhoneNumber);
    }

    private void initContent() {
        updateUi();

        boolean isContactListNull = db.isDataTableValueNull(TableContactList, Key_Contact_List_EventId, eventId);
        boolean isEmergencyNull = db.isDataTableValueNull(TableEmergencie, Key_Emergencie_EventId, eventId);
        boolean isSurroundAreaNull = db.isDataTableValueNull(TableAreaNew, Key_AreaNew_EventId, eventId);
        boolean isImportantInfoNull = db.isDataTableValueNull(TableImportantInfoNew, Key_Important_InfoNew_EventId, eventId);

        List<Integer> s = new ArrayList<>();
        if (isContactListNull) {
            ArrayList<String> list = ListArrayUtil.convertStringArrayToListString(infoMenu);
            int comitee_contact = ListArrayUtil.getPosStringInList(list, "Comitee Contact");
            s.add(comitee_contact);
        }
        if (isEmergencyNull) {
            ArrayList<String> list = ListArrayUtil.convertStringArrayToListString(infoMenu);
            int emergencies = ListArrayUtil.getPosStringInList(list, "Emergencies");
            s.add(emergencies);
        }
        if (isSurroundAreaNull) {
            ArrayList<String> list = ListArrayUtil.convertStringArrayToListString(infoMenu);
            int surrounding_area = ListArrayUtil.getPosStringInList(list, "Surrounding Area");
            s.add(surrounding_area);
        }
        if (isImportantInfoNull) {
            ArrayList<String> list = ListArrayUtil.convertStringArrayToListString(infoMenu);
            int practical_information = ListArrayUtil.getPosStringInList(list, "Practical Information");
            s.add(practical_information);
        }

        int[] ints = ListArrayUtil.convertListIntegertToIntArray(s);

        for (int i = 0; i < infoMenu.length; i++) {
            if (!ListArrayUtil.isListContainInt(ints, i)) {
                listviewModels.add(new InfoListviewModel(infoMenu[i], infoPic[i]));
            }
        }

        adapter = new InfoListViewAdapter(getActivity(), listviewModels, this);
        rvMenufvbi.setNestedScrollingEnabled(false);
        rvMenufvbi.setAdapter(adapter);
        rvMenufvbi.setLayoutManager(new LinearLayoutManager(getActivity()));

    }

    private void initListener() {
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AccountActivity.class);
                startActivityForResult(intent, REQ_CODE_ACCOUNT);
            }
        });
    }

    private void updateUi() {
        GetListUserEventResponseModel model = db.getOneUserData(accountId);
        tvInfoUserNamefvbi.setText(model.getFull_name());
        tvInfoUserPhoneNumberfvbi.setText(model.getAccount_id());

        String imageUrlPath = AppUtil.validationStringImageIcon(getActivity(), model.getImage_url(), model.getImage_url_local(), true);
        Glide.with(getActivity())
                .load(InputValidUtil.isLinkUrl(imageUrlPath) ? imageUrlPath : new File(imageUrlPath))
                .asBitmap()
                .error(R.drawable.template_account_og)
                .placeholder(R.drawable.ic_action_name)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(false)
                .dontAnimate()
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        ivInfoUserImagefvbi.setImageBitmap(resource);
                        if (NetworkUtil.isConnected(getActivity())) {
                            String imageCachePath = PictureUtil.saveImageLogoBackIcon(getActivity(), resource, "user_image" + accountId);
                            db.saveUserPicture(imageCachePath, accountId);
                        }
                    }
                });
    }

    @Override
    public void click(String s) {
        if (s.equalsIgnoreCase("Map")) {
            Intent intent1 = new Intent(getActivity(), aMapActivity.class);
            getActivity().startActivity(intent1);
        } else if (s.equalsIgnoreCase("Gallery")) {
            Intent intent2 = new Intent(getActivity(), GalleryActivity.class);
            getActivity().startActivity(intent2);
        } else if (s.equalsIgnoreCase("Inbox")) {
            String title = "Inbox";
            Intent intent3 = new Intent(getActivity(), cInboxActivity.class);
            intent3.putExtra("TITLE", title);
            startActivityForResult(intent3, REQ_CODE_INBOX);
        } else if (s.equalsIgnoreCase("Comitee Contact")) {
            Intent intent4 = new Intent(getActivity(), dComitteContactActivity.class);
            getActivity().startActivity(intent4);
        } else if (s.equalsIgnoreCase("Emergencies")) {
            Intent intent5 = new Intent(getActivity(), eEmergenciesActivity.class);
            getActivity().startActivity(intent5);
        } else if (s.equalsIgnoreCase("Practical Information")) {
            Intent intent6 = new Intent(getActivity(), fPracticalInfoActivity.class);
            getActivity().startActivity(intent6);
        } else if (s.equalsIgnoreCase("Surrounding Area")) {
            Intent intent7 = new Intent(getActivity(), gSurroundingAreaActivity.class);
            getActivity().startActivity(intent7);
        } else if (s.equalsIgnoreCase("Feedback")) {
            Intent intent8 = new Intent(getActivity(), hFeedbackActivity.class);
            getActivity().startActivity(intent8);
        } else if (s.equalsIgnoreCase("Change Event")) {
            AppUtil.outEventFull(getActivity(), ChangeEventActivity.class, ISeasonConfig.ID_NOTIF);
        }
    }

    private boolean isNotificationVisible() {
        Intent notificationIntent = new Intent(getActivity(), BaseHomeActivity.class);
        PendingIntent test = PendingIntent.getActivity(getActivity(), ID_NOTIF, notificationIntent, PendingIntent.FLAG_NO_CREATE);
        return test != null;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (REQ_CODE_INBOX == requestCode && resultCode == getActivity().RESULT_OK) {
            ((BaseHomeActivity) getActivity()).updateInbox();
            adapter.notifyDataSetChanged();
        } else if (REQ_CODE_ACCOUNT == requestCode && resultCode == getActivity().RESULT_OK) {
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    if (NetworkUtil.isConnected(getActivity())) {
                        ((BaseHomeActivity) getActivity()).recreate();
                    }
                }
            }, 1000);
        }
    }
}


