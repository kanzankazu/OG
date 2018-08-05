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

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class SampleAdapter extends FragmentPagerAdapter {
    int a = 0;
    Context ctxt = null;

    public SampleAdapter(Context ctxt, FragmentManager mgr, int a) {
        super(mgr);
        this.ctxt = ctxt;
        this.a = a;
    }

    @Override
    public Fragment getItem(int position) {
        String[] headervalues = new String[]{
                "11 Jul", "12 Jul", "13 Jul", "14 Jul", "15 Jul",
        };
        if (a == 0) {
            for (int i = 0; i < headervalues.length; i++) {
                if (position == i) {
                    return new cScheduleXFragment();
                }
            }
        } else {
            for (int i = 0; i < headervalues.length; i++) {
                if (position == i) {
                    return new cScheduleXMiniFragment();
                }
            }

        }
        return null;
    }

    @Override
    public int getCount() {
        return 5;
    }
}