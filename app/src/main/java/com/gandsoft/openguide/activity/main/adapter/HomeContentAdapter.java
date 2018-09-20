package com.gandsoft.openguide.activity.main.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.gandsoft.openguide.API.API;
import com.gandsoft.openguide.API.APIrequest.HomeContent.HomeContentPostLikeRequestModel;
import com.gandsoft.openguide.API.APIresponse.Event.EventTheEvent;
import com.gandsoft.openguide.API.APIresponse.HomeContent.HomeContentCommentModelParcelable;
import com.gandsoft.openguide.API.APIresponse.HomeContent.HomeContentPostLikeResponseModel;
import com.gandsoft.openguide.API.APIresponse.HomeContent.HomeContentResponseModel;
import com.gandsoft.openguide.API.APIresponse.LocalBaseResponseModel;
import com.gandsoft.openguide.API.APIresponse.UserData.UserListEventResponseModel;
import com.gandsoft.openguide.IConfig;
import com.gandsoft.openguide.ISeasonConfig;
import com.gandsoft.openguide.R;
import com.gandsoft.openguide.activity.main.fragments.aHomeActivityInFragment.aHomePostCommentActivity;
import com.gandsoft.openguide.support.PictureUtil;

import org.sufficientlysecure.htmltextview.HtmlTextView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeContentAdapter extends RecyclerView.Adapter<HomeContentAdapter.ViewHolder> {

    private Context context;
    private FragmentActivity activity;
    private List<HomeContentResponseModel> models = new ArrayList<>();
    private ArrayList<HomeContentCommentModelParcelable> dataParam = new ArrayList<>();
    private List<HomeContentPostLikeResponseModel> modelsnyaLike = new ArrayList<>();
    private String eventId;
    private String accountId;
    private EventTheEvent theEventModel;
    private UserListEventResponseModel oneListEventModel;

    public HomeContentAdapter(FragmentActivity activity, List<HomeContentResponseModel> items, Context context, String eventId, String accountId, EventTheEvent theEventModel, UserListEventResponseModel oneListEventModel) {
        this.activity = activity;
        this.context = context;
        models = items;
        this.eventId = eventId;
        this.accountId = accountId;
        this.theEventModel = theEventModel;
        this.oneListEventModel = oneListEventModel;
    }

    @Override
    public int getItemCount() {
        return models.size();
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
        if (!TextUtils.isEmpty(model.getKeterangan()) || !TextUtils.isEmpty(model.getEvent())) {
            holder.tvRVHomeContentKeterangan.setHtml(model.getKeterangan() + "" + model.getEvent());
            holder.tvRVHomeContentKeterangan.setVisibility(View.VISIBLE);
        } else {
            holder.tvRVHomeContentKeterangan.setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(model.getImage_posted())) {
            holder.ivRVHomeContentImage.setVisibility(View.VISIBLE);
            if (model.getImage_posted().matches("(?i).*http://.*")) {
                Glide.with(activity)
                        .load(model.getImage_posted())
                        .skipMemoryCache(true)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .thumbnail(0.1f)
                        .error(R.drawable.template_account_og)
                        .into(holder.ivRVHomeContentImage);
            } else {
                holder.ivRVHomeContentImage.setImageBitmap(PictureUtil.Base64ToBitmap(model.getImage_posted()));
            }

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

        if (Integer.parseInt(theEventModel.getCommentpost_status()) == 0) {
            holder.llRVHomeContentComment.setVisibility(View.GONE);
        }

        if (Integer.parseInt(theEventModel.getDeletepost_status()) == 1 || model.getAccount_id().equalsIgnoreCase(accountId) || oneListEventModel.getRole_name().equalsIgnoreCase("ADMIN")) {
            holder.llRVHomeContentRemove.setVisibility(View.VISIBLE);
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
                    postLike(model.getLike(), holder, model);
                } else {
                    holder.tvRVHomeContentLike.setText(String.valueOf(like - 1));
                    holder.ivRVHomeContentLike.setImageResource(R.drawable.ic_love_empty);
                    model.setStatus_like(0);
                    model.setLike(String.valueOf(like - 1));
                    postLike(model.getLike(), holder, model);
                }
            }
        });

        holder.llRVHomeContentComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HomeContentCommentModelParcelable mode = new HomeContentCommentModelParcelable();
                mode.setId(models.get(position).getId());
                mode.setLike(models.get(position).getLike());
                mode.setAccount_id(models.get(position).getAccount_id());
                mode.setTotal_comment(models.get(position).getTotal_comment());
                mode.setStatus_like(models.get(position).getStatus_like());
                mode.setUsername(models.get(position).getUsername());
                mode.setJabatan(models.get(position).getJabatan());
                mode.setDate_created(models.get(position).getDate_created());
                mode.setImage_icon(models.get(position).getImage_icon());
                mode.setImage_icon_local(models.get(position).getImage_icon_local());
                mode.setImage_posted(models.get(position).getImage_posted());
                mode.setImage_posted_local(models.get(position).getImage_posted_local());
                mode.setKeterangan(models.get(position).getKeterangan());
                mode.setEvent(models.get(position).getEvent());
                if (dataParam.size() > 0) {
                    dataParam.clear();
                    dataParam.add(mode);
                } else {
                    dataParam.add(mode);
                }
                Intent intent = new Intent(activity, aHomePostCommentActivity.class);
                intent.putParcelableArrayListExtra(ISeasonConfig.INTENT_PARAM, dataParam);
                activity.startActivity(intent);
            }
        });
    }

    public void postLike(String likes, ViewHolder holder, HomeContentResponseModel model) {
        HomeContentPostLikeRequestModel requestModel = new HomeContentPostLikeRequestModel();
        requestModel.setAccount_id(accountId);
        requestModel.setEvent_id(eventId);
        requestModel.setId_content(model.getId());
        requestModel.setVal_like(likes);
        requestModel.setDbver(String.valueOf(IConfig.DB_Version));
        if (model.getStatus_like() == 1) {
            requestModel.setStatus_like("0");
        } else if (model.getStatus_like() == 0) {
            requestModel.setStatus_like("1");
        }

        API.doHomeContentPostLikeRet(requestModel).enqueue(new Callback<List<LocalBaseResponseModel>>() {
            @Override
            public void onResponse(Call<List<LocalBaseResponseModel>> call, Response<List<LocalBaseResponseModel>> response) {
                if (response.isSuccessful()) {
                    List<LocalBaseResponseModel> s = response.body();
                    if (s.size() == 1) {
                        for (int i = 0; i < s.size(); i++) {
                            LocalBaseResponseModel model = s.get(i);
                            if (model.getStatus().equalsIgnoreCase("ok")) {
                                Log.d("Status ok", "ok");

                            } else {
                            }
                        }
                    } else {
                    }
                } else {
                }
            }

            @Override
            public void onFailure(Call<List<LocalBaseResponseModel>> call, Throwable t) {
                Log.d("tmassage", String.valueOf(t));
            }
        });
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
        private final LinearLayout llRVHomeContentComment;
        private final LinearLayout llRVHomeContentRemove;

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
            llRVHomeContentComment = (LinearLayout) itemView.findViewById(R.id.llRVHomeContentComment);
            llRVHomeContentRemove = (LinearLayout) itemView.findViewById(R.id.llRVHomeContentRemove);
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

    public void replaceData(List<HomeContentResponseModel> datas) {
        models.clear();
        models.addAll(datas);
        notifyDataSetChanged();
    }

    public void addDatas(List<HomeContentResponseModel> datas) {
        models.addAll(datas);
        notifyItemRangeInserted(models.size(), datas.size());
    }

    public void addDataFirst(HomeContentResponseModel data) {
        int insertIndex = 0;
        models.add(insertIndex, data);
        notifyItemInserted(insertIndex);
    }

    public void removeDataFirst() {
        int removeIndex = 0;
        models.remove(removeIndex);
        notifyItemRemoved(removeIndex);
    }

}
