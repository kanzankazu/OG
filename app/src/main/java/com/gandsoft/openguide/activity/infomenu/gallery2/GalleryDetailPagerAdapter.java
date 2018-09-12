package com.gandsoft.openguide.activity.infomenu.gallery2;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class GalleryDetailPagerAdapter extends FragmentPagerAdapter {
    public ArrayList<GalleryImageModel> data = new ArrayList<>();

    public GalleryDetailPagerAdapter(FragmentManager fm, ArrayList<GalleryImageModel> data) {
        super(fm);
        this.data = data;
    }

    @Override
    public Fragment getItem(int position) {

        return GalleryDetailPagerFragment.newInstance(position,
                data.get(position).getId(),
                data.get(position).getLike(),
                data.get(position).getAccount_id(),
                data.get(position).getTotal_comment(),
                data.get(position).getStatus_like(),
                data.get(position).getUsername(),
                data.get(position).getCaption(),
                data.get(position).getImage_posted(),
                data.get(position).getImage_icon(),
                data.get(position).getImage_postedLocal(),
                data.get(position).getImage_iconLocal());
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return data.get(position).getUsername();
    }
}
