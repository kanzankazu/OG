package com.gandsoft.openguide.activity.main.adapter;

import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.gandsoft.openguide.API.APIresponse.HomeContent.HomeContentResponseModel;
import com.gandsoft.openguide.R;

import org.sufficientlysecure.htmltextview.HtmlTextView;

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


        private final TextView tvRVHomeContentUsername;
        private final HtmlTextView tvRVHomeContentKeterangan;
        private final TextView tvRVHomeContentComment;
        private final TextView tvRVHomeContentLike;
        private final TextView tvRVHomeContentTime;
        private final ImageView ivRVHomeContentImage;
        private final ImageView ivRVHomeContentLike;
        private final ImageView ivRVRVHomeContentIcon;
        private final LinearLayout llRVHomeContentLike;
        private final LinearLayout llRVHomeContentStatus;

        public ViewHolder(View itemView) {
            super(itemView);
            tvRVHomeContentUsername = (TextView) itemView.findViewById(R.id.tvRVHomeContentUsername);
            tvRVHomeContentKeterangan = (HtmlTextView) itemView.findViewById(R.id.tvRVHomeContentKeterangan);
            tvRVHomeContentComment = (TextView) itemView.findViewById(R.id.tvRVHomeContentComment);
            tvRVHomeContentLike = (TextView) itemView.findViewById(R.id.tvRVHomeContentLike);
            tvRVHomeContentTime = (TextView) itemView.findViewById(R.id.tvRVHomeContentTime);
            ivRVHomeContentImage = (ImageView) itemView.findViewById(R.id.ivRVHomeContentImage);
            ivRVHomeContentLike = (ImageView) itemView.findViewById(R.id.ivRVHomeContentLike);
            ivRVRVHomeContentIcon = (ImageView) itemView.findViewById(R.id.ivRVRVHomeContentIcon);
            llRVHomeContentLike = (LinearLayout) itemView.findViewById(R.id.llRVHomeContentLike);
            llRVHomeContentStatus = (LinearLayout) itemView.findViewById(R.id.llRVHomeContentStatus);
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
            holder.tvRVHomeContentKeterangan.setHtml(model.getKeterangan());
            holder.tvRVHomeContentKeterangan.setVisibility(View.VISIBLE);
        } else {
            holder.tvRVHomeContentKeterangan.setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(model.getImage_posted())) {
            holder.ivRVHomeContentImage.setVisibility(View.VISIBLE);
            Glide.with(activity)
                    .load(model.getImage_posted())
                    .placeholder(R.drawable.template_account_og)
                    .skipMemoryCache(true)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .error(R.drawable.template_account_og)
                    .into(holder.ivRVHomeContentImage);
        } else {
            holder.ivRVHomeContentImage.setVisibility(View.GONE);
        }

        Glide.with(activity)
                .load(model.getImage_icon())
                .placeholder(R.drawable.template_account_og)
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .error(R.drawable.template_account_og)
                .into(holder.ivRVRVHomeContentIcon);

        if (model.getStatus_like() != 0) {
            holder.ivRVHomeContentLike.setImageResource(R.drawable.ic_love_fill);
        } else {
            holder.ivRVHomeContentLike.setImageResource(R.drawable.ic_love_empty);
        }

        holder.llRVHomeContentLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int like = Integer.parseInt(model.getLike());
                if (model.getStatus_like() == 0) {
                    holder.tvRVHomeContentLike.setText(String.valueOf(like + 1));
                    holder.ivRVHomeContentLike.setImageResource(R.drawable.ic_love_fill);
                    model.setStatus_like(1);
                    model.setLike(String.valueOf(like + 1));
                } else {
                    holder.tvRVHomeContentLike.setText(String.valueOf(like - 1));
                    holder.ivRVHomeContentLike.setImageResource(R.drawable.ic_love_empty);
                    model.setStatus_like(0);
                    model.setLike(String.valueOf(like - 1));
                }
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

    public void replaceData(List<HomeContentResponseModel> datas) {
        models.clear();
        models.addAll(datas);
        notifyDataSetChanged();
    }

    public void addDatas(List<HomeContentResponseModel> datas) {
        models.addAll(datas);
        notifyItemRangeInserted(models.size(), datas.size());
    }

}
