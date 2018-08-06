/***
 Copyright (c) 2012 CommonsWare, LLC
 Licensed under the Apache License, Version 2.0 (the "License"); you may not
 use this file except in compliance with the License. You may obtain a copy
 of the License at http://www.apache.org/licenses/LICENSE-2.0. Unless required
 by applicable law or agreed to in writing, software distributed under the
 License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS
 OF ANY KIND, either express or implied. See the License for the specific
 language governing permissions and limitations under the License.

 Covered in detail in the book _The Busy Coder's Guide to Android Development_
 https://commonsware.com/Android
 */

package com.gandsoft.openguide.activity.main.fragments;

import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gandsoft.openguide.R;
import com.gandsoft.openguide.support.SystemUtil;

public class SampleAdapter extends PagerAdapter {

    private final FragmentActivity activity;
    private final int pager;
    private String[] dates;

    public SampleAdapter(FragmentActivity activity, int pager, String[] dates) {

        this.activity = activity;
        this.pager = pager;
        this.dates = dates;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = null;
        if (pager == 0) {
            LayoutInflater layoutInflater = (LayoutInflater) activity.getSystemService(activity.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.fragment_cx_fact, container, false);
            TextView tvScheduleFactDayfvbi = (TextView) view.findViewById(R.id.tvScheduleFactDay);
            TextView tvScheduleFactDatefvbi = (TextView) view.findViewById(R.id.tvScheduleFactDate);
            TextView tvScheduleFactMonthYearfvbi = (TextView) view.findViewById(R.id.tvScheduleFactMonthYear);
            tvScheduleFactDayfvbi.setText(SystemUtil.getDayFromDateString(dates[position], "dd MMM yyyy"));
            tvScheduleFactDatefvbi.setText(dates[position].substring(0, 2));
            tvScheduleFactMonthYearfvbi.setText(dates[position].substring(3));
            container.addView(view);
        } else if (pager == 1) {
            LayoutInflater layoutInflater = (LayoutInflater) activity.getSystemService(activity.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.fragment_cx_fact_mini, container, false);
            TextView tvScheduleFactMiniDatefvbi = (TextView) view.findViewById(R.id.tvScheduleFactMiniDate);
            tvScheduleFactMiniDatefvbi.setText(dates[position]);
            container.addView(view);
        }
        return view;
    }

    @Override
    public int getCount() {
        return dates.length;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        View view = (View) object;
        container.removeView(view);
    }
}