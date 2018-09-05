package com.gandsoft.openguide.activity.main.adapter;

import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gandsoft.openguide.API.APIresponse.HomeContent.HomeContentResponseModel;
import com.gandsoft.openguide.R;

import java.util.ArrayList;
import java.util.List;

public class PostRecViewAdapter extends RecyclerView.Adapter<PostRecViewAdapter.ViewHolder> {

    private FragmentActivity activity;
    private List<HomeContentResponseModel> models = new ArrayList<>();

    public PostRecViewAdapter(FragmentActivity activity, List<HomeContentResponseModel> items) {
        this.activity = activity;
        models = items;
    }

    class ViewHolder extends RecyclerView.ViewHolder {


        private final TextView tvRVHomeContentUsername, tvRVHomeContentKeterangan, tvRVHomeContentComment, tvRVHomeContentLike;
        private final ImageView ivRVHomeContentImage, ivRVHomeContentLike, ivRVRVHomeContentIcon;
        private final TextView tvRVHomeContentTime;

        public ViewHolder(View itemView) {
            super(itemView);
            tvRVHomeContentUsername = (TextView) itemView.findViewById(R.id.tvRVHomeContentUsername);
            tvRVHomeContentKeterangan = (TextView) itemView.findViewById(R.id.tvRVHomeContentKeterangan);
            tvRVHomeContentComment = (TextView) itemView.findViewById(R.id.tvRVHomeContentComment);
            tvRVHomeContentLike = (TextView) itemView.findViewById(R.id.tvRVHomeContentLike);
            tvRVHomeContentTime = (TextView) itemView.findViewById(R.id.tvRVHomeContentTime);
            ivRVHomeContentImage = (ImageView) itemView.findViewById(R.id.ivRVHomeContentImage);
            ivRVHomeContentLike = (ImageView) itemView.findViewById(R.id.ivRVHomeContentLike);
            ivRVRVHomeContentIcon = (ImageView) itemView.findViewById(R.id.ivRVRVHomeContentIcon);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_list_home_adapter, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        HomeContentResponseModel model = models.get(position);
        holder.tvRVHomeContentComment.setText(model.getTotal_comment());
        holder.tvRVHomeContentLike.setText(model.getLike());
        holder.tvRVHomeContentUsername.setText(model.getUsername());
        holder.tvRVHomeContentTime.setText(model.getDate_created());
        if (!TextUtils.isEmpty(model.getKeterangan())) {
            holder.tvRVHomeContentKeterangan.setText(model.getKeterangan());
            holder.tvRVHomeContentKeterangan.setVisibility(View.VISIBLE);
        } else {
            holder.tvRVHomeContentKeterangan.setVisibility(View.GONE);
        }

        Glide.with(activity)
                .load(model.getImage_icon())
                .placeholder(R.drawable.template_account_og)
                .skipMemoryCache(true)
                .error(R.drawable.template_account_og)
                .into(holder.ivRVRVHomeContentIcon);
        Glide.with(activity)
                .load(model.getImage_posted())
                .placeholder(R.drawable.template_account_og)
                .skipMemoryCache(true)
                .error(R.drawable.template_account_og)
                .into(holder.ivRVHomeContentImage);
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

    public void replaceData(List<HomeContentResponseModel> datas) {
        models.clear();
        models.addAll(datas);
        notifyDataSetChanged();
    }

    public void addDatas(List<HomeContentResponseModel> datas) {
        models.addAll(datas);
        notifyItemRangeInserted(models.size(), datas.size());
    }

    /*public void setListContent(ArrayList<PostRecViewPojo> list_members) {
        this.models = list_members;
        notifyItemRangeChanged(0, list_members.size());
    }*/

}
