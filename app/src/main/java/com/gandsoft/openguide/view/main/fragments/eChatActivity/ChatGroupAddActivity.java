package com.gandsoft.openguide.view.main.fragments.eChatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.gandsoft.openguide.ISeasonConfig;
import com.gandsoft.openguide.R;
import com.gandsoft.openguide.view.main.adapter.ChatGroupAddSelectAdapter;

import java.util.ArrayList;
import java.util.List;

public class ChatGroupAddActivity extends AppCompatActivity {

    private ImageButton ibChatGroupAddBackfvbi, ibChatGroupAddSavefvbi;
    private EditText etChatGroupAddNamefvbi;
    private RecyclerView rvChatGroupAddSelectfvbi;

    private List<String> contact = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_e_chat_group_add);

        initComponent();
        initParam();
        initSession();
        initContent();
        initListener();
    }

    private void initComponent() {
        ibChatGroupAddBackfvbi = (ImageButton) findViewById(R.id.ibChatGroupAddBack);
        etChatGroupAddNamefvbi = (EditText) findViewById(R.id.etChatGroupAddName);
        ibChatGroupAddSavefvbi = (ImageButton) findViewById(R.id.ibChatGroupAddSave);
        rvChatGroupAddSelectfvbi = (RecyclerView) findViewById(R.id.rvChatGroupAddSelect);

    }

    private void initParam() {

    }

    private void initSession() {

    }

    private void initContent() {
        ChatGroupAddSelectAdapter adapter = new ChatGroupAddSelectAdapter(ChatGroupAddActivity.this, new ChatGroupAddSelectAdapter.ChatGroupAddSelectListener() {
            @Override
            public void onItemCheck() {
                //contact.add();
            }

            @Override
            public void onItemUncheck() {
                //contact.remove();
            }
        });
        rvChatGroupAddSelectfvbi.setAdapter(adapter);
        rvChatGroupAddSelectfvbi.setLayoutManager(new LinearLayoutManager(this));
    }

    private void initListener() {
        ibChatGroupAddBackfvbi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        ibChatGroupAddSavefvbi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resultOK();
            }
        });
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Are you sure to finish without save?");
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                resultCancel();
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

    private void resultOK() {
        Intent intent = new Intent();
        intent.putExtra(ISeasonConfig.INTENT_PARAM_BACK, "");
        setResult(Activity.RESULT_OK, intent);
        finish();
    }

    private void resultCancel() {
        Intent intent = new Intent();
        setResult(Activity.RESULT_CANCELED, intent);
        finish();
    }
}
