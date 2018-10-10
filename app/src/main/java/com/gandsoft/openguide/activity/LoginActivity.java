package com.gandsoft.openguide.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.CardView;
import android.text.InputFilter;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gandsoft.openguide.API.API;
import com.gandsoft.openguide.API.APIrequest.Login.LoginRequestModel;
import com.gandsoft.openguide.API.APIresponse.Login.LoginResponseModel;
import com.gandsoft.openguide.ISeasonConfig;
import com.gandsoft.openguide.R;
import com.gandsoft.openguide.database.SQLiteHelper;
import com.gandsoft.openguide.support.DeviceDetailUtil;
import com.gandsoft.openguide.support.InputValidUtil;
import com.gandsoft.openguide.support.NetworkUtil;
import com.gandsoft.openguide.support.SessionUtil;
import com.gandsoft.openguide.support.SystemUtil;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.hbb20.CountryCodePicker;

import java.util.List;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends LocalBaseActivity {

    SQLiteHelper db = new SQLiteHelper(this);

    private static final int UI_LOGIN = 0;
    private static final int UI_VERIFY = 1;
    private static final String TAG = "Lihat LoginActivity";
    private static final int API_CALLER_LOGIN = 1010;

    /**/
    private CountryCodePicker ccpLoginfvbi;
    /*UI*/
    private EditText etLoginfvbi;
    private CardView cvLoginSubmitfvbi;
    private TextView tvLoginSubmitfvbi, tvLoginResendCodefvbi, tvLoginVerifyInfofvbi, tvLoginAppVersionfvbi;
    private LinearLayout llLoginBackfvbi;
    /*UI*/
    private String appVersionName, appVersionCode, appName, appPkg;
    private FirebaseAuth mAuth;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private String phoneNumberSavedwPlus;
    private String phoneNumberSavedwoPlus;
    private String mVerificationId;
    private boolean isLogin, isVerify;
    private ProgressDialog progressDialogSubmit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initComponent();
        initContent();
        initListener();

    }

    private void initComponent() {
        ccpLoginfvbi = (CountryCodePicker) findViewById(R.id.ccpLogin);
        tvLoginVerifyInfofvbi = (TextView) findViewById(R.id.tvLoginVerifyInfo);
        etLoginfvbi = (EditText) findViewById(R.id.etLogin);
        cvLoginSubmitfvbi = (CardView) findViewById(R.id.cvLoginSubmit);
        tvLoginSubmitfvbi = (TextView) findViewById(R.id.tvLoginSubmit);
        tvLoginResendCodefvbi = (TextView) findViewById(R.id.tvLoginResendCode);
        llLoginBackfvbi = (LinearLayout) findViewById(R.id.llLoginBack);
        tvLoginAppVersionfvbi = (TextView) findViewById(R.id.tvLoginAppVersion);
    }

    private void initContent() {
        mAuth = FirebaseAuth.getInstance();

        updateUI(UI_LOGIN, null);

        //getversion
        try {
            appVersionName = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
            appVersionCode = String.valueOf(getPackageManager().getPackageInfo(getPackageName(), 0).versionCode);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        appName = getResources().getString(R.string.app_name);
        appPkg = this.getBaseContext().getPackageName();

        tvLoginAppVersionfvbi.setText(getString(R.string.app_name) + " - v " + appVersionName + "." + appVersionCode);

        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @SuppressLint("LongLogTag")
            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {
                // This callback will be invoked in two situations:
                // 1 - Instant verification. In some cases the phone number can be instantly
                //     verified without needing to send or enter a verification code.
                // 2 - Auto-retrieval. On some devices Google Play services can automatically
                //     detect the incoming verification SMS and perform verification without
                //     user action.

                signInWithPhoneAuthCredential(credential);//onVerificationCompleted

                if (credential != null) {
                    if (credential.getSmsCode() != null) {
                        updateUI(UI_VERIFY, credential);
                        new Handler().postDelayed(new Runnable() {
                            public void run() {
                                //code here
                                etLoginfvbi.setText(credential.getSmsCode());
                            }
                        }, 1000);
                    } else {
                        snackBar("instant validation", false);
                    }
                }
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                // This callback is invoked in an invalid request for verification is made,
                // for instance if the the phone number format is not valid.

                Log.w(TAG, "onVerificationFailed", e);

                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    etLoginfvbi.setError("Invalid phone number, please check your number");
                } else if (e instanceof FirebaseTooManyRequestsException) {
                    snackBar("Quota exceeded, Please try again tommorow.", false); // The SMS quota for the project has been exceeded
                }

                updateUI(UI_LOGIN, null);
            }

            @Override
            public void onCodeSent(String verificationId, PhoneAuthProvider.ForceResendingToken token) {

                // Save verification ID and resending token so we can use them later
                mVerificationId = verificationId;
                mResendToken = token;

                // Update UI
                updateUI(UI_VERIFY, null);
            }
        };
    }

    private void initListener() {
        etLoginfvbi.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                boolean handled = false;
                if (i == EditorInfo.IME_ACTION_GO) {
                    checkData();
                }
                return handled;
            }
        });

        cvLoginSubmitfvbi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkData();
            }
        });

        tvLoginResendCodefvbi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resendVerificationCode(phoneNumberSavedwPlus, mResendToken);
                snackBar("Resend Code", false);
            }
        });

        llLoginBackfvbi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signOut();

            }
        });

        /*bypass login*/
        tvLoginAppVersionfvbi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!InputValidUtil.isEmptyField(etLoginfvbi)) {
                    //SessionUtil.setStringPreferences(ISeasonConfig.KEY_ACCOUNT_ID, getNumberValid(etLoginfvbi));
                    //moveToChangeEvent();
                    if (NetworkUtil.isConnectedIsOnline(LoginActivity.this)) {
                        phoneNumberSavedwPlus = "+" + getNumberValid(etLoginfvbi);
                        phoneNumberSavedwoPlus = getNumberValid(etLoginfvbi);
                        sendLoginData();
                    }
                } else {
                    SystemUtil.etReqFocus(LoginActivity.this, etLoginfvbi, "Data Kosong");
                }
            }
        });
    }

    private void updateUI(int ui, PhoneAuthCredential cred) {
        if (ui == UI_LOGIN) {
            disableViews(tvLoginVerifyInfofvbi, tvLoginResendCodefvbi, llLoginBackfvbi);
            enableViews(ccpLoginfvbi);
            etLoginfvbi.setHint("Enter Phone Number");
            tvLoginSubmitfvbi.setText("VERIFY");
            isLogin = true;
            isVerify = false;
        } else if (ui == UI_VERIFY) {
            enableViews(tvLoginVerifyInfofvbi, tvLoginResendCodefvbi, llLoginBackfvbi);
            disableViews(ccpLoginfvbi);
            etLoginfvbi.setHint("Enter Code");
            etLoginfvbi.setText("");
            tvLoginSubmitfvbi.setText("SEND");
            isLogin = false;
            isVerify = true;

            /*if (cred != null) {
                if (cred.getSmsCode() != null) {
                    etLoginfvbi.setText(cred.getSmsCode());
                } else {
                    snackBar("instant validation", false);
                }
            }*/
        }
    }

    private void checkData() {
        if (!InputValidUtil.isEmptyField(etLoginfvbi)) {
            if (isLogin) {
                if (InputValidUtil.isValidatePhoneNumber(etLoginfvbi.getText().toString())) {
                    //Toast.makeText(getApplicationContext(), getNumberValid(etLoginfvbi), Toast.LENGTH_SHORT).show();
                    startPhoneNumberVerification("+" + getNumberValid(etLoginfvbi));
                    phoneNumberSavedwPlus = "+" + getNumberValid(etLoginfvbi);
                    phoneNumberSavedwoPlus = getNumberValid(etLoginfvbi);
                    snackBar("Start Verify Phone Number", false);
                } else {
                    SystemUtil.etReqFocus(LoginActivity.this, etLoginfvbi, "Data Tidak Valid");
                }
            } else if (isVerify) {
                verifyPhoneNumberWithCode(mVerificationId, etLoginfvbi.getText().toString());
                snackBar("Start Verify Phone Number", false);
            }
        } else {
            SystemUtil.etReqFocus(LoginActivity.this, etLoginfvbi, "Data Kosong");
        }
    }

    private String getNumberValid(EditText editText) {
        String string = editText.getText().toString();
        String sub0 = string.substring(0, 1);
        String sub62 = string.substring(0, 2);
        String subPlus62 = string.substring(0, 3);
        String sVal = null;
        if (sub0.equalsIgnoreCase("0")) {
            sVal = ccpLoginfvbi.getSelectedCountryCode() + string.substring(1);
        } else if (sub62.equalsIgnoreCase(ccpLoginfvbi.getSelectedCountryCode())) {
            sVal = ccpLoginfvbi.getSelectedCountryCode() + string.substring(2);
        } else if (subPlus62.equalsIgnoreCase(ccpLoginfvbi.getSelectedCountryCodeWithPlus())) {
            sVal = ccpLoginfvbi.getSelectedCountryCode() + string.substring(3);
        } else {
            sVal = ccpLoginfvbi.getSelectedCountryCode() + string;
        }
        return sVal;
    }

    private void startPhoneNumberVerification(String phoneNumber) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                60,             // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,       // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks
    }

    private void resendVerificationCode(String phoneNumber, PhoneAuthProvider.ForceResendingToken token) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks,         // OnVerificationStateChangedCallbacks
                token);             // ForceResendingToken from callbacks
    }

    private void verifyPhoneNumberWithCode(String verificationId, String code) {

        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);

        signInWithPhoneAuthCredential(credential);//verifyPhoneNumberWithCode
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @SuppressLint("LongLogTag")
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseUser user = task.getResult().getUser();
                    snackBar("Success", false);
                    sendLoginData();
                } else {
                    if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                        snackBar("Invalid code.", true);
                    }
                }
            }
        });
    }

    private void sendLoginData() {
        LoginRequestModel request = new LoginRequestModel();
        request.setDbver("3");
        request.setDevice_app(DeviceDetailUtil.getAllDataPhone2(this));
        request.setPhonenumber(phoneNumberSavedwoPlus);
        request.setLogin_status("1");

        ProgressDialog progressDialog = ProgressDialog.show(LoginActivity.this, "Loading...", "Please Wait..", false, false);

        API.doLoginRet(request).enqueue(new Callback<List<LoginResponseModel>>() {
            @Override
            public void onResponse(Call<List<LoginResponseModel>> call, Response<List<LoginResponseModel>> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    List<LoginResponseModel> s = response.body();
                    if (s.size() == 1) {
                        for (int i = 0; i < s.size(); i++) {
                            LoginResponseModel model = s.get(i);
                            SessionUtil.setStringPreferences(ISeasonConfig.KEY_ACCOUNT_ID, phoneNumberSavedwoPlus);
                            if (model.getStatus().equalsIgnoreCase("verify")) {
                                Snackbar.make(findViewById(android.R.id.content), "verify", Snackbar.LENGTH_LONG).show();
                                moveToChangeEvent();
                                //db.insertOneKey(SQLiteHelper.TableUserData, SQLiteHelper.KEY_UserData_accountId, phoneNumberSavedwoPlus);
                            } else {
                                Snackbar.make(findViewById(android.R.id.content), "no verify", Snackbar.LENGTH_LONG).show();
                                moveToAccountForNewUser();
                            }
                        }
                    } else {
                        Snackbar.make(findViewById(android.R.id.content), "Data Tidak Sesuai", Snackbar.LENGTH_LONG).show();
                    }
                } else {
                    Snackbar.make(findViewById(android.R.id.content), "Koneksi Ke Server bermasalah", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                }
            }

            @Override
            public void onFailure(Call<List<LoginResponseModel>> call, Throwable t) {
                progressDialog.dismiss();
            }
        });
    }

    @SuppressLint("NewApi")
    private void moveToAccountForNewUser() {
        SessionUtil.setBoolPreferences(ISeasonConfig.KEY_IS_HAS_LOGIN, true);
        startActivity(AccountActivity
                .getActIntent(LoginActivity.this)
                .putExtra(ISeasonConfig.KEY_IS_FIRST_ACCOUNT, true)
        );
        finish();
    }

    private void moveToChangeEvent() {
        SessionUtil.setBoolPreferences(ISeasonConfig.KEY_IS_HAS_LOGIN, true);
        startActivity(ChangeEventActivity
                .getActIntent(LoginActivity.this)
        );
        finish();
    }

    public void setEditTextMaxLength(int length) {
        InputFilter[] filterArray = new InputFilter[1];
        filterArray[0] = new InputFilter.LengthFilter(length);
        etLoginfvbi.setFilters(filterArray);
    }

    private void enableViews(View... views) {
        for (View v : views) {
            //v.setEnabled(true);
            v.setVisibility(View.VISIBLE);
        }
    }

    private void disableViews(View... views) {
        for (View v : views) {
            //v.setEnabled(false);
            v.setVisibility(View.GONE);
        }
    }

    private void snackBar(String success, Boolean reloadAction) {
        if (reloadAction) {
            Snackbar.make(findViewById(android.R.id.content), success, Snackbar.LENGTH_INDEFINITE)
                    .setAction("RELOAD", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            checkData();
                        }
                    })
                    .show();
        } else {
            Snackbar.make(findViewById(android.R.id.content), success, Snackbar.LENGTH_LONG).show();
        }
    }

    private void signOut() {
        mAuth.signOut();
        updateUI(UI_LOGIN, null);
        Snackbar.make(findViewById(android.R.id.content), "Success Sign Out", Snackbar.LENGTH_SHORT).show();
    }

    public static Intent getActIntent(Activity activity) {
        return new Intent(activity, LoginActivity.class);
    }
}
