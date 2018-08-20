package com.gandsoft.openguide.activity.infomenu;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.gandsoft.openguide.API.APIresponse.Event.EventDataContactList;
import com.gandsoft.openguide.API.APIresponse.Event.EventImportanInfo;
import com.gandsoft.openguide.API.APIresponse.Event.EventScheduleListDateDataList;
import com.gandsoft.openguide.ISeasonConfig;
import com.gandsoft.openguide.R;
import com.gandsoft.openguide.activity.infomenu.adapter.ComitteContactAdapter;
import com.gandsoft.openguide.activity.main.adapter.ScheduleRecycleviewAdapter;
import com.gandsoft.openguide.database.SQLiteHelper;
import com.gandsoft.openguide.support.SessionUtil;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class dComitteContactActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private ActionBar ab;

    private String accountId, eventId;
    SQLiteHelper db = new SQLiteHelper(this);

    private ImageView ivComitteIconfvbi;
    private TextView tvComitteTitlefvbi,tvComitteEventfvbi,tvComitteNamefvbi,tvComitteNumberfvbi,tvComitteEmailfvbi;


    private RecyclerView recyclerView;
    private ArrayList<EventScheduleListDateDataList> contactList;
    private ComitteContactAdapter recycleviewAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_d_comitte_contact);

        initCheck();
        initComponent();
        initContent();
    }

    public void initCheck(){
        if (SessionUtil.checkIfExist(ISeasonConfig.KEY_ACCOUNT_ID)) {
            accountId = SessionUtil.getStringPreferences(ISeasonConfig.KEY_ACCOUNT_ID, null);
        }
        if (SessionUtil.checkIfExist(ISeasonConfig.KEY_EVENT_ID)) {
            eventId = SessionUtil.getStringPreferences(ISeasonConfig.KEY_EVENT_ID, null);
        }
    }

    public void initComponent(){
        ivComitteIconfvbi = (ImageView)findViewById(R.id.ivComitteIcon);
        tvComitteTitlefvbi = (TextView)findViewById(R.id.tvComitteTitle);
        tvComitteEventfvbi = (TextView)findViewById(R.id.tvComitteEvent);
        tvComitteNamefvbi = (TextView)findViewById(R.id.tvComitteName);
        tvComitteNumberfvbi = (TextView)findViewById(R.id.tvComitteNumber);
        tvComitteEmailfvbi = (TextView)findViewById(R.id.tvComitteEmail);
    }

    public void initContent() {
        summonActionBar();

        ArrayList<EventDataContactList> models = db.getDataContactList(eventId);
        if (models != null) {
            for (int i = 0; i < models.size(); i++) {
                EventDataContactList model = models.get(i);
                recycleviewAdapter = new ComitteContactAdapter(this, models);
                recyclerView.setAdapter(recycleviewAdapter);
            }
        }
    }

    public void summonActionBar(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ab = getSupportActionBar();
        ab.setTitle("Comitte Contact");
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
