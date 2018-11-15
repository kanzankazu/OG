package com.gandsoft.openguide.view.infomenu;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ExpandableListView;

import com.gandsoft.openguide.API.APIresponse.Event.EventEmergencies;
import com.gandsoft.openguide.ISeasonConfig;
import com.gandsoft.openguide.R;
import com.gandsoft.openguide.database.SQLiteHelper;
import com.gandsoft.openguide.database.SQLiteHelperMethod;
import com.gandsoft.openguide.support.SessionUtil;

import java.util.ArrayList;

public class eEmergenciesActivity extends AppCompatActivity {
    SQLiteHelperMethod db = new SQLiteHelperMethod(this);
    ArrayList<String> headerItems = new ArrayList<>();
    ArrayList<Object> childItems = new ArrayList<Object>();
    private String accountId, eventId;
    private Toolbar toolbar;
    private ActionBar ab;
    private ExpandableListView explvEmergenciesfvbi;
    private int lastExpandedPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_e_emergencies);

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
        explvEmergenciesfvbi = (ExpandableListView) findViewById(R.id.explvEmergencies);
    }

    private void initContent() {
        setSupportActionBar(toolbar);
        ab = getSupportActionBar();
        ab.setTitle("Emergencies");

        ArrayList<EventEmergencies> models = db.getEmergencie(eventId);
        if (models != null) {
            for (int i = 0; i < models.size(); i++) {
                EventEmergencies model = models.get(i);
                headerItems.add(model.getTitle());
                ArrayList<String> child = new ArrayList<String>();
                child.add(model.getKeterangan());
                childItems.add(child);
            }
        }

        EmergencieAdapter adapter = new EmergencieAdapter(headerItems, childItems);
        adapter.setInflater((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE), this);
        explvEmergenciesfvbi.setDividerHeight(2);
        explvEmergenciesfvbi.setGroupIndicator(null);
        explvEmergenciesfvbi.setClickable(true);
        explvEmergenciesfvbi.setAdapter(adapter);
    }

    private void initListener() {
        explvEmergenciesfvbi.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                if (lastExpandedPosition != -1 && groupPosition != lastExpandedPosition) {
                    explvEmergenciesfvbi.collapseGroup(lastExpandedPosition);
                }
                lastExpandedPosition = groupPosition;
            }
        });
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
