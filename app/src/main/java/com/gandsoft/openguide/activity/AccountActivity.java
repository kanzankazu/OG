package com.gandsoft.openguide.activity;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.icu.util.ULocale;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Base64;
import android.util.Log;
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

import com.gandsoft.openguide.API.API;
import com.gandsoft.openguide.API.APIrequest.UserUpdate.UserUpdateRequestModel;
import com.gandsoft.openguide.API.APIresponse.Login.LoginResponseModel;
import com.gandsoft.openguide.API.APIresponse.UserData.UserDataResponseModel;
import com.gandsoft.openguide.API.APIresponse.UserUpdate.UserUpdateResponseModel;
import com.gandsoft.openguide.ISeasonConfig;
import com.gandsoft.openguide.R;
import com.gandsoft.openguide.database.SQLiteHelper;
import com.gandsoft.openguide.support.SessionUtil;
import com.google.android.gms.games.request.Requests;
import com.google.android.gms.nearby.connection.Payload;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;
import java.io.*;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@RequiresApi(api = Build.VERSION_CODES.N)
public class AccountActivity extends LocalBaseActivity implements View.OnClickListener {
    SQLiteHelper db = new SQLiteHelper(this);

    private static final int UI_NEW_USER = 0;
    private static final int UI_OLD_USER = 1;
    private FirebaseAuth mAuth;

    private TextView tvAccIDfvbi, tvAccSelPicfvbi,tvAccGenderfvbi, tvAccTglfvbi, tvAccBulanfvbi, tvAccTahunfvbi, tvAccAggrementfvbi, tvSignOutSkipfvbi;
    private EditText etAccNamefvbi, etAccEmailfvbi;
    private LinearLayout llAccPicfvbi, llAccGenderfvbi, llAccBirthdatefvbi, llAccAggrementfvbi, llAccSavefvbi;
    private CheckBox cbAccAggrementfvbi;
    private ImageButton ibAccClosefvbi, ibAccCamerafvbi, ibCalendarfvbi;
    private ImageView ivWrapPicfvbi;

    private boolean isNewUser = false;
    private String accountId;
    private String base64pic="";

    private Spinner mySpinner;

    Calendar myCalendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        if (SessionUtil.checkIfExist(ISeasonConfig.KEY_ACCOUNT_ID)) {
            accountId = SessionUtil.getStringPreferences(ISeasonConfig.KEY_ACCOUNT_ID, null);
            Log.d("Lihat", "onCreate AccountActivity : " + accountId);
        }
        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        }
        if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
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

