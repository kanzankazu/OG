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
import android.view.View;
import android.widget.ExpandableListView;

import com.gandsoft.openguide.API.APIresponse.Event.EventImportanInfoNew;
import com.gandsoft.openguide.ISeasonConfig;
import com.gandsoft.openguide.R;
import com.gandsoft.openguide.database.SQLiteHelperMethod;
import com.gandsoft.openguide.support.SessionUtil;
import com.gandsoft.openguide.view.infomenu.adapter.PracticalInfoAdapter;

import java.util.ArrayList;

public class fPracticalInfoActivity extends AppCompatActivity {
    SQLiteHelperMethod db = new SQLiteHelperMethod(this);
    private Toolbar toolbar;
    private ActionBar ab;
    private AppCompatActivity aca;
    private ExpandableListView expandableList;

    private String accountId, eventId;
    private ArrayList<EventImportanInfoNew> parentItems = new ArrayList<EventImportanInfoNew>();
    private ArrayList<EventImportanInfoNew> childItems = new ArrayList<>();

    private int lastExpandedPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) throws ArrayIndexOutOfBoundsException {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_f_practical_info);

        if (SessionUtil.checkIfExist(ISeasonConfig.KEY_ACCOUNT_ID)) {
            accountId = SessionUtil.getStringPreferences(ISeasonConfig.KEY_ACCOUNT_ID, null);
        }
        if (SessionUtil.checkIfExist(ISeasonConfig.KEY_EVENT_ID)) {
            eventId = SessionUtil.getStringPreferences(ISeasonConfig.KEY_EVENT_ID, null);
        }

        initComponent();
        initContent();
        initListener();

    }

    public void initComponent() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        expandableList = (ExpandableListView) findViewById(R.id.explist);
    }

    public void initContent() {
        setSupportActionBar(toolbar);
        ab = getSupportActionBar();
        ab.setTitle("Practical Information");

        ArrayList<EventImportanInfoNew> models = db.getImportanInfoNew(eventId);
        if (models != null) {
            for (int i = 0; i < models.size(); i++) {
                EventImportanInfoNew model = models.get(i);
                EventImportanInfoNew model2 = new EventImportanInfoNew();
                model2.setTitle(model.getTitle());
                model2.setInfo(model.getInfo());
                model2.setDetail(db.getImportanInfoNewDetail(eventId, model.getTitle()));
                parentItems.add(model2);
            }
        }

        PracticalInfoAdapter adapter = new PracticalInfoAdapter(fPracticalInfoActivity.this, parentItems, accountId, eventId);
        adapter.setInflater((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE), this);
        expandableList.setDividerHeight(2);
        expandableList.setGroupIndicator(null);
        expandableList.setClickable(true);
        expandableList.setAdapter(adapter);

    }

    private void initListener() {
        expandableList.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                return false;
            }
        });

        expandableList.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                if (lastExpandedPosition != -1 && groupPosition != lastExpandedPosition) {
                    expandableList.collapseGroup(lastExpandedPosition);
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
