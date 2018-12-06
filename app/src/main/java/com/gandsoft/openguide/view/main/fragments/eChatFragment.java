package com.gandsoft.openguide.view.main.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.gandsoft.openguide.ISeasonConfig;
import com.gandsoft.openguide.R;
import com.gandsoft.openguide.database.SQLiteHelperMethod;
import com.gandsoft.openguide.support.SessionUtil;
import com.gandsoft.openguide.view.main.adapter.ChatMainContactAdapter;
import com.gandsoft.openguide.view.main.adapter.ChatMainGroupAdapter;
import com.gandsoft.openguide.view.main.fragments.eChatActivity.ChatContactAddActivity;
import com.gandsoft.openguide.view.main.fragments.eChatActivity.ChatContactDetailActivity;
import com.gandsoft.openguide.view.main.fragments.eChatActivity.ChatGroupAddActivity;
import com.gandsoft.openguide.view.main.fragments.eChatActivity.ChatGroupDetailActivity;

public class eChatFragment extends Fragment {
    private static final int REQ_CODE_GROUP_ADD = 1;
    private static final int REQ_CODE_CONTACT_ADD = 2;
    private SQLiteHelperMethod db;
    private String accountId;
    private String eventId;
    private NestedScrollView nsvChatMainfvbi;
    private ImageButton ibChatMainAddGroupfvbi, ibChatMainAddContactfvbi;
    private RecyclerView rvChatMainGroupfvbi, rvChatMainContactfvbi;
    private ChatMainGroupAdapter chatGroupAdapter;
    private ChatMainContactAdapter chatContactAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        db = new SQLiteHelperMethod(getActivity());
        View view = inflater.inflate(R.layout.fragment_e_chat, container, false);

        accountId = SessionUtil.getStringPreferences(ISeasonConfig.KEY_ACCOUNT_ID, null);
        eventId = SessionUtil.getStringPreferences(ISeasonConfig.KEY_EVENT_ID, null);

        initComponent(view);
        initContent();
        initListener();

        return view;
    }

    private void initComponent(View view) {
        nsvChatMainfvbi = (NestedScrollView) view.findViewById(R.id.nsvChatMain);
        ibChatMainAddGroupfvbi = (ImageButton) view.findViewById(R.id.ibChatMainAddGroup);
        rvChatMainGroupfvbi = (RecyclerView) view.findViewById(R.id.rvChatMainGroup);
        ibChatMainAddContactfvbi = (ImageButton) view.findViewById(R.id.ibChatMainAddContact);
        rvChatMainContactfvbi = (RecyclerView) view.findViewById(R.id.rvChatMainContact);
    }

    private void initContent() {
        chatGroupAdapter = new ChatMainGroupAdapter();
        rvChatMainGroupfvbi.setAdapter(chatGroupAdapter);
        rvChatMainGroupfvbi.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvChatMainGroupfvbi.setNestedScrollingEnabled(false);
        chatContactAdapter = new ChatMainContactAdapter();
        rvChatMainGroupfvbi.setAdapter(chatContactAdapter);
        rvChatMainGroupfvbi.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvChatMainGroupfvbi.setNestedScrollingEnabled(false);
    }

    private void initListener() {
        ibChatMainAddGroupfvbi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveToAddGroup();
            }
        });
        ibChatMainAddContactfvbi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveToAddContact();
            }
        });
    }

    private void moveToAddGroup() {
        Intent intent = new Intent(getActivity(), ChatGroupAddActivity.class);
        getActivity().startActivityForResult(intent, REQ_CODE_GROUP_ADD);
    }

    private void moveToAddContact() {
        Intent intent = new Intent(getActivity(), ChatContactAddActivity.class);
        getActivity().startActivityForResult(intent, REQ_CODE_CONTACT_ADD);
    }

    private void moveToDetailGroup() {
        Intent intent = new Intent(getActivity(), ChatGroupDetailActivity.class);
        getActivity().startActivity(intent);
    }

    private void moveToDetailContact() {
        Intent intent = new Intent(getActivity(), ChatContactDetailActivity.class);
        getActivity().startActivity(intent);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == getActivity().RESULT_OK && requestCode == REQ_CODE_GROUP_ADD) {

        } else if (resultCode == getActivity().RESULT_OK && requestCode == REQ_CODE_CONTACT_ADD) {

        }
    }
}
