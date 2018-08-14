package com.gandsoft.openguide.activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.icu.util.ULocale;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gandsoft.openguide.API.API;
import com.gandsoft.openguide.API.APIrequest.UserUpdate.UserUpdateRequestModel;
import com.gandsoft.openguide.API.APIresponse.UserData.UserDataResponseModel;
import com.gandsoft.openguide.API.APIresponse.UserUpdate.UserUpdateResponseModel;
import com.gandsoft.openguide.ISeasonConfig;
import com.gandsoft.openguide.R;
import com.gandsoft.openguide.database.SQLiteHelper;
import com.gandsoft.openguide.support.SessionUtil;
import com.google.android.gms.games.request.Requests;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@RequiresApi(api = Build.VERSION_CODES.N)
public class AccountActivity extends LocalBaseActivity implements View.OnClickListener {
    SQLiteHelper db = new SQLiteHelper(this);

    private static final int UI_NEW_USER = 0;
    private static final int UI_OLD_USER = 1;
    private FirebaseAuth mAuth;
    /**/
    private TextView tvAccIDfvbi, tvAccSelPicfvbi,tvAccGenderfvbi, tvAccTglfvbi, tvAccBulanfvbi, tvAccTahunfvbi, tvAccAggrementfvbi, tvSignOutSkipfvbi;
    private EditText etAccNamefvbi, etAccEmailfvbi;
    private LinearLayout llAccPicfvbi, llAccGenderfvbi, llAccBirthdatefvbi, llAccAggrementfvbi, llAccSavefvbi;
    private CheckBox cbAccAggrementfvbi;
    private ImageButton ibAccClosefvbi, ibAccCamerafvbi, ibCalendarfvbi;
    /**/
    private boolean isNewUser = false;
    private String accountId;

    Calendar myCalendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        if (SessionUtil.checkIfExist(ISeasonConfig.KEY_ACCOUNT_ID)) {
            accountId = SessionUtil.getStringPreferences(ISeasonConfig.KEY_ACCOUNT_ID, null);
            Log.d("Lihat", "onCreate AccountActivity : " + accountId);
        }

        initComponent();
        initContent();
        initListener();
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        ibCalendarfvbi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(AccountActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }

        });
    }

    private void initComponent() {
        tvAccIDfvbi = (TextView) findViewById(R.id.tvAccID);
        ibAccClosefvbi = (ImageButton) findViewById(R.id.ibAccClose);

        ibAccCamerafvbi = (ImageButton) findViewById(R.id.ibAccCamera);
        tvAccSelPicfvbi = (TextView) findViewById(R.id.tvAccSelPic);

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

        ibCalendarfvbi = (ImageButton) findViewById(R.id.ibAccCalendar);
        llAccBirthdatefvbi = (LinearLayout) findViewById(R.id.llAccBirthdate);
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

        if (!db.isDataTableValueNull(SQLiteHelper.TableUserData, SQLiteHelper.KEY_UserData_accountId, accountId)) {
            updateData(db.getUserData(accountId));
        }else {
            Snackbar.make(findViewById(android.R.id.content), "data kosong", Snackbar.LENGTH_LONG).show();
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
        ibAccClosefvbi.setOnClickListener(this);
        ibAccCamerafvbi.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                startActivity(intent);
            }
        });
        tvAccSelPicfvbi.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivity(intent);
            }
        });
    }

    private void updateData(ArrayList<UserDataResponseModel> models) {
        tvAccIDfvbi.setText(accountId);
        for (int i = 0; i < models.size(); i++) {
            UserDataResponseModel model = models.get(i);
            etAccNamefvbi.setText(model.getFull_name());
            etAccEmailfvbi.setText(model.getEmail());
            tvAccGenderfvbi.setText(model.getGender());
            String[] q = model.getBirthday().split("-");
            tvAccTahunfvbi.setText(q[0]);
            tvAccBulanfvbi.setText(q[1]);
            tvAccTglfvbi.setText(q[2]);
        }
    }

    private void updateUI(int ui) {
        if (ui == UI_NEW_USER) {
            llAccAggrementfvbi.setVisibility(View.VISIBLE);
 //           ibAccClosefvbi.setVisibility(View.GONE);
            tvSignOutSkipfvbi.setText("SKIP THIS STEP");
        } else if (ui == UI_OLD_USER) {
 //           llAccAggrementfvbi.setVisibility(View.GONE);
            ibAccClosefvbi.setVisibility(View.VISIBLE);
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
                        finishAffinity();
                        SessionUtil.setBoolPreferences(ISeasonConfig.KEY_IS_HAS_LOGIN, false);
                        db.deleteAllDataUser();
                        db.deleteAllDataListEvent();
                        db.deleteAllDataWallet();
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
            saveClick();
        } else if (v == tvSignOutSkipfvbi) {
            skipLogoutClick();
        } else if (v == ibAccClosefvbi) {
            finish();
        }
    }

    private void skipLogoutClick() {
        if (isNewUser) {
            moveToChangeEvent();
        } else {
            signOut();
        }
    }

    private void saveClick() {
        if (isNewUser) {
            if (cbAccAggrementfvbi.isChecked()) {
                UserUpdateRequestModel requestModel = new UserUpdateRequestModel();
                requestModel.setAccounsId(accountId);
                requestModel.setFileImageB641("");
                requestModel.setDbver("3");
                requestModel.setDegree_image("ANDROID");
                requestModel.setPrivacypolicy(true);
                requestModel.setName(etAccNamefvbi.getText().toString());
                requestModel.setEmail(etAccEmailfvbi.getText().toString());
                requestModel.setGender(tvAccGenderfvbi.getText().toString());
                requestModel.setDate(tvAccTglfvbi.getText().toString());
                requestModel.setMonth(tvAccBulanfvbi.getText().toString());
                requestModel.setYear(tvAccTahunfvbi.getText().toString());
                API.doUserUpdateRet(requestModel).enqueue(new Callback<List<UserUpdateResponseModel>>() {
                    @Override
                    public void onResponse(Call<List<UserUpdateResponseModel>> call, Response<List<UserUpdateResponseModel>> response) {
                        if(response.isSuccessful()) {
                            moveToChangeEvent();
                        }
                        else {
                            Log.d("gagal",response.message());
                        }
                    }

                    @Override
                    public void onFailure(Call<List<UserUpdateResponseModel>> call, Throwable t) {
                        Log.d("gagal",t.getMessage());
                    }
                });
            } else {
                Snackbar.make(findViewById(android.R.id.content), "Checked Egreement First!!", Snackbar.LENGTH_LONG).show();
                cbAccAggrementfvbi.requestFocus();
            }
        } else {
            finish();
        }
    }

    public static Intent getActIntent(Activity activity) {
        return new Intent(activity, AccountActivity.class);
    }

    @Override
    public void onBackPressed() {
        saveClick();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void updateLabel() {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, ULocale.US);
        Log.d("anuu",sdf.format(myCalendar.getTime()));
        String a[]= sdf.format(myCalendar.getTime()).split("-");

        tvAccTglfvbi.setText(a[2]);
        tvAccBulanfvbi.setText(a[1]);
        tvAccTahunfvbi.setText(a[0]);
//        etAccBirthdayfvbi.setText(sdf.format(myCalendar.getTime()));
    }
}
