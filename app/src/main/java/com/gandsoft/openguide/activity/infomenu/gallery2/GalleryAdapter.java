package com.gandsoft.openguide.activity.infomenu.gallery2;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.Request;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.gandsoft.openguide.API.APIresponse.Gallery.GalleryResponseModel;
import com.gandsoft.openguide.R;
import com.gandsoft.openguide.support.PictureUtil;
import com.gandsoft.openguide.support.SystemUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.ViewHolder> {

    private final Activity activity;
    private final String eventId;
    private List<GalleryResponseModel> models = new ArrayList<>();

    public GalleryAdapter(Activity activity, List<GalleryResponseModel> models, String eventId) {
        this.activity = activity;
        this.models = models;
        this.eventId = eventId;
    }

    @NonNull
    @Override
    public GalleryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_list_gallery_grid, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull GalleryAdapter.ViewHolder holder, int position) {
        GalleryResponseModel model = models.get(position);
        Glide.with(activity)
                .load(model.getImage_posted())
                .asBitmap()
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .centerCrop()
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        Bitmap resizeImage = PictureUtil.resizeImage(resource, 500);
                        holder.mImg.setImageBitmap(resizeImage);
                    }
                });
        Glide.with(activity)
                .load(model.getImage_posted())
                .asBitmap()
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation glideAnimation) {
                        saveImage(resource, position, model.getId());
                    }
                });
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView mImg;

        public ViewHolder(View itemView) {
            super(itemView);
            mImg = (ImageView) itemView.findViewById(R.id.item_img);
        }
    }

    public void setData(List<GalleryResponseModel> datas) {
        if (datas.size() > 0) {
            models.clear();
            models = datas;
        } else {
            models = datas;
        }
        notifyDataSetChanged();
    }

    public void addDatas(List<GalleryResponseModel> datas) {
        models.addAll(datas);
        notifyItemRangeInserted(models.size(), datas.size());
    }

    private String saveImage(Bitmap image, int position, String id) {
        /*Random r = new Random();
        int i1 = r.nextInt(1000);*/
        String savedImagePath = null;
        String imageFileName = id + ".jpg";
        File storageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                + "/.Gandsoft/" + eventId);
        boolean success = true;
        if (!storageDir.exists()) {
            success = storageDir.mkdirs();
        }
        if (success) {
            File imageFile = new File(storageDir, imageFileName);
            savedImagePath = imageFile.getAbsolutePath();
            try {
                OutputStream fOut = new FileOutputStream(imageFile);
                Bitmap storedata = PictureUtil.resizeImage(image, 500);
                storedata.compress(Bitmap.CompressFormat.JPEG, 50, fOut);
                fOut.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Log.d("Lihat", "saveImage GalleryAdapter : " + savedImagePath);
        return savedImagePath;
    }
}
