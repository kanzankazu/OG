package com.gandsoft.openguide.view;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.icu.text.SimpleDateFormat;
import android.icu.util.ULocale;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.gandsoft.openguide.API.API;
import com.gandsoft.openguide.API.APIrequest.UserUpdate.UserUpdateRequestModel;
import com.gandsoft.openguide.API.APIresponse.UserData.GetListUserEventResponseModel;
import com.gandsoft.openguide.API.APIresponse.UserUpdate.UserUpdateResponseModel;
import com.gandsoft.openguide.ISeasonConfig;
import com.gandsoft.openguide.R;
import com.gandsoft.openguide.database.SQLiteHelper;
import com.gandsoft.openguide.database.SQLiteHelperMethod;
import com.gandsoft.openguide.support.AppUtil;
import com.gandsoft.openguide.support.DateTimeUtil;
import com.gandsoft.openguide.support.NetworkUtil;
import com.gandsoft.openguide.support.NotifUtil;
import com.gandsoft.openguide.support.PictureUtil;
import com.gandsoft.openguide.support.ServiceUtil;
import com.gandsoft.openguide.support.SessionUtil;
import com.gandsoft.openguide.support.SystemUtil;
import com.gandsoft.openguide.view.services.RepeatCheckDataService;
import com.google.firebase.auth.FirebaseAuth;
import com.soundcloud.android.crop.Crop;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@RequiresApi(api = Build.VERSION_CODES.N)
public class AccountActivity extends LocalBaseActivity implements View.OnClickListener {
    private static final int RP_ACCESS = 21;
    private static final int REQ_COD_CROP = 6;
    private static final int REQ_COD_CAMERA_KITKAT = 7;
    private static final int REQ_COD_CAMERA_NOUGAT = 9;
    private static final int REQ_CODE_TAKE_PHOTO_INTENT_ID_STANDART = 10;
    private static final int UI_NEW_USER = 0;
    private static final int UI_OLD_USER = 1;
    private static final String CAPTURE_IMAGE_FILE_PROVIDER = "com.gandsoft.openguide";
    SQLiteHelperMethod db = new SQLiteHelperMethod(this);
    private FirebaseAuth mAuth;

    private TextView tvAccIDfvbi, tvAccSelPicfvbi, tvAccGenderfvbi, tvAccTglfvbi, tvAccBulanfvbi, tvAccTahunfvbi, tvAccAggrementfvbi, tvSignOutSkipfvbi;
    private EditText etAccNamefvbi, etAccEmailfvbi;
    private LinearLayout llAccPicfvbi, llAccGenderfvbi, llAccBirthdatefvbi, llAccAggrementfvbi, llAccSavefvbi;
    private CheckBox cbAccAggrementfvbi;
    private ImageButton ibAccClosefvbi, ibAccCamerafvbi, ibCalendarfvbi;
    private ImageView ivWrapPicfvbi;
    private LinearLayout llWrapPicfvbi;

    private boolean isNewUser = false;
    private String accountId, eventId;

    private Spinner mySpinner;
    private Calendar myCalendar;

