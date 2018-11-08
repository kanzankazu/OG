package com.gandsoft.openguide.view;

import android.app.Activity;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.gandsoft.openguide.API.APIresponse.UserData.UserListEventResponseModel;
import com.gandsoft.openguide.R;
import com.gandsoft.openguide.database.SQLiteHelper;
import com.gandsoft.openguide.support.AppUtil;
import com.gandsoft.openguide.support.InputValidUtil;
import com.gandsoft.openguide.support.NetworkUtil;
import com.gandsoft.openguide.support.PictureUtil;
import com.github.siyamed.shapeimageview.RoundedImageView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ChangeEventPastAdapter extends RecyclerView.Adapter<ChangeEventPastAdapter.ViewHolder> {

    private final String accountid;
    private ChangeEventPastHook parent;
    private List<UserListEventResponseModel> models = new ArrayList<>();
    private boolean isPreferUrl;

    public ChangeEventPastAdapter(Activity parent, List<UserListEventResponseModel> items, String accountid) {
        this.models = items;
        this.accountid = accountid;
        try {
            this.parent = (ChangeEventPastHook) parent;
        } catch (Exception e) {
            this.parent = null;
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_list_change_event, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        UserListEventResponseModel model = models.get(position);
        if (model.getStatus().equalsIgnoreCase("PAST EVENT")) {

            String imageUrlPathBack = AppUtil.validationStringImageIcon((Activity) parent, model.getBackground(), model.getBackground_local(), isPreferUrl);
            String imageUrlPathLogo = AppUtil.validationStringImageIcon((Activity) parent, model.getLogo(), model.getLogo_local(), isPreferUrl);
            Log.d("Lihat", "onBindViewHolder ChangeEventPastAdapter : " + isPreferUrl);

            //code here
            Glide.with((Activity) parent)
                    .load(InputValidUtil.isLinkUrl(imageUrlPathBack) ? imageUrlPathBack : new File(imageUrlPathBack))
                    .asBitmap()
                    .error(R.drawable.template_account_og)
                    .placeholder(R.drawable.ic_action_name)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(false)
                    .dontAnimate()
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                            holder.ivListChangeEventBackgroundfvbi.setImageBitmap(resource);
                            if (NetworkUtil.isConnected((Activity) parent) && isPreferUrl) {
                                String imageCachePath = PictureUtil.saveImageLogoBackIcon((Activity) parent, resource, model.getEvent_id() + "_Background_image");
                                holder.db.saveEventBackgroundPicture(imageCachePath, accountid, model.getEvent_id());
                            }

                        }
                    });
            Glide.with((Activity) parent)
                    .load(InputValidUtil.isLinkUrl(imageUrlPathLogo) ? imageUrlPathLogo : new File(imageUrlPathLogo))
                    .asBitmap()
                    .error(R.drawable.template_account_og)
                    .placeholder(R.drawable.ic_action_name)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(false)
                    .dontAnimate()
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                            holder.ivListChangeEventLogofvbi.setImageBitmap(resource);
                            if (NetworkUtil.isConnected((Activity) parent) && isPreferUrl) {
                                String imageCachePath = PictureUtil.saveImageLogoBackIcon((Activity) parent, resource, model.getEvent_id() + "_Logo_image");
                                holder.db.saveEventLogoPicture(imageCachePath, accountid, model.getEvent_id());
                            }

                        }
                    });

            holder.tvListChangeEventTitlefvbi.setText(model.getTitle());
            holder.tvListChangeEventDatefvbi.setText(model.getDate());
            holder.llListChangeEventfvbi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    parent.gotoEvent(model.getEvent_id());
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public void setData(List<UserListEventResponseModel> datas, boolean isPreferUrl) {
        this.isPreferUrl = isPreferUrl;
        /*datas = datas;
        notifyDataSetChanged();*/
        List<UserListEventResponseModel> ds = new ArrayList<>();
        for (UserListEventResponseModel d : datas) {
            if (d.getStatus().equalsIgnoreCase("PAST EVENT")) {
                ds.add(d);
            }
        }
        models = ds;
        notifyDataSetChanged();
    }

    public void replaceData(List<UserListEventResponseModel> datas, boolean isPreferUrl) {
        this.isPreferUrl = isPreferUrl;
        if (models.size() > 0) {
            models.clear();
            List<UserListEventResponseModel> ds = new ArrayList<>();
            for (UserListEventResponseModel d : datas) {
                if (d.getStatus().equalsIgnoreCase("PAST EVENT")) {
                    ds.add(d);
                }
            }
            models.addAll(ds);
        } else {
            setData(datas, isPreferUrl);
        }
        notifyDataSetChanged();
    }

    public void addDatas(List<UserListEventResponseModel> datas, boolean isPreferUrl) {
        models.addAll(datas);
        notifyItemRangeInserted(models.size(), datas.size());
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private final ImageView ivListChangeEventBackgroundfvbi;
        private final RoundedImageView ivListChangeEventLogofvbi;
        private final TextView tvListChangeEventTitlefvbi, tvListChangeEventDatefvbi;
        private final LinearLayout llListChangeEventfvbi;
        SQLiteHelper db = new SQLiteHelper(itemView.getContext());

        public ViewHolder(View itemView) {
            super(itemView);
            llListChangeEventfvbi = (LinearLayout) itemView.findViewById(R.id.llListChangeEvent);
            ivListChangeEventBackgroundfvbi = (ImageView) itemView.findViewById(R.id.ivListChangeEventBackground);
            ivListChangeEventLogofvbi = (RoundedImageView) itemView.findViewById(R.id.ivListChangeEventLogo);
            tvListChangeEventTitlefvbi = (TextView) itemView.findViewById(R.id.tvListChangeEventTitle);
            tvListChangeEventDatefvbi = (TextView) itemView.findViewById(R.id.tvListChangeEventDate);
        }
    }
}
