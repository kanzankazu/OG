package com.gandsoft.openguide.activity.infomenu;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;

import com.gandsoft.openguide.API.APIresponse.Event.EventImportanInfo;
import com.gandsoft.openguide.ISeasonConfig;
import com.gandsoft.openguide.R;
import com.gandsoft.openguide.activity.infomenu.adapter.PracticalInfoAdapter;
import com.gandsoft.openguide.database.SQLiteHelper;
import com.gandsoft.openguide.support.SessionUtil;

import java.util.ArrayList;

public class fPracticalInfoActivity extends AppCompatActivity {
    SQLiteHelper db = new SQLiteHelper(this);
    private Toolbar toolbar;
    private ActionBar ab;
    private AppCompatActivity aca;

    private String accountId, eventId;
    private ArrayList<String> parentItems = new ArrayList<String>();
    private ArrayList<Object> childItems = new ArrayList<Object>();

    private int lastExpandedPosition = -1;
    private ExpandableListView expandableList;

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

        expandableList.setDividerHeight(2);
        expandableList.setGroupIndicator(null);
        expandableList.setClickable(true);

        ArrayList<EventImportanInfo> models = db.getImportanInfo(eventId);
        if (models != null) {
            for (int i = 0; i < models.size(); i++) {
                EventImportanInfo model = models.get(i);
                parentItems.add(String.valueOf(Html.fromHtml(model.getTitle())));
                ArrayList<String> child = new ArrayList<String>();
                child.add(String.valueOf(Html.fromHtml(model.getInfo())));
                childItems.add(child);
            }
        }

        PracticalInfoAdapter adapter = new PracticalInfoAdapter(parentItems, childItems);
        adapter.setInflater((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE), this);
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
