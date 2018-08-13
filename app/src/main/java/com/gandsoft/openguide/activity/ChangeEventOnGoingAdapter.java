package com.gandsoft.openguide.activity;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.gandsoft.openguide.API.APIrespond.UserData.ListEventResponseModel;
import com.gandsoft.openguide.R;
import com.github.siyamed.shapeimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.List;

class ChangeEventOnGoingAdapter extends RecyclerView.Adapter<ChangeEventOnGoingAdapter.ViewHolder> {
    private Context context;
    private List<ListEventResponseModel> models = new ArrayList<>();

    public ChangeEventOnGoingAdapter(Context context, List<ListEventResponseModel> menuUi) {
        this.context = context;
        this.models = menuUi;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_list_change_event, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ListEventResponseModel model = models.get(position);
        if (model.getStatus().equalsIgnoreCase("PAST EVENT")) {
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
            Log.d("Lihat", "onBindViewHolder ChangeEventOnGoingAdapter : " + model.getLogo());
            Log.d("Lihat", "onBindViewHolder ChangeEventOnGoingAdapter : " + model.getBackground());
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

        public ViewHolder(View itemView) {
            super(itemView);
            ivListChangeEventBackgroundfvbi = (ImageView) itemView.findViewById(R.id.ivListChangeEventBackground);
            ivListChangeEventLogofvbi = (RoundedImageView) itemView.findViewById(R.id.ivListChangeEventLogo);
            tvListChangeEventTitlefvbi = (TextView) itemView.findViewById(R.id.tvListChangeEventTitle);
            tvListChangeEventDatefvbi = (TextView) itemView.findViewById(R.id.tvListChangeEventDate);
        }
    }

    public void setData(List<ListEventResponseModel> datas) {
        models = datas;
        notifyDataSetChanged();
    }

    public void replaceData(List<ListEventResponseModel> datas) {
        models.clear();
        models.addAll(datas);
        notifyDataSetChanged();
    }

    public void addDatas(List<ListEventResponseModel> datas) {
        models.addAll(datas);
        notifyItemRangeInserted(models.size(), datas.size());
    }
}