    private String imageFilePath;
    private String imageFilePath2;
    private Bitmap bitmapCrop;
    private Uri uriCrop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        initSession();
        initPermission();
        //initComponent();
        //initContent();
        //initListener();

    }

    private void initSession() {
        if (SessionUtil.checkIfExist(ISeasonConfig.KEY_ACCOUNT_ID)) {
            accountId = SessionUtil.getStringPreferences(ISeasonConfig.KEY_ACCOUNT_ID, null);
        }
        if (SessionUtil.checkIfExist(ISeasonConfig.KEY_EVENT_ID)) {
            eventId = SessionUtil.getStringPreferences(ISeasonConfig.KEY_EVENT_ID, null);
        }
    }

    private void initPermission() {
        // cek apakah sudah memiliki permission untuk access fine location
        if (
                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                        ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                        ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED
                ) {
            // cek apakah perlu menampilkan info kenapa membutuhkan access fine location
            if (
                    ActivityCompat.shouldShowRequestPermissionRationale(AccountActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) ||
                            ActivityCompat.shouldShowRequestPermissionRationale(AccountActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) ||
                            ActivityCompat.shouldShowRequestPermissionRationale(AccountActivity.this, Manifest.permission.READ_PHONE_STATE)
                    ) {
                String[] perm = {
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_PHONE_STATE
                };
                ActivityCompat.requestPermissions(AccountActivity.this, perm, RP_ACCESS);
            } else {
                // request permission untuk access fine location
                String[] perm = {
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_PHONE_STATE
                };
                ActivityCompat.requestPermissions(AccountActivity.this, perm, RP_ACCESS);
            }
        } else {
            // permission access fine location didapat
            // SystemUtil.showToast(ChangeEventActivity.this, "Yay, has permission", Toast.LENGTH_SHORT,Gravity.TOP);
            initComponent();
            initContent();
            initListener();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case RP_ACCESS: //private final int = 1
                Log.d("Lihat", "onRequestPermissionsResult ChangeEventActivity grantResults.length : " + grantResults.length);
                Log.d("Lihat", "onRequestPermissionsResult ChangeEventActivity permissions.length : " + permissions.length);
                boolean isPerpermissionForAllGranted = false;
                ArrayList<String> listStat = new ArrayList<>();
                if (grantResults.length > 0 && permissions.length == grantResults.length) {
                    for (int i = 0; i < permissions.length; i++) {
                        if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                            isPerpermissionForAllGranted = true;
                            listStat.add("1");
                        } else {
                            isPerpermissionForAllGranted = false;
                            listStat.add("0");
                        }
                    }
                } else {
                    isPerpermissionForAllGranted = true;
                }

                int frequency0 = Collections.frequency(listStat, "0");
                int frequency1 = Collections.frequency(listStat, "1");

                if (frequency1 != grantResults.length) {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                    alertDialogBuilder.setMessage("you denied some permission, you must give all permission to next proccess?");
                    alertDialogBuilder.setCancelable(false);
                    alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            initPermission();
                        }
                    });
                    alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            finish();
                            SystemUtil.showToast(getApplicationContext(), "Izin tidak di berikan", Toast.LENGTH_LONG, Gravity.TOP);
                        }
                    });
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                } else {
                    initComponent();
                    initContent();
                    initListener();
                }

                /*if (!isPerpermissionForAllGranted) {
                    finish();
                    SystemUtil.showToast(getApplicationContext(), "Izin tidak di berikan", Snackbar.LENGTH_LONG,Gravity.TOP);
                } else {
                    initComponent();
                    initContent();
                    initListener();
                }*/
                break;
        }
    }

    private void initComponent() {
        tvAccIDfvbi = (TextView) findViewById(R.id.tvAccID);
        ibAccClosefvbi = (ImageButton) findViewById(R.id.ibAccClose);

        tvAccSelPicfvbi = (TextView) findViewById(R.id.tvAccSelPic);
        ibAccCamerafvbi = (ImageButton) findViewById(R.id.ibAccCamera);

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

        ivWrapPicfvbi = (ImageView) findViewById(R.id.ivWrapPic);
        llWrapPicfvbi = (LinearLayout) findViewById(R.id.llWrapPic);

        mySpinner = (Spinner) findViewById(R.id.spinGender);
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

        if (db.isDataTableValueNull(SQLiteHelper.TableUserData, SQLiteHelper.KEY_UserData_accountId, accountId)) {
            SystemUtil.showToast(getApplicationContext(), "data kosong", Toast.LENGTH_SHORT, Gravity.TOP);
        } else {
            updateData(db.getAllUserData(accountId));
        }

        myCalendar = Calendar.getInstance();

        mAuth = FirebaseAuth.getInstance();

        customText(tvAccAggrementfvbi);
        tvSignOutSkipfvbi.setPaintFlags(tvSignOutSkipfvbi.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
    }

    private void initListener() {
        ibAccCamerafvbi.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                llWrapPicfvbi.setVisibility(View.GONE);
                getImageCamera();

            }
        });

        llAccGenderfvbi.setOnClickListener(this);

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
                new DatePickerDialog(AccountActivity.this,
                        date,
                        myCalendar.get(Calendar.YEAR),
                        myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH))
                        .show();
            }

        });

        llAccSavefvbi.setOnClickListener(this);
        tvSignOutSkipfvbi.setOnClickListener(this);
        ibAccClosefvbi.setOnClickListener(this);
    }

    private void getImageCamera() {
        if (!TextUtils.isEmpty(imageFilePath)) {
            File file = new File(imageFilePath);
            if (file.exists()) {
                file.delete();
            }
        }
        if (!TextUtils.isEmpty(imageFilePath2)) {
            File file = new File(imageFilePath2);
            if (file.exists()) {
                file.delete();
            }
        }

        String date = DateTimeUtil.dateToString(DateTimeUtil.currentDate(), new java.text.SimpleDateFormat("ddMMyy_HHmmss"));
        imageFilePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/picture_" + date + ".jpg";

        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT) {
            Uri imageFileUri = Uri.fromFile(new File(imageFilePath));
            Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            cameraIntent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, imageFileUri);
            startActivityForResult(cameraIntent, REQ_COD_CAMERA_KITKAT);
        } /*else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            imageFilePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/picture.jpg";
            mUriNougat = FileProvider.getUriForFile(getApplicationContext(), CAPTURE_IMAGE_FILE_PROVIDER, new File(imageFilePath));
            //mUriNougat = FileProvider.getUriForFile(getApplicationContext(), BuildConfig.APPLICATION_ID + ".provider", new File(imageFilePath));
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            cameraIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, mUriNougat);
            startActivityForResult(cameraIntent, REQ_COD_CAMERA_NOUGAT);
        } */ else {
            /*ContentValues values = new ContentValues();
            values.put(MediaStore.Images.Media.TITLE, "New Picture");
            values.put(MediaStore.Images.Media.DESCRIPTION, "From your Camera");
            imageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            startActivityForResult(intent, REQ_CODE_TAKE_PHOTO_INTENT_ID_STANDART);*/

            Uri imageUri = FileProvider.getUriForFile(getApplicationContext(), "com.gandsoft.openguide", new File(imageFilePath));
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            cameraIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            startActivityForResult(cameraIntent, REQ_CODE_TAKE_PHOTO_INTENT_ID_STANDART);
        }
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "APPNAME-" + timeStamp + ".png";
        File mediaStorageDir = new File(Environment.getExternalStorageDirectory(), "FOLDERNAME");
        File storageDir = new File(mediaStorageDir + "/Images");
        if (!storageDir.exists()) {
            storageDir.mkdirs();
        }
        File image = new File(storageDir, imageFileName);
        return image;
    }

    private void updateData(ArrayList<GetListUserEventResponseModel> models) {
        for (int i = 0; i < models.size(); i++) {
            GetListUserEventResponseModel model = models.get(i);
            etAccNamefvbi.setText(model.getFull_name());
            etAccEmailfvbi.setText(model.getEmail());

            ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_item, getResources().getStringArray(R.array.names));
            myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            mySpinner.setAdapter(myAdapter);
            if (model.getGender() != null) {
                int spinnerPosition = myAdapter.getPosition(model.getGender());
                mySpinner.setSelection(spinnerPosition);
            }
            String[] q = model.getBirthday().split("-");
            tvAccTahunfvbi.setText(q[0]);
            tvAccBulanfvbi.setText(q[1]);
            tvAccTglfvbi.setText(q[2]);
        }
    }

    private void updateUI(int ui) {
        tvAccIDfvbi.setText(accountId);
        if (ui == UI_NEW_USER) {
            llAccAggrementfvbi.setVisibility(View.VISIBLE);
            ibAccClosefvbi.setVisibility(View.GONE);
            tvSignOutSkipfvbi.setText("SKIP THIS STEP");
        } else if (ui == UI_OLD_USER) {
            llAccAggrementfvbi.setVisibility(View.GONE);
            ibAccClosefvbi.setVisibility(View.VISIBLE);
            tvSignOutSkipfvbi.setText("SIGN-OUT");
        }
    }

    private void signOut(boolean isDialog) {
        if (isDialog) {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(AccountActivity.this);
            alertDialogBuilder.setMessage("ARE YOU SURE WANNA SIGN-OUT?");
            alertDialogBuilder.setCancelable(false);
            alertDialogBuilder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface arg0, int arg1) {
                    AppUtil.signOutFull(AccountActivity.this, db, false, accountId);//signout
                    ServiceUtil.stopSevice(AccountActivity.this, RepeatCheckDataService.class);
                    NotifUtil.clearNotificationAll(getApplicationContext());
                }
            });
            alertDialogBuilder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface arg0, int arg1) {

                }
            });
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        } else {
            AppUtil.signOutFull(AccountActivity.this, db, false, accountId);//signout
            ServiceUtil.stopSevice(AccountActivity.this, RepeatCheckDataService.class);
            NotifUtil.clearNotificationAll(getApplicationContext());
        }

    }

    private void quitNotSave() {
        // Munculkan alert dialog apabila user ingin keluar aplikasi
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(AccountActivity.this);
        alertDialogBuilder.setMessage("Discard All of Changes?");
        alertDialogBuilder.setCancelable(true);
        alertDialogBuilder.setPositiveButton("YES",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        SystemUtil.showToast(getApplicationContext(), "Data tak tersimpan", Toast.LENGTH_SHORT, Gravity.TOP);
                        returnToBackActivity();
                        //clearImage();
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

    private void returnToBackActivity() {
        Intent intent = new Intent();
        setResult(Activity.RESULT_OK, intent);
        finish();
    }

    private void skipLogoutClick() {
        if (isNewUser) {
            returnToBackActivity();
        } else {
            signOut(true);
        }
    }

    private void saveClick() {
        if (isNewUser) {
            if (cbAccAggrementfvbi.isChecked()) {
                updateData();//click new user
            } else {
                SystemUtil.showToast(getApplicationContext(), "Checked Egreement First!!", Toast.LENGTH_SHORT, Gravity.TOP);
                cbAccAggrementfvbi.requestFocus();
            }
        } else {
            updateData();//click old user
        }
    }

    private void updateData() {
        if (NetworkUtil.isConnected(getApplicationContext())) {
            UserUpdateRequestModel requestModel = new UserUpdateRequestModel();
            requestModel.setAccounsId(accountId);
            String base64pic = PictureUtil.bitmapToBase64(bitmapCrop, Bitmap.CompressFormat.JPEG, 100);
            if (base64pic.isEmpty()) {
                requestModel.setFileImageB641("");
            } else {
                requestModel.setFileImageB641(base64pic);
            }
            requestModel.setDbver("3");
            requestModel.setDegree_image("ANDROID");
            requestModel.setPrivacypolicy(true);
            requestModel.setName(etAccNamefvbi.getText().toString());
            requestModel.setEmail(etAccEmailfvbi.getText().toString());
            requestModel.setGender(String.valueOf(mySpinner.getSelectedItem()));
            requestModel.setDate(tvAccTglfvbi.getText().toString());
            requestModel.setMonth(tvAccBulanfvbi.getText().toString());
            requestModel.setYear(tvAccTahunfvbi.getText().toString());

            ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setCancelable(false);
            progressDialog.setMessage("Please Wait...");
            progressDialog.setTitle("Upload data baru..");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            // show it
            progressDialog.show();

            API.doUserUpdateRet(requestModel).enqueue(new Callback<List<UserUpdateResponseModel>>() {
                @Override
                public void onResponse(Call<List<UserUpdateResponseModel>> call, Response<List<UserUpdateResponseModel>> response) {
                    progressDialog.dismiss();
                    if (response.isSuccessful()) {
                        List<UserUpdateResponseModel> s = response.body();
                        if (s.size() == 1) {
                            for (int i = 0; i < s.size(); i++) {
                                UserUpdateResponseModel model = s.get(i);
                                db.updateOneKey(SQLiteHelper.TableUserData, SQLiteHelper.KEY_UserData_accountId, accountId, SQLiteHelper.KEY_UserData_imageUrl, model.getImage_url());
                                if (model.getStatus().equalsIgnoreCase("ok")) {
                                    SystemUtil.showToast(getApplicationContext(), "Tersimpan", Toast.LENGTH_SHORT, Gravity.TOP);
                                    returnToBackActivity();

                                    if (NetworkUtil.isConnected(getApplicationContext())) {
                                        String imageCachePath = PictureUtil.saveImageLogoBackIcon(getApplicationContext(), bitmapCrop, "user_image" + accountId);
                                        db.saveUserPicture(imageCachePath, accountId);
                                    }

                                    //clearImage();

                                } else {
                                    SystemUtil.showToast(getApplicationContext(), "Bad Response", Toast.LENGTH_SHORT, Gravity.TOP);
                                }
                            }
                        } else {
                            SystemUtil.showToast(getApplicationContext(), "Data Tidak Sesuai", Toast.LENGTH_SHORT, Gravity.TOP);
                        }
                    } else {
                        SystemUtil.showToast(getApplicationContext(), response.message(), Toast.LENGTH_SHORT, Gravity.TOP);
                    }
                }

                @Override
                public void onFailure(Call<List<UserUpdateResponseModel>> call, Throwable t) {
                    Log.e("Lihat", "onFailure AccountActivity : " + t.getMessage());
                    Snackbar.make(findViewById(android.R.id.content), "Failed to connect server", Snackbar.LENGTH_LONG).setAction("Reload", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            updateData();//onfailure
                        }
                    }).show();
                }
            });
        } else {
            SystemUtil.showToast(getApplicationContext(), "Check you connection", Toast.LENGTH_SHORT, Gravity.TOP);
        }

    }

    private void updateLabel() {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, ULocale.US);
        String a[] = sdf.format(myCalendar.getTime()).split("-");

        tvAccTglfvbi.setText(a[2]);
        tvAccBulanfvbi.setText(a[1]);
        tvAccTahunfvbi.setText(a[0]);
