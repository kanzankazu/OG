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

import com.gandsoft.openguide.API.APIresponse.Event.EventSurroundingAreaNew;
import com.gandsoft.openguide.ISeasonConfig;
import com.gandsoft.openguide.R;
import com.gandsoft.openguide.database.SQLiteHelperMethod;
import com.gandsoft.openguide.support.SessionUtil;

import java.util.ArrayList;

public class gSurroundingAreaActivity extends AppCompatActivity {
    SQLiteHelperMethod db = new SQLiteHelperMethod(this);

    private String accountId, eventId;
    private ExpandableListView explvSurroundAreafvbi;
    private Toolbar toolbar;
    private ActionBar ab;
    private ArrayList<EventSurroundingAreaNew> headerItems = new ArrayList<EventSurroundingAreaNew>();

    private int lastExpandedPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_g_surrounding_area);

        initCheck();
        initComponent();
        initContent();
        initListener();
    }

    private void initCheck() {
        if (SessionUtil.checkIfExist(ISeasonConfig.KEY_ACCOUNT_ID)) {
            accountId = SessionUtil.getStringPreferences(ISeasonConfig.KEY_ACCOUNT_ID, null);
        }
        if (SessionUtil.checkIfExist(ISeasonConfig.KEY_EVENT_ID)) {
            eventId = SessionUtil.getStringPreferences(ISeasonConfig.KEY_EVENT_ID, null);
        }
    }

    private void initComponent() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        explvSurroundAreafvbi = (ExpandableListView) findViewById(R.id.explvSurroundArea);
    }

    private void initContent() {
        setSupportActionBar(toolbar);
        ab = getSupportActionBar();
        ab.setTitle("Surrounding Area");

        ArrayList<EventSurroundingAreaNew> models = db.getSurroundingAreaNew(eventId);
        if (models != null) {
            for (int i = 0; i < models.size(); i++) {
                EventSurroundingAreaNew model = models.get(i);
                EventSurroundingAreaNew model2 = new EventSurroundingAreaNew();
                model2.setTitle(model.getTitle());
                model2.setDetail(db.getSurroundingAreaNewDetail(eventId, model.getTitle()));
                headerItems.add(model2);
            }
        }

        SurroundingAreaAdapter adapter = new SurroundingAreaAdapter(this, headerItems, eventId, accountId);
        adapter.setInflater((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE), this);
        explvSurroundAreafvbi.setDividerHeight(2);
        explvSurroundAreafvbi.setGroupIndicator(null);
        explvSurroundAreafvbi.setClickable(true);
        explvSurroundAreafvbi.setAdapter(adapter);

    }

    private void initListener() {
        explvSurroundAreafvbi.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            int previousGroup = -1;

            @Override
            public void onGroupExpand(int groupPosition) {
                /*if (lastExpandedPosition != -1 && groupPosition != lastExpandedPosition) {
                    explvSurroundAreafvbi.collapseGroup(lastExpandedPosition);
                }
                lastExpandedPosition = groupPosition;*/

                if (groupPosition != previousGroup)
                    explvSurroundAreafvbi.collapseGroup(previousGroup);
                previousGroup = groupPosition;
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
