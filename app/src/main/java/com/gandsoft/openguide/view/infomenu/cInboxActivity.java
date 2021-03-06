package com.gandsoft.openguide.view.infomenu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.gandsoft.openguide.API.APIresponse.Event.EventCommitteeNote;
import com.gandsoft.openguide.ISeasonConfig;
import com.gandsoft.openguide.R;
import com.gandsoft.openguide.database.SQLiteHelperMethod;
import com.gandsoft.openguide.support.SessionUtil;
import com.gandsoft.openguide.view.infomenu.adapter.InboxRecViewAdapter;

import java.util.ArrayList;

public class cInboxActivity extends AppCompatActivity {
    SQLiteHelperMethod db = new SQLiteHelperMethod(this);

    RecyclerView rvCommiteNotefvbi;
    private InboxRecViewAdapter adapter;
    private String accountId, eventId;
    private Toolbar toolbar;
    private ActionBar ab;
    private String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_c_inbox);
        title = getIntent().getStringExtra("TITLE");

        initCheck();
        initComponent();
        initContent();
        initListener();
    }

    public void initCheck() {
        if (SessionUtil.checkIfExist(ISeasonConfig.KEY_ACCOUNT_ID)) {
            accountId = SessionUtil.getStringPreferences(ISeasonConfig.KEY_ACCOUNT_ID, null);
        }
        if (SessionUtil.checkIfExist(ISeasonConfig.KEY_EVENT_ID)) {
            eventId = SessionUtil.getStringPreferences(ISeasonConfig.KEY_EVENT_ID, null);
        }
    }

    private void initComponent() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        rvCommiteNotefvbi = (RecyclerView) findViewById(R.id.rvCommiteNote);
    }

    private void initContent() {
        setSupportActionBar(toolbar);
        ab = getSupportActionBar();
        ab.setTitle(title);

        ArrayList<EventCommitteeNote> models = db.getCommiteNote(eventId);
        adapter = new InboxRecViewAdapter(this, models, accountId, eventId, db);
        rvCommiteNotefvbi.setNestedScrollingEnabled(false);
        rvCommiteNotefvbi.setAdapter(adapter);
        rvCommiteNotefvbi.setLayoutManager(new LinearLayoutManager(this));
    }

    private void initListener() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        setResult(Activity.RESULT_OK, intent);
        intent.putExtra(ISeasonConfig.INTENT_PARAM_BACK, db.getCommiteHasBeenOpened(eventId));
        finish();
    }
}
