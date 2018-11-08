package com.gandsoft.openguide.view.infomenu.gallery2;

import android.app.Activity;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.gandsoft.openguide.support.InputValidUtil;
import com.gandsoft.openguide.support.PictureUtil;
import com.github.chrisbanes.photoview.PhotoView;

import java.io.File;
import java.util.ArrayList;

import static com.gandsoft.openguide.App.getContext;

public class GalleryDetailPagerAdapter extends PagerAdapter {
    ArrayList<String> _imagePaths;
    private Activity _activity;
    private ViewPager _mViewPager;
    private LayoutInflater inflater;

    GalleryDetailPagerAdapter(Activity activity, ArrayList<String> _imagePaths, ViewPager mViewPager) {
        this._activity = activity;
        this._imagePaths = _imagePaths;
        this._mViewPager = mViewPager;
    }

    @Override
    public int getCount() {
        return this._imagePaths.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public View instantiateItem(ViewGroup container, int position) {

        /*inflater = (LayoutInflater) _activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View viewLayout = inflater.inflate(R.layout.layout_fullscreen_image, container, false);*/

        PhotoView photoView = new PhotoView(container.getContext());
//        TouchImageView photoView = new TouchImageView(container.getContext());
        Glide.with(getContext())
                .load(InputValidUtil.isLinkUrl(_imagePaths.get(position)) ? _imagePaths.get(position) : new File(_imagePaths.get(position)))
                .asBitmap()
                .fitCenter()
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        photoView.setImageBitmap(PictureUtil.resizeImageBitmap(resource, 1080));
                    }
                });
        container.addView(photoView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

        return photoView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