//        etAccBirthdayfvbi.setText(sdf.format(myCalendar.getTime()));
    }

    private void customText(TextView view) {
        SpannableStringBuilder spanTxt = new SpannableStringBuilder("I agree to the ");
        spanTxt.append("PRIVACY POLICY");
        spanTxt.setSpan(new ClickableSpan() {
                            @Override
                            public void onClick(View widget) {
                                SystemUtil.showToast(getApplicationContext(), "PRIVACY POLICY Clicked", Toast.LENGTH_SHORT, Gravity.TOP);
                            }
                        },
                spanTxt.length() - "PRIVACY POLICY".length(),
                spanTxt.length(),
                0);
        view.setMovementMethod(LinkMovementMethod.getInstance());
        view.setText(spanTxt, TextView.BufferType.SPANNABLE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == REQ_CODE_TAKE_PHOTO_INTENT_ID_STANDART) {
            /*Uri uri = Uri.fromFile(new File(imageFilePath));

            beginCrop(uri);*/

            Uri imageUri = Uri.fromFile(new File(imageFilePath));
            //imageFilePath = PictureUtil.getImagePathFromUri(this, imageUri);//above kitkat
            Glide.with(getApplicationContext())
                    .load(imageUri)
                    //.load(new File(imageFilePath))
                    .asBitmap()
                    .error(R.drawable.template_account_og)
                    .placeholder(R.drawable.ic_action_name)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(false)
                    .dontAnimate()
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                            Uri uri = PictureUtil.getImageUriFromBitmap(getApplicationContext(), resource);
                            imageFilePath2 = PictureUtil.getImagePathFromUri(AccountActivity.this, uri);
                            beginCrop(uri);
                        }
                    });

        } else if (resultCode == Activity.RESULT_OK && requestCode == REQ_COD_CAMERA_KITKAT) {
            BitmapFactory.Options bmpFactoryOptions = new BitmapFactory.Options();
            bmpFactoryOptions.inJustDecodeBounds = false;
            Bitmap resource = BitmapFactory.decodeFile(imageFilePath, bmpFactoryOptions);//kitkat
            Uri uri = PictureUtil.getImageUriFromBitmap(getApplicationContext(), resource);

            beginCrop(uri);
        } else if (requestCode == Crop.REQUEST_CROP) {
            handleCrop(resultCode, data);
        }
    }

    public void beginCrop(Uri sourceUri) {
        /*ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "New Picture");
        values.put(MediaStore.Images.Media.DESCRIPTION, "From your Camera");
        Uri outputUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);*/

        Uri destinationUri = Uri.fromFile(new File(getCacheDir(), "cropped"));
        Crop.of(sourceUri, destinationUri).asSquare().start(this);
    }

    public void handleCrop(int resultCode, Intent result) {
        if (resultCode == RESULT_OK) {
            llWrapPicfvbi.setVisibility(View.VISIBLE);
            uriCrop = Crop.getOutput(result);

            Glide.with(AccountActivity.this)
                    .load(uriCrop)
                    .asBitmap()
                    .placeholder(R.drawable.template_account_og)
                    .skipMemoryCache(true)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .error(R.drawable.template_account_og)
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                            ivWrapPicfvbi.setImageBitmap(resource);
                            bitmapCrop = resource;
                        }
                    });

        } else if (resultCode == Crop.RESULT_ERROR) {
            SystemUtil.showToast(this, Crop.getError(result).getMessage(), Toast.LENGTH_SHORT, Gravity.TOP);
        }
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
            returnToBackActivity();
        }
    }

    @Override
    public void onBackPressed() {
        quitNotSave();
    }
}
