package com.gandsoft.openguide.view.infomenu.gallery2;

import android.app.Activity;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.gandsoft.openguide.API.APIresponse.HomeContent.HomeContentResponseModel;
import com.gandsoft.openguide.R;
import com.gandsoft.openguide.database.SQLiteHelper;
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
    private List<HomeContentResponseModel> models = new ArrayList<>();

    public GalleryAdapter(Activity activity, List<HomeContentResponseModel> models, String eventId, String accountId) {
        this.activity = activity;
        this.models = models;
        this.eventId = eventId;
        this.accountId = accountId;
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

        Log.d("Lihat", "onBindViewHolder GalleryAdapter : " + model.getId());
        Log.d("Lihat", "onBindViewHolder GalleryAdapter : " + model.getUsername());
        Log.d("Lihat", "onBindViewHolder GalleryAdapter : " + model.getImage_icon());
        Log.d("Lihat", "onBindViewHolder GalleryAdapter : " + model.getImage_icon_local());

        String image_icon = AppUtil.validationStringImageIcon(activity, model.getImage_icon(), model.getImage_icon_local(), true);
        String image_posted = AppUtil.validationStringImageIcon(activity, model.getImage_posted(), model.getImage_posted_local(), false);
        Glide.with(activity)
                .load(InputValidUtil.isLinkUrl(image_posted) ? image_posted : new File(image_posted))
                .asBitmap()
                .placeholder(R.drawable.template_account_og)
                .error(R.drawable.template_account_og)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        holder.mImg.setImageBitmap(resource);
                        String imageCachePath = PictureUtil.saveImageHomeContentImage(activity, resource, model.getId() + "_image", eventId);
                        holder.db.saveGalleryImage(imageCachePath, accountId, eventId, model.getId());
                    }
                });
        Glide.with(activity)
                .load(InputValidUtil.isLinkUrl(image_icon) ? image_icon : new File(image_icon))
                .asBitmap()
                .placeholder(R.drawable.template_account_og)
                .error(R.drawable.template_account_og)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        if (NetworkUtil.isConnected(activity)) {
                            String imageCachePath = PictureUtil.saveImageHomeContentIcon(activity, resource, model.getAccount_id() + "_icon_gallery", eventId);
                            holder.db.saveGalleryIcon(imageCachePath, accountId, eventId, model.getId());
                        }
                    }
                });
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView mImg;
        SQLiteHelper db = new SQLiteHelper(itemView.getContext());

        public ViewHolder(View itemView) {
            super(itemView);
            mImg = (ImageView) itemView.findViewById(R.id.item_img);
        }
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

}
