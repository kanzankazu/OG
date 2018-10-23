package com.gandsoft.openguide.support;

import android.text.InputFilter;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputValidUtil {

    public static boolean isMatch(String s1, String s2) {
        return s1.equals(s2);
    }

    public static boolean isEmptyField(String s1) {
        return TextUtils.isEmpty(s1);
    }

    public static boolean isEmptyField(EditText editText) {
        String s1 = editText.getText().toString();
        return TextUtils.isEmpty(s1);
    }

    public static boolean isValidateEmail(String s1) {
        return Patterns.EMAIL_ADDRESS.matcher(s1).matches();
    }

    public static boolean isTimebiggerThan(String time1, String time2) {
        String hhmmss1[] = time1.split(":");
        String hhmmss2[] = time2.split(":");
        for (int i = 0; i < hhmmss1.length; i++) {
            if (Integer.parseInt(hhmmss1[i]) > Integer.parseInt(hhmmss2[i])) {
                return true;
            }
        }
        return false;
    }

    public static boolean isTimesmallerThan(String time1, String time2) {
        String hhmmss1[] = time1.split(":");
        String hhmmss2[] = time2.split(":");
        for (int i = 0; i < hhmmss1.length; i++) {
            if (Integer.parseInt(hhmmss1[i]) < Integer.parseInt(hhmmss2[i])) {
                return true;
            }
        }
        return false;
    }

    public static boolean isValidateIP(final String ip) {
        String IPADDRESS_PATTERN =
                "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                        "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                        "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                        "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";
        Pattern pattern = Pattern.compile(IPADDRESS_PATTERN);
        Matcher matcher = pattern.matcher(ip);
        return !matcher.matches();
    }

    public static boolean isValidatePhoneNumber(final String num) {
        String PHONE_NUMBER_PATTERN = "^[+]?[0-9]{10,15}$";
        Pattern pattern = Pattern.compile(PHONE_NUMBER_PATTERN);
        Matcher matcher = pattern.matcher(num);
        return matcher.matches();
    }

    public static boolean isNumeric(String str) {
        if (str == null) {
            return false;
        }
        int length = str.length();
        if (length == 0) {
            return false;
        }
        int i = 0;
        if (str.charAt(0) == '-') {
            if (length == 1) {
                return false;
            }
            i = 1;
        }
        for (; i < length; i++) {
            char c = str.charAt(i);
            if (c < '0' || c > '9') {
                return false;
            }
        }
        return true;
    }

    public static float getPercent(long number, long total) {
        return (float) number / (float) total * 100;
    }

    public static void errorET(EditText editText, CharSequence stringerror) {
        editText.setError(stringerror);
        editText.requestFocus();
    }

    public void setEditTextMaxLength(int length, EditText editText) {
        InputFilter[] filterArray = new InputFilter[1];
        filterArray[0] = new InputFilter.LengthFilter(length);
        editText.setFilters(filterArray);
    }

    public static boolean isLinkUrl(String s) {
        if (s.matches("(?i).*http://.*") || s.matches("(?i).*https://.*")) {
            return true;
        } else {
            return false;
        }
    }
}
