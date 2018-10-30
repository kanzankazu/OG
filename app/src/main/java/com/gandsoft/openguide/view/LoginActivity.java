package com.gandsoft.openguide.view;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gandsoft.openguide.API.API;
import com.gandsoft.openguide.API.APIrequest.Login.PostVerifyPhonenumberFirebaseRequestModel;
import com.gandsoft.openguide.API.APIrequest.PostVerifyLoginUserRequestModel;
import com.gandsoft.openguide.API.APIrequest.PostVerifyTokenFirebaseRequestModel;
import com.gandsoft.openguide.API.APIresponse.Login.PostVerifyPhonenumberFirebaseResponseModel;
import com.gandsoft.openguide.API.APIresponse.PostVerifyLoginUserResponseModel;
import com.gandsoft.openguide.API.APIresponse.PostVerifyTokenFirebaseResponseModel;
import com.gandsoft.openguide.IConfig;
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
import com.google.firebase.iid.FirebaseInstanceId;
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
    private boolean isLogin, isVerify, isVerifyOldUser;
    private ProgressDialog progressDialogSubmit;
    private String refreshedToken;
    private boolean isBypass = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        refreshedToken = FirebaseInstanceId.getInstance().getToken();

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

                phoneNumberSignInPhoneAuthWithCredential(credential);//onVerificationCompleted

                if (credential != null) {
                    if (credential.getSmsCode() != null) {
                        updateUI(UI_VERIFY, credential);
                        new Handler().postDelayed(new Runnable() {
                            public void run() {
                                //code here
                                etLoginfvbi.setText(credential.getSmsCode());
                            }
                        }, 500);
                    } else {
                        //snackBar("instant validation", false);
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
                    initPhoneNumberValidationInput();//ime
                }
                return handled;
            }
        });

        cvLoginSubmitfvbi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SystemUtil.hideKeyBoard(LoginActivity.this);
                initPhoneNumberValidationInput();//click
            }
        });

        tvLoginResendCodefvbi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                phoneNumberResendVerificationCode(phoneNumberSavedwPlus, mResendToken);
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
                    //SessionUtil.setStringPreferences(ISeasonConfig.KEY_ACCOUNT_ID, getPhoneNumberValid(etLoginfvbi));
                    //moveToChangeEvent();
                    if (NetworkUtil.isConnectedIsOnline(LoginActivity.this)) {

                        SessionUtil.removeAllSharedPreferences();

                        phoneNumberSavedwPlus = "+" + getPhoneNumberValid(etLoginfvbi);
                        phoneNumberSavedwoPlus = getPhoneNumberValid(etLoginfvbi);
                        sendVerifyPhonenumberFirebase();//click bypass
                        isBypass = true;
                    }
                } else {
                    SystemUtil.etReqFocus(LoginActivity.this, etLoginfvbi, "Data Kosong");
                }
            }
        });
    }

    private void initPhoneNumberValidationInput() {
        if (!InputValidUtil.isEmptyField(etLoginfvbi)) {
            if (isLogin) {
                if (InputValidUtil.isValidatePhoneNumber(etLoginfvbi.getText().toString())) {
                    phoneNumberSavedwPlus = "+" + getPhoneNumberValid(etLoginfvbi);
                    phoneNumberSavedwoPlus = getPhoneNumberValid(etLoginfvbi);
                    //Toast.makeText(getApplicationContext(), getPhoneNumberValid(etLoginfvbi), Toast.LENGTH_SHORT).show();
                    //sendPhoneNumberVerification("+" + getPhoneNumberValid(etLoginfvbi));
                    snackBar("Start Verify Phone Number", false);
                    sendVerifyPhonenumberFirebase();
                } else {
                    SystemUtil.etReqFocus(LoginActivity.this, etLoginfvbi, "Data Tidak Valid");
                }
            } else if (isVerify) {
                phoneNumberVerifyWithCode(mVerificationId, etLoginfvbi.getText().toString());
                snackBar("Start Verify Phone Number", false);
            }
        } else {
            SystemUtil.etReqFocus(LoginActivity.this, etLoginfvbi, "Data Kosong");
        }
    }

    private void updateUI(int ui, PhoneAuthCredential cred) {
        if (ui == UI_LOGIN) {
            disableViews(tvLoginVerifyInfofvbi, tvLoginResendCodefvbi, llLoginBackfvbi);
            enableViews(ccpLoginfvbi);
            etLoginfvbi.setHint("Enter Phone Number");
            tvLoginSubmitfvbi.setText("VERIFY NUMBER");
            isLogin = true;
            isVerify = false;
        } else if (ui == UI_VERIFY) {
            enableViews(tvLoginVerifyInfofvbi, tvLoginResendCodefvbi, llLoginBackfvbi);
            disableViews(ccpLoginfvbi);
            etLoginfvbi.setHint("Enter Code");
            etLoginfvbi.setText("");
            tvLoginSubmitfvbi.setText("VERIFY OTP");
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

    /**/
    private void sendVerifyPhonenumberFirebase() {
        if (NetworkUtil.isConnected(getApplicationContext())) {
            PostVerifyPhonenumberFirebaseRequestModel request = new PostVerifyPhonenumberFirebaseRequestModel();
            request.setDbver(String.valueOf(IConfig.DB_Version));
            request.setDevice_app(DeviceDetailUtil.getAllDataPhone2(this));
            request.setPhonenumber(phoneNumberSavedwoPlus);
            request.setLogin_status("1");

            ProgressDialog progressDialog = ProgressDialog.show(LoginActivity.this, "Loading...", "Please Wait..", false, false);

            API.doPostVerifyPhonenumberFirebaseRet(request).enqueue(new Callback<List<PostVerifyPhonenumberFirebaseResponseModel>>() {
                @Override
                public void onResponse(Call<List<PostVerifyPhonenumberFirebaseResponseModel>> call, Response<List<PostVerifyPhonenumberFirebaseResponseModel>> response) {
                    progressDialog.dismiss();
                    if (response.isSuccessful()) {
                        List<PostVerifyPhonenumberFirebaseResponseModel> s = response.body();
                        if (s.size() == 1) {
                            for (int i = 0; i < s.size(); i++) {
                                PostVerifyPhonenumberFirebaseResponseModel model = s.get(i);
                                if (model.getStatus().equalsIgnoreCase("verify")) {
                                    Snackbar.make(findViewById(android.R.id.content), "verify", Snackbar.LENGTH_LONG).show();
                                    isVerifyOldUser = true;
                                } else {
                                    Snackbar.make(findViewById(android.R.id.content), "number verify", Snackbar.LENGTH_LONG).show();
                                    isVerifyOldUser = false;
                                }

                                if (isBypass) {
                                    moveToChangeEvent();//bypass
                                } else {
                                    sendPhoneNumberVerification("+" + getPhoneNumberValid(etLoginfvbi));
                                }
                            }
                        } else {
                            Snackbar.make(findViewById(android.R.id.content), "error data", Snackbar.LENGTH_LONG).show();
                        }
                    } else {
                        Snackbar.make(findViewById(android.R.id.content), "get data unsuccessful", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                    }
                }

                @Override
                public void onFailure(Call<List<PostVerifyPhonenumberFirebaseResponseModel>> call, Throwable t) {
                    progressDialog.dismiss();
                    Log.d("Lihat", "onFailure LoginActivity : " + t.getMessage());
                    //Snackbar.make(findViewById(android.R.id.content), "Failed Connection To Server", Snackbar.LENGTH_SHORT).show();
                /*Snackbar.make(findViewById(android.R.id.content), "Failed Connection To Server", Snackbar.LENGTH_SHORT).setAction("Reload", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                }).show();*/
                    //Crashlytics.logException(new Exception(t.getMessage()));
                }
            });
        } else {
            Snackbar.make(findViewById(android.R.id.content), "Check you connection", Snackbar.LENGTH_SHORT).show();
        }

    }

    private void sendPhoneNumberVerification(String phoneNumber) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                60,             // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,       // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks
    }

    private void phoneNumberResendVerificationCode(String phoneNumber, PhoneAuthProvider.ForceResendingToken token) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks,         // OnVerificationStateChangedCallbacks
                token);             // ForceResendingToken from callbacks
    }

    private void phoneNumberVerifyWithCode(String verificationId, String code) {

        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);

        phoneNumberSignInPhoneAuthWithCredential(credential);//phoneNumberVerifyWithCode
    }

    private void phoneNumberSignInPhoneAuthWithCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @SuppressLint("LongLogTag")
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseUser user = task.getResult().getUser();
                    snackBar("Success", false);
                    if (isVerifyOldUser) {
                        sendVerificationLoginUsers();//oldUser
                    } else {
                        sendVerivyTokenAccountFirebase();
                    }
                } else {
                    if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                        snackBar("Invalid code.", true);
                    }
                }
            }
        });
    }

    private void sendVerivyTokenAccountFirebase() {
        if (NetworkUtil.isConnected(getApplicationContext())) {
            PostVerifyTokenFirebaseRequestModel requestModel = new PostVerifyTokenFirebaseRequestModel();
            requestModel.setPhonenumber(phoneNumberSavedwoPlus);
            requestModel.setToken(refreshedToken);
            requestModel.setCondition("verifytoken");
            requestModel.setDbver(String.valueOf(IConfig.DB_Version));
            requestModel.setCodecountry(ccpLoginfvbi.getDefaultCountryCode());

            ProgressDialog progressDialog = ProgressDialog.show(LoginActivity.this, "Loading...", "Please Wait..", false, false);

            API.doPostVerivyTokenAccountFirebase(requestModel).enqueue(new Callback<List<PostVerifyTokenFirebaseResponseModel>>() {
                @Override
                public void onResponse(Call<List<PostVerifyTokenFirebaseResponseModel>> call, Response<List<PostVerifyTokenFirebaseResponseModel>> response) {
                    progressDialog.dismiss();
                    if (response.isSuccessful()) {
                        List<PostVerifyTokenFirebaseResponseModel> mods = response.body();
                        if (mods.size() == 1) {
                            for (int i = 0; i < mods.size(); i++) {
                                PostVerifyTokenFirebaseResponseModel mod1 = mods.get(i);
                                if (mod1.getStatus().equalsIgnoreCase("verify")) {
                                    sendVerificationLoginUsers();//newUser
                                } else {
                                    signOut();
                                }
                            }
                        } else {
                            Log.d("Lihat", "onResponse LoginActivity : " + response.message());
                            //Snackbar.make(findViewById(android.R.id.content), "error data", Snackbar.LENGTH_LONG).show();
                            //Crashlytics.logException(new Exception(response.message()));
                        }
                    } else {
                        Log.d("Lihat", "onResponse LoginActivity : " + response.message());
                        //Snackbar.make(findViewById(android.R.id.content), "Failed Connection To Server", Snackbar.LENGTH_LONG).show();
                        //Crashlytics.logException(new Exception(response.message()));
                    }
                }

                @Override
                public void onFailure(Call<List<PostVerifyTokenFirebaseResponseModel>> call, Throwable t) {
                    progressDialog.dismiss();
                    Log.d("Lihat", "onFailure LoginActivity : " + t.getMessage());
                    //Snackbar.make(findViewById(android.R.id.content), "Failed Connection To Server", Snackbar.LENGTH_SHORT).show();
                /*Snackbar.make(findViewById(android.R.id.content), "Failed Connection To Server", Snackbar.LENGTH_SHORT).setAction("Reload", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                }).show();*/
                    //Crashlytics.logException(new Exception(t.getMessage()));
                    signOut();
                }
            });
        } else {
            Snackbar.make(findViewById(android.R.id.content), "Check you connection", Snackbar.LENGTH_SHORT).show();
        }

    }

    private void sendVerificationLoginUsers() {
        new Handler().postDelayed(new Runnable() {
            public void run() {
                if (NetworkUtil.isConnected(getApplicationContext())) {
                    PostVerifyLoginUserRequestModel requestModel = new PostVerifyLoginUserRequestModel();
                    requestModel.setPhonenumber(phoneNumberSavedwoPlus);
                    requestModel.setToken(refreshedToken);
                    requestModel.setPassword("12345");
                    if (isVerifyOldUser) {
                        requestModel.setKondisi("oldpassword");
                    } else {
                        requestModel.setKondisi("createpass");
                    }
                    requestModel.setDbver(String.valueOf(IConfig.DB_Version));

                    API.doPostVerificationLoginUsers(requestModel).enqueue(new Callback<List<PostVerifyLoginUserResponseModel>>() {
                        @Override
                        public void onResponse(Call<List<PostVerifyLoginUserResponseModel>> call, Response<List<PostVerifyLoginUserResponseModel>> response) {
                            //progressDialog.dismiss();
                            if (response.isSuccessful()) {
                                List<PostVerifyLoginUserResponseModel> mods = response.body();
                                if (mods.size() == 1) {
                                    for (int i = 0; i < mods.size(); i++) {
                                        PostVerifyLoginUserResponseModel mod1 = mods.get(i);
                                        if (mod1.getStatus().equalsIgnoreCase("verify")) {
                                            if (isVerifyOldUser) {
                                                moveToChangeEvent();//standart
                                            } else {
                                                moveToAccountForNewUser();

                                            }
                                        } else {
                                            signOut();
                                        }
                                    }
                                } else {
                                    Log.d("Lihat", "onResponse LoginActivity : " + response.message());
                                    //Snackbar.make(findViewById(android.R.id.content), "error data", Snackbar.LENGTH_LONG).show();
                                    //Crashlytics.logException(new Exception(response.message()));
                                }
                            } else {
                                Log.d("Lihat", "onResponse LoginActivity : " + response.message());
                                //Snackbar.make(findViewById(android.R.id.content), "Failed Connection To Server", Snackbar.LENGTH_LONG).show();
                                //Crashlytics.logException(new Exception(response.message()));
                            }
                        }

                        @Override
                        public void onFailure(Call<List<PostVerifyLoginUserResponseModel>> call, Throwable t) {
                            //progressDialog.dismiss();
                            Log.d("Lihat", "onFailure LoginActivity : " + t.getMessage());
                            //Snackbar.make(findViewById(android.R.id.content), "Failed Connection To Server", Snackbar.LENGTH_SHORT).show();
                            //Crashlytics.logException(new Exception(t.getMessage()));
                            signOut();
                        }
                    });
                } else {
                    Snackbar.make(findViewById(android.R.id.content), "Check you connection", Snackbar.LENGTH_SHORT).show();
                }

            }
        }, 1000);
    }
    /**/

    private void moveToAccountForNewUser() {
        SessionUtil.setStringPreferences(ISeasonConfig.KEY_ACCOUNT_ID, phoneNumberSavedwoPlus);//new
        SessionUtil.setBoolPreferences(ISeasonConfig.KEY_IS_HAS_LOGIN, true);//new
        Intent intent = new Intent(LoginActivity.this, AccountActivity.class);
        intent.putExtra(ISeasonConfig.KEY_IS_FIRST_ACCOUNT, true);
        startActivity(intent);
        finish();
    }

    private void moveToChangeEvent() {
        SessionUtil.setStringPreferences(ISeasonConfig.KEY_ACCOUNT_ID, phoneNumberSavedwoPlus);//old
        SessionUtil.setBoolPreferences(ISeasonConfig.KEY_IS_HAS_LOGIN, true);//old
        startActivity(ChangeEventActivity.getActIntent(LoginActivity.this)
        );
        finish();
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

    private String getPhoneNumberValid(EditText editText) {
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

    private void snackBar(String success, Boolean reloadAction) {
        if (reloadAction) {
            Snackbar.make(findViewById(android.R.id.content), success, Snackbar.LENGTH_INDEFINITE).setAction("Reload", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    initPhoneNumberValidationInput();//reload
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

}
