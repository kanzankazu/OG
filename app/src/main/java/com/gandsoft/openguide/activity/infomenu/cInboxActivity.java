package com.gandsoft.openguide.activity.infomenu;

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
import com.gandsoft.openguide.API.APIresponse.Event.EventDataContactList;
import com.gandsoft.openguide.ISeasonConfig;
import com.gandsoft.openguide.R;
import com.gandsoft.openguide.activity.infomenu.adapter.ComitteContactAdapter;
import com.gandsoft.openguide.activity.infomenu.adapter.InboxRecViewAdapter;
import com.gandsoft.openguide.database.SQLiteHelper;
import com.gandsoft.openguide.support.SessionUtil;

import java.util.ArrayList;

public class cInboxActivity extends AppCompatActivity {
    SQLiteHelper db = new SQLiteHelper(this);

    RecyclerView rvCommiteNotefvbi;
    private InboxRecViewAdapter adapter;
    private String accountId,eventId;
    private Toolbar toolbar;
    private ActionBar ab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_c_inbox);

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
        ab.setTitle("Inbox");

        ArrayList<EventCommitteeNote> models = db.getCommiteNote(eventId);
        adapter = new InboxRecViewAdapter(this, models);
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
        finish();
        return super.onOptionsItemSelected(item);
    }
}
