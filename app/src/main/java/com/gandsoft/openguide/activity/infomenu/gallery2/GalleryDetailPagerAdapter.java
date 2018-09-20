package com.gandsoft.openguide.activity.infomenu.gallery2;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import static com.gandsoft.openguide.App.getContext;

public class GalleryDetailPagerAdapter extends PagerAdapter {
    ArrayList<String> imageList;

    GalleryDetailPagerAdapter(ArrayList<String> imageList) {
        this.imageList = imageList;
    }

    @Override
    public int getCount() {
        return (null != imageList) ? imageList.size() : 0;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public View instantiateItem(ViewGroup container, int position) {

        TouchImageView img = new TouchImageView(container.getContext());
        Glide.with(getContext())
                .load(imageList.get(position))
                .asBitmap()
                .fitCenter()
                .into(img);
        /*img.setImageResource(imageList.get(position));*/
        container.addView(img, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        return img;

        /*ScaleImageView photoView = new ScaleImageView(container.getContext());
        Glide.with(getContext())
                .load(imageList.get(position))
                .asBitmap()
                .fitCenter()
                .into(photoView);
        container.addView(photoView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        return photoView;*/
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    /*public ArrayList<GalleryImageModel> data = new ArrayList<>();

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
    }*/
}
