package com.gandsoft.openguide.presenter.widget;

/**
 * Created by glenn on 2/5/18.
 */

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by glenn on 12/14/17.
 */

public class CheckBoxReactiveLinearLayout extends LinearLayout {

    private List<String> childList = new ArrayList<>();

    public CheckBoxReactiveLinearLayout(Context context) {
        super(context);
    }

    public CheckBoxReactiveLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CheckBoxReactiveLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public CheckBoxReactiveLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @NonNull
    private static String removeLast(String str) {
        return str.substring(0, str.length() - 1);
    }

    public void addChildRefID(String refID) {
        childList.add(refID);
    }

    public List<String> getChildRefID(int refID) {
        return childList;
    }

    //use if child is checkboxes
    public String getSelectedCheckbox() {
        String returnString = "";
        for (String id :
                childList) {
            CheckBox beta = (CheckBox) this.findViewWithTag(id);
            if (beta.isChecked()) {
                returnString += beta.getTag().toString() + ",";
            }
        }
        if (returnString != "") {
            returnString = removeLast(returnString);
        }
        return returnString;
    }

    public Integer[] getSelectedCheckboxIndex() {
        List<Integer> returnArray = new ArrayList<>();
        int tot = 0;
        for (String id :
                childList) {
            CheckBox beta = (CheckBox) this.findViewWithTag(id);
            if (beta.isChecked()) {
                returnArray.add(tot);
            }
            tot++;
        }
        return returnArray.toArray(new Integer[returnArray.size()]);
    }

    public List<Integer> getSelectedCheckboxIndexArrayList() {
        List<Integer> returnArray = new ArrayList<>();
        int tot = 0;
        for (String id :
                childList) {
            CheckBox beta = (CheckBox) this.findViewWithTag(id);
            if (beta.isChecked()) {
                returnArray.add(tot);
            }
            tot++;
        }
        return returnArray;
    }

}