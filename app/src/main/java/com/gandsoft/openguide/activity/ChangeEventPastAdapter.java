package com.gandsoft.openguide.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
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
import com.gandsoft.openguide.API.APIresponse.UserData.UserListEventResponseModel;
import com.gandsoft.openguide.R;
import com.github.siyamed.shapeimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.List;

public class ChangeEventPastAdapter extends RecyclerView.Adapter<ChangeEventPastAdapter.ViewHolder> {
    private ChangeEventPastHook parent;
    private List<UserListEventResponseModel> models = new ArrayList<>();

    public ChangeEventPastAdapter(Activity parent, List<UserListEventResponseModel> items) {
        models = items;
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

    @SuppressLint("NewApi")
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        UserListEventResponseModel model = models.get(position);
        if (model.getStatus().equalsIgnoreCase("PAST EVENT")) {
            Glide.with((Activity) parent)
                    .load(model.getBackground())
                    .placeholder(R.drawable.template_account_og)
                    .error(R.drawable.template_account_og)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.ivListChangeEventBackgroundfvbi);
            Glide.with((Activity) parent)
                    .load(model.getLogo())
                    .placeholder(R.drawable.template_account_og)
                    .error(R.drawable.template_account_og)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.ivListChangeEventLogofvbi);
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

    class ViewHolder extends RecyclerView.ViewHolder {

        private final ImageView ivListChangeEventBackgroundfvbi;
        private final RoundedImageView ivListChangeEventLogofvbi;
        private final TextView tvListChangeEventTitlefvbi, tvListChangeEventDatefvbi;
        private final LinearLayout llListChangeEventfvbi;

        public ViewHolder(View itemView) {
            super(itemView);
            llListChangeEventfvbi = (LinearLayout) itemView.findViewById(R.id.llListChangeEvent);
            ivListChangeEventBackgroundfvbi = (ImageView) itemView.findViewById(R.id.ivListChangeEventBackground);
            ivListChangeEventLogofvbi = (RoundedImageView) itemView.findViewById(R.id.ivListChangeEventLogo);
            tvListChangeEventTitlefvbi = (TextView) itemView.findViewById(R.id.tvListChangeEventTitle);
            tvListChangeEventDatefvbi = (TextView) itemView.findViewById(R.id.tvListChangeEventDate);
        }
    }

    public void setData(List<UserListEventResponseModel> datas) {
        models = datas;
        notifyDataSetChanged();
    }

    public void replaceData(List<UserListEventResponseModel> datas) {
        if (models.size() > 0) {
            models.clear();
            models.addAll(datas);
        } else {
            models = datas;
        }
        notifyDataSetChanged();
    }

    public void addDatas(List<UserListEventResponseModel> datas) {
        models.addAll(datas);
        notifyItemRangeInserted(models.size(), datas.size());
    }
}
