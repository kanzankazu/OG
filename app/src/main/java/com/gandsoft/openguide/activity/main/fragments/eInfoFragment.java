package com.gandsoft.openguide.activity.main.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.gandsoft.openguide.API.APIresponse.UserData.UserDataResponseModel;
import com.gandsoft.openguide.ISeasonConfig;
import com.gandsoft.openguide.R;
import com.gandsoft.openguide.activity.AccountActivity;
import com.gandsoft.openguide.activity.ChangeEventActivity;
import com.gandsoft.openguide.activity.infomenu.aMapActivity;
import com.gandsoft.openguide.activity.infomenu.cInboxActivity;
import com.gandsoft.openguide.activity.infomenu.dComitteContactActivity;
import com.gandsoft.openguide.activity.infomenu.eEmergenciesActivity;
import com.gandsoft.openguide.activity.infomenu.fPracticalInfoActivity;
import com.gandsoft.openguide.activity.infomenu.gSurroundingAreaActivity;
import com.gandsoft.openguide.activity.infomenu.gallery2.GalleryActivity;
import com.gandsoft.openguide.activity.infomenu.hFeedbackActivity;
import com.gandsoft.openguide.activity.main.adapter.InfoListViewAdapter;
import com.gandsoft.openguide.activity.main.adapter.InfoListviewModel;
import com.gandsoft.openguide.database.SQLiteHelper;
import com.gandsoft.openguide.support.ListArrayUtil;
import com.gandsoft.openguide.support.SessionUtil;

import java.util.ArrayList;
import java.util.List;


public class eInfoFragment extends Fragment implements InfoListViewAdapter.ListAdapterListener {
    private RecyclerView rvMenufvbi;
    private ImageView ivInfoUserImagefvbi;
    private TextView tvInfoFullNamefvbi, tvInfoUserNamefvbi, tvInfoUserPhoneNumberfvbi;
    private Button button;
    Button bMyPro;
    InfoListViewAdapter adapter;

    private String accountId, eventId;
    private int version_data_event;
    SQLiteHelper db;
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
    private List<InfoListviewModel> listviewModels = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        db = new SQLiteHelper(getActivity());
        View view = inflater.inflate(R.layout.fragment_e_info, container, false);

        accountId = SessionUtil.getStringPreferences(ISeasonConfig.KEY_ACCOUNT_ID, null);
        eventId = SessionUtil.getStringPreferences(ISeasonConfig.KEY_EVENT_ID, null);

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

        boolean isEmergencyNull = db.isDataTableValueNull(SQLiteHelper.TableEmergencie, SQLiteHelper.Key_Emergencie_EventId, eventId);
        boolean isSurroundAreaNull = db.isDataTableValueNull(SQLiteHelper.TableArea, SQLiteHelper.Key_Area_EventId, eventId);
        List<Integer> s = new ArrayList<>();
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
        int[] ints = ListArrayUtil.convertListIntegertToIntArray(s);

        for (int i = 0; i < infoMenu.length; i++) {
            if (!ListArrayUtil.isListContainInt(ints,i)){
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
                startActivity(intent);
            }
        });
    }

    private void updateUi() {
        ArrayList<UserDataResponseModel> models = db.getUserData(accountId);
        if (models != null) {
            for (int i = 0; i < models.size(); i++) {
                UserDataResponseModel model = models.get(i);
                tvInfoUserNamefvbi.setText(model.getFull_name());
                tvInfoUserPhoneNumberfvbi.setText(model.getPhone_number());

                Glide.with(getActivity().getApplicationContext())
                        .load(model.getImage_url())
                        .placeholder(R.drawable.template_account_og)
                        .error(R.drawable.template_account_og)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true)
                        .into(ivInfoUserImagefvbi);

            }
        }
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
            Intent intent3 = new Intent(getActivity(), cInboxActivity.class);
            getActivity().startActivity(intent3);
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
            Intent intent9 = new Intent(getActivity(), ChangeEventActivity.class);
            getActivity().startActivity(intent9);
            getActivity().finish();
        }
    }
}


