package com.gandsoft.openguide.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
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
import com.gandsoft.openguide.ISeasonConfig;
import com.gandsoft.openguide.R;
import com.gandsoft.openguide.activity.main.BaseHomeActivity;
import com.gandsoft.openguide.support.SessionUtil;
import com.github.siyamed.shapeimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.List;

public class ChangeEventPastAdapter extends RecyclerView.Adapter<ChangeEventPastAdapter.ViewHolder> {
    private Context context;
    private List<UserListEventResponseModel> models = new ArrayList<>();

    public ChangeEventPastAdapter() {

    }

    public ChangeEventPastAdapter(Context context, List<UserListEventResponseModel> items) {
        this.context = context;
        models = items;
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
        Log.d("Lihat", "onBindViewHolder ChangeEventOnGoingAdapter : " + model.getLogo());
        Log.d("Lihat", "onBindViewHolder ChangeEventOnGoingAdapter : " + model.getBackground());
        Log.d("Lihat", "onBindViewHolder ChangeEventPastAdapter : " + model.getStatus());
        Log.d("Lihat", "onBindViewHolder ChangeEventPastAdapter : " + model.getTitle());
        model.getBackground();
        model.getLogo();
        Glide.with(context)
                .load(model.getBackground())
                .placeholder(R.drawable.template_account_og)
                .error(R.drawable.template_account_og)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.ivListChangeEventBackgroundfvbi);
        Glide.with(context)
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
                context.startActivity(BaseHomeActivity.getActIntent((Activity) context));
                SessionUtil.setStringPreferences(ISeasonConfig.KEY_EVENT_ID, model.getEvent_id());
            }
        });
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
        Log.d("Lihat", "setData ChangeEventPastAdapter : " + datas.size());
        models = datas;
        notifyDataSetChanged();
    }

    public void replaceData(List<UserListEventResponseModel> datas) {
        models.clear();
        models.addAll(datas);
        notifyDataSetChanged();
    }

    public void addDatas(List<UserListEventResponseModel> datas) {
        models.addAll(datas);
        notifyItemRangeInserted(models.size(), datas.size());
    }
}
