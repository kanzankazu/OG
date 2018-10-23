package com.gandsoft.openguide.view.main.fragments;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.gandsoft.openguide.ISeasonConfig;
import com.gandsoft.openguide.R;

public class cScheduleQNAActivity extends AppCompatActivity {

    private WebView wvScheduleQnaDialogfvbi;
    private String link;
    private Toolbar toolbar;
    private ActionBar ab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c_schedule_qna);

        initComponent();
        initParam();
        initSession();
        initContent();
        initListener();
    }

    private void initComponent() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        wvScheduleQnaDialogfvbi = (WebView) findViewById(R.id.wvScheduleQnaDialog);
    }

    private void initParam() {
        Intent bundle = getIntent();
        if (bundle.hasExtra(ISeasonConfig.INTENT_PARAM)) {
            link = bundle.getStringExtra(ISeasonConfig.INTENT_PARAM);
        }
    }

    private void initSession() {

    }

    private void initContent() {
        setSupportActionBar(toolbar);
        ab = getSupportActionBar();
        ab.setTitle("QnA");

        String html = link;
        String mimeType = "text/html";
        String encoding = "UTF-8";

        wvScheduleQnaDialogfvbi.loadUrl(html);
        WebSettings webSettings = wvScheduleQnaDialogfvbi.getSettings();
        webSettings.setJavaScriptEnabled(true);
        //improve performance
        webSettings.setDomStorageEnabled(true);
        //webSettings.setUseWideViewPort(true);
        webSettings.setSavePassword(true);
        webSettings.setSaveFormData(true);
        webSettings.setEnableSmoothTransition(true);
        wvScheduleQnaDialogfvbi.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        wvScheduleQnaDialogfvbi.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        wvScheduleQnaDialogfvbi.getSettings().setAppCacheEnabled(true);
        wvScheduleQnaDialogfvbi.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        wvScheduleQnaDialogfvbi.getSettings().setLoadWithOverviewMode(true);
        wvScheduleQnaDialogfvbi.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        wvScheduleQnaDialogfvbi.setWebChromeClient(new WebChromeClient());
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
        // Munculkan alert dialog apabila user ingin keluar aplikasi
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Kamu Tidak ingin meneruskan?");
        alertDialogBuilder.setPositiveButton("Ya",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        Intent intent = new Intent();
                        setResult(Activity.RESULT_CANCELED, intent);
                        finish();
                    }
                });
        // Pilihan jika NO
        alertDialogBuilder.setNegativeButton("Tidak",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                    }
                });
        // Tampilkan alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}
