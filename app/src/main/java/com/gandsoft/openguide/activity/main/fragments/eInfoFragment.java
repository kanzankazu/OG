package com.gandsoft.openguide.activity.main.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.gandsoft.openguide.R;
import com.gandsoft.openguide.activity.AccountActivity;
import com.gandsoft.openguide.activity.ChangeEventActivity;
import com.gandsoft.openguide.activity.infomenu.aMapActivity;
import com.gandsoft.openguide.activity.infomenu.bGalleryActivity;
import com.gandsoft.openguide.activity.infomenu.cInboxActivity;
import com.gandsoft.openguide.activity.infomenu.dComitteContactActivity;
import com.gandsoft.openguide.activity.infomenu.eEmergenciesActivity;
import com.gandsoft.openguide.activity.infomenu.fPracticalInfoActivity;
import com.gandsoft.openguide.activity.infomenu.gSurroundingAreaActivity;
import com.gandsoft.openguide.activity.infomenu.gallery2.GalleryActivity;
import com.gandsoft.openguide.activity.infomenu.hFeedbackActivity;
import com.gandsoft.openguide.activity.main.adapter.InfoListViewAdapter;
import com.gandsoft.openguide.activity.main.adapter.InfoListviewModel;
import com.gandsoft.openguide.activity.main.adapter.RecyclerItemClickListener;
import com.gandsoft.openguide.database.SQLiteHelper;

import java.util.ArrayList;
import java.util.List;


public class eInfoFragment extends Fragment {
    private RecyclerView rvMenufvbi;
    private TextView tvInfoFullNamefvbi;
    private Button button;
    Button bMyPro;
    InfoListViewAdapter adapter;
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
        View view = inflater.inflate(R.layout.fragment_e_info, container, false);

        initComponent(view);
        initContent();
        initListener();

        return view;
    }

    private void initComponent(View view) {
        button = (Button) view.findViewById(R.id.button);
        rvMenufvbi = (RecyclerView) view.findViewById(R.id.rvMenu);
        tvInfoFullNamefvbi = (TextView) view.findViewById(R.id.tvInfoFullName);
    }

    private void initContent() {
        for (int i = 0; i < infoMenu.length; i++) {
            listviewModels.add(new InfoListviewModel(infoMenu[i], infoPic[i]));
        }
        adapter = new InfoListViewAdapter(getActivity(), listviewModels);
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

        rvMenufvbi.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), rvMenufvbi, new RecyclerItemClickListener.OnItemClickListener() {
            @SuppressLint("LongLogTag")
            @Override
            public void onItemClick(View view, int position) {
                Log.d("Lihat onItemClick eInfoFragment", String.valueOf(position));
                Log.d("Lihat onItemClick eInfoFragment", String.valueOf(view));
                Log.d("Lihat onItemClick eInfoFragment", String.valueOf(view.getId()));
                switch (position) {
                    case 0:
                        Intent intent1 = new Intent(getActivity(), aMapActivity.class);
                        getActivity().startActivity(intent1);
                        break;
                    case 1:
                        Intent intent2 = new Intent(getActivity(), GalleryActivity.class);
                        getActivity().startActivity(intent2);
                        break;
                    case 2:
                        Intent intent3 = new Intent(getActivity(), cInboxActivity.class);
                        getActivity().startActivity(intent3);
                        break;
                    case 3:
                        Intent intent4 = new Intent(getActivity(), dComitteContactActivity.class);
                        getActivity().startActivity(intent4);
                        break;
                    case 4:
                        Intent intent5 = new Intent(getActivity(), eEmergenciesActivity.class);
                        getActivity().startActivity(intent5);
                        break;
                    case 5:
                        Intent intent6 = new Intent(getActivity(), fPracticalInfoActivity.class);
                        getActivity().startActivity(intent6);
                        break;
                    case 6:
                        Intent intent7 = new Intent(getActivity(), gSurroundingAreaActivity.class);
                        getActivity().startActivity(intent7);
                        break;
                    case 7:
                        Intent intent8 = new Intent(getActivity(), hFeedbackActivity.class);
                        getActivity().startActivity(intent8);
                        break;
                    case 8:
                        Intent intent9 = new Intent(getActivity(), ChangeEventActivity.class);
                        getActivity().startActivity(intent9);
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        }));
    }
}

