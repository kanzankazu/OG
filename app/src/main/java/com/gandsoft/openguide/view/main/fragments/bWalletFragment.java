package com.gandsoft.openguide.view.main.fragments;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.gandsoft.openguide.API.APIresponse.UserData.UserWalletDataResponseModel;
import com.gandsoft.openguide.ISeasonConfig;
import com.gandsoft.openguide.R;
import com.gandsoft.openguide.database.SQLiteHelper;
import com.gandsoft.openguide.database.SQLiteHelperMethod;
import com.gandsoft.openguide.support.SessionUtil;
import com.gandsoft.openguide.support.SystemUtil;

import java.util.ArrayList;
import java.util.List;

public class bWalletFragment extends Fragment {
    SQLiteHelperMethod db;
    WalletViewAdapter adapter;
    private RecyclerView rvWalletfvbi;
    private String accountId, eventId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_b_wallet, container, false);

        accountId = SessionUtil.getStringPreferences(ISeasonConfig.KEY_ACCOUNT_ID, null);
        eventId = SessionUtil.getStringPreferences(ISeasonConfig.KEY_EVENT_ID, null);

        initComponent(view);
        initContent();
        initListener();

        return view;
    }

    private void initComponent(View view) {
        rvWalletfvbi = (RecyclerView) view.findViewById(R.id.rvWallet);
    }

    private void initContent() {
        db = new SQLiteHelperMethod(getActivity());
        List<UserWalletDataResponseModel> listWalletData;
        if (db.isDataTableValueNull(SQLiteHelper.TableWallet, SQLiteHelper.KEY_Wallet_eventId, eventId)) {
            listWalletData = new ArrayList<>();
            SystemUtil.showToast(getActivity(), "Empty Data", Toast.LENGTH_SHORT, Gravity.TOP);
        } else {
            listWalletData = db.getAllWalletData(eventId, accountId);
        }

        adapter = new WalletViewAdapter(getActivity(), getActivity(), listWalletData, eventId);
        rvWalletfvbi.setAdapter(adapter);
        rvWalletfvbi.setLayoutManager(new LinearLayoutManager(getActivity()));

        adapter.notifyDataSetChanged();
    }

    private void initListener() {

    }
}
