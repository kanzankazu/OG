package com.gandsoft.openguide.activity;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.gandsoft.openguide.API.APIresponse.UserData.UserListEventResponseModel;
import com.gandsoft.openguide.R;
import com.github.siyamed.shapeimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.List;

class ChangeEventOnGoingAdapter extends RecyclerView.Adapter<ChangeEventOnGoingAdapter.ViewHolder> {
    private ChangeEventPastHook parent;
    private List<UserListEventResponseModel> models = new ArrayList<>();

    public ChangeEventOnGoingAdapter(Context parent, List<UserListEventResponseModel> items) {
        models = items;
        try {
            this.parent = (ChangeEventPastHook) parent;
        } catch (Exception e) {
            this.parent = null;
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_list_change_event, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        UserListEventResponseModel model = models.get(position);
        if (model.getStatus().equalsIgnoreCase("ONGOING EVENT")) {
            Glide.with((Activity) parent)
                    .load(model.getBackground())
                    .placeholder(R.drawable.template_account_og)
                    .error(R.drawable.template_account_og)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .into(holder.ivListChangeEventBackgroundfvbi);
            Glide.with((Activity) parent)
                    .load(model.getLogo())
                    .placeholder(R.drawable.template_account_og)
                    .error(R.drawable.template_account_og)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
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
        /*datas = datas;
        notifyDataSetChanged();*/
        List<UserListEventResponseModel> ds = new ArrayList<>();
        for (UserListEventResponseModel d : datas) {
            if (d.getStatus().equalsIgnoreCase("ONGOING EVENT")) {
                ds.add(d);
            }
        }
        models = ds;
        notifyDataSetChanged();
    }

    public void replaceData(List<UserListEventResponseModel> datas) {
        if (models.size() > 0) {
            models.clear();
            List<UserListEventResponseModel> ds = new ArrayList<>();
            for (UserListEventResponseModel d : datas) {
                if (d.getStatus().equalsIgnoreCase("ONGOING EVENT")) {
                    ds.add(d);
                }
            }
            models = ds;
        } else {
            setData(datas);
        }
        notifyDataSetChanged();
    }

    public void addDatas(List<UserListEventResponseModel> datas) {
        models.addAll(datas);
        notifyItemRangeInserted(models.size(), datas.size());
    }
}
