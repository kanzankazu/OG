package com.gandsoft.openguide.view.infomenu.gallery2;

import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.gandsoft.openguide.support.PictureUtil;

import java.util.ArrayList;

import static com.gandsoft.openguide.App.getContext;

public class GalleryDetailPagerAdapter extends PagerAdapter {
    ArrayList<String> imageList;
    private ImageViewTouchViewPager mViewPager;

    GalleryDetailPagerAdapter(ArrayList<String> imageList, ImageViewTouchViewPager mViewPager) {
        this.imageList = imageList;
        this.mViewPager = mViewPager;
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

        /*TouchImageView img = new TouchImageView(container.getContext());
        Glide.with(getContext())
                .load(imageList.get(position))
                .asBitmap()
                .fitCenter()
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        img.setImageBitmap(PictureUtil.resizeImageBitmap(resource, 720));
                    }
                });
        container.addView(img, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        return img;*/

        /*ImageViewTouch img = new ImageViewTouch(getContext(), null);
        img.setDisplayType(ImageViewTouchBase.DisplayType.FIT_TO_SCREEN);
        Glide.with(getContext())
                .load(imageList.get(position))
                .asBitmap()
                .thumbnail(0.1f)
                .fitCenter()
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        img.setImageBitmap(PictureUtil.resizeImageBitmap(resource,720));
                    }
                });
        container.addView(img, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        return img;*/

        ScaleImageView photoView = new ScaleImageView(container.getContext());
        Glide.with(getContext())
                .load(imageList.get(position))
                .asBitmap()
                .fitCenter()
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        photoView.setImageBitmap(PictureUtil.resizeImageBitmap(resource,720));
                    }
                });
        container.addView(photoView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        return photoView;
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
                data.get(position).getImage_icon_local());
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
