package com.gandsoft.openguide.support;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Paint;
import android.os.Handler;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.InputFilter;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.gandsoft.openguide.App;
import com.gandsoft.openguide.R;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.common.AccountPicker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SystemUtil {

    private static final int APP_VERSION_NAME = 0;
    private static final int APP_VERSION_CODE = 1;
    private static final int APP_NAME = 2;
    private static final int APP_PACKAGE_NAME = 3;

    private static List<Intent> addIntentsToList(Context context, List<Intent> list, Intent intent) {
        List<ResolveInfo> resInfo = context.getPackageManager().queryIntentActivities(intent, 0);
        for (ResolveInfo resolveInfo : resInfo) {
            String packageName = resolveInfo.activityInfo.packageName;
            Intent targetedIntent = new Intent(intent);
            targetedIntent.setPackage(packageName);
            list.add(targetedIntent);
        }
        return list;
    }

    public static void textCaps(EditText editText) {
        editText.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
    }

    public static void hideKeyBoard(Activity activity) {
        InputMethodManager inputManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        View focusedView = activity.getCurrentFocus();
        /*
         * If number view is focused, an NPE will be thrown
         *
         * Maxim Dmitriev
         */
        if (focusedView != null) {
            if (inputManager != null) {
                inputManager.hideSoftInputFromWindow(focusedView.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }

    public static void showKeyBoard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null) {
            inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
        }
    }

    public static String stringToStars(String s) {
        StringBuilder sb = new StringBuilder(s.length());
        for (int i = 0; i < s.length(); i++) {
            sb.append('*');
        }
        return sb.toString();
    }

    public static Integer stringCount(String s) {
        return s.length();
    }

    public static String stringSpaceToPlus(String s) {
        return s.replace(" ", "+");
    }

    public static void visibile(Context context, View view, int visible, int anim) {
        if (view.getVisibility() != visible) {
            view.setVisibility(visible);
            Animation animation = AnimationUtils.loadAnimation(context, anim);
            view.startAnimation(animation);
        }
    }

    public static boolean isVisible(View view) {
        return view.getVisibility() == View.VISIBLE;
    }

    public static int roundDouble(double d) {
        double dAbs = Math.abs(d);
        int i = (int) dAbs;
        double result = dAbs - (double) i;
        if (result < 0.5) {
            return d < 0 ? -i : i;
        } else {
            return d < 0 ? -(i + 1) : i + 1;
        }
    }

    public static void etReqFocus(Activity activity, EditText editText, String errorString) {
        editText.setError(errorString);
        editText.requestFocus();
        SystemUtil.showKeyBoard(activity);
    }

    public static void etReqFocus(Activity activity, EditText editText) {
        editText.requestFocus();
        SystemUtil.showKeyBoard(activity);
    }

    public static void getEmailAccout(Activity activity, int REQUEST_CODE) {
        Intent googlePicker = AccountPicker.newChooseAccountIntent(null, null, new String[]{GoogleAuthUtil.GOOGLE_ACCOUNT_TYPE}, true, null, null, null, null);
        activity.startActivityForResult(googlePicker, REQUEST_CODE);
    }

    public static void changeColText(int androidRed, TextView textView) {
        if (DeviceDetailUtil.isKitkatBelow()) {
            textView.setTextColor(App.getContext().getResources().getColor(androidRed));
        } else {
            textView.setTextColor(ContextCompat.getColor(App.getContext(), androidRed));
        }
    }

    public static String getAppData(int appSearch) {
        if (appSearch == APP_VERSION_NAME) {
            try {
                return App.getContext().getPackageManager().getPackageInfo(App.getContext().getPackageName(), 0).versionName;
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        } else if (appSearch == APP_VERSION_CODE) {
            try {
                return String.valueOf(App.getContext().getPackageManager().getPackageInfo(App.getContext().getPackageName(), 0).versionCode);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        } else if (appSearch == APP_NAME) {
            return App.getContext().getResources().getString(R.string.app_name);
        } else if (appSearch == APP_PACKAGE_NAME) {
            return App.getContext().getPackageCodePath();
        }
        return null;
    }

    public static void setUnderlineTextView(TextView textview) {
        textview.setPaintFlags(textview.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
    }

    public static boolean isMyServiceRunning(Context context, Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    public static String uniqueCharacters(String test) {
        String temp = "";
        for (int i = 0; i < test.length(); i++) {
            char current = test.charAt(i);
            if (temp.indexOf(current) < 0) {
                temp = temp + current;
            } else {
                temp = temp.replace(String.valueOf(current), "");
            }
        }

        return temp;

    }

    public static ProgressDialog showProgress(Context context, @Nullable String message, @Nullable String title, boolean cancelable) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
        if (!TextUtils.isEmpty(message)) {
            progressDialog.setMessage(message);
        }
        if (!TextUtils.isEmpty(title)) {
            progressDialog.setTitle(title);
        }
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
        return progressDialog;
    }

    public static void hideProgress(ProgressDialog progressDialog, @Nullable int delay) {
        new Handler().postDelayed(new Runnable() {
            public void run() {
                //code here
                progressDialog.dismiss();
            }
        }, delay);

    }

    public static boolean isProgressShown(ProgressDialog progressDialog) {
        return progressDialog.isShowing();
    }

    public static void showToast(Context context, String text, int length, @Nullable int gravity) {
        Toast toast = Toast.makeText(context, text, length);
        if (gravity == Gravity.TOP) {
            toast.setGravity(Gravity.TOP, 0, 20);
        } else if (gravity == Gravity.BOTTOM) {
            toast.setGravity(Gravity.BOTTOM, 0, 20);
        } else if (gravity == Gravity.LEFT) {
            toast.setGravity(Gravity.LEFT, 0, 20);
        } else if (gravity == Gravity.RIGHT) {
            toast.setGravity(Gravity.RIGHT, 0, 20);
        }
        toast.show();

    }

    public static Map<String, List<Integer>> findFirstWordsInString(String[] words, String phrase) {
        Map<String, List<Integer>> dest = new HashMap<>();

        for (String word : words) {
            Log.d("Lihat", "findFirstWordsInString SystemUtil : " + word);
            List<Integer> locations = new ArrayList<>();

            for (int loc = -1; (loc = phrase.indexOf(word, loc + 1)) != -1; ) {
                locations.add(loc);
            }

            dest.put(word, locations);
        }

        return dest;
    }

    public static Map<String, List<Integer>> findLastWordsInString(String[] words, String phrase) {
        Map<String, List<Integer>> dest = new HashMap<>();

        for (String word : words) {
            List<Integer> locations = new ArrayList<>();

            for (int loc = -1; (loc = phrase.lastIndexOf(word, loc + 1)) != -1; ) {
                locations.add(loc);
            }

            dest.put(word, locations);
        }

        return dest;
    }

    public static List<String> getTagValues(final String str, String regex) {
        final Pattern TAG_REGEX = Pattern.compile(regex, Pattern.DOTALL);
        final List<String> tagValues = new ArrayList<String>();
        final Matcher matcher = TAG_REGEX.matcher(str);
        while (matcher.find()) {
            tagValues.add(matcher.group(1));
        }
        return tagValues;
    }

    public static List<ContactVO> getAllContacts(Context context) {
        List<ContactVO> contactVOList = new ArrayList();
        ContactVO contactVO;

        ContentResolver contentResolver = context.getContentResolver();
        Cursor cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC");
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {

                int hasPhoneNumber = Integer.parseInt(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)));
                if (hasPhoneNumber > 0) {
                    String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                    String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

                    contactVO = new ContactVO();
                    contactVO.setContactName(name);

                    Cursor phoneCursor = contentResolver.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                            new String[]{id},
                            null);
                    if (phoneCursor.moveToNext()) {
                        String phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        contactVO.setContactNumber(phoneNumber);
                    }

                    phoneCursor.close();

                    Cursor emailCursor = contentResolver.query(
                            ContactsContract.CommonDataKinds.Email.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?",
                            new String[]{id}, null);
                    while (emailCursor.moveToNext()) {
                        String emailId = emailCursor.getString(emailCursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
                    }
                    contactVOList.add(contactVO);
                }
            }
        }
        return contactVOList;
    }

    private static class ContactVO {
        private String ContactImage;
        private String ContactName;
        private String ContactNumber;

        public String getContactImage() {
            return ContactImage;
        }

        public void setContactImage(String contactImage) {
            ContactImage = contactImage;
        }

        public String getContactName() {
            return ContactName;
        }

        public void setContactName(String contactName) {
            ContactName = contactName;
        }

        public String getContactNumber() {
            return ContactNumber;
        }

        public void setContactNumber(String contactNumber) {
            ContactNumber = contactNumber;
        }
    }
}