//        tvAccGenderfvbi = (TextView) findViewById(R.id.tvAccGender);
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
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                File f = new File(android.os.Environment
                        .getExternalStorageDirectory(), "temp.jpg");
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                startActivityForResult(intent, 1);
            }
        });
        tvAccSelPicfvbi.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 2);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                // onCaptureImageResult(data);
                try {

                    File f = new File(Environment.getExternalStorageDirectory()
                            .toString());
                    for (File temp : f.listFiles()) {
                        if (temp.getName().equals("temp.jpg")) {
                            f = temp;
                            break;
                        }
                    }
                    Bitmap bitmap;
                    BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();

                    bitmap = BitmapFactory.decodeFile(f.getAbsolutePath(),
                            bitmapOptions);
                    ivWrapPicfvbi.setImageBitmap(bitmap);
                    ivWrapPicfvbi.setVisibility(View.VISIBLE);

                    String path = android.os.Environment
                            .getExternalStorageDirectory()
                            + File.separator
                            + ".Gandsoft" + File.separator + "images";
                    f.delete();
                    OutputStream outFile = null;
                    File file = new File(path, String.valueOf(System
                            .currentTimeMillis()) + ".jpg");
                    try {
                        outFile = new FileOutputStream(file);
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 85, outFile);
                        outFile.flush();
                        outFile.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (requestCode == 2) {
                onSelectFromGalleryResult(data);
            }
        }
    }

    private void onSelectFromGalleryResult(Intent data) {
        Uri selectedImageUri = data.getData();
        String[] projection = { MediaStore.MediaColumns.DATA };
        @SuppressWarnings("deprecation")
        Cursor cursor = managedQuery(selectedImageUri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        cursor.moveToFirst();

        String selectedImagePath = cursor.getString(column_index);

        Bitmap bm;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(selectedImagePath, options);
        final int REQUIRED_SIZE = 200;
        int scale = 1;
        while (options.outWidth / scale / 2 >= REQUIRED_SIZE
                && options.outHeight / scale / 2 >= REQUIRED_SIZE)
            scale *= 2;
        options.inSampleSize = scale;
        options.inJustDecodeBounds = false;
        bm = BitmapFactory.decodeFile(selectedImagePath, options);

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream .toByteArray();

        base64pic = Base64.encodeToString(byteArray, Base64.DEFAULT);

        ivWrapPicfvbi.setImageBitmap(bm);
        ivWrapPicfvbi.setVisibility(View.VISIBLE);
        tvAccSelPicfvbi.setText("Image selected");
    }

    private void updateData(ArrayList<UserDataResponseModel> models) {
        tvAccIDfvbi.setText(accountId);
        for (int i = 0; i < models.size(); i++) {
            UserDataResponseModel model = models.get(i);
            etAccNamefvbi.setText(model.getFull_name());
            etAccEmailfvbi.setText(model.getEmail());
//            tvAccGenderfvbi.setText(model.getGender());

            ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(AccountActivity.this,R.layout.spinner_item,getResources().getStringArray(R.array.names));
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
    int MY_CAMERA_REQUEST_CODE = 77777;

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

    private void quitNotSave() {
        // Munculkan alert dialog apabila user ingin keluar aplikasi
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Discard All of Changes?");
        alertDialogBuilder.setCancelable(true);
        alertDialogBuilder.setPositiveButton("YES",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        Snackbar.make(findViewById(android.R.id.content), "Data tak tersimpan", Snackbar.LENGTH_SHORT).show();
                        moveToChangeEvent();
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
            moveToChangeEvent();
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
                updateData();
            } else {
                Snackbar.make(findViewById(android.R.id.content), "Checked Egreement First!!", Snackbar.LENGTH_LONG).show();
                cbAccAggrementfvbi.requestFocus();
            }
        } else {
            updateData();
        }
    }

    private void updateData(){
        UserUpdateRequestModel requestModel = new UserUpdateRequestModel();
        requestModel.setAccounsId(accountId);
        if(base64pic.isEmpty()) {
            requestModel.setFileImageB641("");
        }
        else{
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
                if(response.isSuccessful()) {
                    List<UserUpdateResponseModel> s = response.body();
                    if (s.size() == 1) {
                        for (int i = 0; i < s.size(); i++) {
                            UserUpdateResponseModel model = s.get(i);
                            db.updateOneKey(SQLiteHelper.TableUserData,SQLiteHelper.KEY_UserData_accountId,accountId,SQLiteHelper.KEY_UserData_imageUrl,model.getImage_url());
                            if (model.getStatus().equalsIgnoreCase("ok")) {
                                Snackbar.make(findViewById(android.R.id.content), "Tersimpan", Snackbar.LENGTH_LONG).show();
                                moveToChangeEvent();
                            } else {
                                Snackbar.make(findViewById(android.R.id.content), "Bad Response", Snackbar.LENGTH_LONG).show();
                            }
                        }
                    } else {
                        Snackbar.make(findViewById(android.R.id.content), "Data Tidak Sesuai", Snackbar.LENGTH_LONG).show();
                    }
                    Log.d("brasil",response.message());
                    moveToChangeEvent();
                }
                else {
                    Log.d("gagal",response.message());
                }
            }

            @Override
            public void onFailure(Call<List<UserUpdateResponseModel>> call, Throwable t) {
                Log.d("gagal failur",t.getMessage());
            }
        });
    }
    public static Intent getActIntent(Activity activity) {
        return new Intent(activity, AccountActivity.class);
    }

    @Override
    public void onBackPressed() {
        quitNotSave();
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

/*    private void selectImage() {
        final CharSequence[] options = { "Take Photo", "Gallery" };

        AlertDialog.Builder builder = new AlertDialog.Builder(
                EditProfileActivity.this);
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo")) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    File f = new File(android.os.Environment
                            .getExternalStorageDirectory(), "temp.jpg");
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                    startActivityForResult(intent, 1);
                } else if (options[item].equals("Gallery")) {
                    Intent intent = new Intent(
                            Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, 2);

                }
            }
        });
        builder.show();
    }*/

}
