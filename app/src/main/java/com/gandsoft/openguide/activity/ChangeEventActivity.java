package com.gandsoft.openguide.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gandsoft.openguide.R;
import com.gandsoft.openguide.activity.main.BaseHomeActivity;

public class ChangeEventActivity extends AppCompatActivity {

    private ImageView ceIVUserPicfvbi;
    private TextView ceTVUserNamefvbi, ceTVUserIdfvbi, ceTVInfofvbi, ceTVVersionAppfvbi;
    private LinearLayout ceLLOngoingEventfvbi, ceLLPastEventfvbi;
    private RecyclerView ceRVOngoingEventfvbi, ceRVPastEventfvbi;
    private String appVersionName, appVersionCode, appName, appPkg;
    private Button ceBUserAccountfvbi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_event);

        initComponent();
        initContent();
        initListener();
    }

    private void initComponent() {
        ceIVUserPicfvbi = (ImageView) findViewById(R.id.ceIVUserPic);
        ceTVUserNamefvbi = (TextView) findViewById(R.id.ceTVUserName);
        ceTVUserIdfvbi = (TextView) findViewById(R.id.ceTVUserId);
        ceTVInfofvbi = (TextView) findViewById(R.id.ceTVInfo);
        ceTVVersionAppfvbi = (TextView) findViewById(R.id.ceTVVersionApp);
        ceBUserAccountfvbi = (Button) findViewById(R.id.ceBUserAccount);
        ceLLOngoingEventfvbi = (LinearLayout) findViewById(R.id.ceLLOngoingEvent);
        ceLLPastEventfvbi = (LinearLayout) findViewById(R.id.ceLLPastEvent);
        ceRVOngoingEventfvbi = (RecyclerView) findViewById(R.id.ceRVOngoingEvent);
        ceRVPastEventfvbi = (RecyclerView) findViewById(R.id.ceRVPastEvent);
    }

    private void initContent() {
        customText(ceTVInfofvbi);

        //getversion
        try {
            appVersionName = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
            appVersionCode = String.valueOf(getPackageManager().getPackageInfo(getPackageName(), 0).versionCode);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        appName = getResources().getString(R.string.app_name);
        appPkg = this.getBaseContext().getPackageName();
        ceTVVersionAppfvbi.setText(getString(R.string.app_name) + " - v " + appVersionName + "." + appVersionCode + "" +
                "\n Powered by Gandsoft");


    }

    private void initListener() {
        ceBUserAccountfvbi.setOnClickListener(this::Onclick);
    }

    private void Onclick(View view) {
        if (view == ceBUserAccountfvbi) {
            Intent intent = new Intent(ChangeEventActivity.this, BaseHomeActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private void customText(TextView view) {
        SpannableStringBuilder spanTxt = new SpannableStringBuilder("To create an event, please contact our phone number: ");

        spanTxt.append("+62 21 53661536");
        spanTxt.setSpan(new ClickableSpan() {
                            @Override
                            public void onClick(View widget) {
                                Toast.makeText(getApplicationContext(), "+62 21 53661536 Clicked", Toast.LENGTH_SHORT).show();
                            }
                        },
                spanTxt.length() - "+62 21 53661536".length(),
                spanTxt.length(),
                0);

        spanTxt.append(" or by email at: ");
        spanTxt.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorPrimary)), 32, spanTxt.length(), 0);

        spanTxt.append("hello@gandsoft.com");
        spanTxt.setSpan(new ClickableSpan() {
                            @Override
                            public void onClick(View widget) {
                                Toast.makeText(getApplicationContext(), "hello@gandsoft.com Clicked", Toast.LENGTH_SHORT).show();
                            }
                        },
                spanTxt.length() - "hello@gandsoft.com".length(),
                spanTxt.length(),
                0);

        view.setMovementMethod(LinkMovementMethod.getInstance());
        view.setText(spanTxt, TextView.BufferType.SPANNABLE);
    }

    public static Intent getActIntent(Activity activity) {
        return new Intent(activity, ChangeEventActivity.class);
    }
}
