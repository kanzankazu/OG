package com.gandsoft.openguide.activity.infomenu;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.gandsoft.openguide.API.APIresponse.Event.EventDataContactList;
import com.gandsoft.openguide.API.APIresponse.Event.EventTheEvent;
import com.gandsoft.openguide.ISeasonConfig;
import com.gandsoft.openguide.R;
import com.gandsoft.openguide.activity.infomenu.adapter.ComitteContactAdapter;
import com.gandsoft.openguide.activity.infomenu.adapter.ComitteContactHook;
import com.gandsoft.openguide.database.SQLiteHelper;
import com.gandsoft.openguide.support.SessionUtil;

import java.util.ArrayList;

public class dComitteContactActivity extends AppCompatActivity implements ComitteContactHook {
    private Toolbar toolbar;
    private ActionBar ab;

    private String accountId, eventId;
    SQLiteHelper db = new SQLiteHelper(this);

    private TextView tvComitteTitlefvbi;

    private ComitteContactAdapter recycleviewAdapter;
    private RecyclerView rvCommiteContactfvbi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_d_comitte_contact);

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

    public void initComponent() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        tvComitteTitlefvbi = (TextView) findViewById(R.id.tvComitteTitle);
        rvCommiteContactfvbi = (RecyclerView) findViewById(R.id.rvCommiteContact);

    }

    public void initContent() {
        setSupportActionBar(toolbar);
        ab = getSupportActionBar();
        ab.setTitle("Comitte Contact");

        EventTheEvent eventTitle = db.getTheEvent(eventId);
        tvComitteTitlefvbi.setText(eventTitle.getEvent_name() + "\nCommittee Contacts");

        ArrayList<EventDataContactList> models = db.getDataContactList(eventId);
        recycleviewAdapter = new ComitteContactAdapter(this, models);
        rvCommiteContactfvbi.setNestedScrollingEnabled(false);
        rvCommiteContactfvbi.setAdapter(recycleviewAdapter);
        rvCommiteContactfvbi.setLayoutManager(new LinearLayoutManager(this));
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

    @Override
    public void dialNumber(String number) {
        String phone = getNumberValid(number);
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
        startActivity(intent);
    }

    @Override
    public void sendEmail(String email) {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + email)); //alamat email tujuan
        //emailIntent.putExtra(Intent.EXTRA_SUBJECT, ); //subject email
        startActivity(Intent.createChooser(emailIntent, "Pilih Aplikasi Email"));
    }

    private String getNumberValid(String string) {
        String sub8 = string.substring(0, 1);
        String sub0 = string.substring(0, 1);
        String sub62 = string.substring(0, 2);
        String subPlus62 = string.substring(0, 3);
        String sVal = null;
        if (sub0.equalsIgnoreCase("0")) {
            sVal = "+62" + string.substring(1);
        } else if (sub8.equalsIgnoreCase("8")) {
            sVal = "+62" + string.substring(1);
        } else if (sub62.equalsIgnoreCase("62")) {
            sVal = /*"+62" + string.substring(2);*/ "+" + string;
        } else if (subPlus62.equalsIgnoreCase("+62")) {
            sVal = /*"+62" + string.substring(3);*/ string;
        } else {
            sVal = "+62" + string;
        }
        return sVal;
    }
}
