package com.gandsoft.openguide.activity.main.adapter;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.gandsoft.openguide.API.API;
import com.gandsoft.openguide.API.APIrequest.HomeContent.HomeContentPostLikeRequestModel;
import com.gandsoft.openguide.API.APIresponse.Event.EventTheEvent;
import com.gandsoft.openguide.API.APIresponse.HomeContent.HomeContentPostLikeResponseModel;
import com.gandsoft.openguide.API.APIresponse.HomeContent.HomeContentResponseModel;
import com.gandsoft.openguide.API.APIresponse.LocalBaseResponseModel;
import com.gandsoft.openguide.API.APIresponse.UserData.UserListEventResponseModel;
import com.gandsoft.openguide.IConfig;
import com.gandsoft.openguide.R;

import org.sufficientlysecure.htmltextview.HtmlTextView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeContentAdapter extends RecyclerView.Adapter<HomeContentAdapter.ViewHolder> {

    private Context context;
    private FragmentActivity activity;
    private List<HomeContentResponseModel> models = new ArrayList<>();

    private List<HomeContentPostLikeResponseModel> modelsnyaLike = new ArrayList<>();
    private String eventId;
    private String accountId;
    private EventTheEvent theEventModel;
    private UserListEventResponseModel oneListEventModel;
    private HomeContentListener mListener;

    public HomeContentAdapter(FragmentActivity activity, List<HomeContentResponseModel> items, Context context, String eventId, String accountId, EventTheEvent theEventModel, UserListEventResponseModel oneListEventModel, HomeContentListener mListener) {
        this.activity = activity;
        this.context = context;
        models = items;
        this.eventId = eventId;
        this.accountId = accountId;
        this.theEventModel = theEventModel;
        this.oneListEventModel = oneListEventModel;
        this.mListener = mListener;
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
                Glide.with(context)
                        .load(new File(model.getImage_posted()))
                        .placeholder(R.drawable.template_account_og)
                        .skipMemoryCache(true)
                        .error(R.drawable.template_account_og)
                        .into(holder.ivRVHomeContentImage);
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

        if (Integer.parseInt(theEventModel.getCommentpost_status()) == 1) {
            holder.llRVHomeContentComment.setVisibility(View.VISIBLE);
        } else {
            holder.llRVHomeContentComment.setVisibility(View.GONE);
        }

        Log.d("Lihat", "onBindViewHolder HomeContentAdapter theEventModel.getDeletepost_status: " + theEventModel.getDeletepost_status());
        if (Integer.parseInt(theEventModel.getDeletepost_status()) == 1) {
            Log.d("Lihat", "onBindViewHolder HomeContentAdapter model.getAccount_id: " + model.getAccount_id().equalsIgnoreCase(accountId));
            Log.d("Lihat", "onBindViewHolder HomeContentAdapter oneListEventModel.getRole_name: " + oneListEventModel.getRole_name().equalsIgnoreCase("ADMIN"));
            if (model.getAccount_id().equalsIgnoreCase(accountId) || oneListEventModel.getRole_name().equalsIgnoreCase("ADMIN")) {
                holder.llRVHomeContentRemove.setVisibility(View.VISIBLE);
            } else {
                holder.llRVHomeContentRemove.setVisibility(View.GONE);
            }
        } else {
            holder.llRVHomeContentRemove.setVisibility(View.GONE);
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
                mListener.onCommentClick(models.get(position), position);
            }
        });

        holder.llRVHomeContentRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onDeletePostClick(model.getId(), position);
            }
        });

        if (model.getIs_new()) {
            holder.rlRVHomeContent.setVisibility(View.INVISIBLE);
        } else {
            holder.rlRVHomeContent.setVisibility(View.VISIBLE);
        }
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
                //progressDialog.dismiss();
                Log.d("Lihat", "onFailure HomeContentAdapter : " + t.getMessage());
                //Snackbar.make(findViewById(android.R.id.content), "Failed Connection To Server", Snackbar.LENGTH_SHORT).show();
                //Crashlytics.logException(new Exception(t.getMessage()));
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
        private final RelativeLayout rlRVHomeContent;

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
            rlRVHomeContent = (RelativeLayout) itemView.findViewById(R.id.rlRVHomeContent);
        }

    }

    public interface HomeContentListener {
        void onCommentClick(HomeContentResponseModel homeContentResponseModel, int position);

        void onDeletePostClick(String id, int position);
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
        int position = 0;
        models.add(position, data);
        notifyItemInserted(position);
    }

    public void removeDataFirst() {
        int position = 0;
        models.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, models.size());
    }

    public void changeDataFirstHasUploaded() {
        int position = 0;
        models.get(position).setIs_new(false);
        notifyItemChanged(position);
    }

    public void removeAt(int position) {
        models.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, models.size());
    }

    public void changeTotalComment(int position, String totalComment) {
        Log.d("Lihat", "changeTotalComment HomeContentAdapter : " + totalComment);
        if (totalComment.equalsIgnoreCase("0")) {
            models.get(position).setTotal_comment("0");
        } else {
            models.get(position).setTotal_comment(totalComment);
        }
        notifyItemChanged(position);
    }

}
