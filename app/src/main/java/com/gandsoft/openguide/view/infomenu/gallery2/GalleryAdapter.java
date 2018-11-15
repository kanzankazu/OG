package com.gandsoft.openguide.view.infomenu.gallery2;

import android.app.Activity;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.gandsoft.openguide.API.APIresponse.HomeContent.HomeContentResponseModel;
import com.gandsoft.openguide.R;
import com.gandsoft.openguide.database.SQLiteHelper;
import com.gandsoft.openguide.database.SQLiteHelperMethod;
import com.gandsoft.openguide.support.AppUtil;
import com.gandsoft.openguide.support.InputValidUtil;
import com.gandsoft.openguide.support.NetworkUtil;
import com.gandsoft.openguide.support.PictureUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.ViewHolder> {

    private final Activity activity;
    private final String eventId;
    private String accountId;
    private GalleryAdapterListener galleryAdapterListener;
    private List<HomeContentResponseModel> models = new ArrayList<>();

    public GalleryAdapter(Activity activity, List<HomeContentResponseModel> models, String eventId, String accountId, GalleryAdapterListener galleryAdapterListener) {
        this.activity = activity;
        this.models = models;
        this.eventId = eventId;
        this.accountId = accountId;
        this.galleryAdapterListener = galleryAdapterListener;
    }

    @NonNull
    @Override
    public GalleryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_list_gallery_grid, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull GalleryAdapter.ViewHolder holder, int position) {
        HomeContentResponseModel model = models.get(position);

        String image_icon = AppUtil.validationStringImageIcon(activity, model.getImage_icon(), model.getImage_icon_local(), true);
        String image_posted = AppUtil.validationStringImageIcon(activity, model.getImage_posted(), model.getImage_posted_local(), false);

        Glide.with(activity)
                .load(InputValidUtil.isLinkUrl(image_posted) ? image_posted : new File(image_posted))
                .asBitmap()
                .error(R.drawable.template_account_og)
                .placeholder(R.drawable.ic_action_name)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .dontAnimate()
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        if (InputValidUtil.isLinkUrl(image_posted)) {
                            Bitmap resizeImageBitmap = PictureUtil.resizeImageBitmap(resource, 360);
                            holder.mImg.setImageBitmap(resizeImageBitmap);
                        } else {
                            holder.mImg.setImageBitmap(resource);
                        }

                        if (NetworkUtil.isConnected(activity) && getItemCount() < 18) {
                            String imageCachePath = PictureUtil.saveImageHomeContentImage(activity, resource, model.getId() + "_image", eventId);
                            holder.db.saveGalleryImage(imageCachePath, accountId, eventId, model.getId());
                        }
                    }
                });

        Glide.with(activity)
                .load(InputValidUtil.isLinkUrl(image_icon) ? image_icon : new File(image_icon))
                .asBitmap()
                .error(R.drawable.template_account_og)
                .placeholder(R.drawable.ic_action_name)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .dontAnimate()
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        if (NetworkUtil.isConnected(activity)) {
                            String imageCachePath = PictureUtil.saveImageHomeContentIcon(activity, resource, model.getAccount_id() + "_icon_gallery", eventId);
                            holder.db.saveGalleryIcon(imageCachePath, accountId, eventId, model.getId());
                        }
                    }
                });

        holder.mImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                galleryAdapterListener.onDetailClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public void setData(List<HomeContentResponseModel> datas) {
        if (datas.size() > 0) {
            models.clear();
            models = datas;
        } else {
            models = datas;
        }
        notifyDataSetChanged();
    }

    public void addDatas(List<HomeContentResponseModel> datas) {
        models.addAll(datas);
        notifyItemRangeInserted(models.size(), datas.size());
    }

    public interface GalleryAdapterListener {
        void onDetailClick(int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView mImg;
        SQLiteHelperMethod db = new SQLiteHelperMethod(itemView.getContext());

        public ViewHolder(View itemView) {
            super(itemView);
            mImg = (ImageView) itemView.findViewById(R.id.item_img);
        }
    }
}
