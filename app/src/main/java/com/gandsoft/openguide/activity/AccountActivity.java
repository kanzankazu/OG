package com.gandsoft.openguide.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gandsoft.openguide.ISeasonConfig;
import com.gandsoft.openguide.R;
import com.gandsoft.openguide.database.SQLiteHelper;
import com.gandsoft.openguide.presenter.SeasonManager.SessionUtil;
import com.google.firebase.auth.FirebaseAuth;

public class AccountActivity extends LocalBaseActivity implements View.OnClickListener {
    SQLiteHelper db = new SQLiteHelper(this);

    private static final int UI_NEW_USER = 0;
    private static final int UI_OLD_USER = 1;
    private FirebaseAuth mAuth;
    private TextView tvAccIDfvbi, tvAccGenderfvbi, tvAccTglfvbi, tvAccBulanfvbi, tvAccTahunfvbi, tvAccAggrementfvbi, tvSignOutSkipfvbi;
    private EditText etAccNamefvbi, etAccEmailfvbi;
    private LinearLayout llAccPicfvbi, llAccGenderfvbi, llAccAggrementfvbi, llAccSavefvbi;
    private CheckBox cbAccAggrementfvbi;
    /**/
    private boolean isNewUser = false;
    private String accountId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        initComponent();
        initContent();
        initListener();
    }

    private void initComponent() {
        tvAccIDfvbi = (TextView) findViewById(R.id.tvAccID);
        tvAccGenderfvbi = (TextView) findViewById(R.id.tvAccGender);
        tvAccTglfvbi = (TextView) findViewById(R.id.tvAccTgl);
        tvAccBulanfvbi = (TextView) findViewById(R.id.tvAccBulan);
        tvAccTahunfvbi = (TextView) findViewById(R.id.tvAccTahun);
        tvAccAggrementfvbi = (TextView) findViewById(R.id.tvAccAggrement);
        tvSignOutSkipfvbi = (TextView) findViewById(R.id.tvSignOutSkip);
        etAccNamefvbi = (EditText) findViewById(R.id.etAccName);
        etAccEmailfvbi = (EditText) findViewById(R.id.etAccEmail);
        llAccPicfvbi = (LinearLayout) findViewById(R.id.llAccPic);
        llAccGenderfvbi = (LinearLayout) findViewById(R.id.llAccGender);
        llAccAggrementfvbi = (LinearLayout) findViewById(R.id.llAccAggrement);
        llAccSavefvbi = (LinearLayout) findViewById(R.id.llAccSave);
        cbAccAggrementfvbi = (CheckBox) findViewById(R.id.cbAccAggrement);
    }

    private void initContent() {
        Intent bundle = getIntent();
        if (bundle.hasExtra(ISeasonConfig.KEY_IS_FIRST_ACCOUNT)) {
            isNewUser = bundle.getBooleanExtra(ISeasonConfig.KEY_IS_FIRST_ACCOUNT, false);
            if (isNewUser) {
                updateUI(UI_NEW_USER);
            } else {
                updateUI(UI_OLD_USER);
            }
        } else {
            updateUI(UI_OLD_USER);
            isNewUser = false;
        }
        if (bundle.hasExtra(ISeasonConfig.KEY_ACCOUNT_ID)) {
            accountId = bundle.getStringExtra(ISeasonConfig.KEY_ACCOUNT_ID);
            tvAccIDfvbi.setText(accountId);
        } else {
            accountId = db.getAccountId();
            tvAccIDfvbi.setText(accountId);
        }

        mAuth = FirebaseAuth.getInstance();

        customText(tvAccAggrementfvbi);
        tvSignOutSkipfvbi.setPaintFlags(tvSignOutSkipfvbi.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
    }

    private void initListener() {
        llAccPicfvbi.setOnClickListener(this);
        llAccGenderfvbi.setOnClickListener(this);
        llAccSavefvbi.setOnClickListener(this);
        tvSignOutSkipfvbi.setOnClickListener(this);
    }

    private void updateUI(int ui) {
        if (ui == UI_NEW_USER) {
            llAccAggrementfvbi.setVisibility(View.VISIBLE);
            tvSignOutSkipfvbi.setText("SKIP THIS STEP");
        } else if (ui == UI_OLD_USER) {
            llAccAggrementfvbi.setVisibility(View.GONE);
            tvSignOutSkipfvbi.setText("SIGN-OUT");
        }
    }

    private void customText(TextView view) {
        SpannableStringBuilder spanTxt = new SpannableStringBuilder("I agree to the ");
        spanTxt.append("PRIVACY POLICY");
        spanTxt.setSpan(new ClickableSpan() {
                            @Override
                            public void onClick(View widget) {
                                Toast.makeText(getApplicationContext(), "PRIVACY POLICY Clicked", Toast.LENGTH_SHORT).show();
                            }
                        },
                spanTxt.length() - "PRIVACY POLICY".length(),
                spanTxt.length(),
                0);
        view.setMovementMethod(LinkMovementMethod.getInstance());
        view.setText(spanTxt, TextView.BufferType.SPANNABLE);

        /*SpannableStringBuilder spanTxt = new SpannableStringBuilder("I agree to the ");
        spanTxt.append("Term of services");
        spanTxt.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                Toast.makeText(getApplicationContext(), "Terms of services Clicked", Toast.LENGTH_SHORT).show();
            }
        }, spanTxt.length() - "Term of services".length(), spanTxt.length(), 0);
        spanTxt.append(" and");
        spanTxt.setSpan(new ForegroundColorSpan(Color.BLACK), 32, spanTxt.length(), 0);
        spanTxt.append(" Privacy Policy");
        spanTxt.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                Toast.makeText(getApplicationContext(), "Privacy Policy Clicked", Toast.LENGTH_SHORT).show();
            }
        }, spanTxt.length() - " Privacy Policy".length(), spanTxt.length(), 0);
        view.setMovementMethod(LinkMovementMethod.getInstance());
        view.setText(spanTxt, TextView.BufferType.SPANNABLE);*/
    }

    private void signOut() {
        // Munculkan alert dialog apabila user ingin keluar aplikasi
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("ARE YOU SURE WANNA SIGN-OUT?");
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setPositiveButton("YES",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        mAuth.signOut();
                        Snackbar.make(findViewById(android.R.id.content), "Success Sign Out", Snackbar.LENGTH_SHORT).show();
                        startActivity(new Intent(AccountActivity.this, LoginActivity.class));
                        finish();
                        SessionUtil.setBoolPreferences(ISeasonConfig.KEY_IS_HAS_LOGIN, false);
                    }
                });
        // Pilihan jika NO
        alertDialogBuilder.setNegativeButton("CANCEL",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                    }
                });
        // Tampilkan alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }

    private void moveToChangeEvent() {
        Intent intent = new Intent(AccountActivity.this, ChangeEventActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onClick(View v) {
        if (v == llAccPicfvbi) {
        } else if (v == llAccGenderfvbi) {
        } else if (v == llAccSavefvbi) {
            if (isNewUser) {
                if (cbAccAggrementfvbi.isChecked()) {
                    moveToChangeEvent();
                } else {
                    Snackbar.make(findViewById(android.R.id.content), "Checked Egreement First!!", Snackbar.LENGTH_LONG).show();
                    cbAccAggrementfvbi.requestFocus();
                }
            } else {

            }

        } else if (v == tvSignOutSkipfvbi) {
            if (isNewUser) {
                moveToChangeEvent();
            } else {
                signOut();
            }
        }
    }

    public static Intent getActIntent(Activity activity) {
        return new Intent(activity, AccountActivity.class);
    }
}
